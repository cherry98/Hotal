package cn.edu.bzu.iteam.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import cn.edu.bzu.iteam.idingfang.R;

public class    RegisterActivity extends Activity {

    EditText rootname;
    EditText rootpwd;
    Button btn_register;

    String url = "http://47.104.167.198:8080/HotelServer/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rootname = (EditText) findViewById(R.id.etloginname);
        rootpwd = (EditText) findViewById(R.id.etloginpwd);
        btn_register = (Button) findViewById(R.id.btnregit);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = rootname.getText().toString();
                String pwd = rootpwd.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.username), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.pwd), Toast.LENGTH_LONG).show();
                    return;
                }
                register(name, pwd);
            }
        });
    }

    private void register(String name, String pwd) {
        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("rootname", name);
        params.put("rootpwd", pwd);
        http.get(url + "reg", params, new AjaxCallBack<Object>() {

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(RegisterActivity.this, "注册失败，请稍后再试~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
            }

            @Override
            public void onStart() {
                Toast.makeText(RegisterActivity.this, "注册中.....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Object t) {

                if (t.toString().startsWith("success")) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.putExtra("rootname", rootname.getText().toString());
                    intent.putExtra("rootpwd", rootpwd.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }

        });
    }
}
