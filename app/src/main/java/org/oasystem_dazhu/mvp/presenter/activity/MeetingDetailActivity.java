package org.oasystem_dazhu.mvp.presenter.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.MeetingDetailBean;
import org.oasystem_dazhu.mvp.view.MeetingDetailDelegate;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.ToastUtil;


/**
 * Created by www on 2019/3/20.
 */

public class MeetingDetailActivity extends ActivityPresenter<MeetingDetailDelegate> {
    private String meetingId;
    private String meetingStatus;
    private AlertDialog dialog;
    private RadioGroup radioGroup;
    private EditText remarkEt;
    private int sign_status;

    @Override
    public Class<MeetingDetailDelegate> getDelegateClass() {
        return MeetingDetailDelegate.class;
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
            meetingId = bundle.getString("meetingId");
            meetingStatus = bundle.getString("meetingType");
            viewDelegate.initTopView(Integer.parseInt(meetingStatus));
            getMeetingDetail(meetingId);
            setOnclick();
        }
    }

    private void setOnclick() {
        viewDelegate.setOnClickListener(onClickListener, R.id.toolbar_right_rl);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.toolbar_right_rl:
                    showDialog();
                    break;

                case R.id.meeting_sign_in_commit:
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                        showAnotherDialog();
                    }
                    break;

                case R.id.meeting_sign_in_close:
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    break;
            }
        }
    };

    private void showAnotherDialog() {
        String note;
        if (radioGroup.getCheckedRadioButtonId() == R.id.meeting_sign_in_agree) {
            note = "您确定准时参加吗？";
            sign_status = 1;
        } else {
            note = "您确定无法准时参加吗？";
            sign_status = 2;
        }
        DialogUtil.showDialog(this, note, "确定", "取消", mOnClickListener);

    }

    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                toCommit();
                dialogInterface.dismiss();
            } else dialogInterface.dismiss();
        }
    };

    private void toCommit() {
        PublicModel.getInstance().countersign(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    ToastUtil.s("操作成功");
                }
            }
        }, meetingId, String.valueOf(sign_status), remarkEt.getText().toString().replaceAll(" ", ""));
    }

    private void showDialog() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_sign_in_meeting, null);
        dialog = DialogUtil.createAlertDialog(this, view);
        dialog.show();
        radioGroup = view.findViewById(R.id.meeting_sign_in_buttonGroup);
        radioGroup.check(R.id.meeting_sign_in_agree);
        ImageView commit = view.findViewById(R.id.meeting_sign_in_commit);
        ImageView close = view.findViewById(R.id.meeting_sign_in_close);
        remarkEt = view.findViewById(R.id.meeting_sign_in_et);
        commit.setOnClickListener(onClickListener);
        close.setOnClickListener(onClickListener);
    }


    private void getMeetingDetail(String meetingId) {
        PublicModel.getInstance().getMeetingDetail(new MSubscribe<BaseEntity<MeetingDetailBean>>() {
            @Override
            public void onNext(BaseEntity<MeetingDetailBean> bean) {
                super.onNext(bean);
                viewDelegate.initView(bean.getData());
            }
        }, meetingId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
