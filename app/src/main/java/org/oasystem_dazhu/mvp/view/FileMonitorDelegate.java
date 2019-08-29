package org.oasystem_dazhu.mvp.view;


import android.view.View;
import android.widget.LinearLayout;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.constants.Constants;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

/**
 * Created by www on 2019/2/16.
 */

public class FileMonitorDelegate extends ViewDelegate {
    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_file_monitor;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("文件监控");
        //大竹没做文件监控的已处理、未处理的分类
        LinearLayout tabLayout = get(R.id.file_monitor_tab_layout);
        if ("dazhu".equals(Constants.getOrg())) {
            tabLayout.setVisibility(View.GONE);
        }
    }

}
