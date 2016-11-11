package com.shuyu.video.fragment;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.core.BaseDialogFragment;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.NetUtils;
import com.shuyu.core.uils.ToastUtils;
import com.shuyu.core.widget.BaseProgressDialog;
import com.shuyu.video.R;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.model.CreateOrderResult;
import com.shuyu.video.model.OrderInfo;
import com.shuyu.video.model.PayResult;
import com.shuyu.video.model.Payment;
import com.shuyu.video.model.UserInfo;
import com.shuyu.video.pay.IPay;
import com.shuyu.video.pay.PayFactory;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.Constants;
import com.shuyu.video.utils.DataSignUtils;
import com.shuyu.video.utils.PayUtils;

import java.util.List;

import static com.shuyu.video.api.BaseApi.createApi;

/**
 * Created by zhangleilei on 10/27/16.
 */

public class PayDialogFragment extends BaseDialogFragment {

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

    private String mOrderNo;
    private String mPayCode;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_pay_dialog;
    }

    @Override
    protected void init() {
        btnAliPay = (Button) mView.findViewById(R.id.btn_ali_pay);
        btnWeChatPay = (Button) mView.findViewById(R.id.btn_wechat_pay);
        payBackground = mView.findViewById(R.id.v_pay_bg);
        tvPrice = (TextView) mView.findViewById(R.id.tv_pay_price);
        tvNewPrice = (TextView) mView.findViewById(R.id.tv_pay_new_price);
        tvPriceTips = (TextView) mView.findViewById(R.id.tv_pay_tips);
        ivClose = (ImageView) mView.findViewById(R.id.iv_close);
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
    }

    private void createOrderInfo(final Payment payment) {

        if (payment == null) return;
        mPayCode = payment.getPayCode();

        if (mBaseDialog == null) {
            mBaseDialog = new BaseProgressDialog(getContext());
            mBaseDialog.setMessage("正在生成订单...");
            mBaseDialog.show();
        }

        mOrderNo = PayUtils.createOrderNo();

        orderInfo = new OrderInfo((AppCompatActivity) getActivity());
        orderInfo.setOrderId(mOrderNo);
        orderInfo.setOrderName(tvPriceTips.getText().toString());
        orderInfo.setPartnerId(payment.getPartnerId());
        orderInfo.setKey(payment.getMd5Key());
        orderInfo.setPrice(mRebateMoneys);

        BaseApi.request(BaseApi.createApi(IPayServiceApi.class)
                        .createOrder(payment.getTitle(),
                                1,
                                CommonUtils.getUUID(),
                                orderInfo.getOrderId(),
                                mMoneys,
                                mRebateMoneys,
                                PayUtils.getPayPoint(userRule, payDialogBG != 0),
                                payment.getPayType(),
                                payment.getPayCompanyCode(),
                                payment.getPayCode(),
                                PayResult.PAY_STATE_NO_PAY,
                                CommonUtils.getManufacturer(),
                                NetUtils.getHostIP(),
                                CommonUtils.getChannelNo(),
                                DataSignUtils.getSign()),
                new BaseApi.IResponseListener<CreateOrderResult>() {
                    @Override
                    public void onSuccess(CreateOrderResult data) {
                        LogUtils.d("createOrderInfo", data.getResultMsg());
                        IPay pay = PayFactory.create(payment.getPayCode(), orderInfo);
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
        if (mBaseDialog != null && mBaseDialog.isShowing()) {
            mBaseDialog.dismiss();
            mBaseDialog = null;
        }
    }

    private void getPayment() {
        BaseApi.request(createApi(IPayServiceApi.class).selectPayment(),
                new BaseApi.IResponseListener<List<Payment>>() {
                    @Override
                    public void onSuccess(List<Payment> mPayments) {
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
                    public void onFail() {

                    }
                });
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

        if (TextUtils.isEmpty(mOrderNo)) {
            return;
        }
        BaseApi.request(BaseApi.createApi(IPayServiceApi.class).getOrder(mOrderNo),
                new BaseApi.IResponseListener<PayResult>() {
                    @Override
                    public void onSuccess(PayResult data) {
                        if (mPayCode.equals(PayFactory.YI_KA_ALIPAY)) {
                            PayDialogFragment.this.dismiss();
                            return;
                        }
                        if (data.getPayState() == PayResult.PAY_STATE_SUCCESS) {
                            ToastUtils.getInstance().showToast("支付成功");
                        } else if (data.getPayState() == PayResult.PAY_STATE_CANCEL) {
                            ToastUtils.getInstance().showToast("支付取消");
                        } else {
                            ToastUtils.getInstance().showToast("支付失败");
                        }
                        PayDialogFragment.this.dismiss();
                    }

                    @Override
                    public void onFail() {
                        PayDialogFragment.this.dismiss();
                        ToastUtils.getInstance().showToast("查询订单失败，请重新尝试");
                    }
                });
    }
}
