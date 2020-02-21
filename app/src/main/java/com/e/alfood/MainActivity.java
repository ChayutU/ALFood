package com.e.alfood;

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
import com.apollographql.apollo.sample.CategoryQuery;
import com.e.alfood.Adapter.CategoryAdapter;
import com.e.alfood.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import static com.e.alfood.BuildConfig.SELF_ORDERING_URL;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<CategoryQuery.Item> gridArray = new ArrayList<CategoryQuery.Item>();
    CategoryAdapter categoryAdapter;
    String branId = "69501393-b1e2-45f2-aac0-9def4a4b86c8";
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        hideNavigationBar();

        getApolloClient();


        RecyclerView recyclerView = findViewById(R.id.rvCategory);

        categoryAdapter = new CategoryAdapter(gridArray, this, this);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        getCategoryQuery(branId);

    }

    private ApolloClient getApolloClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(SELF_ORDERING_URL)
                .okHttpClient(okHttpClient)
                .build();
        return apolloClient;
    }


    public void getCategoryQuery(String branchId) {
        getApolloClient().query(CategoryQuery.builder()
                .branchId(branchId)
                .build()).enqueue(new ApolloCall.Callback<CategoryQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<CategoryQuery.Data> response) {
                assert response.data() != null;
                Log.d(TAG, "Response: " + response.data().items().size() + " Loaded");

                MainActivity.this.runOnUiThread(() -> {
                    //tvError.setVisibility(View.GONE);
                    //refreshContainer.setRefreshing(false);

                    if (response.data().items().size() > 0) {
                        categoryAdapter.setItems(response.data().items());
                        mainBinding.rvCategory.setAdapter(categoryAdapter);
                        categoryAdapter.notifyDataSetChanged();
                    }
                });


            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d(TAG, "Exception " + e.getMessage(), e);

                MainActivity.this.runOnUiThread(() -> {
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
