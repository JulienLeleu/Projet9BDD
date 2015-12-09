import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.List;

import tool.BddTools;

@WebServlet("/servlet/SelectMinMaxPersonne")
public class SelectMinMaxPersonne extends HttpServlet {
	
	public void service( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();
		BddTools bdd = (BddTools) ctx.getAttribute("BddTools");
		String id = req.getParameter("id");
	  	ResultSet rsMin = bdd.execute("select * from personne order by datenaiss asc limit 3");
	  	ResultSet rsMax = bdd.execute("select * from personne order by datenaiss desc limit 3");
	  	
	  	PrintWriter out = res.getWriter();
	  	StringBuilder builder = new StringBuilder();
	  	try {
	  		builder.append("{");
	  		builder.append("\"jeunes\" : [");
		  	while (rsMin.next()) {
		  		buildPersonne(builder, rsMin);
		  		if (!rsMin.isLast()) {
		  			builder.append(",");
		  		}
		  	}
		  	builder.append("], ");
		  	builder.append("\"vieux\" : [");
		  	while (rsMax.next()) {
		  		buildPersonne(builder, rsMax);
		  		if (!rsMax.isLast()) {
		  			builder.append(",");
		  		}
		  	}
		  	builder.append("]");
		  	builder.append("}");
	  	} catch (SQLException e) {
	  		out.println(e);
	  	}
	  	res.setContentType("application/json");
	  	out.println(builder.toString());
	}
	
	private void buildPersonne(StringBuilder builder, ResultSet rs) throws SQLException {
		builder.append("{");
  		builder.append("\"id\" : \"" + rs.getString("id") + "\",");
  		builder.append("\"login\" :\"" + rs.getString("login") + "\",");
  		builder.append("\"nom\" :\"" + rs.getString("nom") + "\",");
  		builder.append("\"prenom\" :\"" + rs.getString("prenom") + "\",");
  		builder.append("\"datenaiss\" :\"" + rs.getString("datenaiss") + "\"");
  		builder.append("}");
	}
	
}
