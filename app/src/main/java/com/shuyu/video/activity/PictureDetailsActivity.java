package com.shuyu.video.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shuyu.video.R;
import com.shuyu.video.adapter.PictureAdapter;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IServiceApi;
import com.shuyu.video.model.PictureDetails;
import com.shuyu.video.utils.Constants;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

import butterknife.Bind;

public class PictureDetailsActivity extends AppBaseActivity {

    @Bind(R.id.rv_container)
    RecyclerView mRvContainer;

    private PictureAdapter mPictureAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_picture_details;
    }

    @Override
    protected void initData() {
        int pictureId = getIntent().getIntExtra(Constants.PICTURE_DETAIL_ID, 0);
        if (pictureId == 0) return;

        mPictureAdapter = new PictureAdapter(mContext, null, R.layout.item_picture);
        mRvContainer.setAdapter(mPictureAdapter);
        mRvContainer.setLayoutManager(new GridLayoutManager(mContext, 3));

        mPictureAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Intent intent = new Intent(mContext, PictureShowActivity.class);
                intent.putExtra(Constants.PICTURE_URL, mPictureAdapter.getItem(position).getImgurl());
                startActivity(intent);
            }
        });
        getPictureDetails(pictureId);
    }

    private void getPictureDetails(int groupId) {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getPictureDetails(groupId),
                new BaseApi.IResponseListener<List<PictureDetails>>() {
                    @Override
                    public void onSuccess(List<PictureDetails> data) {
                        mPictureAdapter.replaceAll(data);
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

}
