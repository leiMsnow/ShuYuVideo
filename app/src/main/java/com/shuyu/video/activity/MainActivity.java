package com.shuyu.video.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shuyu.core.widget.ChangeColorView;
import com.shuyu.video.R;
import com.shuyu.video.fragment.MainFragment;
import com.shuyu.video.fragment.RecommendFragment;
import com.shuyu.video.fragment.UserCenterFragment;
import com.shuyu.video.fragment.VipFragment;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.PayUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MainActivity extends AppBaseActivity {

    @Bind(R.id.ccv_main)
    ChangeColorView mCcvMain;
    @Bind(R.id.ccv_vip)
    ChangeColorView mCcvVip;
    @Bind(R.id.ccv_private)
    ChangeColorView mCcvPrivate;
    @Bind(R.id.ccv_user)
    ChangeColorView mCcvUser;

    private List<Fragment> mFragments = null;
    private List<ChangeColorView> mChangeColorViews = null;
    private int[] mTitles = {R.string.nav_main, R.string.nav_vip, R.string.nav_recommend, R.string.nav_me};

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main_page;
    }

    @Override
    protected void initData() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        initDefaultFragment();
        setTitle(mTitles[0]);
        initBottomMenu();

        PayUtils.showGiftPayDialog(mContext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            Intent intent = new Intent(mContext, SearchActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_gift) {
            setBottomMenu(2);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDefaultFragment() {
        mFragments = new ArrayList<>();
        UserCenterFragment userCenterFragment = UserCenterFragment.newInstance();
        mFragments.add(MainFragment.newInstance());
        mFragments.add(VipFragment.newInstance());
        mFragments.add(RecommendFragment.newInstance());
        mFragments.add(userCenterFragment);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            ft.add(R.id.fl_container, mFragments.get(i));
            if (i > 0) ft.hide(mFragments.get(i));
        }
        ft.commit();
        userCenterFragment.setRecommendListener(new UserCenterFragment.IRecommendListener() {
            @Override
            public void recommendTab() {
                setBottomMenu(2);
            }
        });
    }

    private void initBottomMenu() {
        mChangeColorViews = new ArrayList<>();
        mChangeColorViews.add(mCcvMain);
        mChangeColorViews.add(mCcvVip);
        mChangeColorViews.add(mCcvPrivate);
        mChangeColorViews.add(mCcvUser);
        for (int i = 0; i < mChangeColorViews.size(); i++) {
            mChangeColorViews.get(i).setOnClickListener(new OnButtonMenuClickListener(i));
        }
        mChangeColorViews.get(0).setIconAlpha(1.0f);
    }

    class OnButtonMenuClickListener implements View.OnClickListener {
        int position;

        OnButtonMenuClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            setBottomMenu(position);
        }
    }

    private void setBottomMenu(int position) {
        if (mChangeColorViews != null) {
            for (int i = 0; i < mChangeColorViews.size(); i++) {
                mChangeColorViews.get(i).setIconAlpha(0);
            }
            setTitle(mTitles[position]);
            mChangeColorViews.get(position).setIconAlpha(1.0f);
            switchContent(position);
        }
    }

    public void switchContent(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            ft.hide(mFragments.get(i));
        }
        ft.show(mFragments.get(position));
        ft.commit();
    }
}
