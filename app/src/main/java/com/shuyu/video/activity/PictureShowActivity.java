package com.shuyu.video.activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.video.R;
import com.shuyu.video.utils.Constants;

import butterknife.Bind;

public class PictureShowActivity extends AppBaseActivity {

    @Bind(R.id.iv_picture_url)
    ImageView mIvPictureUrl;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_picture;
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            String pictureUrl = getIntent().getStringExtra(Constants.PICTURE_URL);
            Glide.with(mContext).load(pictureUrl).error(R.mipmap.ic_default_image).into(mIvPictureUrl);
        }
    }
}
