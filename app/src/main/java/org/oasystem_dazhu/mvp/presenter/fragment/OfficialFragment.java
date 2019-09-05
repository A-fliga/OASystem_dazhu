package org.oasystem_dazhu.mvp.presenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.manager.FirmingTypeManager;
import org.oasystem_dazhu.mvp.adapter.HomeTypeAdapter;
import org.oasystem_dazhu.mvp.adapter.OfficialDocumentAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.DocumentBean;
import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;
import org.oasystem_dazhu.mvp.model.bean.RefreshListBean;
import org.oasystem_dazhu.mvp.model.bean.ScreenBean;
import org.oasystem_dazhu.mvp.presenter.activity.MainActivity;
import org.oasystem_dazhu.mvp.presenter.activity.MoreTypeActivity;
import org.oasystem_dazhu.mvp.presenter.activity.OfficialDocumentDetailActivity;
import org.oasystem_dazhu.mvp.presenter.activity.OfficialHandleActivity;
import org.oasystem_dazhu.mvp.presenter.activity.ScreenActivity;
import org.oasystem_dazhu.mvp.view.OfficialDelegate;
import org.oasystem_dazhu.utils.SortUtl;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static org.oasystem_dazhu.utils.SortUtl.POSITIVE;
import static org.oasystem_dazhu.utils.SortUtl.REVERSE;

/**
 * Created by www on 2018/12/29.
 */

public class OfficialFragment extends FragmentPresenter<OfficialDelegate> {
    private static final int LOAD_MORE_INIT = 1;
    private OfficialDocumentAdapter mAdapter;
    private List<DocumentBean.DataBean> mNewBeanList = new ArrayList<DocumentBean.DataBean>();
    private HomeTypeAdapter mTypeAdapter;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ScreenBean mScreenBean;
    private boolean mIsPositiveCreate = false;
    private boolean mIsPositiveUpdate = false;

    @Override
    protected void onFragmentVisible() {
    }

