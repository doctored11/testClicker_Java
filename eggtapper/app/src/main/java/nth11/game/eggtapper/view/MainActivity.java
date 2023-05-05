package nth11.game.eggtapper.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nth11.game.eggtapper.R;
import nth11.game.eggtapper.viewModel.UiState;
import nth11.game.eggtapper.viewModel.ViewModel;


///////////////////////////////////////////////////////////////////////////////////////////////////////
//надеюсь что это View - обработка кликов и отображение изменений (изменения  происходят в ViewModel)//
///////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity {
    private final float EGG_SCALE = 1f;
    private final float ANIMAL_SCALE = 2.2f;

    private Button shopBtn;
    private FloatingActionButton settingBtn;
    private FrameLayout frameBase;
    private ProgressBar progressBar;
    private ViewModel model;
    private FragmentTransaction ft;
    private Fragment shopFragment;
    private Fragment regFragment;
    private Fragment settingFragment;
    private FragmentManager fragmentManager;

    private TextView textCount;
    private TextView txtMoney;
    private TextView txtMoneyPerSec;
    private ImageView egg;

    private LinearLayout viewAria;
    private ImageView animal;
    private long lastClickTime = 0;


    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //отключаем темную тему от греха подальше ( возможно временно)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //создание этой вонючей ViewModel

        model = new ViewModelProvider(this).get(ViewModel.class);
        model.setContext(this);
        model.loadAll(this);


//        Log.e("Тест Валют: " , money3.getFormattedValue() + " ");


        fragmentManager = getSupportFragmentManager();
        shopFragment = model.getShopFragment();
        regFragment = model.getRegFragment();
        settingFragment = model.getSettingsFragment();

        textCount = findViewById(R.id.text_count);
        txtMoney = findViewById(R.id.money_txt);
        txtMoneyPerSec = findViewById(R.id.counter_per_second);

        shopBtn = findViewById(R.id.shop_btn);
        settingBtn = findViewById(R.id.settings_btn);
        frameBase = findViewById(R.id.fragment_base);

        progressBar = findViewById(R.id.progress);

        egg = findViewById(R.id.egg);
        animal = findViewById(R.id.animal);

        viewAria = findViewById(R.id.mainLayout);


        //Подписка на изменения uiState из viewModel
        model.getUiState().observe(this, uiState -> {

//
            updateText( uiState);
            egg.setImageResource(uiState.getEggTexture());
            if (model.getAnimal() != null) {   //проверку по хорошему во VM
                animal.setImageBitmap(model.getAnimal().getBitmap());
                animal.setScaleX(ANIMAL_SCALE);
                animal.setScaleY(ANIMAL_SCALE);
            } else {
                animal.setImageBitmap(null);
            }
            Log.i("!!!!!!!!!!!!!!!!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            setFragment(uiState.getFragmentActive());
        });


        shopBtn.setOnClickListener(view -> {
            Fragment fl = model.getUiState().getValue().getFragmentActive();
            Log.i("CLICK", (fl == null) + " ---");

            if (fl != null) {
                fl = null;

            } else {
                fl = shopFragment;
            }
//            if ( fl == null) return;
            model.toFragmentChange(fl);// !возможное отрицание
        });
        settingBtn.setOnClickListener(view -> {
            Fragment fl = model.getUiState().getValue().getFragmentActive();
            Log.i("CLICK", (fl == null) + " ---");

            if (fl != null) {
                fl = null;

            } else {
                fl = settingFragment;
            }
//            if ( fl == null) return;
            model.toFragmentChange(fl);// !возможное отрицание
        });
        viewAria.setOnClickListener(view -> {
            model.closeFragment();

        });

        // обработка касаний( мультитач) ну это же взаимодействие с пользователем - нормально во view наверное
        egg.setOnTouchListener((view, motionEvent) -> {
            int pointerCount = motionEvent.getPointerCount();
            for (int i = 0; i < pointerCount; i++) {
                int action = motionEvent.getActionMasked();
                int pointerId = motionEvent.getPointerId(i);
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        // нажатие пальца
                        long now = System.currentTimeMillis();
                        if (now - lastClickTime >= 10) { // Проверка времени между кликами

                            if (model.onAnimalTap()) {
                                viewAnimation(animal, true, ANIMAL_SCALE);
                            } else {
                                model.onTap();

                            }
                            viewAnimation(view, true, EGG_SCALE);
                        }
                        lastClickTime = now;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        // отпускание пальца
                        if (model.onAnimalTap()) {
                            viewAnimation(animal, false, ANIMAL_SCALE); // мб удалить
                        }
                        viewAnimation(view, false, EGG_SCALE);
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                }
            }
            return true;
        });


    }

    //это логика только отображения - оставляем во View?
    Fragment lastFragment;

    private  void setFragment(Fragment flag) {

        model.saveAll(this);
        ft = fragmentManager.beginTransaction();

        if (lastFragment != null) ft.remove(lastFragment);

        if (flag != null) {
            ft.replace(R.id.fragment_base, flag);
            lastFragment = flag;

        }
        else   if (lastFragment != null){
            ft.remove(lastFragment);
        }
        ft.commit();
    }
    public  void updateText(UiState uiState){
        textCount.setText(uiState.getStrenght() + " ");
        progressBar.setProgress((int) uiState.getStrenght());
        txtMoney.setText(getString(R.string.txt_money) + " " + uiState.getMoney().getFormattedValue());
        txtMoneyPerSec.setText(uiState.getIncubatorProfit().getFormattedValue() + " per sec");
    }

    private void viewAnimation(View view, boolean isDown, float size) {
        float scaleX =  (isDown ? 0.98f * size : size);
        float scaleY =  (isDown ? 0.96f * size : 1.02f * size);


        float translationY = isDown ? 20f : 0f; // расстояние, на которое яйцо опускается

        view.animate()
                .scaleX( scaleX)
                .scaleY(scaleY)
                .translationY(translationY)
                .setDuration(0) // задержка при опускании больше, чем при поднятии
                .withEndAction(() -> {
                    // обратная анимация, которая возвращает яйцо в исходное положение
                    view.animate()
                            .scaleX((1f * size))
                            .scaleY(1f * size)
                            .translationY(0f)
                            .setDuration(0);

                });
    }

    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onPause() {
        super.onPause();
        model.saveAll(this);
//        dbHelper = new BDHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

//        dbHelper = new BDHelper(this);
    }



}