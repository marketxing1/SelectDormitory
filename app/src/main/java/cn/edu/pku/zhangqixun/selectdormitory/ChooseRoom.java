package cn.edu.pku.zhangqixun.selectdormitory;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChooseRoom extends AppCompatActivity {
    ListView myinfolistview;
    ImageView myimg;
    Button btn_deal;
    Student student=new Student();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_notice);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("办理住宿");
        student= (Student) getIntent().getSerializableExtra("student");
        myimg= (ImageView) findViewById(R.id.mypic);
        myinfolistview= (ListView) findViewById(R.id.mylist);
        btn_deal= (Button) findViewById(R.id.deal_with_mybus);
        btn_deal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    v.setBackgroundColor(Color.rgb(211,121,121));
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    v.setBackgroundColor(Color.rgb(141,238,238));
                }
                return false;
            }
        });
        btn_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseRoom.this,ChoosePerson.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("student",student);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        initlistview();//初始化Myinfo
    }

    private void initlistview() {
        List<String> myinfo=new ArrayList<>();
        myinfo.add("姓名:"+"                "+ student.getName());
        myinfo.add("学号:"+"                "+ student.getStudentid());
        myinfo.add("性别:"+"                "+ student.getGender() );
        myinfo.add("校验码:"+"                "+ student.getVcode() );
        ArrayAdapter myadapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,myinfo);
        myinfolistview.setAdapter(myadapter);
    }
}
