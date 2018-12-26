package com.adryanev.dicoding.mymoviecatalogue.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.adryanev.dicoding.mymoviecatalogue.adapters.SearchAdapter;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.movie.Movie;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;
import com.adryanev.dicoding.mymoviecatalogue.jobs.DailyReminderReceiver;
import com.adryanev.dicoding.mymoviecatalogue.jobs.ReleaseTodayReceiver;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.favourite.FavouriteFragment;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.now_playing.NowPlayingFragment;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.popular.PopularFragment;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.upcoming.UpcomingFragment;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.upcoming.UpcomingViewModel;
import com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail.MovieDetailActivity;
import com.adryanev.dicoding.mymoviecatalogue.utils.ActivityUtils;
import com.adryanev.dicoding.mymoviecatalogue.utils.ItemClickSupport;
import com.adryanev.dicoding.mymoviecatalogue.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
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
import android.widget.FrameLayout;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;
import com.adryanev.dicoding.mymoviecatalogue.adapters.TabPagerAdapter;
import com.adryanev.dicoding.mymoviecatalogue.ui.search.SearchActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    SearchView searchView;
    RecyclerView recyclerView;
    MainViewModel viewModel;
    SearchAdapter adapter;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    DailyReminderReceiver dailyReminderReceiver;
    UpcomingViewModel upcomingViewModel;
    ReleaseTodayReceiver releaseTodayReceiver;
    SessionManager manager;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        upcomingViewModel = ViewModelProviders.of(this).get(UpcomingViewModel.class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = findViewById(R.id.container_frame);
        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        recyclerView = findViewById(R.id.main_rv);
        adapter = new SearchAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        if(savedInstanceState == null){
            ActivityUtils.replaceFragment(getSupportFragmentManager(), new NowPlayingFragment(), R.id.container_frame);
            frameLayout.setVisibility(View.VISIBLE);
        }
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Intent i = new Intent(MainActivity.this, MovieDetailActivity.class);
                i.putExtra("movie_id",adapter.getSearches().get(position).getId().toString());
                startActivity(i);
            }
        });
        manager = new SessionManager(this);

        if(manager.isFirstLaunch()){
            dailyReminderReceiver = new DailyReminderReceiver();
            dailyReminderReceiver.setRepeatingAlarm(this);
            manager.setFirstLaunch(false);

        }
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        releaseTodayReceiver = new ReleaseTodayReceiver();

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
                    frameLayout.setVisibility(View.GONE);
                    bottomNavigationView.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    frameLayout.setVisibility(View.VISIBLE);
                    bottomNavigationView.setVisibility(View.VISIBLE);
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
    protected void onResume() {
        super.onResume();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String currentDate = dateFormat.format(date);

        upcomingViewModel.getUpcoming().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {

                for (Result rs: results){
                    if(rs.getReleaseDate().equals(currentDate)){
                        releaseTodayReceiver.setRepeatingAlarm(MainActivity.this, rs);
                        Timber.d("Release Today: %s",rs.getTitle());

                    }
                    else{
                        Timber.d("Release Not Today: %s (%s)",rs.getTitle(),rs.getReleaseDate());
                    }

                }
//                releaseTodayReceiver.setRepeatingAlarm(MainActivity.this, results.get(0));


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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Timber.d("MenuId: %d",id);
        switch (id){
            case R.id.action_favourite:
                ActivityUtils.replaceFragment(getSupportFragmentManager(),new FavouriteFragment(),R.id.container_frame);
                return true;
            case R.id.action_now_playing:
                ActivityUtils.replaceFragment(getSupportFragmentManager(),new NowPlayingFragment(),R.id.container_frame);
                return true;
            case R.id.action_popular:
                ActivityUtils.replaceFragment(getSupportFragmentManager(),new PopularFragment(),R.id.container_frame);
                return true;
            case R.id.action_upcoming:
                ActivityUtils.replaceFragment(getSupportFragmentManager(),new UpcomingFragment(),R.id.container_frame);
                return true;
        }
        return false;
    }
}
