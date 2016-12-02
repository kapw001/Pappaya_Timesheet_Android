package xirid.xirid.com.timesheets.recycle;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xirid.xirid.com.timesheets.R;
import xirid.xirid.com.timesheets.model.TimesheetsModel;

/**
 * Created by yasar on 25/11/16.
 */
public class MyTimesApproveRecyclerView extends RecyclerView.Adapter<MyTimesApproveRecyclerView.MyViewHolder> {

    private List<TimesheetsModel> timesheetsModelsList;


    public MyTimesApproveRecyclerView(List<TimesheetsModel> timesheetsModelsList) {
        this.timesheetsModelsList = timesheetsModelsList;
    }


    @Override
    public MyTimesApproveRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_timesheetstoapprove, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyTimesApproveRecyclerView.MyViewHolder holder, int position) {

        TimesheetsModel timesheetsModel = timesheetsModelsList.get(position);

        holder.startdate.setText(timesheetsModel.getStartDate());
        holder.enddate.setText(timesheetsModel.getEndDate());
        holder.hours.setText(timesheetsModel.getHours());
        holder.status.setText(timesheetsModel.getStatus());
        holder.employeename.setText(timesheetsModel.getName());

        if (timesheetsModel.getStatus().equalsIgnoreCase("Open")) {
            holder.status.setTextColor(Color.MAGENTA);

        } else if (timesheetsModel.getStatus().equalsIgnoreCase("Waiting for approval")) {
            holder.status.setTextColor(Color.YELLOW);

        } else if (timesheetsModel.getStatus().equalsIgnoreCase("Approved")) {
            holder.status.setTextColor(Color.GREEN);

        } else if (timesheetsModel.getStatus().equalsIgnoreCase("Rejected")) {
            holder.status.setTextColor(Color.RED);

        }


    }

    @Override
    public int getItemCount() {
        return timesheetsModelsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView employeename, startdate, enddate, status, hours;

        public MyViewHolder(View view) {
            super(view);
            startdate = (TextView) view.findViewById(R.id.startdate);
            enddate = (TextView) view.findViewById(R.id.enddate);
            status = (TextView) view.findViewById(R.id.status);
            hours = (TextView) view.findViewById(R.id.hours);
            employeename = (TextView) view.findViewById(R.id.employeename);
        }
    }


}
