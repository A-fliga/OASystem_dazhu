package org.oasystem_dazhu.mvp.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

import java.util.List;

import me.jessyan.autosize.AutoSize;

/**
 * Created by www on 2019/3/26.
 */

public class AddLeaveDelegate extends ViewDelegate {
    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_ask_leave;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("新增请假");
        setToolBarRightTv("提交");
    }

    public void initSpinner(final Activity activity, final int resId, final List<String> dataList, final OnItemClickListener itemClickListener) {
        final FrameLayout contentLayout = get(resId);
        contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoSize.cancelAdapt(activity);
                Spinner spinner = new Spinner(activity);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, dataList);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        itemClickListener.onItemClick(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                contentLayout.addView(spinner);
                spinner.performClick();
            }
        });

    }
}
