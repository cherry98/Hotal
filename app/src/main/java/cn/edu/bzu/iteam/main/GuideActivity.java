package cn.edu.bzu.iteam.main;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import cn.edu.bzu.iteam.idingfang.R;

public class GuideActivity extends FragmentActivity {
    ViewPager pager;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.guide_main);
        pager = (ViewPager) findViewById(R.id.guide);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return 3;
            }

            @Override
            public Fragment getItem(int arg0) {
                if (arg0 == 0) {
                    Fragment1 fragment1 = new Fragment1();
                    return fragment1;
                }
                if (arg0 == 1) {
                    Fragment2 fragment2 = new Fragment2();
                    return fragment2;

                }
                if (arg0 == 2) {
                    Fragment3 fragment3 = new Fragment3();
                    return fragment3;

                }
                return null;
            }
        });

    }

    @Override
    protected void onStop() {
        SharedPreferences shared = getSharedPreferences("flag", Context.MODE_PRIVATE);
        Editor editor = shared.edit();
        editor.putString("flag", "1");
        editor.commit();
        finish();
        super.onStop();
    }

}
