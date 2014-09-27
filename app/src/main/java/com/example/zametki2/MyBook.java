package com.example.zametki2;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBook implements Serializable ,Comparable<MyBook>{
    private static final long serialVersionUID = 3434234234545L;
    private Date date ;
    private String name ;
    private String context;
    private static int bookId=0;

    public MyBook(String name, String context, Date date) {
        this.name = name;
        this.context = context;
        this.date = date;
        bookId++;
    }

    public MyBook() {
    }
    public static void bookRemove (){
        bookId --;
    }
    public String getStringDate() {
        return new SimpleDateFormat("HH:mm  dd/MM").format(date);
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(MyBook another) {
        return another.getDate().compareTo(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
    public String toString (){

        return name ;
    }
    public int getId(){
        return bookId;
    }
}
