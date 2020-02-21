package com.e.alfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.sample.ItemQuery;
import com.e.alfood.Adapter.ItemsAdapter;
import com.e.alfood.Model.ProductModel;
import com.e.alfood.databinding.ActivityItemsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import static com.e.alfood.BuildConfig.SELF_ORDERING_URL;


public class ItemsActivity extends AppCompatActivity {
    private static final String TAG = "ItemsActivity";
    public static int cart_count = 0;
    private ActivityItemsBinding itemsBinding;
    public static ArrayList<ProductModel> arrayList = new ArrayList<>();
    public static ArrayList<ItemQuery.Item> arrayQueryList = new ArrayList<ItemQuery.Item>();
    ItemsAdapter itemsAdapter;

    String branId = "69501393-b1e2-45f2-aac0-9def4a4b86c8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsBinding = DataBindingUtil.setContentView(this, R.layout.activity_items);
        hideNavigationBar();

        getApolloClient();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);

        itemsAdapter = new ItemsAdapter(arrayQueryList,this,this);
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));



        getItemQuery(branId);

        itemsBinding.txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemsActivity.this,MainActivity.class));
                finish();
            }
        });
        itemsBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemsActivity.this,MainActivity.class));
                finish();
            }
        });

        itemsBinding.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemsActivity.this,CartActivity.class));
                finish();
            }
        });

    }

    private ApolloClient getApolloClient(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(SELF_ORDERING_URL)
                .okHttpClient(okHttpClient)
                .build();
        return apolloClient;
    }


    public void getItemQuery(String branchId) {
        getApolloClient().query(ItemQuery.builder()
                .branchId(branchId)
                .build()).enqueue(new ApolloCall.Callback<ItemQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<ItemQuery.Data> response) {
                assert response.data() != null;
                //Log.d(TAG, "onResponse: " + response.data().items().size() + " Loaded");
                Log.d(TAG, "Response: " + response.data().items().size()+" Loaded");

                ItemsActivity.this.runOnUiThread(() -> {
                    //tvError.setVisibility(View.GONE);
                    //refreshContainer.setRefreshing(false);

                    if (response.data().items().size()>0){
                        itemsAdapter.setItems(response.data().items());
                        itemsBinding.rvItems.setAdapter(itemsAdapter);
                        itemsAdapter.notifyDataSetChanged();
                    }
                });


            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d(TAG, "Exception " + e.getMessage(), e);

                ItemsActivity.this.runOnUiThread(()->{
//                    binding.refreshContainer.setRefreshing(false);
//                    binding.tvError.setVisibility(View.VISIBLE);
//                    binding.tvError.setText(String.format("Error Occurred %s",
//                            e.getLocalizedMessage()));
                });
            }
        });
    }


    private void hideNavigationBar() {
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }

}
