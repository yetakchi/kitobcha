import controllers.AuthController;
import controllers.CallbackController;
import controllers.CommandController;
import controllers.MessageController;
import models.User;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;


public class MessageHandler {

    private final AuthController loginController = new AuthController();

    private final MessageController messageController = new MessageController();

    private final CallbackController callbackController = new CallbackController();


    public SendMessage commandHandler(Message message, SendMessage sender) {
        String command = message.getEntities().get(0).getText();
        sender.setText(command);

        switch (command) {
            case "/start":
                CommandController.start(sender, message);
                break;
            case "/haqimizda":
                CommandController.about(sender);
                break;
        }

        return sender;
    }

    public SendMessage textMessageHandler(Message message, SendMessage sender) {
        String text = message.getText();

        // Find user
        long id = message.getChat().getId();
        User user = loginController.login(id);
        if (user == null) {
            sender.setText("Tizimga kirish uchun /start ni bosing");
            return sender;
        }

        messageController.setUser(user);

        // Detect necessary command
        switch (text) {
            case "Kitobcha":
                sender.setText(messageController.booklet());
                return sender;
            case "Valyutalar":
                messageController.currencies(sender);
                return sender;
            case "Bot haqida":
                sender.setText(messageController.about());
                return sender;
            default:
                break;
        }

        String result = messageController.action(text);
        sender.setText(result);

        return sender;
    }

    public EditMessageReplyMarkup callbackHandler(CallbackQuery query, Message message) {
        EditMessageReplyMarkup markup = new EditMessageReplyMarkup();
        markup.setChatId(message.getChatId().toString());
        markup.setMessageId(message.getMessageId());

        AnswerCallbackQuery queryAnswer = new AnswerCallbackQuery();
        queryAnswer.setShowAlert(false);
        queryAnswer.setCallbackQueryId(String.valueOf(query.getId()));
        queryAnswer.setCacheTime(500);

        User user = loginController.login(message.getChat().getId());
        callbackController.select(markup, query.getData(), user.getId());

        return markup;
    }

    /*
     * Fallback
     * */
    public SendMessage anyMessage(Message message, SendMessage sender) {
        String name = message.getChat().getFirstName();
        sender.setText("Kechirasiz, " + name + "\nBotga faqat mant kiritish tavsiya etiladi!");
        return sender;
    }
}
