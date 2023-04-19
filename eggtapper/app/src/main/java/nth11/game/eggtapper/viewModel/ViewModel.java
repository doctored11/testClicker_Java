package nth11.game.eggtapper.viewModel;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import nth11.game.eggtapper.R;
import nth11.game.eggtapper.view.ShopFragment;
import nth11.game.eggtapper.model.Animal;
import nth11.game.eggtapper.model.BDHelper;
import nth11.game.eggtapper.model.Egg;
import nth11.game.eggtapper.model.Incubator;
import nth11.game.eggtapper.model.Player;
import nth11.game.eggtapper.model.TapTool;
import nth11.game.eggtapper.model.TextureLoader;

//
//
//
//
public class ViewModel extends androidx.lifecycle.ViewModel {

    private Player player;
    private Animal animal;
    private Egg clickEgg;
    private Incubator incubator;
    private ShopFragment shopFragment;
    Boolean firstStart = true;
    Boolean eggDefender;
    private Context context;


    public ViewModel() {

        createPlayer();

        createEgg();

        createAnimal();

        createShopFragment();

        createIncubator();

//Log.e("0000","0000");
//        getSavedAll();

        autoTap();

    }

    public ShopFragment getShopFragment() {
        return shopFragment;
    }


    private final MutableLiveData<UiState> uiState =
            new MutableLiveData(new UiState(0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0));//внимание сюда)

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    public void createPlayer() {

        player = new Player(0, new TapTool(5, 1, 50, 50));
        if (context != null) loadAll(context);
    }

    public void createAnimal() {
        Log.e("Animal", "Create");
        if (!solveAnimalSpawn(980000)) {
            eggDefender = false;
            return;
        }
        Log.d("AnimalCreate", "!");
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1;
        animal = new Animal(randomNumber);
    }

    public void createShopFragment() {

        shopFragment = new ShopFragment(this);

    }

    public void createEgg() {

        clickEgg = new Egg(1000);

    }

    public Animal getAnimal() {
        return animal;
    }

    public void createIncubator() {
        incubator = new Incubator(1, 1, 1000, 1000, 500);
    }


    public void onTap() {
        if (context != null && firstStart) {
            firstStart = false;
            textureSet(context);
        }

        if (clickEgg.statusChecker()) {
            tapRestartScene();
        }

        if (!eggDefender) {
            clickEgg.reduceStrength(player.getTool().getTapForce());
        }

        player.addMoney(player.getTool().getProfitability());
        uiUpdate();
    }

    public void onToolUpProf() {
        int coast = player.getTool().getCoastProfit();
        updateTool(coast, player.getTool().getTapForce(), player.getTool().getProfitability() * 2, player.getTool().getCoastForce(), player.getTool().getCoastProfit() * 2);
    }

    public void onToolUpForce() {
        int coast = player.getTool().getCoastForce();
        updateTool(coast, player.getTool().getTapForce() * 2, player.getTool().getProfitability(), (int) Math.pow(player.getTool().getCoastForce(), 2), player.getTool().getCoastProfit());
    }

    public void onIncUpProf() {
        int coast = incubator.getCoastProfit();
        updateIncubator(coast, incubator.getTapForce(), (int) (incubator.getProfitability() * 2), incubator.getCoastForce(), incubator.getCoastProfit() * 4, incubator.getTimer());
    }

    public void onIncUpForce() {
        int coast = incubator.getCoastForce();
        updateIncubator(coast, incubator.getTapForce() * 2, incubator.getProfitability(), incubator.getCoastForce() * 22, incubator.getCoastProfit(), incubator.getTimer());
    }

    private void updateTool(int coast, int tapForce, int profitability, int coastForce, int coastProfit) {
        if (player.getMoney() < coast) return;
        player.spendMoney(coast);

        TapTool newTool = new TapTool(tapForce, profitability, coastForce, coastProfit);
        player.setTool(newTool);
        uiState.getValue().setShopActive(false);

        uiUpdate();
    }

    private void updateIncubator(int coast, int tapForce, int profitability, int coastForce, int coastProfit, int timer) {
        if (player.getMoney() < coast) return;
        player.spendMoney(coast);

        incubator = new Incubator(tapForce, profitability, coastForce, coastProfit, timer);
        uiState.getValue().setShopActive(false);

        uiUpdate();
    }

    public void onShopClick(boolean fl) {
        uiState.getValue().setShopActive(fl);
        uiUpdate();

    }

