package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private MeetingListAdapter mAdapter;
    private List<MeetingListBean.DataBean> mBeanList;
    private List<Integer> mIdList;
    private int mSelectedId;

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
        mIdList = new ArrayList<>();
        mIdList.add(R.id.not_start_meeting);
        mIdList.add(R.id.going_meeting);
        mIdList.add(R.id.end_meeting);
        mBeanList = new ArrayList<>();
        mViewDelegate.get(R.id.not_start_meeting).setSelected(true);
        mSelectedId = R.id.not_start_meeting;
        setCheckStates(mSelectedId);
        mViewDelegate.setOnClickListener(onClickListener, R.id.not_start_meeting, R.id.going_meeting, R.id.end_meeting);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setCheckStates(view.getId());
            switch (view.getId()) {
                case R.id.not_start_meeting:
                    if (mSelectedId != view.getId()) {
                        getMeetingList(1);
                        mSelectedId = view.getId();
                    }
                    break;
                case R.id.going_meeting:
                    if (mSelectedId != view.getId()) {
                        getMeetingList(2);
                        mSelectedId = view.getId();
                    }
                    break;
                case R.id.end_meeting:
                    if (mSelectedId != view.getId()) {
                        getMeetingList(3);
                        mSelectedId = view.getId();
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
                if (mBeanList != null)
                    mBeanList.clear();
                mBeanList.addAll(bean.getData().getData());
                mViewDelegate.initList(mBeanList);
                mAdapter = mViewDelegate.getAdapter();
                setItemClick(mAdapter);
            }
        }, String.valueOf(i));
    }

    private void setItemClick(MeetingListAdapter adapter) {
        if (adapter != null) {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("meetingId", mBeanList.get(position).getId());
                    bundle.putString("meetingType", mBeanList.get(position).getNow_status());
                    startMyActivity(MeetingDetailActivity.class, bundle);
                }
            });
        }
    }

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
}
