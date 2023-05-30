package nth11.game.eggtapper.model.entities;

import nth11.game.eggtapper.model.currency.GameCurrency;

public class Incubator extends TapTool {
    private int timer;

    public Incubator(long tapForce, GameCurrency profitability, GameCurrency coastF, GameCurrency coastP, int timer, long upCountProf, long upCountForce) {
        super(tapForce, profitability, coastF,coastP,upCountProf, upCountForce);
        this.timer = timer;
    }
    public int getTimer(){
        return this.timer;
    }
}
