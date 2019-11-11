package com.example.shopmanage.nguoidung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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

    public RecyclerUser(List<SignUpUser> mlist, Context mcontext) {
        this.list = mlist;
        this.context = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        db = new UserDAO(context);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SignUpUser signUpUser = list.get(position);
        holder.names.setText(signUpUser.getFullname());
        holder.phone.setText(signUpUser.getPhone());

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
