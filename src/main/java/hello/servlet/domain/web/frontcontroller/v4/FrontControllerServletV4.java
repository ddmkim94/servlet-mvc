package hello.servlet.domain.web.frontcontroller.v4;

import hello.servlet.domain.web.frontcontroller.ModelView;
import hello.servlet.domain.web.frontcontroller.MyView;
import hello.servlet.domain.web.frontcontroller.v3.ControllerV3;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    // 요청 파라미터를 담을 Map
    Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        // 컨트롤러의 데이터가 담겨질 모델 객체
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        // 반환받은 ModelView 객체로 viewPath, 데이터 처리
        MyView view = viewResolver(viewName);// 물리적 viewName
        view.render(model, request, response);
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {

        Map<String, String> paramMap = new HashMap<>();

        Iterator<String> it = request.getParameterNames().asIterator();
        while (it.hasNext()) {
            String param = it.next();
            paramMap.put(param, request.getParameter(param));
        }

        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
