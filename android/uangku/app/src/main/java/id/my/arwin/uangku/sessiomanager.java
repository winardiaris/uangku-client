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
    public static final String mypref = "uangku" ;
    public static final String TAG_USERNAME = "username" ;
    public static final String TAG_PASSWORD = "password" ;
    public static final String TAG_USERSID = "id" ;
    public static final String TAG_TOKEN = "token" ;
    public static final String isloginkey = "islogin" ;

    public static final String TAG_NAME = "name";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_C_AT = "created_at";
    public static final String TAG_U_AT = "updated_at";
    public static final String TAG_DATA = "data";
    public static final String TAG_GETDATA = "getdata";

    public sessiomanager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(mypref, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setsessionlogin(String token) throws IOException {

            String result = null;
//            ArrayList<NameValuePair> post_parameter = new ArrayList<>();
//            post_parameter.add(new BasicNameValuePair("username", username));
//            post_parameter.add(new BasicNameValuePair("password", password));
            try{
//                String jsonStr = CustomHTTPClient.executeHttpPost(url+"/users/"+token, post_parameter);
                ServiceHandler sh = new ServiceHandler();
                String jsonStr = sh.makeServiceCall(url+"/users/"+token, ServiceHandler.GET);

                if (jsonStr != null) {
                    try {
                        Log.d("setsessionlogin result json: ",jsonStr);
                        JSONObject obj = new JSONObject(jsonStr);


                        String username = obj.getString(TAG_USERNAME);
                        String realname = obj.getString(TAG_NAME);
                        String users_id = obj.getString(TAG_USERSID);
                        String email = obj.getString(TAG_EMAIL);
                        String cat = obj.getString(TAG_C_AT);
                        String uat = obj.getString(TAG_U_AT);

//                      save session
                        editor.putString(TAG_USERNAME, username);
                        editor.putString(TAG_USERSID, users_id);
                        editor.putString(TAG_TOKEN, token);
                        editor.putBoolean(isloginkey, true);
                        editor.putString(TAG_NAME, realname);
                        editor.putString(TAG_EMAIL, email);
                        editor.putString(TAG_C_AT, cat);
                        editor.putString(TAG_U_AT, uat);
                        editor.commit();

                        Log.d("setsessionlogin", "-------------------------------------");
                        Log.d("status:", "Succes");
                        Log.d("users_id:", users_id);
                        Log.d("Username:", username);
                        Log.d("Realname:", realname);
                        Log.d("email:", email);
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
//    public boolean checklogin(){
//        // Check login status
//        if(!this.isUserLoggedIn()){
//
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, sessiomanager.class);
//
//            // Closing all the Activities from stack
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//
//            return true;
//        }
//        return false;
//    }

    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(TAG_USERNAME, pref.getString(TAG_USERNAME, null));
        user.put(TAG_PASSWORD, pref.getString(TAG_PASSWORD, null));
        user.put(TAG_USERSID, pref.getString(TAG_USERSID, null));
        user.put(TAG_NAME,pref.getString(TAG_NAME,null));
        user.put(TAG_TOKEN,pref.getString(TAG_TOKEN,null));
        user.put(TAG_EMAIL,pref.getString(TAG_EMAIL,null));
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


//    public String getusers_id(String token) throws JSONException {
//        ServiceHandler sh = new ServiceHandler();
//        // Making a request to url and getting response
////        sh.makeServiceCall(url+"?op=get&from_data=username&value_data=" + username + "&select_field=users_id&from_table=user", ServiceHandler.GET);
//        String jsonStr = sh.makeServiceCall(url+"/getusers_id/"+token, ServiceHandler.GET);
//        Log.d("getusers_id", "-----------------------------");
//
//        Log.d("json :", jsonStr);
//
//        JSONObject obj = new JSONObject(jsonStr);
//        JSONArray data = obj.getJSONArray(TAG_DATA);
//
//        JSONObject object = data.getJSONObject(0);
//       String users_id_ = object.getString(TAG_GETDATA);
//        Log.d("users_id dari get data",users_id_);
//
//        return users_id_;
//    }



}
