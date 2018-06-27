package com.leafstudio.contacts.application;

import com.leafstudio.contacts.BuildConfig;

import timber.log.Timber;

public class TimberLogImplementation {
    public static void init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
//            Timber.plant(new LoggingTree());
            /**
             Timber.plant(new Timber.DebugTree() {
            @Override protected String createStackElementTag(StackTraceElement element) {
            return String.format("C:%s:%s",
            super.createStackElementTag(element),
            element.getLineNumber());
            }
            });
             */
        } else
            Timber.plant(new ReleaseTree());
    }
}
