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

/**
 * Created by www on 2019/3/26.
 */

public class AddLeaveActivity extends ActivityPresenter<AddLeaveDelegate> {
    private List<String> commitBean;
    private List<String> noteList;
    private List<String> typeNameList;
    private List<String> typeIdList;
    private List<AllUserBean.DataBean> userBeanList;
    //这两个来判断数据有没获取成功
    private Boolean typeFinished = false, userReady = false;
    private DatePicker datePicker;
    private int opType = 0;
    private StringBuffer selectedUserId, selectedUserName;

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
        viewDelegate.getToolBarRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeFinished && userReady) {
                    commitBean.set(4, viewDelegate.getEt(R.id.leave_apply_reason).getText().toString());

                    for (int i = 0; i < commitBean.size(); i++) {
                        if (commitBean.get(i).isEmpty()) {
                            ToastUtil.s(noteList.get(i) + "不能为空");
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
        viewDelegate.setOnClickListener(onClickListener, R.id.leave_apply_start_time, R.id.leave_apply_end_time, R.id.leave_apply_approver);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.leave_apply_start_time:
                    setDate();
                    opType = 1;
                    break;
                case R.id.leave_apply_end_time:
                    setDate();
                    opType = 2;
                    break;
                case R.id.leave_apply_approver:
                    showUserDialog();
                    break;
            }
        }
    };

    private void showUserDialog() {
        opType = 4;
        String[] peopleList = new String[userBeanList.size()];
        boolean[] booleanList = new boolean[userBeanList.size()];
        for (int i = 0; i < userBeanList.size(); i++) {
            peopleList[i] = userBeanList.get(i).getName();
            booleanList[i] = false;
        }
        DialogUtil.showChoiceDialog(this, "请选择人员（请注意顺序）", "确定", "取消", peopleList, booleanList,
                mOnClickListener, multiChoiceClickListener);
    }

    private DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
            if (b) {
                selectedUserId.append(userBeanList.get(i).getId()).append(",");
                selectedUserName.append(userBeanList.get(i).getName()).append(",");
            } else {
                String content = selectedUserId.toString();
                if (content.contains(userBeanList.get(i).getId() + ",")) {
                    content = content.replace(userBeanList.get(i).getId() + ",", "");
                    selectedUserId.setLength(0);
                    selectedUserId.append(content);
                }

                String contentName = selectedUserName.toString();
                if (contentName.contains(userBeanList.get(i).getName() + ",")) {
                    contentName = contentName.replace(userBeanList.get(i).getName() + ",", "");
                    selectedUserName.setLength(0);
                    selectedUserName.append(contentName);
                }

            }
        }
    };


    public void setDate() {
        final Calendar calendar = Calendar.getInstance();
        //通过自定义控件AlertDialog实现
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.data_dialog, null);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        //设置日期简略显示 否则详细显示 包括:星期\周
        datePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        //设置date布局
        builder.setView(view);
        builder.setTitle("请选择日期");
        builder.setPositiveButton("确  定", mOnClickListener);
        builder.setNegativeButton("取  消", mOnClickListener);
        builder.create().show();
    }

    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                if (opType == 1 || opType == 2)
                    setDataMsg(opType);
                if (opType == 3) {
                    toApply();
                }
                if (opType == 4) {
                    viewDelegate.getTv(R.id.leave_apply_approver).setText(selectedUserName.substring(0, selectedUserName.length() - 1));
                    commitBean.set(3, selectedUserId.substring(0, selectedUserId.length() - 1));
                    selectedUserId.setLength(0);
                    selectedUserName.setLength(0);
                }
            }
            if (opType == 4) {
                selectedUserId.setLength(0);
                selectedUserName.setLength(0);
            }
            dialogInterface.dismiss();
        }
    };

    private void toApply() {
        LeaveApplyBean bean = new LeaveApplyBean();
        bean.attendance_type_id = commitBean.get(0);
        bean.start_time = commitBean.get(1);
        bean.end_time = commitBean.get(2);
        bean.user_ids = commitBean.get(3);
        bean.remark = commitBean.get(4);
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
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("%d-%02d-%02d",
                datePicker.getYear(),
                datePicker.getMonth() + 1,
                datePicker.getDayOfMonth()));
        if (opType == 1) {
            commitBean.set(1, stringBuffer.toString());
            viewDelegate.getTv(R.id.leave_apply_start_time).setText(stringBuffer.toString());
        }
        if (opType == 2) {
            commitBean.set(2, stringBuffer.toString());
            viewDelegate.getTv(R.id.leave_apply_end_time).setText(stringBuffer.toString());
        }

    }


    private void getUserData() {
        userBeanList = UserManager.getInstance().getAllUserInfo();
        if (userBeanList != null) {
            userReady = true;
        }
    }

    private void initCommitBean() {
        commitBean = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            commitBean.add("");
        }
        noteList = new ArrayList<>();
        noteList.add("请假类型");
        noteList.add("起始时间");
        noteList.add("结束时间");
        noteList.add("审批人");
        noteList.add("请假理由");
        typeIdList = new ArrayList<>();
        typeNameList = new ArrayList<>();
        userBeanList = new ArrayList<>();
        selectedUserId = new StringBuffer();
        selectedUserName = new StringBuffer();
    }

    private void showSureDialog() {
        opType = 3;
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
                    typeFinished = true;
                    initTypeSpinner(bean);
                }
            }
        });
    }

    private void initTypeSpinner(CarTypeListBean bean) {
        for (int i = 0; i < bean.getData().size(); i++) {
            typeNameList.add(bean.getData().get(i).getName());
            typeIdList.add(bean.getData().get(i).getId() + "");
            viewDelegate.initSpinner(R.id.add_leave_apply_type, typeNameList, new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    commitBean.set(0, typeIdList.get(position));
                }
            });
        }
    }


}
