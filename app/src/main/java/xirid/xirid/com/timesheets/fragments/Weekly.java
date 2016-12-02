package xirid.xirid.com.timesheets.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xirid.xirid.com.timesheets.R;
import xirid.xirid.com.timesheets.activitys.AddNewProjectTimesheets;
import xirid.xirid.com.timesheets.activitys.WeeklyEditable;
import xirid.xirid.com.timesheets.interfaceses.CallFragment;
import xirid.xirid.com.timesheets.model.AddDates;
import xirid.xirid.com.timesheets.model.Projects;
import xirid.xirid.com.timesheets.model.TimesheetsModel;
import xirid.xirid.com.timesheets.recycle.ComplexRecyclerViewAdapter;
import xirid.xirid.com.timesheets.recycle.MyTimesRecyclerView;

/**
 * Created by yasar on 25/11/16.
 */
public class Weekly extends Fragment {

    private List<Object> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ComplexRecyclerViewAdapter mAdapter;
    private CallFragment callFragment;

    private Button edit, submit;
    private ImageView addprojects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callFragment = (CallFragment) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.weekly, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewweekly);

        edit = (Button) v.findViewById(R.id.edit);
        submit = (Button) v.findViewById(R.id.submit);
        addprojects = (ImageView) v.findViewById(R.id.addprojects);

        mAdapter = new ComplexRecyclerViewAdapter(getSampleArrayList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AddNewProjectTimesheets.class);
                startActivity(in);
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


        return v;

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
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.edit) {
//            Intent in = new Intent(getActivity(), WeeklyEditable.class);
//            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }


    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new Projects("Projects", "Billable"));
        items.add(new AddDates("10/11/16", "Hours: 6 hrs", "Comments"));
        items.add(new AddDates("10/11/16", "Hours: 8 hrs", "Comments"));
        items.add(new AddDates("10/11/16", "Hours: 2 hrs", "Comments"));
        items.add(new AddDates("10/11/16", "Hours: 4 hrs", "Comments"));
        items.add(new AddDates("10/11/16", "Hours: 6 hrs", "Comments"));
        items.add(new AddDates("10/11/16", "Hours: 8 hrs", "Comments"));


        return items;
    }
}
