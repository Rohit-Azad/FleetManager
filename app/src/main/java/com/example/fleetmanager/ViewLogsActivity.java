package com.example.fleetmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ViewLogsActivity extends AppCompatActivity {

    EditText toDate,fromDate;
    Spinner select;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);

        toDate=findViewById(R.id.etToDate);
        fromDate=findViewById(R.id.etFromDate);

        select=findViewById(R.id.spinnerSelect);

        String[] choice={"Driver","Mechanic"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.custom_spinner_layout,choice);

        select.setAdapter(adapter);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(toDate);
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
                updateLabel(fromDate);
            }

        };

        toDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ViewLogsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ViewLogsActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void show_logs(View view)
    {

        if(toDate.getText().toString().isEmpty() || fromDate.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please fill in dates",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(ViewLogsActivity.this, LogViewResultsActivity.class);
            i.putExtra("toDate", toDate.getText().toString());

            i.putExtra("FromDate", fromDate.getText().toString());
            i.putExtra("logTableDecider", select.getSelectedItem().toString());
            startActivity(i);
        }
    }

    public void go_back(View view)
    {
        Intent i=new Intent(ViewLogsActivity.this,AdminMenuMainPageActivity.class);
        startActivity(i);
    }

    private void updateLabel(EditText et) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et.setText(sdf.format(myCalendar.getTime()));
    }

}
