package com.adenon.sp.kernel.utils;

import java.lang.annotation.Annotation;
import java.util.List;

import org.osgi.framework.Bundle;

public interface IBundleScanService {

    List<Class<?>> getAllClassesOf(Bundle bundle) throws Exception;

    List<Class<?>> getAnnotatedClassesOf(Bundle bundle,
                                         Class<? extends Annotation> annotation) throws Exception;

    <T> List<Class<T>> getExtendedClassesOf(Bundle bundle,
                                            Class<T> parent) throws Exception;

}