package id.my.arwin.uangku;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import id.my.arwin.uangku.Main.MainActivity;

/**
 * Created by winardiaris on 1/8/16.
 */
public class signup_activity extends Activity {
    private static final String opl = "login";
    private static final String TAG_STATUS = "status";
    sessiomanager session;
    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        final EditText tusername = (EditText) findViewById(R.id.Tusername);
        final EditText tpassword = (EditText) findViewById(R.id.Tpassword);
        Button bmasuk = (Button) findViewById(R.id.Bmasuk);

        //session
        session = new sessiomanager(getApplicationContext());

        bmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = opl.toString();
                String username = tusername.getText().toString();
                String password_ = tpassword.getText().toString();
                String password = new md5sum().md5(password_);

                new masuksistem(signup_activity.this).execute(op, username, password);
                clear();
                Log.d("Event:", "Button masuk diklik");
                Log.d("md5 :", password);
            }
        });

        TextView ldaftar = (TextView) findViewById(R.id.lsignup);
        ldaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signup_activity.this, signupactivity.class);
                startActivity(i);
                clear();
                Log.d("Event:", "Label daftar diklik");
            }
        });

    }
    private class masuksistem extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        private Context context1;

        public masuksistem(Context context) {
            this.context1 = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context1);
            dialog.setMessage("Tunggu sebentar ya..");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            String result = null;
            ArrayList<NameValuePair> post_parameter = new ArrayList<>();
            post_parameter.add(new BasicNameValuePair("op", params[0]));
            post_parameter.add(new BasicNameValuePair("username", params[1]));
            post_parameter.add(new BasicNameValuePair("password", params[2]));

            try{
                String jsonStr = CustomHTTPClient.executeHttpPost("http://192.168.1.22/uangku1.0.1/", post_parameter);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        String status = jsonObj.getString(TAG_STATUS);

                        //session manager
                        String uid = session.getuid(params[1]);
                        session.setsessionlogin(params[1],params[2],uid);

                        // get user data from session
                        HashMap<String, String> user = session.getUserDetails();
                        String username_ = user.get(sessiomanager.TAG_USERNAME);// get name
                        String uid_ = user.get(sessiomanager.TAG_UID);// get uid
                        Boolean islogin = session.isUserLoggedIn();


                        Log.d("username_s",username_);
                        Log.d("uid_s",uid_);

                        result = status;
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }

            }catch (Exception e){
                result = e.toString();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            super.onPostExecute(s);
//            Toast.makeText(signup_activity.this, s, Toast.LENGTH_SHORT).show();
            Log.d("String s onpostexecute",s);
//            finish();
            //jika masuk
            if(s=="1"){
                Toast.makeText(signup_activity.this,"Berhasil masuk",Toast.LENGTH_SHORT).show();
                clear();
                Intent i = new Intent(signup_activity.this,MainActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(signup_activity.this,"Gagal masuk",Toast.LENGTH_SHORT).show();
                clear();
            }
        }
    }
    private void clear(){
        final EditText tusername = (EditText) findViewById(R.id.Tusername);
        final EditText tpassword = (EditText) findViewById(R.id.Tpassword);
        tusername.setText("");
        tusername.setFocusable(true);
        tpassword.setText("");
    }
    public  void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
//    public super {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            // User has pressed Back key. So hide the keyboard
//
//        }
}
