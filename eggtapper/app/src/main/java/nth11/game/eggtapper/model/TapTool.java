package nth11.game.eggtapper.model;

public class TapTool {
    private long tapForce;
    private GameCurrency coastForce;
    private GameCurrency profitability;
    private GameCurrency coastProfit;

    private long upCountProf;
    private long upCountForce;

    public TapTool(long tapForce, GameCurrency profitability, GameCurrency coastF, GameCurrency coastP, long countProf, long countForce) {
        this.tapForce = tapForce;
        this.coastForce = coastF;
        this.profitability = profitability;
        this.coastProfit = coastP;
        this.upCountProf = countProf;
        this.upCountForce = countForce;

    }

    public long getTapForce() {
        return (long) tapForce;
    }

    public GameCurrency getCoastForce() {
        return coastForce;
    }
    public GameCurrency getCoastProfit() {
        return coastProfit;
    }
    public GameCurrency getProfitability(){

        return profitability;
    }



    public long getUpCountProf() {
        return upCountProf;
    }

    public long getUpCountForce() {
        return upCountForce;
    }
}
