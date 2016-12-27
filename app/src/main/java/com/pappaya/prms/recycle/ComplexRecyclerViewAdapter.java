package com.pappaya.prms.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
//import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.pappaya.prms.fragments.Weekly;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.pappaya.prms.R;

import com.pappaya.prms.interfaceses.AddDeleteRow;
import com.pappaya.prms.model.AddDates;
import com.pappaya.prms.model.Projects;

/**
 * Created by yasar on 25/11/16.
 */
public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;

    private final int PROJECTS = 0, ADDDATES = 1;

    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    private AddDeleteRow addDeleteRow;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapter(List<Object> items, Weekly weekly) {
        this.items = items;
        addDeleteRow = (AddDeleteRow) weekly;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Projects) {
            return PROJECTS;
        } else if (items.get(position) instanceof AddDates) {
            return ADDDATES;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case PROJECTS:
                View v1 = inflater.inflate(R.layout.row_projects, viewGroup, false);
                viewHolder = new ViewHolderProjects(v1);
                break;
            case ADDDATES:
                View v2 = inflater.inflate(R.layout.newdateeditanother, viewGroup, false);
                viewHolder = new ViewHolderAddDates(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.row_default, viewGroup, false);
                viewHolder = new ViewHolderAddDates(v3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //More to come
        switch (viewHolder.getItemViewType()) {
            case PROJECTS:
                ViewHolderProjects vh1 = (ViewHolderProjects) viewHolder;
                configureViewHolderProjects(vh1, position);
                break;
            case ADDDATES:


                ViewHolderAddDates vh2 = (ViewHolderAddDates) viewHolder;
//                Object data=items.get(position);
//
//                binderHelper.bind(vh2.swipeLayout, data);


                configureViewHolderAddDates(vh2, position);
                break;
            default:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                break;
        }
    }


    private void configureViewHolderProjects(ViewHolderProjects vh1, int position) {
        Projects projects = (Projects) items.get(position);
        if (projects != null) {
            vh1.getProjects().setText(projects.getProjectName());
            vh1.getBillable().setText(projects.getDescription());
        }
    }

    private void configureViewHolderAddDates(ViewHolderAddDates vh1, int position) {
        AddDates addDates = (AddDates) items.get(position);
        if (addDates != null) {
            vh1.getTxtcomments().setText(addDates.getName());
            vh1.getTxttimer().setText(addDates.getUnit_amount());

//            String date = addDates.getDates();
            Date date = new Date(addDates.getDate());
//            String timeStamp = new SimpleDateFormat("E").format(date);

            vh1.getDateinname().setText(new SimpleDateFormat("EEEE").format(date));
            vh1.getCurrendatedate().setText(new SimpleDateFormat("d").format(date));
            vh1.getCurrentdateinyear().setText(new SimpleDateFormat("MMM, yyyy").format(date));


        }
    }


    public class ViewHolderProjects extends RecyclerView.ViewHolder {

        private TextView projects, billable;

        public ViewHolderProjects(View v) {
            super(v);
            projects = (TextView) v.findViewById(R.id.projects);
            billable = (TextView) v.findViewById(R.id.billable);
        }

        public TextView getProjects() {
            return projects;
        }

        public void setProjects(TextView projects) {
            this.projects = projects;
        }

        public TextView getBillable() {
            return billable;
        }

        public void setBillable(TextView billable) {
            this.billable = billable;
        }
    }

    public class ViewHolderAddDates extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        private TextView dates, hours, comments;
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        private TextView txtcomments, txttimer, dateinname, currendatedate, currentdateinyear;
        private EditText editcomments;
        private LinearLayout timepickerlayout;

        private SwipeRevealLayout swipeLayout;

        private LinearLayout bottom_wrapper;

        private RelativeLayout edit, delete;

        private ImageView imageViewEdit, imageViewDelete;

        public ViewHolderAddDates(final View v) {
            super(v);
            v.setOnClickListener(this);
//            dates = (TextView) v.findViewById(R.id.dates);
//            hours = (TextView) v.findViewById(R.id.hours);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe);
//            bottom_wrapper = (LinearLayout) itemView.findViewById(R.id.bottom_wrapper);
            imageViewEdit = (ImageView) itemView.findViewById(R.id.imageedit);
            imageViewDelete = (ImageView) itemView.findViewById(R.id.imagedelete);

            imageViewEdit.setColorFilter(itemView.getResources().getColor(R.color.colorPrimary));
            imageViewDelete.setColorFilter(itemView.getResources().getColor(R.color.red));

            edit = (RelativeLayout) itemView.findViewById(R.id.tvEdit);
            delete = (RelativeLayout) itemView.findViewById(R.id.tvDelete);
            txtcomments = (TextView) v.findViewById(R.id.txtcomments);
            txttimer = (TextView) v.findViewById(R.id.txttimer);

            dateinname = (TextView) v.findViewById(R.id.dateinname);
            currendatedate = (TextView) v.findViewById(R.id.currendatedate);
            currentdateinyear = (TextView) v.findViewById(R.id.currentdateinyear);

            timepickerlayout = (LinearLayout) v.findViewById(R.id.timepickerlayout);


//            editcomments = (EditText) v.findViewById(R.id.editcomments);
//            editcomments.setImeOptions(EditorInfo.IME_ACTION_DONE);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(v.getContext(), "Edit", Toast.LENGTH_SHORT).show();

                    addDeleteRow.addorEdit(getPosition());


//                    DFragment dFragment = new DFragment();
                    // Show DialogFragment
//                    dFragment.show(Weekly.class, "Dialog Fragment");
//                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
//                    builder.setView(view);
//                    Dialog dialog = builder.create();
//                    Window window = dialog.getWindow();
//                    window.requestFeature(Window.FEATURE_NO_TITLE);
//                    WindowManager.LayoutParams wlp = window.getAttributes();
//                    window.setAttributes(wlp);
//                    dialog.getWindow().setCallback(windowCallback);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(v.getContext(), "Delete", Toast.LENGTH_SHORT).show();
//                    items.remove(getPosition());
                    addDeleteRow.delete(getPosition());


                }
            });

