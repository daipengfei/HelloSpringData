package com.april.fourth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by daipengfei
 * on 2017/3/22.
 */

@SpringBootApplication
@ImportResource("/context.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
