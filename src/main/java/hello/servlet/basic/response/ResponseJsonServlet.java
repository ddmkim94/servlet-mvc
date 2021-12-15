package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // application/json은 스펙상 utf-8을 지원
        response.setHeader("content-type", "application/json");
        response.setCharacterEncoding("utf-8");

        HelloData data = new HelloData();
        data.setUsername("은빈");
        data.setAge(31);

        // Jackson's lib = writeValueAsString: 객체 -> JSON 문자로 변경해줌
        String result = objectMapper.writeValueAsString(data);
        // response.getWriter().write(result);

        ServletOutputStream outputStream = response.getOutputStream();
        PrintStream out = new PrintStream(outputStream, true);
        out.println(result);
    }
}
