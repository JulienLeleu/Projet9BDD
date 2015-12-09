import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.List;

import tool.BddTools;

@WebServlet("/servlet/SelectPersonne")
public class SelectPersonne extends HttpServlet {
	
	public void service( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();
		BddTools bdd = (BddTools) ctx.getAttribute("BddTools");
		String id = req.getParameter("id");
	  	ResultSet rs = bdd.execute("select * from personne where id =" + id);
	  	
	  	PrintWriter out = res.getWriter();
	  	StringBuilder builder = new StringBuilder();
	  	builder.append("<?xml version='1.0' encoding='UTF-8'?>\n");
	  	try {
		  	if (rs.next()) {
		  		builder.append("<personne>");
		  		builder.append("<id>" + rs.getString("id") + "</id>");
		  		builder.append("<login>" + rs.getString("login") + "</login>");
		  		builder.append("<nom>" + rs.getString("nom") + "</nom>");
		  		builder.append("<prenom>" + rs.getString("prenom") + "</prenom>");
		  		builder.append("<datenaiss>" + rs.getString("datenaiss") + "</datenaiss>");
		  		builder.append("</personne>");
		  	}
	  	} catch (SQLException e) {
	  		out.println(e);
	  	}
	  	res.setContentType("text/xml");
	  	out.println(builder.toString());
	}
	
}
