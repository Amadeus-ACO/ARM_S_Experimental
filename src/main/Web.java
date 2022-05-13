package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.user.Student;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public class Web {

    private static MultiValueMap<String, ResponseCookie> cookie;

    public static Student getStudentData(Student student) {
        ObjectMapper mapper = App.context.getBean("objectMapper", ObjectMapper.class);
        Student dbStudent = null;
        try {
            dbStudent = App
                        .context
                        .getBean("webClient", WebClient.class)
                        .method(HttpMethod.GET)
                        .uri("/student/getStudentData")
                        .cookies(bye -> {
                            cookie.forEach((a,b) -> {
                                bye.addAll(a,b
                                        .stream()
                                        .map(HttpCookie::getValue)
                                        .collect(Collectors.toList()));
                            });
                        })
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


    public static Student Auth(String login, String password) {
        ObjectMapper mapper = App.context.getBean("objectMapper", ObjectMapper.class);
        Student dbStudent = null;
        dbStudent = App
                .context
                .getBean("webClient", WebClient.class)
                .method(HttpMethod.GET)
                .uri("/auth/login")
                .headers((httpHeaders) -> {httpHeaders.setBasicAuth(login, password);})
                .exchangeToMono((param) -> {cookie = param.cookies(); return param.bodyToMono(Student.class);})
                .block();

        System.out.println(dbStudent);
        return dbStudent;
    }

}

