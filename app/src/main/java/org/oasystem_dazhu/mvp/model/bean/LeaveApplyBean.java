package org.oasystem_dazhu.mvp.model.bean;

/**
 * Created by www on 2019/3/27.
 */

public class LeaveApplyBean {
    /**
     * attendance_type_id	是	int	申请类型id
     * start_time	是	datetime	日期格式 预定开始时间
     * end_time	是	datetime	日期格式 预定结束时间
     * user_ids	是	strig	审批人user_id 如：1,2 根据选择顺序排列
     * remark	否	string	申请理由
     */
    public String attendance_type_id;
    public String start_time;
    public String end_time;
    public String user_ids;
    public String remark;
}
