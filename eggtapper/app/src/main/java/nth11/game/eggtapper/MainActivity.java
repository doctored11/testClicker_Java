package nth11.game.eggtapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
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



        //Подписка на изменения uiState из viewModel
        model.getUiState().observe(this, uiState -> {

            // update UI
            textCount.setText(uiState.getStrenght() + " ");
            progressBar.setProgress(uiState.getStrenght());
            txtMoney.setText(getString(R.string.txt_money) + " " + uiState.getMoney());
            egg.setImageResource(uiState.getEggTexture());

            setFragment( uiState.getShopActive());


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
    private void animateEgg(View view, boolean isDown) {
        float scaleX = isDown ? 0.97f : 1f;
        float scaleY = isDown ? 0.95f : 1f;
        view.animate().scaleX(scaleX).scaleY(scaleY).setDuration(isDown ? 0 : (long) 0.1);
    }


}