package com.april.fourth.init;

import com.april.fourth.bean.BeanTwo;
import com.april.fourth.entity.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by daipengfei
 * on 2017/3/22.
 */

@Component
public class InitService implements ApplicationRunner {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private BeanTwo beanTwo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Order> orders = mongoTemplate.find(
                new Query(Criteria.where("_id").is("ORDER10006")), Order.class);
        System.out.println(orders);
        mongoTemplate.save(new Order("ORDER10007",new Date(),new GeoJsonPoint(0,1)));
    }
}
