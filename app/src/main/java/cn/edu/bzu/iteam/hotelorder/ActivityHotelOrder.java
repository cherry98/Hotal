package cn.edu.bzu.iteam.hotelorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import cn.edu.bzu.iteam.idingfang.R;
import cn.edu.bzu.iteam.main.LoginActivity;
import cn.edu.bzu.iteam.main.MainActivity;
import cn.edu.bzu.iteam.main.PayActivity;
import cn.edu.bzu.iteam.main.SharedPreferenceUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityHotelOrder extends Activity {
    //@ViewInject(id = R.id.btnHotelOrd, click = "order")
    private ImageButton btnhotelorder;
    private Gallery gallery;
    private String ghotelid = "20";//用于获取在酒店信息简介activity上带过来的值   酒店id
//    private String guserid = "7";//	用户id
    private String ghotelname;
    private String ghoteltype;
    private String ghotelprice;
    private String ghotelsize;
    private String ghotelnum;
    private String ghotelmount;

    private TextView hotelname;
    private TextView hoteltype;
    private TextView hotelprice;
    private TextView hotelsize;
    private TextView hotelnum;
    private TextView hotelmount;
    private MyListView mylistview;

    String url = "http://47.104.167.198:8080/HotelServer/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_hotel_order);


        gallery = (Gallery) findViewById(R.id.mygallery);
        //设置图片适配器  
        gallery.setAdapter(new ImageAdapter(this));


        btnhotelorder = (ImageButton) findViewById(R.id.btnHotelOrd);
        hotelname = (TextView) findViewById(R.id.tvHotelName);
        hoteltype = (TextView) findViewById(R.id.tvHotelType);
        hotelprice = (TextView) findViewById(R.id.tvHotelPrice);
        hotelsize = (TextView) findViewById(R.id.tvHotelSize);
        hotelnum = (TextView) findViewById(R.id.tvhotelnum);
        hotelmount = (TextView) findViewById(R.id.tvhotelmount);
        mylistview = (MyListView) findViewById(R.id.mylistview);

        btnhotelorder.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                order(arg0);
//				Toast.makeText(ActivityHotelOrder.this, "订单成功", Toast.LENGTH_SHORT).show();
            }
        });


        //获取MainActivity中在网络获取的hotelname
        //获取activity传值
        ghotelid = this.getIntent().getExtras().getString("hotelid");
        System.out.println("-----------------------------" + ghotelid);
        ghotelname = this.getIntent().getExtras().getString("hotelname");
        ghoteltype = this.getIntent().getExtras().getString("hoteltype");
        ghotelprice = this.getIntent().getExtras().getString("hotelprice");
        ghotelsize = this.getIntent().getExtras().getString("hotelsize");
        ghotelnum = this.getIntent().getExtras().getString("hotelnum");
        ghotelmount = this.getIntent().getExtras().getString("hotelount");

//		System.out.println("------------------这是宾馆ID-----------------"+ghotelid);

        hotelname.setText("宾馆：" + ghotelname);
        hoteltype.setText("类型：" + ghoteltype);
        hotelprice.setText("价格：" + ghotelprice + "元/天");
        hotelsize.setText("面积：" + ghotelsize + "m²");
        hotelnum.setText("房间号：" + ghotelnum);
        hotelmount.setText("仅剩：" + ghotelmount);

        getCommitList();
    }

    private void getCommitList() {
        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("hid", ghotelid);
        http.get(url + "gethotelcommit", params, new AjaxCallBack<Object>() {
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
                ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) JSON.parseObject(o.toString(), List.class);
                MyAdapter myAdapter = new MyAdapter(ActivityHotelOrder.this, list);
                mylistview.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }


    class ImageAdapter extends BaseAdapter {
        //声明Context
        private Context context;
        //图片源数组
        private Integer[] imageInteger = {
            R.drawable.hotel1,
            R.drawable.hotel2,
            R.drawable.hotel3,
            R.drawable.hotel4,
            R.drawable.hotel5,
            R.drawable.hotel6,
        };

        //声明 ImageAdapter
        public ImageAdapter(Context c) {
            context = c;
        }

        @Override
        //获取图片的个数
        public int getCount() {
            return imageInteger.length;
        }

        @Override
        //获取图片在库中的位置
        public Object getItem(int position) {

            return position;
        }

        @Override
        //获取图片在库中的位置
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = new ImageView(context);
            //给ImageView设置资源
            imageView.setImageResource(imageInteger[position]);
            //设置比例类型
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置布局 图片128x192显示
            imageView.setLayoutParams(new Gallery.LayoutParams(600, 400));
            return imageView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent myIntent = new Intent();
//            myIntent = new Intent(ActivityHotelOrder.this, MainActivity.class);
//            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void order(View v) {

        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();

        if (SharedPreferenceUtils.getLoginornot(this) == 0) {
            Toast.makeText(getApplicationContext(), "请先登录...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (SharedPreferenceUtils.getLoginornot(this) == 1) {


//            guserid = SharedPreferenceUtils.getUserId(this);


//            System.out.println("这是-----------" + guserid + "-----" + ghotelid);
            params.put("userid", SharedPreferenceUtils.getUserId(this));
            params.put("hotelid", ghotelid);

            http.get(url + "order", params, new AjaxCallBack<Object>() {

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    Toast.makeText(ActivityHotelOrder.this, "提交订单失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoading(long count, long current) {
                    //Toast.makeText(ActivityHotelOrder.this, "正在提交订单", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub
                    //Toast.makeText(ActivityHotelOrder.this, "正在提交订单,请稍后", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Object t) {

                    if (t.toString().startsWith("success")) {
                        Intent intent = new Intent(ActivityHotelOrder.this, PayActivity.class);
                        intent.putExtra("money", ghotelprice);
                        startActivity(intent);
                        finish();
                    } else {
                        //System.out.println(t.toString() + "123456");
                        Toast.makeText(ActivityHotelOrder.this, "提交订单失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

}
