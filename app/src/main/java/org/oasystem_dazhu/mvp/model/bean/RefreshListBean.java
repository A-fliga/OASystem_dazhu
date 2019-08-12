package org.oasystem_dazhu.mvp.model.bean;

/**
 * Created by www on 2019/8/12.
 */

public class RefreshListBean {
    private boolean isNeedRefresh;

    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }

    public RefreshListBean setNeedRefresh(boolean needRefresh) {
        isNeedRefresh = needRefresh;
        return this;
    }
}
