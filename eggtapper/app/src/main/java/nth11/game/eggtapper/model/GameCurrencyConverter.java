package nth11.game.eggtapper.model;

import androidx.room.TypeConverter;

public class GameCurrencyConverter {
    @TypeConverter
    public static GameCurrency fromString(String value) {
        // Ваша логика преобразования строки в тип GameCurrency
        return GameCurrency.parse(value);
    }

    @TypeConverter
    public static String toString(GameCurrency value) {
        // Ваша логика преобразования типа GameCurrency в строку
        return value.getFormattedValue();
    }
}
