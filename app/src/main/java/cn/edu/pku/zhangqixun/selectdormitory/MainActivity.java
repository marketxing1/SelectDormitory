package cn.edu.pku.zhangqixun.selectdormitory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.edu.pku.zhangqixun.selectdormitory.Model.Student;

public class MainActivity extends AppCompatActivity {
    Button login_btn;
    EditText my_stuid,my_password;
    String password;
    String stuid;
    String login_url="https://api.mysspku.com/index.php/V1/MobileCourse/Login";
    String query_info="https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid=";
    InputStream in;
    BufferedReader bfr;
    Student student=new Student();
    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(MainActivity.this, "账户或者密码错误！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleSSLHandshake();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("请登录");
        login_btn= (Button) findViewById(R.id.my_login);
        my_stuid= (EditText) findViewById(R.id.accountEt);
        my_password= (EditText) findViewById(R.id.pwdEt);


        login_btn.setOnTouchListener(new View.OnTouchListener() {
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
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stuid=String.valueOf(my_stuid.getText());
                password= String.valueOf(my_password.getText());
                if(stuid!=null&&password!=null&&parsestudentid(stuid)){
                  new Thread(new Runnable() {
                      @Override
                      public void run() {
                          try {
                              System.out.println(login_url+"?username="+stuid+"&password="+password);
                              URL url=new URL(login_url+"?username="+stuid+"&password="+password);
                              HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                              httpURLConnection.setRequestMethod("GET");
                              httpURLConnection.setConnectTimeout(8000);
                              httpURLConnection.setReadTimeout(8000);
                              in=httpURLConnection.getInputStream();
                              bfr=new BufferedReader(new InputStreamReader(in));
                              String line = bfr.readLine();
                              Log.i("test",line);
                              if(parseJson(line)){
                                  if(Nothavedormitory(stuid)) {
                                      Intent intent = new Intent(getApplicationContext(), ChooseRoom.class);
                                      Bundle bundle=new Bundle();
                                      bundle.putSerializable("student",student);
                                      intent.putExtras(bundle);
                                      startActivity(intent);
                                  }else{
                                      Intent intent = new Intent(getApplicationContext(), SuccessActivity.class);
                                      intent.putExtra("student", student);
                                      startActivity(intent);
                                  }
                              }else{
                                  Message msg=new Message();
                                  msg.what=1;
                                  mhandler.sendMessage(msg);

                              }

                          } catch (MalformedURLException e) {
                              e.printStackTrace();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }

                      }

                      private boolean parseJson(String line) {
                          try {
                              JSONObject jsline=new JSONObject(line);
                              String errcode=jsline.getString("errcode");
                              System.out.println(errcode);
                              if(errcode.equals("0")){
                                  return true;
                              }else{
                                  return false;
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                          return true;
                      }
                  }).start();
                }else{
                    Toast.makeText(MainActivity.this, "请输入账户或者密码！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean parsestudentid(String stuid) {
        if(stuid.length()==10&&stuid.substring(3,5).equals("12")){
            System.out.println("hahah");
            return true;
        }else{
            return false;
        }
    }

    private boolean Nothavedormitory(final String stuid) {
        final boolean[] ifnothave = {false};
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(query_info+stuid);
                    HttpURLConnection https= (HttpURLConnection) url.openConnection();
                    https.setRequestMethod("GET");
                    https.setConnectTimeout(8000);
                    https.setReadTimeout(8000);
                    in=https.getInputStream();
                    bfr=new BufferedReader(new InputStreamReader(in));
                    String line=bfr.readLine();
                    System.out.println(line);
                    JSONObject js=new JSONObject(line);
                    if(js.getString("errcode").equals("0")) {
                         Log.i("1","哈哈三大");
                        JSONObject js2 = js.getJSONObject("data");
                        student.setStudentid(js2.getString("studentid"));
                        student.setName(js2.getString("name"));
                        student.setGender(js2.getString("gender"));
                        student.setVcode(js2.getString("vcode"));
                        student.setLocation(js2.getString("location"));
                        student.setGrade(js2.getString("grade"));
                        if(js2.has("room")){
                            student.setRoom(js2.getString("room"));
                        }else {
                            ifnothave[0]=true;
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread mythread=new Thread(runnable);
        mythread.start();
        try {
            mythread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ifnothave[0];

    }


    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}
