package com.example.saad_pcp.diseasewisedietplan.widget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Saad-PCP on 4/20/2017.
 */

public class Food {
    private String fCalories;

    private String fCat;

    private Integer fId;

    private String fImage;

    private String fName;

    private String fUnit;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getFCalories() {
        return fCalories;
    }


    public void setFCalories(String fCalories) {
        this.fCalories = fCalories;
    }


    public String getFCat() {
        return fCat;
    }


    public void setFCat(String fCat) {
        this.fCat = fCat;
    }


    public Integer getFId() {
        return fId;
    }


    public void setFId(Integer fId) {
        this.fId = fId;
    }
    public String getFImage() {
        return fImage;
    }


    public void setFImage(String fImage) {
        this.fImage = fImage;
    }


    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFUnit() {
        return fUnit;
    }

    public void setFUnit(String fUnit) {
        this.fUnit = fUnit;
    }
}
