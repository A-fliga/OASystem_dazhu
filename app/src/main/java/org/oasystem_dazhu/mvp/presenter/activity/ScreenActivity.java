package org.oasystem_dazhu.mvp.presenter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.manager.FirmingTypeManager;
import org.oasystem_dazhu.mvp.adapter.ScreenTypeAdapter;
import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;
import org.oasystem_dazhu.mvp.model.bean.ScreenBean;
import org.oasystem_dazhu.mvp.view.ScreenDelegate;
import org.oasystem_dazhu.utils.DialogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jessyan.autosize.AutoSize;

/**
 * Created by www on 2019/1/8.
 * 筛选界面
 */

public class ScreenActivity extends ActivityPresenter<ScreenDelegate> {
    private List<Map<Integer, Boolean>> mSelectedType;
    private List<HomeTypeBean.DataBean> mTypeBeanList;
    private DatePicker mDatePicker;
    private ScreenBean mScreenBean;
    private ScreenTypeAdapter mAdapter;
    private TextView mStartDate, mEndDate;
    private EditText mOfficeName;
    private EditText mOfficeSerial;
    private EditText mOfficeOrgan;
    private EditText mOfficeNumber;
    private int mOpType = 0;


    @Override
    public Class<ScreenDelegate> getDelegateClass() {
        return ScreenDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mScreenBean = (ScreenBean) bundle.getSerializable("localScreenBean");
        }
        if (bundle != null && bundle.getBoolean("needShowTop")) {
            initTypeList();
        } else {
            mViewDelegate.get(R.id.screen_top_line).setVisibility(View.GONE);
            mViewDelegate.get(R.id.screen_type_tv).setVisibility(View.GONE);
        }
        initDataView();
        mViewDelegate.setOnClickListener(onClickListener, R.id.s_date, R.id.e_date, R.id.screen_reset, R.id.screen_sure);

    }

    private void initDataView() {
        if (!mScreenBean.getOrgan().equals("")) {
            mOfficeOrgan.setText(mScreenBean.getOrgan());
        }
        if (!mScreenBean.getName().equals("")) {
            mOfficeName.setText(mScreenBean.getName());
        }
        if (!mScreenBean.getSerial().equals("")) {
            mOfficeSerial.setText(mScreenBean.getSerial());
        }
        if (!mScreenBean.getNumber().equals("")) {
            mOfficeNumber.setText(mScreenBean.getNumber());
        }
        if (!mScreenBean.getS_date().equals("")) {
            mStartDate.setText(mScreenBean.getS_date());
        }
        if (!mScreenBean.getE_date().equals("")) {
            mEndDate.setText(mScreenBean.getE_date());
        }

    }

    private void initView() {
        mStartDate = mViewDelegate.get(R.id.s_date);
        mEndDate = mViewDelegate.get(R.id.e_date);
        mOfficeName = mViewDelegate.get(R.id.office_name);
        mOfficeSerial = mViewDelegate.get(R.id.office_serial);
        mOfficeOrgan = mViewDelegate.get(R.id.office_organ);
        mOfficeNumber = mViewDelegate.get(R.id.office_number);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //选择起始时间
                case R.id.s_date:
                    mOpType = 2;
                    setDate();
                    break;
                //选择结束时间
                case R.id.e_date:
                    mOpType = 3;
                    setDate();
                    break;
                //重置
                case R.id.screen_reset:
                    mOpType = 1;
                    DialogUtil.showDialog(ScreenActivity.this, "您确定要重置吗？", "确定", "取消", mOnClickListener);

                    break;
                //确定
                case R.id.screen_sure:
                    returnToOrg();
                    break;
            }
        }
    };

    private void returnToOrg() {
        if (mSelectedType != null) {
            List<Map<Integer, Boolean>> list = mAdapter.getList();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get(FirmingTypeManager.getInstance().getTypeIdList().get(i))) {
                    sb.append(FirmingTypeManager.getInstance().getTypeIdList().get(i));
                    sb.append(",");
                }
            }
            if (sb.length() >= 1)
                mScreenBean.setType(sb.substring(0, sb.length() - 1));
            else mScreenBean.setType("");
        }
        mScreenBean.setSerial(mOfficeSerial.getText().toString().replaceAll(" ", ""));
        mScreenBean.setName(mOfficeName.getText().toString().replaceAll(" ", ""));
        mScreenBean.setOrgan(mOfficeOrgan.getText().toString().replaceAll(" ", ""));
        mScreenBean.setNumber(mOfficeNumber.getText().toString().replaceAll(" ", ""));
        Bundle bundle = new Bundle();
        bundle.putSerializable("screenBean", mScreenBean);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(2000, intent);
        finish();
    }


    public void setDate() {
        AutoSize.cancelAdapt(this);
        final Calendar calendar = Calendar.getInstance();
        //通过自定义控件AlertDialog实现
        AlertDialog.Builder builder = new AlertDialog.Builder(ScreenActivity.this);
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
            if (mOpType == 1 && i == -1) {
                reset();
            }
            if (i == -1) {
                if (mOpType == 2) {
                    setDataMsg(2);
                }
                if (mOpType == 3) {
                    setDataMsg(3);
                }
            }
            dialogInterface.dismiss();
        }
    };

    private void setDataMsg(int opType) {
        //日期格式
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("%d-%02d-%02d",
                mDatePicker.getYear(),
                mDatePicker.getMonth() + 1,
                mDatePicker.getDayOfMonth()));
        if (opType == 2) {
            mScreenBean.setS_date(stringBuffer.toString());
            mStartDate.setText(stringBuffer.toString());
        }
        if (opType == 3) {
            mScreenBean.setE_date(stringBuffer.toString());
            mEndDate.setText(stringBuffer.toString());
        }
    }

    private void reset() {
        mScreenBean.setType("");
        mScreenBean.setE_date("");
        mScreenBean.setName("");
        mScreenBean.setSerial("");
        mScreenBean.setS_date("");
        mScreenBean.setOrgan("");
        if (mAdapter != null) {
            mAdapter.clearSelected();
        }
        mStartDate.setText("");
        mEndDate.setText("");
        mOfficeName.setText("");
        mOfficeSerial.setText("");
        mOfficeOrgan.setText("");
    }

    private void initTypeList() {
        String[] type = new String[]{};
        if (!mScreenBean.getType().equals("")) {
            type = mScreenBean.getType().split(",");
        }
        mTypeBeanList = new ArrayList<>();
        mTypeBeanList = FirmingTypeManager.getInstance().getBeanList();
        mSelectedType = new ArrayList<>();
        for (int i = 0; i < mTypeBeanList.size(); i++) {
            Map<Integer, Boolean> map = new HashMap<>();
            map.put(mTypeBeanList.get(i).getId(), false);
            mSelectedType.add(map);
        }
        for (int i = 0; i < mSelectedType.size(); i++) {
            for (String aType : type) {
                if (mSelectedType.get(i).containsKey(Integer.parseInt(aType))) {
                    mSelectedType.get(i).put(Integer.parseInt(aType), true);
                }
            }
        }
        RecyclerView recyclerView = mViewDelegate.get(R.id.screen_recyclerView);
        mAdapter = new ScreenTypeAdapter(mTypeBeanList, mSelectedType, this);
        mViewDelegate.setRecycler(recyclerView, mAdapter, 4, false);
    }
}
