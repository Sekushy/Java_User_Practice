package Database;

import java.sql.*;

public class DbConnection {
    //String connectionString = "jdbc:sqlite:/C:\\Users\\Andrei\\Desktop\\SQLite\\users.db";
    private Connection connection;

    public void establishDatabaseConnection(String connectionString) {
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
