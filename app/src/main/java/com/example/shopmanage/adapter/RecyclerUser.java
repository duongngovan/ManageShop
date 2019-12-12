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
import com.example.shopmanage.dao.UserDAO;
import com.example.shopmanage.model.SignUpUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerUser extends RecyclerView.Adapter<RecyclerUser.ViewHolder> {
    private List<SignUpUser> list;
    private Context context;
    UserDAO db;
    EditText edName,edEmail,edPhone;
    Button btnBack,btnUpdate;

    public RecyclerUser(List<SignUpUser> mlist, Context mcontext) {
        this.list = mlist;
        this.context = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        db = new UserDAO(context);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = layoutInflater.inflate(R.layout.update_user, parent, false);
                builder.setView(view1);
                edName = view1.findViewById(R.id.namesp_update);
                edPhone = view1.findViewById(R.id.giabansp_update);
                edEmail = view1.findViewById(R.id.baohanhsp_update);

                btnBack = view1.findViewById(R.id.backbtn);
                btnUpdate = view1.findViewById(R.id.save_btn);


                edName.setText(list.get(viewHolder.getAdapterPosition()).getFullname());

                edEmail.setText(list.get(viewHolder.getAdapterPosition()).getEmail());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edName.length() != 0 && edPhone.length() != 0 && edEmail.length() != 0) {
                            list.get(viewHolder.getAdapterPosition()).setFullname(edName.getText() + "");

                            list.get(viewHolder.getAdapterPosition()).setEmail(edEmail.getText() + "");
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
        SignUpUser signUpUser = list.get(position);
        holder.names.setText("Họ Và Tên :"+signUpUser.getFullname());
        holder.phone.setText("Số Điện Thoại :"+signUpUser.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView names;
        public TextView phone;
        public ImageButton deletes;
        public CircleImageView circleImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            names = itemView.findViewById(R.id.hoten);
            phone = itemView.findViewById(R.id.sodienthoai);
            circleImageView = itemView.findViewById(R.id.circleimageview);
            deletes =  itemView.findViewById(R.id.delete);
        }
    }

}
