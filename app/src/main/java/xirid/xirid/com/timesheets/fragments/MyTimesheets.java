package xirid.xirid.com.timesheets.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xirid.xirid.com.timesheets.R;
import xirid.xirid.com.timesheets.interfaceses.CallFragment;
import xirid.xirid.com.timesheets.model.TimesheetsModel;
import xirid.xirid.com.timesheets.recycle.MyTimesRecyclerView;

/**
 * Created by yasar on 25/11/16.
 */
public class MyTimesheets extends Fragment {
    private List<TimesheetsModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyTimesRecyclerView mAdapter;
    private CallFragment callFragment;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callFragment = (CallFragment) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.mytimesheets, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewtimesheets);
        prepareMovieData();
        mAdapter = new MyTimesRecyclerView(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFragment.callFragment(R.id.weekly);
            }
        });
        return v;

    }

    private void prepareMovieData() {
        TimesheetsModel movie = new TimesheetsModel("10/11/16", "17/11/16", "48 hrs", "Open");
        movieList.add(movie);

        movie = new TimesheetsModel("10/11/16", "17/11/16", "10 hrs", "Waiting for approval");
        movieList.add(movie);

        movie = new TimesheetsModel("10/11/16", "17/11/16", "26 hrs", "Approved");
        movieList.add(movie);

        movie = new TimesheetsModel("10/11/16", "17/11/16", "5 hrs", "Open");
        movieList.add(movie);

        movie = new TimesheetsModel("10/11/16", "17/11/16", "32 hrs", "Rejected");
        movieList.add(movie);


//        mAdapter.notifyDataSetChanged();
    }
}
