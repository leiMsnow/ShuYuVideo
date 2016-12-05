package com.shuyu.video.fragment;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shuyu.core.BaseDialogFragment;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.NetUtils;
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

import java.util.ArrayList;

/**
 * Created by zhangleilei on 10/27/16.
 */
public class SPDialogFragment extends BaseDialogFragment {

    private Button btnPay;
    private ImageView ivClose;

    private ArrayList<Payment> mPaymentList = new ArrayList<>();
    private OrderInfo orderInfo;
    private int userRule = 0;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_ads_dialog;
    }

    @Override
    protected void init() {
        btnPay = (Button) mView.findViewById(R.id.btn_pay);
        ivClose = (ImageView) mView.findViewById(R.id.iv_close);
        if (getArguments() != null) {
            mPaymentList = getArguments().getParcelableArrayList(Constants.PAYMENY_LIST);
        }
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPaymentList != null && !mPaymentList.isEmpty()) {
                    for (Payment payment : mPaymentList) {
                        createOrderInfo(payment);
                    }
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

        orderInfo = new OrderInfo((AppCompatActivity) getActivity());
        orderInfo.setOrderId(PayUtils.createOrderNo());
        orderInfo.setOrderName("首充大礼包");
        orderInfo.setPartnerId(payment.getPartnerId());
        orderInfo.setKey(payment.getMd5Key());
        String payNum = payment.getPaymentParams().optString("payNum");
        if (TextUtils.isEmpty(payNum)) {
            payNum = "-1";
        }
        orderInfo.setPrice(Double.parseDouble(payNum));
        orderInfo.setPaymentParams(payment.getPaymentParams());
        orderInfo.setPayUrl(payment.getPayUrl());

        LogUtils.d("Payment", payment.toString());
        LogUtils.d("OrderInfo", orderInfo.toString());

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
                                DataSignUtils.getSign(),
                                CommonUtils.getTelNumber()),
                new BaseApi.IResponseListener<CreateOrderResult>() {
                    @Override
                    public void onSuccess(int code, CreateOrderResult data) {
                        if (code == BaseApi.RESCODE_FAILURE) {
                            dismiss();
                            return;
                        }
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
                                dismiss();
                            }

                        });
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        PayUtils.getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(int code, UserInfo data) {
                if (code == BaseApi.RESCODE_FAILURE) {
                    return;
                }
                userRule = data.getUserType();
            }
        });
    }
}
