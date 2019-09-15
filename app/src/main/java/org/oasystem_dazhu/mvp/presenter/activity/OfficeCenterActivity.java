package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.adapter.OfficeListAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AccessoryBean;
import org.oasystem_dazhu.mvp.model.bean.OfficeListBean;
import org.oasystem_dazhu.mvp.view.MyOfficeDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/1/5.
 */

public class OfficeCenterActivity extends ActivityPresenter {
    private List<OfficeListBean.DataBean> mBeanList;
    private OfficeListAdapter mAdapter;
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
        mFromHome = getIntent().getExtras().getBoolean("fromHome", true);
        mBeanList = new ArrayList<>();
        final RecyclerView recyclerView = mViewDelegate.get(R.id.my_office_recycler);
        String document_type_id = getIntent().getExtras().getString("document_type_id");
        PublicModel.getInstance().getOfficeList(new MSubscribe<BaseEntity<OfficeListBean>>() {
            @Override
            public void onNext(BaseEntity<OfficeListBean> bean) {
                super.onNext(bean);
                mBeanList.addAll(bean.getData().getData());
                mAdapter = new OfficeListAdapter(mBeanList, OfficeCenterActivity.this);
                mViewDelegate.setRecycler(recyclerView, mAdapter, true);
                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //是从主页来的，点击后需要展示
                        if (mFromHome)
                            fromHome(position);
                        else {
                            AccessoryBean accessoryBean = new AccessoryBean();
                            accessoryBean.setName(mBeanList.get(position).getSource().getName());
                            accessoryBean.setSource_id(mBeanList.get(position).getSource_id());
                            EventBus.getDefault().post(accessoryBean);
                            finish();
                        }
                    }
                });

            }
        }, document_type_id, "1");

    }

    private void fromHome(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("source_id", mBeanList.get(position).getSource_id() + "");
        String[] str = mBeanList.get(position).getSource().getName().split("\\.");
        bundle.putString("type", str[str.length - 1]);
        startMyActivity(OfficeDetailActivity.class, bundle);
    }
}
