package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
    private ImageView mHomeUserIcon;
    private TextView mHomeUserName, mHomeUserInit;
    private RecyclerView mTypeRecyclerView;
    public SmartRefreshLayout mRefreshLayout;
    @Override
    public void onDestroy() {

    }


    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_official;
    }

    @Override
    public void initWidget() {
        mTypeRecyclerView = get(R.id.home_type_recyclerView);
        mHomeUserIcon = get(R.id.home_user_icon);
        mHomeUserName = get(R.id.home_user_name);
        mHomeUserInit = get(R.id.home_user_unit);
        mRefreshLayout = get(R.id.refreshView);
        UserInfo userInfo = UserManager.getInstance().getUserInfo();
        if (userInfo != null) {
            LoadImgUtil.loadCirclePic(this.getActivity(), userInfo.getHeadimg(), mHomeUserIcon, R.mipmap.home_user_icon);
            mHomeUserName.setText(userInfo.getName());
            mHomeUserInit.setText(userInfo.getCompany_name());
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
        setRecycler(mTypeRecyclerView, adapter, 6, true);
        return adapter;
    }

}
