package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.user.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Web {

    private static MultiValueMap<String, ResponseCookie> pechenka;

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


    public static Student Auth(String login, String password) {
        ObjectMapper mapper = App.context.getBean("objectMapper", ObjectMapper.class);
        Student dbStudent = null;
        dbStudent = App
                .context
                .getBean("webClient", WebClient.class)
                .method(HttpMethod.GET)
                .uri("/auth/login")
                .headers((httpHeaders) -> {httpHeaders.setBasicAuth(login, password);})
                .exchangeToMono((param) -> {pechenka = param.cookies(); return param.bodyToMono(Student.class);})
                .block();

        System.out.println(dbStudent);
        return dbStudent;
    }

}

