package com.adenon.sp.kernel.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.osgi.framework.Bundle;

import com.adenon.sp.kernel.properties.SysProps;

public class BundleScanService implements IBundleScanService {

    private static String                      BUNDLE_PATH_SEPERATOR = ":file:";
    private static String                      PATH_SEPERATOR        = System.getProperty("file.separator");
    private static Map<String, List<Class<?>>> classesMap            = new ConcurrentHashMap<String, List<Class<?>>>();
    //
    private static BundleScanService           INSTANCE              = new BundleScanService();

    public static BundleScanService getInstance() {
        return INSTANCE;
    }

    private BundleScanService() {
    }

    @Override
    public List<Class<?>> getAnnotatedClassesOf(final Bundle bundle,
                                                final Class<? extends Annotation> annotation) throws Exception {
        final List<Class<?>> classes = this.getAllClassesOf(bundle);
        return this.getAnnotatedClassesIn(classes, annotation);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<Class<T>> getExtendedClassesOf(final Bundle bundle,
                                                   final Class<T> extended) throws Exception {
        final List<Class<T>> classList = new ArrayList<Class<T>>();
        final List<Class<?>> allClasses = this.getAllClassesOf(bundle);
        for (final Class<?> c : allClasses) {
            if (extended.isAssignableFrom(c)) {
                classList.add((Class<T>) c);
            }
        }
        return classList;
    }

    @Override
    public List<Class<?>> getAllClassesOf(final Bundle bundle) throws Exception {
        if (bundle.getHeaders().get("Fragment-Host") != null) {
            return new ArrayList<Class<?>>();
        }
        final String bundleId = this.bundleIdOf(bundle);
        List<Class<?>> list = classesMap.get(bundleId);
        if (list == null) {
            list = new ArrayList<Class<?>>();
            final String path = this.getPath(bundle);
            if (this.validPath(path)) {
                final boolean deployMode = path.endsWith(".jar" + File.separator);
                final List<String> classNames = deployMode ? this.getForDeployment(path) : this.getForDevelopment(path);
                list = this.getAllClasses(classNames, bundle);
            } else {
                System.err.println(" # INVALID BUNDLE PATH : " + path);
            }
            classesMap.put(bundleId, list);
        }
        return list;
    }

    private boolean validPath(final String path) {
        return !path.contains(" ") && !path.startsWith("plugins") && !path.contains("org.");
    }

    //

    private List<Class<?>> getAnnotatedClassesIn(final List<Class<?>> classes,
                                                 final Class<? extends Annotation> annotation) throws Exception {
        final List<Class<?>> list = new ArrayList<Class<?>>();
        for (final Class<?> c : classes) {
            if (c.getAnnotation(annotation) != null) {
                list.add(c);
            }
        }
        return list;
    }

    private String getPath(final Bundle bundle) {
        final String location = bundle.getLocation();
        return location.substring(location.indexOf(BUNDLE_PATH_SEPERATOR) + BUNDLE_PATH_SEPERATOR.length());
    }

    private List<Class<?>> getAllClasses(final List<String> classNames,
                                         final Bundle bundle) throws ClassNotFoundException {
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        for (final String className : classNames) {
            final Class<?> clazz = bundle.loadClass(className);
            classes.add(clazz);
        }
        return classes;
    }

    private List<String> getForDeployment(final String path) throws IOException {
        // Sample path :: /elron/workspace/srv_platform/platform/repository/services/service-test_01.00.00.000.jar
        final String fullPath = SysProps.fullPathOf(SysProps.BUNDLE_HOME_PATH) + SysProps.PATH_SEP + path;
        final List<String> classNames = new ArrayList<String>();
        final JarFile jarFile = new JarFile(fullPath);
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                final String className = this.toClassName(entry.getName());
                classNames.add(className);
            }
        }
        return classNames;
    }

    private List<String> getForDevelopment(String path) throws IOException {
        // Sample path :: ../workspace/srv_platform/source/service-test/
        final List<String> classNames = new ArrayList<String>();
        path += "bin" + PATH_SEPERATOR;
        final File file = new File(path);
        // System.err.println(">> Bundle File Path : " + file.getAbsolutePath());
        final Iterator<File> files = FileUtils.iterateFiles(file, new String[] { "class" }, true);
        if (files != null) {
            while (files.hasNext()) {
                final File classFile = files.next();
                final String absolutePath = classFile.getAbsolutePath();
                final int pkg = absolutePath.indexOf(PATH_SEPERATOR + "bin" + PATH_SEPERATOR) + 5;
                final String className = this.toClassName(classFile.getAbsolutePath().substring(pkg));
                classNames.add(className);
            }
        }
        return classNames;
    }

    private String bundleIdOf(final Bundle bundle) {
        return bundle.getSymbolicName() + bundle.getVersion().getQualifier();
    }

    private String toClassName(final String path) {
        return path.replace(PATH_SEPERATOR, ".").replace(".class", "");
    }

}
