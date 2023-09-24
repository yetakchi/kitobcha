package services;

import config.Data;
import entities.Currency;
import models.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class CurrencyService {

    public String[] conversion(Session session, int amount) {
        Currency[] currencies = getSelectedCurrencies(session.getData());
        Currency original = currencies[0];
        Currency target = currencies[1];

        if (original == null || target == null)
            return null;

        double org = this.getCurrency(original.name());
        double tar = this.getCurrency(target.name());

        return new String[]{String.format("%.2f", org / tar * amount), original.name(), target.name()};
    }

    public double getCurrency(String name) {
        JSONArray jsonArray = read();

        JSONObject json;
        for (int i = 0; i < jsonArray.length(); i++) {
            json = jsonArray.getJSONObject(i);

            if (json.getString("Ccy").equals(name))
                return json.getDouble("Rate");
        }

        return 1d;
    }

    private JSONArray read() {
        try {
            URL url = new URL(Data.currencyApiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            connection.disconnect();

            return new JSONArray(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Currency[] getSelectedCurrencies(String data) {
        Currency original = null;
        Currency target = null;

        if (data != null) {
            String[] currencies = data.split(";");
            String org = currencies[0];
            String tar = currencies[1];

            String orgValue = org.split("=")[1]; // [1] -> value
            if (!orgValue.equals("null")) {
                original = Currency.valueOf(orgValue);
            }

            String tarValue = tar.split("=")[1]; // [1] -> value
            if (!tarValue.equals("null")) {
                target = Currency.valueOf(tarValue);
            }
        }

        return new Currency[]{original, target};
    }
}
