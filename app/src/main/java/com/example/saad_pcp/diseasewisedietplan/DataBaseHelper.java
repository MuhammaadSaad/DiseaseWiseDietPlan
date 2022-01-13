package com.example.saad_pcp.diseasewisedietplan;

/**
 * Created by Saad-PCP on 3/25/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DietPlan.db";


    public static final String TABLE_NAME_Food_disease = "Food_disease";
    public static final String COLUMN_FDisease_ID = "FDisease_ID";//0
    public static final String COLUMN_FFood_id = "FFood_ID";//1
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_NAME_SavePlan = "Save_Plan";

    public static final String COLUMN_PID = "PlanID";//0
    public static final String COLUMN_PNAME = "Plan_NAME";//1
    public static final String COLUMN_Pdays = "Plan_Days";//2
    public static final String COLUMN_TCal="Total_calories";//3
    public static final String COLUMN_pStatus="Status";//4
    public static final String COLUMN_PlanActivationDate = "ActivationDate";//5



    public static final String TABLE_NAME_PlanDetails = "Plan_Details";

    public static final String COLUMN_PlID = "PlanID";//0
    public static final String COLUMN_time = "Time";//1
    public static final String COLUMN_FoodCAt = "Food_Category";//2
    public static final String COLUMN_FCalories = "Food_Calories";//3
    public static final String COLUMN_FItem = "Food_Item";//3
    public static final String COLUMN_Fquantity = "Food_Quantity";//4
    public static final String COLUMN_PDay = "Day";//4



    public static final String TABLE_NAME_Food = "Food";

    public static final String COLUMN_FoodID = "FoodID";//0
    public static final String COLUMN_NAME = "NAME";//1
    public static final String COLUMN_Calories = "Calories";//2
    public static final String COLUMN_Unit = "Unit";//3
    public static final String COLUMN_Category = "Category";//4
    public static final String COLUMN_FImage = "Image";//4

    public static final String TABLE_NAME_Disease = "Disease";
    public static final String COLUMN_DiseaseID = "Id";//0
    public static final String COLUMN_Disease_Name = "Disease_Name";//1

    public static final String TABLE_NAME_User = "User";
    public static final String COLUMN_UserID = "Id";//0
    public static final String COLUMN_UDisease_Name = "Disease_Name";//1
    public static final String COLUMN_UDisease_Id = "DiseaseId";//1

    public static final String Table_Ac_Details="Activation_Details";
    public static final String COLUMN_Planiid = "PlanID";//1
    public static final String COLUMN_Planname="Pname";
    public static final String COLUMN_Date = "Date";//1
    public static final String COLUMN_PlanDay = "Plan_Day";//1


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table " + TABLE_NAME_Food + " (" + COLUMN_FoodID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_Calories + " TEXT," + COLUMN_Unit + " TEXT," + COLUMN_Category + " TEXT," + COLUMN_FImage + " TEXT);");
        db.execSQL("create table " + TABLE_NAME_Disease + " ( " + COLUMN_DiseaseID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_Disease_Name + " TEXT);");
      db.execSQL("create table "+TABLE_NAME_Food_disease+"(  "+COLUMN_FDisease_ID +" Integer , " +COLUMN_FFood_id+ " Integer,PRIMARY KEY('FDisease_ID','FFood_ID'), FOREIGN KEY ("+COLUMN_FDisease_ID+") REFERENCES "+TABLE_NAME_Disease +"("+COLUMN_DiseaseID+"),  FOREIGN KEY ("+COLUMN_FFood_id+") REFERENCES "+TABLE_NAME_Food +"("+COLUMN_FoodID+"));" );
        db.execSQL("create table "+ TABLE_NAME_User+"( "+COLUMN_UserID+" Text , "+ COLUMN_UDisease_Id +" Integer, "+COLUMN_UDisease_Name+" Text,PRIMARY KEY("+COLUMN_UDisease_Id+","+COLUMN_UserID+"), FOREIGN KEY ("+COLUMN_UDisease_Id+") REFERENCES "+TABLE_NAME_Disease +"("+COLUMN_DiseaseID+") );");
        db.execSQL("create table "+Table_Ac_Details+"( "+COLUMN_Date+" Text PRIMARY KEY,"+COLUMN_Planname+" Text, "+COLUMN_PlanDay+" Text ,"+COLUMN_Planiid+" Integer, FOREIGN KEY ("+COLUMN_Planiid+") REFERENCES "+TABLE_NAME_SavePlan +");");
        db.execSQL("create table "+TABLE_NAME_SavePlan +"("+COLUMN_PID+ " INTEGER PRIMARY KEY," +COLUMN_PNAME+" TEXT," +COLUMN_Pdays+" INTEGER," +COLUMN_TCal+" INTEGER," +COLUMN_pStatus+" TEXT  , "+COLUMN_PlanActivationDate+" Text);"  );
        db.execSQL("create table " +TABLE_NAME_PlanDetails +"("+COLUMN_PlID+" Integer , " +COLUMN_time+ " TEXT," +COLUMN_FoodCAt+ " TEXT,"+COLUMN_FItem+ " TEXT," +COLUMN_FCalories+ " Integer," +COLUMN_Fquantity + " INTEGER,"+COLUMN_PDay+" Text, FOREIGN KEY ("+COLUMN_PlID+") REFERENCES "+TABLE_NAME_SavePlan +"("+COLUMN_PlID+"));"  );
    }
    public int GetSavePlanCal(int Pid,String Pname,String day){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from Plan_Details where PlanID="+Pid +" and Day='"+day+"';",null);
        c.moveToFirst();
        int cs,t=0;
        while (!c.isAfterLast()){

            cs=c.getInt(4)*c.getInt(5);
            t+=cs;
c.moveToNext();
        }
        return t;
    }
    public Cursor GetSavePlanDays(int Pid){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select Distinct Day from Plan_Details where PlanID='"+Pid +"' order by Day",null);

        return c;
    }

    public Cursor GetUserAcPlanDetails(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+Table_Ac_Details,null);

        return c;
    }
    public Cursor GetSavePlanDetails(int Pid,String time,String day){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from Plan_Details where PlanID="+Pid+" and Time='"+time+"' and Day='"+day+"'",null);

        return c;
    }
    void insertUserAcPlan(String pname,String pday,String date){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_Planiid,GetPlanid(pname));
        values.put(COLUMN_Planname,pname);

        values.put(COLUMN_Date,date);
        values.put(COLUMN_PlanDay,pday);//= "Plan_Name";//1

        db.insert(Table_Ac_Details,null,values);
    }
    void ActivatePlan(String pName){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_pStatus,"Disabled");
        db.update(TABLE_NAME_SavePlan,values,"1 = ?",
                new String[] { String.valueOf("1") });

        ContentValues value1s = new ContentValues();
        value1s.put(COLUMN_pStatus,"Enabled");

        GregorianCalendar gc = new GregorianCalendar();

        int yearat = gc.get(Calendar.YEAR);
        String yearstr = Integer.toString(yearat);
        int monthat = gc.get(Calendar.MONTH) + 1;
        String monthstr = Integer.toString(monthat);
        int dayat = gc.get(Calendar.DAY_OF_MONTH);
        String  daystr = Integer.toString(dayat);
        value1s.put(COLUMN_PlanActivationDate,daystr+"-"+monthstr+"-"+yearstr);
db.update(TABLE_NAME_SavePlan,value1s,"PlanID = ?",
        new String[] { String.valueOf(GetPlanid(pName)) });

        //Delete(Table_Ac_Details,"1","1");
        db.delete(Table_Ac_Details,null,null);

        Cursor cr= GetSavePlanDays(GetPlanid(pName));
        cr.moveToFirst();
        String fday="";int i=0;
        if(cr.getCount()>1) {
            while (!cr.isAfterLast()) {
                gc = new GregorianCalendar();

                gc.add(Calendar.DATE, i);
                i++;
                yearat = gc.get(Calendar.YEAR);
                yearstr = Integer.toString(yearat);
                monthat = gc.get(Calendar.MONTH) + 1;
                monthstr = Integer.toString(monthat);
                dayat = gc.get(Calendar.DAY_OF_MONTH);
                daystr = Integer.toString(dayat);

                fday = cr.getString(0);


                // values.put(COLUMN_PlanActivationDate,currentDateTimeString);
                insertUserAcPlan(pName, fday, daystr + "-" + monthstr + "-" + yearstr);

                cr.moveToNext();
            }
        }else{
          Cursor sc=  GetPlanDetails(GetPlanid(pName),pName);
            int Tdays=1;
            sc.moveToFirst();
            i=0;
            while (!sc.isAfterLast()) {
                Tdays = sc.getInt(2);
                sc.moveToNext();
            }
            while (i<Tdays){

                gc = new GregorianCalendar();

                gc.add(Calendar.DATE, i);
                i++;
                yearat = gc.get(Calendar.YEAR);
                yearstr = Integer.toString(yearat);
                monthat = gc.get(Calendar.MONTH) + 1;
                monthstr = Integer.toString(monthat);
                dayat = gc.get(Calendar.DAY_OF_MONTH);
                daystr = Integer.toString(dayat);


                fday = cr.getString(0);


                // values.put(COLUMN_PlanActivationDate,currentDateTimeString);
                insertUserAcPlan(pName, fday, daystr + "-" + monthstr + "-" + yearstr);
            }




        }
       // insertUserPlan("admin",GetPlanid(pName),pName,fday,fday,lday);
        /*ContentValues value12s = new ContentValues();
        value1ws.put(COLUMN_UserIID,"admin");
        db.update(TABLE_NAME_SavePlan,value1ws,"PlanID = ?",
                new String[] { String.valueOf(GetPlanid(pName)) });*/



        //db.rawQuery("update Save_Plan set Status='Disabled'",null);
       // db.rawQuery("update Save_Plani set Status='Enabled' where PlanID="+GetPlanid(pName),null);
        db.close();
    }
    void Update(String TAble,String col,String Value,String Ucol,String UV){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col,Value);
        db.update(TAble,values,Ucol+" = ?",
                new String[] { String.valueOf(UV) });

       db.close();
    }
    void Delete(String TAble,String col,String Value){
        SQLiteDatabase db=this.getWritableDatabase();
        //db.rawQuery("update Save_Plan set Status='Disabled'",null);
        db.delete(TAble,col + " = ?",
                new String[] { String.valueOf(Value) });

        //db.rawQuery("Delete from Plan_Details where PlanID="+GetPlanid(pName),null);
        //db.rawQuery("Delete from Save_Plan where PlanID="+GetPlanid(pName),null);
        db.close();
    }
    void DeletePlan(String pName){
        SQLiteDatabase db=this.getWritableDatabase();
        //db.rawQuery("update Save_Plan set Status='Disabled'",null);
        db.delete(TABLE_NAME_PlanDetails,COLUMN_PlID + " = ?",
                new String[] { String.valueOf(GetPlanid(pName)) });
        db.delete(TABLE_NAME_SavePlan,COLUMN_PlID + " = ?",
                new String[] { String.valueOf(GetPlanid(pName)) });

        //db.rawQuery("Delete from Plan_Details where PlanID="+GetPlanid(pName),null);
        //db.rawQuery("Delete from Save_Plan where PlanID="+GetPlanid(pName),null);
        db.close();
    }
    public Cursor getPlanSave(){
        ArrayList<String> sa=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from Save_Plan",null);

        return cur;
    }
    public ArrayList<String> getPlans(){
        ArrayList<String> sa=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from Save_Plan",null);
        cur.moveToFirst();
        while (!cur.isAfterLast()){
            sa.add(cur.getString(1));
            cur.moveToNext();

        }
        return sa;
    }
