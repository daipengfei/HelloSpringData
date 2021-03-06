package com.april.fourth.init;

import com.april.fourth.bean.BeanTwo;
import com.april.fourth.dto.PersonDTO;
import com.april.fourth.entity.Order;
import com.april.fourth.service.HelloPerson;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.lucene.spatial3d.geom.GeoDistance;
import org.easyrules.api.RulesEngine;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.DocValueFormat;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by daipengfei
 * on 2017/3/22.
 */

@Component
public class InitService implements ApplicationRunner, InitializingBean {
    //    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RulesEngine rulesEngine;

    @Resource
    private BeanTwo beanTwo;

    @Resource
    private HelloPerson helloPerson;

    @Resource
    private TransportClient transportClient;

    private Cache<Integer, Integer> cache;

    private static final DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        riderPositionUpdate();
//        try {
//            Integer integer = cache.get(1, new Callable<Integer>() {
//                @Override
//                public Integer call() throws Exception {
//                    return null;
//                }
//            });
//            System.out.println(integer);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        System.out.println("null !!!  = " + beanTwo.getFromCache(10));
//        System.out.println(beanTwo.getFromCache(2));
//        System.out.println(beanTwo.getFromCache(3));
//        System.out.println(beanTwo.getFromCache(1));
//        System.out.println(cache.getUnchecked(1));
//        Thread.sleep(1000);
//        System.out.println(cache.getUnchecked(1));
//        System.out.println(cache.getUnchecked(3));
//        System.out.println(cache.getUnchecked(5));
//        System.out.println(cache.getUnchecked(7));
//        insertRiderPosition();
//        riderPositionSearch();
//        devEs();
//        localEs();
//        List<DiscoveryNode> discoveryNodes = client.listedNodes();
//        System.out.println(discoveryNodes);
//        rulesEngine.fireRules();
//        mongoOps();
    }

    private void riderPositionSearch() {
        QueryBuilder qb = geoDistanceQuery("point")
                .point(new GeoPoint(40.12, -71.34))
                .distance(8.853, DistanceUnit.KILOMETERS);
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch("rider")
                .setTypes("rider_position");

        searchRequestBuilder.setQuery(
                boolQuery().filter(termQuery("cityId", 1))
                        .filter(rangeQuery("lastUpdateTime").gte(formatter.print(new DateTime().plusHours(-2))))
                        .filter(qb));
//        searchRequestBuilder.setPostFilter(rangeQuery("lastUpdateTime").gte(new Date()));
        searchRequestBuilder.addSort(SortBuilders.geoDistanceSort("point",40.12,-71.34)
        .order(SortOrder.ASC).unit(DistanceUnit.METERS));
        searchRequestBuilder.setSize(3);
        System.out.println(searchRequestBuilder.toString());
        SearchResponse searchResponse = searchRequestBuilder.get();
        System.out.println(searchResponse);
        SearchHit[] hits = searchResponse.getHits().getHits();
        System.out.println(searchResponse.getHits().getHits().length);
        for (SearchHit hitFields : hits) {
            Map<String, Object> sourceAsMap = hitFields.sourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    private void riderPositionUpdate() throws IOException {
//        UpdateByQueryRequestBuilder requestBuilder =
//                UpdateByQueryAction.INSTANCE.newRequestBuilder(transportClient);
//        requestBuilder.source("rider").source().setTypes("rider_position");
//        BulkIndexByScrollResponse r =
//                requestBuilder
//                        .script(new Script("ctx._source.point.lat = 40.32;ctx._source.point.lon = -71.54"))
//                        .filter(termQuery("riderId", 5446655))
//                        .filter(termQuery("cityId", 1))
//                        .get();
//        System.out.println(r);
        final SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch("rider")
                .setTypes("rider_position")
                .setQuery(boolQuery().filter(termQuery("riderId", 5446655))
                .filter(termQuery("cityId", 1)))
                .setSize(1);
        SearchResponse searchResponse = searchRequestBuilder.get();

        System.out.println(searchResponse);

        Map<String, GeoPoint> map = new HashMap<>();
        map.put("point", new GeoPoint(40.12, -71.33));
        UpdateResponse updateResponse = transportClient.prepareUpdate().setIndex("rider")
                .setType("rider_position").setId(searchResponse.getHits().getHits()[0].getId())
                .setDoc(map).get();
        System.out.println(updateResponse);

//        BulkIndexByScrollResponse response = UpdateByQueryAction.INSTANCE.newRequestBuilder(transportClient)
//                .filter(matchQuery("cityId", 1))
//                .filter(matchQuery("riderId", 5446655))
//                .source("rider").
//                .get();

    }

    private void devEs() throws IOException {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "daipengfei")
                .field("postDate", new Date())
                .startArray("message")
                .startObject().field("message1", "trying out Elasticsearch1").endObject()
                .startObject().field("message2", "trying out Elasticsearch2").endObject()
                .endArray()
                .endObject();
//        Map<String,Object> map = new HashMap<>();
//        map.put("user","daipengfei");
//        map.put("postDate",new Date());
//        Map<String,String> m1 = new HashMap<>();
//        Map<String,String> m2 = new HashMap<>();
//        m1.put("message1","hello");
//        m2.put("message2","hello2");
//        map.put("message", Arrays.asList(m1,m2));
        IndexResponse responseIndex =
                transportClient.prepareIndex("twitter", "tweet", "8")
                        .setSource(builder).get();
        System.out.println(responseIndex.status());
    }


    private void insertRiderPosition() throws IOException {
//        XContentBuilder builder = jsonBuilder()
//                .startObject()
//                .field("user", "daipengfei")
//                .field("postDate", new Date())
//                .startArray("message")
//                .startObject().field("message1", "trying out Elasticsearch1").endObject()
//                .startObject().field("message2", "trying out Elasticsearch2").endObject()
//                .endArray()
//                .endObject();
        Map<String, Object> map = new HashMap<>();
        map.put("cityId", 1);
        map.put("lastUpdateTime", new Date());
        map.put("riderId", 5446655);
        map.put("point", new GeoPoint(40.12, -71.38));
        IndexResponse responseIndex =
                transportClient.prepareIndex("rider", "rider_position")
                        .setSource(map).get();
        System.out.println(responseIndex.status());
    }

    private void localEs() throws IOException {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddresses(new InetSocketTransportAddress(
                new InetSocketAddress("127.0.0.1", 9300)));
        GetResponse response = client.prepareGet(
                "bank", "account", "136")
                .get();
        System.out.println(response);
        IndexResponse responseIndex = client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .get();
    }

    private void mongoOps() {
        org.springframework.data.mongodb.core.query.Criteria criteria =
                org.springframework.data.mongodb.core.query.Criteria.where("_id").is("ORDER10006");
        PersonDTO personDTO = helloPerson.helloPerson();
        System.out.println(personDTO.getName());
        List<Order> orders = mongoTemplate.find(
                new Query(criteria), Order.class);
        System.out.println(orders);
        mongoTemplate.save(new Order("ORDER10007", new Date(), new GeoJsonPoint(0, 1)));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(3)
                .build();
    }

    public static void main(String[] args) throws IOException {
        XContentBuilder xContentBuilder = jsonBuilder().startObject()
                .field("lat").value(40.22)
                .field("lon").value(-71.44)
                .endObject();
        System.out.println(xContentBuilder.string());
    }
}
