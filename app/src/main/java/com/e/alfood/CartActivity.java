package com.e.alfood;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.e.alfood.Adapter.CartAdapter;
import com.e.alfood.Model.Item;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    TextView mealTotalText;
    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListView storedOrders = (ListView)findViewById(R.id.lvCart);

        items = getListItemData();
        mealTotalText = (TextView)findViewById(R.id.txt_total);
        CartAdapter adapter = new CartAdapter(this, items);

        storedOrders.setAdapter(adapter);
        adapter.registerDataSetObserver(observer);
    }

    public int calculateMealTotal(){
        int mealTotal = 0;
        for(Item item : items){
            mealTotal += item.Amount * item.Quantity;
        }
        return mealTotal;
    }

    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            setMealTotal();
        }
    };

    private ArrayList<Item> getListItemData(){
        ArrayList<Item> listViewItems = new ArrayList<Item>();
        listViewItems.add(new Item("1","Rice",30,"123"));
        listViewItems.add(new Item("2","Beans",40,"123"));
        listViewItems.add(new Item("3","Yam",60,"123"));
        listViewItems.add(new Item("4","Pizza",80,"123"));
        listViewItems.add(new Item("5","Fries",100,"123"));

        return listViewItems;
    }

    public void setMealTotal(){
        mealTotalText.setText(" "+ calculateMealTotal());
    }
}
