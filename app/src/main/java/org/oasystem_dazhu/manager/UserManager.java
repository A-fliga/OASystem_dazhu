package org.oasystem_dazhu.manager;

import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.constants.Constants;
import org.oasystem_dazhu.mvp.model.bean.AllUserBean;
import org.oasystem_dazhu.mvp.model.bean.LoginBean;
import org.oasystem_dazhu.mvp.model.bean.RefreshListBean;
import org.oasystem_dazhu.mvp.model.bean.UserInfo;
import org.oasystem_dazhu.simplecache.ACache;

import java.util.ArrayList;
import java.util.List;

import static org.oasystem_dazhu.constants.Constants.LOGIN_INFO;

/**
 * Created by www on 2018/12/29.
 */

public class UserManager {
    private static volatile UserManager sInstance;
    private UserInfo mUserInfo;
    private AllUserBean mAllUserBean;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (sInstance == null) {
            synchronized (UserManager.class) {
                if (sInstance == null) {
                    sInstance = new UserManager();
                }
            }
        }
        return sInstance;
    }

    public Boolean isLogin() {
        ACache aCache = ACache.get(MyApplication.getContext());
        LoginBean bean = (LoginBean) aCache.getAsObject(LOGIN_INFO);
        return bean != null;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.mUserInfo = userInfo;
    }

    public void setAllUserInfo(AllUserBean allUserInfo) {
        this.mAllUserBean = allUserInfo;
    }

    public List<AllUserBean.DataBean> getAllUserInfo() {
        List<AllUserBean.DataBean> beanList = new ArrayList<>();
        for (int i = 0; i < mAllUserBean.getData().size(); i++) {
//            if (allUserBean.getData().get(i).getId() != UserManager.getInstance().getUserInfo().getId()) {
            beanList.add(mAllUserBean.getData().get(i));
//            }
        }
        return beanList;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public RefreshListBean getRefreshBean() {
        return new RefreshListBean().setNeedRefresh(true);
    }

    public boolean isDazhu() {
        return "eqzhu".equals(Constants.getOrg());
    }
}
