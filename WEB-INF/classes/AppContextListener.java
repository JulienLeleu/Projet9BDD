import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import tool.BddTools;

@WebListener
public class AppContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent e) {
		ServletContext ctx = e.getServletContext();
		
		String driver = ctx.getInitParameter("db.driver");
		String url = ctx.getInitParameter("db.url");
		String base = ctx.getInitParameter("db.base");
		String username = ctx.getInitParameter("db.username");
		String password = ctx.getInitParameter("db.password");
		
		BddTools bddTools = new BddTools(driver, url, username, password, base);
		ctx.setAttribute("BddTools", bddTools);
		System.out.println("Database connection initialized");
	}
	
	public void contextDestroyed(ServletContextEvent e) {
		ServletContext ctx = e.getServletContext();
		BddTools bddTools = (BddTools) ctx.getAttribute("BddTools");
		bddTools.close();
		System.out.println("Database connection closed");
	}
}
