package tests;

import entities.Currency;
import models.Session;
import org.junit.jupiter.api.Test;
import services.CurrencyService;
import services.SessionService;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class SessionServiceTest {

    SessionService sessionService = new SessionService();

    CurrencyService currencyService = new CurrencyService();

    @Test
    void selectedCurrency() {
        Session session = sessionService.findByUserId(1);
        Currency[] currencies = currencyService.getSelectedCurrencies(session.getData());

        sessionService.updateData(session, "target=UZS", currencies);

        assertNotNull(session);
    }
}