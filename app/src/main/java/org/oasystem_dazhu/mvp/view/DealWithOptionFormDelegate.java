package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    private RecyclerView mDealWithOptionRecycler;
    public DealWithOptionAdapter mAdapter;
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
        mDealWithOptionRecycler = get(R.id.deal_with_option_recycler);
    }

    public DealWithOptionAdapter initList(List<DealWithOptionBean.DispatchSuggestBean> beanList,boolean done){
        mAdapter = new DealWithOptionAdapter(this.getActivity(),beanList,done);
        setRecycler(mDealWithOptionRecycler, mAdapter,true);
        return mAdapter;
    }

    public void initLeftTv(int count){
        TextView tv = get(R.id.deal_with_option_num);
        tv.setText("已经有"+count+"位提出意见");
    }

    public void initOptionView(){
        View line = get(R.id.bottom_line);
        line.setVisibility(View.VISIBLE);
        TextView add_option_btn = get(R.id.add_option_btn);
        add_option_btn.setBackgroundColor(getActivity().getResources().getColor(R.color.color_f5f5f5));
        add_option_btn.setEnabled(false);
    }
}
