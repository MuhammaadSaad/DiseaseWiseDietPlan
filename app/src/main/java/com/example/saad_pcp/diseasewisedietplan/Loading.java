package com.example.saad_pcp.diseasewisedietplan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Loading extends AppCompatActivity {
DataBaseHelper db;

    public GetFilePathAndStatus GetFile(String fname,String path){
        String jsonString = WCFHandler.GetJsonResult("getFile/"+path);
        return getFileFromBase64AndSaveInSDCard(jsonString,fname,"jpg");

    }

    public static GetFilePathAndStatus getFileFromBase64AndSaveInSDCard(String base64, String filename,String extension){
        GetFilePathAndStatus getFilePathAndStatus = new GetFilePathAndStatus();
        try {
            byte[] pdfAsBytes = Base64.decode(base64, 0);
            FileOutputStream os;
            os = new FileOutputStream(getReportPath(filename,extension), false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
            getFilePathAndStatus.filStatus = true;
            getFilePathAndStatus.filePath = getReportPath(filename,extension);
            return getFilePathAndStatus;
        } catch (IOException e) {
            e.printStackTrace();
            getFilePathAndStatus.filStatus = false;
            getFilePathAndStatus.filePath = getReportPath(filename,extension);
            return getFilePathAndStatus;
        }
    }

    public static String getReportPath(String filename,String extension) {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "/DietPlan");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + filename + "."+extension);
        return uriSting;
    }
    public static class GetFilePathAndStatus{
        public boolean filStatus;
        public String filePath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(ProgressBar.VISIBLE);
        db = new DataBaseHelper(this.getApplicationContext());
// run a background job and once complete

        Thread th = new Thread() {
            @Override
            public void run() {
                int pr = 20;
                try {


                    String JsonStringFood = WCFHandler.GetJsonResult("AllFoodData");
                    JSONArray jsonAraFood = new JSONArray(JsonStringFood);
                    for (int i = 0; i < jsonAraFood.length(); i++) {

                        JSONObject jsonObj = jsonAraFood.getJSONObject(i);
                        GetFilePathAndStatus gf = new GetFilePathAndStatus();
                      gf= GetFile(jsonObj.getString("F_name"), jsonObj.getString("F_image"));
                        db.insertFood( jsonObj.getString("F_name"), jsonObj.getString("F_calories"), jsonObj.getString("F_unit"), jsonObj.getString("F_cat"),gf.filePath);
                        pb.setProgress(pr += 2);

                    }
                    db.close();

pr=0;
                    // ArrayList<Disease> Dis=new ArrayList<>();
                    String JsonString = WCFHandler.GetJsonResult("AllDiseaseName");
                    JSONArray jsonAra = new JSONArray(JsonString);
                    for (int i = 0; i < jsonAra.length(); i++) {

                        JSONObject jsonObj = jsonAra.getJSONObject(i);

                        db.insert_Disease(jsonObj.getInt("D_id"),jsonObj.getString("D_name"));
                        pb.setProgress(pr += 2);

                    }
                    db.close();


                    String JsonStringF = WCFHandler.GetJsonResult("GetLink");
                    JSONArray jsonAraF = new JSONArray(JsonStringF);
                    for (int i = 0; i < jsonAraF.length(); i++) {

                        JSONObject jsonObj = jsonAraF.getJSONObject(i);

                        db.insertFood_Disease(jsonObj.getInt("Fid"),jsonObj.getInt("Did"));
                        pb.setProgress(pr += 2);

                    }
                    db.close();



                } catch (JSONException e) {
                    e.printStackTrace();

                }



                try {
                    sleep(2000);
                    pb.setProgress(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Intent Main = new Intent(getApplicationContext(), Navigation.class);
                startActivity(Main);
            }

        };
        th.start();
   }


}