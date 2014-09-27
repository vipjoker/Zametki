package com.example.zametki2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<MyBook>{

    private List <MyBook> list;
    private Context context ;

    public BookAdapter(Context context,List<MyBook> list){
        super(context,R.layout.text_layout , list);
        this.list =list;
        this.context = context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.text_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textName = (TextView)convertView.findViewById(R.id.layout_for_list_txt_name);
            viewHolder.textDate = (TextView)convertView.findViewById(R.id.layout_for_list_txt_date);
            convertView.setTag(viewHolder);
        }
       ViewHolder holder = (ViewHolder) convertView.getTag();
        MyBook book = list.get(position);

        holder.textName.setText(book.getName());
        holder.textDate.setText(book.getStringDate());

        return convertView;
    }
    static class ViewHolder {
        TextView textName;
        TextView textDate;
    }
    public void filter (String s){
        List<MyBook> origList = list;
        List<MyBook> filterList = new ArrayList<MyBook>();
        if (s.trim().length() == 0){
            this.list = origList;
            return;
        }else{
            for(MyBook book : origList){
                if (book.getName().startsWith(s))
                    filterList.add(book);
            }
            if (!filterList.isEmpty())
                this.list = filterList;
            this.notifyDataSetChanged();
        }


    }
}
