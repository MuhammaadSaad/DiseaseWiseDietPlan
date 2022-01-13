package com.example.saad_pcp.diseasewisedietplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Saad-PCP on 4/6/2017.
 */
public class Products extends ArrayAdapter<Product> {

    Context context;
    ArrayList<Product> product;
    LayoutInflater inflater;
    Product modelProducto;

    private class ViewHolder{
        TextView txtName;
      //  TextView txtPresentacion;
        CheckBox checkBox;

    }
    public Products(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);

        this.context = context;
        this.product = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder;
        if (row == null){
            row = inflater.inflate(R.layout.layout_multi, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.txtName = (TextView) row.findViewById(R.id.txt_product);

            viewHolder.checkBox = (CheckBox) row.findViewById(R.id.checkBoxPro);
            row.setTag(viewHolder);


        }else{
            viewHolder = (ViewHolder) row.getTag();
        }

        modelProducto = product.get(position);

        viewHolder.txtName.setText(modelProducto.getName());
        /// viewHolder.txtPresentacion.setText(modelProducto.getPresentacion());
        viewHolder.checkBox.setChecked(modelProducto.isChecked());



        return row;

    }

    public ArrayList<Product> getProductos(){
        return product;
    }

}

