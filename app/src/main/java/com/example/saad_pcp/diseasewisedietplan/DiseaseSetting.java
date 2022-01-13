package com.example.saad_pcp.diseasewisedietplan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.saad_pcp.diseasewisedietplan.widget.MultiSelector;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiseaseSetting extends Fragment implements
        MultiSelector.MultiSpinnerListener {

    public Button SetDis;
    @Override
    public void onItemsSelected(boolean[] selected) {

    }

    DataBaseHelper db;

    public DiseaseSetting() {

        //db=new DBAgent(getApplicationContext());
    }

    ArrayAdapter<String> adapter;
    MultiSelector multiSpinnerSelect;
    private boolean getDis(String name){
        String dsi= db.getUserDisease();
        boolean sas=false;
        String[] sa=dsi.split(",",0);
        for (String s:sa) {
            if(!name.equals(s)) {
                sas= false;
            }else{
                sas= true;
            }
        }
        return sas;
    }
    private ArrayList<Product> getProductos(ArrayList<String> arr) {
//String st={"AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","",""};
        ArrayList<Product> arrayList = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            final Product producto = new Product();
            producto.setId(i);
            producto.setNombre(arr.get(i).toString());
            //producto.setPresentacion("gr2" + i);
            if(getDis(arr.get(i).toString()))
                producto.setChecked(true);
            arrayList.add(producto);
        }


        return arrayList;
    }
    public void getSelected() {
        MultiSelector multiSpinnerSelecter = multiSpinnerSelect;
        ArrayList<Product> sele = multiSpinnerSelecter != null ? multiSpinnerSelecter.getSelectedItems() : null;
        String txt = "";
        DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());
        dataBaseHelper.Delete("User","Id","admin");
        for (Product producto : sele) {
            txt +=  producto.getName() + " \n";


            dataBaseHelper.InsertUserDisease("admin",producto.getId(), producto.getName());

        }
        Toast.makeText(getContext(), txt, Toast.LENGTH_LONG).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_disease_setting, container, false);

        SetDis=(Button)rootView.findViewById(R.id.SetDisease);
        SetDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelected();

            }
        });
        multiSpinnerSelect = (MultiSelector) rootView.findViewById(R.id.DiseaseSpinner);


        ArrayList<String> arrayList;//=new ArrayList<String>();

        //db=new DataBaseHelper(getApplicationContext());


        db = new DataBaseHelper(this.getContext());
        arrayList = db.getAllDiseases();

        multiSpinnerSelect.setItems(getProductos(arrayList), "All Selected", this);




        return rootView;
    }

}
