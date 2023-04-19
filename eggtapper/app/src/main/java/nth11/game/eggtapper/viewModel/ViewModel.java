package nth11.game.eggtapper.viewModel;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
//class UiState {
//    //    private final int FIRST_TEX = R.drawable.egg1;
//    private final Integer money;
//    private final Integer strength;
//    private final Integer toolUpCoast;
//    private Boolean shopActive;
//    private final Integer incubatorUpCoast;
//    private int eggTexture = R.drawable.egg_stage_0;
//
//    private int id = -1;
//
//
//    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive, Integer incubatorCoast) {
//        this(money, strength, toolUpCoast, shopActive, incubatorCoast, R.drawable.egg_stage_0);
//    }
//
//    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive, Integer incubatorCoast, int id) {
//        this.money = money;
//        this.strength = strength;
//        this.toolUpCoast = toolUpCoast;
//        this.shopActive = shopActive;
//        this.incubatorUpCoast = incubatorCoast;
//        this.id = id;
//    }
//
//    public UiState(Integer money, Integer strength, Integer toolUpCoast, Boolean shopActive, Integer incubatorCoast, int id, int eggTexture) {
//        this.money = money;
//        this.strength = strength;
//        this.toolUpCoast = toolUpCoast;
//        this.shopActive = shopActive;
//        this.incubatorUpCoast = incubatorCoast;
//        this.id = id;
//        this.eggTexture = eggTexture;
//    }
//
//    public UiState(Integer money, Integer strength) {
//        this(money, strength, null, false, null);
//    }
//
//    public UiState(Integer money, Integer strength, Integer toolUpCoast) {
//        this(money, strength, toolUpCoast, false, null);
//    }
//
//    public UiState(Integer money, Integer strength, Boolean shopActive) {
//        this(money, strength, null, shopActive, null);
//    }
//
//    public UiState(Integer money, Integer strength, Boolean shopActive, Integer incubatorCoast) {
//        this(money, strength, null, shopActive, incubatorCoast);
//    }
//
//    public Boolean getShopActive() {
//        return shopActive;
//    }
//
//    public UiState setShopActive(Boolean shopActive) {
//        this.shopActive = shopActive;
//        return null;
//    }
//
//    public Integer getMoney() {
//        return money;
//    }
//
//    public Integer getStrenght() {
//        return strength;
//    }
//
//    public Integer getToolUpCoast() {
//        return toolUpCoast;
//    }
//
//    public Integer getIncubatorUpCoast() {
//        return incubatorUpCoast;
//    }
//
//    public int getEggTexture() {
//        return eggTexture;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//}

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

        AutoTap();

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
        Log.e("Animal","Create");
        if (!solveAnimalSpawn(850000)) {
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
        animal = null; // TODO сделать покрасивше
        eggDefender = true;// TODO сделать покрасивше
        clickEgg = new Egg(1000);

    }

    public Animal getAnimal() {
        return animal;
    }

    public void createIncubator() {
        incubator = new Incubator(1, 1, 1000, 1000, 500);
    }

    //TODO
    public void onTap() {
        if (context != null && firstStart) { //&& animal.getBitmap() == null
            firstStart = false;
            textureSet(context);
        }

        if (clickEgg.statusChecker()) {
            createEgg();
            createAnimal();

            if (context != null) {

                textureSet(context);
            }

            player.addMoney(player.getTool().getProfitability() * 100);//временное решение
        }
        if (!eggDefender) clickEgg.reduceStrength(player.getTool().getTapForce());

        player.addMoney(player.getTool().getProfitability());
        uiUpdate();
//        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(),incubator.getCoastForce(),incubator.getCoastProfit(), uiState.getValue().getShopActive(),uiState.getValue().getIncubatorUpCoastProfit(), uiState.getValue().getIncubatorUpCoastForce() ,animal.getId(), clickEgg.strengthChecker()));

    }

    public void onToolUpProf() {
        int coast = player.getTool().getCoastProfit();
        if (player.getMoney() < coast) return;
        player.spendMoney(coast);

        TapTool newTool = new TapTool(player.getTool().getTapForce(), player.getTool().getProfitability() * 2, player.getTool().getCoastForce(), player.getTool().getCoastProfit() * 2);//TODO - сделать по человечески
        player.setTool(newTool);
        uiState.getValue().setShopActive(false);


        uiUpdate();
    }

    public void onToolUpForce() {
        int coast = player.getTool().getCoastForce();
        if (player.getMoney() < coast) return;
        player.spendMoney(coast);

        TapTool newTool = new TapTool(player.getTool().getTapForce() * 2, player.getTool().getProfitability(), (int) Math.pow(player.getTool().getCoastForce(), 2), player.getTool().getCoastProfit());//TODO - сделать по человечески
        player.setTool(newTool);
        uiState.getValue().setShopActive(false);


        uiUpdate();
    }


    public void onIncUpProf() {
        int coast = incubator.getCoastProfit();
        if (player.getMoney() < coast) return;
        player.spendMoney(coast);
        incubator = new Incubator(incubator.getTapForce(), (int) (incubator.getProfitability() * 2), incubator.getCoastForce(), incubator.getCoastProfit() * 4, incubator.getTimer()); //TODO - сделать по человечески
        uiState.getValue().setShopActive(false);

        uiUpdate();
//        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), player.getTool().getCoastProfit(),player.getTool().getCoastForce(),false, incubator.getCoastProfit(),incubator.getCoastForce(),animal.getId(), clickEgg.strengthChecker()));
    }

    public void onIncUpForce() {
        int coast = incubator.getCoastForce();
        if (player.getMoney() < coast) return;
        player.spendMoney(coast);
        incubator = new Incubator(incubator.getTapForce() * 2, incubator.getProfitability(), incubator.getCoastForce() * 22, incubator.getCoastProfit(), incubator.getTimer()); //TODO - сделать по человечески
        uiState.getValue().setShopActive(false);

        uiUpdate();
    }

    public void onShopClick(boolean fl) {

        uiState.getValue().setShopActive(fl); // ;)
//        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), player.getTool().getCoastProfit(),player.getTool().getCoastForce(),uiState.getValue().getShopActive(), incubator.getCoastProfit(),incubator.getCoastForce(),animal.getId(), clickEgg.strengthChecker()));
        uiUpdate();

    }


    //    TODO метод пока сырой - переписать нормально
    public void AutoTap() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (clickEgg.statusChecker()) {//временное решение
                    createEgg();
                    createAnimal();

                    player.addMoney(incubator.getTapForce() * 10);//временное решение

                    if (context != null) {
                        textureSet(context);
                    }

                }
                if (!eggDefender) clickEgg.reduceStrength(incubator.getTapForce());
                player.addMoney(incubator.getProfitability());

                uiUpdateAuto();
//                uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), player.getTool().getCoastProfit(),player.getTool().getCoastForce(),uiState.getValue().getShopActive(), incubator.getCoastProfit(),incubator.getCoastForce(),animal.getId(), clickEgg.strengthChecker()));


            }
        }, incubator.getTimer(), incubator.getTimer());
    }

    //
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
        boolean boolbuf =(random.nextInt(101) < buffer && random.nextInt(101) < 50);
        Log.e("solve", buffer + " " + boolbuf);
        return boolbuf ;

    }


}
