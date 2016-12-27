package com.pappaya.prms.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yasar on 28/11/16.
 */
public class Utilitys {
    public static String myFormat = "MM/dd/yy"; //In which you need put here
    public static SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private Calendar calendar = Calendar.getInstance();


    public static ProgressDialog progress;

    public static void showProgressDialog(Context context) {
        progress = ProgressDialog.show(context, "Timesheet", "Loading.........", true);
        progress.setCancelable(true);
    }

    public static void hideProgressDialog(Context context) {
        if (progress != null) {
            progress.dismiss();

        }
    }

    public static Date stringToDateUsingFormater(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // Convert from String to Date
        Date startDate = new Date();
        try {
            startDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;

    }

    public static String converDate(String value) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date datetxt = null;
        try {
            datetxt = parser.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        final String formattedDatefrom = formatter.format(datetxt);
        return formattedDatefrom;
    }

    public static String converDateServer(String value) {
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy");
        Date datetxt = null;
        try {
            datetxt = parser.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDatefrom = formatter.format(datetxt);
        return formattedDatefrom;
    }

    public static String displayDate(String value) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date datetxt = null;
        try {
            datetxt = parser.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM, yyyy");
        final String formattedDatefrom = formatter.format(datetxt);
        return formattedDatefrom;
    }

    public static Date getDateA(String value) {
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy");
        Date f = null;
        try {
            f = parser.parse(value);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static Date getDate(String value) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date f = null;
        try {
            f = parser.parse(value);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return f;
    }


    public static String displayDateAnother(String value) {
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy");
        Date datetxt = null;
        try {
            datetxt = parser.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM, yyyy");
        final String formattedDatefrom = formatter.format(datetxt);
        return formattedDatefrom;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //blah
        }
    }


    public static void callCallender(final EditText editText, Activity mContext) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                editText.setText(sdf.format(myCalendar.getTime()));
            }

        };
        new DatePickerDialog(mContext, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void callCallender(final TextView editText, Activity mContext) {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                editText.setText(sdf.format(myCalendar.getTime()));
            }

        };
        new DatePickerDialog(mContext, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void callCallenderReturn(final TextView editText, Activity mContext, final CallValue callValue) {

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

//                editText.setText(sdf.format(myCalendar.getTime()));
                callValue.value(sdf.format(myCalendar.getTime()));

            }


        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                callValue.value(null);
            }
        });

    }

    public interface CallValue {
        void value(String v);

    }

}
