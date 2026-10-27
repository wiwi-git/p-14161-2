package com.ll.domain.testPost.testPost.repository;

import com.ll.framework.ioc.annotations.Repository;

@Repository
public class TestPostRepository {
    private static TestPostRepository instacane;

    private TestPostRepository(){}



//    public static TestPostRepository getInstance() {
//        if (instacane == null) {
//            instacane = new TestPostRepository();
//        }
//
//        return instacane;
//    }
}
