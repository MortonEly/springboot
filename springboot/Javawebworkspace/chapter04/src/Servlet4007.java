import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

@WebServlet(name = "Servlet4007", value = "/Servlet4007")
public class Servlet4007 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ServletContext cont = this.getServletContext();
        Set<String> paths = cont.getResourcePaths("/WEB-INF");
        String str = String.join(", ", paths);
        out.println(str);//   /index.jsp, /WEB-INF/

        String indexPath = cont.getRealPath("/index.jsp");
        out.println(indexPath);// "E:\\work\\javaworkspace\\chapter04\\out\\artifacts\\chapter04_war_exploded\\index.jsp"

        URL resource = cont.getResource("/index.jsp");
        out.println(resource.toString()); // file:/E:/work/javaworkspace/chapter04/out/artifacts/chapter04_war_exploded/index.jsp
        out.println(resource.getPath());// /E:/work/javaworkspace/chapter04/out/artifacts/chapter04_war_exploded/index.jsp
        out.println(resource.getFile());// /E:/work/javaworkspace/chapter04/out/artifacts/chapter04_war_exploded/index.jsp

        InputStream stream = cont.getResourceAsStream("/WEB-INF/classes/com/edu/a.properties");
        Properties pro = new Properties();
        pro.load(stream);
        Enumeration<String> names = (Enumeration<String>) pro.propertyNames();
        while (names.hasMoreElements()){
            String s = names.nextElement();
            String property = pro.getProperty(s);
            out.println(s+"="+property);
        }

    }
}
