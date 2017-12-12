package cn.edu.pku.zhangqixun.selectdormitory.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import cn.edu.pku.zhangqixun.selectdormitory.R;

/**
 * Created by lenovo on 2017/11/22.
 */
public class MyArrayAdapter extends ArrayAdapter {
    Context context;
    int resourceid;
    List myList;
    public MyArrayAdapter(Context context, int resource, List mylist) {
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
