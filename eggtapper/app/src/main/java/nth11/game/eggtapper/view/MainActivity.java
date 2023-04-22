package nth11.game.eggtapper.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nth11.game.eggtapper.R;
import nth11.game.eggtapper.model.GameCurrency;
import nth11.game.eggtapper.viewModel.ViewModel;


///////////////////////////////////////////////////////////////////////////////////////////////////////
//надеюсь что это View - обработка кликов и отображение изменений (изменения  происходят в ViewModel)//
///////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton shopBtn;
    private FrameLayout frameShop;
     private   ProgressBar progressBar;
    private ViewModel model;
    private  FragmentTransaction ft;
    private Fragment shopFragment;
    private FragmentManager fragmentManager;

    private TextView textCount;
    private TextView txtMoney;
    private ImageView egg;
    private ImageView animal;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //отключаем темную тему от греха подальше ( возможно временно)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //создание этой вонючей ViewModel

        model = new ViewModelProvider(this).get(ViewModel.class);
        model.setContext(this);
//        model.loadAll(this);





//        Log.e("Тест Валют: " , money3.getFormattedValue() + " ");



        fragmentManager = getSupportFragmentManager();
        shopFragment = model.getShopFragment();

        textCount = findViewById(R.id.text_count);
        txtMoney = findViewById(R.id.money_txt);

        shopBtn = findViewById(R.id.shop_btn);
        frameShop = findViewById(R.id.fragment_shop);

        progressBar = findViewById(R.id.progress);

        egg = findViewById(R.id.egg);
        animal = findViewById(R.id.animal);


        //Подписка на изменения uiState из viewModel
        model.getUiState().observe(this, uiState -> {

            textCount.setText(uiState.getStrenght() + " ");
            progressBar.setProgress((int) uiState.getStrenght());
            txtMoney.setText(getString(R.string.txt_money) + " " + uiState.getMoney().getFormattedValue());
            egg.setImageResource(uiState.getEggTexture());
            if (model.getAnimal() != null) {   //проверку по хорошему во VM
                animal.setImageBitmap(model.getAnimal().getBitmap());
                animal.setScaleX(1);
                animal.setScaleY(1);
            } else {animal.setImageBitmap(null);}
            setFragment(uiState.getShopActive());
        });




        shopBtn.setOnClickListener(view -> {
            boolean fl = model.getUiState().getValue().getShopActive();
            model.onShopClick(!fl);
        });


//        egg.setOnTouchListener((view, motionEvent) -> {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                model.onTap();
//                if(  model.onAnimalTap() ){
//                    animateEgg(animal, true);
//                }
//                animateEgg(view, true);
//
//
//            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                if(  model.onAnimalTap() ){
//                    animateEgg(animal, false);
//                }
//                animateEgg(view, false);
//                return false;
//            }
//            return true;
//        });

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
                        // Обработка нажатия пальца
                        model.onTap();
                        if (model.onAnimalTap()) {
                            animateEgg(animal, true);
                        }
                        animateEgg(view, true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        // Обработка отпускания пальца
                        if (model.onAnimalTap()) {
                            animateEgg(animal, false);
                        }
                        animateEgg(view, false);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Обработка перемещения пальца
                        // Можно использовать x, y для определения перемещения пальца
                        break;
                }
            }
            return true;
        });





    }

    //это логика только отображения - оставляем во View?
    private void setFragment(boolean flag) {
        ft = fragmentManager.beginTransaction();
        if (flag) {
            ft.replace(R.id.fragment_shop, shopFragment);
        } else {
            ft.remove(shopFragment);
        }
        ft.commit();
    }

    private void animateEgg(View view, boolean isDown) {
        float scaleX = isDown ? 0.96f : 1f;
        float scaleY = isDown ? 0.90f : 1.05f;
        float translationY = isDown ? 50f : 0f; // расстояние, на которое яйцо опускается

        view.animate()
                .scaleX(scaleX)
                .scaleY(scaleY)
                .translationY(translationY)
                .setDuration(0) // задержка при опускании больше, чем при поднятии
                .withEndAction(() -> {
                    // обратная анимация, которая возвращает яйцо в исходное положение
                    view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
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
//        model.saveAll(this);
//        dbHelper = new BDHelper(this);


    }

    @Override
    protected void onResume() {
        super.onResume();


//        dbHelper = new BDHelper(this);


    }




}