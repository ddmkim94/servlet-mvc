package hello.servlet.domain.web.frontcontroller.v5;

import hello.servlet.domain.web.frontcontroller.ModelView;
import hello.servlet.domain.web.frontcontroller.MyView;
import hello.servlet.domain.web.frontcontroller.v3.ControllerV3;
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

/**
 * 기능을 추가하더라도 메인 로직은 전혀 변경되지않음!
 * 해당 기능을 가진 컨트롤러와 어댑터를 추가하는 로직이 전부임
  */
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    // V3를 지원하는 어댑터!!
    public FrontControllerServletV5() {
        initHandlerMappingMap(); // 핸들러 저장
        initHandlerAdapters(); // 어댑터 저장
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object handler = getHandler(request); // 핸들러(컨트롤러) 찾기

        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /**
         * 핸들러 어탭터 찾기
         * - 요청 컨트롤러를 처리할 수 있는 어탭터!
         */
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // 여기에 주소와 전달 데이터가 있어야함!
        ModelView mv = adapter.handle(request, response, handler);
        String viewName = mv.getViewName(); // 논리적 이름 호출! -> new-form, save, members...

        MyView view = viewResolver(viewName); // 실제 물 리적 이름을 생성!
        view.render(mv.getModel(), request, response); // forward!!
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalStateException("handler adapter를 찾을 수 없습니다. handler : " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private void initHandlerMappingMap() {

        // V3 추가
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
        handlerAdapters.add(new ControllerV4HandlerAdapter()); // V4 어탭터 추가
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
