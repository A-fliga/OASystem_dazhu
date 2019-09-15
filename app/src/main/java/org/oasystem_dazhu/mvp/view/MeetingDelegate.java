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
    private RecyclerView mRecyclerView;
    private MeetingListAdapter mAdapter;

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
        mRecyclerView = get(R.id.meeting_recyclerView);
    }

    public void initList(List<MeetingListBean.DataBean> beanList) {
        mAdapter = new MeetingListAdapter(this.getActivity(), beanList);
        setRecycler(mRecyclerView, mAdapter, true);
        if (beanList.size() == 0) {
            ToastUtil.s("暂无数据");
        }
    }

    public MeetingListAdapter getAdapter() {
        return mAdapter;
    }
}
