package cn.edu.pku.zhangqixun.selectdormitory;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Success_Back extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_back);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("亲，办理住宿成功啦");
        btn= (Button) findViewById(R.id.back_shouye);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Success_Back.this,Success.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("student",getIntent().getSerializableExtra("student"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
