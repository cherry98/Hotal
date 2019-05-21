package cn.edu.bzu.iteam.hotelorder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @PrejectName: Hotal
 * @PackageName: cn.edu.bzu.iteam.hotelorder
 * @Desc:
 * @Author: qxx0101
 * @Date: 5/6/2019
 * @Version:
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeight=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newHeight);
    }
}
