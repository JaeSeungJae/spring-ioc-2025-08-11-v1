package com.ll.domain.testPost.testPost.service;

import com.ll.domain.testPost.testPost.repository.TestPostRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestFacadePostService {
    private final TestPostService testPostService;
    private final TestPostRepository testPostRepository;
//    public TestFacadePostService(TestPostService testPostService,
//                                 TestPostRepository testPostRepository) {
//        this.testPostService = testPostService;
//        this.testPostRepository = testPostRepository;
//    }

    // 선택: 디버깅용 게터
    public TestPostService getTestPostService() {
        return testPostService;
    }
    public TestPostRepository getTestPostRepository() {
        return testPostRepository;
    }
}