//            timepickerlayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Calendar mcurrentTime = Calendar.getInstance();
//                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                    int minute = mcurrentTime.get(Calendar.MINUTE);
//                    TimePickerDialog mTimePicker;
//                    mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
//                        @Override
//                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                            txttimer.setText(selectedHour + ":" + selectedMinute + " Hrs");
//                        }
//                    }, hour, minute, true);//Yes 24 hour time
//                    mTimePicker.setTitle("Select Time");
//                    mTimePicker.show();
//                }
//            });


//            txtcomments.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    final Dialog m_dialog;
//                    m_dialog = new Dialog(v.getContext());
////                    m_dialog.getWindow();
//                    m_dialog.getWindow()
//                            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                    m_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//                    LayoutInflater m_inflater = LayoutInflater.from(v.getContext());
//                    View m_view = m_inflater.inflate(R.layout.popup, null);
//                    final EditText yourEditText = (EditText) m_view.findViewById(R.id.editText);
//                    yourEditText.requestFocus();
//
//                    yourEditText.setText(txtcomments.getText());
//
//                    Button done = (Button) m_view.findViewById(R.id.dialogdone);
//                    Button cancel = (Button) m_view.findViewById(R.id.dialogcancel);
//
//                    done.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            txtcomments.setText(yourEditText.getText());
//
//                            m_dialog.hide();
//
//                        }
//                    });
//
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            m_dialog.hide();
//                        }
//                    });


//                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(yourEditText, InputMethodManager.SHOW_FORCED);

//                    myPopLay = (LinearLayout) m_view.findViewById(R.id.myPopLay);
//                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                            RecyclerView.LayoutParams.MATCH_PARENT,
//                            RecyclerView.LayoutParams.MATCH_PARENT
//                    );
//                    m_dialog.setContentView(m_view);
//                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                    lp.copyFrom(m_dialog.getWindow().getAttributes());
//                    lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
//                    lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    m_dialog.getWindow().setAttributes(lp);
//                    m_dialog.setCanceledOnTouchOutside(true);
//                    m_dialog.show();


