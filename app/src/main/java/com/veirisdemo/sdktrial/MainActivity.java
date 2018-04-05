package com.veirisdemo.sdktrial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.veiris.lib.SDKVeiris;
import com.veiris.lib.SDKVeirisInterface;


public class MainActivity extends AppCompatActivity implements SDKVeirisInterface {
    private SDKVeiris sdkVeiris;
    private TextView tv;
    private TextView tvTokenResult,tvLicenseCode,tvLicenseType,tvLicenseStatus,tvPrepostResult
            ,tvExecutionDate,tvValidationResult,tvValidationExecutionDate;
    private ImageView iv;
    String api_key = "";
    String credential_code ="";
    String credential_token="";
    String refference_id = "";
    String scan_type = "";
    String SCAN_KEY="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.video_location_path);
        iv = (ImageView)findViewById(R.id.imageViewID);
        tvTokenResult = (TextView)findViewById(R.id.tokenView);
        tvLicenseCode=(TextView)findViewById(R.id.licenseCode);
        tvLicenseType=(TextView)findViewById(R.id.licenseType);
        tvLicenseStatus=(TextView)findViewById(R.id.licenseStatus);
        tvPrepostResult=(TextView)findViewById(R.id.prepostResult);
        tvExecutionDate=(TextView)findViewById(R.id.prepostExecutionDate);
        tvValidationResult=(TextView)findViewById(R.id.validationResult);
        tvValidationExecutionDate=(TextView)findViewById(R.id.validationExecutionDate);
        //init SDK veiris config;
        sdkVeiris = new SDKVeiris(this,this,SCAN_KEY);
        //add below to get result on interface
        sdkVeiris.getSdkPresenter().setRouter(this);
        //Create Check License Token, The result can be taken through onLicenseDone Interface
        String token = sdkVeiris.getTokenSDKcheck(api_key,credential_code,credential_token);
        //SDK for checking license through API
        sdkVeiris.getSdkPresenter().checkLicense(token);
//
        //Gather prepost Token
        String prepostToken = sdkVeiris.getPrepostToken(api_key,credential_code,credential_token,refference_id);
        //SDK for doing prepost validation
        sdkVeiris.getSdkPresenter().checkPrepostValidation(prepostToken);


        //Gather Validation Token
        String validationToken = sdkVeiris.getValidationToken(api_key,credential_code,credential_token,refference_id
                ,scan_type);
        //SDK for doing validation
        sdkVeiris.getSdkPresenter().checkValidation(validationToken);
    }
    public void scanId(View view){
        /*
            Activating scan ID activities with camera
            The result can be gathered on activity result
            the request code is REQUEST_SCAN_ID
        */
        sdkVeiris.startWithCamera(view);

    }
    public void recordFace(View view){
        /*
            Activating recording camera
            The result can be gathered on activity result
            the request code is REQUEST_RECORD_VIDEO
        */
        sdkVeiris.startVideo();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == sdkVeiris.REQUEST_SCAN_ID) {
                sdkVeiris.checkScanResult(requestCode, resultCode, data);
                if (sdkVeiris.SCAN_DONE) {
                    Log.v("json array string", sdkVeiris.getJsonResultString());
                    Log.v("IDscanned String", sdkVeiris.getImageString());
                    iv.setImageBitmap(sdkVeiris.getScanIDImage());
                }
            } else if (requestCode == sdkVeiris.REQUEST_RECORD_VIDEO) {
                tv.setText("Video Location :" + data.getStringExtra("filelocation"));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onLicenseDone(String result, String licenseCode, String licenseType, String licenseStatus) {
        tvTokenResult.setText(result);
        tvLicenseCode.setText(licenseCode);
        tvLicenseType.setText(licenseType);
        tvLicenseStatus.setText(licenseStatus);
    }

    @Override
    public void onPrepostCheckDone(String prepostCheckResult, String executionDate) {
        tvPrepostResult.setText(prepostCheckResult);
        tvExecutionDate.setText("Prepost "+executionDate);
    }

    @Override
    public void onValidationDone(String validationResult, String validationDate) {
        tvValidationResult.setText(validationResult);
        tvValidationExecutionDate.setText("Validation "+validationDate);

    }
}
