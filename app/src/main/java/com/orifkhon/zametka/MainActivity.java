package com.orifkhon.zametka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Инициализировать переменная
    EditText editText;
    Button btApp,btReset;
    RecyclerView recyclerView;
    // для кнопка назад
    private long backPressedTime;
    private Toast backToast;


    List<MainData> dataList =new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.edit_text);
        btApp=findViewById(R.id.bt_zakhira);
        btReset=findViewById(R.id.bt_toza);
        recyclerView=findViewById(R.id.recycler_view);


        //Инициализировать базу данных
        database= RoomDB.getInstance(this);
        //Сохранить значение базы данных в списке данных
        dataList =database.mainDao().getAll();
        //Инициализировать  Linear Layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Инициализировать адаптер
        adapter =new MainAdapter(MainActivity.this,dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

        btApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //получить строку из текста редактирования
                String sText =editText.getText().toString().trim();
                //Проверить состояние
                if (!sText.equals("")){
                    //Когда текст не пустой
                    //Инициализировать main data
                    MainData data =new MainData();
                    //Установить текст на основные данные
                    data.setText(sText);
                    //Вставить текст в базу данных
                    database.mainDao().insert(data);
                    //Очистить edit text
                    editText.setText("");
                    //Уведомлять, когда данные вставлены
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Удалить все данные из базы данных
                database.mainDao().reset(dataList);
                //Уведомлять, когда все данные удалены
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
    //конец кнопка назад


    @Override
    public void onBackPressed() {

        if(backPressedTime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else {
            backToast=Toast.makeText(getBaseContext(),"Барои баромадан дубора пахш кунед.",Toast.LENGTH_SHORT);
            backToast.show();
        } backPressedTime=System.currentTimeMillis();
    }

}
