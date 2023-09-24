package controllers;

import models.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import resources.Button;
import services.UserService;


public class CommandController {

    private final static UserService service = new UserService();

    public static void start(SendMessage sender, Message message) {
        Chat chat = message.getChat();

        User user = new User();
        user.setTelegramId(chat.getId());
        user.setName(chat.getFirstName());
        user.setSurname(chat.getLastName());
        user.setUsername(chat.getUserName());

        service.store(user);

        sender.setText(String.format("Salom, %s! Bizning botga xush kelibsiz!", chat.getFirstName()));
        sender.setReplyMarkup(Button.getMainButton());
    }

    public static void about(SendMessage sender) {
        sender.setText("Salom! Bizning botga xush kelibsiz!");
    }
}
