package nth11.game.eggtapper;

import android.app.Activity;
import android.os.Bundle;
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

        LinearLayout toolUp = view.findViewById(R.id.toolUp);
        TextView coastTool = view.findViewById(R.id.text_toolUp_coast);
        coastTool.setText(String.format("$ = %d", model.getUiState().getValue().getToolUpCoast()));

        LinearLayout incUp = view.findViewById(R.id.incubator_first_up);
        TextView coastInc = view.findViewById(R.id.text_incubator_first_coast);
        coastInc.setText(String.format("$ = %d", model.getUiState().getValue().getIncubatorUpCoast()));

        toolUp.setOnClickListener(v -> model.onToolUp());
        incUp.setOnClickListener(v -> model.onIncUp());

        return view;
    }

}
