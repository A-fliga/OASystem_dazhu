package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.manager.FirmingTypeManager;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.adapter.HomeBusinessManagerAdapter;
import org.oasystem_dazhu.mvp.adapter.HomeTypeAdapter;
import org.oasystem_dazhu.mvp.model.bean.HomeBusinessManagerBean;
import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;
import org.oasystem_dazhu.mvp.model.bean.UserInfo;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.utils.LoadImgUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2018/12/29.
 */

public class OfficialDelegate extends ViewDelegate {
    private ImageView home_user_icon;
    private TextView home_user_name, home_user_unit;
    private RecyclerView typeRecyclerView,businessManagerRecycler;


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
        businessManagerRecycler = get(R.id.home_manager_recyclerView);
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
        for (int i = 0; i < beanList.size(); i++) {
            typeContentList.add(beanList.get(i).getName());
            imgIdList.add(beanList.get(i).getImg());
        }
        if (UserManager.getInstance().getUserInfo().getIs_monitoring() == 1) {
            typeContentList.add("文件监控");
        }

        HomeTypeAdapter adapter = new HomeTypeAdapter(imgIdList, typeContentList, beanList, this.getActivity());
        setRecyclerView(typeRecyclerView, adapter);
        return adapter;
    }

    public HomeBusinessManagerAdapter initManagerAdapter() {
        List<HomeBusinessManagerBean> beanList = new ArrayList<>();
        HomeBusinessManagerBean bean = null;
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                bean = new HomeBusinessManagerBean();
                bean.name = "考勤管理";
                bean.resId = R.mipmap.ask_for_leave_icon;
            }
            if (i == 1) {
                bean = new HomeBusinessManagerBean();
                bean.name = "会议管理";
                bean.resId = R.mipmap.meeting_icon;
            }
            if (i == 2) {
                bean = new HomeBusinessManagerBean();
                bean.name = "用车申请";
                bean.resId = R.mipmap.car_apply_icon;
            }
            beanList.add(bean);
        }
        HomeBusinessManagerAdapter adapter = new HomeBusinessManagerAdapter(beanList,this.getActivity());
        setRecyclerView(businessManagerRecycler, adapter);
        return adapter;
    }

    private void setRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
