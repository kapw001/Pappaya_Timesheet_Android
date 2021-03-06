package com.pappaya.prms.recycle;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pappaya.prms.model.Account_ids;
import com.pappaya.prms.model.TimeSheet;
import com.pappaya.prms.model.TimeSheetActivitys;
import com.pappaya.prms.utils.Utilitys;

import java.util.ArrayList;
import java.util.List;

import com.pappaya.prms.R;
import com.pappaya.prms.activitys.ApproveRejectActicity;
import com.pappaya.prms.activitys.WeeklyActivity;
import com.pappaya.prms.model.TimeSheetDetail;

/**
 * Created by yasar on 25/11/16.
 */
public class MyTimesRecyclerView extends RecyclerView.Adapter<MyTimesRecyclerView.MyViewHolder> {

    private List<TimeSheet> timesheetsModelsList;


    public MyTimesRecyclerView(List<TimeSheet> timesheetsModelsList) {
        this.timesheetsModelsList = timesheetsModelsList;
    }


    @Override
    public MyTimesRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_timesheets, parent, false);

        return new MyViewHolder(view);
    }

    private static final String TAG = "MyTimesRecyclerView";

    @Override
    public void onBindViewHolder(MyTimesRecyclerView.MyViewHolder holder, int position) {

        if (!timesheetsModelsList.isEmpty()) {
            final List<TimeSheetDetail> timesheetsModel = timesheetsModelsList.get(position).getTimeSheetDetailslist();
            for (int i = 0; i < timesheetsModel.size(); i++) {
                final TimeSheetDetail m = timesheetsModel.get(i);
                Log.e(TAG, "onBindViewHolder: " + m.getDate_from() + "  Test  " + m.getTotal_timesheet());


                holder.startdate.setText(Utilitys.displayDate(m.getDate_from()));
                holder.enddate.setText(Utilitys.displayDate(m.getDate_to()));
                holder.hours.setText(m.getTotal_timesheet());

                ArrayList<Account_ids> account_idsArrayList = m.getAccount_ids().getAccount_idsArrayList();

                String projects = "";
                for (Account_ids projectname : account_idsArrayList
                        ) {
                    projects = projectname.getName() + "  ";
                }


                holder.project.setText(projects);
//        holder.status.setText(timesheetsModel.getStatus());
                if (m.getState().toLowerCase().equalsIgnoreCase("done")) {
                    holder.txtstatus.setText("Approved");
                    holder.status.setBackgroundResource(R.drawable.approved);
                } else if (m.getState().equalsIgnoreCase("draft")) {
                    holder.txtstatus.setText("Open");
                    holder.status.setBackgroundResource(R.drawable.openicon);
                } else if (m.getState().equalsIgnoreCase("confirm")) {
                    holder.txtstatus.setText("Waiting Approval");
                    holder.status.setBackgroundResource(R.drawable.waitingforapproval);
                } else if (m.getState().equalsIgnoreCase("new")) {
                    holder.txtstatus.setText("New");
                } else if (m.getState().equalsIgnoreCase("rejected")) {
//            drawable.setColor(Color.RED);
                    holder.txtstatus.setText("Rejected");
                    holder.status.setBackgroundResource(R.drawable.rejected);

                }

                final String proj = projects;

                final String formattedDatefrom = Utilitys.displayDate(m.getDate_from());
                final String formattedDateto = Utilitys.displayDate(m.getDate_to());
                final ArrayList<TimeSheetActivitys> openActivity = timesheetsModelsList.get(position).getTimeSheetActivityseslist();

                final String sheetid = timesheetsModel.get(i).getId();


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        Intent in = new Intent(view.getContext(), ApproveRejectActicity.class);

                        if (m.getState().equalsIgnoreCase("draft")) {
                            Intent in = new Intent(view.getContext(), WeeklyActivity.class);
                            in.putParcelableArrayListExtra("open", openActivity);
                            in.putExtra("startdate", formattedDatefrom);
                            in.putExtra("enddate", formattedDateto);
                            in.putExtra("hours", m.getTotal_timesheet());
                            in.putExtra("project", proj);
                            in.putExtra("sheetid", sheetid);
                            in.putExtra("billable", "");
                            in.putExtra("menuenable", true);
                            in.putExtra("updatetimesheet", true);
                            view.getContext().startActivity(in);
                        } else {

                            Intent in = new Intent(view.getContext(), ApproveRejectActicity.class);
                            in.putParcelableArrayListExtra("open", openActivity);
                            in.putExtra("startdate", formattedDatefrom);
                            in.putExtra("enddate", formattedDateto);
                            in.putExtra("hours", m.getTotal_timesheet());
                            in.putExtra("project", proj);
                            in.putExtra("billable", "");
                            in.putExtra("menuenable", false);
                            view.getContext().startActivity(in);
                        }


//                        if (m.getState().equalsIgnoreCase("draft")) {
//                            Intent in = new Intent(view.getContext(), WeeklyActivity.class);
//                            in.putExtra("startdate", formattedDatefrom);
//                            in.putExtra("enddate", formattedDateto);
//                            in.putExtra("hours", m.getTotal_timesheet());
//                            in.putExtra("project", proj);
//                            in.putExtra("billable", "");
//                            view.getContext().startActivity(in);
//                        }
                    }
                });

            }

        }


    }

    @Override
    public int getItemCount() {
        return timesheetsModelsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView startdate, enddate, hours, project, txtstatus;

        public ImageView imgtime, imgprojects, status;

        public MyViewHolder(View view) {
            super(view);
            startdate = (TextView) view.findViewById(R.id.startdate);
            enddate = (TextView) view.findViewById(R.id.enddate);
            project = (TextView) view.findViewById(R.id.project);
            hours = (TextView) view.findViewById(R.id.txttimer);
            imgtime = (ImageView) view.findViewById(R.id.imgtime);
            status = (ImageView) view.findViewById(R.id.status);
            txtstatus = (TextView) view.findViewById(R.id.txtstatus);

            imgprojects = (ImageView) view.findViewById(R.id.imgproject);
//            imgtime.setColorFilter(view.getResources().getColor(R.color.colorPrimary));
//            imgprojects.setColorFilter(view.getResources().getColor(R.color.colorPrimary));
        }
    }


}
