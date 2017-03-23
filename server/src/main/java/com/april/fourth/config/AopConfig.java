package com.april.fourth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;

/**
 * Created by daipengfei
 * on 2017/3/23.
 */
@EnableLoadTimeWeaving
@Configuration
public class AopConfig {

    @Bean
    public LoadTimeWeaver loadTimeWeaver() {
        System.out.println("hi");
        return new InstrumentationLoadTimeWeaver();
    }

}
