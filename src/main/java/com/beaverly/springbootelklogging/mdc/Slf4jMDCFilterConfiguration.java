package com.beaverly.springbootelklogging.mdc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class Slf4jMDCFilterConfiguration {

    @Bean
    public FilterRegistrationBean servletRegistrationBean(
            final OncePerRequestFilter slf4jMdcFilter
            ) {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(slf4jMdcFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
