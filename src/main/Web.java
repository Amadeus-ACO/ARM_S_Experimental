package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.user.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Web {

    public static Student getStudentData(Student student) {
        ObjectMapper mapper = App.context.getBean("objectMapper", ObjectMapper.class);
        Student dbStudent = null;
        try {
            dbStudent = App
                        .context
                        .getBean("webClient", WebClient.class)
                        .method(HttpMethod.GET)
                        .uri("/student/getStudentData")
                        .body(Mono.just(mapper.writeValueAsString(student)), String.class)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .retrieve()
                        .bodyToMono(Student.class)
                        .block();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(dbStudent);
        return dbStudent;
    }
}

