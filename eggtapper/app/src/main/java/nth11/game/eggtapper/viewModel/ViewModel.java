package nth11.game.eggtapper.viewModel;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import nth11.game.eggtapper.AuthFragment;
import nth11.game.eggtapper.RegFragment;
import nth11.game.eggtapper.model.GetUserCallback;
import nth11.game.eggtapper.model.MyDbHelper;
import nth11.game.eggtapper.model.PasswordCallback;
import nth11.game.eggtapper.model.SoundPlayer;
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


    public static final GameCurrency BASE_TAPTOOL_FORCECLICK_PRICE = new GameCurrency(100, ' ');
    public static final GameCurrency BASE_TAPTOOL_PROFITCLICK_PRICE = new GameCurrency(200, ' ');
    ;
    public static final long BASE_TAPTOOL_FORCECLICK_VALUE = 1;
    public static final GameCurrency BASE_TAPTOOL_PROFITCLICK_VALUE = new GameCurrency(1, ' ');
    ;

    public static final double BASE_TAPTOOL_FORCECLICK_MULTIPLAER = 1.25;
    public static final double BASE_TAPTOOL_PROFITCLICK_MULTIPLAER = 1.14;


    public static final GameCurrency BASE_INCUBATOR_FORCE_PRICE = new GameCurrency(300, ' ');
    public static final GameCurrency BASE_INCUBATOR_PROFIT_PRICE = new GameCurrency(250, ' ');
    public static final long BASE_INCUBATOR_FORCE_VALUE = 0;
    public static final GameCurrency BASE_INCUBATOR_PROFIT_VALUE = new GameCurrency(0, ' ');
    public static final double BASE_INCUBATOR_FORCE_MULTIPLAER = 1.27;
    public static final double BASE_INCUBATOR_PROFIT_MULTIPLAER = 1.12;

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
    MyDbHelper dbHelper;


    public ViewModel() {


        createPlayer();

        createAnimal();

        createFragments();

        createIncubator();


        autoTap();
        createEgg(getRandomStrenght());

    }

    public ShopFragment getShopFragment() {
        return shopFragment;
    }

    public RegFragment getRegFragment() {
        return regFragment;
    }

    public AuthFragment getAuthorizationFragment() {
        return authorizationFragment;
    }

    public SettingsFragment getSettingsFragment() {
        return settingsFragment;
    }


    private final MutableLiveData<UiState> uiState = new MutableLiveData(new UiState(new GameCurrency(0, ' '), 0, new GameCurrency(0, ' '), 0, new GameCurrency(0, ' '), new GameCurrency(0, ' '), null, new GameCurrency(0, ' '), 0, new GameCurrency(0, ' '), new GameCurrency(0, ' ')));

    public LiveData<UiState> getUiState() {
        return uiState;
    }

    public void createPlayer() {

        player = new Player(new GameCurrency(600, ' '), new TapTool(BASE_TAPTOOL_FORCECLICK_VALUE, BASE_TAPTOOL_PROFITCLICK_VALUE, BASE_TAPTOOL_FORCECLICK_PRICE, BASE_TAPTOOL_PROFITCLICK_PRICE, 1, 1));
        if (context != null) loadAll(context);
    }

    public void createAnimal() {

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


    private SoundPlayer soundPlayer;

    public void onTap() {

        uiState.getValue().setFragmentActive(null);// - для быстрого  закрытия магазина и тд
        uiUpdate(); //

        soundPlayer = new SoundPlayer(context);
        soundPlayer.playSound("click");
        vibrate(context, 10);

        if (animal != null && clickEgg.statusChecker()) {

            return;
        }


        if (context != null && firstStart) {
            firstStart = false;
            textureSet(context);
        }

        if (clickEgg.statusChecker() && animal == null) {
            vibrate(context, 45);

            tapRestartScene();
        }

        if (!eggDefender) {
            clickEgg.reduceStrength(player.getTool().getTapForce());
        }

        player.addMoney(player.getTool().getProfitability());

        uiUpdate();
    }

    public void closeFragment() {
        uiState.getValue().setFragmentActive(null);
        uiUpdate();
    }

    public boolean onAnimalTap() {
        if (animal == null || !clickEgg.statusChecker()) return false;

        uiState.getValue().setFragmentActive(null);// - для быстрого  закрытия магазина и тд
        uiUpdate(); //


        soundPlayer = new SoundPlayer(context);
        soundPlayer.playSound("duck_quack");
        vibrate(context, 15);

        if (!animal.statusChecker()) animal.reduceStrength();

        if (animal.statusChecker()) {
            tapRestartScene();
        }


        player.addMoney(player.getTool().getProfitability().simpleMultiplay(10));

        uiUpdate();
        return true;
    }

    public void autoTap() { //
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
        uiUpdate();
    }

    public void onToolUpForce() {
        GameCurrency coast = player.getTool().getCoastForce();
        coast.prefixUpdate();
        if (!GameCurrency.compare(player.getMoney(), coast)) return;

        GameCurrency newForceCost = (BASE_TAPTOOL_FORCECLICK_PRICE.simpleMultiplay(Math.pow(BASE_TAPTOOL_FORCECLICK_MULTIPLAER, player.getTool().getUpCountForce())));
        long newForceValue = player.getTool().getTapForce() + 2;
        newForceCost.prefixUpdate();

        updateTool(coast, newForceValue, player.getTool().getProfitability(), newForceCost, player.getTool().getCoastProfit(), 1, 0);
        uiUpdate();
    }

    public void onIncUpProf() {
        GameCurrency coast = incubator.getCoastProfit();
        if (!GameCurrency.compare(player.getMoney(), coast)) return;

        GameCurrency newProfitCost = (BASE_INCUBATOR_PROFIT_PRICE.simpleMultiplay(Math.pow(BASE_INCUBATOR_PROFIT_MULTIPLAER, incubator.getUpCountProf())));
        GameCurrency newProfitValue = (incubator.getProfitability().add(new GameCurrency(3, ' ')));

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
//        uiState.getValue().setFragmentActive(null);

        uiUpdate();
    }

    private void updateIncubator(GameCurrency coast, long tapForce, GameCurrency profitability, GameCurrency coastForce, GameCurrency coastProfit, int timer, int forceCountUp, int profCountUp) {
        if (!GameCurrency.compare(player.getMoney(), coast)) return;
        player.spendMoney(coast);

        incubator = new Incubator(tapForce, profitability, coastForce, coastProfit, timer, incubator.getUpCountProf() + profCountUp, incubator.getUpCountForce() + forceCountUp);

        uiUpdate();
    }

    public void toFragmentChange(Fragment fl) {
        MyDbHelper dbHelper = new MyDbHelper(context);
        if (dbHelper.getRegisteredUserCount() < 1) { // если не зареган то не выпускаем из меню todo - мб ошибка
            Fragment registrFragment = getRegFragment();
            uiState.getValue().setFragmentActive(registrFragment);
            uiUpdate();
            return;
        }

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


    public synchronized void textureSet(Context context) {
        if (animal == null) return;

        TextureLoader.loadTexture(context, new OnBitmapReadyListener() {

            @Override
            public void onBitmapReady(Bitmap bitmap) {
                if (animal == null) return;// вроде очень надо

                Log.e("!!!!", "Покрас картинки");
                animal.setBitmap(bitmap);
                eggDefender = false;

            }
        });
    }


    public void setContext(Context context) {
        this.context = context;
        dbHelper = new MyDbHelper(context);

    }

    public Player getPlayer() {
        //временно для теста метод
        return player;
    }

    public synchronized   void saveAll(Context cont) {
        Log.i("Save","Start");

        // Получаем доступ к базе данных
        MyDbHelper dbHelper = new MyDbHelper(cont);
        Log.i("Save","1");
        if (getUsername() == null) return;
        Log.i("Save","2");

        dbHelper.getPassword(getUsername(), new PasswordCallback() {    //todo
            @Override
            public void onPasswordReceived(String password) {
                Log.i("Save","3");
                User user = new User(getUsername(), password, player.getMoney(), clickEgg.getStrenght(), player.getTool().getTapForce(), player.getTool().getProfitability(), player.getTool().getCoastProfit(), player.getTool().getCoastForce(), incubator.getTapForce(), incubator.getProfitability(), incubator.getCoastProfit(), incubator.getCoastForce(), player.getTool().getUpCountProf(), player.getTool().getUpCountForce(), incubator.getUpCountProf(), incubator.getUpCountForce()); //todo
                dbHelper.updateUser(user);
                Log.i("Save","3 " +  player.getMoney().getFormattedValue());
                dbHelper.saveLastLoggedInUser(getUsername());

            }
        });

//


//        loadAll(cont);
    }

    public void createBdDefUser(Context context) {
        MyDbHelper dbHelper = new MyDbHelper(context);
        dbHelper.addDefaultUser(); //todo
    }

    public synchronized void loadAll(Context cont) {
        Log.i("Load","Start");
        Log.i("Load","1");

        MyDbHelper dbHelper = new MyDbHelper(context);

        if (dbHelper.getUsers().size() < 1) { //todo - мб ошибка
            Fragment registrFragment = getRegFragment();
            toFragmentChange(registrFragment);
        }

        if (Username == "default") {
            setUsername(dbHelper.getLastLoggedInUser());
        }
        Log.i("Load","2");



        if (getUsername() == null) return;

        Log.i("Load","3");

        dbHelper.getUser(getUsername(), new GetUserCallback() {
            @Override
            public void onUserReceived(User user1) {
                //
                Log.i("Load","4");
                User user = user1;
                if (user == null) return;
                Log.i("Load","5");
                player.setMoney(user.getMoney());
                Log.i("Load","5 " +user.getMoney().getFormattedValue() );

//
                long lastLogoutTime = dbHelper.getLastLogoutTime();
                Log.i("Времмя выхода", lastLogoutTime + " ");
                int minutesSinceLogout = getMinutesSinceLastLogout(lastLogoutTime);
                Log.i("разница во времени (мин)", minutesSinceLogout + " ");
                GameCurrency afkProfit = (user.getIncubatorProfit().simpleMultiplay(120)).simpleMultiplay(minutesSinceLogout); //получаем профит в минуту и *minutesSinceLogout
                Log.i("Прибыльность инкубатора", user.getIncubatorProfit().getFormattedValue() + " ");
                afkProfit.prefixUpdate();
                Log.e("afkProfit", afkProfit.getFormattedValue() + " ");
                player.addMoney(afkProfit);
//

                Log.i("Load","6");
                TapTool nt = new TapTool(user.getToolForce(), user.getToolProfit(), user.getToolUpCoastForce(), user.getToolUpCoastProfit(), user.getCountTapP(), user.getCountTapF());
                player.setTool(nt);
                incubator = new Incubator(user.getIncubatorForce(), user.getIncubatorProfit(), user.getIncubatorUpCoastForce(), user.getIncubatorUpCoastProfit(), 500, user.getCountIncP(), user.getCountIncF());
                Log.i("Load","7");
            }
        });


//


    }


    public int getMinutesSinceLastLogout(long lastLogoutTime) {
        Calendar currentTime = Calendar.getInstance();
        long currentTimeMillis = currentTime.getTimeInMillis();

        long timeDifferenceMillis = currentTimeMillis - lastLogoutTime;
        int minutesDifference = (int) (timeDifferenceMillis / (60 * 1000));

        return minutesDifference;
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

        return boolbuf;
    }

    public void tapRestartScene() {


        animal = null;
        eggDefender = true;


        createAnimal();
        if (context != null) {
            textureSet(context);
        }

        player.addMoney(player.getTool().getProfitability().simpleMultiplay(100));//временное решение


        createEgg(getRandomStrenght());

    }

    private class AutoTapTask implements Runnable {
        @Override
        public void run() {

            if (clickEgg != null && clickEgg.statusChecker() && animal == null) {
                tapRestartScene();
            }

            if (clickEgg != null && !eggDefender) {

                clickEgg.reduceStrength(incubator.getTapForce());
                player.addMoney(incubator.getProfitability());
            }
            if (player != null && (animal == null || !clickEgg.statusChecker())) {

                player.addMoney(incubator.getProfitability());
            }
            if (player != null && (animal != null && clickEgg.statusChecker())) {

                player.addMoney(incubator.getProfitability().simpleMultiplay(0.1));
                animal.reduceStrength(0.05);
            }

            if (animal != null && animal.statusChecker()) {

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
        return (long) (Math.random() * (upperBound - lowerBound + 1) + lowerBound);
//        Math.random() * (max - min + 1) + min)
    }

    private Vibrator vibrator;

    private void vibrate(Context context, long time) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(time);
        }
    }


}
