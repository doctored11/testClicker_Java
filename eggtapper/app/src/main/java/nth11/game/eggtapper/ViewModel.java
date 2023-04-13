package nth11.game.eggtapper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.Closeable;


class UiState {
    private Integer money;
    private Integer strength;
    private Integer toolUpCoast;



    public UiState(Integer money, Integer strength) {
        this.money = money;
        this.strength = strength;
    }
    public UiState(Integer money, Integer strength, Integer toolUpCoast) {
        this.money = money;
        this.strength = strength;
        this.toolUpCoast= toolUpCoast;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getStrenght() {
        return strength;
    }
}

//
//
//
//
public class ViewModel extends androidx.lifecycle.ViewModel {

    Player player;
    Egg clickEgg;


    public ViewModel() {
        createPlayer();
        createEgg();
    }


    private final MutableLiveData<UiState> uiState =
            new MutableLiveData(new UiState(0, 0));

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    public void createPlayer() {
        player = new Player(0, new TapTool(5, 1, 50));
    }

    public void createEgg() {
        clickEgg = new Egg(1000);

    }

    public void onTap() {
        clickEgg.reduceStrength(player.getTool().getTapForce());
        player.addMoney(player.getTool().getProfitability());
        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght()));
    }

    public  void onToolUp(){
        TapTool newTool = new TapTool(player.getTool().getTapForce() * 2, player.getTool().getProfitability() * 2, player.getTool().getCoast()*4);
        player.setTool(newTool);
        player.spendMoney(newTool.getCoast());
        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), newTool.getCoast()));
    }

}
