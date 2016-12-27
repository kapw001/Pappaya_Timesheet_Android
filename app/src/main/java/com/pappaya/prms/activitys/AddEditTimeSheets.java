package com.pappaya.prms.activitys;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pappaya.prms.utils.Utilitys;

import java.util.ArrayList;
import java.util.Calendar;

import com.pappaya.prms.R;
import com.pappaya.prms.model.AddDates;

public class AddEditTimeSheets extends AppCompatActivity {

    private LinearLayout laycalendar, laytimer;
    private TextView calendaredit, timeredit;
    private EditText descriptionedit;

    private ArrayList<AddDates> list;
    private int position;

    private String currentdate, hour, des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_time_sheets);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        list = getIntent().getParcelableArrayListExtra("data");
        position = getIntent().getIntExtra("position", 0);

        Toast.makeText(AddEditTimeSheets.this, "" + position, Toast.LENGTH_SHORT).show();

        currentdate = getIntent().getStringExtra("date");
        hour = getIntent().getStringExtra("hour");
        des = getIntent().getStringExtra("des");

        laycalendar = (LinearLayout) findViewById(R.id.laycalendar);
        laytimer = (LinearLayout) findViewById(R.id.timerlay);

        calendaredit = (TextView) findViewById(R.id.calendaredit);
        timeredit = (TextView) findViewById(R.id.timeredit);
        descriptionedit = (EditText) findViewById(R.id.descriptionedit);

        calendaredit.setText(currentdate);
        timeredit.setText(hour);
        descriptionedit.setText(des);


        laycalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilitys.callCallender(calendaredit, AddEditTimeSheets.this);
            }
        });

        laytimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTimer();
            }
        });

    }

    private void callTimer() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(AddEditTimeSheets.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeredit.setText(selectedHour + "." + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
//        mTimePicker.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mTimePicker.setTitle("Select Time");

        mTimePicker.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addedit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {

//            list.get(0).setDates(calendaredit.getText().toString());
//            list.get(0).setHours(timeredit.getText().toString());
//            list.get(0).setComments(descriptionedit.getText().toString());
            callResult();

            return true;
        } else if (item.getItemId() == android.R.id.home) {
            callResult();
            finish(); // close this activity and return to preview activity (if there is any)
        }


        return super.onOptionsItemSelected(item);
    }


    private void callResult() {

        currentdate = calendaredit.getText().toString();
        hour = timeredit.getText().toString();
        des = descriptionedit.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("date", currentdate);
        intent.putExtra("hour", hour);
        intent.putExtra("des", des);
//            intent.putParcelableArrayListExtra("data", list);

        setResult(2, intent);
        finish();//finishing activity

    }

}
