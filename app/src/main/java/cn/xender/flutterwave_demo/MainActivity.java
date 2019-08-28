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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RavePayManager(MainActivity.this).setAmount(1)    //Required double
                        .setCountry("GH")  //Required
                        .setCurrency("GHS") //Required
                        .setEmail("gojjdawangrui@gmail.com")      //Required
                        .setfName("JOY")        //Required
                        .setlName("Adanu")        //Required
//                .setNarration(narration)
                        .setPublicKey("FLWPUBK-6790074903692e260195911c502ffc98-X").setEncryptionKey("6b0ec0713e277b68e667f6cc")    //Required
//                        .setPublicKey("FLWPUBK_TEST-116d0ab7a697b7a8c4e6bcba2a52da27-X").setEncryptionKey("FLWSECK_TESTcedae0097637")    //Required
                        .setTxRef("fwtid" + System.currentTimeMillis())        //Required
                        .acceptAccountPayments(true)
                        .acceptCardPayments(true)
                        .acceptMpesaPayments(true)
                        .acceptAchPayments(true)
                        .acceptGHMobileMoneyPayments(true)
                        .acceptUgMobileMoneyPayments(true)
                        .acceptBankTransferPayments(true)
                        .onStagingEnv(false)
//                .setMeta(List<Meta>)
//                .withTheme(styleId)
//                .isPreAuth(false)
//                .setSubAccounts(List<SubAccount>)
//                .shouldDisplayFee(false)
//                .showStagingLabel(false)
                        .initialize();
            }
        });
    }

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
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

/**
 * Rayn 成功的onActivityResult
 * {
 * "status":"Transaction successfully fetched",
 * "message":"Tx Fetched",
 * "data":{
 * "id":427398,
 * "txRef":"my-ref",
 * "orderRef":"URF_1549613005326_2869035",
 * "flwRef":"FLW-MOCK-481dc616a228f87e5b0a5e7166db26bd",
 * "redirectUrl":"https://rave-webhook.herokuapp.com/receivepayment",
 * "device_fingerprint":"358240051111110",
 * "settlement_token":null,
 * "cycle":"one-time",
 * "amount":120,
 * "charged_amount":120,
 * "appfee":2050.68,
 * "merchantfee":0,
 * "merchantbearsfee":1,
 * "chargeResponseCode":"00",
 * "raveRef":"RV31549613003870757FA0908E",
 * "chargeResponseMessage":"Please enter the OTP sent to your mobile number 080****** and email te**@rave**.com",
 * "authModelUsed":"VBVSECURECODE",
 * "currency":"NGN",
 * "IP":"41.86.149.44:7575",
 * "narration":"CARD Transaction ",
 * <p>
 * "status":"successful", 注意这里的status
 * <p>
 * "modalauditid":"5a024e6424e8f3b302e90b81dba5fe67",
 * <p>
 * "vbvrespmessage":"Approved. Successful",     还有这里的message
 * <p>
 * "authurl":"https://ravesandbox.azurewebsites.net/mockvbvpage?ref=FLW-MOCK-481dc616a228f87e5b0a5e7166db26bd&code=00&message=Approved. Successful&receiptno=RN1549613005428",
 * "vbvrespcode":"00",
 * "acctvalrespmsg":null,
 * "acctvalrespcode":"RN1549613005428",
 * "paymentType":"card",
 * "paymentPlan":null,
 * "paymentPage":null,
 * "paymentId":"989",
 * "fraud_status":"ok",
 * "charge_type":"normal",
 * "is_live":0,
 * "createdAt":"2019-02-08T08:03:25.000Z",
 * "updatedAt":"2019-02-08T08:03:58.000Z",
 * "deletedAt":null,
 * "customerId":83809,
 * "AccountId":9070,
 * "customer.id":83809,
 * "customer.phone":null,
 * "customer.fullName":"Bolaji Kassim",
 * "customer.customertoken":null,
 * "customer.email":"kas@flw.com",
 * "customer.createdAt":"2019-02-08T08:03:24.000Z",
 * "customer.updatedAt":"2019-02-08T08:03:24.000Z",
 * "customer.deletedAt":null,
 * "customer.AccountId":9070,
 * "meta":[
 * <p>
 * ],
 * "flwMeta":{
 * <p>
 * }
 * }
 * }
 * <p>
 * Rayn 失败的onActivityResult
 * {
 * "status":"Transaction successfully fetched",
 * "message":"Tx Fetched",
 * "data":{
 * "id":27272462,
 * "txRef":"ref",
 * "orderRef":"URF_1549621287102_6793935",
 * "flwRef":"FLW117494557",
 * "redirectUrl":"https://rave-webhook.herokuapp.com/receivepayment",
 * "device_fingerprint":"358240051111110",
 * "settlement_token":null,
 * "cycle":"one-time",
 * "amount":120,
 * "charged_amount":120,
 * "appfee":1.68,
 * "merchantfee":0,
 * "merchantbearsfee":1,
 * "chargeResponseCode":"02",
 * "raveRef":"RV31549621285692729CEF5F12",
 * "chargeResponseMessage":"Kindly enter the OTP sent to *******9550 and k*******@aol.com. OR enter the OTP generated on your Hardware Token device.",
 * "authModelUsed":"GTB_OTP",
 * "currency":"NGN",
 * "IP":"41.86.149.34:57327",
 * "narration":"CARD Transaction ",
 * <p>
 * "status":"failed",   注意这里的status
 * <p>
 * "modalauditid":"46251589b2a5e08a3a9402b170b3b536",
 * <p>
 * "vbvrespmessage":"Token Authentication Failed. Incorrect Token Supplied.",   还有这里的message
 * <p>
 * "authurl":"N/A",
 * "vbvrespcode":"RR",
 * "acctvalrespmsg":null,
 * "acctvalrespcode":null,
 * "paymentType":"card",
 * "paymentPlan":null,
 * "paymentPage":null,
 * "paymentId":"1130261",
 * "fraud_status":"ok",
 * "charge_type":"normal",
 * "is_live":0,
 * "createdAt":"2019-02-08T10:21:27.000Z",
 * "updatedAt":"2019-02-08T10:21:45.000Z",
 * "deletedAt":null,
 * "customerId":11751463,
 * "AccountId":26140,
 * "customer.id":11751463,
 * "customer.phone":null,
 * "customer.fullName":"Bolaji Kassim",
 * "customer.customertoken":null,
 * "customer.email":"kas@email.com",
 * "customer.createdAt":"2019-02-08T10:21:26.000Z",
 * "customer.updatedAt":"2019-02-08T10:21:26.000Z",
 * "customer.deletedAt":null,
 * "customer.AccountId":26140,
 * "meta":[
 * <p>
 * ],
 * "flwMeta":{
 * <p>
 * }
 * }
 * }
 */


