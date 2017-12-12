package cn.edu.pku.zhangqixun.selectdormitory;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cn.edu.pku.zhangqixun.selectdormitory.Model.*;

public class BacktoSuccessActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backto_success);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("办理住宿成功");
        btn= (Button) findViewById(R.id.back_shouye);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BacktoSuccessActivity.this,SuccessActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("student",getIntent().getSerializableExtra("student"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
