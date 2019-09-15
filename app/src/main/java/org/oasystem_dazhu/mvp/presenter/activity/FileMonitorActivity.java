package org.oasystem_dazhu.mvp.presenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import org.oasystem_dazhu.mvp.view.FileMonitorDelegate;
import org.oasystem_dazhu.utils.SortUtl;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static org.oasystem_dazhu.utils.SortUtl.POSITIVE;
import static org.oasystem_dazhu.utils.SortUtl.REVERSE;


/**
 * Created by www on 2019/2/16.
 * 文件监控的界面
 */

public class FileMonitorActivity extends ActivityPresenter<FileMonitorDelegate> {
    private OfficialDocumentAdapter mAdapter;
    private List<DocumentBean.DataBean> mNewBeanList;
    private List<Integer> mIdList;
    private ScreenBean mScreenBean;
    private boolean mIsPositiveCreate = false;
    private boolean mIsPositiveUpdate = false;
    private boolean mDone = false;
    private int mSelectedId;

    @Override
    public Class<FileMonitorDelegate> getDelegateClass() {
        return FileMonitorDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDelegate.setOnClickListener(onClickListener, R.id.official_not_done_tab, R.id.official_done_tab,
                R.id.to_screen, R.id.to_sort_create, R.id.to_sort_update, R.id.refresh);
        init();
        getFileMonitorList(new ScreenBean());
    }


    private void init() {
        mIdList = new ArrayList<>();
        mIdList.add(R.id.official_not_done_tab);
        mIdList.add(R.id.official_done_tab);
        mSelectedId = R.id.official_not_done_tab;
        setCheckStates(mSelectedId);
        mViewDelegate.get(R.id.official_not_done_tab).setSelected(true);
    }


    private void getFileMonitorList(ScreenBean screenBean) {
        if (mDone) {
            screenBean.setStatus("1");
        } else {
            screenBean.setStatus("0");
        }
        PublicModel.getInstance().getMonitorList(new MSubscribe<BaseEntity<DocumentBean>>() {
            @Override
            public void onNext(BaseEntity<DocumentBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    if (bean.getData().getData().size() == 0) {
                        ToastUtil.l("暂无数据");
                    }
                    if (mNewBeanList != null) {
                        mNewBeanList.clear();
                    }
                    mNewBeanList = new ArrayList<DocumentBean.DataBean>();
                    mNewBeanList.addAll(SortUtl.sort(bean.getData().getData()));
                    RecyclerView recyclerView = mViewDelegate.get(R.id.file_monitor_recyclerView);
                    mAdapter = new OfficialDocumentAdapter(false, FileMonitorActivity.this, mNewBeanList);
                    mViewDelegate.setRecycler(recyclerView, mAdapter, true);
                    mAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("done", true);
                            bundle.putSerializable("DocumentDataBean", mNewBeanList.get(position));
                            startMyActivity(OfficialDocumentDetailActivity.class, bundle);
                        }
                    });
                }
            }
        }, screenBean);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.official_not_done_tab:
                    if (mDone) {
                        mDone = false;
                        getFileMonitorList(new ScreenBean());
                    }
                    setCheckStates(view.getId());
                    break;
                case R.id.official_done_tab:
                    if (!mDone) {
                        mDone = true;
                        getFileMonitorList(new ScreenBean());
                    }
                    setCheckStates(view.getId());
                    break;
                //去筛选
                case R.id.to_screen:
                    Intent intent = new Intent(FileMonitorActivity.this, ScreenActivity.class);
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
                    mIsPositiveUpdate = !mIsPositiveUpdate;
                    mIsPositiveCreate = false;
                    mNewBeanList = SortUtl.sort(mNewBeanList, mIsPositiveUpdate ? POSITIVE : REVERSE, false);
                    mAdapter.setBeanList(mNewBeanList);
                    mAdapter.notifyDataSetChanged();
                    break;
                case R.id.refresh:
                    mIsPositiveUpdate = false;
                    mIsPositiveCreate = false;
                    getFileMonitorList(new ScreenBean());
                    break;

            }
        }
    };

    private void setCheckStates(int id) {
        RelativeLayout parent;
        for (int i = 0; i < mIdList.size(); i++) {
            if (mIdList.get(i) == id) {
                mViewDelegate.get(id).setSelected(true);
                parent = (RelativeLayout) mViewDelegate.get(id).getParent();
                TextView childTv = (TextView) parent.getChildAt(1);
                childTv.setTextColor(getResources().getColor(R.color.color_ffffff));

            } else {
                mViewDelegate.get(mIdList.get(i)).setSelected(false);
                parent = (RelativeLayout) mViewDelegate.get(mIdList.get(i)).getParent();
                TextView childTv = (TextView) parent.getChildAt(1);
                childTv.setTextColor(getResources().getColor(R.color.color_e8421d));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 2000) {
            mScreenBean = (ScreenBean) data.getExtras().getSerializable("screenBean");
            if (mScreenBean != null) {
                getFileMonitorList(mScreenBean);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void refreshList(RefreshListBean bean) {
        if (bean != null && bean.isNeedRefresh()) {
            getFileMonitorList(new ScreenBean());
        }
    }
}
