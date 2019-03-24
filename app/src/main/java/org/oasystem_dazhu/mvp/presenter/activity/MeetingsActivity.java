package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.view.View;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.adapter.MeetingListAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.MeetingListBean;
import org.oasystem_dazhu.mvp.view.MeetingDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/3/19.
 */

public class MeetingsActivity extends ActivityPresenter<MeetingDelegate> {
    private List<Integer> idList;
    private int selectedId;
    private MeetingListAdapter adapter;
    private List<MeetingListBean.DataBean> beanList;

    @Override
    public Class<MeetingDelegate> getDelegateClass() {
        return MeetingDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getMeetingList(1);
    }


    private void init() {
        idList = new ArrayList<>();
        idList.add(R.id.not_start_meeting);
        idList.add(R.id.going_meeting);
        idList.add(R.id.end_meeting);
        beanList = new ArrayList<>();
        viewDelegate.get(R.id.not_start_meeting).setSelected(true);
        selectedId = R.id.not_start_meeting;
        viewDelegate.setOnClickListener(onClickListener, R.id.not_start_meeting, R.id.going_meeting, R.id.end_meeting);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setCheckStates(view.getId());
            switch (view.getId()) {
                case R.id.not_start_meeting:
                    if (selectedId != view.getId()) {
                        getMeetingList(1);
                        selectedId = view.getId();
                    }
                    break;
                case R.id.going_meeting:
                    if (selectedId != view.getId()) {
                        getMeetingList(2);
                        selectedId = view.getId();
                    }
                    break;
                case R.id.end_meeting:
                    if (selectedId != view.getId()) {
                        getMeetingList(3);
                        selectedId = view.getId();
                    }
                    break;
            }
        }
    };



    private void getMeetingList(int i) {
        PublicModel.getInstance().getMeetingList(new MSubscribe<BaseEntity<MeetingListBean>>() {
            @Override
            public void onNext(BaseEntity<MeetingListBean> bean) {
                super.onNext(bean);
                if (beanList != null)
                    beanList.clear();
                beanList.addAll(bean.getData().getData());
                viewDelegate.initList(beanList);
                adapter = viewDelegate.getAdapter();
                setItemClick(adapter);
            }
        }, String.valueOf(i));
    }

    private void setItemClick(MeetingListAdapter adapter) {
        if (adapter != null) {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("meetingId",beanList.get(position).getId());
                    bundle.putString("meetingType",beanList.get(position).getNow_status());
                    startMyActivity(MeetingDetailActivity.class,bundle);
                }
            });
        }
    }

    private void setCheckStates(int id) {
        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i) == id) {
                viewDelegate.get(id).setSelected(true);
            } else
                viewDelegate.get(idList.get(i)).setSelected(false);
        }
    }
}
