package com.wzx.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by arthurwang on 17/3/17.
 */
@Configuration
public class WebConfig {
    @Bean
    ServletRegistrationBean<WebServlet> h2servletRegistration() {
        ServletRegistrationBean<WebServlet> registrationBean =
                new ServletRegistrationBean<>(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
