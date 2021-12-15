package hello.servlet.domain.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * ModelView: 객체와 뷰 이름을 함께 전달하는 클래스
 */
@Getter @Setter
public class ModelView {

    private String viewName;
    private Map<String, Object> model = new HashMap<>(); // [이름, 객체]

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
