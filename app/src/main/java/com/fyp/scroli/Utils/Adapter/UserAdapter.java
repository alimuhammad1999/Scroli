package com.fyp.scroli.Utils.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.scroli.Data.Models.User;
import com.fyp.scroli.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> list;
    itemClickListner myitemclick;
    Context ctx;

    public UserAdapter(List<User> list, UserAdapter.itemClickListner myitemclick) {
        this.list = list;
        this.myitemclick = myitemclick;
        this.ctx = (Context) myitemclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.person_layout,parent,false);
        return new ViewHolder(v,myitemclick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fname.setText(list.get(position).getFirst_name());
        holder.lname.setText(list.get(position).getLast_name());
        holder.fname.setText(list.get(position).getFirst_name());

        Glide.with((Activity) ctx).load(list.get(position)
                        .getAvatar())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView image;
        protected TextView fname;
        protected TextView lname;
        itemClickListner myitemclick;
        public ViewHolder(@NonNull View itemView, UserAdapter.itemClickListner myitemclick) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.avatar);
            fname= (TextView) itemView.findViewById(R.id.f_name);
            lname= (TextView) itemView.findViewById(R.id.l_name);
            this.myitemclick = myitemclick;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            myitemclick.onItemClick(getAdapterPosition());
        }
    }
    public interface itemClickListner{
        void onItemClick(int position);
    }
}
