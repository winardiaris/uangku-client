package id.my.arwin.uangku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.my.arwin.uangku.Main.AppSetting;

/**
 * Created by winardiaris on 1/11/16.
 */
public class sessiomanager  {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;
    private static final String url = AppSetting.SERVER;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;


    //session
    public static final String mypref = "mypref" ;
    public static final String TAG_USERNAME = "username" ;
    public static final String TAG_PASSWORD = "password" ;
    public static final String TAG_UID = "uid" ;
    public static final String isloginkey = "islogin" ;

    public static final String TAG_REALNAME = "realname";
    public static final String TAG_LASTLOGIN = "lastlogin";
    public static final String TAG_C_AT = "c_at";
    public static final String TAG_U_AT = "u_at";
    public static final String TAG_DATA = "data";
    public static final String TAG_GETDATA = "getdata";

    public sessiomanager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(mypref, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setsessionlogin(String username,String password,String uid) throws IOException {

            String result = null;
            ArrayList<NameValuePair> post_parameter = new ArrayList<>();
            post_parameter.add(new BasicNameValuePair("op", "viewuser"));
            post_parameter.add(new BasicNameValuePair("username", username));
            try{
                String jsonStr = CustomHTTPClient.executeHttpPost(url, post_parameter);
                if (jsonStr != null) {
                    try {
//                        JSONArray jsonarray = new JSONArray(jsonStr);
                        JSONObject obj = new JSONObject(jsonStr);
                        JSONArray data = obj.getJSONArray(TAG_DATA);

                        JSONObject jsonObj = data.getJSONObject(0);

                        String realname = jsonObj.getString(TAG_REALNAME);
                        String lastlogin = jsonObj.getString(TAG_LASTLOGIN);
                        String cat = jsonObj.getString(TAG_C_AT);
                        String uat = jsonObj.getString(TAG_U_AT);

//                      save session
                        editor.putString(TAG_USERNAME, username);
                        editor.putString(TAG_PASSWORD, password);
                        editor.putString(TAG_UID, uid);
                        editor.putBoolean(isloginkey, true);
                        editor.putString(TAG_REALNAME, realname);
                        editor.putString(TAG_LASTLOGIN, lastlogin);
                        editor.putString(TAG_C_AT, cat);
                        editor.putString(TAG_U_AT, uat);
                        editor.commit();

                        Log.d("setsessionlogin", "-------------------------------------");
                        Log.d("status:", "Succes");
                        Log.d("UID:", uid);
                        Log.d("Username:", username);
                        Log.d("Realname:", realname);
                        Log.d("Password:", password);
                        Log.d("LastLogin:", lastlogin);
                        Log.d("C_at:", cat);
                        Log.d("U_at:", uat);

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


    }
    public boolean checklogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, sessiomanager.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }

    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(TAG_USERNAME, pref.getString(TAG_USERNAME, null));
        user.put(TAG_PASSWORD, pref.getString(TAG_PASSWORD, null));
        user.put(TAG_UID, pref.getString(TAG_UID, null));
        user.put(TAG_REALNAME,pref.getString(TAG_REALNAME,null));
        user.put(TAG_LASTLOGIN,pref.getString(TAG_LASTLOGIN,null));
        user.put(TAG_C_AT,pref.getString(TAG_C_AT,null));
        user.put(TAG_U_AT,pref.getString(TAG_U_AT,null));

        // return user
        return user;
    }
    public boolean isUserLoggedIn(){
        return pref.getBoolean(isloginkey, false);
    }


    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, sessiomanager.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    public String getuid(String username) throws JSONException {
        ServiceHandler sh = new ServiceHandler();
        // Making a request to url and getting response
//        sh.makeServiceCall(url+"?op=get&from_data=username&value_data=" + username + "&select_field=uid&from_table=user", ServiceHandler.GET);
        String jsonStr = sh.makeServiceCall(url+"?op=get&from_data=username&value_data="+username+"&select_field=uid&from_table=user", ServiceHandler.GET);
        Log.d("getuid", "-----------------------------");

        Log.d("json :", jsonStr);

        JSONObject obj = new JSONObject(jsonStr);
        JSONArray data = obj.getJSONArray(TAG_DATA);

        JSONObject object = data.getJSONObject(0);
       String uid_ = object.getString(TAG_GETDATA);
        Log.d("uid dari get data",uid_);

        return uid_;
    }



}
