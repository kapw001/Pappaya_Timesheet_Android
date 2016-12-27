package com.pappaya.prms.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.pappaya.prms.interfaceses.Call;
import com.pappaya.prms.interfaceses.CallJsonRequest;
import com.pappaya.prms.model.TimeSheet;
import com.pappaya.prms.model.TimeSheetActivitys;
import com.pappaya.prms.sessionmanagement.SessionManager;
import com.pappaya.prms.utils.JsonRequest;
import com.pappaya.prms.utils.Utilitys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.pappaya.prms.R;
import com.pappaya.prms.activitys.AddEditTimeSheets;
import com.pappaya.prms.activitys.AddNewProjectTimesheets;
import com.pappaya.prms.activitys.AskStartDateEndDate;
import com.pappaya.prms.activitys.WeeklyEditable;
import com.pappaya.prms.interfaceses.AddDeleteRow;
import com.pappaya.prms.interfaceses.CallFragment;
import com.pappaya.prms.model.AddDates;
import com.pappaya.prms.model.Projects;
import com.pappaya.prms.recycle.ComplexRecyclerViewAdapter;

/**
 * Created by yasar on 25/11/16.
 */
public class Weekly extends Fragment implements AddDeleteRow {
    public static List<TimeSheet> movieList = new ArrayList<>();
    private static final String TAG = "Weekly";
    private RecyclerView recyclerView;
    private ComplexRecyclerViewAdapter mAdapter;
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

    ArrayList list = new ArrayList<>();

    private Call call;

    List<AddDates> addDatesList = new ArrayList<>();

    SessionManager sessionManager;
    String timesheetid;

    private CallJsonRequest callJsonRequest;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callFragment = (CallFragment) activity;
        call = (Call) activity;
        callJsonRequest = (CallJsonRequest) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.weekly, container, false);
        Intent in = new Intent(getActivity(), AskStartDateEndDate.class);
//        in.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) movieList);
        startActivityForResult(in, RESULT_CODEFIRST);

        sessionManager = new SessionManager(getActivity());

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewweekly);

        card_view = (CardView) v.findViewById(R.id.card_view);

        card_view.setVisibility(View.INVISIBLE);

        edit = (Button) v.findViewById(R.id.edit);
        submit = (Button) v.findViewById(R.id.submit);
        addprojects = (ImageView) v.findViewById(R.id.addprojects);

        startDate = (TextView) v.findViewById(R.id.startdate);
        totalhours = (TextView) v.findViewById(R.id.totalhours);
        endDate = (TextView) v.findViewById(R.id.enddate);

        mAdapter = new ComplexRecyclerViewAdapter(list, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AddNewProjectTimesheets.class);
                in.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) movieList);
                startActivityForResult(in, RESULT_CODEANOTHER);
            }
        });

//        getSampleArrayList();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), WeeklyEditable.class);
                startActivity(in);
            }
        });

//        Calendar calendar = Calendar.getInstance();
//        String startdate = new SimpleDateFormat("MM/dd/yy").format(calendar.getTime());

