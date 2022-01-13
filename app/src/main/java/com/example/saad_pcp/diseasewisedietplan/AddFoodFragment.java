package com.example.saad_pcp.diseasewisedietplan;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class AddFoodFragment extends Fragment {

    ImageButton imgbtn;
    Button btnAddFood;
    EditText fname,FoodCal,FoodUnit;
    Spinner FoodCat;
    ImageView img;

    ImageView imageView;
    public AddFoodFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root=inflater.inflate(R.layout.fragment_add_food, container, false);
        imgbtn=(ImageButton)root.findViewById(R.id.setFoodImg);
        btnAddFood=(Button)root.findViewById(R.id.Add_FoodData);
        fname=(EditText)root.findViewById(R.id.EntryFoodName);
        FoodCal=(EditText)root.findViewById(R.id.EntryFoodCal);
        FoodUnit=(EditText)root.findViewById(R.id.EntryFoodUnit);
        FoodCat=(Spinner)root.findViewById(R.id.FoodCat);
        imageView=(ImageView) root.findViewById(R.id.imgview);
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fname.getText().toString()=="" ||FoodCal.getText().toString()=="" ||FoodUnit.getText().toString()==""||FoodCat.getSelectedItem().toString()==""||imageUri.toString()==""  )
                    Toast.makeText(getContext(),"Please Fill Details",Toast.LENGTH_LONG).show();
                else{
                    DataBaseHelper db=new DataBaseHelper(getContext());
                    db.insertFood(fname.getText().toString(),FoodCal.getText().toString(),FoodUnit.getText().toString(),FoodCat.getSelectedItem().toString(),imageUri.toString());
                    Toast.makeText(getContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                }
            }
        });

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{
                    Intent i = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    Toast.makeText(getContext(),"Chose File from Gallery",Toast.LENGTH_SHORT).show();
                    for(int f=0;f<=300;f++) {
                        imageUri = i.getData();

                        imageView.setImageURI(imageUri);
                    }
                }catch(Exception exp){
                    Log.i("Error",exp.toString());
                }

            }
        });


        return root;
    }
boolean flags=true;
    Uri imageUri=null;
    static   int  RESULT_LOAD_IMAGE=1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        flags=false;
        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            imageUri = data.getData();

            imageView.setImageURI(imageUri);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor =getActivity().getContentResolver().query(imageUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);



        }
    }

}
