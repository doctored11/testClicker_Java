package nth11.game.eggtapper;

public class Incubator extends TapTool {
    private int timer;

    public Incubator(int tapForce, int profitability, int coast,int timer) {
        super(tapForce, profitability, coast);
        this.timer = timer;
    }
    public int getTimer(){
        return this.timer;
    }
}
