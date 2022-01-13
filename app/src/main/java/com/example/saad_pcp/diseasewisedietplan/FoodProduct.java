package com.example.saad_pcp.diseasewisedietplan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.net.URI;

/**
 * Created by Saad-PCP on 4/8/2017.
 */
public class FoodProduct { private String ProductName;

    private String  FoodImagepath;
    private int Calories;
    private int  Quantity;
    private String Unit;
    private boolean checked = false;
    private String status;

    private int _id;

    public void setStatus(String sa){
        status=sa;
    }
    public String getStatus(){
        return status;
    }
    public FoodProduct(){Quantity=2;}
    public String GetImage(){


        return FoodImagepath;
    }
    public void setFoodImage(String imageUri){
        FoodImagepath=imageUri;

       // FoodImage.setImageURI(data);
    }

    public double getCalories() {
        return Calories;
    }

    public void setCalories(int Cal) {
        this.Calories = Cal;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }
    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Un) {
        this.Unit = Un;
    }


    public String getName() {
        return ProductName;
    }

    public void setNombre(String nombre) {
        this.ProductName = nombre;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

  /*  public String getPresentacion() {
        return _presentacion;
    }

    public void setPresentacion(String _presentacion) {
        this._presentacion = _presentacion;
    }*/

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

