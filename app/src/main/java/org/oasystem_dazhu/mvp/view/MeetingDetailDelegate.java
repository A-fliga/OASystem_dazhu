package org.oasystem_dazhu.mvp.view;

import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.model.bean.MeetingDetailBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;


/**
 * Created by www on 2019/3/20.
 */

public class MeetingDetailDelegate extends ViewDelegate {
    private TextView meeting_detail_title,meeting_detail_compere,meeting_detail_type,meeting_detail_start_time,
            meeting_detail_end_time,meeting_detail_remember,meeting_detail_content;
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
        meeting_detail_title = get(R.id.meeting_detail_title);
        meeting_detail_compere = get(R.id.meeting_detail_compere);
        meeting_detail_type = get(R.id.meeting_detail_type);
        meeting_detail_start_time = get(R.id.meeting_detail_start_time);
        meeting_detail_end_time = get(R.id.meeting_detail_end_time);
        meeting_detail_remember = get(R.id.meeting_detail_remember);
        meeting_detail_content = get(R.id.meeting_detail_content);
    }

    public void initTopView(int status){
//        if(status != 3){
//        }
        setToolBarRightTv("回执");
    }

    public void initView(MeetingDetailBean bean){
        meeting_detail_title.setText("会议主题："+bean.getName());
        meeting_detail_compere.setText("主持人："+bean.getCompere_name());
        meeting_detail_type.setText("会议类型："+bean.getConference_type_name());
        meeting_detail_start_time.setText("开始时间："+bean.getStart_time());
        meeting_detail_end_time.setText("结束时间："+bean.getEnd_time());
        meeting_detail_remember.setText("会议纪要员："+bean.getAge_name());
    }
}
