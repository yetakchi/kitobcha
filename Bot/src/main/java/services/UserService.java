package services;

import com.j256.ormlite.dao.Dao;
import database.DbManager;
import models.Session;
import models.User;

import java.sql.SQLException;


public class UserService {

    public void store(User dto) {
        Dao<User, Long> dao = DbManager.getUserDao();

        try {
            User user = dao.queryBuilder()
                    .where()
                    .eq("telegram_id", dto.getTelegramId())
                    .queryForFirst();

            if (user == null) {
                user = new User();
                user.setName(dto.getName());
                user.setSurname(dto.getSurname());
                user.setUsername(dto.getUsername());
                user.setTelegramId(dto.getTelegramId());

                dao.create(user);

                // State
                Session session = new Session();
                session.setUser(user);
                DbManager.getSessionDao().create(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findByTelegramId(long id) {
        try {
            return DbManager.getUserDao().queryBuilder()
                    .where()
                    .eq("telegram_id", id)
                    .queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
