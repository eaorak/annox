package com.adenon.sp.kernel.utils.log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.varia.LevelRangeFilter;

import com.adenon.sp.kernel.osgi.BundleInfo;
import com.adenon.sp.kernel.osgi.ManifestHeaders;
import com.adenon.sp.kernel.properties.SysProps;


class PropertyReloader implements Runnable {


    private final LoggingService util;
    private final File           file;
    private long                 lastModified;

    public PropertyReloader(LoggingService util,
                            String filePath) {
        this.util = util;
        this.file = new File(filePath);
        this.lastModified = this.file.lastModified();
    }

    @Override
    public void run() {
        long modified = this.file.lastModified();
        if (modified != this.lastModified) {
            this.util.loadConfiguration();
            this.lastModified = modified;
        }
    }

}

public class LoggingService implements ILoggingService {

    private static String               LOGGER_PATTERN = "|%d|%-5p|%-15t|%C{1}.%M|# %m%n";
    private static String               LOG_DIR        = "logs";
    private static String               LOG_HOME;
    private static String               SEP;
    private static String               LOG_FILE       = "sp-logging.properties";
    //
    private Level                       defaultLevel   = Level.DEBUG;
    private boolean                     override       = false;
    //
    private static final LoggingService INSTANCE       = new LoggingService();
    //
    private Appender                    errorAppender;
    private final Map<String, LogInfo>  logInfoMap     = new Hashtable<String, LogInfo>();
    private final Map<String, Level>    bundleLogging  = new HashMap<String, Level>();
    private PropertiesConfiguration     propConf;
    private final String                propFilePath;

    public static LoggingService getInstance() {
        return INSTANCE;
    }

    private LoggingService() {
        final String spHome = SysProps.HOME_PATH.value();
        SEP = SysProps.PATH_SEP.value();
        LOG_HOME = spHome + SEP + LOG_DIR + SEP;
        //
        String spHomeProperty = System.getProperty("SP_HOME");
        if ((spHomeProperty != null) && !"".equals(spHomeProperty)) {
            this.propFilePath = spHomeProperty + SEP + SysProps.CONF_PATH.value() + SEP + LOG_FILE;
        } else {
            this.propFilePath = spHome + SEP + SysProps.CONF_PATH.value() + SEP + LOG_FILE;
        }
        this.loadConfiguration();
        try {
            this.errorAppender = this.createErrorAppender();
        } catch (final IOException e) {
            throw new RuntimeException("Error on creating error appender !", e);
        }
        ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(1);
        schedulePool.scheduleWithFixedDelay(new PropertyReloader(this, this.propFilePath), 0, 10, TimeUnit.SECONDS);
    }

    public synchronized void loadConfiguration() {
        try {
            this.propConf = new PropertiesConfiguration(this.propFilePath);
            this.defaultLevel = Level.toLevel(this.propConf.getString(LoggingProps.SYSTEM_LOG_LEVEL.property()));
            this.override = this.propConf.getBoolean(LoggingProps.SYSTEM_OVERRIDE_LEVELS.property());
            this.bundleLogging.clear();
            // Read bundle log properties
            Iterator<String> keys = this.propConf.getKeys();
            String bundleKey = LoggingProps.LEVEL_BUNDLE_NAME.property();
            while (keys.hasNext()) {
                String keyName = keys.next();
                if (keyName.startsWith(bundleKey)) {
                    String bundleName = LoggingProps.toBundleName(keyName);
                    this.bundleLogging.put(bundleName, Level.toLevel(this.propConf.getString(keyName)));
                    this.changeLevelOf(bundleName);
                }
            }
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public Map<String, ILogInfo> getBundleLogInfo() {
        return (Map) this.logInfoMap;
    }

    public synchronized ILogInfo createBundleLogger(final BundleInfo bundleInfo,
                                                    final String loggerName,
                                                    final BundleType type) throws IOException {
        if (type == BundleType.API) {
            return new LogInfo(this, null, null, Level.OFF);
        }
        final String bundleName = bundleInfo.get(ManifestHeaders.BUNDLE_SYMBOLICNAME);
        LogInfo logInfo = this.logInfoMap.get(bundleName);
        if (logInfo == null) {
            logInfo = this.createLogger(bundleInfo, loggerName, type.path());
            this.logInfoMap.put(bundleName, logInfo);
        }
        return logInfo;
    }

    @Override
    public void setSystemLogLevel(Level level) {
        for (LogInfo info : this.logInfoMap.values().toArray(new LogInfo[0])) {
            info.setLevel(level);
        }
        this.defaultLevel = level;
        this.saveConfiguration();
    }

    @Override
    public void changeLevelOf(String bundleName,
                              Level level) {
        LogInfo logInfo = this.logInfoMap.get(bundleName);
        if (logInfo == null) {
            throw new RuntimeException("No bundle could be found with name [" + bundleName + "] !");
        }
        logInfo.setLevel(level);
        this.bundleLogging.put(bundleName, level);
        this.saveConfiguration();
    }


    @Override
    public Level getSystemLogLevel() {
        return this.defaultLevel;
    }


    void saveConfiguration() {
        this.propConf.clear();
        this.propConf.addProperty(LoggingProps.SYSTEM_LOG_LEVEL.property(), this.defaultLevel.toString());
        this.propConf.addProperty(LoggingProps.SYSTEM_OVERRIDE_LEVELS.property(), this.override);
        for (String bundleName : this.bundleLogging.keySet()) {
            Level level = this.bundleLogging.get(bundleName);
            this.propConf.addProperty(LoggingProps.toKey(bundleName), level.toString());
        }
        try {
            this.propConf.save();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    //

    private LogInfo createLogger(final BundleInfo bundleInfo,
                                 final String loggerName,
                                 final String path) throws IOException {
        final String logPath = LOG_HOME + path + SEP + bundleInfo.get(ManifestHeaders.BUNDLE_NAME) + ".log";
        final Appender appender = this.createAppender(logPath);
        //
        final Logger logger = Logger.getLogger(loggerName);
        logger.setLevel(Level.ALL);
        logger.addAppender(appender);
        logger.addAppender(this.errorAppender);
        final LogInfo logInfo = new LogInfo(this, appender, logger, this.defaultLevel);
        this.changeLevelOf(bundleInfo.get(ManifestHeaders.BUNDLE_SYMBOLICNAME));
        return logInfo;
    }

    private void changeLevelOf(String bundleName) {
        Level newLevel = this.bundleLogging.get(bundleName);
        newLevel = (newLevel == null) ? this.defaultLevel : (this.override ? this.defaultLevel : newLevel);
        LogInfo logInfo = this.logInfoMap.get(bundleName);
        if (logInfo == null) {
            return;
        }
        logInfo.setLevel(newLevel);
    }

    // TODO : Get logger properties from file.
    private FileAppender createAppender(final String fullPath) throws IOException {
        final PatternLayout layout = new PatternLayout();
        layout.setConversionPattern(LOGGER_PATTERN);
        final RollingFileAppender appender = new RollingFileAppender(layout, fullPath, true);
        appender.setMaxFileSize("10000KB");
        appender.setMaxBackupIndex(10);
        return appender;
    }

    private Appender createErrorAppender() throws IOException {
        final String logPath = LOG_HOME + "errors.log";
        final Appender appender = this.createAppender(logPath);
        this.setFilter(appender, Level.ERROR);
        return appender;
    }

    public void setFilter(Appender appender,
                          Level level) {
        final LevelRangeFilter filter = new LevelRangeFilter();
        filter.setLevelMin(level);
        appender.addFilter(filter);
    }


}
