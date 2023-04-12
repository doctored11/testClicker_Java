package nth11.game.eggtapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public  int eggStrength = 10000;
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //отключаем темную тему от греха подальше ( возможно временно)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        TextView text_count = findViewById(R.id.text_count);
        text_count.setText(eggStrength+" ");
        progressBar = findViewById(R.id.progress);
        progressBar.setProgress(eggStrength);

        ImageView egg = findViewById(R.id.egg);
        egg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.animate().scaleX(0.97f).scaleY(0.95f).setDuration((long) 0);
                    eggStrength-=1000;
                    text_count.setText(eggStrength+" ");
                    progressBar.setProgress(eggStrength);


                } else if ((motionEvent.getAction() == MotionEvent.ACTION_UP)){
                    view.animate().scaleX(1f).scaleY(1f).setDuration((long) 0.1);
                }

                return true;
            }
        });


    }
}