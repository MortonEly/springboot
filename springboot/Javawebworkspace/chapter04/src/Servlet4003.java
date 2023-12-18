import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/*
ServletConfig
    在Servlet实例化时创建。
    通过init(ServletConfig config)传递
    封装了一些配置信息：servletname等
    还可以自己配置。initParameter
*/
@WebServlet(name = "Servlet4003", value = "/Servlet4003"
, initParams = {@WebInitParam(name="aaa",value = "444"),@WebInitParam(name="bbb",value = "555")})
public class Servlet4003 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ServletConfig conf = this.getServletConfig();
        String value = conf.getInitParameter("aaa");
        Enumeration<String> names = conf.getInitParameterNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            out.println(value+";"+name);
        }
//        out.println(value+";"+names);

        ServletContext servletContext = conf.getServletContext();//获取上下文路径
    }
}
