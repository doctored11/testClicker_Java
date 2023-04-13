package nth11.game.eggtapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ShopFragment extends Fragment {
    ViewModel model;
    private Player player;

    public ShopFragment(ViewModel model) {
        this.model = model;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        LinearLayout toolUp = view.findViewById(R.id.toolUp);
        toolUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.onToolUp();
            }
        });
        return view;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }


}
