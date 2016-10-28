package com.shuyu.video.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jokers.payplatform.MyTask;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.NetUtils;
import com.shuyu.video.R;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.db.helper.PaymentDaoHelper;
import com.shuyu.video.model.CreateOrderResult;
import com.shuyu.video.model.OrderInfo;
import com.shuyu.video.model.Payment;
import com.shuyu.video.model.UserInfo;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.DataSignUtils;
import com.shuyu.video.utils.PayUtils;

import java.util.List;

/**
 * Created by zhangleilei on 10/27/16.
 */

public class PayDialogFragment extends DialogFragment {

    private Button btnAliPay;
    private Button btnWeChatPay;
    private View payBackground;
    private TextView tvPrice;
    private TextView tvNewPrice;
    private TextView tvPriceTips;
    private ImageView ivClose;
    private Payment mAliPayPayment;
    private Payment mWeChatPayment;
    private OrderInfo orderInfo;
    private double[] mMoneys;
    private String payUrl = "http://app.6lyy.com/appCharge.aspx";
    private String callBackUrl = "http://121.199.21.125:8009/notice/yikanotify.service";
    private int userRule = 0;

    @Override
    public boolean isCancelable() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View layout = inflater.inflate(R.layout.fragment_pay_dialog, container);
        btnAliPay = (Button) layout.findViewById(R.id.btn_ali_pay);
        btnWeChatPay = (Button) layout.findViewById(R.id.btn_wechat_pay);
        payBackground = layout.findViewById(R.id.rl_pay_bg);
        tvPrice = (TextView) layout.findViewById(R.id.tv_pay_price);
        tvNewPrice = (TextView) layout.findViewById(R.id.tv_pay_new_price);
        tvPriceTips = (TextView) layout.findViewById(R.id.tv_pay_tips);
        ivClose = (ImageView) layout.findViewById(R.id.iv_close);
        tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        getPayment();
        btnAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrderInfo(mAliPayPayment);

            }
        });
        btnWeChatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrderInfo(mWeChatPayment);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return layout;
    }

    private void createOrderInfo(final Payment mPayment) {

        if (mPayment == null) return;

        orderInfo = new OrderInfo();
        orderInfo.setOrderId(PayUtils.createOrderNo());
        orderInfo.setOrderName(tvPriceTips.getText().toString());
        orderInfo.setPartnerId(mPayment.getPartnerId());
        orderInfo.setKey(mPayment.getMd5Key());
        orderInfo.setPrice(mMoneys[1]);

        BaseApi.request(BaseApi.createApi(IPayServiceApi.class)
                        .createOrder(mPayment.getTitle(),
                                1,
                                CommonUtils.getUUID(),
                                orderInfo.getOrderId(),
                                mMoneys[1],
                                mMoneys[1],
                                PayUtils.getPayPoint(userRule),
                                mPayment.getPayType(),
                                mPayment.getPayCompanyCode(),
                                mPayment.getPayCode(),
                                0,
                                CommonUtils.getManufacturer(),
                                NetUtils.getHostIP(),
                                CommonUtils.getChannelNo(),
                                DataSignUtils.getSign()),
                new BaseApi.IResponseListener<CreateOrderResult>() {
                    @Override
                    public void onSuccess(CreateOrderResult data) {
                        LogUtils.d("createOrderInfo", data.getResultMsg());
                        if (mPayment.getPayType() == PayUtils.ALI_PAY) {
                            payAliPay(orderInfo);
                        } else {
                            payWeChat();
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void getPayment() {
        List<Payment> mPayments = PaymentDaoHelper.getHelper().getDataAll();
        if (mPayments != null) {
            for (Payment payment : mPayments) {
                if (payment.getPayType() == PayUtils.WE_CHAT_PAY && mWeChatPayment == null) {
                    mWeChatPayment = payment;
                } else if (payment.getPayType() == PayUtils.ALI_PAY && mAliPayPayment == null) {
                    mAliPayPayment = payment;
                }
            }
        }
    }

    private void payAliPay(OrderInfo mOrderInfo) {
        MyTask task = new MyTask(getActivity(),
                mOrderInfo.getPartnerId(),
                callBackUrl, mOrderInfo.getKey(), mOrderInfo.getOrderId(),
                String.valueOf(mOrderInfo.getPrice()),
                payUrl, mOrderInfo.getOrderName());
        task.execute(payUrl);
    }

    private void payWeChat() {

    }

    @Override
    public void onResume() {
        super.onResume();
        PayUtils.getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                userRule = data.getUserType();
                if (userRule > 0) {
                    payBackground.setBackgroundResource(R.mipmap.bg_pay_dialog_vip);
                } else {
                    payBackground.setBackgroundResource(R.mipmap.bg_pay_dialog_member);
                }
                mMoneys = PayUtils.getPayMoney(userRule);
                if (mMoneys[0] > mMoneys[1]) {
                    tvPrice.setText(String.format("原价：%.2f元", mMoneys[0]));
                }
                tvNewPrice.setText(String.format("特价：%.2f元", mMoneys[1]));
                tvPriceTips.setText(PayUtils.getPayMoneyTips(userRule));
            }

            @Override
            public void onFail() {

            }
        });
    }

}
