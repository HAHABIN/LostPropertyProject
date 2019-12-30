package com.example.habin.lostpropertyproject.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.example.habin.lostpropertyproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * SwipeRefreshLayout + recyclerView
 */
public class SwipeRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    private View mEmptyView;

    private Context context;

    private LoadMoreView loadMoreView;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private OnLoadListener mListener;
    private DataObserver mDataObserver;
    private WrapperAdapter mWrapperAdapter;

    private boolean isEmptyViewShowing;
    private boolean isLoadingMore;

    private int lastVisiablePosition = 0;

    private List<View> mHeaderViews = new ArrayList<>();
    private List<View> mFootViews = new ArrayList<>();

    private boolean loadMoreing = false;
    private boolean loadMoreFinish = false;  // 数据加载完成不在加载，只有刷新之后再重置

    public SwipeRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setupSwipeRecyclerView();
    }

    /**
     * 支持添加头部界面,
     *
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderViews.add(view);
    }

    private void addFootView(View view) {
        //TODO...  添加更多底部视图
    }

    public void addLoadMoreView() {
        loadMoreView = new LoadMoreView(context);
        loadMoreView.setStatus(LoadMoreView.normal);
        mFootViews.add(loadMoreView);
    }

    private void setupSwipeRecyclerView() {

        isEmptyViewShowing = false;
        isLoadingMore = false;

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_swipe_recyclerview, this);
        mRefreshLayout = view.findViewById(R.id.sfl);
        mRefreshLayout.setOnRefreshListener(this);
        initRefreshStyle();

        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onRefresh() {
        loadMoreFinish = false;
        if (mListener != null) {
            mListener.onRefresh();
        }
        if (loadMoreView != null) loadMoreView.setStatus(LoadMoreView.normal);
    }

    private void startLoadMore() {
        loadMoreing = true;
        loadMoreView.setStatus(LoadMoreView.loading);
        mRefreshLayout.setEnabled(false);
        if (mListener != null) {
            mListener.onLoadMore();
        }
    }

    public void stopLoad() {
        if (isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        } else {
            loadMoreing = false;
            mRefreshLayout.setEnabled(true);
            loadMoreView.setStatus(LoadMoreView.normal);
        }
    }

    /**
     * 没有更多数据了
     */
    public void noMoreData() {
        loadMoreFinish = true;
        loadMoreView.setStatus(LoadMoreView.loadFinish);
    }

    boolean sIsScrolling = false;

    private OnScrollListener scrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //设置滚动时暂停加载图片,滚动时不加载图片
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                sIsScrolling = true;
                Glide.with(context).pauseRequests();
            } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (sIsScrolling) {
                    if (context instanceof Activity) {
                        if (!((Activity) context).isFinishing()) {
                            Glide.with(context).resumeRequests();
                        }
                    }
                }
                sIsScrolling = false;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                lastVisiablePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                lastVisiablePosition = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisiablePosition = findMax(into);
            }
            int childCount = mWrapperAdapter == null ? 0 : mWrapperAdapter.getItemCount();
            if (childCount > 1 && lastVisiablePosition == childCount - 1) {
                if (!isRefreshing() && !loadMoreing && loadMoreView != null && !loadMoreFinish) {
                    // 上拉到最后的cell 不在刷新和加载更多时, 才开始加载更多
                    startLoadMore();
                }
            }
