package com.example.apkact8.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apkact8.MainActivity;
import com.example.apkact8.R;
import com.example.apkact8.database.DBController;
import com.example.apkact8.database.Teman;
import com.example.apkact8.MainActivity;
import com.example.apkact8.R;
import com.example.apkact8.database.DBController;
import com.example.apkact8.database.Teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {

    private ArrayList<Teman> listData;
    private Context control;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData=listData;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInf=LayoutInflater.from(parent.getContext());
        View view=layoutInf.inflate(R.layout.row_data_teman,parent,false);
        control= parent.getContext();
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TemanViewHolder holder, int position) {
        String nma,tlp,id;
        nma=listData.get(position).getNama();
        tlp=listData.get(position).getTelepon();
        id=listData.get(position).getId();
        DBController db=new DBController(control);

        holder.namaTxt.setTextColor(Color.GREEN);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nma);
        holder.teleponTxt.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                PopupMenu popupMenu=new PopupMenu(control,holder.cardku);
                popupMenu.inflate(R.menu.menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.mnEdit:
                                Intent i=new Intent(control,Teman.class);
                                i.putExtra("id",id);
                                i.putExtra("nama",nma);
                                i.putExtra("telepon",tlp);
                                control.startActivity(i);
                                break;
                            case R.id.mnHapus:
                                HashMap<String,String>values=new HashMap<>();
                                values.put("id",id);
                                db.DeleteData(values);
                                Intent j=new Intent(control, MainActivity.class);
                                control.startActivity(j);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listData !=null)? listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt,teleponTxt;

        public TemanViewHolder(View view) {
            super(view);
            cardku=(CardView) view.findViewById(R.id.kartu);
                    namaTxt=(TextView) view.findViewById(R.id.textNama);
                    teleponTxt=(TextView) view.findViewById(R.id.textTelepon);

            cardku.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return true;
                }
            });
        }
    }
}
