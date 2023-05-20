package nth11.game.eggtapper.model;


import android.view.View;

import androidx.room.Ignore;

import nth11.game.eggtapper.viewModel.ViewModel;

public class User {

    private String name;
    private String password;
    private GameCurrency money;
    private long strength;
    private long toolForce;
    private GameCurrency toolProfit;
    private GameCurrency toolUpCoastProfit;
    private GameCurrency toolUpCoastForce;
    private long incubatorForce;
    private GameCurrency incubatorProfit;
    private GameCurrency incubatorUpCoastprofit;

    public void setIncubatorUpCoastprofit(GameCurrency incubatorUpCoastprofit) {
        this.incubatorUpCoastprofit = incubatorUpCoastprofit;
    }

    public void setIncubatorUpCoastForce(GameCurrency incubatorUpCoastForce) {
        this.incubatorUpCoastForce = incubatorUpCoastForce;
    }

    private GameCurrency incubatorUpCoastForce;
    private long countTapP;
    private long countTapF;
    private long countIncP;
    private long countIncF;
    public User() {

    }

    @Ignore
    public User(String name, String password, GameCurrency money, long strength,
                long toolForce, GameCurrency toolProfit, GameCurrency toolUpCoastProfit,
                GameCurrency toolUpCoastForce, long incubatorForce, GameCurrency incubatorProfit,
                GameCurrency incubatorUpCoastprofit, GameCurrency incubatorUpCoastForce, long countTapP, long countTapF, long countIncP, long countIncF ) {
        this.name = name;
        this.password = password;
        this.money = money;
        this.strength = strength;
        this.toolForce = toolForce;
        this.toolProfit = toolProfit;
        this.toolUpCoastProfit = toolUpCoastProfit;
        this.toolUpCoastForce = toolUpCoastForce;
        this.incubatorForce = incubatorForce;
        this.incubatorProfit = incubatorProfit;
        this.incubatorUpCoastprofit = incubatorUpCoastprofit;
        this.incubatorUpCoastForce = incubatorUpCoastForce;
        this.countTapP = countTapP;
        this.countTapF = countTapF;
        this.countIncP = countIncP;
        this.countIncF = countIncF;

    }
    @Ignore
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.money = new GameCurrency(0,' ') ;
        this.strength = 5000;
        this.toolForce = ViewModel.BASE_TAPTOOL_FORCECLICK_VALUE;
        this.toolProfit = ViewModel.BASE_TAPTOOL_PROFITCLICK_VALUE ;
        this.toolUpCoastProfit = ViewModel.BASE_TAPTOOL_PROFITCLICK_PRICE;
        this.toolUpCoastForce = ViewModel.BASE_TAPTOOL_FORCECLICK_PRICE;
        this.incubatorForce = ViewModel.BASE_INCUBATOR_FORCE_VALUE;
        this.incubatorProfit = ViewModel.BASE_INCUBATOR_PROFIT_VALUE;
        this.incubatorUpCoastprofit = ViewModel.BASE_INCUBATOR_PROFIT_PRICE;
        this.incubatorUpCoastForce = ViewModel.BASE_INCUBATOR_FORCE_PRICE;
        this.countTapP = 0;
        this.countTapF = 0;
        this.countIncP = 0;
        this.countIncF = 0;

    }

    // Геттеры и сеттеры для всех полей

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GameCurrency getMoney() {
        return money;
    }

    public void setMoney(GameCurrency money) {
        this.money = money;
    }

    public long getStrength() {
        return strength;
    }

    public void setStrength(long strength) {
        this.strength = strength;
    }

    public long getToolForce() {
        return toolForce;
    }

    public void setToolForce(long toolForce) {
        this.toolForce = toolForce;
    }

    public GameCurrency getToolProfit() {
        return toolProfit;
    }

    public void setToolProfit(GameCurrency toolProfit) {
        this.toolProfit = toolProfit;
    }

    public GameCurrency getToolUpCoastProfit() {
        return toolUpCoastProfit;
    }

    public void setToolUpCoastProfit(GameCurrency toolUpCoastProfit) {
        this.toolUpCoastProfit = toolUpCoastProfit;
    }

    public GameCurrency getToolUpCoastForce() {
        return toolUpCoastForce;
    }

    public void setToolUpCoastForce(GameCurrency toolUpCoastForce) {
        this.toolUpCoastForce = toolUpCoastForce;
    }

    public long getIncubatorForce() {
        return incubatorForce;
    }

    public void setIncubatorForce(long incubatorForce) {
        this.incubatorForce = incubatorForce;
    }

    public GameCurrency getIncubatorProfit() {
        return incubatorProfit;
    }

    public void setIncubatorProfit(GameCurrency incubatorProfit) {
        this.incubatorProfit = incubatorProfit;
    }


    public GameCurrency getIncubatorUpCoastProfit() {
        return incubatorUpCoastprofit;
    }

    public GameCurrency getIncubatorUpCoastForce() {
        return incubatorUpCoastForce;
    }

    public long getCountTapP() {
        return countTapP;
    }

    public void setCountTapP(int countTapP) {
        this.countTapP = countTapP;
    }

    public long getCountTapF() {
        return countTapF;
    }

    public void setCountTapF(int countTapF) {
        this.countTapF = countTapF;
    }

    public long getCountIncP() {
        return countIncP;
    }

    public void setCountIncP(int countIncP) {
        this.countIncP = countIncP;
    }

    public long getCountIncF() {
        return countIncF;
    }

    public void setCountIncF(int countIncF) {
        this.countIncF = countIncF;
    }
}