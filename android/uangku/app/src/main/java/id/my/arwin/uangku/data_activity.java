package id.my.arwin.uangku;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import id.my.arwin.uangku.Main.AppSetting;

/**
 * Created by winardiaris on 1/21/16.
 */
public class data_activity extends ListActivity {
    JSONArray data = null;
    String url = null;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> dataList = null;
    private ProgressDialog pDialog;
    sessiomanager session;


    //node json
    private static final String TAG_ID = "id";
    private static final String TAG_USERSID = "users_id";
    private static final String TAG_DATE = "date";
    private static final String TAG_BILL = "bill";
    private static final String TAG_TYPE = "type";
    private static final String TAG_VALUE = "value";
    private static final String TAG_DESC = "desc";
    private static final String TAG_STATUS = "status";
    private static final String TAG_C_AT= "created_at";
    private static final String TAG_U_AT = "updated_at";
    private static final String TAG_DATA = "data";
    private static final String TAG_TOKEN = "token";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
        session = new sessiomanager(getApplicationContext());

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String token = user.get(sessiomanager.TAG_TOKEN);// get username
        url = AppSetting.SERVER+"data?token="+token;
        Log.d("url", url);

        Button bcari = (Button)findViewById(R.id.bcari);
        bcari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tcari = (EditText) findViewById(R.id.tsearch);
                String cari = tcari.getText().toString();
                url += "&s=" + cari;

                Log.d("url", url);
            }
        });

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil data dari ListView yang dipilih
                String did = ((TextView) view.findViewById(R.id.tdid)).getText().toString();

                Intent in = new Intent(getApplicationContext(), lihatdata_activity.class);
                in.putExtra(TAG_ID, did);

                startActivity(in);
            }
        });


        new getData().execute();

    }
    private class getData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(data_activity.this);
            pDialog.setMessage("Tolong Tunggu...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            dataList = ParseJSON(jsonStr);
            Log.d("Response: ", "> " + jsonStr);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                        data_activity.this, dataList,
                        R.layout.list_item, new String[] { TAG_ID,TAG_TYPE, TAG_DATE,TAG_VALUE,TAG_DESC },
                        new int[] {  R.id.tdid,R.id.ttype,R.id.tdate, R.id.tvalue, R.id.tdesc });
                setListAdapter(adapter);
        }

    }
    private ArrayList<HashMap<String,String>> ParseJSON(String jsonStr){
        if (jsonStr != null) {
            try {

                JSONArray jsonArr = new JSONArray(jsonStr);
                // Getting JSON Array node
                int banyak = jsonArr.length();
                Log.d("banyak data",String.valueOf(banyak));


                for (int i = 0; i < banyak; i++) {
                    JSONObject c = jsonArr.getJSONObject(i);

                    String id = c.getString(TAG_ID);
                    String type = c.getString(TAG_TYPE);
                    String date = c.getString(TAG_DATE);
                    String value = c.getString(TAG_VALUE);
                    String desc = c.getString(TAG_DESC);

                    Log.d("id",id);
                    Log.d("type",type);
                    Log.d("date",date);
                    Log.d("value",value);
                    Log.d("desc",desc);
                    Log.d("end","================================");



                    // tmp hashmap for single data
                    HashMap<String, String> data_ = new HashMap<String, String> ();

                    // adding each child node to HashMap key => value
                    data_.put(TAG_ID, id);
                    data_.put(TAG_TYPE, type);
                    data_.put(TAG_DATE, date);
                    data_.put(TAG_VALUE, value);
                    data_.put(TAG_DESC, desc);

                    // adding data to data list
                    dataList.add(data_);
                }


                if (pDialog.isShowing())
                    pDialog.dismiss();

                return dataList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            return null;
        }

    }

}
