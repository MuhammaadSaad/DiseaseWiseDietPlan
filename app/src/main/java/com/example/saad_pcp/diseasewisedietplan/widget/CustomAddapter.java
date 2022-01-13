package com.example.saad_pcp.diseasewisedietplan.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saad_pcp.diseasewisedietplan.FoodProduct;
import com.example.saad_pcp.diseasewisedietplan.R;

import java.util.ArrayList;

public class CustomAddapter

extends ArrayAdapter<FoodProduct> implements View.OnClickListener{

    private ArrayList<FoodProduct> dataSet;
    Context context;
    ArrayList<FoodProduct> product;
    LayoutInflater inflater;
    FoodProduct modelProducto;

    Context mContext;

    // View lookup cache
    private class ViewHolder{
        TextView txtFoodName;
        TextView txtFoodCal;
        TextView txtFoodUnit;
        TextView tatFoodQuantity;
        //  TextView txtPresentacion;
        CheckBox checkBox;
        LinearLayout food;

    }
    public CustomAddapter(ArrayList<FoodProduct> data, Context context) {
        super(context, R.layout.food_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        modelProducto = product.get(position);
        modelProducto.setChecked(true);
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder;
        if (row == null){
            row = inflater.inflate(R.layout.food_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.food=(LinearLayout)row.findViewById(R.id.foodLayout);
            viewHolder.txtFoodName = (TextView) row.findViewById(R.id.txt_Food);
            viewHolder.txtFoodCal=(TextView)row.findViewById(R.id.txt_FoodCal);
            viewHolder.txtFoodUnit =(TextView)row.findViewById(R.id.txt_FoodUnit);
            viewHolder.checkBox = (CheckBox) row.findViewById(R.id.checkBoxFood);
            viewHolder.tatFoodQuantity=(TextView)row.findViewById(R.id.txtFoodQty);
            row.setTag(viewHolder);


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
        }

        viewHolder.txtFoodName.setText(modelProducto.getName());
        viewHolder.txtFoodCal.setText("Calories: "+modelProducto.getCalories());
        viewHolder.txtFoodUnit.setText("Unit: "+modelProducto.getUnit());
        viewHolder.tatFoodQuantity.setText("Quantity: "+modelProducto.getQuantity());
        /// viewHolder.txtPresentacion.setText(modelProducto.getPresentacion());
        viewHolder.checkBox.setChecked(modelProducto.isChecked());



        return row;

    }
}