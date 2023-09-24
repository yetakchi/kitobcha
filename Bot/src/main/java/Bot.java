import config.Data;
import database.Connector;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Bot extends TelegramLongPollingBot {

    MessageHandler handler = new MessageHandler();

    @Override
    public String getBotUsername() {
        return " "; // config.Data.username;
    }

    @Override
    public String getBotToken() {
        return Data.token;
    }

    @Override
    public void onUpdateReceived(Update chat) {
        // Callback handler
        if (chat.hasCallbackQuery()) {
            CallbackQuery query = chat.getCallbackQuery();
            Message message = query.getMessage();

            EditMessageReplyMarkup response = handler.callbackHandler(query, message);
            reply(response);
            return;
        }

        Message message = chat.getMessage();
        SendMessage sender = new SendMessage();
        sender.setChatId(message.getChatId().toString());

        // Command handler
        if (message.hasEntities()) {
            SendMessage res = handler.commandHandler(message, sender);
            sendMessage(res);
            return;
        }

        // Text message handler
        if (message.hasText()) {
            SendMessage response = handler.textMessageHandler(message, sender);
            sendMessage(response);
            return;
        }

        // Any type messages [different from the above] (sticker, files or etc)
        sendMessage(handler.anyMessage(message, sender));
    }

    private void sendMessage(SendMessage sender) {
        try {
            execute(sender);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void reply(EditMessageReplyMarkup markup) {
        try {
            execute(markup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /* Start */
    public static void main(String[] args) {
        Bot bot = new Bot();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);

            new Connector().connect();
            // Notify to Console
            System.out.println("Bot ishga tushdi!");
        } catch (TelegramApiException e) {
            System.out.println("Xatolik: " + e.getMessage());
        }
    }
}
