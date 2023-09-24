package database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import config.Data;

import java.sql.SQLException;


public class Connector {

    String databaseUrl = String.format("jdbc:sqlite:%s", Data.dbname);

    // create a connection source to our database
    public ConnectionSource connect() {
        try {
            return new JdbcConnectionSource(databaseUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
