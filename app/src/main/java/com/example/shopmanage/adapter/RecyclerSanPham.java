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
import com.example.shopmanage.model.SanPham;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerSanPham extends RecyclerView.Adapter<RecyclerSanPham.ViewHolder> {

    private Context context;
    private List<SanPham> list;

    public RecyclerSanPham(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_sanpham,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        holder.txtId.setText(sanPham.getId());
        holder.txtName.setText(sanPham.getName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinhanh(), 0, sanPham.getHinhanh().length);
        holder.circleImageView_nv.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView txtId;
        public TextView txtName;
        public ImageButton deletes_nv;
        public CircleImageView circleImageView_nv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            txtId = itemView.findViewById(R.id.id_sp);
            txtName = itemView.findViewById(R.id.name_sp);
            circleImageView_nv = itemView.findViewById(R.id.circleimageview_sp);
            deletes_nv =  itemView.findViewById(R.id.delete_sp);
        }
    }
}
