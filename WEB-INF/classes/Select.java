import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.List;

import tool.BddTools;

@WebServlet("/servlet/Select")
public class Select extends HttpServlet {
	
	public void service( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();
		BddTools bdd = (BddTools) ctx.getAttribute("BddTools");
	  	List<List<String>> table = bdd.getRequest("select * from " + req.getParameter("table"));
	  	
	  	RequestDispatcher dispatcher = req.getRequestDispatcher("../table.jsp");
	  	req.setAttribute("result", table);
	  	dispatcher.forward(req, res);
	}
	
}
