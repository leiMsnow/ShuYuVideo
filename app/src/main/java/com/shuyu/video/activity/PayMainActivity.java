package com.shuyu.video.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ipaynow.plugin.api.IpaynowPlugin;
import com.ipaynow.plugin.manager.route.dto.ResponseParams;
import com.ipaynow.plugin.manager.route.impl.ReceivePayResult;
import com.ipaynow.plugin.utils.MerchantTools;
import com.ipaynow.plugin.utils.PreSignMessageUtil;
import com.shuyu.video.R;
import com.shuyu.video.utils.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PayMainActivity extends AppBaseActivity implements ReceivePayResult {

    private Button btnWeChatPay;

    private PreSignMessageUtil preSign = new PreSignMessageUtil();
    //TODO 签名接口，仅供测试时使用，结束请替换成自己的签名生成接口地址
    private static final String GET_ORDER_MESSAGE_URL = "http://posp.ipaynow.cn/ZyPluginPaymentTest_PAY/api/pay2.php";
    //TODO 测试账号仅供开发者测试时使用
    private static final String appID = "1408709961320306";
    private static ProgressDialog progressDialog = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_pay_main;
    }

    @Override
    protected void initData() {
        // 初始化支付插件,传入 context 对象
        IpaynowPlugin.getInstance().init(this);
        //无论微信、qq 安装与否,网关页面都显示渠道按钮。
        IpaynowPlugin.getInstance().unCkeckEnvironment();
        btnWeChatPay = (Button) findViewById(R.id.btn_wechat_pay);
        btnWeChatPay.setOnClickListener(OnPayClick);
    }

    private View.OnClickListener OnPayClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!checkNetInfo()) return;

            showProgressDialog();
            createPayMessage();

            if (v == btnWeChatPay) {
                preSign.payChannelType = "1310";// 微信插件支付
            }
            //获取请求参数并调起支付
            GetMessage gm = new GetMessage();
            gm.execute(preSign.generatePreSignMessage());
        }
    };

    private boolean checkNetInfo() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            Toast.makeText(this, "请检查网络连接状态", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("进度提示");
        progressDialog.setMessage("支付安全环境扫描");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    /**
     * 本地生成订单信息
     *
     */
    private void createPayMessage() {

        preSign.appId = appID;
        preSign.mhtOrderStartTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        preSign.mhtOrderNo = preSign.mhtOrderStartTime;
        preSign.mhtOrderName = "鼠标";
        preSign.mhtOrderType = "01";
        preSign.mhtCurrencyType = "156";
        preSign.mhtOrderAmt = "10";
        preSign.mhtOrderDetail = "关于支付的演示";
        preSign.mhtOrderTimeOut = "3600";
        preSign.notifyUrl = "http://localhost:10802/";
        preSign.mhtCharset = "UTF-8";
        preSign.mhtReserved = "test";
        preSign.consumerId = "456123";
        preSign.consumerName = "yuyang";
    }

    public class GetMessage extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... params) {
            //将订单内容进行拼接生成待签名串
            String preSignStr = preSign.generatePreSignMessage();
            //生成签名串；请在自己服务器进行生成签名，具体请看服务器签名文档
            String signStr = HttpUtil.post(GET_ORDER_MESSAGE_URL,
                    "paydata=" + MerchantTools.urlEncode(preSignStr));
            //支付接口请求参数格式：
            String requestMessage = preSignStr + "&" + signStr;
            return requestMessage;
        }

        protected void onPostExecute(String requestMessage) {
            progressDialog.dismiss();
            //设置支付结果回调接口，同时调起支付请求
            IpaynowPlugin.getInstance().setCallResultReceiver(PayMainActivity.this).pay(requestMessage);
        }
    }

    @Override
    public void onIpaynowTransResult(ResponseParams responseParams) {
        String respCode = responseParams.respCode;
        String errorCode = responseParams.errorCode;
        String errorMsg = responseParams.respMsg;
        StringBuilder temp = new StringBuilder();
        if (respCode.equals("00")) {
            temp.append("交易状态:成功");
        } else if (respCode.equals("02")) {
            temp.append("交易状态:取消");
        } else if (respCode.equals("01")) {
            temp.append("交易状态:失败").append("\n").append("错误码:").append(errorCode).append("原因:" + errorMsg);
        } else if (respCode.equals("03")) {
            temp.append("交易状态:未知").append("\n").append("原因:" + errorMsg);
        } else {
            temp.append("respCode=").append(respCode).append("\n").append("respMsg=").append(errorMsg);
        }
        Toast.makeText(this, "onIpaynowTransResult:" + temp.toString(), Toast.LENGTH_LONG).show();
    }
}
