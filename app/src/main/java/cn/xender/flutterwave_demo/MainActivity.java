package cn.xender.flutterwave_demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;

public class MainActivity extends AppCompatActivity {

    static boolean isProd = true;

    static String PublicKey = "FLWPUBK_TEST-116d0ab7a697b7a8c4e6bcba2a52da27-X";
    static String EncryptionKey = "FLWSECK_TESTcedae0097637";
    static boolean onStagingEnv = true;

    static {
        if (isProd){
            PublicKey = "FLWPUBK-6790074903692e260195911c502ffc98-X";
            EncryptionKey = "6b0ec0713e277b68e667f6cc";
            onStagingEnv = false;
        }
    }

    int KE = 1;
    int GH = 2;
    int UG = 3;
    int US = 4;

    int i = 1;  /** ---Rayn 调这里 */

    String country;
    String currency;
    boolean acceptAccountPayments = true;
    boolean acceptMpesaPayments;
    boolean acceptGHMobileMoneyPayments;
    boolean acceptUgMobileMoneyPayments;
    boolean acceptAchPayments;
    boolean acceptBankTransferPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (i == KE){
            country = "KE";
            currency = "KES";
            acceptMpesaPayments = true;
        }else if (i == GH){
            country = "GH";
            currency = "GHS";
            acceptGHMobileMoneyPayments = true;
        } else if (i == UG){
            country = "UG";
            currency = "UGX";
            acceptUgMobileMoneyPayments = true;
        } else if (i == US){    /** ---Rayn 我们的商户不可用US */
            country = "US";
            currency = "USD";
            acceptAchPayments = true;
            acceptAccountPayments = true;
        }



