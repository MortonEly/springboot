import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "Servlet4006", value = "/Servlet4006")
public class Servlet4006 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ServletContext cont = this.getServletContext();
        Enumeration<String> attributeNames = cont.getAttributeNames();
        while(attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement();

            Object att =  cont.getAttribute(name);

            out.println(name+":"+att);
        }

        cont.removeAttribute("fff");

        Enumeration<String> attributeNames2 = cont.getAttributeNames();
        while(attributeNames2.hasMoreElements()){
            String name = attributeNames2.nextElement();

            Object att =  cont.getAttribute(name);
            out.println(name+":"+att);
        }



    }
}