//                    LayoutInflater layoutInflater
//                            = (LayoutInflater) v.getContext()
//                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View popupView = layoutInflater.inflate(R.layout.popup, null);
//                    final PopupWindow popupWindow = new PopupWindow(
//                            popupView,
//                            RecyclerView.LayoutParams.MATCH_PARENT,
//                            RecyclerView.LayoutParams.WRAP_CONTENT);
//
//                    EditText editText = (EditText) popupView.findViewById(R.id.editText);
//
//
//                    Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
//                    popupWindow.setOutsideTouchable(true);
//                    btnDismiss.setOnClickListener(new Button.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            // TODO Auto-generated method stub
//                            popupWindow.dismiss();
//                        }
//                    });
//
//                    popupWindow.showAsDropDown(txtcomments, 50, -30);
//                }
//            });
//            editcomments.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus) {
//
//                    } else {
//                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(editcomments.getWindowToken(), 0);
//                        txtcomments.setVisibility(View.VISIBLE);
//                        editcomments.setVisibility(View.INVISIBLE);
//                        txtcomments.setFocusable(false);
//                    }
//                }
//            });
//
//            KeyboardVisibilityEvent.setEventListener(
//                    (Activity) v.getContext(),
//                    new KeyboardVisibilityEventListener() {
//                        @Override
//                        public void onVisibilityChanged(boolean isOpen) {
//                            // some code depending on keyboard visiblity status
//                            if (isOpen) {
//
//                            } else {
//                                txtcomments.setFocusable(false);
//                                txtcomments.setVisibility(View.VISIBLE);
//                                editcomments.setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    });

        }

        public TextView getTxtcomments() {
            return txtcomments;
        }

        public void setTxtcomments(TextView txtcomments) {
            this.txtcomments = txtcomments;
        }

        public TextView getTxttimer() {
            return txttimer;
        }

        public void setTxttimer(TextView txttimer) {
            this.txttimer = txttimer;
        }

        public TextView getDateinname() {
            return dateinname;
        }

        public void setDateinname(TextView dateinname) {
            this.dateinname = dateinname;
        }

        public TextView getCurrendatedate() {
            return currendatedate;
        }

        public void setCurrendatedate(TextView currendatedate) {
            this.currendatedate = currendatedate;
        }

        public EditText getEditcomments() {
            return editcomments;
        }

        public void setEditcomments(EditText editcomments) {
            this.editcomments = editcomments;
        }

        public TextView getCurrentdateinyear() {
            return currentdateinyear;
        }

        public void setCurrentdateinyear(TextView currentdateinyear) {
            this.currentdateinyear = currentdateinyear;
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.txtcomments:
//                    txtcomments.setVisibility(View.INVISIBLE);
//                    editcomments.setVisibility(View.VISIBLE);
//                    txtcomments.setFocusable(true);
                    break;
                case R.id.editcomments:
//                    txtcomments.setVisibility(View.VISIBLE);
//                    editcomments.setVisibility(View.INVISIBLE);
                    break;
                default:
//                    txtcomments.setVisibility(View.VISIBLE);
//                    editcomments.setVisibility(View.INVISIBLE);
                    break;
            }

        }


//        public TextView getDates() {
//            return dates;
//        }
//
//        public void setDates(TextView dates) {
//            this.dates = dates;
//        }
//
//        public TextView getHours() {
//            return hours;
//        }
//
//        public void setHours(TextView hours) {
//            this.hours = hours;
//        }
//
//        public TextView getComments() {
//            return comments;
//        }
//
//        public void setComments(TextView comments) {
//            this.comments = comments;
//        }
    }

    private class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {
        public RecyclerViewSimpleTextViewHolder(View itemView) {
            super(itemView);
        }
    }


}