package controllers;

import entities.Currency;
import models.Session;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import resources.Button;
import services.CurrencyService;
import services.SessionService;


public class CallbackController {

    SessionService sessionService = new SessionService();

    CurrencyService currencyService = new CurrencyService();

    public void select(EditMessageReplyMarkup sender, String data, long userId) {
        Session session = sessionService.findByUserId(userId);

        Currency[] currencies = currencyService.getSelectedCurrencies(session.getData());
        setSelectedCurrency(data, session, currencies);

        currencies = currencyService.getSelectedCurrencies(session.getData());
        sender.setReplyMarkup(Button.currencyButtonsWithData(currencies));
    }

    public void setSelectedCurrency(String callData, Session session, Currency[] currencies) {
        sessionService.updateData(session, callData, currencies);
    }
}
