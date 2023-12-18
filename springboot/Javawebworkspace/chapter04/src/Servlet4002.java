import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Servlet4002", value = "/Servlet4002")
public class Servlet4002 extends GenericServlet {
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init start!");
    }
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("hello servlet!");
    }

    public void destroy() {
        System.out.println("I'm destroy!");
    }
}
