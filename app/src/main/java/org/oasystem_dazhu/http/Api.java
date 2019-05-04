package org.oasystem_dazhu.http;


import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.bean.AllUserBean;
import org.oasystem_dazhu.mvp.model.bean.AskForLeaveDetailBean;
import org.oasystem_dazhu.mvp.model.bean.AskLeaveBean;
import org.oasystem_dazhu.mvp.model.bean.AttendanceBean;
import org.oasystem_dazhu.mvp.model.bean.AttendanceStatisticsBean;
import org.oasystem_dazhu.mvp.model.bean.CarApplyDetailBean;
import org.oasystem_dazhu.mvp.model.bean.CarApplyListBean;
import org.oasystem_dazhu.mvp.model.bean.CarTypeListBean;
import org.oasystem_dazhu.mvp.model.bean.DocumentBean;
import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;
import org.oasystem_dazhu.mvp.model.bean.LoginBean;
import org.oasystem_dazhu.mvp.model.bean.MeetingDetailBean;
import org.oasystem_dazhu.mvp.model.bean.MeetingListBean;
import org.oasystem_dazhu.mvp.model.bean.OfficeListBean;
import org.oasystem_dazhu.mvp.model.bean.OfficeTypeBean;
import org.oasystem_dazhu.mvp.model.bean.UpFileBean;
import org.oasystem_dazhu.mvp.model.bean.UserInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by www on 2017/11/13.
 * 定义网络请求接口
 */

public interface Api {

    /**
     * 登录
     *
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @POST("auth/login")
    Observable<BaseEntity<LoginBean>> login(@Body RequestBody register);

    /**
     * 修改密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @POST("user/update_pass")
    Observable<BaseEntity> updatePwd(@Header("Authorization") String token, @Body RequestBody pwd);


    /**
     * 获取用户信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @POST("user/info")
    Observable<BaseEntity<UserInfo>> getUserInfo(@Header("Authorization") String token);

    /**
     * 获取资源
     *
     * @return
     */
    @GET("read_flie/{source_id}")
    Observable<ResponseBody> getSource(@Header("Authorization") String token, @Path("source_id") int source_id);

    /**
     * 获取文档分类
     *
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @POST("document/type")
    Observable<BaseEntity<OfficeTypeBean>> getOfficeType(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 获取个人文档
     *
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @POST("document/personage")
    Observable<BaseEntity<OfficeListBean>> getOfficeList(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 待我审批
     *
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/wait")
    Observable<BaseEntity<DocumentBean>> getNotDoneDocument(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 我已审批
     *
     * @return
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/finish")
    Observable<BaseEntity<DocumentBean>> getDoneDocument(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 上传资源接口
     */
    @Multipart
    @POST("upload_flie")
    Observable<BaseEntity<UpFileBean>> upload_file(@Header("Authorization") String token, @Part MultipartBody.Part body);

    /**
     * 审批接口
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/examine")
    Observable<BaseEntity> examine(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 结束流程
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/close")
    Observable<BaseEntity> closeAll(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 添加附件
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/add_accessory")
    Observable<BaseEntity> addAccessory(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 意见
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/add_opinion")
    Observable<BaseEntity> add_opinion(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 获取全部用户的信息
     */
    @Headers({"Content-Type: application/json"})
    @POST("user/all")
    Observable<BaseEntity<AllUserBean>> getAllUser(@Header("Authorization") String token);

    /**
     * 加签
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/add_countersign")
    Observable<BaseEntity> add_countersign(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 获取固定分类
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/office_form_type")
    Observable<BaseEntity<HomeTypeBean>> getType(@Header("Authorization") String token);

    /**
     * 文件监控
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/monitoring")
    Observable<BaseEntity<DocumentBean>> getMonitorList(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 获取会议列表
     */
    @Headers({"Content-Type: application/json"})
    @POST("conference/index")
    Observable<BaseEntity<MeetingListBean>> getMeetingList(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 获取会议详情
     */
    @Headers({"Content-Type: application/json"})
    @POST("conference/details")
    Observable<BaseEntity<MeetingDetailBean>> getMeetingDetail(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 会议签到
     */
    @Headers({"Content-Type: application/json"})
    @POST("conference/countersign")
    Observable<BaseEntity> countersign(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 获取用车申请列表
     */
    @Headers({"Content-Type: application/json"})
    @POST("car/index")
    Observable<BaseEntity<CarApplyListBean>> getApplyBean(@Header("Authorization") String token);

    /**
     * 获取待我审批的用车列表
     */
    @Headers({"Content-Type: application/json"})
    @POST("car/lists")
    Observable<BaseEntity<CarApplyListBean>> getApproveBean(@Header("Authorization") String token);

    /**
     * 获取申请详情
     */
    @Headers({"Content-Type: application/json"})
    @POST("car/details")
    Observable<BaseEntity<CarApplyDetailBean>> getApplyDetailBean(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 获取申请类型
     */
    @Headers({"Content-Type: application/json"})
    @POST("car/typelist")
    Observable<CarTypeListBean> getCarTypeBean(@Header("Authorization") String token);

    /**
     * 新增用车申请
     */
    @Headers({"Content-Type: application/json"})
    @POST("car/create")
    Observable<BaseEntity> car_apply(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 审批用车通过
     */
    @Headers({"Content-Type: application/json"})
    @POST("car/agree")
    Observable<BaseEntity> approveAgree(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 审批用车不通过
     */
    @Headers({"Content-Type: application/json"})
    @POST("car/reject")
    Observable<BaseEntity> approveReject(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 考勤详情
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/clock_info")
    Observable<BaseEntity<AttendanceBean>> getAttendanceInfo(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 提交打卡
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/clock")
    Observable<BaseEntity> addAttendance(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 获取考勤统计
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/clock_statistics")
    Observable<BaseEntity<AttendanceStatisticsBean>> getAttendanceStatistics(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 获取请假列表
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/index")
    Observable<BaseEntity<AskLeaveBean>> getAskLeaveBean(@Header("Authorization") String token);


    /**
     * 获取请假详情
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/details")
    Observable<BaseEntity<AskForLeaveDetailBean>> getAskLeaveDetailBean(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 请假审批列表
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/lists")
    Observable<BaseEntity<AskLeaveBean>> getLeaveApprove(@Header("Authorization") String token);

    /**
     * 同意请假
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/agree")
    Observable<BaseEntity> leaveAgree(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 获取请假类型
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/typelist")
    Observable<CarTypeListBean> getLeaveTypeList(@Header("Authorization") String token);

    /**
     * 新增请假
     */
    @Headers({"Content-Type: application/json"})
    @POST("attendance/create")
    Observable<BaseEntity> addLeaveApply(@Header("Authorization") String token ,@Body RequestBody body);

    /**
     * 代签
     */
    @Headers({"Content-Type: application/json"})
    @POST("dispatch/add_daiqian")
    Observable<BaseEntity> addDaiqian(@Header("Authorization") String token ,@Body RequestBody body);

}
