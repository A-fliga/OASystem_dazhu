package org.oasystem_dazhu.mvp.presenter.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AskForLeaveDetailBean;
import org.oasystem_dazhu.mvp.model.bean.AskLeaveBean;
import org.oasystem_dazhu.mvp.view.AskForLeaveDetailDelegate;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.ToastUtil;

/**
 * Created by www on 2019/3/26.
 * 请假详情页面
 */

public class AskForLeaveDetailActivity extends ActivityPresenter<AskForLeaveDetailDelegate> {
    private String mApplyId = "";
    private String mExamineId = "";
    private EditText mRemarkEt;

    @Override
    public Class<AskForLeaveDetailDelegate> getDelegateClass() {
        return AskForLeaveDetailDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mApplyId = bundle.getString("leave_apply_id");
            Boolean isApplyDetail = bundle.getBoolean("isApplyDetail");
            mViewDelegate.showBottom(!isApplyDetail);
            if (!isApplyDetail) {
                mViewDelegate.setOnClickListener(onClickListener, R.id.leave_apply_agree_img, R.id.leave_apply_agree_refuse);
                mExamineId = bundle.getString("examine_id");
            }
        }
        getApplyDetail(mApplyId);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.leave_apply_agree_img:
                    showAgreeDialog();
                    break;
                case R.id.leave_apply_agree_refuse:
                    showRefuseDialog();
                    break;
            }
        }
    };

    private void getApplyDetail(String applyId) {
        PublicModel.getInstance().getAskLeaveDetailBean(new MSubscribe<BaseEntity<AskForLeaveDetailBean>>() {
            @Override
            public void onNext(BaseEntity<AskForLeaveDetailBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    mViewDelegate.initView(bean.getData());
                    mViewDelegate.initFlows(bean.getData());
                }
            }
        }, applyId);
    }

    private void showRefuseDialog() {
        mRemarkEt = new EditText(this);
        new AlertDialog.Builder(this).setTitle("拒绝原因(非必填)")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(mRemarkEt)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        toRefuse(mRemarkEt.getText().toString().replaceAll(" ", ""));
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

    private void toRefuse(String remark) {
        PublicModel.getInstance().approveReject(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    EventBus.getDefault().post(new AskLeaveBean());
                    ToastUtil.s("操作成功");
                    finish();
                } else {
                    ToastUtil.s(bean.getMsg());
                }
            }

        }, mExamineId, remark);
    }


    private void showAgreeDialog() {
        DialogUtil.showDialog(this, "确定同意吗？", "确定", "取消", mOnclickListener);
    }

    DialogInterface.OnClickListener mOnclickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                toAgree();
            }
            dialogInterface.dismiss();
        }
    };

    private void toAgree() {
        PublicModel.getInstance().leaveAgree(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    EventBus.getDefault().post(new AskLeaveBean());
                    ToastUtil.s("操作成功");
                    finish();
                } else {
                    ToastUtil.s(bean.getMsg());
                }
            }
        }, mExamineId, mApplyId);
    }

}
