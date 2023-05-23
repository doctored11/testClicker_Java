package nth11.game.eggtapper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nth11.game.eggtapper.model.GameCurrency;

public class GameCurrencyTest {
    @Test
    public void testCompare() {
        GameCurrency currency1 = new GameCurrency(1000, 'K');
        GameCurrency currency2 = new GameCurrency(1, 'M');
        GameCurrency currency3 = new GameCurrency(100, 'B');

        assertFalse(GameCurrency.compare(currency2, currency1));
        assertTrue(GameCurrency.compare(currency3, currency2));
        assertFalse(GameCurrency.compare(currency1, currency3));
    }


    @Test
    public void testComparePrefix() {
        GameCurrency currency1 = new GameCurrency(1000, 'K');
        GameCurrency currency2 = new GameCurrency(1, 'M');
        GameCurrency currency3 = new GameCurrency(100, 'B');

        assertFalse(GameCurrency.comparePrefix(currency1, currency2));
        assertFalse(GameCurrency.comparePrefix(currency2, currency3));
        assertTrue(GameCurrency.comparePrefix(currency3, currency1));
    }

    @Test
    public void testPrefixUpdate() {
        GameCurrency currency = new GameCurrency(1000, 'K');
        currency.prefixUpdate();
        assertEquals("1,00 M", currency.getFormattedValue());

        currency.setValue(0.001);
        currency.setPrefix('M');
        currency.prefixUpdate();
        assertEquals("1,00 K", currency.getFormattedValue());
    }

    @Test
    public void testAdd() {
        GameCurrency currency1 = new GameCurrency(1000, 'K');
        GameCurrency currency2 = new GameCurrency(1, 'M');

        GameCurrency result = currency1.add(currency2);
        assertEquals("2000,00 K", result.getFormattedValue());
    }

    @Test
    public void testSubtract() {
        GameCurrency currency1 = new GameCurrency(1000, 'K');
        GameCurrency currency2 = new GameCurrency(1, 'M');

        GameCurrency result = currency1.subtract(currency2);
        assertEquals("0.0", Double.toString(result.getValue())); // 1000K - 1M = 0K

        currency1.setValue(500);
        currency2.setValue(200);
        result = currency1.subtract(currency2);
        assertEquals("300.0", Double.toString(result.getValue())); // 500K - 200K = 300K
    }


    @Test
    public void testSimpleMultiplay() {
        GameCurrency currency = new GameCurrency(1000, 'K');

        GameCurrency result = currency.simpleMultiplay(2);
        assertEquals("2,00 M", result.getFormattedValue());

        currency.setValue(1);
        result = currency.simpleMultiplay(1000);
        assertEquals("1000,00 M", result.getFormattedValue());
    }

    @Test
    public void testParse() {
        GameCurrency currency1 = GameCurrency.parse("1000 K");
        assertEquals("1000,00 K", currency1.getFormattedValue());

        GameCurrency currency2 = GameCurrency.parse("1,5 M");
        assertEquals("1,50 M", currency2.getFormattedValue());

        GameCurrency currency3 = GameCurrency.parse("200 B");
        assertEquals("200,00 B", currency3.getFormattedValue());
    }
}
