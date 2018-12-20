package com.adryanev.dicoding.mymoviecatalogue.views.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.views.search.viewholder.SearchSuggestionViewHolder;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchSuggestionViewHolder> {
    OnItemClickListener listener;
    private Context ctx;
    private List<Search> searches;

    public SearchAdapter(Context context, List<Search> list, OnItemClickListener listener){
        this.ctx = context;
        this.searches = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public SearchSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_search_suggestion,viewGroup,false);
        return new SearchSuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestionViewHolder searchSuggestionViewHolder, int i) {
        searchSuggestionViewHolder.bind(searches.get(i),listener);
    }

    @Override
    public int getItemCount() {
        if(searches == null){
            return 0;
        }
        return searches.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Search item);
    }


}
