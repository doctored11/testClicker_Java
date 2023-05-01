package nth11.game.eggtapper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nth11.game.eggtapper.model.MyDbHelper;
import nth11.game.eggtapper.model.User;
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

        // Открываем или создаем базу данных
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        db = dbHelper.getWritableDatabase();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ContentValues values = new ContentValues();
                    values.put(dbHelper.getColumnName(), name);
                    values.put(dbHelper.getColumnPassword(), password);

                    // Вставляем новую запись в таблицу
                    long newRowId = db.insert(dbHelper.getTableName(), null, values);

                    if (newRowId > -1) {

                        Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
//                          //в таблицу кладем начального пользователя.
                        //Todo перенести из этого "View" класса
                        MyDbHelper dbHelper = new MyDbHelper(getContext());
                        User user = new User(name, password);
                        dbHelper.updateUser(user);
                        model.loadAll(getContext());
                        model.setUsername(name);
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
        // Закрываем базу данных при уничтожении фрагмента
        db.close();
        super.onDestroy();
    }
}
