package com.ll.framework.ioc;

import com.ll.domain.testPost.testPost.repository.TestPostRepository;
import com.ll.domain.testPost.testPost.service.TestFacadePostService;
import com.ll.domain.testPost.testPost.service.TestPostService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ApplicationContext {

    // 싱글톤 저장소
    private final Map<String, Object> singletons = new ConcurrentHashMap<>();
    // 빈 공급자 레지스트리
    private final Map<String, Supplier<?>> providers = new ConcurrentHashMap<>();

    public ApplicationContext() {
        // 1) 리포지토리
        providers.put("testPostRepository", TestPostRepository::new);

        // 2) 서비스
        providers.put("testPostService", () ->
                new TestPostService(getBean("testPostRepository", TestPostRepository.class)));

        // 3) 퍼사드 서비스
        providers.put("testFacadePostService", () ->
                new TestFacadePostService(
                        getBean("testPostService", TestPostService.class),
                        getBean("testPostRepository", TestPostRepository.class)
                ));
    }

    @SuppressWarnings("unchecked")
    public <T> T genBean(String beanName) {
        // 미등록이면 즉시 명확한 예외
        Supplier<?> supplier = providers.get(beanName);
        if (supplier == null) {
            throw new IllegalArgumentException("등록되지 않은 빈 이름입니다: " + beanName);
        }
        // 싱글톤 보장
        Object instance = singletons.computeIfAbsent(beanName, k -> {
            Object obj = supplier.get();
            if (obj == null) {
                throw new IllegalStateException("빈 공급자가 null 을 반환했습니다: " + beanName);
            }
            return obj;
        });
        return (T) instance;
    }

    // 타입 세이프 헬퍼 (레지스트리 내부에서 사용)
    private <T> T getBean(String name, Class<T> type) {
        Object o = genBean(name);
        if (!type.isInstance(o)) {
            throw new IllegalStateException("빈 타입 불일치: " + name + " -> " + o.getClass().getName()
                    + ", 기대 타입: " + type.getName());
        }
        return type.cast(o);
    }
}
