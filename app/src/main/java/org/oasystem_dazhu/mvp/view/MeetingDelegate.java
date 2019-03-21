package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.RecyclerView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.MeetingListAdapter;
import org.oasystem_dazhu.mvp.model.bean.MeetingListBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.List;

/**
 * Created by www on 2019/3/19.
 */

public class MeetingDelegate extends ViewDelegate {
    private RecyclerView recyclerView;
    private MeetingListAdapter adapter;

    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_meeting;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("会议管理");
        recyclerView = get(R.id.meeting_recyclerView);
    }

    public void initList(List<MeetingListBean.DataBean> beanList) {
        adapter = new MeetingListAdapter(this.getActivity(), beanList);
        setRecycler(recyclerView, adapter, true);
        if (beanList.size() == 0) {
            ToastUtil.s("暂无数据");
        }
    }

    public MeetingListAdapter getAdapter() {
        return adapter;
    }
}
