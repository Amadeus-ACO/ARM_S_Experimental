package main;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import entity.CamelCaseNamingStrategy;
import entity.Entity;
import javafx.application.Application;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;

@Slf4j
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class Main {
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    @Bean
    public static @NotNull ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        Reflections reflections = new Reflections(Entity.class.getPackageName());

        Set<Class<? extends Entity>> subClasses = reflections.getSubTypesOf(Entity.class);
        subClasses.forEach(aClass -> objectMapper.registerSubtypes(new NamedType(aClass, aClass.getSimpleName())));
        objectMapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        // TODO: По возможности избавиться от этого
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        return objectMapper;
    }

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    @DependsOn("objectMapper")
    public WebClient webClient() {
        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer
                            .defaultCodecs()
                            .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer
                            .defaultCodecs()
                            .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer
                            .defaultCodecs()
                            .maxInMemorySize((int) Math.pow(2, 100));
                })
                .build();

        return WebClient.builder()
                .baseUrl("http://194.190.152.231:8080")
                .exchangeStrategies(strategies)
                .build();
    }
}
