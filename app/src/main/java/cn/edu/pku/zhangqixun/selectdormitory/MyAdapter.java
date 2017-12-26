package cn.edu.pku.zhangqixun.selectdormitory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import cn.edu.pku.zhangqixun.*;

/**
 * author: 余星星
 * data:2017/12/13.
 * E-mail:2549721818@qq.com
 * brief:适配器函数。
 */
public class MyAdapter extends ArrayAdapter {
    Context context;
    int resourceid;
    List myList;
    public MyAdapter(Context context, int resource, List mylist) {
        super(context, resource, mylist);
        this.context=context;
        this.resourceid=resource;
        this.myList=mylist;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView!=null){
            return convertView;
        }else{
            View view= LayoutInflater.from(context).inflate(resourceid,null);
            TextView notv= (TextView) view.findViewById(R.id.mytv);
            notv.setText((String) myList.get(position));
            return view;
        }
    }
}
