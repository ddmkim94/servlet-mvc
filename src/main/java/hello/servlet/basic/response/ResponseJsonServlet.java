package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // json타입은 스펙상 utf-8을 사용하도록 정의되어 있기 때문에 charset 파라미터를 쓰지 않아도 ㄱㅊ!
        // 오히려 의미없는 파라미터를 하나 추가하는 꼴이다~
        resp.setContentType("application/json");

        HelloData data = new HelloData("동민", 29);

        String result = objectMapper.writeValueAsString(data);
        resp.getWriter().write(result);
    }
}
