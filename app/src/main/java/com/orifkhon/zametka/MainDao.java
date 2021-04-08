package com.orifkhon.zametka;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
//Вставить запрос
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);
    //Удалить запрос
    @Delete
    void delete(MainData mainData);
    //Удалить весь запрос
    @Delete
    void reset(List<MainData> mainDao);

    // Обновить запрос
    @Query("UPDATE table_name SET text =:sText WHERE ID =:sID")
    void update(int sID,String sText);

    //Получить все данные запрос
    @Query("SELECT * FROM TABLE_NAME")
    List<MainData> getAll();


}

