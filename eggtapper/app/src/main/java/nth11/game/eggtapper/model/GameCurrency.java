package nth11.game.eggtapper.model;

import android.util.Log;

public class GameCurrency implements Comparable<GameCurrency> {
    private double value; // Значение валюты
    private char prefix; // Приставка, означающая единицы измерения

    // Конструктор
    public GameCurrency(double value, char prefix) {
        this.value = value;
        this.prefix = prefix;
    }


    // Метод для сравнения

    public boolean compare(GameCurrency first, GameCurrency second) {
        if (first.prefix == second.prefix) {
            return (first.value >= second.value);
        }
        return (first.getDegreeByPrefix() >= second.getDegreeByPrefix());

    }

    public boolean comparePrefix(GameCurrency first, GameCurrency second) {
        return first.prefix >= second.prefix;

    }

    public void prefixUpdate() {
        Log.e("степень при попытке апдейта: ", this.getDegreeByPrefix() + " ");
        if (this.value > 999 * Math.pow(10, this.getDegreeByPrefix())) {
            prefixUp();
            return;
        }

        if (this.value < 1 * Math.pow(10, this.getDegreeByPrefix())) {
            prefixDown();
            return;
        }

    }

    public void prefixUp() {
        Log.e("prefUp: ", " prefUp!");
        this.setPrefix(getPrefixByDegree(getDegreeByPrefix() + 3));
        this.value /= (Math.pow(10, this.getDegreeByPrefix()));
    }

    public void prefixDown() {
        Log.e("prefDown: ", " prefDown!");
        //последовательность важна (нв)
        this.value *= (Math.pow(10, this.getDegreeByPrefix()));
        this.setPrefix(getPrefixByDegree(getDegreeByPrefix() - 3));

    }


    public GameCurrency add(GameCurrency second) {
        if (compare(this, second)) {
            if (this.prefix == second.prefix) {
                return new GameCurrency((this.value + second.value), this.prefix);
            }
            if (comparePrefix(this, second)) {
                if (this.getDegreeByPrefix() - second.getDegreeByPrefix() <= 9) {
                    return new GameCurrency(this.value * Math.pow(10, this.getDegreeByPrefix()) + second.value, second.prefix);
                }
                return new GameCurrency(this.value * 1.0000001, this.prefix);
            }

        } else {
            if (second.getDegreeByPrefix() - this.getDegreeByPrefix() <= 9) {
                return new GameCurrency(second.value * Math.pow(10, second.getDegreeByPrefix()) + this.value, this.prefix);
            }
            return second; //если разница очень большая просто заменяем на большее
        }
        return this;

    }

    public GameCurrency subtract(GameCurrency second) {
        if (!compare(this, second)) return this;
        if (this.prefix == second.prefix) {
            return new GameCurrency((this.value - second.value), this.prefix);
        }

        if (this.getDegreeByPrefix() - second.getDegreeByPrefix() <= 9) {
            return new GameCurrency(this.value * Math.pow(10, this.getDegreeByPrefix()) - second.value, second.prefix);
        }
        return this; // если разница супер большая - то не вычетаем - пофиг)

    }

    public GameCurrency simpleMultiplay(double simpleMultiplayer){
        Log.e("Умножение :", "возможны ошибки при больших значениях множителя! "+ simpleMultiplayer);
        if(Double.MAX_VALUE/simpleMultiplayer < this.value ){
            GameCurrency bg = new GameCurrency((Double.MAX_VALUE*0.99), this.prefix);
            bg.prefixUpdate();
            return bg;
        }
        GameCurrency bg = new GameCurrency(this.getValue()*simpleMultiplayer, this.prefix);
        bg.prefixUpdate();
        return bg;



    }








    //  вспомогательныt методs


    private int getDegreeByPrefix() {
        switch (this.prefix) {
            case 'T':
                return 12;
            case 'B':
                return 9;
            case 'M':
                return 6;
            case 'K':
                return 3;
            default:
                return 0;
        }
    }

    private char getPrefixByDegree(int degree) {
        switch (degree) {
            case 12:
                return 'T';
            case 9:
                return 'B';
            case 6:
                return 'M';
            case 3:
                return 'K';
            default:
                return ' ';
        }
    }

     //Геттеры Сеттер и прочее

    // Геттер для форматированного значения
    public String getFormattedValue() {
        String formattedValue = String.format("%.2f", this.value);
        return formattedValue + " " + this.prefix;
    }

    // Геттеры и сеттеры для полей
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public char getPrefix() {
        return prefix;
    }

    public void setPrefix(char prefix) {
        this.prefix = prefix;
    }


    @Override
    public int compareTo(GameCurrency second) {
        return 0;
    }
}