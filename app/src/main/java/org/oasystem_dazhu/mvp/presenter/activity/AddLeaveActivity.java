package org.oasystem_dazhu.mvp.presenter.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;

import org.greenrobot.eventbus.EventBus;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AllUserBean;
import org.oasystem_dazhu.mvp.model.bean.AskLeaveBean;
import org.oasystem_dazhu.mvp.model.bean.CarTypeListBean;
import org.oasystem_dazhu.mvp.model.bean.LeaveApplyBean;
import org.oasystem_dazhu.mvp.view.AddLeaveDelegate;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.jessyan.autosize.AutoSize;

/**
 * Created by www on 2019/3/26.
 * 新增请假的页面
 */

public class AddLeaveActivity extends ActivityPresenter<AddLeaveDelegate> {
    private DatePicker mDatePicker;
    private List<String> mCommitBean;
    private List<String> mNoteList;
    private List<String> mTypeNameList;
    private List<String> mTypeIdList;
    private List<AllUserBean.DataBean> mUserBeanList;
    private StringBuilder selectedUserId;
    private StringBuilder selectedUserName;
    //这两个来判断数据有没获取成功
    private boolean mTypeFinished = false;
    private boolean mUserReady = false;
    private int mOpType = 0;


    @Override
    public Class<AddLeaveDelegate> getDelegateClass() {
        return AddLeaveDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCommitBean();
        getTypeData();
        getUserData();
        initClickListener();
        //判断一下非空数据
        mViewDelegate.getToolBarRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTypeFinished && mUserReady) {
                    mCommitBean.set(4, mViewDelegate.getEt(R.id.leave_apply_reason).getText().toString());

                    for (int i = 0; i < mCommitBean.size(); i++) {
                        if (mCommitBean.get(i).isEmpty()) {
                            ToastUtil.s(mNoteList.get(i) + "不能为空");
                            return;
                        }
                    }
                    //走申请接口
                    showSureDialog();
                } else {
                    ToastUtil.s("数据未初始化完成，请等待");
                }
            }
        });
    }

    private void initClickListener() {
        mViewDelegate.setOnClickListener(onClickListener, R.id.leave_apply_start_time, R.id.leave_apply_end_time, R.id.leave_apply_approver);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.leave_apply_start_time:
                    setDate();
                    mOpType = 1;
                    break;
                case R.id.leave_apply_end_time:
                    setDate();
                    mOpType = 2;
                    break;
                case R.id.leave_apply_approver:
                    showUserDialog();
                    break;
            }
        }
    };

    private void showUserDialog() {
        mOpType = 4;
        String[] peopleList = new String[mUserBeanList.size()];
        boolean[] booleanList = new boolean[mUserBeanList.size()];
        for (int i = 0; i < mUserBeanList.size(); i++) {
            peopleList[i] = mUserBeanList.get(i).getName();
            booleanList[i] = false;
        }
        DialogUtil.showChoiceDialog(this, "请选择人员（请注意顺序）", "确定", "取消", peopleList, booleanList,
                mOnClickListener, multiChoiceClickListener);
    }

    private DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
            if (b) {
                selectedUserId.append(mUserBeanList.get(i).getId()).append(",");
                selectedUserName.append(mUserBeanList.get(i).getName()).append(",");
            } else {
                String content = selectedUserId.toString();
                if (content.contains(mUserBeanList.get(i).getId() + ",")) {
                    content = content.replace(mUserBeanList.get(i).getId() + ",", "");
                    selectedUserId.setLength(0);
                    selectedUserId.append(content);
                }

                String contentName = selectedUserName.toString();
                if (contentName.contains(mUserBeanList.get(i).getName() + ",")) {
                    contentName = contentName.replace(mUserBeanList.get(i).getName() + ",", "");
                    selectedUserName.setLength(0);
                    selectedUserName.append(contentName);
                }

            }
        }
    };


    public void setDate() {
        AutoSize.cancelAdapt(this);
        final Calendar calendar = Calendar.getInstance();
        //通过自定义控件AlertDialog实现
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.data_dialog, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.date_picker);
        //设置日期简略显示 否则详细显示 包括:星期\周
        mDatePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        //设置date布局
        builder.setView(view);
        builder.setTitle("请选择日期");
        builder.setPositiveButton("确  定", mOnClickListener);
        builder.setNegativeButton("取  消", mOnClickListener);
        builder.show();
    }

    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                if (mOpType == 1 || mOpType == 2)
                    setDataMsg(mOpType);
                if (mOpType == 3) {
                    toApply();
                }
                if (mOpType == 4) {
                    mViewDelegate.getTv(R.id.leave_apply_approver).setText(selectedUserName.substring(0, selectedUserName.length() - 1));
                    mCommitBean.set(3, selectedUserId.substring(0, selectedUserId.length() - 1));
                    selectedUserId.setLength(0);
                    selectedUserName.setLength(0);
                }
            }
            if (mOpType == 4) {
                selectedUserId.setLength(0);
                selectedUserName.setLength(0);
            }
            dialogInterface.dismiss();
        }
    };

    private void toApply() {
        LeaveApplyBean bean = new LeaveApplyBean();
        bean.attendance_type_id = mCommitBean.get(0);
        bean.start_time = mCommitBean.get(1);
        bean.end_time = mCommitBean.get(2);
        bean.user_ids = mCommitBean.get(3);
        bean.remark = mCommitBean.get(4);
        PublicModel.getInstance().addLeaveApply(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                ToastUtil.s("申请成功");
                EventBus.getDefault().post(new AskLeaveBean());
                finish();
            }
        }, bean);
    }

    private void setDataMsg(int opType) {
        //日期格式
        StringBuilder StringBuilder = new StringBuilder();
        StringBuilder.append(String.format("%d-%02d-%02d",
                mDatePicker.getYear(),
                mDatePicker.getMonth() + 1,
                mDatePicker.getDayOfMonth()));
        if (opType == 1) {
            mCommitBean.set(1, StringBuilder.toString());
            mViewDelegate.getTv(R.id.leave_apply_start_time).setText(StringBuilder.toString());
        }
        if (opType == 2) {
            mCommitBean.set(2, StringBuilder.toString());
            mViewDelegate.getTv(R.id.leave_apply_end_time).setText(StringBuilder.toString());
        }

    }


    private void getUserData() {
        mUserBeanList = UserManager.getInstance().getAllUserInfo();
        if (mUserBeanList != null) {
            mUserReady = true;
        }
    }

    private void initCommitBean() {
        mCommitBean = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mCommitBean.add("");
        }
        mNoteList = new ArrayList<>();
        mNoteList.add("请假类型");
        mNoteList.add("起始时间");
        mNoteList.add("结束时间");
        mNoteList.add("审批人");
        mNoteList.add("请假理由");
        mTypeIdList = new ArrayList<>();
        mTypeNameList = new ArrayList<>();
        mUserBeanList = new ArrayList<>();
        selectedUserId = new StringBuilder();
        selectedUserName = new StringBuilder();
    }

    private void showSureDialog() {
        mOpType = 3;
        DialogUtil.showDialog(this, "您确定要请假吗？", "确定", "取消", mOnClickListener);
    }

    private void getTypeData() {
        PublicModel.getInstance().getLeaveTypeList(new MSubscribe<CarTypeListBean>() {
            @Override
            public void onNext(CarTypeListBean bean) {
                super.onNext(bean);
                if (bean.getCode() != 0) {
                    ToastUtil.s(bean.getMsg());
                } else {
                    mTypeFinished = true;
                    initTypeSpinner(bean);
                }
            }
        });
    }

    private void initTypeSpinner(CarTypeListBean bean) {
        for (int i = 0; i < bean.getData().size(); i++) {
            mTypeNameList.add(bean.getData().get(i).getName());
            mTypeIdList.add(bean.getData().get(i).getId() + "");
            mViewDelegate.initSpinner(this, R.id.add_leave_apply_type, mTypeNameList, new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mCommitBean.set(0, mTypeIdList.get(position));
                }
            });
        }
    }


}
