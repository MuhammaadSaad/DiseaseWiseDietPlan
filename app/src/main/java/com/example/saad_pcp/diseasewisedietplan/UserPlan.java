package com.example.saad_pcp.diseasewisedietplan;


import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserPlan extends Fragment {


    public UserPlan() {
        // Required empty public constructor
    }



    TableLayout t;
    TableRow tr;
    TextView TimingTV,valueTV,plan,cal,TCal;
    Date d;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_plan, container, false);

        DataBaseHelper db = new DataBaseHelper(getContext());
        ArrayList<String> ylist = new ArrayList<String>();
        String someDate="";
        int pid;
        String pname = "", cday = "";
        Cursor cur = db.GetUserAcPlanDetails();
        cur.moveToFirst();
        boolean flag=false;
        if(cur.getCount()>0) {
            while (!cur.isAfterLast()) {

                pname = cur.getString(1);
                someDate = cur.getString(0);


                Date currentDate = new Date();

                GregorianCalendar gc = new GregorianCalendar();

                int yearat = gc.get(Calendar.YEAR);
                String yearstr = Integer.toString(yearat);
                int monthat = gc.get(Calendar.MONTH) + 1;
                String monthstr = Integer.toString(monthat);
                int dayat = gc.get(Calendar.DAY_OF_MONTH);
                String daystr = Integer.toString(dayat);
                String currentDateTimeString = daystr + "-" + monthstr + "-" + yearstr;

                d = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    currentDate = dateFormat.parse(currentDateTimeString);
                    d = dateFormat.parse(someDate);

                    if (currentDateTimeString.equals(someDate)) {
                        cday = cur.getString(2);
                        flag = true;
                    }

                    int com = d.compareTo(currentDate);


                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cur.moveToNext();
            }
            if (!flag) {
                if(pname.equals("")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(
                            R.id.rel_lyout_for_frags,
                            new CreatePlanFragment()
                    ).commit();

                }else {
                    db.ActivatePlan(pname);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(
                            R.id.rel_lyout_for_frags,
                            new UserPlan()
                    ).commit();
                }
            }
        }else{
            db=new DataBaseHelper(getContext());
            String dis=db.getUserDisease();
            if(dis.equals("")){
                getActivity().getSupportFragmentManager().beginTransaction().replace(
                        R.id.rel_lyout_for_frags,
                        new DiseaseSetting()
                ).commit();
            }
            if(pname.equals("")){
                getActivity().getSupportFragmentManager().beginTransaction().replace(
                        R.id.rel_lyout_for_frags,
                        new CreatePlanFragment()
                ).commit();

            }

        }
        //d.setDat();
        // Date date =Date.valueOf(someDate); //(Date) sdf.parse(someDate);




        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.



        plan = (TextView) root.findViewById(R.id.UserPlName);
        TCal = (TextView) root.findViewById(R.id.UserCal);
        t = (TableLayout) root.findViewById(R.id.Usermaintable);

//        String strtext = getArguments().getString("Plan Name");
        db = new DataBaseHelper(getContext());


        int tcal = db.GetPlanCal(db.GetPlanid(pname), pname);
        TCal.setText("Total Calories: " + tcal + " ");
        TCal.setGravity(Gravity.CENTER);
        TCal.setAllCaps(true);

        plan.setText("Plan Name: " + pname + " ");
        plan.setGravity(Gravity.CENTER);
        plan.setAllCaps(true);
      //  Cursor da = db.GetSavePlanDays(db.GetPlanid(pname));
        String currentTimeString = DateFormat.getTimeInstance().toString();
        Calendar c = Calendar.getInstance();
        int seconds = c.getTime().getHours();
if(seconds>=2&&seconds<=10)
        ShowDAta(pname, "BREAKFAST", cday);
else if(seconds>=11&&seconds<=16)
        ShowDAta(pname, "LUNCH", cday);
else if(seconds>=17&&seconds<=19)
        ShowDAta(pname, "TEA TIME", cday);
else if(seconds>=20&&seconds<=24)
        ShowDAta(pname, "DINNER", cday);
        return root;
    }

    void  ShowDAta(String pname,String time,String day){
        DataBaseHelper db=new DataBaseHelper(getContext());

        tr = new TableRow(getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView companyTV = new TextView(getContext());
        companyTV.setText(Html.fromHtml("<u>"+time+"</u>"));
        companyTV.setTextColor(Color.WHITE);
        companyTV.setBackgroundColor(Color.RED);

        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(5, 5, 5, 0);
        tr.addView(companyTV);
        companyTV = new TextView(getContext());
        companyTV.setText("");
        companyTV.setTextColor(Color.WHITE);
        companyTV.setBackgroundColor(Color.RED);

        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(5, 5, 5, 0);
        tr.addView(companyTV);
        companyTV = new TextView(getContext());
        companyTV.setText("");
        companyTV.setTextColor(Color.WHITE);
        companyTV.setBackgroundColor(Color.RED);

        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(5, 5, 5, 0);
        tr.addView(companyTV);
        t.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr = new TableRow(getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        companyTV = new TextView(getContext());

        companyTV.setText(Html.fromHtml("<u>Item Name</u>"));
        //companyTV.
        companyTV.setTextColor(Color.parseColor("#285300"));
        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(5, 5, 5, 5);
        tr.addView(companyTV);  // Adding textView to tablerow.

        /** Creating another textview **/
        valueTV = new TextView(getContext());
        valueTV.setText(Html.fromHtml("<u>Calories</u>"));
        valueTV.setTextColor(Color.parseColor("#285300"));
        valueTV.setPadding(5, 5, 5, 5);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV);

        valueTV = new TextView(getContext());
        valueTV.setText(Html.fromHtml("<u>Quantity</u>"));
        valueTV.setTextColor(Color.parseColor("#285300"));
        valueTV.setPadding(5, 5, 5, 5);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV);
        t.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        Cursor br= db.GetSavePlanDetails(db.GetPlanid(pname),time,day);
        br.moveToFirst();
        int tcal=0;
        while (!br.isAfterLast()){
            tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            companyTV = new TextView(getContext());
            companyTV.setText(br.getString(3));
            companyTV.setTextColor(Color.RED);
            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            companyTV.setPadding(5, 5, 5, 5);
            tr.addView(companyTV);  // Adding textView to tablerow.

            /** Creating another textview **/
            valueTV = new TextView(getContext());
            valueTV.setText(br.getString(4));
            valueTV.setTextColor(Color.RED);

            valueTV.setPadding(5, 5, 5, 5);
            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(valueTV);

            valueTV = new TextView(getContext());
            valueTV.setText(br.getString(5));
            valueTV.setTextColor(Color.RED);
            valueTV.setPadding(5, 5, 5, 5);
            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(valueTV);
            t.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            tcal+=(br.getInt(5)*br.getInt(4));
            br.moveToNext();
        }
        tr = new TableRow(getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        valueTV = new TextView(getContext());
        valueTV.setText("Total "+time+" Calories: "+tcal);
        valueTV.setTextColor(Color.BLUE);


        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV);

        t.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        return;
    }

}