    @Override
    protected void onFragmentHidden() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView refresh = mViewDelegate.get(R.id.refresh);
        refresh.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        initAll();
        mViewDelegate.mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIsRefreshMode = false;
                mRefreshLayout = refreshLayout;
                mRefreshLayout.setEnableRefresh(false);
                mPage++;
                getNotDoneList(new ScreenBean());
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIsRefreshMode = true;
                mRefreshLayout = refreshLayout;
                mRefreshLayout.setEnableLoadMore(false);
                initAll();
            }
        });
        if (getActivity() != null) {
            mViewDelegate.mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
            mViewDelegate.mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        }
    }

    private void initAll() {
        mPage = LOAD_MORE_INIT;
        mTypeAdapter = mViewDelegate.initTypeList();
        getFirmingType();
        mViewDelegate.setOnClickListener(mOnClickListener,
                R.id.to_screen, R.id.to_sort_create, R.id.to_sort_update, R.id.refresh, R.id.home_user_icon);
        setOnItemClickListener();
    }

    private void setOnItemClickListener() {
        mTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (FirmingTypeManager.getInstance().getBeanList().size() > 5) {
                    //position 到了5的话  一定是更多按钮
                    if (position == 5) {
                        startMyActivity(MoreTypeActivity.class, null);
                    } else {
                        start2Activity(FirmingTypeManager.getInstance().getBeanList().get(position).getId());
                    }
                } else {
                    //这些是自定义分类
                    if (position <= FirmingTypeManager.getInstance().getBeanList().size() - 1) {
                        start2Activity(FirmingTypeManager.getInstance().getBeanList().get(position).getId());
                    }
                    //有多的代表是更多
                    else {
                        startMyActivity(MoreTypeActivity.class, null);
                    }
                }
            }
        });
    }

    private void getNotDoneList(ScreenBean screenBean) {
        PublicModel.getInstance().getNotDoneDocument(new MSubscribe<BaseEntity<DocumentBean>>() {
            @Override
            public void onNext(final BaseEntity<DocumentBean> bean) {
                super.onNext(bean);
                finishLoad();
                if (bean.getCode() == 0) {
                    if (bean.getData().getData().size() == 0) {
                        mPage--;
                        //只有是下拉刷新的时候才需要弹出来
                        if (mIsRefreshMode) {
                            ToastUtil.s("暂无数据");
                            if (mRefreshLayout != null) {
                                mRefreshLayout.setEnableLoadMore(false);
                            }
                        } else {
                            ToastUtil.s("没有更多了");
                            return;
                        }
                    }
                    if (mIsRefreshMode) {
                        mNewBeanList.clear();
                    }
                    mNewBeanList.addAll(bean.getData().getData());
                    mNewBeanList = (SortUtl.sort(mNewBeanList));
                    if (mAdapter != null && mRecyclerView != null && !mIsRefreshMode) {
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("done", false);
                                bundle.putSerializable("DocumentDataBean", mNewBeanList.get(position));
                                startMyActivity(OfficialDocumentDetailActivity.class, bundle);
                            }
                        });
                        mRecyclerView.scrollToPosition((mPage) * 10);
                        return;
                    }
                    mRecyclerView = mViewDelegate.get(R.id.home_recyclerView);
                    mAdapter = new OfficialDocumentAdapter(false, getActivity(), mNewBeanList);
                    mViewDelegate.setRecycler(mRecyclerView, mAdapter, true);
                    mAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("done", false);
                            bundle.putSerializable("DocumentDataBean", mNewBeanList.get(position));
                            startMyActivity(OfficialDocumentDetailActivity.class, bundle);
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mPage--;
                finishLoad();
            }

            @Override
            public void onStart() {
                onStartWithoutLoadingView();
            }
        }, screenBean, mPage);
    }

    private void finishLoad() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMore();
            mRefreshLayout.setEnableRefresh(true);
            mRefreshLayout.setEnableLoadMore(true);
        }
    }

    private void getFirmingType() {
        PublicModel.getInstance().getType(new MSubscribe<BaseEntity<HomeTypeBean>>() {
            @Override
            public void onNext(BaseEntity<HomeTypeBean> bean) {
                super.onNext(bean);
                List<HomeTypeBean.DataBean> beanList = new ArrayList<HomeTypeBean.DataBean>();
                beanList.addAll(bean.getData().getData());
                FirmingTypeManager.getInstance().addBeanList(beanList);
                mTypeAdapter = mViewDelegate.initTypeList();
                setOnItemClickListener();
                getNotDoneList(new ScreenBean());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                finishLoad();
            }

            @Override
            public void onStart() {
                onStartWithoutLoadingView();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void refreshList(RefreshListBean bean) {
        if (bean != null && bean.isNeedRefresh()) {
            mPage = LOAD_MORE_INIT;
            mIsRefreshMode = true;
            getFirmingType();
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //去筛选
                case R.id.to_screen:
                    Intent intent = new Intent(getActivity(), ScreenActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("needShowTop", true);
                    if (mScreenBean == null)
                        mScreenBean = new ScreenBean();
                    bundle2.putSerializable("localScreenBean", mScreenBean);
                    intent.putExtras(bundle2);
                    startActivityForResult(intent, 1000);
                    break;
                case R.id.to_sort_create:
                    mIsPositiveCreate = !mIsPositiveCreate;
                    mIsPositiveUpdate = false;
                    mNewBeanList = SortUtl.sort(mNewBeanList, mIsPositiveCreate ? POSITIVE : REVERSE, true);
                    mAdapter.setBeanList(mNewBeanList);
                    mAdapter.notifyDataSetChanged();
                    break;
                case R.id.to_sort_update:
                    mIsPositiveCreate = false;
                    mIsPositiveUpdate = !mIsPositiveUpdate;
                    mNewBeanList = SortUtl.sort(mNewBeanList, mIsPositiveUpdate ? POSITIVE : REVERSE, false);
                    mAdapter.setBeanList(mNewBeanList);
                    mAdapter.notifyDataSetChanged();
                    break;

                case R.id.home_user_icon:
                    MainActivity activity = (MainActivity) getActivity();
                    if (activity != null) {
                        activity.viewPager.setCurrentItem(1);
                        activity.setCheck(1);
                    }
                    break;
            }
        }
    };


    private void start2Activity(int typeId) {
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", typeId);
        startMyActivity(OfficialHandleActivity.class, bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 2000) {
            mScreenBean = (ScreenBean) data.getExtras().getSerializable("screenBean");
            mPage = LOAD_MORE_INIT;
            if (mScreenBean != null) {
                mIsRefreshMode = true;
                getNotDoneList(mScreenBean);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public Class<OfficialDelegate> getDelegateClass() {
        return OfficialDelegate.class;
    }

}
