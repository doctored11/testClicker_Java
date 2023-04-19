package nth11.game.eggtapper.viewModel;


import android.util.Log;

import nth11.game.eggtapper.R;

 public class UiState {
    //    private final int FIRST_TEX = R.drawable.egg1;
    private final Integer money;
    private final Integer strength;

    private final Integer toolForce;

    private final Integer toolProfit;
    private final Integer toolUpCoastProfit;
    private final Integer toolUpCoastForce;
    private Boolean shopActive;
    private final Integer incubatorForce;
    private final Integer incubatorProfit;
    private final Integer incubatorUpCoastprofit;
    private final Integer incubatorUpCoastForce;
    private int eggTexture = R.drawable.egg_stage_0;

    private int id = -1;


    public UiState(Integer money, Integer strength,Integer toolProfit, Integer toolForce ,Integer toolUpCoastProfit,Integer toolUpCoastForce, Boolean shopActive,Integer incubatorProfit, Integer incubatorForce , Integer incubatorUpCoastprofit, Integer incubatorUpCoastForce) {
        this(money, strength,toolProfit,toolForce, toolUpCoastProfit, toolUpCoastForce, shopActive,incubatorProfit,incubatorForce ,incubatorUpCoastprofit, incubatorUpCoastForce, 0,R.drawable.egg_stage_0);
    }


    public UiState(Integer money, Integer strength,Integer toolProfit,Integer toolForce,  Integer toolUpCoastProfit, Integer toolUpCoastForce, Boolean shopActive,Integer incubatorProfit,Integer incubatorForce ,Integer incubatorUpCoastprofit, Integer incubatorUpCoastForce, int id, int eggTexture) {
        this.money = money;
        this.strength = strength;

        this.toolProfit = toolProfit;
        this.toolForce = toolForce;
        this.toolUpCoastForce = toolUpCoastForce;
        this.toolUpCoastProfit = toolUpCoastProfit;

        this.shopActive = shopActive;

        this.incubatorProfit = incubatorProfit;
        this.incubatorForce = incubatorForce;
        this.incubatorUpCoastprofit = incubatorUpCoastprofit;
        this.incubatorUpCoastForce = incubatorUpCoastForce;

        this.id = id;
        this.eggTexture = eggTexture;
    }
    public Boolean getShopActive() {
        return shopActive;
    }

    public UiState setShopActive(Boolean shopActive) {
        this.shopActive = shopActive;
        return null;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getStrenght() {
        return strength;
    }


    public Integer getIncubatorUpCoastProfit() {
        return incubatorUpCoastprofit;
    }
    public Integer getIncubatorUpCoastForce(){
        return incubatorUpCoastForce;
    }

    public int getEggTexture() {
        return eggTexture;
    }

    public void setId(int id) {
        this.id = id;
    }

     public Integer getToolForce() {
         return toolForce;
     }

     public Integer getToolProfit() {
         return toolProfit;
     }
     public Integer getToolUpCoastProfit() {
         return toolUpCoastProfit;
     }


     public Integer getToolUpCoastForce() {
         return toolUpCoastForce;
     }

     public Integer getIncubatorForce() {
         return incubatorForce;
     }

     public Integer getIncubatorProfit() {
         return incubatorProfit;
     }
 }
