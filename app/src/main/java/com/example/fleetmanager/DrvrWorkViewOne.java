package com.example.fleetmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DrvrWorkViewOne extends AppCompatActivity {
    EditText vTodt,vFromdt;
    Button btn1,btn2;

    RadioGroup rg1;
    RadioButton rb1;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drvr_work_view_one);

        vTodt=(EditText)findViewById(R.id.todate);
        vFromdt=(EditText)findViewById(R.id.frmdt);

        btn1=(Button)findViewById(R.id.viewBtn);
        btn2=(Button)findViewById(R.id.cnclbtn);


        rg1=findViewById(R.id.rdgrpChoice);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        vTodt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DrvrWorkViewOne.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        vFromdt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DrvrWorkViewOne.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void viewfWrk(View view) {
        String todate1,frdate;

        todate1 = vTodt.getText().toString();
        frdate = vFromdt.getText().toString();

        rb1=findViewById(rg1.getCheckedRadioButtonId());

        String c=rb1.getText().toString();

        Intent i = new Intent(DrvrWorkViewOne.this, DrvrWorkViewTwo.class);

        i.putExtra("Wtodate",todate1);

        i.putExtra("Wfromdate",frdate);
        i.putExtra("choice",c);

        startActivity(i);



    }

    public void vcancel(View view) {

        this.finish();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        vTodt.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        vFromdt.setText(sdf.format(myCalendar.getTime()));
    }
}
