package com.e.alfood.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.sample.ItemQuery;
import com.e.alfood.CartActivity;
import com.e.alfood.Model.Item;
import com.e.alfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends ArrayAdapter<ItemQuery.Item> {
    Context context;
    int layoutResourceId;
    private List<ItemQuery.Item> menuList;
    private ArrayList<ItemQuery.Item> items = new ArrayList<ItemQuery.Item>();
    private List<Item> itemList;


    public ItemsAdapter(Context context, int layoutResourceId,
                                 ArrayList<ItemQuery.Item> items) {
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
            row = inflater.inflate(R.layout.items, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = row.findViewById(R.id.txtTitle);
            holder.txtPrice = row.findViewById(R.id.tvPrice);
            holder.iconThumbnail = row.findViewById(R.id.iconThumbnail);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }


        ItemQuery.Item item = items.get(position);
        holder.txtTitle.setText(item.name());
        int y = (int) item.price();
        holder.txtPrice.setText(y +" บาท");

        String imageUrl = item.image();
        Picasso.get().load(imageUrl).into(holder.iconThumbnail);

        /*
        String strImage = item.image().replaceFirst("^data:image/[^;]*;base64,?","");

        byte[] decodedString = Base64.decode(strImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.iconThumbnail.setImageBitmap(decodedByte);
         */

        holder.iconThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog_item_quantity_update);
                ImageView cartDecrement = dialog.findViewById(R.id.cart_decrement);
                ImageView cartIncrement = dialog.findViewById(R.id.cart_increment);
                ImageView closeDialog = dialog.findViewById(R.id.imageView_close_dialog_cart);
                TextView updateQtyDialog = dialog.findViewById(R.id.update_quantity_dialog);
                TextView viewCartDialog = dialog.findViewById(R.id.view_cart_button_dialog);
                final TextView quantity = dialog.findViewById(R.id.cart_product_quantity_tv);
                quantity.setText(String.valueOf(0));
                final int[] cartCounter = {0};//{(arrayListImage.get(position).getStocks())};
                cartDecrement.setEnabled(false);
                cartDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cartCounter[0] == 1) {
                            Toast.makeText(context, "cant add less than 0", Toast.LENGTH_SHORT).show();
                        } else {
                            cartCounter[0] -= 1;
                            quantity.setText(String.valueOf(cartCounter[0]));
                        }

                    }
                });
                cartIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartDecrement.setEnabled(true);
                        cartCounter[0] += 1;
                        quantity.setText(String.valueOf(cartCounter[0]));


                    }
                });
                viewCartDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context.startActivity(new Intent(context, CartActivity.class));
                    }
                });
                dialog.show();
                updateQtyDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, String.valueOf(cartCounter[0]) + "", Toast.LENGTH_SHORT).show();

                        
                    }

                });
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        TextView txtPrice;
        ImageView iconThumbnail;

    }


}