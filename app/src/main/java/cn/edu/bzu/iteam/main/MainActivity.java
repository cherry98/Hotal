package cn.edu.bzu.iteam.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.bzu.iteam.hotelorder.ActivityHotelOrder;
import cn.edu.bzu.iteam.idingfang.R;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

public class MainActivity extends Activity {
    ListView hotellist;
    ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private String urlhotelpic1;
    private String photelname;//p为post要转到下一个activity的值
    private String photeltype;
    private String photelsize;
    private String photelprice;
    private String photelid;
    private String userid;
    private String photelnum;
    private String photelmount;
    private String hotelNote;

    private static final String TAG = MainActivity.class.getSimpleName();
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }

    };

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我正在使用i订房，快捷方便会员下单，快来下载吧！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        // oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        // oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        // oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
//		 SharedPreferences shared = getSharedPreferences("cn_sharesdk_weibodb_SinaWeibo_1", Context.MODE_PRIVATE);
//		 Editor editor = shared.edit();
//		 editor.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hotellist = (ListView) findViewById(R.id.hotellistview);
        // 设置点击酒店简介之后的行为
        hotellist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ActivityHotelOrder.class);
                //利用intent在两个activity之间传值
                //将酒店信息传给下一个activity
                intent.putExtra("hotelid", ((Map) list.get(position)).get("hotelid").toString());
                intent.putExtra("hotelname", ((Map) list.get(position)).get("hotelname").toString());
                intent.putExtra("hoteltype", ((Map) list.get(position)).get("hoteltype").toString());
                intent.putExtra("hotelprice", ((Map) list.get(position)).get("hotelprice").toString());
                intent.putExtra("hotelsize", ((Map) list.get(position)).get("hotelsize").toString());
                intent.putExtra("hotelnum", ((Map) list.get(position)).get("hotelnum").toString());
                intent.putExtra("hotelount", ((Map) list.get(position)).get("hotelmount").toString());

                startActivity(intent);
            }
        });

    }

    private void getHotelList() {
        FinalHttp http = new FinalHttp();
        String url = "http://47.104.167.198:8080/HotelServer/hotelservlet1";
        http.get(url, new AjaxCallBack<Object>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
//                Toast.makeText(MainActivity.this, "正在载入酒店信息,请稍后...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Object o) {
                list = (ArrayList<Map<String, String>>) JSON.parseObject(o.toString(), List.class);
//
                MyAdapter adapter = new MyAdapter(list, MainActivity.this);
                hotellist.setAdapter(adapter);// 添加适配器
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHotelList();
    }

    class MyAdapter extends BaseAdapter {
        List list;
        Context context;

        public MyAdapter(List list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main_item, null);

            ImageView img = (ImageView) convertView.findViewById(R.id.ivhotelpicture);
            TextView hotelname = (TextView) convertView.findViewById(R.id.tvhotelname);
            TextView hoteltype = (TextView) convertView.findViewById(R.id.tvhoteltype);
            TextView hotelprice = (TextView) convertView.findViewById(R.id.tvhotelprice);
            TextView hotelsize = (TextView) convertView.findViewById(R.id.tvhotelsize);
//			TextView hotelnum  = (TextView) convertView.findViewById(R.id.tvhotelnum);
            TextView hotelmount = (TextView) convertView.findViewById(R.id.tvhotelmount);
            TextView tvhotelnote = (TextView) convertView.findViewById(R.id.tvhotelnote);

            photelid = ((Map) list.get(position)).get("hotelid").toString();
            photelname = ((Map) list.get(position)).get("hotelname").toString();
            photeltype = ((Map) list.get(position)).get("hoteltype").toString();
            photelprice = ((Map) list.get(position)).get("hotelprice").toString();
            photelsize = ((Map) list.get(position)).get("hotelsize").toString();
            photelnum = ((Map) list.get(position)).get("hotelnum").toString();
            photelmount = ((Map) list.get(position)).get("hotelmount").toString();
            hotelNote = ((Map) list.get(position)).get("hotelNote").toString();

            hotelname.setText(photelname);
            //	hoteltype.setText(photeltype);
//			hotelnum.setText("房间号："+photelnum);
            hotelmount.setText("剩余：" + photelmount + " 间");

            hoteltype.setText("类型：" + ((Map) list.get(position)).get("hoteltype").toString());
            hotelprice.setText(((Map) list.get(position)).get("hotelprice").toString() + "元/天");
            hotelsize.setText("尺寸：" + ((Map) list.get(position)).get("hotelsize").toString() + "m²");
            tvhotelnote.setText("优惠信息：" + hotelNote);
            FinalBitmap bitmap = FinalBitmap.create(MainActivity.this);
            bitmap.configBitmapMaxHeight(100);
            bitmap.configBitmapMaxWidth(200);
            urlhotelpic1 = "http://47.104.167.198:8080/HotelServer/" + ((Map) list.get(position)).get("hotelpicture1");
            bitmap.display(img, urlhotelpic1);

            return convertView;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itemmyorder) {
            startActivity(new Intent(this, MyOrderActivity.class));
        }
        if (id == R.id.itemabout) {
            Toast.makeText(MainActivity.this, "爱订房(idingfang)来自iTeam团队", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.itemcheckupdate) {
            Toast.makeText(getApplicationContext(), "请耐心等待新版本...", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.share) {
            showShare();
            return true;
        }

        if (id == R.id.loginout) {
            //退出时清除用户登录
            SharedPreferenceUtils.setLoginornot(this, 0);
            SharedPreferenceUtils.setUserId(this, "");
            startActivity(new Intent(this, LoginActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override // 主页面点击退出按钮两次退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {

            Log.e(TAG, "exit application");

            this.finish();
        }
    }
}