/**
 * Rayn 失败的onActivityResult
 * {
 *     "status":"Transaction successfully fetched",
 *     "message":"Tx Fetched",
 *     "data":{
 *         "id":27272462,
 *         "txRef":"ref",
 *         "orderRef":"URF_1549621287102_6793935",
 *         "flwRef":"FLW117494557",
 *         "redirectUrl":"https://rave-webhook.herokuapp.com/receivepayment",
 *         "device_fingerprint":"358240051111110",
 *         "settlement_token":null,
 *         "cycle":"one-time",
 *         "amount":120,
 *         "charged_amount":120,
 *         "appfee":1.68,
 *         "merchantfee":0,
 *         "merchantbearsfee":1,
 *         "chargeResponseCode":"02",
 *         "raveRef":"RV31549621285692729CEF5F12",
 *         "chargeResponseMessage":"Kindly enter the OTP sent to *******9550 and k*******@aol.com. OR enter the OTP generated on your Hardware Token device.",
 *         "authModelUsed":"GTB_OTP",
 *         "currency":"NGN",
 *         "IP":"41.86.149.34:57327",
 *         "narration":"CARD Transaction ",
 *
 *         "status":"failed",   注意这里的status
 *
 *         "modalauditid":"46251589b2a5e08a3a9402b170b3b536",
 *
 *         "vbvrespmessage":"Token Authentication Failed. Incorrect Token Supplied.",   还有这里的message
 *
 *         "authurl":"N/A",
 *         "vbvrespcode":"RR",
 *         "acctvalrespmsg":null,
 *         "acctvalrespcode":null,
 *         "paymentType":"card",
 *         "paymentPlan":null,
 *         "paymentPage":null,
 *         "paymentId":"1130261",
 *         "fraud_status":"ok",
 *         "charge_type":"normal",
 *         "is_live":0,
 *         "createdAt":"2019-02-08T10:21:27.000Z",
 *         "updatedAt":"2019-02-08T10:21:45.000Z",
 *         "deletedAt":null,
 *         "customerId":11751463,
 *         "AccountId":26140,
 *         "customer.id":11751463,
 *         "customer.phone":null,
 *         "customer.fullName":"Bolaji Kassim",
 *         "customer.customertoken":null,
 *         "customer.email":"kas@email.com",
 *         "customer.createdAt":"2019-02-08T10:21:26.000Z",
 *         "customer.updatedAt":"2019-02-08T10:21:26.000Z",
 *         "customer.deletedAt":null,
 *         "customer.AccountId":26140,
 *         "meta":[
 *
 *         ],
 *         "flwMeta":{
 *
 *         }
 *     }
 * }
 */