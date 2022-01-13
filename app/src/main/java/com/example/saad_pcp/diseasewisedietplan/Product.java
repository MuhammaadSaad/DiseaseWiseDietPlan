package com.example.saad_pcp.diseasewisedietplan;

/**
 * Created by Saad-PCP on 4/6/2017.
 */
public  class Product {

    private String ProductName;
    //private String _presentacion;
    private boolean checked = false;
    private int _id;

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

