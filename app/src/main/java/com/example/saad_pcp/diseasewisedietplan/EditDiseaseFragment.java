package com.example.saad_pcp.diseasewisedietplan;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.saad_pcp.diseasewisedietplan.widget.FoodMultiSelector;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditDiseaseFragment extends Fragment implements
    FoodMultiSelector.FoodMultiSpinnerListener{

        @Override
        public void onItemsSelected(boolean[] selected) {

        }

        public EditDiseaseFragment() {
            // Required empty public constructor
        }
        private ArrayList<FoodProduct> getProductos(String s){
//String st={"AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","",""};
            DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());

            ArrayList<FoodProduct> arrayList = dataBaseHelper.get_FoodWithDisease(s);

            Cursor res=  dataBaseHelper.getFood_Disease(s);
            res.moveToFirst();
            while (!res.isAfterLast()){
                int fid= res.getInt(0);
                for (FoodProduct sFood:arrayList
                        ) {
                    if (sFood.getId() == fid) {
                        sFood.setChecked(true);
                        sFood.setStatus("Disabled");

                    }
                }
                res.moveToNext();

            }



            return arrayList;
        }

        Spinner Dis;
        ListView FoodList;
        EditText Dname;
        Button edDis;
        DataBaseHelper db;
        ArrayList<FoodProduct> fl;
        private FoodProducts adapter;
        ArrayAdapter<String> ladapter;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View root= inflater.inflate(R.layout.fragment_edit_disease, container, false);
            Dis=(Spinner)root.findViewById(R.id.EditDiseases);
            edDis=(Button)root.findViewById(R.id.Edit_DiseaseData);
            edDis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db=new DataBaseHelper(getContext());
                    int did=db.getDisId(Dis.getSelectedItem().toString());
                    db.Delete("Food_Disease","FDisease_ID",did+"");
                    //  db.Delete("Delete from Food_Disease where FDisease_ID="+did);

                    for (FoodProduct sele : fl) {
                        if (sele.isChecked()) {
                            DataBaseHelper db = new DataBaseHelper(getContext());
                            int fid=sele.getId();
                            db.insertFood_Disease(fid,did);

                        }
                    }
                    if(Dname.getText().toString()!="") {
                        db.Update("Disease", "Disease_Name", Dname.getText().toString(), "id", did + "");
                    }
                    // db.Update("Update Disease set Disease_Name='"+Dname.getText()+"' where id="+did);

                }
            });
            FoodList=(ListView)root.findViewById(R.id.EditFoodData);
            Dname=(EditText)root.findViewById(R.id.EditDiseaseName);
            db=new DataBaseHelper(getContext());
            ArrayList<String> disea= db.getAllDiseases();
            ladapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, disea);
            Dis.setAdapter(ladapter);
            Dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String c= adapterView.getItemAtPosition(i).toString();
                    fl=getProductos(c);
                    adapter= new FoodProducts(getContext(),0,fl);
                    FoodList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            FoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final  FoodProduct dataModel = (FoodProduct) adapterView.getItemAtPosition(i);

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

            return root;
        }

}
