package id.my.arwin.uangku;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by winardiaris on 1/11/16.
 */
public class signupactivity extends Activity {
    private static final String opl = "newuser";
    private static final String TAG_STATUS = "status";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupactivity);
        final EditText tusername = (EditText) findViewById(R.id.Tusername);
        final EditText trealname = (EditText) findViewById(R.id.Trealname);
        final EditText tpassword = (EditText) findViewById(R.id.Tpassword);

        Button bdaftar = (Button) findViewById(R.id.Bdaftar);

        bdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = opl.toString();
                String username= tusername.getText().toString();
                String realname= trealname.getText().toString();
                String password_ = tpassword.getText().toString();
                String password = new md5sum().md5(password_);

                new daftarpengguna(signupactivity.this).execute(op, username,realname, password);
                Log.d("Event:","Button daftar di tekan");
            }
        });
    }
    private class daftarpengguna extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        private Context context1;

        public daftarpengguna(Context context) {
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
            post_parameter.add(new BasicNameValuePair("realname", params[2]));
            post_parameter.add(new BasicNameValuePair("password", params[3]));
            try{
                String jsonStr = CustomHTTPClient.executeHttpPost("http://192.168.1.22/uangku1.0.1/", post_parameter);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        String status = jsonObj.getString(TAG_STATUS);
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
//            finish();
            //jika berhasil mendaftar
            if(s=="1"){
                Toast.makeText(signupactivity.this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(s=="2"){
                Toast.makeText(signupactivity.this, "Username telah digunakan", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(signupactivity.this,"Gagal mendaftar",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