        findViewById(R.id.bt_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RavePayManager(MainActivity.this).setAmount(0.1)    //Required double
                        .setCountry(country)  //Required
                        .setCurrency(currency) //Required
                        .setEmail("gojjdawangrui@gmail.com")      //Required
                        .setfName("JOY")        //Required
                        .setlName("Adanu")        //Required
                        .setNarration("narrationnnn")  //description
                        .setPublicKey(PublicKey).setEncryptionKey(EncryptionKey)    //Required
                        .setTxRef("fwtid" + System.currentTimeMillis())        //Required
                        .acceptAccountPayments(acceptAccountPayments)
                        .acceptCardPayments(true)
                        .acceptMpesaPayments(acceptMpesaPayments)      //KE KES
                        .acceptGHMobileMoneyPayments(acceptGHMobileMoneyPayments)      //GH GHS
                        .acceptUgMobileMoneyPayments(acceptUgMobileMoneyPayments)      //UG UGX
                        .acceptAchPayments(acceptAchPayments)        //US USD 并且acceptAccountPayments为true
                        .acceptBankTransferPayments(acceptBankTransferPayments)     //Nigerian Naira 比较特殊 先不管
                        .onStagingEnv(onStagingEnv)
//                        .setMeta(List<Meta>)
//                        .withTheme(styleId)
//                        .isPreAuth(false)
//                        .setSubAccounts(List<SubAccount>)
//                        .shouldDisplayFee(false)
//                        .showStagingLabel(false)
                        .initialize();
            }
        });
    }

    /**
     * Rayn 余额不足：SDK里面Http的请求结果 只显示toast，保持在sdk支付页面不动
     * {
     *     "status":"error",
     *     "message":"Insufficient+Funds",
     *     "data":{
     *         "code":"FLW_ERR",
     *         "message":"Insufficient+Funds",
     *         "tx":{
     *             "id":96876620,
     *             "txRef":"fwtid1566987799648",
     *             "orderRef":"URF_1566987824876_2260635",
     *             "flwRef":"FLW171816717",
     *             "redirectUrl":"https://rave-webhook.herokuapp.com/receivepayment",
     *             "device_fingerprint":"865441038936209",
     *             "settlement_token":null,
     *             "cycle":"one-time",
     *             "amount":1,
     *             "charged_amount":1,
     *             "appfee":0.03,
     *             "merchantfee":0,
     *             "merchantbearsfee":1,
     *             "chargeResponseCode":"5",
     *             "raveRef":"RV31566987823701AE49110F0E",
     *             "chargeResponseMessage":"Insufficient+Funds",
     *             "authModelUsed":"NOAUTH",
     *             "currency":"GHS",
     *             "IP":"::ffff:10.31.122.8",
     *             "narration":"CARD Transaction ",
     *             "status":"failed",
     *             "modalauditid":"0a361d07bf3b73509380b879383f5c09",
     *             "vbvrespmessage":"Insufficient+Funds",
     *             "authurl":"N/A",
     *             "vbvrespcode":"5",
     *             "acctvalrespmsg":null,
     *             "acctvalrespcode":null,
     *             "paymentType":"card",
     *             "paymentPlan":null,
     *             "paymentPage":null,
     *             "paymentId":"4054779",
     *             "fraud_status":"ok",
     *             "charge_type":"normal",
     *             "is_live":0,
     *             "retry_attempt":null,
     *             "getpaidBatchId":null,
     *             "createdAt":"2019-08-28T10:23:44.000Z",
     *             "updatedAt":"2019-08-28T10:23:49.000Z",
     *             "deletedAt":null,
     *             "customerId":67286245,
     *             "AccountId":75135
     *         }
     *     }
     * }
     */



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String TAG = "onActivityResult";
        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
         */
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            System.out.println(TAG + "---Rayn resultCode:" + resultCode + " message:" + message);
            /**
             * Rayn 成功
             * {
             *     "status":"Transaction successfully fetched",
             *     "message":"Tx Fetched",
             *     "data":{
             *         "id":771046,
             *         "txRef":"fwtid1566982043350",
             *         "orderRef":"URF_1566982229282_4988935",
             *         "flwRef":"FLW-MOCK-ddaf17b7a917bfc69f563ea9b626e969",
             *         "redirectUrl":"https://rave-webhook.herokuapp.com/receivepayment",
             *         "device_fingerprint":"865441038936209",
             *         "settlement_token":null,
             *         "cycle":"one-time",
             *         "amount":1,
             *         "charged_amount":1,
             *         "appfee":1.48,
             *         "merchantfee":0,
             *         "merchantbearsfee":1,
             *         "chargeResponseCode":"00",
             *         "raveRef":"RV3156698222786104D87DA56A",
             *         "chargeResponseMessage":"Please enter the OTP sent to your mobile number 080****** and email te**@rave**.com",
             *         "authModelUsed":"ACCESS_OTP",
             *         "currency":"GHS",
             *         "IP":"::ffff:10.31.101.58",
             *         "narration":"CARD Transaction ",
             *         "status":"successful",  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             *         "modalauditid":"115e462be1ed37788c6cefb9b6c94f50",
             *         "vbvrespmessage":"successful", !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             *         "authurl":"https://ravesandboxapi.flutterwave.com/mockvbvpage?ref=FLW-MOCK-ddaf17b7a917bfc69f563ea9b626e969&code=00&message=Approved. Successful&receiptno=RN1566982229364",
             *         "vbvrespcode":"00",
             *         "acctvalrespmsg":null,
             *         "acctvalrespcode":null,
             *         "paymentType":"card",
             *         "paymentPlan":null,
             *         "paymentPage":null,
             *         "paymentId":"989",
             *         "fraud_status":"ok",
             *         "charge_type":"normal",
             *         "is_live":0,
             *         "retry_attempt":null,
             *         "getpaidBatchId":null,
             *         "createdAt":"2019-08-28T08:50:29.000Z",
             *         "updatedAt":"2019-08-28T08:54:23.000Z",
             *         "deletedAt":null,
             *         "customerId":182688,
             *         "AccountId":70801,
             *         "customer.id":182688,
             *         "customer.phone":null,
             *         "customer.fullName":"JOY Adanu",
             *         "customer.customertoken":null,
             *         "customer.email":"gojjdawangrui@gmail.com",
             *         "customer.createdAt":"2019-08-28T08:50:28.000Z",
             *         "customer.updatedAt":"2019-08-28T08:50:28.000Z",
             *         "customer.deletedAt":null,
             *         "customer.AccountId":70801,
             *         "meta":[
             *
             *         ],
             *         "flwMeta":{
             *
             *         }
             *     }
             * }
             */

            /**
             * Rayn 失败 Function Not Permitted to Cardholder
             * {
             *     "status":"Transaction successfully fetched",
             *     "message":"Tx Fetched",
             *     "data":{
             *         "id":771109,
             *         "txRef":"fwtid1566984086576",
             *         "orderRef":"URF_1566984143442_8215135",
             *         "flwRef":"FLW-MOCK-b18fe18fbafb296f49e292780d2d63bb",
             *         "redirectUrl":"https://rave-webhook.herokuapp.com/receivepayment",
             *         "device_fingerprint":"865441038936209",
             *         "settlement_token":null,
             *         "cycle":"one-time",
             *         "amount":1,
             *         "charged_amount":1,
             *         "appfee":1.48,
             *         "merchantfee":0,
             *         "merchantbearsfee":1,
             *         "chargeResponseCode":"02",
             *         "raveRef":"RV31566984142020B65D03A11A",
             *         "chargeResponseMessage":"Function Not Permitted to Cardholder",
             *         "authModelUsed":"VBVSECURECODE",
             *         "currency":"GHS",
             *         "IP":"::ffff:10.63.213.53",
             *         "narration":"CARD Transaction ",
             *         "status":"failed", !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             *         "modalauditid":"de9c65febcefad1bba6ed6cdb8b9eb7d",
             *         "vbvrespmessage":"Function Not Permitted to Cardholder",!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             *         "authurl":"https://ravesandboxapi.flutterwave.com/mockvbvpage?ref=FLW-MOCK-b18fe18fbafb296f49e292780d2d63bb&code=RR-57&message=Function-Not-Permitted-to-Cardholder&receiptno=RN1566984143523",
             *         "vbvrespcode":"RR-57",
             *         "acctvalrespmsg":null,
             *         "acctvalrespcode":"RN1566984143523",
             *         "paymentType":"card",
             *         "paymentPlan":null,
             *         "paymentPage":null,
             *         "paymentId":"11050",
             *         "fraud_status":"ok",
             *         "charge_type":"normal",
             *         "is_live":0,
             *         "retry_attempt":null,
             *         "getpaidBatchId":null,
             *         "createdAt":"2019-08-28T09:22:23.000Z",
             *         "updatedAt":"2019-08-28T09:22:33.000Z",
             *         "deletedAt":null,
             *         "customerId":182718,
             *         "AccountId":70801,
             *         "customer.id":182718,
             *         "customer.phone":null,
             *         "customer.fullName":"JOY Adanu",
             *         "customer.customertoken":null,
             *         "customer.email":"gojjdawangrui@gmail.com",
             *         "customer.createdAt":"2019-08-28T09:22:22.000Z",
             *         "customer.updatedAt":"2019-08-28T09:22:22.000Z",
             *         "customer.deletedAt":null,
             *         "customer.AccountId":70801,
             *         "meta":[
             *
             *         ],
             *         "flwMeta":{
             *
             *         }
             *     }
             * }
             */

            /**
             * Rayn 失败 Function Not Permitted to Terminal
             * {
             *     "status":"Transaction successfully fetched",
             *     "message":"Tx Fetched",
             *     "data":{
             *         "id":771125,
             *         "txRef":"fwtid1566984474613",
             *         "orderRef":"URF_1566984497804_4358235",
             *         "flwRef":"FLW-MOCK-340e853819b566435747ad87f1ed1ff6",
             *         "redirectUrl":"https://rave-webhook.herokuapp.com/receivepayment",
             *         "device_fingerprint":"865441038936209",
             *         "settlement_token":null,
             *         "cycle":"one-time",
             *         "amount":1,
             *         "charged_amount":1,
             *         "appfee":1.48,
             *         "merchantfee":0,
             *         "merchantbearsfee":1,
             *         "chargeResponseCode":"02",
             *         "raveRef":"RV31566984496375182E91E659",
             *         "chargeResponseMessage":"Function Not Permitted to Terminal",
             *         "authModelUsed":"VBVSECURECODE",
             *         "currency":"GHS",
             *         "IP":"::ffff:10.30.30.223",
             *         "narration":"CARD Transaction ",
             *         "status":"failed",!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             *         "modalauditid":"c75536667997d938c5242a8cc826f865",
             *         "vbvrespmessage":"Function Not Permitted to Terminal",!!!!!!!!!!!!!!!!!!!!!!!!1
             *         "authurl":"https://ravesandboxapi.flutterwave.com/mockvbvpage?ref=FLW-MOCK-340e853819b566435747ad87f1ed1ff6&code=RR-58&message=Function-Not-Permitted-to-Terminal&receiptno=RN1566984497882",
             *         "vbvrespcode":"RR-58",
             *         "acctvalrespmsg":null,
             *         "acctvalrespcode":"RN1566984497882",
             *         "paymentType":"card",
             *         "paymentPlan":null,
             *         "paymentPage":null,
             *         "paymentId":"11021",
             *         "fraud_status":"ok",
             *         "charge_type":"normal",
             *         "is_live":0,
             *         "retry_attempt":null,
             *         "getpaidBatchId":null,
             *         "createdAt":"2019-08-28T09:28:17.000Z",
             *         "updatedAt":"2019-08-28T09:28:25.000Z",
             *         "deletedAt":null,
             *         "customerId":182723,
             *         "AccountId":70801,
             *         "customer.id":182723,
             *         "customer.phone":null,
             *         "customer.fullName":"JOY Adanu",
             *         "customer.customertoken":null,
             *         "customer.email":"gojjdawangrui@gmail.com",
             *         "customer.createdAt":"2019-08-28T09:28:16.000Z",
             *         "customer.updatedAt":"2019-08-28T09:28:16.000Z",
             *         "customer.deletedAt":null,
             *         "customer.AccountId":70801,
             *         "meta":[
             *
             *         ],
             *         "flwMeta":{
             *
             *         }
             *     }
             * }
             */

            /**
             * Rayn 失败 Transaction-Error
             * {
             *     "status":"Transaction successfully fetched",
             *     "message":"Tx Fetched",
             *     "data":{
             *         "id":771118,
             *         "txRef":"fwtid1566984343900",
             *         "orderRef":"URF_1566984377944_2903535",
             *         "flwRef":"FLW-MOCK-a9ee919d3bf5b881ae642e324aed91bd",
             *         "redirectUrl":"https://rave-webhook.herokuapp.com/receivepayment",
             *         "device_fingerprint":"865441038936209",
             *         "settlement_token":null,
             *         "cycle":"one-time",
             *         "amount":1,
             *         "charged_amount":1,
             *         "appfee":1.48,
             *         "merchantfee":0,
             *         "merchantbearsfee":1,
             *         "chargeResponseCode":"02",
             *         "raveRef":"RV31566984376568B4C91F9DD0",
             *         "chargeResponseMessage":"Transaction Error",
             *         "authModelUsed":"VBVSECURECODE",
             *         "currency":"GHS",
             *         "IP":"::ffff:10.102.251.6",
             *         "narration":"CARD Transaction ",
             *         "status":"failed",!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             *         "modalauditid":"73965e31b29172c0dae0e05112e64163",
             *         "vbvrespmessage":"Transaction-Error",!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             *         "authurl":"https://ravesandboxapi.flutterwave.com/mockvbvpage?ref=FLW-MOCK-a9ee919d3bf5b881ae642e324aed91bd&code=RR-Z1&message=Transaction-Error&receiptno=RN1566984378021",
             *         "vbvrespcode":"RR-Z1",
             *         "acctvalrespmsg":null,
             *         "acctvalrespcode":"RN1566984378021",
             *         "paymentType":"card",
             *         "paymentPlan":null,
             *         "paymentPage":null,
             *         "paymentId":"11022",
             *         "fraud_status":"ok",
             *         "charge_type":"normal",
             *         "is_live":0,
             *         "retry_attempt":null,
             *         "getpaidBatchId":null,
             *         "createdAt":"2019-08-28T09:26:17.000Z",
             *         "updatedAt":"2019-08-28T09:26:25.000Z",
             *         "deletedAt":null,
             *         "customerId":182721,
             *         "AccountId":70801,
             *         "customer.id":182721,
             *         "customer.phone":null,
             *         "customer.fullName":"JOY Adanu",
             *         "customer.customertoken":null,
             *         "customer.email":"gojjdawangrui@gmail.com",
             *         "customer.createdAt":"2019-08-28T09:26:17.000Z",
             *         "customer.updatedAt":"2019-08-28T09:26:17.000Z",
             *         "customer.deletedAt":null,
             *         "customer.AccountId":70801,
             *         "meta":[
             *
             *         ],
             *         "flwMeta":{
             *
             *         }
             *     }
             * }
             */



            if (resultCode == RavePayActivity.RESULT_SUCCESS) {//111

            } else if (resultCode == RavePayActivity.RESULT_ERROR) {//222

            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {//333

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}





































