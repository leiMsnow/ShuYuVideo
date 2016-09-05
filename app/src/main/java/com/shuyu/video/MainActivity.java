package com.shuyu.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.shuyu.core.BaseActivity;
import com.shuyu.core.widget.ChangeColorView;
import com.shuyu.video.main.fragment.MainFragment;
import com.shuyu.video.main.fragment.UserCenterFragment;
import com.shuyu.video.main.fragment.PrivateFragment;
import com.shuyu.video.main.fragment.VipFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseActivity {

    @Bind(R.id.ccv_main)
    ChangeColorView mCcvMain;
    @Bind(R.id.ccv_vip)
    ChangeColorView mCcvVip;
    @Bind(R.id.ccv_private)
    ChangeColorView mCcvPrivate;
    @Bind(R.id.ccv_user)
    ChangeColorView mCcvUser;

    private List<ChangeColorView> changeColorViews = null;
    private List<Fragment> fragments = null;
    private Fragment mContent;
    private String[] tags = {"main", "vip", "private", "my"};
    private int[] titles = {R.string.nav_main, R.string.nav_vip, R.string.nav_private, R.string.nav_me};

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main_page;
    }

    @Override
    protected void initData() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initMenuFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDefaultFragment(savedInstanceState);
        setTitle(titles[0]);
    }

    private void initDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragments = new ArrayList<>();
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            MainFragment mainFragment = MainFragment.newInstance();
            VipFragment vipFragment = VipFragment.newInstance();
            PrivateFragment privateFragment = PrivateFragment.newInstance();
            UserCenterFragment myFragment = UserCenterFragment.newInstance();
            mContent = mainFragment;
            fts.add(R.id.fl_container, mainFragment, tags[0]);
            fts.add(R.id.fl_container, vipFragment, tags[1]);
            fts.add(R.id.fl_container, privateFragment, tags[2]);
            fts.add(R.id.fl_container, myFragment, tags[3]);

            fts.show(mainFragment)
                    .hide(vipFragment)
                    .hide(privateFragment)
                    .hide(myFragment)
                    .commitAllowingStateLoss();

            fragments.add(mainFragment);
            fragments.add(vipFragment);
            fragments.add(privateFragment);
            fragments.add(myFragment);

        } else {
            MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                    .findFragmentByTag(tags[0]);
            VipFragment vipFragment = (VipFragment) getSupportFragmentManager()
                    .findFragmentByTag(tags[1]);
            PrivateFragment privateFragment = (PrivateFragment) getSupportFragmentManager()
                    .findFragmentByTag(tags[2]);
            UserCenterFragment myFragment = (UserCenterFragment) getSupportFragmentManager()
                    .findFragmentByTag(tags[3]);
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(mainFragment)
                    .hide(vipFragment)
                    .hide(privateFragment)
                    .hide(myFragment)
                    .commitAllowingStateLoss();
        }
    }

    private void initMenuFragment() {
        changeColorViews = new ArrayList<>();
        changeColorViews.add(mCcvMain);
        changeColorViews.add(mCcvVip);
        changeColorViews.add(mCcvPrivate);
        changeColorViews.add(mCcvUser);

        for (int i = 0; i < changeColorViews.size(); i++) {
            changeColorViews.get(i).setOnClickListener(new OnButtonMenuClickListener(i));
        }
        // 首页高亮显示
        changeColorViews.get(0).setIconAlpha(1.0f);
    }

    class OnButtonMenuClickListener implements View.OnClickListener {

        int position = -1;

        OnButtonMenuClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (position == -1)
                return;
            setMenuFragment(position);
        }
    }

    private void setMenuFragment(int position) {
        if (changeColorViews != null) {
            for (int i = 0; i < changeColorViews.size(); i++) {
                changeColorViews.get(i).setIconAlpha(0);
            }
            changeColorViews.get(position).setIconAlpha(1.0f);
            if (fragments != null)
                switchContent(mContent, fragments.get(position), position);
        }
    }

    /**
     * fragment 切换
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to, int position) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(from)
                        .add(R.id.fl_container, to, tags[position]).commitAllowingStateLoss();
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
            setTitle(titles[position]);
        }
    }
}
