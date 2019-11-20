package com.example.shopmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanage.R;
import com.example.shopmanage.model.Loai;

import java.util.List;

public class RecyclerViewLoai extends RecyclerView.Adapter<RecyclerViewLoai.ViewHolder> {

    private Context context;
    private List<Loai> list;

    public RecyclerViewLoai(Context context, List<Loai> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_loai,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Loai loai = list.get(position);
        holder.txtId.setText(loai.getId());
        holder.txtName.setText(loai.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View itemview;
        public TextView txtId;
        public TextView txtName;
        public ImageButton delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            txtId = itemView.findViewById(R.id.id_loai);
            txtName = itemView.findViewById(R.id.name_loai);
        }
    }
}
