package hello.servlet.domain.web.frontcontroller.v3;

import hello.servlet.domain.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    // V3: request, response 객체 삭제!! -> 서블릿 종속 X
    ModelView process(Map<String, String> paramMap);
}