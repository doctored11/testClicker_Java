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

    public  void  prefixUpdate() {
        Log.e("степень при попытке апдейта: ", this.getDegreeByPrefix() + " число " + this.value + this.getFormattedValue());
        if (this.value >=Math.pow(10,this.getDegreeByPrefix())) {
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
        while (this.value >=Math.pow(10,this.getDegreeByPrefix())) {
            this.setPrefix(getPrefixByDegree(getDegreeByPrefix() + 3));
            Log.e("степеньь_____", this.getDegreeByPrefix() + "  ");
            Log.e("валуе _____", this.value + "  ");

            int degree = this.getDegreeByPrefix() - 3 < 3 ? 3 : this.getDegreeByPrefix() - 3;
            this.setValue(this.value / (Math.pow(10, (degree))));
            Log.e("числшоо _____", this.getFormattedValue() + "  ");
        }

        Log.d("после  Up", this.getFormattedValue() + " -_-" );

    }

    public void prefixDown() {
        Log.e("prefDown: ", " prefDown!");
        //последовательность важна (нв)
//        this.value *= (Math.pow(10, this.getDegreeByPrefix()));
//        this.setPrefix(getPrefixByDegree(getDegreeByPrefix() - 3));

        if (this.value < 1 && this.value!=0 ) {
            int degree = this.getDegreeByPrefix()<=3? 3:this.getDegreeByPrefix()-3 ;
            this.setValue(this.value * (Math.pow(10, (degree))));
            this.setPrefix(getPrefixByDegree(getDegreeByPrefix() - 3));
            Log.i("after pref down : ", this.getFormattedValue() + " ---");
            prefixDown();

        }


    }


    public  GameCurrency add(GameCurrency second) {
        Log.e("Сложенние ",  "Начало ");
//        this.prefixUpdate(); //Todo - возможно надо но почему то вызывает ошибку - разобраться !!!
//        second.prefixUpdate();
        if (compare(this, second)){
            Log.e("Сложенние ",  "первое>=");

            int buffDegree = this.getDegreeDifference(second);
            return new GameCurrency(this.value * Math.pow(10, buffDegree) + second.value, second.prefix);


        }
        else {
            Log.e("Сложенние ",  "второt<");
            int buffDegree = second.getDegreeDifference(this);
            return new GameCurrency(second.value * Math.pow(10, buffDegree) + this.value, this.prefix);
        }

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
        Log.e("Умножение :", "возможны ошибки при больших значениях множителя! "+ simpleMultiplayer +", до = " + this.getFormattedValue());
        if(Double.MAX_VALUE/simpleMultiplayer < this.value ){ //? проверить знак < >
            GameCurrency bg = new GameCurrency((Double.MAX_VALUE*0.99), this.prefix);
            bg.prefixUpdate();
            return bg;
        }
        this.prefixUpdate();
        GameCurrency bg = new GameCurrency(this.getValue()*simpleMultiplayer, this.prefix);
        bg.prefixUpdate();
        Log.i("после: ", bg.getFormattedValue()+ " ");
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
    public static GameCurrency parse(String input) {
        if (input== null) return new GameCurrency(0, ' ');
        input = input.replaceAll("\\s+", ""); // удаляем все пробелы из строки
        double value;
        char prefix;

        try {
            // пытаемся преобразовать число в строке
            value = Double.parseDouble(input.replaceAll("[^0-9.,]", "").replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }

        // если символ не найден, устанавливаем префикс по умолчанию
        if (!input.matches(".*[a-zA-Z]+.*")) {
            prefix = ' ';
        } else {
            // находим последний символ в строке
            char lastChar = input.charAt(input.length() - 1);
            // проверяем, что символ является буквой
            if (!Character.isLetter(lastChar)) {
                throw new IllegalArgumentException("Invalid input: " + input);
            }
            prefix = Character.toUpperCase(lastChar);
        }

        Log.i("Пaрсинг денег: ",new GameCurrency(value, prefix).getFormattedValue() +"__________________________________________________________________________" );
        return new GameCurrency(value, prefix);
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