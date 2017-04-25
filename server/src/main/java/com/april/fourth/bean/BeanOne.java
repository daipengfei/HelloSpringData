package com.april.fourth.bean;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * Created by daipengfei
 * on 2017/3/22.
 */

public class BeanOne implements InitializingBean{
    private String age;

    public BeanOne() {
        System.out.println(age);
        System.out.println("construct bean one!");
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        System.out.println("set age! age = " + age);
        this.age = age;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("age !!! = " + age);
    }

    @PostConstruct
    public void init(){
        System.out.println("init!!!");
    }

}