//            int topRowVerticalPosition =
//                    (recyclerView.getChildCount() == 0) ?  0  : recyclerView.getChildAt(0).getTop();
//            mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
        }
    };

    private void initRefreshStyle() {

        mRefreshLayout.setProgressViewEndTarget(false, 200);

        //加载颜色是循环播放的，只要没有完成刷新就会一直循环
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_purple
        );
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /*********************************对外开放的api**************************************/
    /**
     * 是否正在刷新
     *
     * @return
     */
    public boolean isRefreshing() {
        return mRefreshLayout.isRefreshing();
    }

    /**
     * 是否正在显示空页面
     *
     * @return
     */
    public boolean isEmptyViewShowing() {
        return isEmptyViewShowing;
    }

    /**
     * @return swipeRefreshLayout
     */
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mRefreshLayout;
    }

    /**
     * @return RecyclerView
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * 设置加载监听器
     *
     * @param listener
     */
    public void setOnLoadListener(OnLoadListener listener) {
        mListener = listener;
    }

    /**
     * 设置空界面
     *
     * @param emptyView the view to be showing when the data set size is zero
     */
    public void setEmptyView(View emptyView) {
        if (mEmptyView != null) {
            removeView(mEmptyView);
        }
        this.mEmptyView = emptyView;

        if (mDataObserver != null) {
            mDataObserver.onChanged();
        }
    }

    /**
     * 设置recyclerView 适配器
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            if (mDataObserver == null) {
                mDataObserver = new DataObserver();
            }
            mWrapperAdapter = new WrapperAdapter(adapter);
            recyclerView.setAdapter(mWrapperAdapter);
            adapter.registerAdapterDataObserver(mDataObserver);
            mDataObserver.onChanged();
        }
    }

    /**
     * 设置自动刷新,需要在设置loadListener之后设置
     *
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {
        mRefreshLayout.setRefreshing(refreshing);
        if (refreshing && !isLoadingMore && mListener != null) {
            mListener.onRefresh();
        }
    }


    /*********************************对外开放的api**************************************/

    /**
     * 装饰者模式,支持recycler添加头部与底部UI
     */
    private class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public static final int TYPE_FOOTER = 10086;
        public static final int TYPE_HEADER = 10010;

        RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

        public WrapperAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
            this.mInnerAdapter = adapter;
        }

        public boolean isFooter(int position) {
            if (loadMoreView == null) return false;
            // 对于footer判断  ：不是头部，大于外部adapter的个数 才是footer
            if (!isHeader(position)) {
                if (mInnerAdapter.getItemCount() > 0) {
                    if (position >= (mInnerAdapter.getItemCount() + mHeaderViews.size()))
                        return true;
                } else
                    return true;
            }
            return false;
        }

        public boolean isHeader(int position) {
            return mHeaderViews.size() > 0 && position >= 0 && position < mHeaderViews.size();
        }

        public int getHeaderCount() {
            return mHeaderViews.size();
        }

        public int getFooterCount() {
            return mFootViews.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (TYPE_HEADER == viewType) {
                return new CustomViewHolder(mHeaderViews.get(0));
            } else if (TYPE_FOOTER == viewType) {
                return new CustomViewHolder(mFootViews.get(0));
            }
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (isHeader(position)) return;
            if (isFooter(position)) return;
            if (this.mInnerAdapter != null) {
                int rePosition = position - getHeaderCount();
                int itemCount = this.mInnerAdapter.getItemCount();
                if (rePosition < itemCount) {
                    this.mInnerAdapter.onBindViewHolder(holder, rePosition);
                    return;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {


            if (isHeader(position)) {
                return TYPE_HEADER;
            } else if (isFooter(position)) {
                return TYPE_FOOTER;
            } else {
                return mInnerAdapter.getItemViewType(position - mHeaderViews.size());
            }
        }

        public int getRealCount() {
            return mInnerAdapter != null ? mInnerAdapter.getItemCount() : 0;
        }

        @Override
        public int getItemCount() {
            int numer = getHeaderCount();
            numer += this.mInnerAdapter != null ? this.mInnerAdapter.getItemCount() : 0;
            numer += loadMoreView != null ? getFooterCount() : 0;
            return numer;
        }

        @Override
        public long getItemId(int position) {
            return mInnerAdapter.getItemId(position);
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && isFooter(holder.getLayoutPosition())) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            mInnerAdapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            if (holder.getItemViewType() == TYPE_HEADER) {
                super.onViewDetachedFromWindow(holder);
            } else if (holder.getItemViewType() == TYPE_FOOTER) {
                super.onViewDetachedFromWindow(holder);
            } else {
                this.mInnerAdapter.onViewDetachedFromWindow(holder);
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return isHeader(position) || isFooter(position) ? gridManager.getSpanCount() : 1;
                    }
                });
            }
            mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            mInnerAdapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
            return mInnerAdapter.onFailedToRecycleView(holder);
        }

        @Override
        public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            mInnerAdapter.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            mInnerAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            mInnerAdapter.onViewRecycled(holder);
        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * a inner class used to monitor the dataSet change
     * <p>
     * because wrapperAdapter do not know when wrapperAdapter.mInnerAdapter
     * <p>
     * dataSet changed, these method are final
     */
    class DataObserver extends RecyclerView.AdapterDataObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            showEmptyView();
            mWrapperAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            mWrapperAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            mWrapperAdapter.notifyItemRangeRemoved(fromPosition, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            mWrapperAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }

        private void showEmptyView() {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null && mEmptyView != null) {
                int count = 0;
                if (mFootViews.size() > 0) {
                    count++;
                }
                if (mHeaderViews.size() > 0) {
                    count++;
                }
                if (adapter.getItemCount() == count) {
                    isEmptyViewShowing = true;
                    if (mEmptyView.getParent() == null) {
                        LayoutParams params = new LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.CENTER;

                        addView(mEmptyView, params);
                    }
                    recyclerView.setVisibility(GONE);
                    mEmptyView.setVisibility(VISIBLE);
                } else {
                    isEmptyViewShowing = false;
                    mEmptyView.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                }
            }
        }

    }

    public interface OnLoadListener {

        void onRefresh();

        void onLoadMore();
    }
}
