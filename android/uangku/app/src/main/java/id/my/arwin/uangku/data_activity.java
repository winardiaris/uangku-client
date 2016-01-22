package id.my.arwin.uangku;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by winardiaris on 1/21/16.
 */
public class data_activity extends ListActivity {
    JSONArray data = null;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> dataList;
    private ProgressDialog pDialog;

    //node json
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);

        dataList = new ArrayList<HashMap<String, String>>();

        String url = "http://192.168.1.22/uangku1.0.1/?op=viewdata&uid=1";
        this.getData(url);
    }

    private void getData(String url){
        pDialog = new ProgressDialog(data_activity.this);
        pDialog.setMessage("Tolong Tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                data = jsonObj.getJSONArray(TAG_DATA);

                // looping through All Contacts
                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);

                    // tmp hashmap for single data
                    HashMap<String, String> data_ = new HashMap<String, String> ();

                    // adding each child node to HashMap key => value
                    data_.put(TAG_DID, c.getString(TAG_DID));
                    data_.put(TAG_TYPE, c.getString(TAG_TYPE));
                    data_.put(TAG_DATE, c.getString(TAG_DATE));
                    data_.put(TAG_VALUE, c.getString(TAG_VALUE));
                    data_.put(TAG_DESC, c.getString(TAG_DESC));


                    // adding data to data list
                    dataList.add(data_);
                }

                ListAdapter adapter = new SimpleAdapter(
                        data_activity.this, dataList,
                        R.layout.list_item, new String[] { TAG_DID,TAG_TYPE, TAG_DATE,TAG_VALUE,TAG_DESC },
                        new int[] {  R.id.tdid,R.id.ttype,R.id.tdate, R.id.tvalue, R.id.tdesc });
                setListAdapter(adapter);

                if (pDialog.isShowing())
                    pDialog.dismiss();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
    }
}
