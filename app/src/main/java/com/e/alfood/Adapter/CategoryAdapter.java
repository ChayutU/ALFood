package com.e.alfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.apollographql.apollo.sample.CategoryQuery;
import com.e.alfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryQuery.Item> {
    Context context;
    int layoutResourceId;
    private List<CategoryQuery.Item> menuList;
    private ArrayList<CategoryQuery.Item> items = new ArrayList<CategoryQuery.Item>();


    public CategoryAdapter(Context context, int layoutResourceId,
                                 ArrayList<CategoryQuery.Item> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.category, parent, false);

            holder = new RecordHolder();
            holder.iconThumbnail = row.findViewById(R.id.iconThumbnail);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }


        CategoryQuery.Item item = items.get(position);

        String imageUrl = item.image();
        Picasso.get().load(imageUrl).into(holder.iconThumbnail);

        /*
        String strImage = item.image().replaceFirst("^data:image/[^;]*;base64,?","");

        byte[] decodedString = Base64.decode(strImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.iconThumbnail.setImageBitmap(decodedByte);
         */



        return row;

    }

    static class RecordHolder {
        ImageView iconThumbnail;

    }
}