package cn.edu.bzu.iteam.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.edu.bzu.iteam.idingfang.R;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

public class LoginActivity extends Activity {
    EditText rootname;
    EditText rootpwd;
    ImageButton btn_login;
    Button btn_register;
    String url = "http://47.104.167.198:8080/HotelServer/";
    private String userid;
    private int loginOrnot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        rootname = (EditText) findViewById(R.id.etloginname);
        rootpwd = (EditText) findViewById(R.id.etloginpwd);
        btn_login = (ImageButton) findViewById(R.id.ibtnlogin);
        btn_register = (Button) findViewById(R.id.btnregit);
        btn_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String name = rootname.getText().toString();
                String pwd = rootpwd.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.username), Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.pwd), Toast.LENGTH_LONG).show();
                    return;
                }
                login(name, pwd);
            }
        });
        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 0);
            }
        });
    }

    public void login(String name, String pwd) {
        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("rootname", name);
        params.put("rootpwd", pwd);
        http.get(url + "login", params, new AjaxCallBack<Object>() {

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(LoginActivity.this, "登录失败，请稍后再试~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
            }

            @Override
            public void onStart() {
                Toast.makeText(LoginActivity.this, "登录中.....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Object t) {

                if (t.toString().startsWith("success")) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    String str = t.toString();
                    userid = (str.substring(8, str.length())) + "";
                    loginOrnot = 1;
                    SharedPreferenceUtils.setUserId(LoginActivity.this, userid);
                    SharedPreferenceUtils.setLoginornot(LoginActivity.this, 1);
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userid", userid);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "您还未注册，请先去注册~", Toast.LENGTH_LONG).show();
                }

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0 && data != null) {
            rootname.setText(data.getStringExtra("rootname"));
            rootpwd.setText(data.getStringExtra("rootpwd"));
            Toast.makeText(this, "注册成功，请登录~", Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent myIntent = new Intent();
//            myIntent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
