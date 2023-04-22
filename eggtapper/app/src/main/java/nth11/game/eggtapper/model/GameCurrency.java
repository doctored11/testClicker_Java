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

    public static boolean compare(GameCurrency first, GameCurrency second) {

        if (first.getDegreeByPrefix() == second.getDegreeByPrefix() ) {

            return (first.value >= second.value);
        }

       if (first.getDegreeByPrefix() >= second.getDegreeByPrefix()){
//           GameCurrency(this.value * Math.pow(10, this.getDegreeByPrefix()) + second.value, second.prefix);
           int deltaDegree = first.getDegreeDifference(second);
          GameCurrency newFirst = new  GameCurrency(first.value * Math.pow(10, deltaDegree) , second.prefix);
          return (newFirst.value>second.value);
       } else{
           int deltaDegree = second.getDegreeDifference(first);
           GameCurrency newSecond = new  GameCurrency(second.value * Math.pow(10, deltaDegree) , first.prefix);
           return (first.value>newSecond.value);

       }

    }

    public static boolean comparePrefix(GameCurrency first, GameCurrency second) {
        return first.getDegreeByPrefix() >= second.getDegreeByPrefix();

    }

    public void prefixUpdate() {
        Log.e("степень при попытке апдейта: ", this.getDegreeByPrefix() + " число " + this.value + this.getFormattedValue());
        if (this.value > 999 * Math.pow(10, this.getDegreeByPrefix())) {
            prefixUp();
            return;
        }

        if (this.value < 1 ) {
            prefixDown();
            return;
        }

    }

    public void prefixUp() {
        Log.e("prefUp: ", " prefUp!");
        Log.d("до Up", this.getFormattedValue() + " - _ -" );
        this.setPrefix(getPrefixByDegree(getDegreeByPrefix() + 3));
        this.setValue(this.value / (Math.pow(10, this.getDegreeByPrefix())));
        Log.d("после  Up", this.getFormattedValue() + " -_-" );
    }

    public void prefixDown() {
        Log.e("prefDown: ", " prefDown!");
        //последовательность важна (нв)
        this.value *= (Math.pow(10, this.getDegreeByPrefix()));
        this.setPrefix(getPrefixByDegree(getDegreeByPrefix() - 3));

    }


    public GameCurrency add(GameCurrency second) {
        if (compare(this, second)) {
            Log.e("add", " 1");
            if (this.getDegreeByPrefix() == second.getDegreeByPrefix()) {
                Log.e("add", "1.1");
                return new GameCurrency((this.value + second.value), this.prefix);
            }
            if (comparePrefix(this, second)) {
                Log.e("add", "1.2");
                if (this.getDegreeByPrefix() - second.getDegreeByPrefix() <= 9) {
                    Log.e("add", "1.2.1");
                    int buffDegree = this.getDegreeDifference(second);
                    return new GameCurrency(this.value * Math.pow(10, buffDegree) + second.value, second.prefix); //
                }
                return new GameCurrency(this.value * 1.0000001, this.prefix);
            }

        } else {
            Log.e("add", "2");
            if (second.getDegreeByPrefix() - this.getDegreeByPrefix() <= 9) {
                int buffDegree = second.getDegreeDifference(this);

                Log.e("add", "2.1");
                return new GameCurrency(second.value * Math.pow(10, buffDegree  ) + this.value, this.prefix);
            }
            return second; //если разница очень большая просто заменяем на большее
        }
        return this;

    }

    public GameCurrency subtract(GameCurrency second) {
        Log.e("Вычет ",  "Начало вычета");

        this.prefixUpdate(); //важное обновление.
        second.prefixUpdate();
        Log.e("Вычет ",  this.getFormattedValue()+ "  " +second.getFormattedValue());
        if (!compare(this, second)) return this;
        if (this.prefix == second.prefix) {
            Log.e("Вычет ","успешно - одинаковые префиксы ");
            return new GameCurrency((this.value - second.value), this.prefix);
        }

        if (this.getDegreeByPrefix() - second.getDegreeByPrefix() <= 9) {
            Log.e("Вычет ","успешно - разные префиксы <=9 ");

//            return new GameCurrency(this.value * Math.pow(10, this.getDegreeByPrefix()) - second.value, second.prefix);
            int buffDegree = this.getDegreeDifference(second);
               return new GameCurrency(this.value * Math.pow(10, buffDegree) - second.value, second.prefix);


        }
        Log.e("Вычет ","успешно - разные префиксы >9 ");
        return this; // если разница супер большая - то не вычетаем - пофиг)

    }

    public GameCurrency simpleMultiplay(double simpleMultiplayer){
        Log.e("Умножение :", "возможны ошибки при больших значениях множителя! "+ simpleMultiplayer);
        if(Double.MAX_VALUE/simpleMultiplayer < this.value ){ //? проверить знак < >
            GameCurrency bg = new GameCurrency((Double.MAX_VALUE*0.99), this.prefix);
            bg.prefixUpdate();
            return bg;
        }
        GameCurrency bg = new GameCurrency(this.getValue()*simpleMultiplayer, this.prefix);
        bg.prefixUpdate();
        return bg;



    }
    public int getDegreeDifference( GameCurrency second){
       if( !GameCurrency.comparePrefix(this,second) )return -1;
        int fN = this.getDegreeByPrefix();
        int sN = second.getDegreeByPrefix();
        return fN-sN;




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