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
import org.oasystem_dazhu.mvp.model.bean.CarApplyBean;
import org.oasystem_dazhu.mvp.model.bean.CarApplyDetailBean;
import org.oasystem_dazhu.mvp.view.CarApplyDetailDelegate;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.ToastUtil;

/**
 * Created by www on 2019/3/23.
 */

public class CarApplyDetailActivity extends ActivityPresenter<CarApplyDetailDelegate> {
    private String applyId = "";
    private String examine_id = "";
    private EditText remarkEt;

    @Override
    public Class<CarApplyDetailDelegate> getDelegateClass() {
        return CarApplyDetailDelegate.class;
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
            applyId = bundle.getString("car_apply_id");
            Boolean isApplyDetail = bundle.getBoolean("isApplyDetail");
            viewDelegate.showBottom(!isApplyDetail);
            if (!isApplyDetail) {
                viewDelegate.setOnClickListener(onClickListener, R.id.car_apply_agree_img, R.id.car_apply_agree_refuse);
                examine_id = bundle.getString("examine_id");
            }
        }
        getApplyDetail(applyId);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.car_apply_agree_img:
                    showAgreeDialog();
                    break;
                case R.id.car_apply_agree_refuse:
                    showRefuseDialog();
                    break;
            }
        }
    };

    private void showRefuseDialog() {
        remarkEt = new EditText(this);
        new AlertDialog.Builder(this).setTitle("拒绝原因(非必填)")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(remarkEt)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        toRefuse(remarkEt.getText().toString().replaceAll(" ",""));
                    }
                })
                .setNegativeButton("取消", null)
        .show();


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

    private void toRefuse(String remark) {
        PublicModel.getInstance().approveReject(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    EventBus.getDefault().post(new CarApplyBean());
                    ToastUtil.s("操作成功");
                } else {
                    ToastUtil.s(bean.getMsg());
                }
            }

        },examine_id,remark);
    }

    private void toAgree() {
        PublicModel.getInstance().approveAgree(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    EventBus.getDefault().post(new CarApplyBean());
                    ToastUtil.s("操作成功");
                } else {
                    ToastUtil.s(bean.getMsg());
                }
            }
        }, examine_id, applyId);
    }


    private void getApplyDetail(String applyId) {
        PublicModel.getInstance().getApplyDetailBean(new MSubscribe<BaseEntity<CarApplyDetailBean>>() {
            @Override
            public void onNext(BaseEntity<CarApplyDetailBean> bean) {
                super.onNext(bean);
                viewDelegate.initView(bean.getData());
                viewDelegate.initFlows(bean.getData());
            }
        }, applyId);
    }
}
