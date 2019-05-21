package cn.edu.bzu.iteam.hotelorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.edu.bzu.iteam.idingfang.R;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List list;

    public MyAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_commit, viewGroup, false);
        TextView textView = (TextView) view.findViewById(R.id.content);

        String username = ((Map) list.get(i)).get("username").toString();
        String photelevaluate = ((Map) list.get(i)).get("photelevaluate").toString();
        textView.setText(username + "：" + photelevaluate);
        return view;
    }
}
