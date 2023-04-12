package nth11.game.eggtapper;

public class TapTool {
    private int tapForce;
    private int coast;
    private int profitability;

    public TapTool(int tapForce,  int profitability,int coast) {
        this.tapForce = tapForce;
        this.coast = coast;
        this.profitability = profitability;
    }


    public int getTapForce() {
        return tapForce;
    }

    public int getCoast() {
        return coast;
    }
    public int getProfitability(){
        return profitability;
    }
}
