package config;

import io.github.cdimascio.dotenv.Dotenv;

public class Data {

    private static final Dotenv env = Dotenv.load();

    public static final String token = env.get("BOT_TOKEN");

    public static final String username = env.get("BOT_USERNAME");

    public static final String dbname = env.get("DB_NAME");

    public static final String currencyApiURL = "https://cbu.uz/oz/arkhiv-kursov-valyut/json/";
}
