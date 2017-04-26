package com.april.fourth.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * Created by daipengfei
 * on 2017/4/19.
 */
@Configuration
public class ElasticSearchConfig {

    @Bean
    public TransportClient transportClient() {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch_daipengfei")
//                .put("client.transport.sniff", true)
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);
//        client.addTransportAddresses(
//                new InetSocketTransportAddress(
//                        new InetSocketAddress("192.168.11.44", 9300)),
//                new InetSocketTransportAddress(
//                        new InetSocketAddress("192.168.11.48", 9300)),
//                new InetSocketTransportAddress(
//                        new InetSocketAddress("192.168.11.51", 9300)));
        client.addTransportAddresses(
                new InetSocketTransportAddress(
                        new InetSocketAddress("127.0.0.1", 9300)));
        return client;
    }
}
