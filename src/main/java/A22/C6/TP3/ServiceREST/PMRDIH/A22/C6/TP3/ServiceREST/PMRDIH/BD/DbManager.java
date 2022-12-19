package A22.C6.TP3.ServiceREST.PMRDIH.A22.C6.TP3.ServiceREST.PMRDIH.BD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://172.20.45.92:3306/TP3";
    static final String USER = "admin";
    static final String PASS = "admin";

    public List<String> fetchRoute(){
        List<String> routeOptimale = new ArrayList<>();
        String str = "";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connexion à la bd...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Création de la requête...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM route_optimale";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String route = rs.getString("addresse");
                System.out.print(", route: " + route);
                routeOptimale.add(route);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("problème de connexion");
        } catch (Exception e) {
            System.out.println("problème de connexion");
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                System.out.println("problème de connexion");
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                System.out.println("problème de connexion");
            }
        }
        System.out.println("Connexion avec la bd ferme");
        return routeOptimale;
    }

    public void AjouterALABD(String addresse){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            // Execute a query
            System.out.println("Inserting records into the table...");
            String sql = "INSERT INTO route_optimale(addresse) VALUES (?)";

            try(PreparedStatement valeurAInserer = conn.prepareStatement(sql)) {
                valeurAInserer.setString(1, addresse);
                valeurAInserer.execute();
            }
            stmt.execute(sql);

            System.out.println("Inserted records into the table...");
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

}
