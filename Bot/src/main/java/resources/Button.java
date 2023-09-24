package resources;

import entities.Currency;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Button {

    // MainButton
    public static ReplyKeyboardMarkup getMainButton() {
        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(new KeyboardRow(keyboardButtons(
                "Kitobcha",
                "Valyutalar"
        )));
        rows.add(new KeyboardRow(keyboardButtons(
                "Bot haqida"
        )));

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setKeyboard(rows);
        return markup;
    }

    // Currency button list
    public static InlineKeyboardMarkup currencyButtons() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        for (Currency currency : Currency.values())
            buttons.add(Arrays.asList(
                    inlineButton(currency.name(), String.format("original=%s", currency)),
                    inlineButton(currency.name(), String.format("target=%s", currency))
            ));

        return new InlineKeyboardMarkup(buttons);
    }

    public static InlineKeyboardMarkup currencyButtonsWithData(Currency[] selectedCurrencies) {
        Currency originalCurrency = selectedCurrencies[0];
        Currency targetCurrency = selectedCurrencies[1];

        List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();
        for (Currency currency : Currency.values())
            buttonList.add(Arrays.asList(
                    Button.inlineButton(getSelectedCurrency(originalCurrency, currency), String.format("original=%s", currency)),
                    Button.inlineButton(getSelectedCurrency(targetCurrency, currency) + " ", String.format("target=%s", currency))
            ));

        return new InlineKeyboardMarkup(buttonList);
    }

    // Add icon to selected currency button
    private static String getSelectedCurrency(Currency selected, Currency current) {
        if (selected == current)
            return current.name().concat(" \u2705");
        return current.name();
    }

    // Keyboard button list generator
    private static List<KeyboardButton> keyboardButtons(String... names) {
        List<KeyboardButton> buttons = new ArrayList<>();

        for (String name : names)
            buttons.add(new KeyboardButton(name));

        return buttons;
    }

    // InlineKeyboard button generator
    public static InlineKeyboardButton inlineButton(String text, String callbackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }
}
