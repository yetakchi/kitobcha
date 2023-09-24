package database.migrations;

import com.j256.ormlite.table.TableUtils;
import database.Connector;
import models.Session;

import java.sql.SQLException;


class SessionMigration {

    Connector connector = new Connector();

    void up() {
        try {
            TableUtils.createTableIfNotExists(connector.connect(), Session.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void down() {
        try {
            TableUtils.dropTable(connector.connect(), Session.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
