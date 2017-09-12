package com.happy3w.memoryassistant.utils;

import org.springframework.context.ConfigurableApplicationContext;

public class ContextHolder {
    private static ConfigurableApplicationContext context;

    public static void setContext(ConfigurableApplicationContext context) {
        ContextHolder.context = context;
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }
}
