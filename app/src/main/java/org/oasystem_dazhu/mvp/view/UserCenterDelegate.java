package org.oasystem_dazhu.mvp.view;

import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.model.bean.UserInfo;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.utils.AppUtil;
import org.oasystem_dazhu.utils.LoadImgUtil;

/**
 * Created by www on 2018/12/29.
 */

public class UserCenterDelegate extends ViewDelegate {
    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_user_center;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("个人中心");
        UserInfo userInfo = UserManager.getInstance().getUserInfo();
        if (userInfo != null) {
            ImageView icon = get(R.id.center_user_icon);
            TextView name = get(R.id.center_user_name);
            TextView unit = get(R.id.center_user_unit);
            LoadImgUtil.loadCirclePic(this.getActivity(), userInfo.getHeadimg(), icon, R.mipmap.center_icon);
            name.setText(userInfo.getName());
            unit.setText(userInfo.getCompany_name());
        }

        TextView versionCode = get(R.id.versionCode);
        versionCode.setText("版本号：" + AppUtil.getVersionCode());
    }
}
