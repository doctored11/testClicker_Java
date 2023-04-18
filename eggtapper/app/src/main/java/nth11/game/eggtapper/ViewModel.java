package nth11.game.eggtapper;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


class UiState {
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
            new MutableLiveData(new UiState(0, 0, null, false, null));

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    public void createPlayer() {
        player = new Player(0, new TapTool(5, 1, 50));
        if(context!=null) loadAll(context);
    }

    public void createAnimal() {
        Log.d("AnimalCreate", "!");
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1;
        animal = new Animal(randomNumber);
    }

    public void createShopFragment() {

        shopFragment = new ShopFragment(this);

    }

    public void createEgg() {
        eggDefender = true;
        clickEgg = new Egg(1000);

    }

    public Animal getAnimal() {
        return animal;
    }

    public void createIncubator() {
        incubator = new Incubator(1, 1, 1000, 500);
    }

    //TODO
    public void onTap() {
        if (context != null && animal.getBitmap() == null && firstStart) {
            firstStart = false;
            textureSet(context);
        }

        if (clickEgg.statusChecker()) {
            Log.d("!-!", "1_прочность <=0");
            createAnimal();
            if (context != null) {
                Log.d("!-!", "Вошел2 0_0");
                textureSet(context);
            }
            createEgg();
            player.addMoney(player.getTool().getProfitability() * 100);//временное решение
        }
        if (!eggDefender) clickEgg.reduceStrength(player.getTool().getTapForce());

        player.addMoney(player.getTool().getProfitability());

        uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), null, uiState.getValue().getShopActive(), incubator.getCoast(), animal.getId(), clickEgg.strengthChecker()));

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

                    if (context != null) {
                        Log.d("!-!", "ВошелAuto 0_0");
                        textureSet(context);
                    }

                }
                if (!eggDefender) clickEgg.reduceStrength(incubator.getTapForce());
                player.addMoney(incubator.getProfitability());

                uiState.postValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), null, uiState.getValue().getShopActive(), incubator.getCoast(), animal.getId(), clickEgg.strengthChecker()));


            }
        }, incubator.getTimer(), incubator.getTimer());
    }

    //
    public interface OnBitmapReadyListener {
        void onBitmapReady(Bitmap bitmap);
    }

    public void textureSet(Context context) {

        TextureLoader.loadTexture(context, animal.getSprite(), new OnBitmapReadyListener() {
            @Override
            public void onBitmapReady(Bitmap bitmap) {

                Log.e("!!!!", "Покрас картинки");
                animal.setBitmap(bitmap);
                eggDefender = false;
                Log.i("защитник ", eggDefender+" ");
            }
        });

    }


    public void setContext(Context context) {
        this.context = context;
        Log.e("111", player.getTool().getTapForce()+ " ");

//       loadAll(context);
    }
    public Player getPlayer(){
        //временно для теста метод
        return player;
    }


    public void saveAll(Context cont){

        BDHelper dbHelper;
         BDHelper.DataReader dataReader;
        dbHelper = new BDHelper(cont);
        TapTool tt = player.getTool();

        dbHelper.saveData(player.getMoney(), tt.getTapForce(), tt.getProfitability(), tt.getCoast(),
                incubator.getTapForce(), incubator.getProfitability(), incubator.getCoast());
    }
    public void loadAll(Context cont){
        BDHelper dbHelper;
        BDHelper.DataReader dataReader;
        dataReader = new BDHelper.DataReader(cont);
        int[] buff =  dataReader.readData();
        //COLUMN_MONEY, COLUMN_TAP_TOOL_FORCE, COLUMN_TAP_TOOL_PROFIT,
        //                    COLUMN_TAP_TOOL_COAST, COLUMN_INCUBATOR_FORCE, COLUMN_INCUBATOR_PROFIT, COLUMN_INCUBATOR_COAST
        Log.e("чтение Бд", buff[0]+" "+buff[1]+" "+buff[2]+" "+buff[4]+" -_-");
        player.setMoney(buff[0]);
        TapTool nt = new TapTool(buff[1],buff[2],buff[3]);
        player.setTool(nt);
        incubator = new Incubator(buff[4],buff[5],buff[6],500);
    }



}
