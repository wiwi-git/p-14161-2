package com.ll.domain.testPost.testPost.service;
//      com.ll.domain.testPost.testPost.serivce.TestPostService
import com.ll.domain.testPost.testPost.repository.TestPostRepository;
import com.ll.framework.ioc.annotations.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestPostService {
    private final TestPostRepository testPostRepository;
    private static TestPostService instance;

    public static TestPostService getInstance() {
        if (instance == null) {
            instance = new TestPostService(TestPostRepository.getInstance());
        }
        return instance;
    }
}
