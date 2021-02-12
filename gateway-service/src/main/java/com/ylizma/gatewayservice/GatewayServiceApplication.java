package com.ylizma.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

//    @Bean
//     RouteLocator gatewayLocator(RouteLocatorBuilder locatorBuilder){
//////        return locatorBuilder.routes()
//////                .route(r -> r.path("/customers/**").uri("http://localhost:8081"))
//////                .route(r -> r.path("/products/**").uri("http://localhost:8082"))
//////                .build();
////
//        return locatorBuilder.routes()
//                .route(r -> r.path("/customers/**").uri("lb://customer-service"))
//                .route(r -> r.path("/products/**").uri("lb://inventory-service"))
//                .build();
//     }

    @Bean
    DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(ReactiveDiscoveryClient client, DiscoveryLocatorProperties properties){
        return new DiscoveryClientRouteDefinitionLocator(client,properties);
    }
}


