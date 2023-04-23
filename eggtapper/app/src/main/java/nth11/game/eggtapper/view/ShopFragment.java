package nth11.game.eggtapper.view;

import android.os.Bundle;

import nth11.game.eggtapper.R;
import nth11.game.eggtapper.model.GameCurrency;
import nth11.game.eggtapper.viewModel.ViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ShopFragment extends Fragment {

    private ViewModel model;

    public ShopFragment(ViewModel model) {
        this.model = model;
    }

    public ShopFragment() {
        // Конструктор без аргументов
    }

    public void setModel(ViewModel model) {
        this.model = model;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        LinearLayout toolUpProf = view.findViewById(R.id.toolUp_profit);
        TextView coastToolProf = view.findViewById(R.id.text_toolUp_coast_prof);
        GameCurrency buff =model.getUiState().getValue().getToolUpCoastProfit();
        buff.prefixUpdate();
        coastToolProf.setText(("$ = "+buff.getFormattedValue()));

        LinearLayout incUpProf = view.findViewById(R.id.incubatorUp_profit);
        TextView coastIncProf = view.findViewById(R.id.text_incubator_first_coast_prof);
        coastIncProf.setText(("$ = "+ model.getUiState().getValue().getIncubatorUpCoastProfit().getFormattedValue()));

        LinearLayout toolUpForce = view.findViewById(R.id.toolUp_force);
        TextView coastToolForce = view.findViewById(R.id.text_toolUp_coast_force);
        coastToolForce.setText(("$ = "+ model.getUiState().getValue().getToolUpCoastForce().getFormattedValue()));

        LinearLayout incUpForce = view.findViewById(R.id.incubatorUp_force);
        TextView coastIncForce = view.findViewById(R.id.text_incubator_first_coast_force);
        coastIncForce.setText(("$ = "+ model.getUiState().getValue().getIncubatorUpCoastForce().getFormattedValue()));



        toolUpProf.setOnClickListener(v -> model.onToolUpProf());
        incUpProf.setOnClickListener(v -> model.onIncUpProf());

        toolUpForce.setOnClickListener(v -> model.onToolUpForce());
        incUpForce.setOnClickListener(v -> model.onIncUpForce());

        return view;
    }

}
