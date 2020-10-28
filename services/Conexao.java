package javafx.mvc.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Nayara
 */
public class Conexao {

    private Connection conn;
    private static Conexao instance;

    private Conexao() {
        this.conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "devel");
        connectionProps.put("password", "developer");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/os-desen",
                    connectionProps);
            System.out.println("Connection is created!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro:"+e.getMessage());
        }
    }

    public static Conexao getInstance() {
        if (instance == null) {
            instance = new Conexao();
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }

}
