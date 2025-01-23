package com.volvo.emspdemo;

import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class EmspDemoApplication {

    public static void main(String[] args) {
        // 手动配置 Axon Server
        // 手动配置 Axon
        /*Configuration axonConfig = DefaultConfigurer.defaultConfiguration()
                .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine()) // 使用内存事件存储
                .configureEventBus(c -> SimpleEventBus.builder().build()) // 配置事件总线
                .start(); // 启动 Axon 配置
*/
        SpringApplication.run(EmspDemoApplication.class, args);
    }

}
