package hello.servlet.domain.web.frontcontroller.v2;

import hello.servlet.domain.web.frontcontroller.MyView;
import hello.servlet.domain.web.frontcontroller.v1.ControllerV1;
import hello.servlet.domain.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.domain.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.domain.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import hello.servlet.domain.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.domain.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.domain.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    Map<String, ControllerV2> controllerMap = new HashMap<>();

    // 생성자로 초기화
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV2 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 1. view 정보를 가진 MyView 객체를 반환
        MyView view = controller.process(request, response);

        // 2. render()를 통해 forward!!
        view.render(request, response);
    }
}
