package hello.servlet.domain.web.frontcontroller.v3.controller;

import hello.servlet.domain.web.frontcontroller.ModelView;
import hello.servlet.domain.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    // ["member", member]
    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form"); // 물리적인 이름은 프론트 컨트롤러에서 처리할거임!
    }
}
