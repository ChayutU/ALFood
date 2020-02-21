package com.e.alfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.alfood.Model.Item;
import com.e.alfood.R;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Item> {
    private List<Item> list;
    private Context context;

    TextView currentFoodName,
            currentCost,
            quantityText;

    ImageView decrement,increment,removeMeal ;

    public CartAdapter(Context context, List<Item> myOrders) {
        super(context, 0, myOrders);
        this.list = myOrders;
        this.context = context;
    }


    public View getView(final int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_row,parent,false
            );
        }

        final Item currentFood = getItem(position);

        currentFoodName = (TextView)listItemView.findViewById(R.id.product_cart_code);
        currentCost = (TextView)listItemView.findViewById(R.id.product_cart_price);
        quantityText = (TextView)listItemView.findViewById(R.id.cart_product_quantity_tv);
        decrement = (ImageView)listItemView.findViewById(R.id.cart_decrement);
        increment = (ImageView)listItemView.findViewById(R.id.cart_increment);
        removeMeal = (ImageView)listItemView.findViewById(R.id.delete_item_from_cart);


        //Set the text of the meal, amount and quantity
        currentFoodName.setText(currentFood.Name);
        currentCost.setText(" "+ (currentFood.Amount * currentFood.Quantity));
        quantityText.setText("x "+ currentFood.Quantity);

        //OnClick listeners for all the buttons on the ListView Item
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFood.addToQuantity();
                quantityText.setText("x "+ currentFood.Quantity);
                currentCost.setText(" "+ (currentFood.Amount * currentFood.Quantity));
                notifyDataSetChanged();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFood.removeFromQuantity();
                quantityText.setText("x "+currentFood.Quantity);
                currentCost.setText(" "+ (currentFood.Amount * currentFood.Quantity));
                notifyDataSetChanged();
            }
        });

        removeMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return listItemView;
    }

}