package nth11.game.eggtapper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nth11.game.eggtapper.model.MyDbHelper;
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

        // Получаем доступ к базе данных
        dbHelper = new MyDbHelper(getContext());
//
//        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Формируем запрос для получения всех записей из таблицы пользователей
        String[] projection = {dbHelper.getColumnName()};
        String sortOrder = dbHelper.getColumnName() + " ASC";
        Cursor cursor = db.query(
                dbHelper.getTableName(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        // Перебираем все записи и выводим их на экран
        int i = 1;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.getColumnName()));
            LinearLayout userLayout = new LinearLayout(getContext());
            userLayout.setId(View.generateViewId());
            userLayout.setBackgroundResource(android.R.color.white);
            userLayout.setPadding(16, 16, 16, 16);
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

            TextView textView = new TextView(getContext());
            textView.setText(name);
            userLayout.addView(textView);

            usersList.addView(userLayout);
            i++;
        }
//

//
        cursor.close();
        dbHelper.close();
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

                String inputPassword = input.getText().toString();
                String passwordFromDb = dbHelper.getPassword(name);

                if (passwordFromDb != null && passwordFromDb.equals(inputPassword)) {
                    // Пароль верный, выполнить авторизацию Todo - реализовать!
                    model.setUsername(name);
                    Toast.makeText(getContext(), "Password is correct", Toast.LENGTH_SHORT).show();
//                    model.loadAll(getContext());

                } else {
                    // Пароль неверный, показать сообщение об ошибке
                    Toast.makeText(getContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();

                }
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