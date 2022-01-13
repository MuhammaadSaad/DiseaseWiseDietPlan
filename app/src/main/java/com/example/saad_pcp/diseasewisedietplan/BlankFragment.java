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


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {



    public BlankFragment() {
        // Required empty public constructor
    }
    TableLayout t;
    TableRow tr;
    TextView TimingTV,valueTV,plan,cal,TCal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_blank, container, false);
        plan=(TextView)root.findViewById(R.id.ShowPlName);
        TCal=(TextView)root.findViewById(R.id.ShowCal);
        t=(TableLayout)root.findViewById(R.id.maintable);

        String strtext = getArguments().getString("Plan Name");
        DataBaseHelper db=new DataBaseHelper(getContext());



        int tcal=db.GetPlanCal(db.GetPlanid(strtext),strtext);
        TCal.setText("Total Calories: "+ tcal+" ");
        TCal.setGravity(Gravity.CENTER);
        TCal.setAllCaps(true);

        plan.setText("Plan Name: "+strtext+" ");
        plan.setGravity(Gravity.CENTER);
        plan.setAllCaps(true);
        Cursor da= db.GetSavePlanDays(db.GetPlanid(strtext));
        da.moveToFirst();
        while (!da.isAfterLast()) {

            tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            TextView companyTV = new TextView(getContext());
            companyTV.setText(Html.fromHtml("<b>"+da.getString(0)+"</b>"));
            companyTV.setBackgroundColor(Color.GREEN);
            companyTV.setTextColor(Color.WHITE);
            companyTV.setGravity(Gravity.CENTER_HORIZONTAL);
            companyTV.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            companyTV.setPadding(5, 5, 5, 0);
            tr.addView(companyTV);
            t.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            ShowDAta(strtext, "BREAKFAST", da.getString(0));

            ShowDAta(strtext, "LUNCH", da.getString(0));
            ShowDAta(strtext, "TEA TIME", da.getString(0));
            ShowDAta(strtext, "DINNER", da.getString(0));
            da.moveToNext();
        }
        //Toast.makeText(getContext(),strtext,Toast.LENGTH_LONG).show();

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
