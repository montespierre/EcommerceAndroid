package com.weimont.ecommerceandroid.Model;

public class PlateModel {

    public PlateModel(){
        /// Constructor vacio
    }

    private int plate_img;

    public PlateModel(int plate_img){
        this.plate_img = plate_img;
    }

    public int getPlate_img(){
        return plate_img;
    }

    public void setPlate_img(int plate_img){
        this.plate_img = plate_img;
    }
}
