package com.example.saad_pcp.diseasewisedietplan;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saad_pcp.diseasewisedietplan.widget.FoodMultiSelector;

import java.util.ArrayList;


public class CreatePlan2Fragment extends Fragment implements
        FoodMultiSelector.FoodMultiSpinnerListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Spinner Cate,DaySpinner;
    LinearLayout food;
    @Override
    public void onItemsSelected(boolean[] selected) {

    }
    private ArrayList<FoodProduct> getProductos(){
//String st={"AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","",""};
        DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());

        String c=  Cate.getSelectedItem().toString();
        ArrayList<FoodProduct> arrayList = dataBaseHelper.get_Foods_dis(c,dataBaseHelper.getUserDisease());
        ArrayList<FoodProduct> res=  dataBaseHelper.get_Foods(c);

        for (FoodProduct Foods:res ){
            for (FoodProduct HFood : arrayList) {
                if (HFood.getId() == Foods.getId()) {
                    HFood.setChecked(true);
                    Foods.setStatus(HFood.getStatus());
                    break;
                }
            }
        }
        return res;
        //return  arrayList;
    }
    public CreatePlan2Fragment() {
        // Required empty public constructor
    }


    private ArrayAdapter<String> ladapter;
    private FoodProducts adapter;
    private ArrayList<String> LarrayList;
    private ArrayList<FoodProduct> dataFood;
    ListView FoodListView;
    Switch SwDay;
    TextView Tcal,Tdays,TPlname;
    private Button addMeal,Finish;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_create_plan2, container, false);
        SwDay=(Switch)rootView.findViewById(R.id.DaySwitch);
        Tcal=(TextView)rootView.findViewById(R.id.txtTotCal);
        Tdays=(TextView)rootView.findViewById(R.id.TxtDayst);
        TPlname=(TextView)rootView.findViewById(R.id.TxtPlName);
        food=(LinearLayout)rootView.findViewById(R.id.foodLayout);
        DaySpinner=(Spinner)rootView.findViewById(R.id.DaySpinner);

        SwDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    DaySpinner.setSelection(DaySpinner.getAdapter().getCount()-1);
                }
                else{

                    DaySpinner.setSelection(0);
                }
            }
        });
        Cate=(Spinner)rootView.findViewById(R.id.PlanFoodCat);
        Cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String c= adapterView.getItemAtPosition(i).toString();
                dataFood=getProductos();
                adapter= new FoodProducts(getContext(),0,dataFood);
                FoodListView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final Spinner Luncht = (Spinner) rootView.findViewById(R.id.Lunch);
        LarrayList = new ArrayList<String>();
        LarrayList.add("BREAKFAST");
        LarrayList.add("LUNCH");
        LarrayList.add("TEA TIME");
        LarrayList.add("DINNER");
        ladapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, LarrayList);
        Luncht.setAdapter(ladapter);

         /*   foodList=(FoodMultiSelector) rootView.findViewById(R.id.FoodDatas);
            foodList.setItems(getProductos(),"Nothing Selected",this);*/
        FoodListView =(ListView)rootView.findViewById(R.id.FoodlistView);
        dataFood=getProductos();
        adapter=                    new FoodProducts(getContext(),0,dataFood);
        FoodListView.setAdapter(adapter);
        FoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final  FoodProduct dataModel = (FoodProduct) parent.getItemAtPosition(position);
                if(dataModel.getStatus().equals("Enabled"))
                {
                    if (dataModel.isChecked()) {
                        dataModel.setChecked(false);
                        dataModel.setQuantity(1);
                    } else {
                        dataModel.setChecked(true);


                        adapter.notifyDataSetChanged();
                        AlertDialog.Builder mBuilder;
                        final AlertDialog dialog;
                        LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mView = inflater1.inflate(R.layout.get_quantity, null);

                        final EditText et_new_pwd = (EditText) mView.findViewById(R.id.Foodqty);
                        mBuilder = new AlertDialog.Builder(getContext());
                        mBuilder.setView(mView);
                        dialog = mBuilder.create();
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        dialog.findViewById(R.id.setQty).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!et_new_pwd.getText().toString().equals("")) {
                                    dataModel.setQuantity(Integer.parseInt(et_new_pwd.getText().toString()));
                                    dialog.hide();;
                                }else
                                {
                                    et_new_pwd.setBackgroundColor(Color.YELLOW);
                                }

                              /*  Snackbar.make(view, dataModel.getName() + "\n" + dataModel.getQuantity() + " Data " + dataModel.getCalories(), Snackbar.LENGTH_LONG)
                                        .setAction("No action", null).show();*/

                            }
                        });


                    }
                   /* Snackbar.make(view, dataModel.getName() + "\n" + dataModel.getQuantity() + " Data " + dataModel.getCalories(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();*/
                }
                else{
                    DataBaseHelper db=new DataBaseHelper(getContext());
                    String Diseases = db.getFoodDisease(dataModel.getId());
                    new AlertDialog.Builder(getContext())
                            .setTitle(dataModel.getName()+" Not Suggested to You")
                            .setMessage("Because You are suffering from "+Diseases)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Whatever...

                                }
                            }).show();
                }
            }
        });
        Finish=(Button)rootView.findViewById(R.id.Finish);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String cid = TPlname.getText().toString();
                    DataBaseHelper db = new DataBaseHelper(getContext());
                    db.ActivatePlan(cid);
                    Fragment fr = new BlankFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("Plan Name", cid);
                    fr.setArguments(args);
                    ft.replace(R.id.rel_lyout_for_frags, fr);
                    ft.commit();
                }catch (Exception s){
                    Toast.makeText(getContext(),"No Item In Plan",Toast.LENGTH_SHORT).show();
                    s.printStackTrace();
                }
            }
        });
        addMeal =(Button)rootView.findViewById(R.id.addMeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rs="";

                String pnam=TPlname.getText().toString();
                if(checkQuantity(pnam)) {
                    for (FoodProduct sele : dataFood) {
                        if (sele.isChecked()) {
                            DataBaseHelper db = new DataBaseHelper(getContext());
                            pnam=TPlname.getText().toString();
                            String d=DaySpinner.getSelectedItem().toString();

                            rs=rs+"/n"+ db.InsertPlanData(db.GetPlanid(pnam), Luncht.getSelectedItem().toString(), sele.getName(), Cate.getSelectedItem().toString(), sele.getCalories(), sele.getQuantity(),d);

                        }
                    }
                    Toast.makeText(getContext(),rs,Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(getContext(),"Selected Calories Excede Limits",Toast.LENGTH_LONG).show();
            }
        });

        DataBaseHelper db=new DataBaseHelper(getContext());
        Cursor cs=db.getPlanSave();
        cs.moveToFirst();
        int d=0;
        while (!cs.isAfterLast())
        {
            TPlname.setText(  cs.getString(1));
            Tcal.setText("Total Calories: "+cs.getInt(3));

            d=cs.getInt(2);
            cs.moveToNext();
        }
        ArrayList<String>  LdayList = new ArrayList<String>();


        for (int i=1;i<=d;i++){
            LdayList.add("Day "+i);
        }
        LdayList.add("ALL DAYS");
        ArrayAdapter<String>  ldayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, LdayList);
        DaySpinner.setAdapter(ldayadapter);
        return rootView;
    }
    public boolean  checkQuantity( String pnam){

        int tot=0;
        for (FoodProduct sele:dataFood) {
            if(sele.isChecked()){

                tot+= sele.getCalories()*(double) sele.getQuantity();

                //db.InsertPlanData(1,Luncht.getSelectedItem().toString(),sele.getName(),Cate.getSelectedItem().toString(),sele.getCalories(),sele.getQuantity());
                // r+=sele.getName()+", "+sele.getId()+", qty="+sele.getQuantity();
            }
        }
        DataBaseHelper db=new DataBaseHelper(getContext());




        int tCal=db.GetPlanCal(db.GetPlanid(pnam),pnam);
        int SCal=db.GetSavePlanCal(db.GetPlanid(pnam),pnam,DaySpinner.getSelectedItem().toString());
        if(tot<=(tCal-SCal)){
            return true;
        }else {
            return false;
        }


    }


}
