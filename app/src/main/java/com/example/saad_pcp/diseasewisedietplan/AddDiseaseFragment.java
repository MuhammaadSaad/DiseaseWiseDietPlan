package com.example.saad_pcp.diseasewisedietplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.saad_pcp.diseasewisedietplan.widget.FoodMultiSelector;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDiseaseFragment extends Fragment implements
        FoodMultiSelector.FoodMultiSpinnerListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    FoodMultiSelector foodList;
    @Override
    public void onItemsSelected(boolean[] selected) {

    }


    private ArrayList<FoodProduct> getProductos(){
//String st={"AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","",""};
        DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());

        ArrayList<FoodProduct> arrayList = dataBaseHelper.get_Food();




        return arrayList;
    }

    public AddDiseaseFragment() {
        // Required empty public constructor
    }
    Button AddDiseas;
    EditText DisName;
    private FoodProducts adapter;
    ListView FoodListView;

    private ArrayList<FoodProduct> dataFood;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_disease, container, false);


        FoodListView=(ListView) rootView.findViewById(R.id.FoodData);
        dataFood=getProductos();

        adapter= new FoodProducts(getContext(),0,dataFood);
        FoodListView.setAdapter(adapter);
        FoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final  FoodProduct dataModel = (FoodProduct) parent.getItemAtPosition(position);

                if (dataModel.isChecked()) {
                    dataModel.setChecked(false);
                    dataModel.setStatus("Enabled");
                }
                else {
                    dataModel.setChecked(true);
                    dataModel.setStatus("Disabled");
                }
                adapter.notifyDataSetChanged();

            }
        });
        // foodList.setItems(getProductos(),"Nothing Selected",this);
        AddDiseas=(Button) rootView.findViewById(R.id.Add_DiseaseData);
        DisName=(EditText) rootView.findViewById(R.id.EntryDiseaseName);
        AddDiseas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dbs=new DataBaseHelper(getContext());
                dbs=new DataBaseHelper(getContext());
                String res=  dbs.insert_Disease(dbs.getDisId(),DisName.getText().toString());
                if(res=="Inserted"){
                    InsertFoodSelect();

                }

            }
        });

        return rootView;
    }


    public void InsertFoodSelect() {
        // obj = (CreatePlanFragment) getSupportFragmentManager().findFragmentById(R.id.rel_lyout_for_frags);
        ListView multiSpinnerSelecter =FoodListView;

        String txt = "";


        for (FoodProduct sele:dataFood) {
            if(sele.isChecked()) {
                DataBaseHelper dbs = new DataBaseHelper(getContext());
                int id = 0;
                id = dbs.getDisId(DisName.getText().toString());


                String res = dbs.insertFood_Disease(sele.getId(), id);
                txt +=  sele.getName() + " \n";
            }
        }
        Toast.makeText(getContext(), txt, Toast.LENGTH_LONG).show();

    }


}
