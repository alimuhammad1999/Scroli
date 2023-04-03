package com.fyp.scroli.Utils.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class MyGenericAdapter<T> extends RecyclerView.Adapter<MyGenericAdapter<T>.ViewHolder> {
    private List<T> list;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
