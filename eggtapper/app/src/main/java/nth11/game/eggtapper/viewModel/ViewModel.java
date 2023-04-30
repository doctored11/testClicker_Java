package nth11.game.eggtapper.viewModel;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import nth11.game.eggtapper.AuthFragment;
import nth11.game.eggtapper.RegFragment;
import nth11.game.eggtapper.model.MyDbHelper;
import nth11.game.eggtapper.model.User;
import nth11.game.eggtapper.view.SettingsFragment;
import nth11.game.eggtapper.model.GameCurrency;
import nth11.game.eggtapper.model.Animal;
//import nth11.game.eggtapper.model.BDHelper;
import nth11.game.eggtapper.model.Egg;
import nth11.game.eggtapper.model.Incubator;
import nth11.game.eggtapper.model.Player;
import nth11.game.eggtapper.model.TapTool;
import nth11.game.eggtapper.model.TextureLoader;
import nth11.game.eggtapper.view.ShopFragment;

//
//
//
//
public class ViewModel extends androidx.lifecycle.ViewModel {
    public final long MIN_EGG_STRENGTH = 1000l;
    //    public final long MAX_EGG_STRENGTH = 100_000_000_000_000l; //хз сколько надеюсь не очень много
    public final long MAX_EGG_STRENGTH = 25_000l; //хз сколько надеюсь не очень много



    public final GameCurrency BASE_TAPTOOL_FORCECLICK_PRICE = new GameCurrency(100, ' ');
    public final GameCurrency BASE_TAPTOOL_PROFITCLICK_PRICE = new GameCurrency(200, ' ');
    ;
    public final long BASE_TAPTOOL_FORCECLICK_VALUE = 1;
    public final GameCurrency BASE_TAPTOOL_PROFITCLICK_VALUE = new GameCurrency(800, ' ');
    ;

    public final double BASE_TAPTOOL_FORCECLICK_MULTIPLAER = 1.25;
    public final double BASE_TAPTOOL_PROFITCLICK_MULTIPLAER = 1.14;


    public final GameCurrency BASE_INCUBATOR_FORCE_PRICE = new GameCurrency(300, ' ');
    public final GameCurrency BASE_INCUBATOR_PROFIT_PRICE = new GameCurrency(250, ' ');
    public final long BASE_INCUBATOR_FORCE_VALUE = 0;
    public final GameCurrency BASE_INCUBATOR_PROFIT_VALUE = new GameCurrency(0, ' ');
    public final double BASE_INCUBATOR_FORCE_MULTIPLAER = 1.27;
    public final double BASE_INCUBATOR_PROFIT_MULTIPLAER = 1.12;

    private Player player;
    private Animal animal;
    private String Username = "default";
    private Egg clickEgg;
    private Incubator incubator;
    private ShopFragment shopFragment;
    private AuthFragment authorizationFragment;
    private RegFragment regFragment;
    private SettingsFragment settingsFragment;
    Boolean firstStart = true;
    Boolean eggDefender = true;
    private Context context;


    public ViewModel() {

        createPlayer();

        createAnimal();

        createFragments();

        createIncubator();

//Log.e("0000","0000");
//        getSavedAll();

        autoTap();
        createEgg(getRandomStrenght());
        Log.e("random", getRandomStrenght() + "_");
    }

    public ShopFragment getShopFragment() {
        return shopFragment;
    }
    public RegFragment getRegFragment() {
        return regFragment;
    }
    public AuthFragment getAuthorizationFragment(){
        return authorizationFragment;
    }

    public SettingsFragment getSettingsFragment() {
        return settingsFragment;
    }


