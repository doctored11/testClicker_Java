package nth11.game.eggtapper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nth11.game.eggtapper.model.MyDbHelper;
import nth11.game.eggtapper.model.PassUtils;
import nth11.game.eggtapper.model.PasswordCallback;
import nth11.game.eggtapper.viewModel.ViewModel;

public class AuthFragment extends Fragment {

    private ViewModel model;
    private FragmentTransaction ft;
    private FragmentManager fragmentManager;
    private MyDbHelper dbHelper;


    public AuthFragment(ViewModel model) {
        this.model = model;
    }

    public AuthFragment() {
        // Конструктор без аргументов
    }

    public void setModel(ViewModel model) {
        this.model = model;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        LinearLayout usersList = view.findViewById(R.id.users_list);

        MyDbHelper dbHelper = new MyDbHelper(getContext());
        model.saveAll(getContext());

        // Перебираем все записи и выводим их на экран
        int i = 0;
        List<String> names = dbHelper.getUsers();
        Log.i("NAMES_NAMES", names + " _________________)___________");
        while (i < names.size()) {
            String name = names.get(i);
            LinearLayout userLayout = new LinearLayout(getContext());

            userLayout.setId(View.generateViewId());
            userLayout.setBackgroundResource(android.R.color.background_light);
            userLayout.setPadding(24, 24, 24, 24);
            userLayout.setClickable(true);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 16);
            userLayout.setLayoutParams(layoutParams);
            userLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPasswordDialog(name);
                }
            });

//            TextView textView = new TextView(getContext());

            // Загружаем шрифт из ресурсов
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.videotype);

// Создаем TextView и применяем шрифт
            TextView textView = new TextView(getContext());
            textView.setTypeface(typeface);
            textView.setText(name);


            textView.setText(name);
            userLayout.addView(textView);

            usersList.addView(userLayout);
            i++;
        }
//

//

//
        return view;
    }

    private void showPasswordDialog(String name) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter password for " + name);

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String inputPassword = input.getText().toString().trim();
                String hashedInpPassword = PassUtils.hashPassword(inputPassword);
//                String passwordFromDb = dbHelper.getPassword(name);


                MyDbHelper dbHelper = new MyDbHelper(getContext());
                dbHelper.getPassword(name, new PasswordCallback() {    //todo
                    @Override
                    public void onPasswordReceived(String password) {

                        String passwordFromDb = password;
                        Log.i("AUTH_PASS BD_________", passwordFromDb+" !!!!!!!!!! Name "+ name);
                        Log.i("AUTH_PASS INPUT", hashedInpPassword+" !!!!!!!!!!");


                        if (passwordFromDb != null && passwordFromDb.equals(hashedInpPassword)) {
                            // Пароль верный, выполнить авторизацию
                            model.setUsername(name);
                            Toast.makeText(getContext(), "Password is correct", Toast.LENGTH_SHORT).show();
                            model.loadAll(getContext());

                        } else {
                            // Пароль неверный, показать сообщение об ошибке
                            Toast.makeText(getContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}