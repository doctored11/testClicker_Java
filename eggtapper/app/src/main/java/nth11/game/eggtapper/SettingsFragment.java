package nth11.game.eggtapper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nth11.game.eggtapper.viewModel.ViewModel;


public class SettingsFragment extends Fragment {
    private ViewModel model;


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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}