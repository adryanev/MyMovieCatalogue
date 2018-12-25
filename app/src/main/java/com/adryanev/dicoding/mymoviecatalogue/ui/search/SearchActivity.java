package com.adryanev.dicoding.mymoviecatalogue.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseSearch;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;
import com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail.MovieDetailActivity;
import com.adryanev.dicoding.mymoviecatalogue.adapters.SearchAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SearchActivity extends AppCompatActivity {

    ApiInterface apiService;
    List<Search> movieList;
    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    EditText searchBar;
    Toolbar toolbar;
    ImageButton button;
    CoordinatorLayout search;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        prepareView();

    }

    private void prepareView() {
        apiService = RetrofitClient.getClient().create(ApiInterface.class);
        search = findViewById(R.id.search_coordinator);
        toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cari Film");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        nestedScrollView = findViewById(R.id.search_scroll_view);
        searchBar = findViewById(R.id.search_edit_text);
        button = findViewById(R.id.imageButton);
        recyclerView = (RecyclerView) findViewById(R.id.rv_search);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        searchBar.setFocusableInTouchMode(true);
        recyclerView.setLayoutManager(layoutManager);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    String search = searchBar.getText().toString();
                    hideKeybord();
                    prepareData(search);
                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchBar.getText().toString();
                hideKeybord();
                prepareData(query);
            }
        });
    }

    private void prepareData(String query) {
        Timber.d("Query = %s",query);
//        apiService.searchMovies(1,query).enqueue(new Callback<ResponseSearch>() {
//            @Override
//            public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
//                movieList = response.body().getResults();
//                SearchAdapter adapter = new SearchAdapter(SearchActivity.this, movieList, new SearchAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(Search item) {
//                        Timber.d(item.getId().toString());
//                        Intent i = new Intent(SearchActivity.this, MovieDetailActivity.class);
//                        i.putExtra("movie_id",item.getId().toString());
//                        searchBar.setText("");
//                        startActivity(i);
//                    }
//                });
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseSearch> call, Throwable t) {
//                Timber.e(t.getLocalizedMessage());
//            }
//        });
    }

    //region Methods keyboard
    private void showKeyboardRunOnUiThread() {
        if (!isFinishing()) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    if (inputMethodManager != null) {
                        inputMethodManager.toggleSoftInput(
                                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }

                }
            });
        }
    }

    private void hideKeybord() {
        if ( !isFinishing()) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(
                        search.getWindowToken(), 0);
            }
        }
    }
}
