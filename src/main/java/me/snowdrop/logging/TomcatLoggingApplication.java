/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 * <p>
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package me.snowdrop.logging;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="claprun@redhat.com">Christophe Laprun</a>
 */
@org.springframework.boot.autoconfigure.SpringBootApplication
public class TomcatLoggingApplication {
    private static Log logger = LogFactory.getLog(TomcatLoggingApplication.class);

    // tag::snippet-listener[]
    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                logger.info("---> ServletContext initialized");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                logger.info("---> ServletContext destroyed");
            }

        };
    }
    // end::snippet-listener[]

    // tag::snippet-container[]
    @Bean
    public ServletWebServerFactory servletContainer() {
        // configure embedded Tomcat container
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

        // Default configuration file: logback-access.xml
        LogbackValve logbackValve = new LogbackValve();

        logbackValve.setFilename("logback-access.xml");

        // Add logback valve to embedded Tomcat
        tomcat.addContextValves(logbackValve);

        return tomcat;
    }
    // end::snippet-container[]

    public static void main(String[] args) {
        SpringApplication.run(TomcatLoggingApplication.class, args);
    }
}
