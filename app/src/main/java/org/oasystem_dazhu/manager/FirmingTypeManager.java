package org.oasystem_dazhu.manager;


import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/2/16.
 */

public class FirmingTypeManager {
    private static volatile FirmingTypeManager instance;
    private List<HomeTypeBean.DataBean> beanList;

    private FirmingTypeManager() {
    }

    public static FirmingTypeManager getInstance() {
        if (instance == null) {
            synchronized (FirmingTypeManager.class) {
                if (instance == null) {
                    instance = new FirmingTypeManager();
                }
            }
        }
        return instance;
    }

    public void addBeanList(List<HomeTypeBean.DataBean> beanList) {
        this.beanList = new ArrayList<>();
        this.beanList.addAll(beanList);
    }

    public List<HomeTypeBean.DataBean> getBeanList() {
        return beanList;
    }

    public String getTypeImg(int typeId){
        List<Integer> typeIdList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            typeIdList.add(beanList.get(i).getId());
        }
        int index = typeIdList.indexOf(typeId);
        if (index < 0) {
            index = 0;
        }
        return beanList.get(index).getImg();
    }

    public String getTypeName(int typeId){
        List<Integer> typeIdList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            typeIdList.add(beanList.get(i).getId());
        }
        int index = typeIdList.indexOf(typeId);
        return beanList.get(index).getName();
    }
    public List<Integer> getTypeIdList(){
        List<Integer> typeIdList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            typeIdList.add(beanList.get(i).getId());
        }
        return typeIdList;
    }
}