public int InsertPlan(String Pname,String days,String tcal,String Status){



        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(COLUMN_PNAME, Pname);
        contentValues.put(COLUMN_Pdays, days);
        contentValues.put(COLUMN_TCal, tcal);
        contentValues.put(COLUMN_pStatus, Status);
    GregorianCalendar gc = new GregorianCalendar();

    int yearat = gc.get(Calendar.YEAR);
    String yearstr = Integer.toString(yearat);
    int monthat = gc.get(Calendar.MONTH) + 1;
    String monthstr = Integer.toString(monthat);
    int dayat = gc.get(Calendar.DAY_OF_MONTH);
    String  daystr = Integer.toString(dayat);

    contentValues.put(COLUMN_PlanActivationDate,daystr+"-"+monthstr+"-"+yearstr);
    db.insert(TABLE_NAME_SavePlan, null, contentValues);

    /*}else{
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update Save_Plan set Plan_Days=" +days+ ",Plan_NAME='" +Pname+
                "',Total_calories="+tcal+ " where PlanID="+Pid);
    }*/
    int Pid=GetPlanid(Pname);
    return Pid;

}
 public  int checkItem(int Pid,String time,String item,String cat,double cal,int qty,String day){
     SQLiteDatabase db = this.getWritableDatabase();

     Cursor cr=db.rawQuery("Select * from "+TABLE_NAME_PlanDetails+" where "+COLUMN_PlID+"='"+Pid+"' and "+COLUMN_time+"='"+time+"' and "+COLUMN_PDay+"='"+day+"';",null);
     cr.moveToFirst();
     int qtys=0;
     while (!cr.isAfterLast()){
         if(cr.getString(3).equals(item)){
             qtys=cr.getInt(5);
         }
         cr.moveToNext();
     }
     return qtys;
    }
    public String InsertPlanData(int Pid,String time,String item,String cat,double cal,int qty,String day){
        long res =-1;
          ContentValues contentValues = new ContentValues();
            SQLiteDatabase db = this.getWritableDatabase();

            contentValues.put(COLUMN_PlID, Pid);
            contentValues.put(COLUMN_time, time);
            contentValues.put(COLUMN_FoodCAt, cat);
            contentValues.put(COLUMN_FItem, item);
            contentValues.put(COLUMN_FCalories, cal);
            contentValues.put(COLUMN_Fquantity, qty);
            contentValues.put(COLUMN_PDay, day);
             res = db.insert(TABLE_NAME_PlanDetails, null, contentValues);



        if (res == -1)
            return  "Error in saving";
        else
            return item + " Inserted successfully";

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Food);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Disease);
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Food_disease);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_User);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SavePlan);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PlanDetails);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Ac_Details);


        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOSPITAL_INFO);
        onCreate(db);
    }

    public String getUserDisease(){
        String result="";

        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT  "+COLUMN_UDisease_Name+" FROM " + TABLE_NAME_User + ";", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                String dis = res.getString(0);
                // res.getString(res.getColumnIndex(COLUMN_Disease_Name));
                if(result.equals(""))
                    result=dis;
                else
                    result=result+",\n"+ dis;
                res.moveToNext();
            }
        }catch (Exception ex){
            return "error";
        }
        return result;
    }

    public String getFoodDisease(int foodid){
        String result="";

        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select Disease_Name from User where User.DiseaseId in (Select FDisease_ID from Food_disease where FFood_ID="+foodid+");", null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                String dis = res.getString(0);
                // res.getString(res.getColumnIndex(COLUMN_Disease_Name));
                result= dis+"\n"+result;
                res.moveToNext();
            }
        }catch (Exception ex){
            return "error";
        }
        return result;
    }

    public boolean checkUserDisease(String user,int Disease_id,String DisNam) {

        boolean flag=false;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from User",null);
        cur.moveToFirst();
        while (!cur.isAfterLast()){
            String u=cur.getString(0).toString();
            int i=cur.getInt(1);
            String D=cur.getString(2).toString();
            if(u.equalsIgnoreCase(user))
                if(i==Disease_id)
                    if(D.equalsIgnoreCase(DisNam))
                flag=true;
            cur.moveToNext();

        }
        return flag;

    }

    public String InsertUserDisease(String user,int Disease_id,String DisNam){
        String result;
        Cursor fur = null;
        try {
           boolean cur=checkUserDisease(user,Disease_id,DisNam);
int id=getDisId(DisNam);
            fur = getDisData(Disease_id);
            int j = fur.getCount();

            if (( j >= 0 )&&( !cur)) {


                ContentValues contentValues = new ContentValues();
                SQLiteDatabase db = this.getWritableDatabase();
                //contentValues.put(COLUMN_FoodID, id);
                contentValues.put(COLUMN_UserID, user);
                contentValues.put(COLUMN_UDisease_Id, id);
                contentValues.put(COLUMN_UDisease_Name, DisNam);



                long res = db.insert(TABLE_NAME_User, null, contentValues);
                if (res == -1)
                    result = "Error in saving";
                else
                    return user+" "+Disease_id + " Inserted successfully";
            } else {
                result = user+" "+Disease_id + " Already Inseted";
            }
        } catch (Exception ex) {
            result = ex.toString();
        }

        return result;

    }

    public Cursor getFood_Disease(String s)
    {
        Cursor cur = null;
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("Select FFood_id,FDisease_ID from Food_disease where FDisease_ID="+getDisId(s),null);


    }
    public void Delete(String q){
        SQLiteDatabase db=this.getWritableDatabase();
        db.rawQuery(q,null);
    }
    public void Update(String q){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            db.rawQuery(q, null);
        } catch (Exception ex) {

                ex.printStackTrace();
            }
    }

