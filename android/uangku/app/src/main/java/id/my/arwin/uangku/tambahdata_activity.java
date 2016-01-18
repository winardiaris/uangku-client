package id.my.arwin.uangku;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by winardiaris on 1/15/16.
 */
public class tambahdata_activity extends FragmentActivity {
    String type = "in";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambahdata_activity);

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
                String jenis = type;

                final String tanggal = tanggal_.getText().toString();
                String jumlah = jumlah_.getText().toString();
                String bukti = bukti_.getText().toString();
                String ket = ket_.getText().toString();

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
                                    tanggal_.setText("");
                                    jumlah_.setText("");
                                    bukti_.setText("");
                                    ket_.setText("");
                                    tanggal_.requestFocus();
                                }
                    }).show();
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
}
