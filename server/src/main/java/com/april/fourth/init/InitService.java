package com.april.fourth.init;

import com.april.fourth.bean.BeanTwo;
import com.april.fourth.dto.PersonDTO;
import com.april.fourth.entity.Order;
import com.april.fourth.service.HelloPerson;
import org.easyrules.api.RulesEngine;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
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
//    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RulesEngine rulesEngine;

    @Resource
    private BeanTwo beanTwo;

    @Resource
    private HelloPerson helloPerson;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Criteria criteria = new Criteria();
        CriteriaQuery query = new CriteriaQuery(criteria);
        elasticsearchTemplate.queryForList(query,Object.class);
//        rulesEngine.fireRules();
//        mongoOps();
    }

    private void mongoOps() {
        org.springframework.data.mongodb.core.query.Criteria criteria =
                org.springframework.data.mongodb.core.query.Criteria.where("_id").is("ORDER10006");
        PersonDTO personDTO = helloPerson.helloPerson();
        System.out.println(personDTO.getName());
        List<Order> orders = mongoTemplate.find(
                new Query(criteria), Order.class);
        System.out.println(orders);
        mongoTemplate.save(new Order("ORDER10007",new Date(),new GeoJsonPoint(0,1)));
    }
}
