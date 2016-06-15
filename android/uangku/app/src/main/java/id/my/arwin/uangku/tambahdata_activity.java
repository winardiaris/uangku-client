package id.my.arwin.uangku;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import id.my.arwin.uangku.Main.AppSetting;

/**
 * Created by winardiaris on 1/15/16.
 */
public class tambahdata_activity extends FragmentActivity {
    String type = "in";
    private static final String opl = "newdata";
    private static final String TAG_STATUS = "status";
    private static final String TAG_DATA = "data";
    private static final String url = AppSetting.SERVER;
    sessiomanager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambahdata_activity);
        //session
        session = new sessiomanager(getApplicationContext());

        final EditText tdate = (EditText) findViewById(R.id.Tdate);
        final EditText tanggal_ = (EditText) findViewById(R.id.Tdate);
        final EditText jumlah_ = (EditText) findViewById(R.id.tvalue);
        final EditText bukti_ = (EditText) findViewById(R.id.ttoken);
        final EditText ket_ = (EditText) findViewById(R.id.tdesc);
        tdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(tambahdata_activity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        selectedmonth = selectedmonth+1;
                        String selecteddate = selectedyear+"-"+selectedmonth+"-"+selectedday;
                        tdate.setText(selecteddate);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Pilih tanggal");
                mDatePicker.show();  }
        });

        final Button bsave = (Button)findViewById(R.id.bsave);
        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = opl.toString();
                String jenis = type;
                final String tanggal = tanggal_.getText().toString();
                final String jumlah = jumlah_.getText().toString();
                final String bukti = bukti_.getText().toString();
                final String ket = ket_.getText().toString();

                HashMap<String, String> user = session.getUserDetails();
                String uid = user.get(sessiomanager.TAG_USERSID);// get uid

                if(tanggal.equals("")){
                    Toast.makeText(tambahdata_activity.this, "Isi tanggal terlebih dahulu", Toast.LENGTH_SHORT).show();
                    tanggal_.requestFocus();
                }
                else if(jumlah.equals("")){
                    Toast.makeText(tambahdata_activity.this, "Isi jumlah terlebih dahulu", Toast.LENGTH_SHORT).show();
                    jumlah_.requestFocus();
                }else if(bukti.equals("")){
                    Toast.makeText(tambahdata_activity.this, "Isi No. bukti terlebih dahulu", Toast.LENGTH_SHORT).show();
                    bukti_.requestFocus();
                }else if(ket.equals("")){
                    Toast.makeText(tambahdata_activity.this, "Isi keterangan terlebih dahulu", Toast.LENGTH_SHORT).show();
                    ket_.requestFocus();
                }
                else{
                    //lanjut simpan

                    Log.d("Button simpan diklik", "------------------------------");
                    Log.d("Jenis", jenis);
                    Log.d("Tanggal", tanggal);
                    Log.d("jumlah", jumlah);
                    Log.d("bukti", bukti);
                    Log.d("ket", ket);

                    new simpandata(tambahdata_activity.this).execute(op,uid,tanggal,bukti,jenis,jumlah,ket);

                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdebet:
                if (checked)
                    type = "in";
                    break;
            case R.id.rkredit:
                if (checked)
                    type = "out";
                    break;
        }
        Log.d("radio type:",type);
    }

    private class simpandata extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        private Context context1;

        public simpandata(Context context) {
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
                Toast.makeText(tambahdata_activity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(tambahdata_activity.this).setIcon(android.R.drawable.ic_dialog_info).setTitle("Info")
                        .setMessage("Data berhasil disimpan,ingin tambah baru?")
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i = new Intent(tambahdata_activity.this,menuutama_activity.class);//nanti ganti ke data
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
                Toast.makeText(tambahdata_activity.this, "Data telah ada", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(tambahdata_activity.this,"Gagal menyimpan",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
