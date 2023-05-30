package nth11.game.eggtapper.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import nth11.game.eggtapper.R;
import nth11.game.eggtapper.model.currency.GameCurrency;
import nth11.game.eggtapper.viewModel.ViewModel;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShopFragment extends Fragment {

    private ViewModel model;

    public ShopFragment(ViewModel model) {
        this.model = model;
    }

    public Animation anim;

    public ShopFragment() {
        // Конструктор без аргументов
    }

    public void setModel(ViewModel model) {
        this.model = model;
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        LinearLayout toolUpProf = view.findViewById(R.id.toolUp_profit);
        TextView coastToolProf = view.findViewById(R.id.text_toolUp_coast_prof);
        GameCurrency buff = model.getUiState().getValue().getToolUpCoastProfit();
        buff.prefixUpdate();
        coastToolProf.setText(("$ = " + buff.getFormattedValue()));

        LinearLayout incUpProf = view.findViewById(R.id.incubatorUp_profit);
        TextView coastIncProf = view.findViewById(R.id.text_incubator_first_coast_prof);
        coastIncProf.setText(("$ = " + model.getUiState().getValue().getIncubatorUpCoastProfit().getFormattedValue()));

        LinearLayout toolUpForce = view.findViewById(R.id.toolUp_force);
        TextView coastToolForce = view.findViewById(R.id.text_toolUp_coast_force);
        coastToolForce.setText(("$ = " + model.getUiState().getValue().getToolUpCoastForce().getFormattedValue()));

        LinearLayout incUpForce = view.findViewById(R.id.incubatorUp_force);
        TextView coastIncForce = view.findViewById(R.id.text_incubator_first_coast_force);
        coastIncForce.setText(("$ = " + model.getUiState().getValue().getIncubatorUpCoastForce().getFormattedValue()));


        toolUpProf.setOnClickListener(v -> {
            model.onToolUpProf();
//анимацию нажатия
        });
        incUpProf.setOnClickListener(v -> model.onIncUpProf());

        toolUpForce.setOnClickListener(v -> model.onToolUpForce());
        incUpForce.setOnClickListener(v -> model.onIncUpForce());

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Подписываемся на изменения значений в модели
        model.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            // Обновляем текстовые поля
            GameCurrency buff = uiState.getToolUpCoastProfit();
            buff.prefixUpdate();
            ((TextView) view.findViewById(R.id.text_toolUp_coast_prof)).setText("$ = " + buff.getFormattedValue());
            ((TextView) view.findViewById(R.id.text_incubator_first_coast_prof)).setText("$ = " + uiState.getIncubatorUpCoastProfit().getFormattedValue());
            ((TextView) view.findViewById(R.id.text_toolUp_coast_force)).setText("$ = " + uiState.getToolUpCoastForce().getFormattedValue());
            ((TextView) view.findViewById(R.id.text_incubator_first_coast_force)).setText("$ = " + uiState.getIncubatorUpCoastForce().getFormattedValue());
        });

        //
        view.findViewById(R.id.toolUp_profit).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                onMyButtonClick(view.findViewById(R.id.toolUp_profit));
                model.onToolUpProf();
                return true;
            }
            return false;
        });



        view.findViewById(R.id.incubatorUp_profit).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                onMyButtonClick(view.findViewById(R.id.incubatorUp_profit));
                model.onIncUpProf();
                return true;
            }
            return false;
        });

//

        view.findViewById(R.id.toolUp_force).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                onMyButtonClick(view.findViewById(R.id.toolUp_force));
                model.onToolUpForce();
                return true;
            }
            return false;
        });


//


        view.findViewById(R.id.incubatorUp_force).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                onMyButtonClick(view.findViewById(R.id.incubatorUp_force));
                model.onIncUpForce();
                return true;
            }
            return false;
        });

    }

    public void onMyButtonClick(LinearLayout btn) {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.animation1);
        btn.startAnimation(anim);

    }



}
