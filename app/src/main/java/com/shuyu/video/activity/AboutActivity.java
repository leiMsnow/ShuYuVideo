package com.shuyu.video.activity;

import android.widget.TextView;

import com.shuyu.core.uils.AppUtils;
import com.shuyu.video.R;

import butterknife.Bind;

public class AboutActivity extends AppBaseActivity {

    @Bind(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        String version = getString(R.string.app_name)+"\nV"+ AppUtils.getVersionName();
        mTvVersion.setText(version);
    }

}
