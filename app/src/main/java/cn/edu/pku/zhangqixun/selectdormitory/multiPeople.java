package cn.edu.pku.zhangqixun.selectdormitory;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class multiPeople extends AppCompatActivity {
    LinearLayout personlayout1,personlayout2,personlayout3,tongzhurentv, mylayout;
    View v1,v2,v3,v4;
    Button btn;
    TextView no,name,sex,five,thirteen,fourteen,target_building;
    Student student=new Student();
    String choose_info="https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom";
    InputStream in;
    BufferedReader bfr;
    String dorno="5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_people);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getIntent().getIntExtra("personno",1)==0){
            actionBar.setTitle("单人办理住宿");
        }else{
            actionBar.setTitle("多人办理住宿");
        }
        no= (TextView) findViewById(R.id.chooseno);
        name= (TextView) findViewById(R.id.choosename);
        sex= (TextView) findViewById(R.id.choosesex);
        five= (TextView) findViewById(R.id.dorimtryno5);
        thirteen= (TextView) findViewById(R.id.dorimtryno13);
        fourteen= (TextView) findViewById(R.id.dorimtryno14);
        student= (Student) getIntent().getSerializableExtra("student");
        no.setText(student.getStudentid());
        name.setText(student.getName());
        sex.setText(student.getGender());
        five.setText(getIntent().getStringExtra("five"));
        fourteen.setText(getIntent().getStringExtra("fourteen"));
        thirteen.setText(getIntent().getStringExtra("thirteen"));
        target_building= (TextView) findViewById(R.id.target_buiding);
        mylayout= (LinearLayout) findViewById(R.id.mychoose_building);
        mylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingleChoiceDialog();
            }
        });
        btn= (Button) findViewById(R.id.forsure);
        btn.setOnTouchListener(new View.OnTouchListener() {
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = null;
                Callable callable=new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        URL url=new URL(choose_info);
                        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setConnectTimeout(8000);
                        httpURLConnection.setReadTimeout(8000);
                        in=httpURLConnection.getInputStream();
                        bfr=new BufferedReader(new InputStreamReader(in));
                        String line=bfr.readLine();
                        System.out.println(line);
                        JSONObject js=new JSONObject(line);
                        String errcode=js.getString("errcode");
                        return errcode;
                    }
                };
                FutureTask futureTask=new FutureTask(callable);
                Thread myThread=new Thread(futureTask);
                myThread.start();
                try {
                    code= (String) futureTask.get();
                    myThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(code.equals("0")) {
                    Intent intent=new Intent(getApplicationContext(),Success_Back.class);
                    Student student= (Student) getIntent().getSerializableExtra("student");
                    student.setRoom(dorno);
                    System.out.println(dorno+"寝室号");
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("student",student);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    //如果返回不是成功就....
                }
            }
        });

        personlayout1= (LinearLayout) findViewById(R.id.person1);
        personlayout2= (LinearLayout) findViewById(R.id.person2);
        personlayout3= (LinearLayout) findViewById(R.id.person3);
        tongzhurentv= (LinearLayout) findViewById(R.id.tongzhurentv);
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
        v3=findViewById(R.id.v3);
        v4=findViewById(R.id.v4);

        if(getIntent().getIntExtra("personno",1)==0){
            personlayout1.setVisibility(View.GONE);
            personlayout2.setVisibility(View.GONE);
            personlayout3.setVisibility(View.GONE);
            tongzhurentv.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);

        }else if(getIntent().getIntExtra("personno",1)==1){
            personlayout1.setVisibility(View.GONE);
            personlayout2.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
        }else if(getIntent().getIntExtra("personno",1)==2){
            personlayout2.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
        }
    }
    private void showSingleChoiceDialog(){
        final String[] items = { "5号楼","13号楼","14号楼" };
        final int[] yourChoice = {-1};
        AlertDialog.Builder singleChoiceDialog =new AlertDialog.Builder(this);
        singleChoiceDialog.setTitle("请选择楼层");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice[0] = which;
                        System.out.println(which);
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice[0] != -1) {
                            System.out.println(which);
                            target_building.setText("目标"+items[yourChoice[0]]);
                            dorno=(String) target_building.getText().subSequence(2,4);
                        }else{
                            target_building.setText("目标"+items[0]);
                            dorno=(String) target_building.getText().subSequence(2,3);
                        }
                    }
                });
        singleChoiceDialog.show();
    }
}
