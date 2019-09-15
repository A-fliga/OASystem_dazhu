package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.adapter.MyOfficeTypeAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AccessoryBean;
import org.oasystem_dazhu.mvp.model.bean.OfficeTypeBean;
import org.oasystem_dazhu.mvp.view.MyOfficeDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/1/5.
 * 选择个人文件上传的时候用的。现在暂时没有用处。
 */

public class MyOfficeActivity extends ActivityPresenter {
    private MyOfficeTypeAdapter mAdapter;
    private List<OfficeTypeBean.DataBean> mBeanList;
    private boolean mFromHome = true;

    @Override
    public Class getDelegateClass() {
        return MyOfficeDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mFromHome = getIntent().getExtras().getBoolean("fromHome", true);
        final RecyclerView my_office_recycler = mViewDelegate.get(R.id.my_office_recycler);
        PublicModel.getInstance().getOfficeType(new MSubscribe<BaseEntity<OfficeTypeBean>>() {
            @Override
            public void onNext(final BaseEntity<OfficeTypeBean> bean) {
                super.onNext(bean);
                mBeanList = new ArrayList<OfficeTypeBean.DataBean>();
                mBeanList.addAll(bean.getData().getData());
                mAdapter = new MyOfficeTypeAdapter(bean.getData().getData(), MyOfficeActivity.this);
                mViewDelegate.setRecycler(my_office_recycler, mAdapter, true);
                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("document_type_id", mBeanList.get(position).getId() + "");
                        bundle.putBoolean("fromHome", mFromHome);
                        startMyActivity(OfficeCenterActivity.class, bundle);
                    }
                });
            }
        }, "1");
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void addAccessory(AccessoryBean bean) {
        if (bean != null) {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
