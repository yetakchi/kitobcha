package controllers;

import models.Session;
import models.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import resources.Button;
import services.BookMakerService;
import services.CurrencyService;
import services.SessionService;


public class MessageController {

    private final SessionService sessionService = new SessionService();

    private final BookMakerService bookService = new BookMakerService();

    private final CurrencyService currencyService = new CurrencyService();

    private User user;

    public String booklet() {
        try {
            Session session = sessionService.findByUserId(getUser().getId());
            sessionService.updateState(session, Session.BOOK_SELECTED);
            return "Kitobning boshlanish va tugash sahifalarini (misol: 1, 4) shaklida yoki sahifalar sonini (12 ko\u2018rinishida) kiriting!";
        } catch (Exception exception) {
            return "Oldin botdan ro\u2018yxatdan o\u2018ting";
        }
    }

    public void currencies(SendMessage sender) {
        Session session = sessionService.findByUserId(getUser().getId());
        sessionService.clearData(session);
        sessionService.updateState(session, Session.CURRENCY_SELECTED);

        sender.setReplyMarkup(Button.currencyButtons());
        sender.setText("Valyutani tanlang");
    }

    public String about() {
        Session session = sessionService.findByUserId(getUser().getId());
        sessionService.updateState(session, Session.MAIN_SELECTED);

        return "Yaratuvchi\nZaripboyev Anvar";
    }

    public String action(String text) {
        Session session = sessionService.findByUserId(getUser().getId());
        short status = session.getStatus();

        String result = "Birorta funksiyani tanlang!";
        if (status == Session.BOOK_SELECTED) {
            int[] pages = bookService.booklet(text);

            String[] results = bookService.book(pages[0], pages[1]);
            String size = results[2];
            String front = results[0];
            String back = results[1];

            String response = "Jami: " + size + " varaq";
            return response + "\n\nOld tomoni:\n" + front + "\n\nOrqa tomoni:\n" + back;
        }

        if (status == Session.CURRENCY_SELECTED) {
            try {
                int amount = Integer.parseInt(text.trim());
                String[] res = currencyService.conversion(session, amount);

                if (res == null)
                    return "Valyutalarni tanlang!";
                return String.format("%d %s = %s %s", amount, res[1], res[0], res[2]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
