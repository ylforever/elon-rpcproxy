package com.elon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 注入HTTPS的rest template
 *
 * @author elon
 * @since 2021/10/17
 */
@Configuration
public class HTTPSRestConfigTemplate {
    /**
     * 注入RestTemplate实例
     *
     * @return RestTemplate实例
     * @author elon
     */
    @Bean(name = "httpsRestConfigTemplate")
    public RestTemplate getRestTemplate(){
        return new RestTemplate(new HttpsClientRequestFactory());
    }
}
