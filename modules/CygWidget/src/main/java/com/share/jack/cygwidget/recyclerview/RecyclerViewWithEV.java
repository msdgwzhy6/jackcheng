package com.share.jack.cygwidget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.jack.mc.cyg.cygtools.util.CygLog;
import com.jack.mc.cyg.cygtools.util.CygObject;

/**
 *
 */

public class RecyclerViewWithEV extends RecyclerView {

    private View emptyView;

    public RecyclerViewWithEV(Context context) {
        super(context);
    }

    public RecyclerViewWithEV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWithEV(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 数据观察者AdapterDataObserver用来监听数据的变化
     */
    private final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkEmptyView();
        }
    };

    private void checkEmptyView() {
        if (CygObject.isNullObject(emptyView) || CygObject.isNullObject(getAdapter())) {
            CygLog.error("null emptyView");
            return;
        }
        final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
        emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
        setVisibility(emptyViewVisible ? GONE : VISIBLE);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        checkEmptyView();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkEmptyView();
    }
}