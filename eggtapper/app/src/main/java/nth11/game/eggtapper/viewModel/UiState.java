package nth11.game.eggtapper.viewModel;


import androidx.fragment.app.Fragment;

import nth11.game.eggtapper.R;
import nth11.game.eggtapper.model.GameCurrency;

public class UiState {
    //    private final int FIRST_TEX = R.drawable.egg1;
    private final GameCurrency money;
    private final long strength;

    private final long toolForce;

    private final GameCurrency toolProfit;
    private final GameCurrency toolUpCoastProfit;
    private final GameCurrency toolUpCoastForce;
    private Fragment fragmentActive;
    private final long incubatorForce;
    private final GameCurrency incubatorProfit;
    private final GameCurrency incubatorUpCoastprofit;
    private final GameCurrency incubatorUpCoastForce;
    private int eggTexture = R.drawable.egg_stage_0;

    private int id = -1;


    public UiState(GameCurrency money, Integer strength, GameCurrency toolProfit, long toolForce , GameCurrency toolUpCoastProfit, GameCurrency toolUpCoastForce, Fragment shopActive, GameCurrency incubatorProfit, long incubatorForce , GameCurrency incubatorUpCoastprofit, GameCurrency incubatorUpCoastForce) {
        this(money, strength,toolProfit,toolForce, toolUpCoastProfit, toolUpCoastForce, shopActive,incubatorProfit,incubatorForce ,incubatorUpCoastprofit, incubatorUpCoastForce, 0,R.drawable.egg_stage_0);
    }


    public UiState(GameCurrency money, long strength, GameCurrency toolProfit, long toolForce, GameCurrency toolUpCoastProfit, GameCurrency toolUpCoastForce, Fragment shopActive, GameCurrency incubatorProfit, long incubatorForce , GameCurrency incubatorUpCoastprofit, GameCurrency incubatorUpCoastForce, int id, int eggTexture) {
        this.money = money;
        this.strength = strength;

        this.toolProfit = toolProfit;
        this.toolForce = toolForce;
        this.toolUpCoastForce = toolUpCoastForce;
        this.toolUpCoastProfit = toolUpCoastProfit;

        this.fragmentActive = shopActive;

        this.incubatorProfit = incubatorProfit;
        this.incubatorForce = incubatorForce;
        this.incubatorUpCoastprofit = incubatorUpCoastprofit;
        this.incubatorUpCoastForce = incubatorUpCoastForce;

        this.id = id;
        this.eggTexture = eggTexture;
    }
    public Fragment getFragmentActive() {
        return fragmentActive;
    }

    public UiState setFragmentActive(Fragment fragmentActive) {
        this.fragmentActive = fragmentActive;
        return null;
    }

    public GameCurrency getMoney() {
        return money;
    }

    public long getStrenght() {
        return strength;
    }


    public GameCurrency getIncubatorUpCoastProfit() {
        return incubatorUpCoastprofit;
    }
    public GameCurrency getIncubatorUpCoastForce(){

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

     public GameCurrency getToolProfit() {
         return toolProfit;
     }
     public GameCurrency getToolUpCoastProfit() {
         return toolUpCoastProfit;
     }


     public GameCurrency getToolUpCoastForce() {
         return toolUpCoastForce;
     }

     public long getIncubatorForce() {
         return incubatorForce;
     }

     public GameCurrency getIncubatorProfit() {
         return incubatorProfit;
     }
 }
