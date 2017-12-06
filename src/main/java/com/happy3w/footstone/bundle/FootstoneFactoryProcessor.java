package com.happy3w.footstone.bundle;

import com.happy3w.footstone.FootstoneSvcAccess;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

public class FootstoneFactoryProcessor implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        FootstoneSvcAccess.setBeanFactory(beanFactory);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
