package cn.edu.pku.zhangqixun.selectdormitory;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.pku.zhangqixun.selectdormitory.Model.Student;

public class SuccessActivity extends AppCompatActivity {
    TextView no,name,sex,buildingno,bedno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("分配宿舍信息");
        no= (TextView) findViewById(R.id.succhooseno);
        name= (TextView) findViewById(R.id.succhoosename);
        sex= (TextView) findViewById(R.id.succhoosesex);
        buildingno= (TextView) findViewById(R.id.sucdorno);
        bedno= (TextView) findViewById(R.id.sucbedno);
        Student student= (Student) getIntent().getSerializableExtra("student");
        student.getStudentid();
        no.setText(student.getStudentid());
        name.setText(student.getName());
        sex.setText(student.getGender());
        buildingno.setText(student.getRoom());
        bedno.setText(student.getRoom());
    }
}
