package nth11.game.eggtapper.model;

public class TapTool {
    private long tapForce;
    private long coastForce;
    private long profitability;
    private long coastProfit;

    private long upCountProf;
    private long upCountForce;

    public TapTool(long tapForce, long profitability, long coastF, long coastP, long countProf, long countForce) {
        this.tapForce = tapForce;
        this.coastForce = coastF;
        this.profitability = profitability;
        this.coastProfit = coastP;
        this.upCountProf = countProf;
        this.upCountForce = countForce;

    }

    public long getTapForce() {
        return tapForce;
    }

    public long getCoastForce() {
        return coastForce;
    }
    public long getCoastProfit() {
        return coastProfit;
    }
    public long getProfitability(){
        return profitability;
    }



    public long getUpCountProf() {
        return upCountProf;
    }

    public long getUpCountForce() {
        return upCountForce;
    }
}
