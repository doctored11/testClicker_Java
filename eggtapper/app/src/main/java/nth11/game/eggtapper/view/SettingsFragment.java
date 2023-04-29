package nth11.game.eggtapper.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nth11.game.eggtapper.R;
import nth11.game.eggtapper.viewModel.ViewModel;


public class SettingsFragment extends Fragment {
    private ViewModel model;
    private FragmentTransaction ft;
    private FragmentManager fragmentManager;

    public SettingsFragment(ViewModel model) {
        this.model = model;
    }

    public SettingsFragment() {
        // Конструктор без аргументов
    }

    public void setModel(ViewModel model) {
        this.model = model;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button regBtn = view.findViewById(R.id.button_reg_r);
        Fragment registrFragment = model.getRegFragment();

        Button authBtn = view.findViewById(R.id.button_auto);
        Fragment authFragment = model.getAuthorizationFragment();
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.toFragmentChange(registrFragment);
            }
        });
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.toFragmentChange(authFragment);
            }
        });

        return view;
    }

}