package cn.edu.bzu.iteam.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import cn.edu.bzu.iteam.idingfang.R;

public class MyEvaluateActivity extends Activity {

    EditText input_evalaute;
    Button evalaute_finish;

    private String url = "http://47.104.167.198:8080/HotelServer/addcommit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluate);
        evalaute_finish = (Button) findViewById(R.id.evalaute_finish);
        input_evalaute = (EditText) findViewById(R.id.input_evalaute);

        Intent data = getIntent();
        if (data == null) {
            return;
        }

        final String id = data.getStringExtra("id");
        final String uid = data.getStringExtra("uid");


        evalaute_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commit = input_evalaute.getText().toString();
                if (!TextUtils.isEmpty(commit)) {
                    evalaute(id, uid, commit);
                } else {
                    Toast.makeText(MyEvaluateActivity.this, "请先输入你的评论", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void evalaute(String id, String uid, String commit) {
        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("uid", uid);
        params.put("hid", id);
        params.put("commit", commit);
        http.get(url, params, new AjaxCallBack<Object>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
            }

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                if (o.toString().startsWith("success")) {
                    Toast.makeText(MyEvaluateActivity.this, "评论成功", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    //System.out.println(t.toString() + "123456");
                    Toast.makeText(MyEvaluateActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }
}
