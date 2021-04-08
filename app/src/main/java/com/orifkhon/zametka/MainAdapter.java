package com.orifkhon.zametka;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
//Инициализировать переменную
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;
    //Создать конструктор
    public MainAdapter(Activity context,List<MainData> dataList){
        this.context= context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       //Инициализировать view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view) ;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//Инициализировать main data
        MainData data =dataList.get(position);
        //Инициализировать  database
        database =RoomDB.getInstance(context);

        //Установить текст в text view
        holder.textView.setText(data.getText());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Инициализировать  main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //Get id
                int sID =d.getID();
                //get text
                String sText =d.getText();

                //Создать диалог
                Dialog dialog =new Dialog(context);
                //Set content view
                dialog.setContentView(R.layout.dialog_update);
                //Инициализировать ширину
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Инициализировать высоту
                int height =WindowManager.LayoutParams.WRAP_CONTENT;
                //Set layout
                dialog.getWindow().setLayout(width,height);
                //Показать диалог
                dialog.show();

                //Инициализировать и присвоить переменную
                EditText editText =dialog.findViewById(R.id.edit_text);
                Button btUpdate =dialog.findViewById(R.id.bt_ivazkuni);

                //Установить текст на edit text
                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Закрыть диалоговое окно
                        dialog.dismiss();
                        //Получить обновленный текст от edit text
                        String uText = editText.getText().toString().trim();
                       // Обновление в базе данных
                        database.mainDao().update(sID,uText);
                        //Уведомлять об обновлении данных
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Инициализировать main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //Удалить текст из базы данных
                database.mainDao().delete(d);
                //Уведомлять об удалении данных
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position,dataList.size());

            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       //Инициализировать переменную
        TextView textView;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        //Назначить переменную
            textView =itemView.findViewById(R.id.text_view);
            btEdit =itemView.findViewById(R.id.bt_edit);
            btDelete =itemView.findViewById(R.id.bt_delete);
        }
    }
}
