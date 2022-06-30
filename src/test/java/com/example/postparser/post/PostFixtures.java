package com.example.postparser.post;

import com.example.postparser.post.result.*;


import java.io.File;

import static com.example.postparser.post.result.PostSaveStatus.*;

public class PostFixtures {
    public static final Post POST0 = new Post(2, 12, "in quibusdam tempore odit est dolorem", "itaque id aut magnam praesentium quia et ea odit");
    public static final Post POST1 = new Post(3, 13, "testTitle", "testBody");
    public static final Post POST2 = new Post(1, 1, "title1", "body1");


    public static final Result THROWN_BY_EXCEPTION_RESULT = new FailureResult(POST1, PostSaveStatus.FAILURE);
    public static final Result SUCCESS_RESULT0 = new SuccessResult(POST0, PostSaveStatus.SUCCESS);
    public static final Result SUCCESS_RESULT1 = new SuccessResult(POST1, PostSaveStatus.SUCCESS);
    public static final Result FAILURE_RESULT2 = new InternalErrorResult("java.util.concurrent.ExecutionException: Test Execution Exception", FAILED_SAVE_TASK);

    public static final File FILE_0 = new File("/home/test/tmp/directory/12.json");
    public static final File FILE_1 = new File("/home/test/tmp/directory/13.json");
}
