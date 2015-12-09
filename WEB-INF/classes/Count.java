import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tool.BddTools;

@WebServlet("/servlet/Count")
public class Count extends HttpServlet {
	
	public void service( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();
		BddTools bdd = (BddTools) ctx.getAttribute("BddTools");
	  	ResultSet rs = bdd.execute("select count(*) as cpt from " + req.getParameter("table"));
	  	
	  	PrintWriter out = res.getWriter();
	  	try {
		  	if (rs.next()) {
		  		out.print(rs.getString("cpt"));
		  	}
	  	} catch (SQLException e) {
	  		out.println(e);
	  	}
	}
	
}
