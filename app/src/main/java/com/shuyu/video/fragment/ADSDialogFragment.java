package com.shuyu.video.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.lp.sdk.yninterface.YNInterface;
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

public class ADSDialogFragment extends DialogFragment {

    private View payBackground;
    private ImageView ivClose;
    private Payment mPayment;
    private OrderInfo orderInfo;
    private double mMoneys;
    private double mRebateMoneys;

    private int userRule = 0;

    private BaseProgressDialog mBaseDialog;

    private String mOrderNo;
    private String mPayCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        YNInterface.getInstance(getContext()).initSdk("0005000001", "000500");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View layout = inflater.inflate(R.layout.fragment_ads_dialog, container);
        payBackground = layout.findViewById(R.id.v_pay_bg);
        ivClose = (ImageView) layout.findViewById(R.id.iv_close);
        getPayment();
        payBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrderInfo(mPayment);
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
        orderInfo.setOrderName("");
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
                                PayUtils.getPayPoint(userRule,false),
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
                payBackground.setBackgroundResource(PayUtils.getPayDialogBG(userRule));
                mMoneys = PayUtils.getPayRebateMoney(userRule, false, false);
                mRebateMoneys = PayUtils.getPayRebateMoney(userRule, true, false);
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
                            ADSDialogFragment.this.dismiss();
                            return;
                        }
                        if (data.getPayState() == PayResult.PAY_STATE_SUCCESS) {
                            ToastUtils.getInstance().showToast("支付成功");
                        } else if (data.getPayState() == PayResult.PAY_STATE_CANCEL) {
                            ToastUtils.getInstance().showToast("支付取消");
                        } else {
                            ToastUtils.getInstance().showToast("支付失败");
                        }
                        ADSDialogFragment.this.dismiss();
                    }

                    @Override
                    public void onFail() {
                        ADSDialogFragment.this.dismiss();
                        ToastUtils.getInstance().showToast("查询订单失败，请重新尝试");
                    }
                });
    }
}
