package com.example.shopmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanage.R;
import com.example.shopmanage.model.HoaDon;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerViewHoaDon extends RecyclerView.Adapter<RecyclerViewHoaDon.ViewHolder> {

    private List<HoaDon> list;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public RecyclerViewHoaDon(List<HoaDon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_hoadon,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon hoaDon = list.get(position);
        holder.txtID.setText(hoaDon.getId());
        holder.txtNgay.setText(sdf.format(hoaDon.getNgay()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public TextView txtID;
        public TextView txtNgay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            txtID = itemView.findViewById(R.id.id_hd);
            txtNgay = itemView.findViewById(R.id.ngay_hd);
        }
    }
}
