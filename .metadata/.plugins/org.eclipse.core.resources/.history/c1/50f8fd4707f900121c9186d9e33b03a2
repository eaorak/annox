package com.adenon.sp.kernel.utils.log;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.LevelRangeFilter;

public class LogInfo implements ILogInfo {

    private final Logger         logger;
    private Level                level;
    private LevelRangeFilter     filter;
    private final LoggingService logging;

    public LogInfo(LoggingService logging,
                   Appender appender,
                   Logger logger,
                   Level level) {
        this.logging = logging;
        this.level = level;
        this.logger = logger;
        if (logger != null) {
            this.filter = new LevelRangeFilter();
            this.filter.setLevelMin(level);
            appender.addFilter(this.filter);
        }
    }

    public void setLevel(Level level) {
        if (this.logger == null) {
            return;
        }
        this.level = level;
        this.filter.setLevelMin(level);
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    public Logger getLogger() {
        return this.logger;
    }

}
