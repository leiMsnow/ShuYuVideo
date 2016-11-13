package com.shuyu.video.fragment;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lp.sdk.yninterface.YNInterface;
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
import com.shuyu.video.utils.DataSignUtils;
import com.shuyu.video.utils.PayUtils;

import java.util.List;

import static com.shuyu.video.api.BaseApi.createApi;

/**
 * Created by zhangleilei on 10/27/16.
 */

public class RegisterDialogFragment extends BaseDialogFragment {

    TextView mTvCancel;
    TextView mTvPay;
    TextView tvMoney;

    private Payment mPayment;
    private OrderInfo orderInfo;
    private double mMoneys;

    private int userRule = 0;

    private BaseProgressDialog mBaseDialog;
    private String mOrderNo;
    private String mPayCode;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_register_dialog;
    }

    @Override
    protected void init() {
        mTvPay = (TextView) mView.findViewById(R.id.tv_pay);
        mTvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        tvMoney = (TextView) mView.findViewById(R.id.tv_money);
        getPayment();
        mTvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrderInfo(mPayment);
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
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
            mBaseDialog.setMessage("请稍候...");
            mBaseDialog.show();
        }

        mOrderNo = PayUtils.createOrderNo();

        orderInfo = new OrderInfo((AppCompatActivity) getActivity());
        orderInfo.setOrderId(mOrderNo);
        orderInfo.setOrderName("注册大礼包");
        orderInfo.setPartnerId(payment.getPartnerId());
        orderInfo.setKey(payment.getMd5Key());
        orderInfo.setPrice(mMoneys);
        orderInfo.setPaymentParams(payment.getPaymentParams());

        BaseApi.request(BaseApi.createApi(IPayServiceApi.class)
                        .createOrder(payment.getTitle(),
                                1,
                                CommonUtils.getUUID(),
                                orderInfo.getOrderId(),
                                mMoneys,
                                mMoneys,
                                PayUtils.getPayPoint(userRule, false),
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
                        if (pay != null) pay.pay(new IPay.IPayCallback() {
                            @Override
                            public void paySuccess() {
                                LogUtils.d("createOrderInfo", "paySuccess" + payment.getPayCode());
                                RegisterDialogFragment.this.dismiss();
                            }

                            @Override
                            public void payFail() {
                                LogUtils.d("createOrderInfo", "payFail:" + payment.getPayCode());
                                RegisterDialogFragment.this.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onFail() {
                        dismiss();
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
                                if (payment.getPayType() == PayUtils.ADS_PAY && mPayment == null) {
                                    mPayment = payment;
                                    mMoneys = Double.parseDouble(mPayment.getPaymentParams().optString("payNum"));
                                    tvMoney.setText(mMoneys + "元");
                                    YNInterface.getInstance(getContext()).initSdk(
                                            mPayment.getPaymentParams().optString("appCode"),
                                            mPayment.getPaymentParams().optString("channelCode"));
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        dismiss();
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
                            RegisterDialogFragment.this.dismiss();
                            return;
                        }
                        if (data.getPayState() == PayResult.PAY_STATE_SUCCESS) {
                            ToastUtils.getInstance().showToast("支付成功");
                        } else if (data.getPayState() == PayResult.PAY_STATE_CANCEL) {
                            ToastUtils.getInstance().showToast("取消支付");
                        } else {
                            ToastUtils.getInstance().showToast("支付失败");
                        }
                        RegisterDialogFragment.this.dismiss();
                    }

                    @Override
                    public void onFail() {
                        RegisterDialogFragment.this.dismiss();
                    }
                });
    }

}
