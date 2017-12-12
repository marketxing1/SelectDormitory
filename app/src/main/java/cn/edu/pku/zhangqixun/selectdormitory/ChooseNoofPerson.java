package cn.edu.pku.zhangqixun.selectdormitory;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.zhangqixun.selectdormitory.Model.MyArrayAdapter;
import cn.edu.pku.zhangqixun.selectdormitory.Model.Student;

public class ChooseNoofPerson extends AppCompatActivity {
    ListView choosenoperson;
    List mylist;
    String building_info="https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?gender=";
    InputStream in;
    BufferedReader bfr;
    String five = null,thirteen,fourteen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_noof_person);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("办理住宿");
        choosenoperson= (ListView) findViewById(R.id.deal_with_personno);

        mylist=new ArrayList();
        mylist.add("单人办理");
        mylist.add("两人办理");
        mylist.add("三人办理");
        mylist.add("四人办理");
        MyArrayAdapter myArrayAdapter=new MyArrayAdapter(this,R.layout.personno_choose,mylist);
        choosenoperson.setAdapter(myArrayAdapter);
        choosenoperson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        Student student= (Student) getIntent().getSerializableExtra("student");
                        String sex;
                        if(student.getGender().equals("男")){
                            sex="1";
                        }else{
                            sex="2";
                        }
                        try {
                            URL url=new URL(building_info+sex);
                            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            httpURLConnection.setConnectTimeout(8000);
                            httpURLConnection.setReadTimeout(8000);
                            in=httpURLConnection.getInputStream();
                            bfr=new BufferedReader(new InputStreamReader(in));
                            String line=bfr.readLine();
                            JSONObject js=new JSONObject(line);
                            JSONObject js2=js.getJSONObject("data");
                            five=js2.getString("5");
                            thirteen=  js2.getString("13");
                            fourteen=  js2.getString("14");

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                Thread myThread=new Thread(runnable);
                myThread.start();
                try {
                    myThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                    Intent intent=new Intent(ChooseNoofPerson.this,multiPeople.class);
                    if(i==0){
                        intent.putExtra("personno",0);
                    }
                    else if(i==1){
                        intent.putExtra("personno",1);
                    }else if(i==2){
                        intent.putExtra("personno",2);
                    }else if(i==3){
                        intent.putExtra("personno",3);
                    }
                    intent.putExtra("five",five);
                    intent.putExtra("thirteen",thirteen);
                    intent.putExtra("fourteen",fourteen);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("student",getIntent().getSerializableExtra("student"));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }


        });

    }
}
