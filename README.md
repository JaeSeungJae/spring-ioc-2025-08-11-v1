## #️⃣ 연관된 이슈
X
> ex) #이슈번호, #이슈번호

## 📝 작업 내용

- ApplicationContext 객체 생성
- `testPostService` 빈 생성 여부
- `testPostService` 싱글톤 검증
- `testPostRepository` 빈 생성 여부
- `testPostService` → `testPostRepository` 의존성 주입 여부
- `testFacadePostService` → `testPostService`, `testPostRepository` 의존성 주입 여부

> 이번 PR에서 작업한 내용을 간략히 설명해주세요 (이미지 첨부 가능)

### 클래스 구조
- singletons: ConcurrentHashMap<String, Object>
- providers : ConcurrentHashMap<String, Supplier<?>>
- genBean(String): <T> T         // 외부 공개, 이름으로 빈 획득
- getBean(String, Class<T>): T   // 내부 전용, 타입 세이프티 보장

### 동작 방식

제조법 등록: 생성자에서 레포지토리 → 서비스 → 파사드 순으로 providers에 등록합니다.

요청 처리: genBean("beanName") 호출 시

미등록이면 즉시 예외

등록되어 있으면 singletons.computeIfAbsent로 원자적 생성

의존성 주입: 제조법 내부에서 getBean(의존빈, 기대타입)을 통해 필요한 빈을 가져와 생성자에 주입합니다.

## 🖼️ 스크린샷 (선택)


> UI 변경 등 시각적으로 확인할 수 있는 내용이 있다면 첨부해주세요
