package hello.servlet.domain.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {

    // model에 값을 넣어서 사용해!
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
