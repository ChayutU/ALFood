package com.e.alfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.sample.CategoryQuery;
import com.e.alfood.Adapter.CategoryAdapter;
import com.e.alfood.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import static com.e.alfood.BuildConfig.SELF_ORDERING_URL;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding mainBinding;


    ArrayList<CategoryQuery.Item> gridArray = new ArrayList<CategoryQuery.Item>();
    CategoryAdapter categoryAdapter;

    String branId = "69501393-b1e2-45f2-aac0-9def4a4b86c8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        hideNavigationBar();

        getApolloClient();
        setToolbar();

        mainBinding.gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,ItemsActivity.class));
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

    private void setToolbar() {

        //getSupportActionBar().setTitle("Self Ordering");
        //getSupportActionBar().setSubtitle("Tung Luang Gas");


        categoryAdapter = new CategoryAdapter(this,R.layout.activity_main,gridArray);
        mainBinding.gvCategory.setAdapter(categoryAdapter);

        getCategoryQuery(branId);
        //binding.refreshContainer.setOnRefreshListener(() -> getItemQuery(branId));


    }


    public void getCategoryQuery(String branchId) {
        getApolloClient().query(CategoryQuery.builder()
                .branchId(branchId)
                .build()).enqueue(new ApolloCall.Callback<CategoryQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<CategoryQuery.Data> response) {
                assert response.data() != null;
                Log.d(TAG, "Response: " + response.data().items().size()+" Loaded");

                MainActivity.this.runOnUiThread(() -> {
                    //tvError.setVisibility(View.GONE);
                    //refreshContainer.setRefreshing(false);

                    if (response.data().items().size()>0){
                        categoryAdapter.addAll(response.data().items());
                        mainBinding.gvCategory.setAdapter(categoryAdapter);
                        categoryAdapter.notifyDataSetChanged();
                    }
                });


            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d(TAG, "Exception " + e.getMessage(), e);

                MainActivity.this.runOnUiThread(()->{
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
