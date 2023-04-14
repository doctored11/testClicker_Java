package nth11.game.eggtapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("!!!");
        setContentView(R.layout.activity_main);
        //отключаем темную тему от греха подальше ( возможно временно)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //создание этой вонючей ViewModel
        model = new ViewModelProvider(this).get(ViewModel.class);

        TextView text_count = findViewById(R.id.text_count);
        TextView txt_money = findViewById(R.id.money_txt);

        shopBtn = findViewById(R.id.shop_btn);
        frameShop = findViewById(R.id.fragment_shop);

        progressBar = findViewById(R.id.progress);

        ImageView egg = findViewById(R.id.egg);

        //Подписка на изменения uiState из viewModel
        model.getUiState().observe(this, uiState -> {

            // update UI
            text_count.setText(uiState.getStrenght() + " ");
            progressBar.setProgress(uiState.getStrenght());
            txt_money.setText(getString(R.string.txt_money) + " " + uiState.getMoney());

            setFragment(model.getShopFragment(), uiState.getShopActive());


        });

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fl = model.getUiState().getValue().getShopActive();
                model.onShopClick(!fl);

            }
        });
        model.AutoTap();



        egg.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
            @Override
            public boolean onTouch(@SuppressLint("ClickableViewAccessibility") View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.animate().scaleX(0.97f).scaleY(0.95f).setDuration((long) 0);
                    model.onTap();
                } else if ((motionEvent.getAction() == MotionEvent.ACTION_UP)) {
                    view.animate().scaleX(1f).scaleY(1f).setDuration((long) 0.1);
                    return false;
                }
                return true;
            }
        });
    }

    //это логика только отображения - оставляем во View?
    private void setFragment(Fragment fragment, Boolean flag) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        if (flag) {
            ft.add(R.id.fragment_shop, fragment);
        } else {
            ft.remove(fragment);
        }
        ft.commit();
    }
}