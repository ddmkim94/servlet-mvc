package hello.servlet.domain.web.frontcontroller.v5.adapter;

import hello.servlet.domain.web.frontcontroller.ModelView;
import hello.servlet.domain.web.frontcontroller.v3.ControllerV3;
import hello.servlet.domain.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// ControllerV3를 지원하는 어댑터!
public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    /**
     * 어댑터는 실제 컨트롤러를 호출하고, ModelView를 반환해야한다.
     */
    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;
        Map<String, String> paramMap = createParamMap(request);
        return controller.process(paramMap);
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
}
