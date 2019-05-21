package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.view.View;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.DealWithOptionBean;
import org.oasystem_dazhu.mvp.view.DealWithOptionFormDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/5/22.
 */

public class DealWithOptionFormActivity extends ActivityPresenter<DealWithOptionFormDelegate> {
    private List<DealWithOptionBean.DispatchSuggestBean> beanList;

    @Override
    public Class<DealWithOptionFormDelegate> getDelegateClass() {
        return DealWithOptionFormDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int itemId = bundle.getInt("itemId");
            initRecyclerView(itemId);
        }

        viewDelegate.setOnClickListener(onClickListener, R.id.add_option_btn);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_option_btn:


                    break;

            }
        }
    };

    private void initRecyclerView(int itemId) {
        beanList = new ArrayList<>();
        PublicModel.getInstance().getFormList(new MSubscribe<BaseEntity<DealWithOptionBean>>() {
            @Override
            public void onNext(BaseEntity<DealWithOptionBean> bean) {
                super.onNext(bean);
                if (beanList.size() != 0) {
                    beanList.clear();
                }
                beanList.addAll(bean.getData().getDispatch_suggest());
                viewDelegate.initLeftTv(beanList.size());
                viewDelegate.initList(beanList);
            }
        }, String.valueOf(itemId));

    }
}
