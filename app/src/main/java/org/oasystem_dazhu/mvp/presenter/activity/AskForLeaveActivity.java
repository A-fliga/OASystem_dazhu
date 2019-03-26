package org.oasystem_dazhu.mvp.presenter.activity;

import org.oasystem_dazhu.mvp.view.AskForLeaveDelegate;

/**
 * Created by www on 2019/3/25.
 */

public class AskForLeaveActivity extends ActivityPresenter<AskForLeaveDelegate> {
    @Override
    public Class<AskForLeaveDelegate> getDelegateClass() {
        return AskForLeaveDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }
}
