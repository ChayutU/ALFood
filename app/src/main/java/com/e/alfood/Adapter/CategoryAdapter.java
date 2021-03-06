package com.e.alfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.sample.CategoryQuery;
import com.e.alfood.ItemsActivity;
import com.e.alfood.MainActivity;
import com.e.alfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryQuery.Item> items = Collections.emptyList();
    private Context context;

    public CategoryAdapter(ArrayList<CategoryQuery.Item> arrayList, MainActivity mainActivity, Context context) {
        this.context = context;
    }

    public void setItems(List<CategoryQuery.Item> items) {
        this.items = items;
        this.notifyDataSetChanged();
        Log.d(TAG, "Updated posts in adapter: " + items.size());
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.category, parent, false);

        return new CategoryAdapter.ViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        final CategoryQuery.Item item = this.items.get(position);
        holder.setItem(item);

        holder.iconThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ItemsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView iconThumbnail;
        private Context context;

        ViewHolder(View itemView, Context context) {
            super(itemView);
            iconThumbnail = itemView.findViewById(R.id.iconThumbnail);
            this.context = context;
        }

        void setItem(final CategoryQuery.Item item) {

            String imageUrl = item.image();
            Picasso.get().load(imageUrl).into(iconThumbnail);


        }
    }
}