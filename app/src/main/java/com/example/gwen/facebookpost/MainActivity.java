package com.example.gwen.facebookpost;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class MainActivity extends AppCompatActivity {

    Button buttonCompartir;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        printKeyHash();

        buttonCompartir=(Button) findViewById(R.id.btnCompartirLink);

        callbackManager=CallbackManager.Factory.create();
        shareDialog=new ShareDialog(this);

        buttonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShareLinkContent linkContent=new ShareLinkContent.Builder().setQuote("Android Developers").setContentUrl(Uri.parse("https://developer.android.com/?hl=es-419")).build();

                if (shareDialog.canShow(ShareLinkContent.class)){


                    shareDialog.show(linkContent);

                }



            }
        });

    }



    private void printKeyHash() {

        try {

            PackageInfo info=getPackageManager().getPackageInfo("com.example.gwen.facebookpost" ,
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures){

                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));




            }

        }catch (PackageManager.NameNotFoundException e){

            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();



        }


    }


}
