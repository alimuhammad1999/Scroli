package com.fyp.scroli.Utils.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fyp.scroli.Data.Models.Person;
import com.fyp.scroli.R;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Person> list;
    itemClickListner myitemclick;
    Context ctx;
    TestCallback callback;
    private static final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_ITEM = 0;

    public PersonAdapter(List<Person> list, Context ctx,TestCallback callback) {
        this.list = list;
        this.myitemclick =(itemClickListner) ctx;
        this.ctx = ctx;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_layout, parent, false);
            return new ViewHolder(view,myitemclick);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_layout, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ViewHolder) {
            populateItemRows((ViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            //showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }
    private void populateItemRows(ViewHolder holder, int position) {

        load_image(position,holder);
        holder.fname.setText(list.get(position).getFirst_name());
        holder.lname.setText(list.get(position).getLast_name());
        holder.fname.setText(list.get(position).getFirst_name());
        holder.about.setText(list.get(position).getAbout());
        holder.image.setOnClickListener(view -> load_image(position,holder));
    }
   /* private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }*/
    public void load_image(int position, ViewHolder holder){
        Glide.with(ctx).load(list.get(position)
                        .getAvatar()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        int h = holder.image.getHeight();
                        holder.card.setRadius((float) h/2);
                        return false;
                    }
                })
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.image);
    }

    public void add(List<Person> list){
        this.list.addAll(list);
        callback.testCallbackfunction();
    }

    @Override
    public int getItemCount() { return list.size();}

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView image;
        protected TextView fname;
        protected TextView lname;
        protected TextView about;
        protected CardView card;
        itemClickListner myitemclick;

        public ViewHolder(View itemView,itemClickListner myitemclick) {
            super(itemView);
            card  = itemView.findViewById(R.id.view2);
            image = itemView.findViewById(R.id.avatar);
            fname = itemView.findViewById(R.id.f_name);
            lname = itemView.findViewById(R.id.l_name);
            about = itemView.findViewById(R.id.about);
            this.myitemclick = myitemclick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            float alpha = (float) 20;
            float reverse = (float) -15;
            view.animate().setDuration(2).translationX(alpha).translationY(alpha).withEndAction(new Runnable() {
                @Override
                public void run() {
                    view.animate().setDuration(2).translationX(reverse).translationY(reverse);
                    myitemclick.onItemClick(getAdapterPosition());
                }
            });

        }
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
    public interface itemClickListner{
        void onItemClick(int position);
    }
    public interface TestCallback{
        void testCallbackfunction();
    }
}