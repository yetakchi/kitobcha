package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import models.Session;
import models.User;

import java.sql.SQLException;


public class DbManager {

    // Connector database
    private static final Connector connector = new Connector();

    // Get singleton instance
    public static Dao<User, Long> getUserDao() {

        try {
            // Data access object
            Dao<User, Long> dao = DaoManager.createDao(connector.connect(), User.class);
            return dao;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    // Get singleton instance
    public static Dao<Session, Long> getSessionDao() {

        try {
            // Data access object
            return DaoManager.createDao(connector.connect(), Session.class);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
