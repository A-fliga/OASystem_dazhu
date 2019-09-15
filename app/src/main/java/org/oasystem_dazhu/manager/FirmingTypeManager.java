package org.oasystem_dazhu.manager;


import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/2/16.
 */

public class FirmingTypeManager {
    private static volatile FirmingTypeManager sInstance;
    private List<HomeTypeBean.DataBean> mBeanList;

    private FirmingTypeManager() {
    }

    public static FirmingTypeManager getInstance() {
        if (sInstance == null) {
            synchronized (FirmingTypeManager.class) {
                if (sInstance == null) {
                    sInstance = new FirmingTypeManager();
                }
            }
        }
        return sInstance;
    }

    public void addBeanList(List<HomeTypeBean.DataBean> beanList) {
        this.mBeanList = new ArrayList<>();
        this.mBeanList.addAll(beanList);
    }

    public List<HomeTypeBean.DataBean> getBeanList() {
        return mBeanList;
    }

    public String getTypeImg(int typeId){
        List<Integer> typeIdList = new ArrayList<>();
        for (int i = 0; i < mBeanList.size(); i++) {
            typeIdList.add(mBeanList.get(i).getId());
        }
        int index = typeIdList.indexOf(typeId);
        if (index < 0) {
            index = 0;
        }
        return mBeanList.get(index).getImg();
    }

    public String getTypeName(int typeId){
        List<Integer> typeIdList = new ArrayList<>();
        for (int i = 0; i < mBeanList.size(); i++) {
            typeIdList.add(mBeanList.get(i).getId());
        }
        int index = typeIdList.indexOf(typeId);
        return mBeanList.get(index).getName();
    }
    public List<Integer> getTypeIdList(){
        List<Integer> typeIdList = new ArrayList<>();
        for (int i = 0; i < mBeanList.size(); i++) {
            typeIdList.add(mBeanList.get(i).getId());
        }
        return typeIdList;
    }
}
