import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "Servlet4004", value = "/Servlet4004")
public class Servlet4004 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ServletContext cont = this.getServletContext();
        Enumeration<String> names = cont.getInitParameterNames();
        while(names.hasMoreElements()){
            String name = names.nextElement();
            String value = cont.getInitParameter(name);
            out.println(name+":"+value);
        }
    }
}
