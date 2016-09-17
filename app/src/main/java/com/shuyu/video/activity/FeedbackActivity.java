package com.shuyu.video.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.ToastUtils;
import com.shuyu.video.R;
import com.shuyu.video.api.IServiceApi;
import com.shuyu.video.model.FeedbackEntity;

import butterknife.Bind;

public class FeedbackActivity extends AppBaseActivity {

    @Bind(R.id.et_feedback)
    EditText mEtFeedback;
    @Bind(R.id.et_tel)
    EditText mEtTel;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEtFeedback.getText().toString().trim())) {
                    ToastUtils.getInstance().showToast("请输入您的意见");
                    return;
                }
                if (TextUtils.isEmpty(mEtTel.getText().toString().trim())) {
                    ToastUtils.getInstance().showToast("请输入您的联系方式");
                    return;
                }
                submitFeedback(mEtFeedback.getText().toString(),mEtTel.getText().toString());
            }
        });
    }

    private void submitFeedback(String content,String contact){
        BaseApi.request(BaseApi.createApi(IServiceApi.class).feedback(content, contact),
                new BaseApi.IResponseListener<FeedbackEntity>() {
                    @Override
                    public void onSuccess(FeedbackEntity data) {
                        ToastUtils.getInstance().showToast("感谢您的反馈");
                        finish();
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }
}
