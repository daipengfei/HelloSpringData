package com.april.fourth.bean;

import org.springframework.stereotype.Component;

/**
 * Created by daipengfei
 * on 2017/3/22.
 */

@Component
public class BeanTwo {

    public BeanTwo(BeanOne beanOne) {
        System.out.println("init beanTwo !!!");
    }

}
