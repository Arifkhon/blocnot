package com.orifkhon.zametka;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Определить имя таблицы
@Entity(tableName = "table_name")
public class MainData  implements Serializable {
    //Создать столбец идентификатора
    @PrimaryKey(autoGenerate = true)
    private int ID;


    //Создать столбец идентификатора
    @ColumnInfo(name = "text")
    private String text;
    //Сгенерировать геттер и сеттер

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
