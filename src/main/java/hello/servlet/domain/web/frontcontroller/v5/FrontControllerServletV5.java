package hello.servlet.domain.web.frontcontroller.v5;

import hello.servlet.domain.web.frontcontroller.ModelView;
import hello.servlet.domain.web.frontcontroller.MyView;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.domain.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.domain.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.domain.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.domain.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap(); // V3 Controller 저장 [Form, Save, List]
        initHandlerAdapters(); // 핸들러 어댑터 저장 (여기선 V3 전용 어댑터만 저장)
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private void initHandlerMappingMap() {
        // V3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());

    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter()); // V4 추가
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // ControllerV3HandlerAdapter!
        // ControllerV4HandlerAdapter!
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(request, response, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter 찾을 수 없음! handler = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }
}