package services;

import database.DbManager;
import entities.Currency;
import models.Session;

import java.sql.SQLException;


public class SessionService {

    public Session findByUserId(long userId) {
        try {
            return DbManager.getSessionDao().queryBuilder()
                    .where()
                    .eq("user_id", userId)
                    .queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateState(Session session, short status) {
        session.setStatus(status);
        update(session);
    }

    public void clearData(Session session) {
        session.setData(null);
        update(session);
    }

    public void updateData(Session session, String callData, Currency[] selectedCurrencies) {
        String[] values = callData.split("="); // => key, value

        Currency original = selectedCurrencies[0];
        Currency target = selectedCurrencies[1];

        // Check for detecting type of button and set data
        if (values[0].equals("original")) {
            original = Currency.valueOf(values[1]);
        } else {
            target = Currency.valueOf(values[1]);
        }

        session.setData(String.format("original=%s;target=%s", original, target));

        update(session);
    }

    public void update(Session session) {
        try {
            DbManager.getSessionDao().update(session);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
