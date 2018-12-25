package com.adryanev.dicoding.mymoviecatalogue.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

public class ScrollChildLayout extends SwipeRefreshLayout {
    private View childScrollUp;
    public ScrollChildLayout(@NonNull Context context) {
        super(context);
    }

    public ScrollChildLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if(childScrollUp!=null){
            return childScrollUp.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }

    public void setChildScrollUp(View childScrollUp) {
        this.childScrollUp = childScrollUp;
    }
}
