package com.example.saad_pcp.diseasewisedietplan;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavePlanFragment extends Fragment {


    public SavePlanFragment() {
        // Required empty public constructor
    }
    ListView plans;
    DataBaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db=new DataBaseHelper(getContext());
        View root= inflater.inflate(R.layout.fragment_save_plan, container, false);
        plans=(ListView)root.findViewById(R.id.Plans);

        ArrayList<String> your_array_list = db.getPlans();

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext()
                ,
                android.R.layout.simple_list_item_1,
                your_array_list );

        plans.setAdapter(arrayAdapter);
        plans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final  String cid=adapterView.getItemAtPosition(i).toString();
                new AlertDialog.Builder(getContext())
                        .setTitle(cid)
                        .setMessage("Chose Option")
                        .setCancelable(false)
                        .setNegativeButton("Activate", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DataBaseHelper fb=new DataBaseHelper(getContext());
                                        fb.ActivatePlan(cid);
                                        Toast.makeText(getContext(),cid +" Activated Successfully",Toast.LENGTH_LONG).show();
                                    }
                                }
                        ).setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataBaseHelper dbs=new DataBaseHelper(getContext());
                                dbs.DeletePlan(cid);
                                Toast.makeText(getContext(),cid +" Deleted Successfully",Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(
                                        R.id.rel_lyout_for_frags,
                                        new SavePlanFragment()
                                ).commit();
                            }
                        })
                        .setNeutralButton("View", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Whatever...
                                        Fragment fr=new BlankFragment();
                                        FragmentManager fm=getFragmentManager();
                                        FragmentTransaction ft=fm.beginTransaction();
                                        Bundle args = new Bundle();
                                        args.putString("Plan Name", cid);
                                        fr.setArguments(args);
                                        ft.replace(R.id.rel_lyout_for_frags, fr);
                                        ft.commit();
                                    }
                                }
                        ).show();
            }
        });
        return root;
    }


}
