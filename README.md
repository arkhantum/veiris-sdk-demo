# veiris-sdk-demo


In order to applying this SDK into your application, please check out website https://veiris.com/
contact our support from "Contact us" and tell us what you need.

Certain key must be obtained from our customer support.

To start implementing, implement the dependency from our library distribution repo then add it to 
your application's build.gradle :

                implementation 'com.veiris:lib:1.0.2'

also it needs to mention the repository reference in your application's build.gradle  :

                repositories {
                    ....
                    maven {
                        url  "https://isocorp.bintray.com/maven"
                    }
                }

In your project' build.gradle add below refferences :


                buildscript {
                
                    repositories {
                        ...
                       maven { url "http://dl.bintray.com/isocorpteam/maven" }
                    }
                }

Then your app is ready to use the SDK.


Init your SDK :

                sdkVeiris = new SDKVeiris(this,this,SCAN_KEY);
                
Make sure your activity implement **SDKVeirisInterface** :
    
                public class MainActivity extends AppCompatActivity implements SDKVeirisInterface
                
The interface will help to gather API response result that is used in checking license status,
pre-post confirmation and post validation from your document uploading process.

To do the API call, the SDK use some presenter mechanism as below :
 
        sdkVeiris.getSdkPresenter().setRouter(this);
        //Create Check License Token, The result can be taken through onLicenseDone Interface
        String token = sdkVeiris.getTokenSDKcheck(api_key,credential_code,credential_token);
        //SDK for checking license through API
        sdkVeiris.getSdkPresenter().checkLicense(token);
        //Gather prepost Token
        String prepostToken = sdkVeiris.getPrepostToken(api_key,credential_code,credential_token,refference_id);
        //SDK for doing prepost validation
        sdkVeiris.getSdkPresenter().checkPrepostValidation(prepostToken);
        //Gather Validation Token
        String validationToken = sdkVeiris.getValidationToken(api_key,credential_code,credential_token,refference_id
                ,scan_type);
        //SDK for doing validation
        sdkVeiris.getSdkPresenter().checkValidation(validationToken);

To access Scan Passport use below :

         sdkVeiris.startWithCamera(view);
         
Then get the result from **onActivityResult(int requestCode, int resultCode, Intent data)**
as below : 

                if (requestCode == sdkVeiris.REQUEST_SCAN_ID) {
                    sdkVeiris.checkScanResult(requestCode, resultCode, data);
                    if (sdkVeiris.SCAN_DONE) {
                        Log.v("json array string", sdkVeiris.getJsonResultString());
                        Log.v("IDscanned String", sdkVeiris.getImageString());
                        iv.setImageBitmap(sdkVeiris.getScanIDImage());
                    }
                }
                
String Json result and Base64 String image can be used and processed furthermore.



To access Video Recording use below :

                 sdkVeiris.startVideo();
                 
Once it finished creating 10s face contained video, the onActivityResult would give saving path of 
the video file that can be used for uploading process. Here is how to do so :
 
                if (requestCode == sdkVeiris.REQUEST_RECORD_VIDEO) {
                                tv.setText("Video Location :" + data.getStringExtra("filelocation"));
                }
                
                
Contact us to gather all of your key needed for implementing veiris android apps in your project.
