package nth11.game.eggtapper.viewModel;


import nth11.game.eggtapper.R;

 public class UiState {
    //    private final int FIRST_TEX = R.drawable.egg1;
    private final Integer money;
    private final Integer strength;
    private final Integer toolUpCoast;
    private Boolean shopActive;
    private final Integer incubatorUpCoast;
    private int eggTexture = R.drawable.egg_stage_0;

    private int id = -1;


    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive, Integer incubatorCoast) {
        this(money, strength, toolUpCoast, shopActive, incubatorCoast, R.drawable.egg_stage_0);
    }

    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive, Integer incubatorCoast, int id) {
        this.money = money;
        this.strength = strength;
        this.toolUpCoast = toolUpCoast;
        this.shopActive = shopActive;
        this.incubatorUpCoast = incubatorCoast;
        this.id = id;
    }

    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive, Integer incubatorCoast, int id, int eggTexture) {
        this.money = money;
        this.strength = strength;
        this.toolUpCoast = toolUpCoast;
        this.shopActive = shopActive;
        this.incubatorUpCoast = incubatorCoast;
        this.id = id;
        this.eggTexture = eggTexture;
    }

    public UiState(Integer money, Integer strength) {
        this(money, strength, null, false, null);
    }

    public UiState(Integer money, Integer strength, Integer toolUpCoast) {
        this(money, strength, toolUpCoast, false, null);
    }

    public UiState(Integer money, Integer strength, Boolean shopActive) {
        this(money, strength, null, shopActive, null);
    }

    public UiState(Integer money, Integer strength, Boolean shopActive, Integer incubatorCoast) {
        this(money, strength, null, shopActive, incubatorCoast);
    }

    public Boolean getShopActive() {
        return shopActive;
    }

    public UiState setShopActive(Boolean shopActive) {
        this.shopActive = shopActive;
        return null;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getStrenght() {
        return strength;
    }

    public Integer getToolUpCoast() {
        return toolUpCoast;
    }

    public Integer getIncubatorUpCoast() {
        return incubatorUpCoast;
    }

    public int getEggTexture() {
        return eggTexture;
    }

    public void setId(int id) {
        this.id = id;
    }
}
