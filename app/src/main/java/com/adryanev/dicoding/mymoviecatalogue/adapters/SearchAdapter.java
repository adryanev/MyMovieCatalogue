package com.adryanev.dicoding.mymoviecatalogue.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.ui.search.SearchSuggestionViewHolder;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchSuggestionViewHolder> {
    private Context ctx;
    private List<Search> searches;

    public SearchAdapter(Context context){
        this.ctx = context;
    }
    @NonNull
    @Override
    public SearchSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_search_suggestion,viewGroup,false);
        return new SearchSuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestionViewHolder searchSuggestionViewHolder, int i) {
        searchSuggestionViewHolder.bind(searches.get(i));
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
        notifyDataSetChanged();
    }

    public List<Search> getSearches() {
        return searches;
    }

    @Override
    public int getItemCount() {
        if(searches == null){
            return 0;
        }
        return searches.size();
    }

}
