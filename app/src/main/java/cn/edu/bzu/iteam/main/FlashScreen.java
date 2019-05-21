package cn.edu.bzu.iteam.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import cn.edu.bzu.iteam.idingfang.R;

public class FlashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashscreen);


        SharedPreferenceUtils.setLoginornot(this, 0);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent();
                SharedPreferences shared = getSharedPreferences("flag", Context.MODE_PRIVATE);
                String str = shared.getString("flag", "0");
                if (str.equals("0")) {
                    intent.setClass(getApplicationContext(), GuideActivity.class);
                } else {
                    intent.setClass(getApplicationContext(), MainActivity.class);
                }


                startActivity(intent);
                finish();

            }
        }, 4000);
    }

}