    private final MutableLiveData<UiState> uiState =
            new MutableLiveData(new UiState(new GameCurrency(0, ' '), 0, new GameCurrency(0, ' '), 0, new GameCurrency(0, ' '), new GameCurrency(0, ' '), null, new GameCurrency(0, ' '), 0, new GameCurrency(0, ' '), new GameCurrency(0, ' ')));

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    public void createPlayer() {

        player = new Player(new GameCurrency(600, ' '), new TapTool(BASE_TAPTOOL_FORCECLICK_VALUE, BASE_TAPTOOL_PROFITCLICK_VALUE, BASE_TAPTOOL_FORCECLICK_PRICE, BASE_TAPTOOL_PROFITCLICK_PRICE, 1, 1));
        if (context != null) loadAll(context);
    }

    public void createAnimal() {
        Log.e("Animal", "Create");
        if (!solveAnimalSpawn(999000l)) {
            eggDefender = false;
            return;
        }
        Log.d("AnimalCreate", "!");
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1;
        animal = new Animal(randomNumber);
    }

    public void createFragments() {

        shopFragment = new ShopFragment(this);
        settingsFragment = new SettingsFragment(this);
        regFragment = new RegFragment(this);
        authorizationFragment = new AuthFragment(this);


    }

    public void createEgg(long strength) {

        clickEgg = new Egg(strength);

    }

    public Animal getAnimal() {
        return animal;
    }

    public void createIncubator() {
        incubator = new Incubator(BASE_INCUBATOR_FORCE_VALUE, BASE_INCUBATOR_PROFIT_VALUE, BASE_INCUBATOR_FORCE_PRICE, BASE_INCUBATOR_PROFIT_PRICE, 500, 1, 1);
    }


    public void onTap() {
        if (animal != null && clickEgg.statusChecker()) {
            Log.i("outTap", "_tapOut_" + (animal != null) + " " + clickEgg.statusChecker());
            return;
        }


        Log.e("onTap", player.getTool().getProfitability().getFormattedValue() + " " + uiState.getValue().getMoney().getFormattedValue());
        if (context != null && firstStart) {
            firstStart = false;
            textureSet(context);
        }

        if (clickEgg.statusChecker() && animal == null) {
            Log.i("++++++++++++=onTap", " RESTART( ");
            tapRestartScene();
        }

        if (!eggDefender) {
            clickEgg.reduceStrength(player.getTool().getTapForce());
        }

        player.addMoney(player.getTool().getProfitability());
        uiUpdate();
    }

    public boolean onAnimalTap() {
        if (animal == null || !clickEgg.statusChecker()) return false;

        Log.e("_____________________________________onBirdTap", player.getTool().getProfitability().getFormattedValue() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + uiState.getValue().getMoney().getFormattedValue());
//        if (context != null && firstStart) {
//            firstStart = false;
//            textureSet(context);
//        }

        if (!animal.statusChecker()) animal.reduceStrength();

        if (animal.statusChecker()) {
            tapRestartScene();
        }

//        if (!eggDefender) {

//

        player.addMoney(player.getTool().getProfitability().simpleMultiplay(10));

        uiUpdate();
        return true;
    }

