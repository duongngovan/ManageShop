package com.example.shopmanage.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanage.R;
import com.example.shopmanage.dao.NhanVienDAO;
import com.example.shopmanage.model.HoaDon;
import com.example.shopmanage.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerNhanVien extends RecyclerView.Adapter<RecyclerNhanVien.ViewHolder>  {
    private List<NhanVien> list;
    private List<NhanVien> arrSortNhanVien;

    private Context context;
    NhanVienDAO db;
    EditText edName,edSdt,edDiachi,edLuong;
    Button btnBack,btnUpdate;

    public RecyclerNhanVien(List<NhanVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_nhanvien,parent,false);
        db = new NhanVienDAO(context);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = layoutInflater.inflate(R.layout.update_nhanvien, parent, false);
                builder.setView(view1);
                edName = view1.findViewById(R.id.namesp_update);
                edSdt = view1.findViewById(R.id.giabansp_update);
                edDiachi = view1.findViewById(R.id.baohanhsp_update);
                edLuong = view1.findViewById(R.id.soluongsp_update);

                btnBack = view1.findViewById(R.id.backbtn);
                btnUpdate = view1.findViewById(R.id.save_btn);


                edName.setText(list.get(viewHolder.getAdapterPosition()).getName());
                edSdt.setText(list.get(viewHolder.getAdapterPosition()).getSdt());
                edDiachi.setText(list.get(viewHolder.getAdapterPosition()).getDiaChi());
                edLuong.setText(list.get(viewHolder.getAdapterPosition()).getLuong()+"");

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edName.length() != 0 && edSdt.length() != 0 && edDiachi.length() != 0 && edLuong.length() != 0) {
                            list.get(viewHolder.getAdapterPosition()).setName(edName.getText() + "");
                            list.get(viewHolder.getAdapterPosition()).setSdt(edSdt.getText() + "");
                            list.get(viewHolder.getAdapterPosition()).setDiaChi(edDiachi.getText() + "");
                            list.get(viewHolder.getAdapterPosition()).setLuong(Double.parseDouble(edLuong.getText().toString() +""));
                            db.updateHandler(list.get(viewHolder.getAdapterPosition()));
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
        viewHolder.deletes_nv.setOnClickListener(new View.OnClickListener() {
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
        NhanVien nhanVien = list.get(position);
        holder.names_nv.setText("Họ Và Tên :"+nhanVien.getName());
        holder.phone_nv.setText("Số Điện Thoại :"+nhanVien.getSdt());
        Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.getImage(), 0, nhanVien.getImage().length);
        holder.circleImageView_nv.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void filterList(ArrayList<NhanVien> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public TextView names_nv;
        public TextView phone_nv;
        public ImageButton deletes_nv;
        public ImageView circleImageView_nv;
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
