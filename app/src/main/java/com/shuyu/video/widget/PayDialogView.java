package com.shuyu.video.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.R;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.db.helper.PaymentDaoHelper;
import com.shuyu.video.model.CreateOrderResult;
import com.shuyu.video.model.Payment;
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
        private int payCodeIndex = 0;
        private List<Payment> mPayments;

        public Builder(Context context) {
            this.mContext = context;
        }

        private PayDialogView create() {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.view_pay_dialog, null);
            btnPay = (Button) layout.findViewById(R.id.btn_pay);
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

                }
            });

            mPayments = PaymentDaoHelper.getHelper().getDataAll();

            return dialog;
        }

        public PayDialogView show() {
            PayDialogView dialog = create();
            dialog.show();
            initWindow(dialog);
            createOrderInfo();
            return dialog;
        }

        // TODO: 2016/10/20  弹窗下单
        private void createOrderInfo() {

            String orderNo = DialogUtils.createOrderNo();
            String payCode = getPayCode();
            String sign = DataSignUtils.getSign();
            BaseApi.request(BaseApi.createApi(IPayServiceApi.class)
                            .createOrder(orderNo, payCode, sign),
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

        private String getPayCode() {
            if (mPayments != null && mPayments.size() > payCodeIndex) {
                String payCode = mPayments.get(payCodeIndex).getPayCode();
                payCodeIndex++;
                return payCode;
            }
            return "";
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
