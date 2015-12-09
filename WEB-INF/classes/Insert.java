import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import tool.BddTools;

@WebServlet("/servlet/Insert")
public class Insert extends HttpServlet {
	
	public void service( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletContext ctx = req.getServletContext();
		BddTools bdd = (BddTools) ctx.getAttribute("BddTools");
		String table = req.getParameter("table");
		Map<String, String> descriptor = bdd.describe(table);
		Map<String, String> values = new HashMap<>();
		
		String info;
		String value;
		for (String column : descriptor.keySet()) {
			value = req.getParameter(column);
			if (value != null && value.length() > 0) {
				values.put(column, "'" + value + "'");
			}
		}
		
		int result = bdd.insertInto(table, values);
		if (result == -1) {
			info = "Une erreur s'est produite lors de l'insertion dans la table " + table;
		}
		else {
			info = "Insertion réussie dans la table " + table;
		}
				
		req.setAttribute("info", info);
		res.sendRedirect("Select?table=" + table);
	}
	
}
