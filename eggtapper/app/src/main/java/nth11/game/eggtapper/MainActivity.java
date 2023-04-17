package nth11.game.eggtapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton shopBtn;
    private FrameLayout frameShop;


    ProgressBar progressBar;
    ViewModel model;
    FragmentTransaction ft;
    private Fragment shopFragment;
    private FragmentManager fragmentManager;

    private TextView textCount;
    private TextView txtMoney;
    private ImageView egg;
    private ImageView animal;
    private Bitmap bitmap;
    private Animal nowAnimal = new Animal(0);
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //отключаем темную тему от греха подальше ( возможно временно)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //создание этой вонючей ViewModel
        model = new ViewModelProvider(this).get(ViewModel.class);

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
//            Animal duck1 = new Animal(1); //TODO временно - перенести из view
//


            // update UI
            textCount.setText(uiState.getStrenght() + " ");
            progressBar.setProgress(uiState.getStrenght());
            txtMoney.setText(getString(R.string.txt_money) + " " + uiState.getMoney());
//
            bitmap = BitmapFactory.decodeResource(getResources(), model.getAnimal().getSprite());
            BitmapEditor.changeWhiteToBlueAsync(bitmap, new BitmapEditor.OnCompleteListener() {
                @Override
                public void onComplete(Bitmap bitmap) {
                    egg.setImageBitmap(bitmap);
                    egg.setScaleX(1);
                    egg.setScaleY(1);
                }
            });
            nowAnimal = model.getAnimal();


            egg.setImageResource(uiState.getEggTexture());
//
//            animal.setImageResource(model.getAnimal().getSprite()); // !

            setFragment(uiState.getShopActive());


        });


        shopBtn.setOnClickListener(view -> {
            boolean fl = model.getUiState().getValue().getShopActive();
            model.onShopClick(!fl);
        });


        egg.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                animateEgg(view, true);
                model.onTap();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                animateEgg(view, false);
                return false;
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

    //    private void animateEgg(View view, boolean isDown) {
//        float scaleX = isDown ? 0.96f : 1f;
//        float scaleY = isDown ? 0.95f : 1.05f;
//        view.animate().scaleX(scaleX).scaleY(scaleY).setDuration(isDown ? 0 : (long) 0.1);
//    }
    private void animateEgg(View view, boolean isDown) {
        float scaleX = isDown ? 1.96f : 1f;
        float scaleY = isDown ? 1.90f : 1.05f;
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


}