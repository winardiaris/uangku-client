package id.my.arwin.uangku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.my.arwin.uangku.Main.MainActivity;

/**
 * Created by winardiaris on 1/14/16.
 */
public class menuutama_activity extends Activity {
    sessiomanager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuutama_activity);
        session = new sessiomanager(getApplicationContext());

        //logout
        Button blogout = (Button) findViewById(R.id.blogout);
        blogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(menuutama_activity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Keluar")
                        .setMessage("Yakin mau keluar?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                session.logoutUser();

                                Intent i = new Intent(menuutama_activity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }).setNegativeButton("Tidak", null).show();
            }
        });

        Button bnewdata = (Button) findViewById(R.id.bnewdata);
        bnewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menuutama_activity.this,tambahdata_activity.class);
                startActivity(i);
            }
        });

        Button blistdata = (Button) findViewById(R.id.blistdata);
        blistdata.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(menuutama_activity.this,data_activity.class);
                startActivity(i);
            }
        });

        Button bsetting = (Button)findViewById(R.id.bsetting);
        bsetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                Intent i = new Intent(menuutama_activity.this,pengaturan_activity.class);
                startActivity(i);
            }
        });
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

}
