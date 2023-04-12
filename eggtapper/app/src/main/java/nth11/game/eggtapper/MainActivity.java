package nth11.game.eggtapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    ShopFragment shopFragment = new ShopFragment();
    Player player;


    ProgressBar progressBar ;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //отключаем темную тему от греха подальше ( возможно временно)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

         player = new Player(0,new TapTool(5,1,0));
        Egg clickEgg = new Egg(1000);

        shopBtn = findViewById(R.id.shop_btn);
        frameShop = findViewById(R.id.fragment_shop);

//        setFragment(shopFragment);
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(shopFragment);
            }
        });

        TextView text_count = findViewById(R.id.text_count);
        text_count.setText(clickEgg.getStrenght()+" ");
        progressBar = findViewById(R.id.progress);
        progressBar.setProgress(clickEgg.getStrenght());

        TextView txt_money = findViewById(R.id.money_txt);
        txt_money.setText(getString(R.string.txt_money)+" "+ player.getMoney());


        ImageView egg = findViewById(R.id.egg);
        egg.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
            @Override
            public boolean onTouch(@SuppressLint("ClickableViewAccessibility") View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.animate().scaleX(0.97f).scaleY(0.95f).setDuration((long) 0);
                    clickEgg.reduceStrength(player.getTool().getTapForce());
                    player.addMoney(player.getTool().getProfitability());
                    text_count.setText(clickEgg.getPercentStrenght()+" %");
                    txt_money.setText(getString(R.string.txt_money)+" "+player.getMoney());
                    progressBar.setProgress(clickEgg.getPercentStrenght());


                } else if ((motionEvent.getAction() == MotionEvent.ACTION_UP)){
                    view.animate().scaleX(1f).scaleY(1f).setDuration((long) 0.1);
                }

                return true;
            }
        });


    }

    private Fragment currentFragment = null;

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (currentFragment == null) {
            ft.add(R.id.fragment_shop, fragment);
            currentFragment = fragment;
            shopFragment.setPlayer(player);
        } else if (currentFragment == fragment) {
            ft.remove(fragment);
            currentFragment = null;
        } else {
            ft.replace(R.id.fragment_shop, fragment);
            currentFragment = fragment;
        }
        ft.commit();
    }
}