package com.example.shopmanage.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanage.R;
import com.example.shopmanage.dao.LoaiDAO;
import com.example.shopmanage.model.Loai;
import com.example.shopmanage.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewLoai extends RecyclerView.Adapter<RecyclerViewLoai.ViewHolder> {

    private Context context;
    private List<Loai> list;
    private LoaiDAO db;
    private EditText edName,edMota;
    private Button btnBack,btnUpdate;

    public RecyclerViewLoai(Context context, List<Loai> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_loai,parent,false);
        db = new LoaiDAO(context);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = layoutInflater.inflate(R.layout.update_loai, parent, false);
                builder.setView(view1);
                edName = view1.findViewById(R.id.namesp_update);
                edMota = view1.findViewById(R.id.mota_update);


                btnBack = view1.findViewById(R.id.backbtn);
                btnUpdate = view1.findViewById(R.id.save_btn);


                edName.setText(list.get(viewHolder.getAdapterPosition()).getName());
                edMota.setText(list.get(viewHolder.getAdapterPosition()).getMoTa());


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edName.length() != 0 && edMota.length() != 0 ) {
                            list.get(viewHolder.getAdapterPosition()).setName(edName.getText() + "");
                            list.get(viewHolder.getAdapterPosition()).setMoTa(edMota.getText() + "");

                            db.update(list.get(viewHolder.getAdapterPosition()));
                            notifyDataSetChanged();
                            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(list.get(viewHolder.getAdapterPosition()));
                        list.remove(list.get(viewHolder.getAdapterPosition()));
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"Hủy bỏ thao tác thành công",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Loai loai = list.get(position);
        holder.txtId.setText("Mã:"+loai.getId());
        holder.txtName.setText("Tên:"+loai.getName());
        holder.txtLoai.setText("Mô tả: "+loai.getMoTa());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void filterList(ArrayList<Loai> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View itemview;
        public TextView txtId;
        public TextView txtName;
        public ImageButton delete;
        public TextView txtLoai;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            txtId = itemView.findViewById(R.id.id_loai);
            txtName = itemView.findViewById(R.id.name_loai);
            txtLoai = itemView.findViewById(R.id.mota_loai);
            delete = itemView.findViewById(R.id.delete_loai);
        }
    }

}
