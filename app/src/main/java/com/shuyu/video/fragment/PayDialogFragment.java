package com.shuyu.video.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.NetUtils;
import com.shuyu.core.widget.BaseProgressDialog;
import com.shuyu.video.R;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.db.helper.PaymentDaoHelper;
import com.shuyu.video.model.CreateOrderResult;
import com.shuyu.video.model.OrderInfo;
import com.shuyu.video.model.Payment;
import com.shuyu.video.model.UserInfo;
import com.shuyu.video.pay.IPay;
import com.shuyu.video.pay.PayFactory;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.Constants;
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
    private double mMoneys;
    private double mRebateMoneys;

    private int userRule = 0;
    private int payDialogBG = 0;

    private BaseProgressDialog mBaseDialog;

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
        payBackground = layout.findViewById(R.id.v_pay_bg);
        tvPrice = (TextView) layout.findViewById(R.id.tv_pay_price);
        tvNewPrice = (TextView) layout.findViewById(R.id.tv_pay_new_price);
        tvPriceTips = (TextView) layout.findViewById(R.id.tv_pay_tips);
        ivClose = (ImageView) layout.findViewById(R.id.iv_close);
        tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (getArguments() != null)
            payDialogBG = getArguments().getInt(Constants.KEY_PAY_DIALOG, 0);
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

        if (mBaseDialog == null) {
            mBaseDialog = new BaseProgressDialog(getContext());
            mBaseDialog.setMessage("正在生成订单...");
            mBaseDialog.show();
        }

        orderInfo = new OrderInfo((AppCompatActivity) getActivity());
        orderInfo.setOrderId(PayUtils.createOrderNo());
        orderInfo.setOrderName(tvPriceTips.getText().toString());
        orderInfo.setPartnerId(mPayment.getPartnerId());
        orderInfo.setKey(mPayment.getMd5Key());
        orderInfo.setPrice(mRebateMoneys);

        BaseApi.request(BaseApi.createApi(IPayServiceApi.class)
                        .createOrder(mPayment.getTitle(),
                                1,
                                CommonUtils.getUUID(),
                                orderInfo.getOrderId(),
                                mMoneys,
                                mRebateMoneys,
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
                        IPay pay = PayFactory.create(mPayment.getPayCode(), orderInfo);
                        if (pay != null) pay.pay(null);
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBaseDialog.isShowing()) {
            mBaseDialog.dismiss();
            mBaseDialog = null;
        }
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
            if (mWeChatPayment == null) {
                btnWeChatPay.setVisibility(View.GONE);
            }
            if (mAliPayPayment == null) {
                btnAliPay.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        PayUtils.getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                userRule = data.getUserType();
                payBackground.setBackgroundResource(PayUtils.getPayDialogBG(userRule));
                if (payDialogBG != 0) {
                    payBackground.setBackgroundResource(payDialogBG);
                }
                mMoneys = PayUtils.getPayRebateMoney(userRule, false, payDialogBG != 0);
                mRebateMoneys = PayUtils.getPayRebateMoney(userRule, true, payDialogBG != 0);
                if (mMoneys > mRebateMoneys) {
                    tvPrice.setText(String.format("原价：%.2f元", mMoneys));
                }
                tvNewPrice.setText(String.format("特价：%.2f元", mRebateMoneys));
                tvPriceTips.setText(PayUtils.getPayMoneyTips(userRule, payDialogBG != 0));
            }

            @Override
            public void onFail() {

            }
        });
    }

}