    public void autoTap() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        long delay = incubator.getTimer();
        long period = incubator.getTimer();
        executorService.scheduleAtFixedRate(new AutoTapTask(), delay, period, TimeUnit.MILLISECONDS);
    }


    public void onToolUpProf() {
        GameCurrency coast = player.getTool().getCoastProfit();
        if (!GameCurrency.compare(player.getMoney(), coast)) return;

        GameCurrency newProfitCost = BASE_TAPTOOL_PROFITCLICK_PRICE.simpleMultiplay(Math.pow(BASE_TAPTOOL_PROFITCLICK_MULTIPLAER, player.getTool().getUpCountProf()));
        GameCurrency newProfitValue = player.getTool().getProfitability().add(new GameCurrency(5, ' '));
        newProfitValue.prefixUpdate();
        newProfitCost.prefixUpdate();

        updateTool(coast, player.getTool().getTapForce(), newProfitValue, player.getTool().getCoastForce(), newProfitCost, 0, 1);
    }

    public void onToolUpForce() {
        GameCurrency coast = player.getTool().getCoastForce();
        coast.prefixUpdate();
        if (!GameCurrency.compare(player.getMoney(), coast)) return;

        GameCurrency newForceCost = (BASE_TAPTOOL_FORCECLICK_PRICE.simpleMultiplay(Math.pow(BASE_TAPTOOL_FORCECLICK_MULTIPLAER, player.getTool().getUpCountForce())));
        long newForceValue = player.getTool().getTapForce() + 2;
        newForceCost.prefixUpdate();

        updateTool(coast, newForceValue, player.getTool().getProfitability(), newForceCost, player.getTool().getCoastProfit(), 1, 0);
    }

    public void onIncUpProf() {
        GameCurrency coast = incubator.getCoastProfit();
        if (!GameCurrency.compare(player.getMoney(), coast)) return;

        Log.d("onInc count", incubator.getUpCountProf() + " ");
        GameCurrency newProfitCost = (BASE_INCUBATOR_PROFIT_PRICE.simpleMultiplay(Math.pow(BASE_INCUBATOR_PROFIT_MULTIPLAER, incubator.getUpCountProf())));
//        Log.i("profit do:" , incubator.getProfitability().getFormattedValue() + "$");
        GameCurrency newProfitValue = (incubator.getProfitability().add(new GameCurrency(3, ' ')));
//        Log.i("profit after:" , newProfitValue.getFormattedValue() + "$");

        newProfitCost.prefixUpdate();
        newProfitValue.prefixUpdate();


        updateIncubator(coast, incubator.getTapForce(), newProfitValue, incubator.getCoastForce(), newProfitCost, incubator.getTimer(), 0, 1);
    }

    public void onIncUpForce() {
        GameCurrency coast = incubator.getCoastForce();
        if (!GameCurrency.compare(player.getMoney(), coast)) return;

        GameCurrency newForceCost = (BASE_INCUBATOR_FORCE_PRICE.simpleMultiplay(Math.pow(BASE_INCUBATOR_FORCE_MULTIPLAER, incubator.getUpCountForce())));
        long newForceValue = (incubator.getTapForce() + 1);
        newForceCost.prefixUpdate();


        updateIncubator(coast, newForceValue, incubator.getProfitability(), newForceCost, incubator.getCoastProfit(), incubator.getTimer(), 1, 0);
    }

    private void updateTool(GameCurrency coast, long tapForce, GameCurrency profitability, GameCurrency coastForce, GameCurrency coastProfit, int forceContUp, int profCountUp) {
//        if (!GameCurrency.compare(player.getMoney(),coast)) return;
        coast.prefixUpdate();
        player.spendMoney(coast);


        TapTool newTool = new TapTool(tapForce, profitability, coastForce, coastProfit, player.getTool().getUpCountProf() + profCountUp, player.getTool().getUpCountForce() + forceContUp);//
        player.setTool(newTool);
        uiState.getValue().setFragmentActive(null);

        uiUpdate();
    }

    private void updateIncubator(GameCurrency coast, long tapForce, GameCurrency profitability, GameCurrency coastForce, GameCurrency coastProfit, int timer, int forceCountUp, int profCountUp) {
        if (!GameCurrency.compare(player.getMoney(), coast)) return;
        player.spendMoney(coast);
        Log.e("Incubator count:", incubator.getUpCountForce() + " ");

        incubator = new Incubator(tapForce, profitability, coastForce, coastProfit, timer, incubator.getUpCountProf() + profCountUp, incubator.getUpCountForce() + forceCountUp);
        uiState.getValue().setFragmentActive(null);
        Log.i("upInc", incubator.getProfitability().getFormattedValue() +"$ " + incubator.getTapForce() + "F");

        uiUpdate();
    }

    public void toFragmentChange(Fragment fl) {
        uiState.getValue().setFragmentActive(fl);
        uiUpdate();

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
       this.Username = username;
    }

    public interface OnBitmapReadyListener {
        void onBitmapReady(Bitmap bitmap);
    }


    public void textureSet(Context context) {
        if (animal == null) return;
//        TextureLoader.loadTexture(context, animal.getSprite(), new OnBitmapReadyListener() {
        TextureLoader.loadTexture(context, new OnBitmapReadyListener() {

            @Override
            public void onBitmapReady(Bitmap bitmap) {
                if (animal == null) return;// вроде очень надо

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
        // Получаем доступ к базе данных
        MyDbHelper dbHelper = new MyDbHelper(cont);
//
//        MyDbHelper dbHelper = new MyDbHelper(getContext());
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (getUsername()==null) return;

//         public User(String name, String password,                                GameCurrency money, long strength,long toolForce,                                     GameCurrency toolProfit, GameCurrency toolUpCoastProfit,GameCurrency toolUpCoastForce,          long incubatorForce, GameCurrency incubatorProfit,GameCurrency incubatorUpCoastprofit, GameCurrency incubatorUpCoastForce, long countTapP, long countTapF, long countIncP, long countIncF ) {

        User user = new User(getUsername(),dbHelper.getPassword(getUsername()),player.getMoney(), clickEgg.getStrenght(), player.getTool().getTapForce(),player.getTool().getCoastProfit(),player.getTool().getCoastProfit(),player.getTool().getCoastForce(),incubator.getTapForce(),incubator.getProfitability(),incubator.getCoastProfit(),incubator.getCoastForce(),player.getTool().getUpCountProf(),player.getTool().getUpCountForce(),incubator.getUpCountProf(),incubator.getUpCountForce());
        dbHelper.updateUser(user);
    }

    public void loadAll(Context cont) {
        MyDbHelper dbHelper = new MyDbHelper(cont);
        if( getUsername()== null) return;
        User user = dbHelper.getUser(getUsername());
        if (user == null) return;

//        int[] buff = dataReader.readData();
//        //COLUMN_MONEY, COLUMN_TAP_TOOL_FORCE, COLUMN_TAP_TOOL_PROFIT,
//        //                    COLUMN_TAP_TOOL_COAST, COLUMN_INCUBATOR_FORCE, COLUMN_INCUBATOR_PROFIT, COLUMN_INCUBATOR_COAST
//        Log.e("чтение Бд", buff[0] + " " + buff[1] + " " + buff[2] + " " + buff[4] + " -_-");
         player.setMoney(user.getMoney());
////        player.setMoney(buff[0]);
        TapTool nt = new TapTool(user.getToolForce(),user.getToolProfit(),user.getToolUpCoastForce(),user.getToolUpCoastProfit(),user.getCountTapP(),user.getCountTapF());
////        TapTool nt = new TapTool(buff[1],buff[2],buff[3]);
        player.setTool(nt);
//
         incubator = new Incubator(user.getIncubatorForce(),user.getIncubatorProfit(),user.getIncubatorUpCoastForce(),user.getIncubatorProfit(),500, user.getCountIncP(), user.getCountIncF());
////        incubator = new Incubator(buff[4],buff[5],buff[6],500);
    }

    public void uiUpdate() {
        TapTool tt = player.getTool();

        if (animal != null) {
            uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), tt.getProfitability(), tt.getTapForce(), tt.getCoastProfit(), tt.getCoastForce(), uiState.getValue().getFragmentActive(), incubator.getProfitability(), incubator.getTapForce(), incubator.getCoastProfit(), incubator.getCoastForce(), animal.getId(), clickEgg.strengthChecker()));
        } else {
            uiState.setValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), tt.getProfitability(), tt.getTapForce(), tt.getCoastProfit(), tt.getCoastForce(), uiState.getValue().getFragmentActive(), incubator.getProfitability(), incubator.getTapForce(), incubator.getCoastProfit(), incubator.getCoastForce(), 0, clickEgg.strengthChecker()));
        }
    }

    public void uiUpdateAuto() {
        TapTool tt = player.getTool();
        if (animal != null) {
            uiState.postValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), tt.getProfitability(), tt.getTapForce(), tt.getCoastProfit(), tt.getCoastForce(), uiState.getValue().getFragmentActive(), incubator.getProfitability(), incubator.getTapForce(), incubator.getCoastProfit(), incubator.getCoastForce(), animal.getId(), clickEgg.strengthChecker()));
        } else {
            uiState.postValue(new UiState(player.getMoney(), clickEgg.getPercentStrenght(), tt.getProfitability(), tt.getTapForce(), tt.getCoastProfit(), tt.getCoastForce(), uiState.getValue().getFragmentActive(), incubator.getProfitability(), incubator.getTapForce(), incubator.getCoastProfit(), incubator.getCoastForce(), 0, clickEgg.strengthChecker()));
        }
    }


    public boolean solveAnimalSpawn(long eggStrength) {
        Random random = new Random();
        long buffer = (long) eggStrength / 20000;
        //TODO сделать зависимость от прочности
        boolean boolbuf = (random.nextInt(101) < 42);
        Log.e("solve", buffer + " " + boolbuf);
        return boolbuf;
    }

    public void tapRestartScene() {
        Log.i("restart", "__restart");

        animal = null;
        eggDefender = true;


        createAnimal();
        if (context != null) {
            textureSet(context);
        }
        Log.i("restart", "---до начисление монет ---");
        player.addMoney(player.getTool().getProfitability().simpleMultiplay(100));//временное решение
        Log.i("restart", "---монеты добавлены---");

        createEgg(getRandomStrenght());

    }

    private class AutoTapTask implements Runnable {
        @Override
        public void run() {
            Log.i("Autotap", "___-Autotap!");
            if (clickEgg != null && clickEgg.statusChecker() && animal == null) {
                Log.i("++++++++++++=onAutoTap", " RESTART( ");
                tapRestartScene();
            }

            if (clickEgg != null && !eggDefender) {
                Log.i("++++++++++++=onAutoTap", " 1 ");
                clickEgg.reduceStrength(incubator.getTapForce());
                player.addMoney(incubator.getProfitability());
            }
            if (player != null && (animal == null || !clickEgg.statusChecker())) {
                Log.i("++++++++++++=onAutoTap", " 2 ");
                player.addMoney(incubator.getProfitability());
            }
            if (player != null && (animal != null && clickEgg.statusChecker())) {
                Log.i("++++++++++++=onAutoTap", " 3 ");
                player.addMoney(incubator.getProfitability().simpleMultiplay(0.1));
                animal.reduceStrength(0.05);
            }

            if (animal != null && animal.statusChecker()) {
                Log.i("++++++++++++=onAutoTap", " 4 ");
                tapRestartScene();
            }
            uiUpdateAuto();
        }
    }

    public long getRandomStrenght() {
//        Log.e("сложность", player.getTool().getTapForce() + incubator.getTapForce() + "_ _");
//        if (player.getTool().getTapForce() + incubator.getTapForce() < 10) {
//            long max = (long) 10_000l;
//            return getRandomNumber(MIN_EGG_STRENGTH, max);
//        }
//        if (player.getTool().getTapForce() + incubator.getTapForce() < 250) {
//            long max = (long) Math.pow(MAX_EGG_STRENGTH, 1 / ((MAX_EGG_STRENGTH * 0.1 - player.getTool().getTapForce())));
//            return getRandomNumber(MIN_EGG_STRENGTH, 200_000l);
//        }
//        return getRandomNumber(MIN_EGG_STRENGTH, MAX_EGG_STRENGTH); //Todo - вернуть это на "релизе"
        return getRandomNumber(1_000, 3_000);

    }

    public static long getRandomNumber(long lowerBound, long upperBound) {
        Random random = new Random();
        Log.i("random", Math.random() + " " + Math.random() + " " + Math.random() + " " + Math.random() + " " + Math.random() + " " + Math.random());
        return (long) (Math.random() * (upperBound - lowerBound + 1) + lowerBound);
//        Math.random() * (max - min + 1) + min)
    }


}
