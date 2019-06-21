package com.flyersoft.discuss.shuhuang;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.flyersoft.discuss.BaseFragment;
import com.flyersoft.discuss.R;
import com.flyersoft.discuss.http.MRManager;
import com.flyersoft.discuss.http.body.BaseRequest;
import com.flyersoft.discuss.javabean.seekbook.BookList;
import com.flyersoft.discuss.javabean.seekbook.Discuss;
import com.flyersoft.discuss.tools.LogTools;
import com.flyersoft.discuss.tools.ToastTools;
import com.flyersoft.discuss.weight.DividerItemDecorationStyleOne;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * creat by: huzheng
 * date: 2019/6/4
 * description:书单发布列表
 */
public class BookListFragment extends BaseFragment {

    private List<BookList> data;
    private PullRefreshLayout layout;
    private RecyclerView mRecyclerView;
    private EndlessRecyclerOnScrollListener listener;
    private BookListFragmentAdapter mAdapter;
    private Handler handler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public ViewGroup initView() {
        ViewGroup view = (ViewGroup) LayoutInflater.from(this.getActivity()).inflate(R.layout.book_list_fragment_publish, null);
        layout = view.findViewById(R.id.book_list_fragment_refreshlayout);
        mRecyclerView = view.findViewById(R.id.book_list_fragment_recyclerview);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.setPage(1);
                getData(0, true);
            }
        });


        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        mRecyclerView.addItemDecoration(new DividerItemDecorationStyleOne(getContext(), DividerItemDecorationStyleOne.VERTICAL_LIST, R.drawable.divider, 0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        listener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LogTools.H("recyclerView loadMore " + currentPage);
                getData(currentPage - 1, false);
            }
        };
        mRecyclerView.addOnScrollListener(listener);

        layout.setRefreshing(true);
        getData(0, true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setRefreshing(false);
            }
        }, 5000);
        return view;
    }

    private void getData(int page, final boolean isRefresh) {
        MRManager.getInstance(getContext()).queryBookLists(page, 10).subscribe(new Observer<BaseRequest<List<BookList>>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(BaseRequest<List<BookList>> listBaseRequest) {
                if (isRefresh) {
                    data.clear();
                    data.addAll(listBaseRequest.getData());
                    mAdapter.setFoot(true);
                } else {
                    if (listBaseRequest.getData().size() > 0) {
                        data.addAll(listBaseRequest.getData());
                        mAdapter.setFoot(true);
                    } else {
                        mAdapter.setFoot(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                layout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable throwable) {
                LogTools.H(throwable.getMessage());
                layout.setRefreshing(false);
            }

            @Override
            public void onComplete() {
                layout.setRefreshing(false);
            }
        });
    }
}
