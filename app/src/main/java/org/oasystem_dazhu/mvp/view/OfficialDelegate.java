package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.constants.Constants;
import org.oasystem_dazhu.manager.FirmingTypeManager;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.adapter.HomeTypeAdapter;
import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;
import org.oasystem_dazhu.mvp.model.bean.UserInfo;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.utils.LoadImgUtil;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.AutoSize;

/**
 * Created by www on 2018/12/29.
 */

public class OfficialDelegate extends ViewDelegate {
    private ImageView home_user_icon;
    private TextView home_user_name, home_user_unit;
    private RecyclerView typeRecyclerView;


    @Override
    public void onDestroy() {

    }


    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_official;
    }

    @Override
    public void initWidget() {
        typeRecyclerView = get(R.id.home_type_recyclerView);
        home_user_icon = get(R.id.home_user_icon);
        home_user_name = get(R.id.home_user_name);
        home_user_unit = get(R.id.home_user_unit);

        UserInfo userInfo = UserManager.getInstance().getUserInfo();
        if (userInfo != null) {
            LoadImgUtil.loadCirclePic(this.getActivity(), userInfo.getHeadimg(), home_user_icon, R.mipmap.home_user_icon);
            home_user_name.setText(userInfo.getName());
            home_user_unit.setText(userInfo.getCompany_name());
        }
    }

    public HomeTypeAdapter initTypeList() {
        List<String> imgIdList = new ArrayList<>();
        List<String> typeContentList = new ArrayList<>();
        List<HomeTypeBean.DataBean> beanList = FirmingTypeManager.getInstance().getBeanList();
        int size = 0;
        if (beanList.size() <= 5) {
            size = beanList.size();
        } else {
            size = 5;
        }
        for (int i = 0; i < size; i++) {
            typeContentList.add(beanList.get(i).getName());
            imgIdList.add(beanList.get(i).getImg());
        }
        typeContentList.add("更多");
        imgIdList.add("more");
        AutoSize.autoConvertDensity(this.getActivity(), Constants.SIZE_IN_DP,false);
        HomeTypeAdapter adapter = new HomeTypeAdapter(this.getActivity(), imgIdList, typeContentList, beanList, true);
        setRecycler(typeRecyclerView, adapter, 6, true);
        return adapter;
    }

}
