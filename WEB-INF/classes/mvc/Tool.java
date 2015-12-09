package mvc;

import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import tool.*;

public class Tool {

	public Tool() {}

	private boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}	
	
	public List<Personne> rechPersonnes(String id, String nom, String prenom, String sexe, String tel, String fonction, String colonneATrier, String ordreDeTrie) {
		Path path = Paths.get("config.properties");
		Properties prop = new Properties();
		try {
			prop.load(Files.newBufferedReader(path, Charset.forName("UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String driver = prop.getProperty("db.driver");
		String url = prop.getProperty("db.url");
		String base = prop.getProperty("db.base");
		String username = prop.getProperty("db.username");
		String password = prop.getProperty("db.password");
		BddTools bddTools = new BddTools(driver, url, username, password, base);
		List<Personne> personnes = new ArrayList<Personne>();
		try {
			String query = "SELECT * FROM annuaire WHERE 1 = 1";
			int length = query.length();
			if (!isEmpty(id)) {
				query += " AND CAST(num AS TEXT) LIKE " + BddTools.quote(id + "%");
			}
			if (!isEmpty(nom)) {
				query += " AND nom LIKE " + BddTools.quote(nom + "%");
			}
			if (!isEmpty(prenom)) {
				query += " AND prenom LIKE " + BddTools.quote(prenom + "%");
			}
			if (!isEmpty(sexe)) {
				query += " AND sexe LIKE " + BddTools.quote(sexe + "%");
			}
			if (!isEmpty(tel)) {
				query += " AND tel LIKE " + BddTools.quote(tel + "%");
			}
			if (!isEmpty(fonction)) {
				query += " AND fonction LIKE " + BddTools.quote(fonction + "%");
			}
			if (!isEmpty(colonneATrier)) {
				query += " ORDER BY " + colonneATrier;
				if (!isEmpty(ordreDeTrie)) {
					query += " " + ordreDeTrie;
				}
			}
			System.out.println(query);
			// On s'assure que la requête a été concaténée
			if (query.length() > length) {
				ResultSet rs = bddTools.execute(query);
				System.out.println(query);
				while (rs.next()) {
					Personne p = new Personne();
					p.setId(rs.getInt("num"));
					p.setNom(rs.getString("nom"));
					p.setPrenom(rs.getString("prenom"));
					p.setSexe(rs.getString("sexe"));
					p.setTel(rs.getString("tel"));
					p.setFonction(rs.getString("fonction"));
					personnes.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bddTools.close();
		return personnes;
	}

}
