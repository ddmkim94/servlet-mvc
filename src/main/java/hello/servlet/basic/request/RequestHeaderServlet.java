package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printStartLine(req);
        printHeaders(req);

    }

    private void printHeaders(HttpServletRequest req) {

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + " : " + req.getHeader(headerName));
        }
    }

    private void printStartLine(HttpServletRequest req) {
        System.out.println("--- REQUEST-LINE START! ----");

        System.out.println("req.getMethod() : " + req.getMethod()); // GET, POST 등등...
        System.out.println("req.getProtocol() : " + req.getProtocol()); // HTTP/1.1
        System.out.println("req.getScheme() : " + req.getScheme()); //
        System.out.println("req.getRequestURL() : " + req.getRequestURL());
        System.out.println("req.getRequestURI() : " + req.getRequestURI());
        System.out.println("request.getQueryString() : " + req.getQueryString()); // username=?&age=?
        System.out.println("req.isSecure() : " + req.isSecure()); // https 사용 여부
        System.out.println("---- REQUEST-LINE END ----");
        System.out.println();
    }
}
