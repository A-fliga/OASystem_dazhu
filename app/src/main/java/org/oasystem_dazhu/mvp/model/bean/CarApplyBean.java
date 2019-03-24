package org.oasystem_dazhu.mvp.model.bean;

/**
 * Created by www on 2019/3/23.
 */

public class CarApplyBean {

    /**
     * car_number	是	string	车辆车牌号
     user_id	是	int	别选择的是谁使用的 user_id
     car_use_type_id	是	int	申请类型id
     start_time	是	datetime	日期格式 预定开始时间
     end_time	是	datetime	日期格式 预定结束时间
     user_ids	是	strig	审批人user_id 如：1,2 根据选择顺序排列
     mileage	否	string	预定里程数
     destination	否	string	预定 目的地
     */
    public String user_id;
    public String car_number;
    public String car_use_type_id;
    public String start_time;
    public String end_time;
    public String user_ids;
    public String mileage;
    public String destination;
}
