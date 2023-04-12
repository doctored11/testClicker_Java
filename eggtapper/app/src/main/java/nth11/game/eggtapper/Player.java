package nth11.game.eggtapper;

public class Player {
    private int money;
    private TapTool tool;

    public Player(int money, TapTool tool) {
        this.money = money;
        this.tool = tool;
    }

    public int getMoney() {
        return money;
    }

    public TapTool getTool() {
        return tool;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public void  addMoney(int money){
        this.money +=money;
    }
    public void  addMoney(){
        this.money ++;
    }

    public void setTool(TapTool tool) {
        this.tool = tool;
    }
}
