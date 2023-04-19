package nth11.game.eggtapper.model;

public class Incubator extends TapTool {
    private int timer;

    public Incubator(int tapForce, int profitability, int coastF,int coastP,int timer) {
        super(tapForce, profitability, coastF,coastP);
        this.timer = timer;
    }
    public int getTimer(){
        return this.timer;
    }
}
