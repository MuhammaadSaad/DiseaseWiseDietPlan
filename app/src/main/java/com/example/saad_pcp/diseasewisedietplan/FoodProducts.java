package com.example.saad_pcp.diseasewisedietplan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Saad-PCP on 4/8/2017.
 */
public class FoodProducts extends ArrayAdapter<FoodProduct> implements View.OnClickListener {
        Context context;
        ArrayList<FoodProduct> product;
        LayoutInflater inflater;
        FoodProduct modelProducto;

private class ViewHolder{
    TextView txtFoodName;
    TextView txtFoodCal;
    TextView txtFoodUnit;
    TextView tatFoodQuantity;
    //  TextView txtPresentacion;
    CheckBox checkBox;
    ImageView Img;
    LinearLayout food;

}

    public FoodProducts(Context context, int resource, ArrayList<FoodProduct> objects) {
        super(context, resource, objects);


        this.context = context;
        this.product = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        FoodProduct dataModel=(FoodProduct) object;





                if (dataModel.isChecked())
                    dataModel.setChecked(false);
                else
                    dataModel.setChecked(true);




                Snackbar.make(v, "Name" +dataModel.getName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();






    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        final ViewHolder viewHolder;
        if (row == null){
            row = inflater.inflate(R.layout.food_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.food=(LinearLayout)row.findViewById(R.id.foodLayout);
            viewHolder.txtFoodName = (TextView) row.findViewById(R.id.txt_Food);
            viewHolder.txtFoodCal=(TextView)row.findViewById(R.id.txt_FoodCal);
            viewHolder.txtFoodUnit =(TextView)row.findViewById(R.id.txt_FoodUnit);
            viewHolder.checkBox = (CheckBox) row.findViewById(R.id.checkBoxFood);
            viewHolder.tatFoodQuantity=(TextView)row.findViewById(R.id.txtFoodQty);
            viewHolder.Img=(ImageView)row.findViewById(R.id.FoodImage);


            row.setTag(viewHolder);
            row.setTag(R.id.txt_Food,viewHolder.txtFoodName);
            row.setTag(R.id.txt_FoodCal,viewHolder.txtFoodCal);
            row.setTag(R.id.txt_FoodUnit,viewHolder.txtFoodUnit);
            row.setTag(R.id.txt_FoodUnit,viewHolder.tatFoodQuantity);

            row.setTag(R.id.FoodImage,viewHolder.Img);
            row.setTag(R.id.checkBoxFood,viewHolder.checkBox);



        }else{
            viewHolder = (ViewHolder) row.getTag();
        }


        modelProducto = product.get(position);
        if(modelProducto.getStatus()=="Enabled"){
            viewHolder.food.setBackgroundColor(Color.WHITE);
            row.setBackgroundColor(Color.WHITE);
        }else {
            viewHolder.food.setBackgroundColor(Color.GRAY);
            row.setBackgroundColor(Color.GRAY);
            viewHolder.checkBox.setVisibility(View.INVISIBLE);
        }
        viewHolder.txtFoodName.setText(modelProducto.getName());
        viewHolder.txtFoodCal.setText("Calories: "+modelProducto.getCalories());
        viewHolder.txtFoodUnit.setText("Unit: "+modelProducto.getUnit());

        viewHolder.tatFoodQuantity.setText("Quantity: "+modelProducto.getQuantity());
        /// viewHolder.txtPresentacion.setText(modelProducto.getPresentacion());
        File imgFile = new File(modelProducto.GetImage());

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            viewHolder.Img.setImageBitmap(myBitmap);

        }

        viewHolder.checkBox.setChecked(modelProducto.isChecked());



        return row;

    }

    public ArrayList<FoodProduct> getProductos(){
        return product;
    }

}

