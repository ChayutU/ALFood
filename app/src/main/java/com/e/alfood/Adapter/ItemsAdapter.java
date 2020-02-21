package com.e.alfood.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.sample.ItemQuery;
import com.e.alfood.CartActivity;
import com.e.alfood.ItemsActivity;
import com.e.alfood.Model.ProductImage;
import com.e.alfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.e.alfood.ItemsActivity.arrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    public static ArrayList<ProductImage> cartModels = new ArrayList<ProductImage>();
    public static ProductImage cartModel;
    private List<ItemQuery.Item> items = Collections.emptyList();
    private Context context;
    private CallBackUs mCallBackus;
    private HomeCallBack homeCallBack;

    public ItemsAdapter(ArrayList<ItemQuery.Item> arrayList, ItemsActivity itemsActivity, Context context) {
        this.context = context;
    }

    public void setItems(List<ItemQuery.Item> items) {
        this.items = items;
        this.notifyDataSetChanged();
        Log.d(TAG, "Updated posts in adapter: " + items.size());
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.items, parent, false);

        return new ItemViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final ItemQuery.Item item = this.items.get(position);
        holder.setItem(item);

        holder.iconThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog_item_quantity_update);
                final ImageView cartDecrement = dialog.findViewById(R.id.cart_decrement);
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
                        Toast.makeText(context, cartCounter[0] + "", Toast.LENGTH_SHORT).show();

                        cartModel = new ProductImage();
                        cartModel.setProductQuantity((cartCounter[0]));
                        cartModel.setProductPrice(arrayList.get(position).getPrice());
                        cartModel.setProductImage(arrayList.get(position).getImagePath());
                        cartModel.setTotalCash(cartCounter[0] *
                                Integer.parseInt(arrayList.get(position).getPrice()));
                        Log.d("pos", String.valueOf(position));

                        cartModels.add(cartModel);
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface CallBackUs {
        void addCartItemView();
    }

    // this interface creates for call the invalidateoptionmenu() for refresh the menu item
    public interface HomeCallBack {
        void updateCartCount(Context context);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {


        TextView txtTitle;
        TextView txtPrice;
        ImageView iconThumbnail;
        private Context context;

        ItemViewHolder(View itemView, Context context) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.tvPrice);
            iconThumbnail = itemView.findViewById(R.id.iconThumbnail);
            this.context = context;
        }

        void setItem(final ItemQuery.Item item) {
            txtTitle.setText(item.name());
            int y = (int) item.price();
            txtPrice.setText(y + " บาท");

            String imageUrl = item.image();
            Picasso.get().load(imageUrl).into(iconThumbnail);


        }
    }
}