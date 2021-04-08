package com.orifkhon.zametka;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Сущности базы данных приложения
@Database(entities = {MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //Создать экземпляр базы данных
    private static RoomDB database;
    //Определить имя базы данных
    private static String DATABASE_NAME ="database";
     public synchronized static RoomDB getInstance(Context context){
         //Проверить состояние
         if(database==null){
             // Когда база данных нулевая
             // Инициализируем базу данных
             database = Room.databaseBuilder(context.getApplicationContext()
             ,RoomDB.class,DATABASE_NAME)
                     .allowMainThreadQueries()
                     .fallbackToDestructiveMigration()
                     .build();

         }
         //База данных возврата
         return database;
     }
     //Создать  Dao
    public abstract MainDao mainDao();

}
