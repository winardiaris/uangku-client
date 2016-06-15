package id.my.arwin.uangku.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import id.my.arwin.uangku.ServiceHandler;
import id.my.arwin.uangku.menuutama_activity;
import id.my.arwin.uangku.sessiomanager;
import id.my.arwin.uangku.signup_activity;

public class MainActivity extends Activity {
    private ProgressDialog pDialog;
    private static final String url = AppSetting.SERVER;
    private static String url_test = url+"check";
    private static final String TAG_DATA = "data";
    private static final String TAG_STATUS = "status";
//    ArrayList<HashMap<String, String>> status_;
    private String status_;
    sessiomanager session;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status_ = new String();
        session = new sessiomanager(getApplicationContext());

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String username_ = user.get(sessiomanager.TAG_USERNAME);// get name
        String uid_ = user.get(sessiomanager.TAG_USERSID);// get uid
        Boolean islogin = session.isUserLoggedIn();

        if(islogin==true){
//            setContentView(R.layout.activity_main);
            Intent i = new Intent(MainActivity.this,menuutama_activity.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent(MainActivity.this,signup_activity.class);
            startActivity(i);
        }
        new cekkoneksi().execute();
    }
    private class cekkoneksi extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            sh.makeServiceCall(url_test, ServiceHandler.GET);
            String jsonStr = sh.makeServiceCall(url_test, ServiceHandler.GET);

            Log.d("Response doinbackground Mainactivity: ",jsonStr);
            if (jsonStr != null) {
                try {

                    Log.d("data json",jsonStr);
                    JSONObject obj = new JSONObject(jsonStr);
                    JSONObject data = obj.getJSONObject("");

                    status_ = data.getString(TAG_STATUS);
                    Log.d("status",status_);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            Toast.makeText(MainActivity.this,status_,Toast.LENGTH_SHORT).show();
//            setContentView(R.layout.signup_activity);

        }
    }
}
