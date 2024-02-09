package net.learning.springreactivemongocurdpoc.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * Config file for all the configuration code
 */
@Configuration
@RequiredArgsConstructor
public class Config {

    private final ApplicationContext applicationContext;

    /**
     * To remove _class field from payload being saved in DB.
     */
    @PostConstruct
    public void init() {
        MappingMongoConverter mappingMongoConverter =
                applicationContext.getBean(MappingMongoConverter.class);
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}