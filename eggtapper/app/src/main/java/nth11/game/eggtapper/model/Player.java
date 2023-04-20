package nth11.game.eggtapper.model;

public class Player {
    private long money;
    private TapTool tool;

    public Player(long money, TapTool tool) {
        this.money = money;
        this.tool = tool;
    }

    public long getMoney() {
        return money;
    }

    public TapTool getTool() {
        return tool;
    }

    public void setMoney(long money) {
        this.money = money;
    }
    public void  addMoney(long money){
        this.money +=money;
    }
    public void  spendMoney(long money){
        this.money -=money;
    }
    public void  addMoney(){
        this.money ++;
    }

    public void setTool(TapTool tool) {
        this.tool = tool;
    }
}
