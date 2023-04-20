package nth11.game.eggtapper.viewModel;


import nth11.game.eggtapper.R;

 public class UiState {
    //    private final int FIRST_TEX = R.drawable.egg1;
    private final long money;
    private final long strength;

    private final long toolForce;

    private final long toolProfit;
    private final long toolUpCoastProfit;
    private final long toolUpCoastForce;
    private Boolean shopActive;
    private final long incubatorForce;
    private final long incubatorProfit;
    private final long incubatorUpCoastprofit;
    private final long incubatorUpCoastForce;
    private int eggTexture = R.drawable.egg_stage_0;

    private int id = -1;


    public UiState(Integer money, Integer strength,Integer toolProfit, Integer toolForce ,Integer toolUpCoastProfit,Integer toolUpCoastForce, Boolean shopActive,Integer incubatorProfit, Integer incubatorForce , Integer incubatorUpCoastprofit, Integer incubatorUpCoastForce) {
        this(money, strength,toolProfit,toolForce, toolUpCoastProfit, toolUpCoastForce, shopActive,incubatorProfit,incubatorForce ,incubatorUpCoastprofit, incubatorUpCoastForce, 0,R.drawable.egg_stage_0);
    }


    public UiState(long money, long strength, long toolProfit, long toolForce, long toolUpCoastProfit, long toolUpCoastForce, Boolean shopActive, long incubatorProfit, long incubatorForce , long incubatorUpCoastprofit, long incubatorUpCoastForce, int id, int eggTexture) {
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

    public long getMoney() {
        return money;
    }

    public long getStrenght() {
        return strength;
    }


    public long getIncubatorUpCoastProfit() {
        return incubatorUpCoastprofit;
    }
    public long getIncubatorUpCoastForce(){
        return incubatorUpCoastForce;
    }

    public int getEggTexture() {
        return eggTexture;
    }

    public void setId(int id) {
        this.id = id;
    }

     public long getToolForce() {
         return toolForce;
     }

     public long getToolProfit() {
         return toolProfit;
     }
     public long getToolUpCoastProfit() {
         return toolUpCoastProfit;
     }


     public long getToolUpCoastForce() {
         return toolUpCoastForce;
     }

     public long getIncubatorForce() {
         return incubatorForce;
     }

     public long getIncubatorProfit() {
         return incubatorProfit;
     }
 }
