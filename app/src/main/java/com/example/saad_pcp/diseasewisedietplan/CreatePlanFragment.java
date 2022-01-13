package com.example.saad_pcp.diseasewisedietplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePlanFragment extends Fragment {

    public EditText TotalCal;
    public EditText TotalDays,planName;
    public TextView Diseases;
    Button btnNext;



    public CreatePlanFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_plan, container, false);
        TotalCal=(EditText)rootView.findViewById(R.id.TxtCalory);
        TotalDays=(EditText)rootView.findViewById(R.id.TxtDays);
        Diseases=(TextView) rootView.findViewById(R.id.SelDisease);
        btnNext=(Button)rootView.findViewById(R.id.NextDisease);
        planName=(EditText)rootView.findViewById(R.id.PlanName);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String Cals = TotalCal.getEditableText().toString();
                String days = TotalDays.getEditableText().toString();
                String pname=planName.getEditableText().toString();
                DataBaseHelper fb=new DataBaseHelper(getContext());
                fb.InsertPlan(pname,days,Cals,"Enabled");


                CreatePlanFragment obj = (CreatePlanFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.rel_lyout_for_frags);
                getActivity().getSupportFragmentManager().beginTransaction().replace(
                        R.id.rel_lyout_for_frags,
                        new CreatePlan2Fragment()
                ).commit();
            }
        });

        DataBaseHelper db=new DataBaseHelper(getContext());

        String diseases=  db.getUserDisease();
        if(diseases.equals(""))
            Diseases.setText("You can Set Your Disease from Settings.");
        else
            Diseases.setText(diseases );


        return rootView;
    }


}
