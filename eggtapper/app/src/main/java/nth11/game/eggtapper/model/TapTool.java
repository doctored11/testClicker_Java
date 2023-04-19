package nth11.game.eggtapper.model;

public class TapTool {
    private int tapForce;
    private int coastForce;
    private int profitability;
    private int coastProfit;

    public TapTool(int tapForce,  int profitability,int coastF,int coastP) {
        this.tapForce = tapForce;
        this.coastForce = coastF;
        this.profitability = profitability;
        this.coastProfit = coastP;
    }

    public int getTapForce() {
        return tapForce;
    }

    public int getCoastForce() {
        return coastForce;
    }
    public int getCoastProfit() {
        return coastProfit;
    }
    public int getProfitability(){
        return profitability;
    }
}
