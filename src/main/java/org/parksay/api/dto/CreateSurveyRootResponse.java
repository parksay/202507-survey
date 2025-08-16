package org.parksay.api.dto;

import lombok.Getter;
import org.parksay.core.entity.SurveyRoot;


@Getter
public class CreateSurveyRootResponse {

    private SurveyRoot surveyRoot;



    public CreateSurveyRootResponse(SurveyRoot surveyRoot) {
        this.surveyRoot =surveyRoot;
        // JPA 에서 양방향 관계일 때 JSON 변환하면서 무한 루프 발생
        // 상위 엔티티는 컬렉션으로 하위 엔티티 참조하고, 하위 엔티티는 상위 엔티티 참조하고 있고
        // 서로 참조하고 있어서 무한 루프 발생
        // 이때 상위 엔티티의 컬렉션 필드에 `@JsonManagedReference` 를 붙여줌
        // 반대로 하위 엔티티가 참조하고 있는 상위 엔티티 필드에 `@JsonBackReference` 를 붙여줌
        //        ```
        //        public class Parent {
        //            @JsonManagedReference
        //            private List<Child> children;
        //        }
        //
        //        public class Child {
        //            @JsonBackReference
        //            private Parent parent;
        //        }
        //        ```

    }
}
