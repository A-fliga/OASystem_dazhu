package org.oasystem_dazhu.mvp.view;

import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.model.bean.MeetingDetailBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;


/**
 * Created by www on 2019/3/20.
 */

public class MeetingDetailDelegate extends ViewDelegate {
    private TextView mMeetingDetailTitle, mMeetingDetailCompere, mMeetingDetailType, mMeetingDetailStartTime,
            mMeetingDetailEndTime, mMeetingDetailRemember, mMeetingDetailContent;
    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_meeting_detail;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("会议详情");
        mMeetingDetailTitle = get(R.id.meeting_detail_title);
        mMeetingDetailCompere = get(R.id.meeting_detail_compere);
        mMeetingDetailType = get(R.id.meeting_detail_type);
        mMeetingDetailStartTime = get(R.id.meeting_detail_start_time);
        mMeetingDetailEndTime = get(R.id.meeting_detail_end_time);
        mMeetingDetailRemember = get(R.id.meeting_detail_remember);
        mMeetingDetailContent = get(R.id.meeting_detail_content);
    }

    public void initTopView(int status){
//        if(status != 3){
//        }
        setToolBarRightTv("回执");
    }

    public void initView(MeetingDetailBean bean){
        mMeetingDetailTitle.setText("会议主题："+bean.getName());
        mMeetingDetailCompere.setText("主持人："+bean.getCompere_name());
        mMeetingDetailType.setText("会议类型："+bean.getConference_type_name());
        mMeetingDetailStartTime.setText("开始时间："+bean.getStart_time());
        mMeetingDetailEndTime.setText("结束时间："+bean.getEnd_time());
        mMeetingDetailRemember.setText("会议纪要员："+bean.getAge_name());
    }
}
