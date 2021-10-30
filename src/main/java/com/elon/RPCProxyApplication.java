package com.elon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RPCProxyApplication {
    private static final Logger LOGGER = LogManager.getLogger(RPCProxyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RPCProxyApplication.class);
        LOGGER.info("Start up RPCProxyApplication success.");
    }
}
