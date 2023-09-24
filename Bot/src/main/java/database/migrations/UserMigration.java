package database.migrations;

import com.j256.ormlite.table.TableUtils;
import database.Connector;
import models.User;

import java.sql.SQLException;


class UserMigration {

    Connector connector = new Connector();

    void up() {
        try {
            TableUtils.createTableIfNotExists(connector.connect(), User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void down() {
        try {
            TableUtils.dropTable(connector.connect(), User.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
