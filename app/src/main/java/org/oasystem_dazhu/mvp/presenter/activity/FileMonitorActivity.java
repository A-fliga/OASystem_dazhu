package org.oasystem_dazhu.mvp.presenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.adapter.OfficialDocumentAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.DocumentBean;
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
 */

public class FileMonitorActivity extends ActivityPresenter<FileMonitorDelegate> {
    private OfficialDocumentAdapter adapter;
    private List<DocumentBean.DataBean> newBeanList;
    private Boolean isPositive_create = false, isPositive_update = false;
    private ScreenBean screenBean;
    private boolean done = false;
    private List<Integer> idList;
    private int selectedId;

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
        viewDelegate.setOnClickListener(onClickListener, R.id.official_not_done_tab, R.id.official_done_tab,
                R.id.to_screen, R.id.to_sort_create, R.id.to_sort_update, R.id.refresh);
        init();
        getFileMonitorList(new ScreenBean());
    }


    private void init() {
        idList = new ArrayList<>();
        idList.add(R.id.official_not_done_tab);
        idList.add(R.id.official_done_tab);
        selectedId = R.id.official_not_done_tab;
        setCheckStates(selectedId);
        viewDelegate.get(R.id.official_not_done_tab).setSelected(true);
    }


    private void getFileMonitorList(ScreenBean screenBean) {
        if (done) {
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
                    if (newBeanList != null)
                        newBeanList.clear();
                    newBeanList = new ArrayList<DocumentBean.DataBean>();
                    newBeanList.addAll(SortUtl.sort(bean.getData().getData()));
                    RecyclerView recyclerView = viewDelegate.get(R.id.file_monitor_recyclerView);
                    adapter = new OfficialDocumentAdapter(false, FileMonitorActivity.this, newBeanList);
                    viewDelegate.setRecycler(recyclerView, adapter, true);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("done", true);
                            bundle.putSerializable("DocumentDataBean", newBeanList.get(position));
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
                    if (done) {
                        done = false;
                        getFileMonitorList(new ScreenBean());
                    }
                    setCheckStates(view.getId());
                    break;
                case R.id.official_done_tab:
                    if (!done) {
                        done = true;
                        getFileMonitorList(new ScreenBean());
                    }
                    setCheckStates(view.getId());
                    break;
                //去筛选
                case R.id.to_screen:
                    Intent intent = new Intent(FileMonitorActivity.this, ScreenActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("needShowTop", true);
                    if (screenBean == null)
                        screenBean = new ScreenBean();
                    bundle2.putSerializable("localScreenBean", screenBean);
                    intent.putExtras(bundle2);
                    startActivityForResult(intent, 1000);
                    break;

                case R.id.to_sort_create:
                    isPositive_create = !isPositive_create;
                    isPositive_update = false;
                    newBeanList = SortUtl.sort(newBeanList, isPositive_create ? POSITIVE : REVERSE, true);
                    adapter.setBeanList(newBeanList);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.to_sort_update:
                    isPositive_update = !isPositive_update;
                    isPositive_create = false;
                    newBeanList = SortUtl.sort(newBeanList, isPositive_update ? POSITIVE : REVERSE, false);
                    adapter.setBeanList(newBeanList);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.refresh:
                    isPositive_update = false;
                    isPositive_create = false;
                    getFileMonitorList(new ScreenBean());
                    break;

            }
        }
    };

    private void setCheckStates(int id) {
        RelativeLayout parent;
        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i) == id) {
                viewDelegate.get(id).setSelected(true);
                parent = (RelativeLayout) viewDelegate.get(id).getParent();
                TextView childTv = (TextView) parent.getChildAt(1);
                childTv.setTextColor(getResources().getColor(R.color.color_ffffff));

            } else {
                viewDelegate.get(idList.get(i)).setSelected(false);
                parent = (RelativeLayout) viewDelegate.get(idList.get(i)).getParent();
                TextView childTv = (TextView) parent.getChildAt(1);
                childTv.setTextColor(getResources().getColor(R.color.color_e8421d));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 2000) {
            screenBean = (ScreenBean) data.getExtras().getSerializable("screenBean");
            if (screenBean != null) {
                getFileMonitorList(screenBean);
            }
        }
    }
}
