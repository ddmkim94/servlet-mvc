package hello.servlet.domain.web.frontcontroller.v5.adapter;

import hello.servlet.domain.web.frontcontroller.ModelView;
import hello.servlet.domain.web.frontcontroller.v4.ControllerV4;
import hello.servlet.domain.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV4 controller = (ControllerV4) handler;

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        /**
         * model의 데이터를 ModelView에 넣어서 전달
         * 해당 코드가 어댑터의 역할을 하는 코드: 맞지 않는 규격을 맞춰주기 때문에
          */
        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        // ** 실제 컨트롤러가 ModelView를 반환하지 못하면, 어댑터가 ModelView를 만들어서 리턴!
        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
