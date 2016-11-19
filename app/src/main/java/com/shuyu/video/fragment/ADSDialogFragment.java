package com.shuyu.video.fragment;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shuyu.core.BaseDialogFragment;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.NetUtils;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangleilei on 10/27/16.
 */

public class ADSDialogFragment extends BaseDialogFragment {

    private Button btnPay;
    private List<Payment> mPaymentList = new ArrayList<>();
    private OrderInfo orderInfo;

    private int userRule = 0;

    private BaseProgressDialog mBaseDialog;
    private ImageView ivClose;

    private int currentPayments = 0;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_ads_dialog;
    }

    @Override
    protected void init() {
        btnPay = (Button) mView.findViewById(R.id.btn_pay);
        ivClose = (ImageView) mView.findViewById(R.id.iv_close);
        if (getArguments() != null) {
            mPaymentList = getArguments().getParcelableArrayList("PaymentList");
        }
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPaymentList != null && !mPaymentList.isEmpty()) {
                    createOrderInfo(mPaymentList.get(currentPayments));
                } else {
                    dismiss();
                }
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

        if (mBaseDialog == null) {
            mBaseDialog = new BaseProgressDialog(getContext());
            mBaseDialog.setMessage("正在领取礼包...");
            mBaseDialog.show();
        }

        orderInfo = new OrderInfo((AppCompatActivity) getActivity());
        orderInfo.setOrderId(PayUtils.createOrderNo());
        orderInfo.setOrderName("首充大礼包");
        orderInfo.setPartnerId(payment.getPartnerId());
        orderInfo.setKey(payment.getMd5Key());
        orderInfo.setPrice(Double.parseDouble(payment
                .getPaymentParams().optString("payNum")));
        orderInfo.setPaymentParams(payment.getPaymentParams());

        BaseApi.request(BaseApi.createApi(IPayServiceApi.class)
                        .createOrder(payment.getTitle(),
                                1,
                                CommonUtils.getUUID(),
                                orderInfo.getOrderId(),
                                orderInfo.getPrice(),
                                orderInfo.getPrice(),
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
                                dismiss();
                            }

                            @Override
                            public void payFail() {
                                LogUtils.d("createOrderInfo", "payFail:" + payment.getPayCode());
                                currentPayments = currentPayments + 1;
                                if (currentPayments >= mPaymentList.size()) {
                                    dismiss();
                                } else {
                                    createOrderInfo(mPaymentList.get(currentPayments));
                                }
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
    }
}
