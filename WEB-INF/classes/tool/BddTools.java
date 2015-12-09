package tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

public class BddTools {

	public static String stringify(Collection<?> c) {
		String str = c.toString();
		return str.replaceAll("\\[", "(").replaceAll("]", ")");
	}
	
	public static String stringify(Map<?,?> m) {
		String str = m.toString();
		return str.replaceAll("\\{", "").replaceAll("\\}", "");
	}
	
	public static String stringifyWhere(Map<?,?> m) {
		return stringify(m).replaceAll(",", " AND ");
	}
	
	public static String quote(String str) {
		if (str == null || str.length() == 0) {
			return "''";
		}
		if (str.startsWith("'") && str.endsWith("'")) {
			return str;
		}
		return "'" + str + "'";
	}
	
	public static String putIn(Map<String,String> m, String key, String value) {
		return m.put(key, quote(value));
	}
	
	private Connection con;
	private String driver;
	private String url;
	private String username;
	private String password;
	
	public BddTools(String driver, String url, String username, String password, String base) {
		this.con = connecter(driver, url, username, password, base);
	}
	
	public Connection connecter(String driver, String url, String username, String password, String base) {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url + base, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Connexion échouée: " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insertInto(String table, Map<String, String> values) {
		try {
			String query = "INSERT INTO " + table + stringify(values.keySet()) + " VALUES " + stringify(values.values());
			Statement st = con.createStatement();
			return st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int update(String table, Map<String, String> backup, Map<String, String> values) {
		try {
			String query = "UPDATE " + table + " SET " + stringify(values) + " WHERE " + stringifyWhere(backup);
			Statement st = con.createStatement();
			return st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int delete(String table, Map<String, String> values) {
		try {
			String query = "DELETE FROM " + table + " WHERE " + stringifyWhere(values);
			Statement st = con.createStatement();
			return st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public List<List<String>> getRequest(String query) {
		List<List<String>> tuples = new ArrayList<List<String>>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsm = rs.getMetaData();
			
			List<String> line = null;
			line = new ArrayList<String>();
			for (int i=1; i<=rsm.getColumnCount(); i++) {
				line.add(rsm.getColumnName(i));
			}
			tuples.add(line);
			
			while (rs.next()) {
				line = new ArrayList<String>();
				for (int i=1; i<=rsm.getColumnCount(); i++) {
					line.add(rs.getString(i));
				}
				tuples.add(line);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tuples;
	}
	
	public ResultSet execute(String query) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int nbLines(String table) {
		int count = 0;
		try {
			Statement st = con.createStatement();
			String query = "select count(*) from " + table;
			ResultSet rs = st.executeQuery(query);
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Map<String, String> describe(String table) {
		Map<String, String> map = new HashMap<>();
		try {
			Statement st = con.createStatement();
			String query = "select * from " + table;
			ResultSet rs = st.executeQuery(query);
			ResultSetMetaData rsm = rs.getMetaData();
			
			int length = rsm.getColumnCount();
			for (int i=1; i<=length; i++) {
				map.put(rsm.getColumnName(i), rsm.getColumnTypeName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

}
