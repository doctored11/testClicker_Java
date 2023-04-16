package nth11.game.eggtapper;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Timer;
import java.util.TimerTask;



class UiState {
    private final int FIRST_TEX = R.drawable.egg1;
    private final Integer money;
    private final Integer strength;
    private final Integer toolUpCoast;
    private  Boolean shopActive;
    private final Integer incubatorUpCoast;
    private int eggTexture = R.drawable.egg1;



    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive, Integer incubatorCoast) {
        this(money, strength, toolUpCoast, shopActive, incubatorCoast, R.drawable.egg1);
    }

    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive, Integer incubatorCoast, int eggTexture) {
        this.money = money;
        this.strength = strength;
        this.toolUpCoast = toolUpCoast;
        this.shopActive = shopActive;
        this.incubatorUpCoast = incubatorCoast;
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
}
//
//
//
//
public class ViewModel extends androidx.lifecycle.ViewModel {

    private Player player;
     private Animal animal;
     private  Egg clickEgg;
     private Incubator incubator;
     private ShopFragment shopFragment;


    public ViewModel() {
        createPlayer();
        createEgg();
        createAnimal();
        createShopFragment();
        createIncubator();
        AutoTap();

    }

    public ShopFragment getShopFragment() {
        return shopFragment;
    }


    private final MutableLiveData<UiState> uiState =
            new MutableLiveData(new UiState(0, 0, null, false, null));

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    public void createPlayer() {
        player = new Player(0, new TapTool(5, 1, 50));
    }
    public void  createAnimal(){
        animal = new Animal(1);
    }

    public void createShopFragment() {

        shopFragment = new ShopFragment(this);

    }

    public void createEgg() {
        clickEgg = new Egg(1000);

    }
    public  Animal getAnimal(){
        return  animal;
    }

    public void createIncubator() {
        incubator = new Incubator(1, 1, 1000, 500);
    }

    //TODO
    public void onTap() {
        if (clickEgg.statusChecker()) {
            createEgg();
            player.addMoney(player.getTool().getProfitability() * 100);//временное решение

        }

        clickEgg.reduceStrength(player.getTool().getTapForce());
        player.addMoney(player.getTool().getProfitability());

        TapTool newTool = new TapTool(player.getTool().getTapForce(), player.getTool().getProfitability(), player.getTool().getCoast());

        uiState.setValue(new UiState(player.getMoney(),clickEgg.getPercentStrenght(),newTool.getCoast(),false, incubator.getCoast(),clickEgg.strengthChecker()));

    }

    public void onToolUp() {
        int coast = player.getTool().getCoast();
        if (player.getMoney() < coast) return;
        player.spendMoney(player.getTool().getCoast());
        TapTool newTool = new TapTool(player.getTool().getTapForce() * 4, player.getTool().getProfitability() * 4, player.getTool().getCoast() * 8);//TODO - сделать по человечески
        player.setTool(newTool);

        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), newTool.getCoast()));
    }

    public void onIncUp() {
        int coast = incubator.getCoast();
        if (player.getMoney() < coast) return;
        player.spendMoney(coast);
        incubator = new Incubator(incubator.getTapForce() * 2, incubator.getProfitability() * 2, incubator.getCoast() * 22, incubator.getTimer()); //TODO - сделать по человечески
        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), false, incubator.getCoast()));
    }

    public void onShopClick(boolean fl) {

        TapTool newTool = new TapTool(player.getTool().getTapForce(), player.getTool().getProfitability(), player.getTool().getCoast());

        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), newTool.getCoast(), fl, incubator.getCoast()));
    }



    //    TODO метод пока сырой - переписать нормально
    public void AutoTap() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (clickEgg.statusChecker()) {//временное решение
                    createEgg();
                    player.addMoney(incubator.getTapForce() * 10);//временное решение

                }
                ;
                clickEgg.reduceStrength(incubator.getTapForce());


                player.addMoney(incubator.getProfitability());

                uiState.postValue(new UiState(player.getMoney(),clickEgg.getPercentStrenght(),null,uiState.getValue().getShopActive(), null,clickEgg.strengthChecker()));


            }
        }, incubator.getTimer(), incubator.getTimer());
    }




}
