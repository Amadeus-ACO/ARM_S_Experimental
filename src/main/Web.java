package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Section;
import entity.user.Student;
import entity.work.GivenTask;
import exceptions.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class Web {

    private static MultiValueMap<String, ResponseCookie> cookie;

    private static WebClient webClient;

    public static Object getSectionTree(Section.TYPE sectionType) {
        return webClient
                .method(HttpMethod.GET)
                .uri("/student/getSectionTree?type=" + sectionType)
                .cookies(cookiesConsumer)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Section.class))
                .block();
    }

    public static GivenTask getGivenTaskDescription(GivenTask givenTask) {
        return webClient
                .method(HttpMethod.GET)
                .uri("/student/getGivenTaskDescription")
                .cookies(cookiesConsumer)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(givenTask))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(GivenTask.class))
                .block();
    }

    @Autowired
    public void setWebClient(WebClient webClient) {
        Web.webClient = webClient;
    }

    private static Consumer<MultiValueMap<String, String>> cookiesConsumer = cookiesMap -> {
        cookie.forEach((a, b) -> cookiesMap.addAll(a, b
                .stream()
                .map(HttpCookie::getValue)
                .collect(Collectors.toList())));
    };

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
        //ObjectMapper mapper = App.context.getBean("objectMapper", ObjectMapper.class);
        Student dbStudent = App
                .context
                .getBean("webClient", WebClient.class)
                .method(HttpMethod.GET)
                .uri("/student/login")
                .headers((httpHeaders) -> {httpHeaders.setBasicAuth(login, password);})
                .exchangeToMono((param) -> {
                    cookie = param.cookies();
                    if (param.statusCode() == HttpStatus.OK)
                        return param.bodyToMono(Student.class);
                    return Mono.error(new NotAuthorizedException(param.statusCode()));
                })
                .block();

        System.out.println(dbStudent);
        return dbStudent;
    }

    public static List<GivenTask> getGivenTaskList(Student student) {
        return webClient
                .method(HttpMethod.GET)
                .uri("/student/getGivenTaskList")
                .cookies(cookiesConsumer)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(BodyInserters.fromValue(student))
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.OK)
                        return clientResponse.bodyToMono(new ParameterizedTypeReference<List<GivenTask>>() {});
                    else if (clientResponse.statusCode() == HttpStatus.NOT_FOUND)
                        return Mono.empty();
                    else
                        return Mono.error(new Exception("Возникла проблема при получении списка заданий; " +
                                clientResponse.statusCode()));
                }).block();

                /*.exchangeToMono((param) -> {
                    cookie = param.cookies();
                    if (param.statusCode() == HttpStatus.UNAUTHORIZED)
                        return Mono.error(new NotAuthorizedException());
                    return param.bodyToMono(Student.class);
                })
                .block();

        System.out.println(dbStudent);
        return dbStudent;*/
    }


}

