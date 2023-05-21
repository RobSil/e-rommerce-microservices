package com.robsil.autoconfiguration;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnClass({EurekaRegistration.class})
public class CustomGrpcMetadataEurekaConfiguration {

    private final EurekaRegistration eurekaRegistration;

    @Value("${grpc.server.port}")
    private int grpcPort;

    private static final Logger logger = LoggerFactory.getLogger(CustomGrpcMetadataEurekaConfiguration.class);

    public static final String CLOUD_DISCOVERY_METADATA_NAME = "gRPC_port";

    public CustomGrpcMetadataEurekaConfiguration(EurekaRegistration eurekaRegistration) {
        this.eurekaRegistration = eurekaRegistration;
    }

    @PostConstruct
    public void init() {
        logger.debug("CustomGrpcMetadataEurekaConfiguration init started");
        if (eurekaRegistration != null) {
            if (grpcPort < 1) {
                grpcPort = 9082;
            }
            eurekaRegistration.getInstanceConfig()
                    .getMetadataMap()
                    .put(CLOUD_DISCOVERY_METADATA_NAME, Integer.toString(grpcPort));
            logger.info("successfully set metadata with grpc info. Port: {}", grpcPort);
            logger.debug("metadata name: {}, port: {}", CLOUD_DISCOVERY_METADATA_NAME, grpcPort);
        }
    }

}