public String insertFood_Disease(int food_id,int Disease_id){
    String result;
    Cursor cur = null, fur = null;
    try {
        cur = getData(food_id);
        fur = getDisData(Disease_id);
        int j = fur.getCount();
        int i = cur.getCount();
        if (cur.getCount() > 0 && fur.getCount() > 0) {


            ContentValues contentValues = new ContentValues();
            SQLiteDatabase db = this.getWritableDatabase();
            //contentValues.put(COLUMN_FoodID, id);
            contentValues.put(COLUMN_FDisease_ID, Disease_id);
            contentValues.put(COLUMN_FFood_id, food_id);


            long res = db.insert(TABLE_NAME_Food_disease, null, contentValues);
            if (res == -1)
                result = "Error in saving";
            else
                return food_id+" "+Disease_id + " Inserted successfully";
        } else {
            result = food_id+" "+Disease_id + " Already Inseted";
        }
    } catch (Exception ex) {
        result = ex.toString();
    }

    return result;
}
    public String insertFood( String Name, String Cal, String Unit, String Cat,String img) {
        String result;
        Cursor cur = null, fur = null;
        try {

            fur = getFoodName(Name);
            int j = fur.getColumnCount();

            if ( fur.getCount() <= 0) {


                ContentValues contentValues = new ContentValues();
                SQLiteDatabase db = this.getWritableDatabase();
                //contentValues.put(COLUMN_FoodID, id);
                contentValues.put(COLUMN_NAME, Name);
                contentValues.put(COLUMN_Calories, Cal);
                contentValues.put(COLUMN_Unit, Unit);
                contentValues.put(COLUMN_Category, Cat);
                contentValues.put(COLUMN_FImage,img);


                long res = db.insert(TABLE_NAME_Food, null, contentValues);
                if (res == -1)
                    result = "Error in saving";
                else
                    return Name + " Inserted successfully";
            } else {
                result = Name + " Already Inseted";
            }
        } catch (Exception ex) {
            result = ex.toString();
            ex.printStackTrace();
        }

        return result;
    }


    public String insert_Disease(int id,String name) {
        String result = "";
    Cursor ds=getDiseaseName(name);
        if(ds.getCount()<=0){
            SQLiteDatabase db = getWritableDatabase();

            try {
                ContentValues values = new ContentValues();
                values.put(COLUMN_DiseaseID,id);
                values.put(COLUMN_Disease_Name, name);


                long res = db.insert(TABLE_NAME_Disease, null, values);
                result = "Inserted";
                db.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }else{
            result="Already inserted";
        }
        return result;
    }
    public Cursor getUserData(int id) {
        Cursor cur = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + TABLE_NAME_User + " where "+COLUMN_UserID+"='" + id + "' ", null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cur;
    }
    public Cursor getData(int id) {
        Cursor cur = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + TABLE_NAME_Food + " where FoodId='" + id + "' ", null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cur;
    }

    public int getDisId(String Nam) {
        Cursor cur = null;
int id=0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + TABLE_NAME_Disease + " where "+COLUMN_Disease_Name+"='" + Nam + "'; ", null);
            cur.moveToFirst();
while(!cur.isAfterLast()) {

    if (cur.getString(cur.getColumnIndex("Disease_Name")).equals(Nam)) {
        id = cur.getInt(cur.getColumnIndex("Id"));
    }
    cur.moveToNext();
}

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public int getDisId() {
        Cursor cur = null;
        int id=0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select max(Id)+1 from Disease; ", null);
            cur.moveToFirst();
            while(!cur.isAfterLast()) {


                    id = cur.getInt(0);

                cur.moveToNext();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public Cursor getDisData(int id) {
        Cursor cur = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + TABLE_NAME_Disease + " where "+COLUMN_DiseaseID+"='" + id + "' ", null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cur;
    }
    public Cursor getFoodName(String Name) {
        Cursor cur = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + TABLE_NAME_Food + " where Name='" + Name + "' ", null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cur;
    }

    public Cursor getDiseaseName(String Name) {
        Cursor cur =null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + TABLE_NAME_Disease + " where "+COLUMN_Disease_Name+"='" + Name + "' ;", null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cur;
    }

    public void reset_all_tables() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME_Food, null, null);
        db.delete(TABLE_NAME_Disease, null, null);
        //  db.delete(TABLE_NAME_HOSPITAL_INFO, null, null);

    }

/*
    public String update_profile(String email,String nic,String contact_no,String address,String pic,String blood_group) {
        String result = "";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_EMAIL, email);
            contentValues.put(COLUMN_NIC, nic);
            contentValues.put(COLUMN_CONTACT_NO, contact_no);
            contentValues.put(COLUMN_ADDRESS, address);
            contentValues.put(COLUMN_PIC, pic);
            contentValues.put(COLUMN_BLOOD_GROUP, blood_group);

            long res = db.update(TABLE_NAME_AUTHENTICATION, contentValues,"email=" +"'"+email+"' ",null);
            if (res == -1)
                result = "Error in saving";
            else
                return "Updated successfully";
        } catch (Exception ex) {
            result=ex.getMessage();
        }
        return result;
    }

    public Cursor getDisease(String hsptlName){
        Cursor cur=null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + TABLE_NAME_HOSPITAL_INFO + " where NAME='" + hsptlName + "' ", null);

        } catch (Exception ex) {
        }
        return cur;
    }

    public String add_Hospital(String name,String lat,String lng){
        String result="";
        Cursor cur;
        try{
            cur = getHospital(name);
            int i = cur.getColumnCount();

            if (cur.getCount() <= 0) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_NAME_H, name);
                contentValues.put(COLUMN_LATITUDE_H, lat);
                contentValues.put(COLUMN_LONGITUDE_H, lng);

                long res = db.insert(TABLE_NAME_HOSPITAL_INFO, null, contentValues);
                if (res == -1)
                    result = "Error in saving";
                else
                    return "Inserted successfully";
            }
            else {
                return "Already Added";
            }
        }catch (Exception ex){

        }
        return result;
    }
*/

    public String databasetostring() {

        String dbString = "";

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_Disease + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast())
        {
            if (c.getString(c.getColumnIndex("Disease_Name")) != null)
            {

                dbString += c.getString(c.getColumnIndex("Disease_Name"));

                dbString += "\n";

            }

            c.moveToNext();

        }

        db.close();

        return dbString;

    }
    public Cursor GetPlanDetails(int Pid,String Pname){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from Save_Plan where PlanID="+Pid,null);

        return c;
    }
    public int GetPlanid(String pname){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select Save_Plan.PlanID from Save_Plan where "+COLUMN_PNAME+"='"+pname+"'",null);
        c.moveToFirst();
        int t=0;
        while (!c.isAfterLast()){
            t=c.getInt(0);
            c.moveToNext();

        }

        return t;
    }
    public int GetPlanCal(int Pid,String Pname){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("select Save_Plan.Total_calories from Save_Plan where Save_Plan.PlanID="+Pid,null);
       c.moveToFirst();
        int t=0;
       if(!c.isAfterLast()){
            t+=c.getInt(0);

        }
        return t;
    }

    public ArrayList<String> getAllDiseases() {

        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT  * FROM " + TABLE_NAME_Disease + ";", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String dis = res.getString(1);
            // res.getString(res.getColumnIndex(COLUMN_Disease_Name));
            array_list.add(dis);
            res.moveToNext();
        }
        return array_list;

    }

    public ArrayList<FoodProduct> get_Food() {
        Cursor res = null;
        ArrayList<FoodProduct> array_list=new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            res = db.rawQuery("select * from Food order by NAME;", null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                FoodProduct f=new FoodProduct();
                f.setId(res.getInt(0));
                f.setNombre(res.getString(1));
                f.setCalories(res.getInt(2));
                f.setUnit(res.getString(3));
                f.setQuantity(1);
                String myUrl = res.getString(5);

                f.setFoodImage(myUrl);
                f.setStatus("Enabled");


                // res.getString(res.getColumnIndex(COLUMN_Disease_Name));
                array_list.add(f);
                res.moveToNext();
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return array_list;
    }
    public ArrayList<FoodProduct> get_FoodWithDisease(String Dis) {
      Cursor res = null,fur=null;
        ArrayList<FoodProduct> array_list=new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            res=null;
            res = db.rawQuery("select Food.FoodID,Name,Calories,Unit,Image from Food ", null);
            res.moveToFirst();

            while (!res.isAfterLast()) {


                        FoodProduct f = new FoodProduct();
                        f.setId(res.getInt(0));
                        f.setNombre(res.getString(1));
                        f.setCalories(res.getInt(2));
                        f.setUnit(res.getString(3));
                        f.setQuantity(1);
                        String myUrl = res.getString(4);
                        f.setFoodImage(myUrl);
                        f.setChecked(false);
                f.setStatus("Enabled");
                        // res.getString(res.getColumnIndex(COLUMN_Disease_Name));
                        array_list.add(f);




                res.moveToNext();
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return array_list;
    }
    public ArrayList<FoodProduct> get_Foods(String Cat) {
        Cursor res = null;
        ArrayList<FoodProduct> array_list=new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            res = db.rawQuery("select * from Food where Food.Category='"+Cat+"' order by NAME;", null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                FoodProduct f=new FoodProduct();
                f.setId(res.getInt(0));
                f.setNombre(res.getString(1));
                f.setCalories(res.getInt(2));
                f.setUnit(res.getString(3));
                f.setQuantity(1);
                String myUrl = res.getString(5);

                f.setFoodImage(myUrl);
                f.setStatus("Enabled");

                // res.getString(res.getColumnIndex(COLUMN_Disease_Name));
                array_list.add(f);
                res.moveToNext();
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return array_list;
    }
    public int diseaseid(String Dis) {
        Cursor cur = null;


        SQLiteDatabase db = this.getReadableDatabase();
        cur = db.rawQuery("select "+COLUMN_DiseaseID+" from " + TABLE_NAME_Disease + " where "+COLUMN_DiseaseID+"='" + Dis + "' ", null);

        cur.moveToFirst();
        if(!cur.isAfterLast()){
            return cur.getInt(0);
        }else {
            return 0;
        }

    }
    public ArrayList<FoodProduct> get_Foods_dis(String Cat,String Diseases) {
        Cursor res = null;
        ArrayList<FoodProduct> array_list=new ArrayList<>();
        try {

                SQLiteDatabase db = this.getReadableDatabase();
                res = db.rawQuery("Select * from Food where Food.Category='"+Cat+"' and Food.FoodID in ( select FoodID from Food join Food_disease on  Food.FoodID==Food_disease.FFood_ID where Food_disease.FDisease_ID in (select User.DiseaseId from User)) ", null);
                res.moveToFirst();

                while (!res.isAfterLast()) {
                    FoodProduct f = new FoodProduct();
                    f.setId(res.getInt(0));
                    f.setNombre(res.getString(1));
                    f.setCalories(res.getInt(2));
                    f.setUnit(res.getString(3));
                    f.setQuantity(1);
                    String myUrl = res.getString(5);
                    f.setFoodImage(myUrl);
                    f.setStatus("Disabled");
                    // res.getString(res.getColumnIndex(COLUMN_Disease_Name));
                    array_list.add(f);
                    res.moveToNext();
                }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return array_list;
    }

}
