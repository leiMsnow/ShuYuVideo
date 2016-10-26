package com.shuyu.video.activity;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;

import com.example.jokers.payplatform.MyTask;
import com.shuyu.video.R;
import com.shuyu.video.model.OrderInfo;
import com.shuyu.video.utils.Constants;

public class PayMainActivity extends AppBaseActivity {

    private Button btnWeChatPay;
    private Button btnAliPay;

    private String url = "http://app.6lyy.com/appCharge.aspx";
    private String callbackurl = "http://121.199.21.125:8009/notice/yikanotify.service";

    ProgressDialog dialog;

    private OrderInfo mOrderInfo;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_pay_main;
    }

    @Override
    protected void initData() {
        // 必须先初始化
        mOrderInfo = getIntent().getExtras().getParcelable(Constants.KEY_ORDER_INFO);
        btnWeChatPay = (Button) findViewById(R.id.btn_wechat_pay);
        btnAliPay = (Button) findViewById(R.id.btn_ali_pay);
        btnWeChatPay.setOnClickListener(OnPayClick);
        btnAliPay.setOnClickListener(OnPayClick);

    }

    private View.OnClickListener OnPayClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnWeChatPay) {
                weChatPay();
            } else if (v == btnAliPay) {
                payAliPay();
            }
        }
    };

    private void payAliPay() {
        MyTask task = new MyTask(this,
                mOrderInfo.getPartnerId(),
                callbackurl, mOrderInfo.getKey(), mOrderInfo.getOrderId(),
                String.valueOf(mOrderInfo.getPrice()),
                url, mOrderInfo.getOrderName());
        task.execute(url);
    }


    /**
     * 调用支付
     */
    private void weChatPay() {
    }

}
