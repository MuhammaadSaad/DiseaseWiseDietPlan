package com.example.saad_pcp.diseasewisedietplan;

import android.content.Context;

/**
 * Created by Saad-PCP on 3/30/2017.
 */
public class Disease {
    public String DiseaseName;
   //private String DiseaseID;
    public int mId;


    public int getmId()
    {
        return  mId;
    }
    public String getName()
    {
        return  DiseaseName;
    }


    public void setmId(int value)
    {
        mId = value;
    }
    public void setmName(String value)
    {
        DiseaseName = value;
    }
public void InsertD(Context c,int id, String name){

   DataBaseHelper dataBaseHelper=new DataBaseHelper(c);

    dataBaseHelper.insert_Disease(id,name);
    dataBaseHelper.close();
}
    /*public ArrayList<String> GetDiseases(Context c){
       DataBaseHelper dataBaseHelper=new DataBaseHelper(c);
        dataBaseHelper.insert_Disease("Diabbites");
    ArrayList<String> arrayList=new ;//= dataBaseHelper.getAllDiseases();
        dataBaseHelper.close();
        return  arrayList;
    }*/
}
