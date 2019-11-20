package com.example.shopmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanage.R;
import com.example.shopmanage.model.KhachHang;

import java.util.List;

public class RecyclerViewKhachHang extends RecyclerView.Adapter<RecyclerViewKhachHang.ViewHolder> {

    private List<KhachHang> list;
    private Context context;

    public RecyclerViewKhachHang(List<KhachHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_khachhang,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KhachHang khachHang = list.get(position);
        holder.txtName.setText(khachHang.getName());
        holder.txtSDT.setText(khachHang.getSdt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View itemview;
        public TextView txtName;
        public TextView txtSDT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            txtName = (TextView)itemView.findViewById(R.id.name_kh);
            txtSDT = (TextView)itemView.findViewById(R.id.sdt_kh);
        }
    }
}
