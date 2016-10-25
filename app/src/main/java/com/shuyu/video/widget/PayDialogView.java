package com.shuyu.video.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.NetUtils;
import com.shuyu.video.R;
import com.shuyu.video.activity.PayMainActivity;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.db.helper.PaymentDaoHelper;
import com.shuyu.video.model.CreateOrderResult;
import com.shuyu.video.model.Payment;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.DataSignUtils;
import com.shuyu.video.utils.DialogUtils;

import java.util.List;

/**
 * 支付dialog
 *
 * @author zhangleilei
 */
public class PayDialogView extends Dialog {

    private PayDialogView(Context context) {
        super(context);
    }

    public static class Builder {
        private Context mContext;
        private Button btnPay;
        private TextView tvPrice;
        private TextView tvNewPrice;
        private TextView tvPriceTips;
        private int payCodeIndex = 0;
        private List<Payment> mPayments;
        private double[] mMoneys;

        public Builder(Context context) {
            this.mContext = context;
        }

        private PayDialogView create() {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.view_pay_dialog, null);
            btnPay = (Button) layout.findViewById(R.id.btn_pay);
            tvPrice = (TextView) layout.findViewById(R.id.tv_pay_price);
            tvNewPrice = (TextView) layout.findViewById(R.id.tv_pay_new_price);
            tvPriceTips = (TextView) layout.findViewById(R.id.tv_pay_tips);

            tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            final PayDialogView dialog = new PayDialogView(mContext);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            //隐藏标题栏,必须在setContentView()前调用
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setContentView(layout);

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PayMainActivity.class);
                    mContext.startActivity(intent);
                    dialog.dismiss();
                }
            });

            mPayments = PaymentDaoHelper.getHelper().getDataAll();
            mMoneys = DialogUtils.getPayMoney(mContext);
            if (mMoneys[0] > mMoneys[1]) {
                tvPrice.setText(String.format("原价：%.2f元", mMoneys[0]));
            }
            tvNewPrice.setText(String.format("特价：%.2f元", mMoneys[1]));
            tvPriceTips.setText(DialogUtils.getPayMoneyTips(mContext));
            return dialog;
        }

        public PayDialogView show() {
            PayDialogView dialog = create();
            dialog.show();
            initWindow(dialog);
            createOrderInfo();
            return dialog;
        }

        private void createOrderInfo() {

            Payment payment = getPayment();
            if (payment == null) return;

            BaseApi.request(BaseApi.createApi(IPayServiceApi.class)
                            .createOrder(payment.getTitle(),
                                    1,
                                    CommonUtils.getUUID(),
                                    DialogUtils.createOrderNo(),
                                    mMoneys[1],
                                    mMoneys[1],
                                    DialogUtils.getPayPoint(mContext),
                                    payment.getPayType(),
                                    payment.getPayCompanyCode(),
                                    payment.getPayCode(),
                                    0,
                                    CommonUtils.getManufacturer(),
                                    NetUtils.getHostIP(),
                                    CommonUtils.getChannelNo(),
                                    DataSignUtils.getSign()),
                    new BaseApi.IResponseListener<CreateOrderResult>() {
                        @Override
                        public void onSuccess(CreateOrderResult data) {
                            LogUtils.d(PayDialogView.class.getName(), data.getResultMsg());
                        }

                        @Override
                        public void onFail() {

                        }
                    });
        }

        private Payment getPayment() {
            if (mPayments != null && mPayments.size() > payCodeIndex) {
                Payment payCode = mPayments.get(payCodeIndex);
                payCodeIndex++;
                return payCode;
            }
            return null;
        }

        private void initWindow(Dialog dialog) {
            /*
             * 获取框的窗口对象及参数对象以修改对话框的布局设置,
             * 可以直接调用getWindow(),表示获得这个Activity的Window
             * 对象,这样这可以以同样的方式改变这个Activity的属性.
             */
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();

            lp.gravity = Gravity.CENTER;
            lp.x = 0; // 新位置X坐标
            lp.y = 0; // 新位置Y坐标
            lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 高度
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 高度

            // 当Window的Attributes改变时系统会调用此函数,
            // 可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
            dialogWindow.setAttributes(lp);
        }
    }
}
