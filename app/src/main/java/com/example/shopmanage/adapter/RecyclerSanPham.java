package com.example.shopmanage.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanage.R;
import com.example.shopmanage.dao.SanPhamDAO;
import com.example.shopmanage.model.NhanVien;
import com.example.shopmanage.model.SanPham;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerSanPham extends RecyclerView.Adapter<RecyclerSanPham.ViewHolder> {

    private Context context;
    private List<SanPham> list;
    private SanPhamDAO sanPhamDAO;
    private EditText edName,edGiaBan,edBaoHanh,edSoLuong;
    private Button btnUpdate,btnBack;

    public RecyclerSanPham(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_sanpham,parent,false);
        sanPhamDAO = new SanPhamDAO(context);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = layoutInflater.inflate(R.layout.update_nhanvien, parent, false);
                builder.setView(view1);
                edName = view1.findViewById(R.id.namesp_update);
                edGiaBan = view1.findViewById(R.id.giabansp_update);
                edBaoHanh = view1.findViewById(R.id.baohanhsp_update);
                edSoLuong = view1.findViewById(R.id.soluongsp_update);

                btnBack = view1.findViewById(R.id.backbtn);
                btnUpdate = view1.findViewById(R.id.save_btn);


                edName.setText(list.get(viewHolder.getAdapterPosition()).getName());
                edGiaBan.setText(list.get(viewHolder.getAdapterPosition()).getGiaBan()+"");
                edBaoHanh.setText(list.get(viewHolder.getAdapterPosition()).getBh());
                edSoLuong.setText(list.get(viewHolder.getAdapterPosition()).getSoLuong()+"");

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edName.length() != 0 && edGiaBan.length() != 0 && edBaoHanh.length() != 0 && edSoLuong.length() != 0) {
                            list.get(viewHolder.getAdapterPosition()).setName(edName.getText() + "");
                            list.get(viewHolder.getAdapterPosition()).setGiaBan(Double.parseDouble(edGiaBan.getText()+""));
                            list.get(viewHolder.getAdapterPosition()).setBh(edBaoHanh.getText() + "");
                            list.get(viewHolder.getAdapterPosition()).setSoLuong(Integer.parseInt(edSoLuong.getText().toString() +""));
                            sanPhamDAO.update(list.get(viewHolder.getAdapterPosition()));
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
                        sanPhamDAO.delete(list.get(viewHolder.getAdapterPosition()));
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
        SanPham sanPham = list.get(position);

        holder.txtName.setText("Tên :  "+sanPham.getName());
        holder.txtGiaban.setText("Giá bán :  "+(double) sanPham.getGiaBan());
        holder.txtSoluong.setText("Số lượng :  "+sanPham.getSoLuong());
        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinhanh(), 0, sanPham.getHinhanh().length);
        holder.circleImageView_nv.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void filterList(ArrayList<SanPham> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView txtName,txtGiaban,txtSoluong;

        public ImageButton deletes_nv;
        public ImageView circleImageView_nv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            txtName = itemView.findViewById(R.id.name_sanpham);
            txtGiaban = itemView.findViewById(R.id.giaban_sanpham);
            txtSoluong = itemView.findViewById(R.id.soluong_sanpham);
            circleImageView_nv = itemView.findViewById(R.id.circleimageview_sp);
            deletes_nv =  itemView.findViewById(R.id.delete_sp);
        }
    }
}
