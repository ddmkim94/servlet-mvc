package hello.servlet.domain.web.frontcontroller.v5.adapter;

import hello.servlet.domain.web.frontcontroller.ModelView;
import hello.servlet.domain.web.frontcontroller.v3.ControllerV3;
import hello.servlet.domain.web.frontcontroller.v4.ControllerV4;
import hello.servlet.domain.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// ControllerV4를 지원하는 어댑터! -> paramMap, Model
public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    /**
     * 어댑터는 실제 컨트롤러를 호출하고, ModelView 반환해야한다.
     * String을 반환하는 V4를 ModelView로 감싸서 반환해야한다.
     */
    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler
                ;
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
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