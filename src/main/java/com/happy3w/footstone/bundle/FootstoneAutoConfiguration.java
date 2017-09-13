package com.happy3w.footstone.bundle;

import com.happy3w.footstone.exception.IExceptionGrave;
import com.happy3w.footstone.res.ResConst;
import com.happy3w.footstone.resource.IResourceMgrSvc;
import com.happy3w.footstone.sql.DefaultDatabaseMgrSvc;
import com.happy3w.footstone.sql.IDatabaseMgrSvc;
import com.happy3w.footstone.svc.IIDGeneratorSvc;
import com.happy3w.footstone.ui.action.IObjectActionSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.happy3w.footstone.bundle", "com.happy3w.footstone.sql"})
public class FootstoneAutoConfiguration {
    @Bean
    public IResourceMgrSvc resourceMgrSvc() {
        IResourceMgrSvc svc = new ResourceMgrSvcImpl();
        try {
            svc.regRes(ResConst.ResKey, ResConst.class.getResourceAsStream("resource.properties"));
        } catch (IOException e) {
            log.error("Unexpected error when load resource.", e);
        }
        return svc;
    }

    @Bean
    public IExceptionGrave exceptionGrave() {
        return new ExceptionGraveImpl();
    }

    @Bean
    public IObjectActionSvc objectActionSvc() {
        return new ObjectActionSvcImpl();
    }

    @Bean
    public IIDGeneratorSvc iidGeneratorSvc() {
        return new IDGeneratorSvcImpl();
    }

    @Bean
    public IDatabaseMgrSvc iDatabaseMgrSvc() {
        return new DefaultDatabaseMgrSvc();
    }
}
