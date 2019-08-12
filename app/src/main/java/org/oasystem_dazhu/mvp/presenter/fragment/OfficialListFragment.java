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
    private Boolean done = false;
    private int typeId = 0;
    public OfficialDocumentAdapter doneAdapter, notDoneAdapter;
    private List<DocumentBean.DataBean> doneBeanList, notDoneBeanList;

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
            done = bundle.getBoolean("done");
            typeId = bundle.getInt("typeId");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        if (done) {
            getDoneDocument(new ScreenBean());
        } else
            getNotDoneDocument(new ScreenBean());
    }

    public void notifyDataSetChanged(Boolean done, Boolean positive, Boolean isCreate) {
        if (done) {
            if (positive) {
                doneBeanList = SortUtl.sort(doneBeanList, SortUtl.POSITIVE, isCreate);
                doneAdapter.setBeanList(doneBeanList);
            } else {
                doneBeanList = SortUtl.sort(doneBeanList, SortUtl.REVERSE, isCreate);
                doneAdapter.setBeanList(doneBeanList);
            }
            doneAdapter.notifyDataSetChanged();
        } else {
            if (positive) {
                notDoneBeanList = SortUtl.sort(notDoneBeanList, SortUtl.POSITIVE, isCreate);
                notDoneAdapter.setBeanList(notDoneBeanList);
            } else {
                notDoneBeanList = SortUtl.sort(notDoneBeanList, SortUtl.REVERSE, isCreate);
                notDoneAdapter.setBeanList(notDoneBeanList);
            }
            notDoneAdapter.notifyDataSetChanged();
        }
    }

    public void getDoneDocument(ScreenBean screenBean) {
        screenBean.setType(String.valueOf(typeId));
        final RecyclerView recyclerView = viewDelegate.get(R.id.official_document_recycler);
        PublicModel.getInstance().getDoneDocument(new MSubscribe<BaseEntity<DocumentBean>>() {
            @Override
            public void onNext(final BaseEntity<DocumentBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    if (doneBeanList != null)
                        doneBeanList.clear();
                    doneBeanList = new ArrayList<>();
                    doneBeanList.addAll(SortUtl.sort(bean.getData().getData()));
                    doneAdapter = new OfficialDocumentAdapter(true, getActivity(), doneBeanList);
                    viewDelegate.setRecycler(recyclerView, doneAdapter, true);
                    doneAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("done", done);
                            bundle.putSerializable("DocumentDataBean", doneBeanList.get(position));
                            startMyActivity(OfficialDocumentDetailActivity.class, bundle);
                        }
                    });
                }

            }
        }, screenBean);
    }

    public void getNotDoneDocument(ScreenBean screenBean) {
        screenBean.setType(String.valueOf(typeId));
        final RecyclerView recyclerView = viewDelegate.get(R.id.official_document_recycler);
        PublicModel.getInstance().getNotDoneDocument(new MSubscribe<BaseEntity<DocumentBean>>() {
            @Override
            public void onNext(final BaseEntity<DocumentBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    if (notDoneBeanList != null)
                        notDoneBeanList.clear();
                    notDoneBeanList = new ArrayList<>();
                    notDoneBeanList.addAll(SortUtl.sort(bean.getData().getData()));
                    notDoneAdapter = new OfficialDocumentAdapter(false, getActivity(), notDoneBeanList);
                    viewDelegate.setRecycler(recyclerView, notDoneAdapter, true);
                    notDoneAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("done", done);
                            bundle.putSerializable("DocumentDataBean", notDoneBeanList.get(position));
                            startMyActivity(OfficialDocumentDetailActivity.class, bundle);
                        }
                    });
                }
            }
        }, screenBean);
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void refreshList(RefreshListBean bean) {
        if (bean != null && bean.isNeedRefresh()) {
            if (done) {
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
