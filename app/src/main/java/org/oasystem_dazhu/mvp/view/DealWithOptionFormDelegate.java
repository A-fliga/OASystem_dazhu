package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.DealWithOptionAdapter;
import org.oasystem_dazhu.mvp.model.bean.DealWithOptionBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

import java.util.List;

/**
 * Created by www on 2019/5/22.
 */

public class DealWithOptionFormDelegate extends ViewDelegate {
    private RecyclerView deal_with_option_recycler;
    public DealWithOptionAdapter adapter;
    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_deal_with_option_form;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("历史记录");
        deal_with_option_recycler = get(R.id.deal_with_option_recycler);
    }

    public DealWithOptionAdapter initList(List<DealWithOptionBean.DispatchSuggestBean> beanList){
        adapter = new DealWithOptionAdapter(this.getActivity(),beanList);
        setRecycler(deal_with_option_recycler,adapter,true);
        return adapter;
    }

    public void initLeftTv(int count){
        TextView tv = get(R.id.deal_with_option_num);
        tv.setText("已经有"+count+"位提出意见");
    }

}
