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
import com.example.shopmanage.dao.KhachHangDAO;
import com.example.shopmanage.model.KhachHang;
import com.example.shopmanage.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewKhachHang extends RecyclerView.Adapter<RecyclerViewKhachHang.ViewHolder> {

    private List<KhachHang> list;
    private Context context;
    private KhachHangDAO db;
    private EditText edName,edPhone,edtDiaChi;
    private Button btnBack,btnUpdate;

    public RecyclerViewKhachHang(List<KhachHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_khachhang,parent,false);
        db = new KhachHangDAO(context);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = layoutInflater.inflate(R.layout.update_khachhang, parent, false);
                builder.setView(view1);
                edName = view1.findViewById(R.id.namesp_update);
                edPhone = view1.findViewById(R.id.giabansp_update);
                edtDiaChi = view1.findViewById(R.id.baohanhsp_update);

                btnBack = view1.findViewById(R.id.backbtn);
                btnUpdate = view1.findViewById(R.id.save_btn);


                edName.setText(list.get(viewHolder.getAdapterPosition()).getName());
                edPhone.setText(list.get(viewHolder.getAdapterPosition()).getSdt());
                edtDiaChi.setText(list.get(viewHolder.getAdapterPosition()).getDiaChi());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edName.length() != 0 && edPhone.length() != 0 && edtDiaChi.length() != 0) {
                            list.get(viewHolder.getAdapterPosition()).setName(edName.getText() + "");
                            list.get(viewHolder.getAdapterPosition()).setSdt(edPhone.getText() + "");
                            list.get(viewHolder.getAdapterPosition()).setDiaChi(edtDiaChi.getText() + "");
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
        viewHolder.deletes.setOnClickListener(new View.OnClickListener() {
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
        KhachHang khachHang = list.get(position);
        holder.txtName.setText("Họ Và Tên :  "+khachHang.getName());
        holder.txtSDT.setText("Số Điện Thoại : "+khachHang.getSdt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void filterList(ArrayList<KhachHang> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View itemview;
        public TextView txtName;
        public TextView txtSDT;
        public ImageButton deletes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            txtName = (TextView)itemView.findViewById(R.id.name_kh);
            txtSDT = (TextView)itemView.findViewById(R.id.sdt_kh);
            deletes = itemView.findViewById(R.id.delete_kh);
        }
    }
}
