package com.adryanev.dicoding.mymoviecatalogue.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.adryanev.dicoding.mymoviecatalogue.adapters.SearchAdapter;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail.MovieDetailActivity;
import com.adryanev.dicoding.mymoviecatalogue.utils.ItemClickSupport;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;
import com.adryanev.dicoding.mymoviecatalogue.adapters.TabPagerAdapter;
import com.adryanev.dicoding.mymoviecatalogue.ui.search.SearchActivity;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity{
    ApiInterface apiInterface;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    SearchView searchView;
    RecyclerView recyclerView;
    MainViewModel viewModel;
    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.main_view_pager);
        recyclerView = findViewById(R.id.main_rv);
        adapter = new SearchAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Intent i = new Intent(MainActivity.this, MovieDetailActivity.class);
                i.putExtra("movie_id",adapter.getSearches().get(position).getId().toString());
                startActivity(i);
            }
        });
        tabLayout = findViewById(R.id.main_tab);
        setupFragment();
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
    }



    private void setupFragment() {
        viewPager.setAdapter(new TabPagerAdapter(this, getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        if(item!= null){
            item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    recyclerView.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    viewPager.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    return true;
                }
            });

            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            if(searchManager!=null){
                searchView = (SearchView)item.getActionView();
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setQueryHint(getResources().getString(R.string.search_movies));
                searchView.setOnQueryTextListener(listener);
        }



        }
        return true;
    }
    private SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchData(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }

    };

    private void searchData(final String query) {
        viewModel.search(query,1).observe(this, new Observer<List<Search>>() {
            @Override
            public void onChanged(List<Search> searches) {
                Timber.d("Set data search: %s to adapter",query);
                adapter.setSearches(searches);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_search:

                searchView.setOnQueryTextListener(listener);
                return true;
            case R.id.action_setting:
               Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
               startActivity(i);

                return true;
        }

        return true;

    }



}
