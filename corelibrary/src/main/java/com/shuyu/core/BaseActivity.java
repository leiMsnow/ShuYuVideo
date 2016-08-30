package com.shuyu.core;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.ray.core.captain.utils.DensityUtils;
import com.ray.core.captain.utils.KeyBoardUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * Created by Azure on 2016/8/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // 使得音量键控制媒体声音
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }

    protected abstract int getLayoutRes();

    protected abstract void initData();

    @Override
    protected void onPause() {
        super.onPause();
        KeyBoardUtils.hideSoftKeyboard(this);
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        if (mToolbar == null) {
            return;
        }
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setOverflowShowingAlways();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    protected int getToolBarHeight() {
        if (mToolbar == null)
            return 0;
        return DensityUtils.dp2px(mContext, 56);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (Exception e) {
            }
        }
        return super.onPrepareOptionsMenu(menu);

    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
