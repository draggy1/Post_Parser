package com.example.postparser.post;

import com.example.postparser.post.result.FailureResult;
import com.example.postparser.post.result.PostSaveStatus;
import com.example.postparser.post.result.SuccessResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;

public class PostFixtures {

    public static final Post POST0 = new Post(2, 12, "in quibusdam tempore odit est dolorem", "itaque id aut magnam praesentium quia et ea odit");
    public static final Post POST1 = new Post(3, 13, "testTitle", "testBody");
    public static final Post POST2 = new Post(1, 1, "title1", "body1");
    public static final Post POST3 = new Post(2, 2, "title2", "body2");
    public static final Post POST4 = new Post(3, 3, "title3", "body3");


    public static final SuccessResult SUCCESS_RESULT0 = new SuccessResult(POST0, PostSaveStatus.SUCCESS);
    public static final SuccessResult SUCCESS_RESULT1 = new SuccessResult(POST1, PostSaveStatus.SUCCESS);
    public static final SuccessResult SUCCESS_RESULT2 = new SuccessResult(POST2, PostSaveStatus.SUCCESS);
    public static final SuccessResult SUCCESS_RESULT3 = new SuccessResult(POST3, PostSaveStatus.SUCCESS);
    public static final FailureResult FAILURE_RESULT1 = new FailureResult(POST4, PostSaveStatus.FAILURE);

    public static final File FILE_0 = new File("/home/test/tmp/directory/12.json");
    public static final File FILE_1 = new File("/home/test/tmp/directory/13.json");



    static WebClient prepareWebClientMock(){
        String givenJson = """
                [{
                    "userId":2,
                    "id":12,
                    "title":"in quibusdam tempore odit est dolorem",
                    "body":"itaque id aut magnam praesentium quia et ea odit"
                },
                {
                    "userId":3,
                    "id":13,
                    "title":"testTitle",
                    "body":"testBody"
                }
                ]
                """;


        return WebClient.builder()
                .exchangeFunction(clientRequest ->
                        Mono.just(ClientResponse.create(HttpStatus.OK)
                                .header("content-type", "application/json")
                                .body(givenJson)
                                .build())
                ).build();
    }
}
