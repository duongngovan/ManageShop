package com.example.shopmanage.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmanage.R;
import com.example.shopmanage.dao.HoaDonDAO;
import com.example.shopmanage.model.HoaDon;
import com.example.shopmanage.model.NhanVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewHoaDon extends RecyclerView.Adapter<RecyclerViewHoaDon.ViewHolder> {

    private List<HoaDon> list;
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private HoaDonDAO hoaDonDAO;

    public RecyclerViewHoaDon(List<HoaDon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_hoadon,parent,false);
        hoaDonDAO = new HoaDonDAO(context);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hoaDonDAO.delete(list.get(viewHolder.getAdapterPosition()));
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
        HoaDon hoaDon = list.get(position);
        holder.txtID.setText("Mã Hóa Đơn :  "+hoaDon.getId());
        holder.txtNgay.setText("Ngày :  "+sdf.format(hoaDon.getNgay()));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public void filterList(ArrayList<HoaDon> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public TextView txtID;
        public TextView txtNgay;
        public ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview = itemView;
            txtID = itemView.findViewById(R.id.id_hd);
            txtNgay = itemView.findViewById(R.id.ngay_hd);
            delete = itemView.findViewById(R.id.delete_hd);
        }
    }
}
