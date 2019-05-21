package cn.edu.bzu.iteam.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.bzu.iteam.idingfang.R;

public class PayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        TextView money = (TextView) findViewById(R.id.money);
        money.setText("您要支付的金额为：" + getIntent().getStringExtra("money") + "元");
        findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
