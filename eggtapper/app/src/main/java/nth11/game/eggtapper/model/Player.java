package nth11.game.eggtapper.model;

public class Player {
    private GameCurrency money;
    private TapTool tool;

    public Player(GameCurrency money, TapTool tool) {
        this.money = money;
        this.tool = tool;
    }

    public GameCurrency getMoney() {
        return money;
    }

    public TapTool getTool() {
        return tool;
    }

    public void setMoney(GameCurrency money) {
        this.money = money;
    }
    public synchronized  void  addMoney(GameCurrency moneyAdd){ //!synchronized
        this.money = this.money.add(moneyAdd);
        this.money.prefixUpdate();
    }
    public void  spendMoney(GameCurrency spendMoney){
        this.money = this.money.subtract(spendMoney);
        this.money.prefixUpdate();
    }
    public synchronized  void  addMoney(){ //!
        this.money = this.money.add(new GameCurrency(1,' '));
        this.money.prefixUpdate();
    }

    public void setTool(TapTool tool) {
        this.tool = tool;
    }
}
