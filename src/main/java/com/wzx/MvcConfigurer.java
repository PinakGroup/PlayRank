package com.wzx;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by arthurwang on 16/12/28.
 */
@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index.html");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        super.configurePathMatch(configurer);
//        configurer.setUseSuffixPatternMatch(false);
//    }
}
