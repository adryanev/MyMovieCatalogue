package com.adryanev.dicoding.mymoviecatalogue.ui.main.upcoming;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adryanev.dicoding.mymoviecatalogue.R;

public class UpcomingViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView upcomingBackdrop;
    public TextView upcomingJudul;
    public TextView upcomingDate;
    public ImageButton upcomingShare;

    public UpcomingViewHolder(@NonNull View itemView) {
        super(itemView);
        upcomingBackdrop = itemView.findViewById(R.id.upcoming_backdrop);
        upcomingJudul = itemView.findViewById(R.id.upcoming_title);
        upcomingDate = itemView.findViewById(R.id.upcoming_year);
        upcomingShare = itemView.findViewById(R.id.upcoming_share);
    }
}
