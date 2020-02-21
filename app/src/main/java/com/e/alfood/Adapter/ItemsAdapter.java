package com.e.alfood.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.sample.ItemQuery;
import com.e.alfood.ItemsActivity;
import com.e.alfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<ItemQuery.Item> items = Collections.emptyList();
    private Context context;

    public ItemsAdapter(ArrayList<ItemQuery.Item> arrayList, ItemsActivity itemsActivity, Context context){
        this.context = context;
    }

    public void setItems(List<ItemQuery.Item> items) {
        this.items = items;
        this.notifyDataSetChanged();
        Log.d(TAG, "Updated posts in adapter: " + items.size());
    }


    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.items, parent, false);

        return new ViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ItemQuery.Item item = this.items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        private Context context;
        TextView txtTitle;
        TextView txtPrice;
        ImageView iconThumbnail;

        ViewHolder(View itemView, Context context) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            iconThumbnail = (ImageView)itemView.findViewById(R.id.iconThumbnail);
            this.context = context;
        }

        void setItem(final ItemQuery.Item item) {
            txtTitle.setText(item.name());
            int y = (int) item.price();
            txtPrice.setText(y +" บาท");

            String imageUrl = item.image();
            Picasso.get().load(imageUrl).into(iconThumbnail);



        }
    }
}