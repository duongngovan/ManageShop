package com.example.shopmanage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanage.R;
import com.example.shopmanage.dao.NhanVienDAO;
import com.example.shopmanage.model.NhanVien;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerNhanVien extends RecyclerView.Adapter<RecyclerNhanVien.ViewHolder> {
    private List<NhanVien> list;
    private Context context;
    NhanVienDAO nhanVienDAO;

    public RecyclerNhanVien(List<NhanVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_nhanvien,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NhanVien nhanVien = list.get(position);
        holder.names_nv.setText(nhanVien.getName());
        holder.phone_nv.setText(nhanVien.getSdt());
        Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.getImage(), 0, nhanVien.getImage().length);
        holder.circleImageView_nv.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public TextView names_nv;
        public TextView phone_nv;
        public ImageButton deletes_nv;
        public CircleImageView circleImageView_nv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            names_nv = itemView.findViewById(R.id.hoten_nv);
            phone_nv = itemView.findViewById(R.id.sodienthoai_nv);
            circleImageView_nv = itemView.findViewById(R.id.circleimageview_nv);
            deletes_nv =  itemView.findViewById(R.id.delete_nv);

        }
    }
}
