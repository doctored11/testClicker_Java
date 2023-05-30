package nth11.game.eggtapper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nth11.game.eggtapper.model.bd.MyDbHelper;
import nth11.game.eggtapper.model.bd.PassUtils;
import nth11.game.eggtapper.model.bd.User;
import nth11.game.eggtapper.viewModel.ViewModel;


public class RegFragment extends Fragment {

    private EditText nameEditText, passwordEditText;
    private Button registerButton;
    private SQLiteDatabase db;

    private ViewModel model;
    private FragmentTransaction ft;
    private FragmentManager fragmentManager;

    public RegFragment(ViewModel model) {
        this.model = model;
    }

    public RegFragment() {
        // Конструктор без аргументов
    }

    public void setModel(ViewModel model) {
        this.model = model;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        nameEditText = view.findViewById(R.id.editTextTextPersonName);
        passwordEditText = view.findViewById(R.id.editTextTextPassword);
        registerButton = view.findViewById(R.id.button);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                model.saveAll(getContext());
                String name = nameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String hashedPassword = PassUtils.hashPassword(password);
                Log.i("REG_PASS INPUT", hashedPassword+" !!!!!!!!!! Name "+name );

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {


                    MyDbHelper dbHelper = new MyDbHelper(getContext());


                    // втавляем запись в таблицу
                    if (!dbHelper.isUsernameExists(name)) {
                        User user = new User(name,hashedPassword);
                            dbHelper.addUser(user);
                        if (!dbHelper.isUsernameExists(name)) Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();

                        model.setUsername(name);
                        model.loadAll(getContext());
                        model.saveAll(getContext());
                    } else {
                        Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please enter name and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
