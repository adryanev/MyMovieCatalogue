package com.adryanev.dicoding.mymoviecatalogue.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.adryanev.dicoding.mymoviecatalogue.R;

import org.jetbrains.annotations.NotNull;

public class ItemClickSupport {
    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View view);
    }
    public interface OnItemLongClickListener{
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View view);
    }
    private final RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onItemClickListener !=null){
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                onItemClickListener.onItemClicked(recyclerView,holder.getAdapterPosition(),v);
            }
        }
    };
    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if(onItemLongClickListener !=null){
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                return onItemLongClickListener.onItemLongClicked(recyclerView,holder.getAdapterPosition(),v);
            }
            return false;
        }
    };
    private ItemClickSupport(@NotNull RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        recyclerView.setTag(R.id.item_click_support);
        recyclerView.addOnChildAttachStateChangeListener(attachListener);
    }
    private RecyclerView.OnChildAttachStateChangeListener attachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if(onItemClickListener != null){
                view.setOnClickListener(onClickListener);
            }
            if(onItemLongClickListener!=null){
                view.setOnLongClickListener(onLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };

    public static ItemClickSupport addTo(@NonNull RecyclerView recyclerView){
        ItemClickSupport itemClickSupport = (ItemClickSupport) recyclerView.getTag(R.id.item_click_support);
        if(itemClickSupport == null){
            itemClickSupport = new ItemClickSupport(recyclerView);
        }
        return itemClickSupport;
    }

    public static ItemClickSupport removeFrom(@NonNull RecyclerView recyclerView){
        ItemClickSupport itemClickSupport = (ItemClickSupport) recyclerView.getTag(R.id.item_click_support);
        if(itemClickSupport !=null){
            itemClickSupport.detach(recyclerView);
        }
        return itemClickSupport;
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        onItemLongClickListener = longClickListener;
    }

    private void detach(RecyclerView recyclerView) {
    }
}
