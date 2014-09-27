package com.example.zametki2;

import android.app.Application;

import java.util.ArrayList;

public class AppContext extends Application {
    public static final String ACTION_TYPE ="com.example.zametki.actionType";
    public static final String DOC_INDEX ="com.example.zametki.actionIndex";

    public static final int ACTION_NEW_TASK =0;
    public static final int ACTION_UPDATE = 1;
    private ArrayList<MyBook> listOfBooks = new ArrayList<MyBook>();
     public  ArrayList<MyBook> getListOfBooks(){
         return listOfBooks;
     }
    public void setListOfBooks (ArrayList<MyBook> listOfBooks){
        this.listOfBooks  = listOfBooks;
    }

}
