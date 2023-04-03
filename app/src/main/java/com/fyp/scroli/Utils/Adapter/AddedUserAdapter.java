package com.fyp.scroli.Utils.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.scroli.Data.AppDatabase;
import com.fyp.scroli.Data.DatabaseClient;
import com.fyp.scroli.Data.Models.AddedUser;
import com.fyp.scroli.R;

public class AddedUserAdapter extends RecyclerView.Adapter<AddedUserAdapter.ViewHolder> {

    AddedUser[] list;
    Context ctx;
    refresher r;


    public AddedUserAdapter(AddedUser[] list,Context ctx) {
        this.list = list;
        this.ctx = ctx;
        this.r = (refresher) ctx;
    }

    @NonNull
    @Override
    public AddedUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddedUserAdapter.ViewHolder holder, int position) {

        holder.fname.setText(new StringBuilder().append(list[position].getId()).append(". ").toString());
        holder.lname.setText(list[position].getName());
        holder.about.setText(list[position].getJob());
        holder.image.setOnClickListener(view-> deleteuser(list[position]));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteuser(AddedUser user) {
        AppDatabase db = DatabaseClient.getInstance(ctx).getAppDB();
        db.dataAO().deleteAddedUser(user);
        notifyDataSetChanged();
        //r.refreshview();
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView fname;
        TextView lname;
        TextView about;

        public ViewHolder(View itemView) {
            super(itemView);

            image= (ImageView) itemView.findViewById(R.id.avatar);
            fname= (TextView) itemView.findViewById(R.id.f_name);
            lname= (TextView) itemView.findViewById(R.id.l_name);
            about = (TextView) itemView.findViewById(R.id.about);
        }
    }
    public interface refresher{
        void refreshview();
    }
}
