package com.pappaya.prms.activitys;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pappaya.prms.interfaceses.AddDeleteRow;
import com.pappaya.prms.interfaceses.Call;
import com.pappaya.prms.interfaceses.CallFragment;
import com.pappaya.prms.model.Projects;
import com.pappaya.prms.model.TimeSheet;
import com.pappaya.prms.recycle.ComplexRecyclerViewAdapterApproveRejectActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.pappaya.prms.R;

import com.pappaya.prms.model.AddDates;

public class ApproveRejectActicityBackup extends AppCompatActivity implements AddDeleteRow {
    private static final String TAG = "ApproveRejectActicity";
    private List<Object> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ComplexRecyclerViewAdapterApproveRejectActivity mAdapter;
    private CallFragment callFragment;

    private Button edit, submit;
    private ImageView addprojects;

    private TextView startDate, endDate, totalhours;

    private int edit_position;
    private View view;
    private boolean add = false;
    private Paint p = new Paint();
    private int RESULT_CODEFIRST = 1;
    private int RESULT_CODE = 2;
    private int RESULT_CODEANOTHER = 3;

    private CardView card_view;

    ArrayList<Object> list = new ArrayList<>();

    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewweekly);

        card_view = (CardView) findViewById(R.id.card_view);

        edit = (Button) findViewById(R.id.edit);
        submit = (Button) findViewById(R.id.submit);
        addprojects = (ImageView) findViewById(R.id.addprojects);

        startDate = (TextView) findViewById(R.id.startdate);

        endDate = (TextView) findViewById(R.id.enddate);
        totalhours = (TextView) findViewById(R.id.totalhours);

        mAdapter = new ComplexRecyclerViewAdapterApproveRejectActivity(list, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), AddNewProjectTimesheets.class);
                startActivityForResult(in, RESULT_CODEANOTHER);
            }
        });

//        getSampleArrayList();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), WeeklyEditable.class);
                startActivity(in);
            }
        });

        TimeSheet timeSheet = getIntent().getParcelableExtra("open");

        Log.e(TAG, "onCreate: open Activity  " + timeSheet.toString());


        getSampleArrayList(getIntent().getStringExtra("startdate"), getIntent().getStringExtra("enddate"), getIntent().getStringExtra("billable"), getIntent().getStringExtra("project"), getIntent().getStringExtra("hours"));

    }


    private void getSampleArrayList(String datefrom, String dateto, String billable, String projects, String hours) {

        startDate.setText(datefrom);
        endDate.setText(dateto);

        totalhours.setText(hours);

        Calendar c = Calendar.getInstance();

        String newDatefrom = datefrom;


//        ArrayList<Object> items = new ArrayList<>();

        list.add(new Projects(projects, billable));

        Date s = new Date(newDatefrom);
        Date e = new Date(dateto);
        Calendar nc = Calendar.getInstance();
        nc.setTime(e);
        nc.add(Calendar.DATE, 1);
        String dgg = new SimpleDateFormat("MM/dd/yy").format(nc.getTime());
        e = new Date(dgg);

        c.setTime(s);

        while (s.before(e)) {
            String timeStamp = new SimpleDateFormat("MM/dd/yy").format(c.getTime());
//            list.add(new AddDates(timeStamp, "8 Hrs", "Description"));
            c.add(Calendar.DATE, 1);
            String d = new SimpleDateFormat("MM/dd/yy").format(c.getTime());
            s = new Date(d);


        }

//        for (int i = 0; i < 7; i++) {
//
//            String timeStamp = new SimpleDateFormat("MM/dd/yy").format(c.getTime());
//
////            Random r = new Random();
////            int h = r.nextInt(24);
//
//            list.add(new AddDates(timeStamp, "8 Hrs", "Description"));
//            c.add(Calendar.DATE, 1);
//
//        }


        mAdapter.notifyDataSetChanged();


//        items.add(new AddDates("10/11/16", "Hours: 6 hrs", "Description"));
//        items.add(new AddDates("10/11/16", "Hours: 8 hrs", "Description"));
//        items.add(new AddDates("10/11/16", "Hours: 2 hrs", "Description"));
//        items.add(new AddDates("10/11/16", "Hours: 4 hrs", "Description"));
//        items.add(new AddDates("10/11/16", "Hours: 6 hrs", "Description"));
//        items.add(new AddDates("10/11/16", "Hours: 8 hrs", "Description"));


//        return items;
    }

    @Override
    public void delete(int position) {

        list.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    private int pos;

    @Override
    public void addorEdit(int position) {
        int pos = position;

        if (list.get(position) instanceof AddDates) {
            AddDates data = (AddDates) list.get(position);
//            pos = position;

//
            Intent intent = new Intent(getApplicationContext(), AddEditTimeSheets.class);

            intent.putExtra("position", pos);
            intent.putExtra("date", data.getDate());
            intent.putExtra("hour", data.getUnit_amount());
            intent.putExtra("des", data.getName());

//            Toast.makeText(getActivity(), "" + position + " " + data.getDates() + "  " + data.getHours() + "  " + data.getComments(), Toast.LENGTH_SHORT).show();

//            intent.putParcelableArrayListExtra("data", data.getList());


            startActivityForResult(intent, RESULT_CODE);
        }


    }

    @Override
    public void addNewProjects() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.approvereject, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.approve) {
            Toast.makeText(getApplicationContext(), "Timesheet approved!", Toast.LENGTH_SHORT).show();
//            Intent in = new Intent(getActivity(), WeeklyEditable.class);
//            startActivity(in);
        } else if (id == R.id.reject) {
            Toast.makeText(getApplicationContext(), "Timesheet rejected!", Toast.LENGTH_SHORT).show();
//            Intent in = new Intent(getActivity(), WeeklyEditable.class);
//            startActivity(in);
        } else if (item.getItemId() == android.R.id.home) {

            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == Activity.RESULT_OK) {

        if (requestCode == RESULT_CODE) {
            int position = data.getIntExtra("position", 0);
//            Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
//            ArrayList<AddDates> d = data.getParcelableArrayListExtra("data");

//            Toast.makeText(getActivity(), "" + position + "  " + data.getStringExtra("date"), Toast.LENGTH_SHORT).show();

            AddDates s = (AddDates) list.get(position);
//            s.getComments();
//            s.setComments("dzfsdfsd");

            s.setName(data.getStringExtra("des"));
            s.setUnit_amount(data.getStringExtra("hour"));
            s.setDate(data.getStringExtra("date"));
//            Toast.makeText(getActivity(), "" + s.getComments(), Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();

        } else if (requestCode == RESULT_CODEANOTHER) {

            if (data.getBooleanExtra("isAdded", false)) {
                list.add(new Projects(data.getStringExtra("project"), data.getStringExtra("billable")));
//              AddDates(timeStamp, "0 Hrs", "Description")
//                list.add(new AddDates(data.getStringExtra("date"), data.getStringExtra("hour"), data.getStringExtra("des")));
                mAdapter.notifyDataSetChanged();
            }

        } else if (requestCode == RESULT_CODEFIRST) {
            if (data.getBooleanExtra("isAdded", false)) {
                getSampleArrayList(data.getStringExtra("datefrom"), data.getStringExtra("dateto"), data.getStringExtra("billable"), data.getStringExtra("project"), data.getStringExtra("hours"));
            } else {
                call.call(R.id.weekly);
            }
        } else if (requestCode == 4) {

            call.call(R.id.weekly);

        } else if (requestCode == 5) {

        }
//        }
    }
}
