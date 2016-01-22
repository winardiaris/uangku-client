package id.my.arwin.uangku;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import id.my.arwin.uangku.CustomHTTPClient;
import id.my.arwin.uangku.Main.AppSetting;

/**
 * Created by winardiaris on 1/22/16.
 */
public class lihatdata_activity extends Activity {
    sessiomanager session;
    private static final String opl = "viewdata";
    private static final String TAG_STATUS = "status";
    private static final String url = AppSetting.SERVER;
    private static final String TAG_DID = "did";
    private static final String TAG_UID = "uid";
    private static final String TAG_DATE = "date";
    private static final String TAG_TOKEN = "token";
    private static final String TAG_TYPE = "type";
    private static final String TAG_VALUE = "value";
    private static final String TAG_DESC = "desc";
    private static final String TAG_STATUS = "status";
    private static final String TAG_C_AT= "c_at";
    private static final String TAG_U_AT = "u_at";
    private static final String TAG_DATA = "data";

    String type = "in";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihatdata_activity);

        final TextView tdid = (TextView)findViewById(R.id.tdid);
        final TextView tuid = (TextView)findViewById(R.id.tuid);
        final EditText tdate = (EditText) findViewById(R.id.Tdate);
        final EditText tanggal_ = (EditText) findViewById(R.id.Tdate);
        final EditText jumlah_ = (EditText) findViewById(R.id.tvalue);
        final EditText bukti_ = (EditText) findViewById(R.id.ttoken);
        final EditText ket_ = (EditText) findViewById(R.id.tdesc);

        //session
        session = new sessiomanager(getApplicationContext());
        Intent in = getIntent();
        HashMap<String, String> user = session.getUserDetails();

        final String did = in.getStringExtra(TAG_DID);// Get JSON values from previous intent
        String uid = user.get(sessiomanager.TAG_UID);// get uid
        String op = opl.toString();


        Button bupdate = (Button)findViewById(R.id.bupdate);
        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = opl.toString();
                String jenis = type;
                final String did = tdid.getText().toString();
                final String tanggal = tanggal_.getText().toString();
                final String jumlah = jumlah_.getText().toString();
                final String bukti = bukti_.getText().toString();
                final String ket = ket_.getText().toString();

                HashMap<String, String> user = session.getUserDetails();
                String uid = user.get(sessiomanager.TAG_UID);// get uid

                if(tanggal.equals("")){
                    Toast.makeText(lihatdata_activity.this, "Isi tanggal terlebih dahulu", Toast.LENGTH_SHORT).show();
                    tanggal_.requestFocus();
                }
                else if(jumlah.equals("")){
                    Toast.makeText(lihatdata_activity.this, "Isi jumlah terlebih dahulu", Toast.LENGTH_SHORT).show();
                    jumlah_.requestFocus();
                }else if(bukti.equals("")){
                    Toast.makeText(lihatdata_activity.this, "Isi No. bukti terlebih dahulu", Toast.LENGTH_SHORT).show();
                    bukti_.requestFocus();
                }else if(ket.equals("")){
                    Toast.makeText(lihatdata_activity.this, "Isi keterangan terlebih dahulu", Toast.LENGTH_SHORT).show();
                    ket_.requestFocus();
                }
                else{
                    //lanjut simpan

                    Log.d("Button perbaharui diklik", "------------------------------");
                    Log.d("Jenis", jenis);
                    Log.d("Tanggal", tanggal);
                    Log.d("jumlah", jumlah);
                    Log.d("bukti", bukti);
                    Log.d("ket", ket);

                    new perbaharuidata(lihatdata_activity.this).execute(op,did, uid, tanggal, bukti, jenis, jumlah, ket);

                }
            }
        });

        new getThisData().execute(op, uid, did);

    }


    private class getThisData extends AsyncTask<String, Void, String> {

        private Context context1;
        ProgressDialog dialog;

        public getThisData(Context context) {
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
            String result;

            ArrayList<NameValuePair> post_parameter = new ArrayList<>();

            post_parameter.add(new BasicNameValuePair(opl, params[0]));
            post_parameter.add(new BasicNameValuePair(TAG_UID, params[1]));
            post_parameter.add(new BasicNameValuePair(TAG_DID, params[2]));
           try{
               result = CustomHTTPClient.executeHttpPost(url, post_parameter);
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
            Log.d("hasil onPostExecute",s);
            try{
                JSONObject obj = new JSONObject(s);
                JSONArray data = obj.getJSONArray(TAG_DATA);

                JSONObject object = data.getJSONObject(0);
                String gdid = object.getString(TAG_DID);
                String guid = object.getString(TAG_UID);
                String gdate = object.getString(TAG_DATE);
                String gtoken = object.getString(TAG_TOKEN);
                String gtype = object.getString(TAG_TYPE);
                String gvalue = object.getString(TAG_VALUE);
                String gdesc = object.getString(TAG_DESC);
                String gu_at = object.getString(TAG_U_AT);

                TextView tdid_ = (TextView)findViewById(R.id.tdid);
                tdid_.setText(gdid);

                TextView tuid_ = (TextView)findViewById(R.id.tuid);
                tuid_.setText(guid);

                EditText tdates = (EditText) findViewById(R.id.tdate);
                tdates.setText(gdate);

                EditText ttoken_ = (EditText) findViewById(R.id.ttoken);
                ttoken_.setText(gtoken);

                RadioButton rdebet = (RadioButton) findViewById(R.id.rdebet);
                RadioButton rkredit = (RadioButton) findViewById(R.id.rkredit);
                if(gtype.equals("in")){
                    rdebet.setChecked(true);
                }
                else {
                    rkredit.setChecked(true);
                }

                EditText tvalue = (EditText) findViewById(R.id.tvalue);
                tvalue.setText(gvalue);

                EditText tdesc = (EditText) findViewById(R.id.tdesc);
                tdesc.setText(gdesc);

                TextView t_u_at = (TextView)findViewById(R.id.t_u_at);
                t_u_at.setText(gu_at);
            }catch (JSONException e){

            }
        }
    }
    private class perbaharuidata extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        private Context context1;

        public perbaharuidata(Context context) {
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
            post_parameter.add(new BasicNameValuePair("date", params[2]));
            post_parameter.add(new BasicNameValuePair("token", params[3]));
            post_parameter.add(new BasicNameValuePair("type", params[4]));
            post_parameter.add(new BasicNameValuePair("value", params[5]));
            post_parameter.add(new BasicNameValuePair("desc", params[6]));
            try{
                String jsonStr = CustomHTTPClient.executeHttpPost(url, post_parameter);
                if (jsonStr != null) {
                    try {

                        Log.d("data json",jsonStr);
                        JSONObject obj = new JSONObject(jsonStr);
                        JSONObject data = obj.getJSONObject(TAG_DATA);

                        String status = data.getString(TAG_STATUS);
                        Log.d("status",status);
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
            Log.d("String s onpostexecute", s);
//            finish();
            //jika berhasil menyimpan
            if(s.equals("success")){
                Toast.makeText(lihatdata_activity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(lihatdata_activity.this).setIcon(android.R.drawable.ic_dialog_info).setTitle("Info")
                        .setMessage("Data berhasil disimpan,ingin tambah baru?")
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i = new Intent(lihatdata_activity.this,menuutama_activity.class);//nanti ganti ke data
                                startActivity(i);
                                finish();
                            }
                        }).setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tanggal_ = (EditText) findViewById(R.id.Tdate);
                        EditText jumlah_ = (EditText) findViewById(R.id.tvalue);
                        EditText bukti_ = (EditText) findViewById(R.id.ttoken);
                        EditText ket_ = (EditText) findViewById(R.id.tdesc);

                        tanggal_.setText("");
                        jumlah_.setText("");
                        bukti_.setText("");
                        ket_.setText("");
                        tanggal_.requestFocus();
                    }
                }).show();
            }
            else if(s.equals("duplicate")){
                Toast.makeText(lihatdata_activity.this, "Data telah ada", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(lihatdata_activity.this,"Gagal menyimpan",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
