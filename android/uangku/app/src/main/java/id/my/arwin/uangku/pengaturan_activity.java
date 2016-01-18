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

/**
 * Created by winardiaris on 1/18/16.
 */
public class pengaturan_activity extends Activity {
    sessiomanager session;
    private static final String opl = "updateuser";
    private static final String TAG_STATUS = "status";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengaturan_activity);
        //session
        session = new sessiomanager(getApplicationContext());
         final EditText tusename = (EditText) findViewById(R.id.tusername);
         final EditText trealname = (EditText) findViewById(R.id.trealname);
         final EditText tpassword = (EditText) findViewById(R.id.tpassword);
         TextView llastlogin = (TextView) findViewById(R.id.llastlogin);
        TextView llastupdat = (TextView) findViewById(R.id.llastupdate);


        this.set();
        final Button bupdate = (Button)findViewById(R.id.bupdate);
        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = opl.toString();
                final String username = tusename.getText().toString();
                final String realname = trealname.getText().toString();
                final String password_ = tpassword.getText().toString();
                String password = new md5sum().md5(password_);

                HashMap<String, String> user = session.getUserDetails();
                String uid = user.get(sessiomanager.TAG_UID);// get uid

                if (username.equals("")) {
                    Toast.makeText(pengaturan_activity.this, "Isi Nama pengguna terlebih dahulu", Toast.LENGTH_SHORT).show();
                    tusename.requestFocus();
                } else if (realname.equals("")) {
                    Toast.makeText(pengaturan_activity.this, "Isi nama asli terlebih dahulu", Toast.LENGTH_SHORT).show();
                    trealname.requestFocus();
                } else if (password.equals("")) {
                    Toast.makeText(pengaturan_activity.this, "Isi kata sandi terlebih dahulu", Toast.LENGTH_SHORT).show();
                    tpassword.requestFocus();
                } else {
                    //lanjut simpan

                    Log.d("Button simpan diklik", "------------------------------");
                    Log.d("uid", uid);
                    Log.d("username", username);
                    Log.d("realname", realname);
                    Log.d("passwowrd", password);

                    new ubahdata(pengaturan_activity.this).execute(op, uid, username, realname, password);

                }
            }
        });
    }

    private void set (){
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(sessiomanager.TAG_USERNAME);// get username
        String realname = user.get(sessiomanager.TAG_REALNAME);// get realname
        String lastlogin = user.get(sessiomanager.TAG_LASTLOGIN);
        String lastupdate = user.get(sessiomanager.TAG_U_AT);

        EditText tusename = (EditText) findViewById(R.id.tusername);
        EditText trealname = (EditText) findViewById(R.id.trealname);
        TextView llastlogin = (TextView) findViewById(R.id.llastlogin);
        TextView llastupdat = (TextView) findViewById(R.id.llastupdate);
        
        tusename.setText(username);
        trealname.setText(realname);
        llastlogin.setText("Terakhir masuk: "+lastlogin);
        llastupdat.setText("Terakhir diubah: "+lastupdate);

    }
    private class ubahdata extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        private Context context1;

        public ubahdata(Context context) {
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
            post_parameter.add(new BasicNameValuePair("uid", params[1]));
            post_parameter.add(new BasicNameValuePair("username", params[2]));
            post_parameter.add(new BasicNameValuePair("realname", params[3]));
            post_parameter.add(new BasicNameValuePair("password", params[4]));
            
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
            //jika berhasil menyimpan
            if(s=="1"){
                Toast.makeText(pengaturan_activity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(pengaturan_activity.this,menuutama_activity.class);//nanti ganti ke data
                startActivity(i);
                finish();
            }
            else if(s=="2"){
                Toast.makeText(pengaturan_activity.this, "??", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(pengaturan_activity.this,"Gagal menyimpan",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