    public void autoTap() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        long delay = incubator.getTimer();
        long period = incubator.getTimer();
        executorService.scheduleAtFixedRate(new AutoTapTask(), delay, period, TimeUnit.MILLISECONDS);
    }

    public interface OnBitmapReadyListener {
        void onBitmapReady(Bitmap bitmap);
    }


    public void textureSet(Context context) {
        if (animal == null) return;
        TextureLoader.loadTexture(context, animal.getSprite(), new OnBitmapReadyListener() {
            @Override
            public void onBitmapReady(Bitmap bitmap) {
                Log.e("!!!!", "Покрас картинки");
                animal.setBitmap(bitmap);
                eggDefender = false;
                Log.i("защитник ", eggDefender + " ");
            }
        });
    }


    public void setContext(Context context) {
        this.context = context;
//       loadAll(context);
    }

    public Player getPlayer() {
        //временно для теста метод
        return player;
    }

    public void saveAll(Context cont) {

        BDHelper dbHelper;
        BDHelper.DataReader dataReader;
        dbHelper = new BDHelper(cont);
        TapTool tt = player.getTool();

        dbHelper.saveData(player.getMoney(), tt.getTapForce(), tt.getProfitability(), tt.getCoastForce(),
                incubator.getTapForce(), incubator.getProfitability(), incubator.getCoastForce());
    }

    public void loadAll(Context cont) {
        BDHelper dbHelper;
        BDHelper.DataReader dataReader;
        dataReader = new BDHelper.DataReader(cont);
        int[] buff = dataReader.readData();
        //COLUMN_MONEY, COLUMN_TAP_TOOL_FORCE, COLUMN_TAP_TOOL_PROFIT,
        //                    COLUMN_TAP_TOOL_COAST, COLUMN_INCUBATOR_FORCE, COLUMN_INCUBATOR_PROFIT, COLUMN_INCUBATOR_COAST
        Log.e("чтение Бд", buff[0] + " " + buff[1] + " " + buff[2] + " " + buff[4] + " -_-");
//        player.setMoney(buff[0]);
//        TapTool nt = new TapTool(buff[1],buff[2],buff[3]);
//        player.setTool(nt);
//        incubator = new Incubator(buff[4],buff[5],buff[6],500);
    }

    public void uiUpdate() {
        TapTool tt = player.getTool();

        if (animal != null) {
            uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), tt.getProfitability(), tt.getTapForce(), tt.getCoastProfit(), tt.getCoastForce(), uiState.getValue().getShopActive(), incubator.getProfitability(), incubator.getTapForce(), incubator.getCoastProfit(), incubator.getCoastForce(), animal.getId(), clickEgg.strengthChecker()));
        } else {
            uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), tt.getProfitability(), tt.getTapForce(), tt.getCoastProfit(), tt.getCoastForce(), uiState.getValue().getShopActive(), incubator.getProfitability(), incubator.getTapForce(), incubator.getCoastProfit(), incubator.getCoastForce(), 0, clickEgg.strengthChecker()));
        }
    }

    public void uiUpdateAuto() {
        TapTool tt = player.getTool();
        if (animal != null) {
            uiState.postValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), tt.getProfitability(), tt.getTapForce(), tt.getCoastProfit(), tt.getCoastForce(), uiState.getValue().getShopActive(), incubator.getProfitability(), incubator.getTapForce(), incubator.getCoastProfit(), incubator.getCoastForce(), animal.getId(), clickEgg.strengthChecker()));
        } else {
            uiState.postValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), tt.getProfitability(), tt.getTapForce(), tt.getCoastProfit(), tt.getCoastForce(), uiState.getValue().getShopActive(), incubator.getProfitability(), incubator.getTapForce(), incubator.getCoastProfit(), incubator.getCoastForce(), 0, clickEgg.strengthChecker()));
        }
    }


    public boolean solveAnimalSpawn(int eggStrength) {
        Random random = new Random();
        double buffer = eggStrength / 20000;
        boolean boolbuf = (random.nextInt(101) < buffer && random.nextInt(101) < 50);
        Log.e("solve", buffer + " " + boolbuf);
        return boolbuf;
    }

    public void tapRestartScene() {

        animal = null;
        eggDefender = true;

        createEgg();
        createAnimal();
        if (context != null) {
            textureSet(context);
        }
        player.addMoney(player.getTool().getProfitability() * 100);//временное решение
    }

    private class AutoTapTask implements Runnable {
        @Override
        public void run() {
            if (clickEgg != null && clickEgg.statusChecker()) {
                tapRestartScene();
            }
            if (clickEgg != null && !eggDefender) {
                clickEgg.reduceStrength(incubator.getTapForce());
            }
            if (player != null) {
                player.addMoney(incubator.getProfitability());
            }
            uiUpdateAuto();
        }
    }


}
