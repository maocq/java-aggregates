package co.com.bancolombia.mongo.config;

//import co.com.bancolombia.model.order.Product;
//import com.mongodb.reactivestreams.client.MongoClient;
import co.com.bancolombia.mongo.config.converters.ZonedDateTimeReadConverter;
import co.com.bancolombia.mongo.config.converters.ZonedDateTimeWriteConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoConnectionDetails;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.PropertiesMongoConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;
//import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                List.of(new ZonedDateTimeReadConverter(), new ZonedDateTimeWriteConverter()));
    }

    @Bean
    public MongoDBSecret dbSecret(@Value("${spring.data.mongodb.uri}") String uri) {
        return MongoDBSecret.builder()
                .uri(uri)
                .build();
    }

    @Bean
    public MongoConnectionDetails mongoProperties(MongoDBSecret secret) {
        MongoProperties properties = new MongoProperties();
        properties.setUri(secret.getUri());
        return new PropertiesMongoConnectionDetails(properties);
    }

    /*
    @Bean
    public String mongoTemplate(MongoClient mongoClient) {
        //MongoClient mongoClient = MongoClients
        //        .create(MongoClientSettings.builder()
        //                .applyConnectionString(new ConnectionString(uri))
        //                .build());

        ReactiveMongoTemplate template = new ReactiveMongoTemplate(mongoClient, "test");
        Query query = Query.query(Criteria.where("name").is("Product"));

        var product = template.findOne(query, Product.class).block();
        return "Ok";
    }
    */
}
