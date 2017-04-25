package com.april.fourth.bean;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daipengfei
 * on 2017/3/22.
 */

@Component
public class BeanTwo {

    static final Map<Integer, Integer> map = new HashMap<>();

    static {
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);

    }

    public BeanTwo(BeanOne beanOne) {
        System.out.println("init beanTwo !!!");
    }

    @Cacheable(value = "weather", key = "#key")
    public int getFromCache(int key) {
        System.out.println("get!!!!!!!!!");
        return map.get(key);
    }

    static class Source {
        private Date now;

        public Date getNow() {
            return now;
        }

        public void setNow(Date now) {
            this.now = now;
        }
    }

    static class Des {
        private Date now;

        public Date getNow() {
            return now;
        }

        public void setNow(Date now) {
            this.now = now;
        }
    }

    public static void main(String[] args) {
        Source source = new Source();
        Des des = new Des();
        BeanUtils.copyProperties(source, des);
        System.out.println(des.getNow());
    }

}
