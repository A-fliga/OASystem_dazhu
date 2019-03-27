package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.RecyclerView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.AskForLeaveAdapter;
import org.oasystem_dazhu.mvp.model.bean.AskLeaveBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.List;

/**
 * Created by www on 2019/3/25.
 */

public class AskForLeaveDelegate extends ViewDelegate {
    private RecyclerView recyclerView;
    private AskForLeaveAdapter adapter;

    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_ask_for_leave;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("请假管理");
        getToolBarRightImg().setImageResource(R.mipmap.add_ask_for_leave);
        recyclerView = get(R.id.car_apply_list);
    }

    public AskForLeaveAdapter initList(List<AskLeaveBean.DataBean> beanList) {
        adapter = new AskForLeaveAdapter(beanList);
        setRecycler(recyclerView, adapter, true);
        if (beanList.size() == 0) {
            ToastUtil.s("暂无数据");
        }
        return adapter;
    }
}
