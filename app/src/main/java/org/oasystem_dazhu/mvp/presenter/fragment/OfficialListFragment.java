package org.oasystem_dazhu.mvp.presenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.adapter.OfficialDocumentAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.DocumentBean;
import org.oasystem_dazhu.mvp.model.bean.RefreshListBean;
import org.oasystem_dazhu.mvp.model.bean.ScreenBean;
import org.oasystem_dazhu.mvp.presenter.activity.OfficialDocumentDetailActivity;
import org.oasystem_dazhu.mvp.view.OfficialListDelegate;
import org.oasystem_dazhu.utils.SortUtl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/1/19.
 */

public class OfficialListFragment extends FragmentPresenter<OfficialListDelegate> {
    public OfficialDocumentAdapter mDoneAdapter, mNotDoneAdapter;
    private List<DocumentBean.DataBean> mDoneBeanList;
    private List<DocumentBean.DataBean> mNotDoneBeanList;
    private boolean mDone = false;
    private int mTypeId = 0;


    @Override
    public Class<OfficialListDelegate> getDelegateClass() {
        return OfficialListDelegate.class;
    }

    @Override
    protected void onFragmentVisible() {

    }

    @Override
    protected void onFragmentHidden() {

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDone = bundle.getBoolean("done");
            mTypeId = bundle.getInt("typeId");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        if (mDone) {
            getDoneDocument(new ScreenBean());
        } else
            getNotDoneDocument(new ScreenBean());
    }

    public void notifyDataSetChanged(Boolean done, Boolean positive, Boolean isCreate) {
        if (done) {
            if (positive) {
                mDoneBeanList = SortUtl.sort(mDoneBeanList, SortUtl.POSITIVE, isCreate);
                mDoneAdapter.setBeanList(mDoneBeanList);
            } else {
                mDoneBeanList = SortUtl.sort(mDoneBeanList, SortUtl.REVERSE, isCreate);
                mDoneAdapter.setBeanList(mDoneBeanList);
            }
            mDoneAdapter.notifyDataSetChanged();
        } else {
            if (positive) {
                mNotDoneBeanList = SortUtl.sort(mNotDoneBeanList, SortUtl.POSITIVE, isCreate);
                mNotDoneAdapter.setBeanList(mNotDoneBeanList);
            } else {
                mNotDoneBeanList = SortUtl.sort(mNotDoneBeanList, SortUtl.REVERSE, isCreate);
                mNotDoneAdapter.setBeanList(mNotDoneBeanList);
            }
            mNotDoneAdapter.notifyDataSetChanged();
        }
    }

    public void getDoneDocument(ScreenBean screenBean) {
        screenBean.setType(String.valueOf(mTypeId));
        final RecyclerView recyclerView = mViewDelegate.get(R.id.official_document_recycler);
        PublicModel.getInstance().getDoneDocument(new MSubscribe<BaseEntity<DocumentBean>>() {
            @Override
            public void onNext(final BaseEntity<DocumentBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    if (mDoneBeanList != null)
                        mDoneBeanList.clear();
                    mDoneBeanList = new ArrayList<>();
                    mDoneBeanList.addAll(SortUtl.sort(bean.getData().getData()));
                    mDoneAdapter = new OfficialDocumentAdapter(true, getActivity(), mDoneBeanList);
                    mViewDelegate.setRecycler(recyclerView, mDoneAdapter, true);
                    mDoneAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("done", mDone);
                            bundle.putSerializable("DocumentDataBean", mDoneBeanList.get(position));
                            startMyActivity(OfficialDocumentDetailActivity.class, bundle);
                        }
                    });
                }

            }
        }, screenBean);
    }

    public void getNotDoneDocument(ScreenBean screenBean) {
        screenBean.setType(String.valueOf(mTypeId));
        final RecyclerView recyclerView = mViewDelegate.get(R.id.official_document_recycler);
        PublicModel.getInstance().getNotDoneDocument(new MSubscribe<BaseEntity<DocumentBean>>() {
            @Override
            public void onNext(final BaseEntity<DocumentBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    if (mNotDoneBeanList != null)
                        mNotDoneBeanList.clear();
                    mNotDoneBeanList = new ArrayList<>();
                    mNotDoneBeanList.addAll(SortUtl.sort(bean.getData().getData()));
                    mNotDoneAdapter = new OfficialDocumentAdapter(false, getActivity(), mNotDoneBeanList);
                    mViewDelegate.setRecycler(recyclerView, mNotDoneAdapter, true);
                    mNotDoneAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("done", mDone);
                            bundle.putSerializable("DocumentDataBean", mNotDoneBeanList.get(position));
                            startMyActivity(OfficialDocumentDetailActivity.class, bundle);
                        }
                    });
                }
            }
        }, screenBean, -1);
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void refreshList(RefreshListBean bean) {
        if (bean != null && bean.isNeedRefresh()) {
            if (mDone) {
                getDoneDocument(new ScreenBean());
            } else {
                getNotDoneDocument(new ScreenBean());
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
