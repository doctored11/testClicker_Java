package nth11.game.eggtapper;

import android.os.Handler;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Timer;
import java.util.TimerTask;


class UiState {
    private Integer money;
    private Integer strength;
    private Integer toolUpCoast;
    private Boolean shopActive = false;






    public UiState(Integer money, Integer strength) {
        this.money = money;
        this.strength = strength;
        this.shopActive = false;
    }
    public UiState(Integer money, Integer strength, Integer toolUpCoast) {
        this.money = money;
        this.strength = strength;
        this.toolUpCoast= toolUpCoast;
        this.shopActive = false;
    }
    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive) {
        this.money = money;
        this.strength = strength;
        this.toolUpCoast= toolUpCoast;
        this.shopActive = shopActive;
    }
    public UiState(Integer money, Integer strength,  Boolean shopActive) {
        this.money = money;
        this.strength = strength;
        this.shopActive = shopActive;
    }
    public Boolean getShopActive(){return shopActive;}
    public UiState setShopActive(Boolean shopActive){this.shopActive = shopActive;
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
}

//
//
//
//
public class ViewModel extends androidx.lifecycle.ViewModel {

    Player player;
    Egg clickEgg;
    ShopFragment shopFragment ;


    public ViewModel() {
        createPlayer();
        createEgg();
        createShopFragment();


    }

    public ShopFragment getShopFragment(){
        return shopFragment;
    }


    private final MutableLiveData<UiState> uiState =
            new MutableLiveData(new UiState(0, 0));

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    public void createPlayer() {
        player = new Player(0, new TapTool(5, 1, 50));
    }

    public void createShopFragment(){

        shopFragment = new ShopFragment(this);

    }

    public void createEgg() {
        clickEgg = new Egg(1000);

    }
    //TODO
    public void onTap() {
        if(clickEgg.statusChecker()) {
            createEgg();
            player.addMoney(player.getTool().getProfitability()*100) ;//временное решение

        };
        clickEgg.reduceStrength(player.getTool().getTapForce());
        player.addMoney(player.getTool().getProfitability());
        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght()));
    }

    public  void onToolUp(){
        int coast = player.getTool().getCoast();
        if (player.getMoney()<coast ) return;
        player.spendMoney(player.getTool().getCoast());
        TapTool newTool = new TapTool(player.getTool().getTapForce() * 2, player.getTool().getProfitability() * 2, player.getTool().getCoast()*4);
        player.setTool(newTool);

        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), newTool.getCoast()));
    }

    public void onShopClick( boolean fl){

        TapTool newTool = new TapTool(player.getTool().getTapForce() , player.getTool().getProfitability() , player.getTool().getCoast());

        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(),newTool.getCoast(),fl));
//        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght()).setShopActive(fl

    }

//    TODO метод пока сырой - переписать нормально
public void AutoTap() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            player.addMoney(1);
            uiState.postValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(),uiState.getValue().getShopActive()));
        }
    }, 500, 500);
}



}
