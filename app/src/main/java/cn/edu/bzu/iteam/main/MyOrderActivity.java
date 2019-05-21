package cn.edu.bzu.iteam.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.bzu.iteam.idingfang.R;

public class MyOrderActivity extends Activity {

    ListView listView;
    ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        listView = (ListView) findViewById(R.id.myorder_listview);
        myoderList();
    }

    private void myoderList() {
        if (SharedPreferenceUtils.getLoginornot(this) != 1) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        FinalHttp http = new FinalHttp();
        String url = "http://47.104.167.198:8080/HotelServer/orderlist";
        AjaxParams params = new AjaxParams();
        params.put("uid", SharedPreferenceUtils.getUserId(this, "3"));
        http.get(url, params, new AjaxCallBack<Object>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
//                Toast.makeText(MyOrderActivity.this, "正在载入订单信息,请稍后...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Object o) {
                list = (ArrayList<Map<String, String>>) JSON.parseObject(o.toString(), List.class);
//
                MyAdapter adapter = new MyAdapter(list, MyOrderActivity.this);
                listView.setAdapter(adapter);// 添加适配器
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(MyOrderActivity.this, "获取数据失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myoderList();
    }

    private String id;
    private String photelname;//p为post要转到下一个activity的值
    private String photeltype;
    private String photelprice;
    private String photelnum;
    private String photelstatus;
    private String isevaluate;
    private String photelevaluate;

    class MyAdapter extends BaseAdapter {

        List list;
        Context context;

        MyAdapter(List list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(MyOrderActivity.this).inflate(R.layout.item_my_order, null);
            TextView order_name = (TextView) convertView.findViewById(R.id.order_name);
            TextView oder_price = (TextView) convertView.findViewById(R.id.oder_price);
            TextView order_num = (TextView) convertView.findViewById(R.id.order_num);
            TextView order_status = (TextView) convertView.findViewById(R.id.order_status);
            TextView order_type = (TextView) convertView.findViewById(R.id.order_type);
            TextView order_evaluate = (TextView) convertView.findViewById(R.id.order_evaluate);
            TextView order_note = (TextView) convertView.findViewById(R.id.order_note);
            TextView order_checkout = (TextView) convertView.findViewById(R.id.order_checkout);


            id = ((Map) list.get(position)).get("id").toString();
            photelname = ((Map) list.get(position)).get("hotelname") + "";
            photeltype = ((Map) list.get(position)).get("hoteltype") + "";
            photelprice = ((Map) list.get(position)).get("hotelprice") + "";
            photelnum = ((Map) list.get(position)).get("hotelnum") + "";
            photelstatus = ((Map) list.get(position)).get("photelstatus") + "";
            photelevaluate = ((Map) list.get(position)).get("photelevaluate") + "";
            isevaluate = ((Map) list.get(position)).get("isevaluate") + "";

            if ("0".equals(isevaluate)) {
                order_checkout.setVisibility(View.VISIBLE);
                order_note.setVisibility(View.GONE);
                order_evaluate.setVisibility(View.GONE);
            } else if ("1".equals(isevaluate)) {//退房未评价

                order_note.setVisibility(View.GONE);
                order_evaluate.setVisibility(View.VISIBLE);
                order_evaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyOrderActivity.this, MyEvaluateActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("uid", SharedPreferenceUtils.getUserId(MyOrderActivity.this, "3"));
                        startActivity(intent);
                    }
                });
            } else {
                order_note.setVisibility(View.VISIBLE);
                order_evaluate.setVisibility(View.GONE);
                order_note.setText(photelevaluate);
                order_checkout.setVisibility(View.GONE);
            }
            final String hotelid = ((Map) list.get(position)).get("hotelid").toString();
            order_checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkOut(id, hotelid);
                }
            });
            order_name.setText("商家：" + photelname);
            oder_price.setText(photelprice + "元");
            order_num.setText("房间号：" + photelnum);
            order_status.setText(photelstatus);
            order_type.setText("房型：" + photeltype);

            return convertView;
        }
    }

    private void checkOut(String id, String hotelid) {
        FinalHttp http = new FinalHttp();
        String url = "http://47.104.167.198:8080/HotelServer/outuserhotel";
        AjaxParams params = new AjaxParams();
        params.put("oid", id);
        params.put("hid", hotelid);
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
                Toast.makeText(MyOrderActivity.this, "退房成功", Toast.LENGTH_SHORT).show();
                myoderList();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Toast.makeText(MyOrderActivity.this, "退房失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