//        startDate.setText(new SimpleDateFormat("MM/dd/yy").format(calendar.getTime()));
//        calendar.add(Calendar.DATE, 6);
//        endDate.setText(new SimpleDateFormat("MM/dd/yy").format(calendar.getTime()));


        return v;

    }

    public void setFetchDataList(List<TimeSheet> movieList) {
        this.movieList = movieList;

        Log.e(TAG, "setFetchDataList: " + movieList.size());
    }

    public void setFetchDataList1(List<TimeSheet> movieList) {
        this.movieList = movieList;

        Log.e(TAG, "setFetchDataList: " + movieList.size());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
//        menu.getItem(0).setVisible(false);
        inflater.inflate(R.menu.weeklymenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            JSONArray jsonArray = new JSONArray();

//            Log.e(TAG, "onOptionsItemSelected: " + addDatesList.toString());

            for (int i = 0; i < addDatesList.size(); i++) {


                addDatesList.get(i).setDate(Utilitys.converDateServer(addDatesList.get(i).getDate()));

                JSONObject jsonObject = new JSONObject();
                AddDates addDates = addDatesList.get(i);
                try {
                    jsonObject.put("date", addDates.getDate());
                    jsonObject.put("account_id", addDates.getAccount_id());
                    jsonObject.put("is_timesheet", addDates.is_timesheet());
                    jsonObject.put("line_id", Integer.parseInt(addDates.getLine_id()));
                    jsonObject.put("name", addDates.getName());
                    jsonObject.put("unit_amount", Double.parseDouble(addDates.getUnit_amount()));
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            JSONArray jsArray = new JSONArray(addDatesList);

            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(addDatesList).getAsJsonArray();
            Log.e(TAG, "onOptionsItemSelected: Gson " + myCustomArray);
            JSONObject params = new JSONObject();
            try {

                params.put("db", sessionManager.getUserDetails().get(SessionManager.KEY_URL));
                params.put("login", sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
                params.put("password", sessionManager.getUserDetails().get(SessionManager.KEY_PASSWORD));
                params.put("sheet_id", timesheetid);

                params.put("lines", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject();


            try {
                jsonObject.put("jsonrpc", "2.0");
                jsonObject.put("method", "call");
                jsonObject.put("id", "2");
                jsonObject.put("params", params);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e(TAG, "onOptionsItemSelected: post format " + jsonObject.toString());


            JsonRequest.makeRequest(getActivity(), jsonObject, "update", new JsonRequest.RequestCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    Log.e(TAG, "onSuccess:Update Timesheet   " + response);
                    callJsonRequest.onCallRequest();
//                    getActivity().finish();

                    Toast.makeText(getActivity(), "Timesheet Saved", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void OnFail(VolleyError error) {
                    Log.e(TAG, "OnFail: " + error);
                }
            });


            for (int i = 0; i < addDatesList.size(); i++) {

                addDatesList.get(i).setDate(Utilitys.converDate(addDatesList.get(i).getDate()));
            }

            Toast.makeText(getActivity(), "Timesheet saved!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.submit) {


            JSONObject params = new JSONObject();
            try {

                params.put("db", sessionManager.getUserDetails().get(SessionManager.KEY_URL));
                params.put("login", sessionManager.getUserDetails().get(SessionManager.KEY_NAME));
                params.put("password", sessionManager.getUserDetails().get(SessionManager.KEY_PASSWORD));
                params.put("sheet_id", timesheetid);
                params.put("state", "confirm");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject();


            try {
                jsonObject.put("jsonrpc", "2.0");
                jsonObject.put("method", "call");
                jsonObject.put("id", "2");
                jsonObject.put("params", params);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonRequest.makeRequest(getActivity(), jsonObject, "update_status", new JsonRequest.RequestCallback() {
                @Override
                public void onSuccess(JSONObject response) {

                    Log.e(TAG, "onSuccess:Update Timesheet   " + response);

                    callJsonRequest.onCallRequest();
//                    getActivity().finish();
                    callFragment.callFragment(R.id.weekly);
                }

                @Override
                public void OnFail(VolleyError error) {
                    Log.e(TAG, "OnFail: " + error);
                }
            });


            Toast.makeText(getActivity(), "Timesheet submit!", Toast.LENGTH_SHORT).show();
//            Intent in = new Intent(getActivity(), WeeklyEditable.class);
//            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }


    private void getSampleArrayList(String datefrom, String dateto, String billable, String projects, String projectid, String sheetid) {


        timesheetid = sheetid;

        startDate.setText(Utilitys.displayDateAnother(datefrom));
        endDate.setText(Utilitys.displayDateAnother(dateto));

        Calendar c = Calendar.getInstance();

        String newDatefrom = datefrom;


//        ArrayList<Object> items = new ArrayList<>();

//        list.add(new Projects(projects, billable, projectid, sheetid));


        Date s = new Date(newDatefrom);
        Date e = new Date(dateto);
        Calendar nc = Calendar.getInstance();
        nc.setTime(e);
        nc.add(Calendar.DATE, 1);
        String dgg = new SimpleDateFormat("MM/dd/yy").format(nc.getTime());
        e = new Date(dgg);

        c.setTime(s);

        double total_hours = 0.0;

        while (s.before(e)) {
            String timeStamp = new SimpleDateFormat("MM/dd/yy").format(c.getTime());
            list.add(new AddDates(timeStamp, "8", "Description", "0", projectid, projects));
            addDatesList.add(new AddDates(timeStamp, "8", "Description", "0", projectid, projects));
            c.add(Calendar.DATE, 1);
            String d = new SimpleDateFormat("MM/dd/yy").format(c.getTime());
            s = new Date(d);


        }

        for (int i = 0; i < addDatesList.size(); i++) {
            total_hours += Double.parseDouble(addDatesList.get(i).getUnit_amount());
        }
        totalhours.setText("Total Hours: " + total_hours);

        mAdapter.notifyDataSetChanged();
        setHasOptionsMenu(true);
        card_view.setVisibility(View.VISIBLE);

    }

    @Override
    public void delete(int position) {

        list.remove(position);
        addDatesList.remove(position);

        double total_hours = 0.0;
        for (int i = 0; i < addDatesList.size(); i++) {
            total_hours += Double.parseDouble(addDatesList.get(i).getUnit_amount());
        }

        totalhours.setText("Total Hours: " + total_hours);


        mAdapter.notifyDataSetChanged();
    }

    private int pos;

    @Override
    public void addorEdit(int position) {
        int pos = position;

        if (list.get(position) instanceof AddDates) {
            AddDates data = (AddDates) list.get(position);

            Intent intent = new Intent(getActivity(), AddEditTimeSheets.class);

            intent.putExtra("position", pos);
            intent.putExtra("date", data.getDate());
            intent.putExtra("hour", data.getUnit_amount());
            intent.putExtra("des", data.getName());


            startActivityForResult(intent, RESULT_CODE);
        }


    }

    @Override
    public void addNewProjects() {

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
            AddDates s1 = (AddDates) addDatesList.get(position);
//            s.getComments();
//            s.setComments("dzfsdfsd");

            s.setName(data.getStringExtra("des"));
            s.setUnit_amount(data.getStringExtra("hour"));
            s.setDate(data.getStringExtra("date"));

            s1.setName(data.getStringExtra("des"));
            s1.setUnit_amount(data.getStringExtra("hour"));
            s1.setDate(data.getStringExtra("date"));
//            Toast.makeText(getActivity(), "" + s.getComments(), Toast.LENGTH_SHORT).show();

            double total_hours = 0.0;
            for (int i = 0; i < addDatesList.size(); i++) {
                total_hours += Double.parseDouble(addDatesList.get(i).getUnit_amount());
            }

            totalhours.setText("Total Hours: " + total_hours);

            mAdapter.notifyDataSetChanged();

        } else if (requestCode == RESULT_CODEANOTHER) {

            if (data.getBooleanExtra("isAdded", false)) {
//                list.add(new Projects(data.getStringExtra("project"), data.getStringExtra("billable")));
//              AddDates(timeStamp, "0 Hrs", "Description")
                list.add(new AddDates(data.getStringExtra("date"), data.getStringExtra("hour"), data.getStringExtra("des"), "0", data.getStringExtra("projectid"), data.getStringExtra("project")));
                addDatesList.add(new AddDates(data.getStringExtra("date"), data.getStringExtra("hour"), data.getStringExtra("des"), "0", data.getStringExtra("projectid"), data.getStringExtra("project")));
                mAdapter.notifyDataSetChanged();

                double total_hours = 0.0;
                for (int i = 0; i < addDatesList.size(); i++) {
                    total_hours += Double.parseDouble(addDatesList.get(i).getUnit_amount());
                }

                totalhours.setText("Total Hours: " + total_hours);
                mAdapter.notifyDataSetChanged();
            }

        } else if (requestCode == RESULT_CODEFIRST) {
            if (data.getBooleanExtra("isAdded", false)) {
                getSampleArrayList(data.getStringExtra("datefrom"), data.getStringExtra("dateto"), data.getStringExtra("billable"), data.getStringExtra("project"), data.getStringExtra("projectid"), data.getStringExtra("sheetid"));
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
