import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.List;

import tool.BddTools;

@WebServlet("/servlet/RechercherPersonne")
public class RechercherPersonne extends HttpServlet {
	
	public void service( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();
		BddTools bdd = (BddTools) ctx.getAttribute("BddTools");
		String nom = req.getParameter("nom");
	  	ResultSet rs = bdd.execute("select * from personne where UPPER(nom) like '" + nom + "%'");
	  	
	  	PrintWriter out = res.getWriter();
	  	StringBuilder builder = new StringBuilder();
	  	try {
		  	while (rs.next()) {
		  		builder.append(rs.getString("nom"));
		  		builder.append(";");
		  	}
	  	} catch (SQLException e) {
	  		out.println(e);
	  	}
	  	res.setContentType("text/csv");
	  	out.println(builder.toString());
	}
	
}
