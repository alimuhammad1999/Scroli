package com.fyp.scroli.Utils.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.scroli.Data.Person;
import com.fyp.scroli.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Person> list;
    //private List<Data> mlist;
    itemClickListner myitemclick;
    Context ctx;

    public MyAdapter(List<Person> list, itemClickListner myitemclick,Context ctx) {
        this.list = list;
        this.myitemclick = myitemclick;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(v,myitemclick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        //new DownloadImageTask(holder.image).execute(list.get(position).getAvatar());
        holder.fname.setText(list.get(position).getFirst_name());
        holder.lname.setText(list.get(position).getLast_name());
        holder.fname.setText(list.get(position).getFirst_name());

        Glide.with(ctx).load(list.get(position)
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

        public ViewHolder(View itemView,itemClickListner myitemclick) {
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