package com.example.zametki2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class MyActivity extends Activity {

    public static final int REQUEST_FOR_READER = 777;
    public  static final int INFO_REQUEST = 898;
    public static final String BOOK_DOC = "com.example.zametki2.MyBook";
    private TextView txtview ;
    private ArrayList<MyBook> listOfBooks;
    private BookAdapter adapter;
    private ListView list;
    private EditText txtSearch;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        listOfBooks = ((AppContext) getApplicationContext()).getListOfBooks();

        list= (ListView)findViewById(R.id.listView);
        list.setEmptyView(findViewById(R.id.emptyView));

        list.setOnItemClickListener(new ListViewClickListener());
        list.setTextFilterEnabled(true);
        txtSearch = (EditText)findViewById(R.id.search_edit_text);
        txtSearch.addTextChangedListener(new TextChangeListener());
        intent = new Intent(this, Reader.class);
    }

    @Override
    protected void onStart() {

        super.onStart();
        adapter = new BookAdapter(this, listOfBooks);
        list.setAdapter(adapter);
        checkSearchActive();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; intthis adds items to the action bar if it is present.
        Log.v("test" , "OnCreateOptions menu");
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode == REQUEST_FOR_READER){
           MyBook book = null;
           switch (resultCode){
               case Reader.RESULT_BOOK_DELETE:{
                   book = (MyBook)data.getSerializableExtra(BOOK_DOC);
                   deleteBook(book);
               }
               break;
               case Reader.RESULT_CANCELED:
                   MyBook.bookRemove();
                   break;
               case Reader.RESULT_BOOK_SAVE:{
                   book  = (MyBook)data.getSerializableExtra(BOOK_DOC);
                   if (isNew (book)){
                       saveBook(book);
                   }else {
                       updateBook(book);
                   }
               }
               break;
               default:
                   Toast.makeText(this ,"Somethig goes wrong" ,Toast.LENGTH_SHORT).show();
           }
       }

       if (requestCode == INFO_REQUEST){
           Toast.makeText(this, "Wellcome back from info-page" , Toast.LENGTH_SHORT).show();
       }

        super.onActivityResult(requestCode, resultCode, data);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

       switch (item.getItemId()) {
           case R.id.add_new :{

               Bundle bundle = new Bundle();
               bundle.putInt(AppContext.ACTION_TYPE , AppContext.ACTION_NEW_TASK);
               intent.putExtras(bundle);
               startActivity(intent);
               return true;
           }
           case R.id.menu_info:{
               Intent intent = new Intent(this, InfoActivity.class);
               startActivityForResult(intent , INFO_REQUEST);
               return true;
           }
       }
        return super.onOptionsItemSelected(item);
    }

    private class ListViewClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MyBook book = (MyBook)parent.getAdapter().getItem(position);
            showBook(book);
        }
    }
    private void showBook(MyBook book){
        Intent intent = new Intent(this , Reader.class);
        intent.putExtra(BOOK_DOC, book);
        startActivityForResult(intent, REQUEST_FOR_READER);
    }
    private void deleteBook(MyBook book){
        if(listOfBooks.size()!= 0) {
            listOfBooks.remove(book.getId() - 1);
            MyBook.bookRemove();
            Collections.sort(listOfBooks);
            adapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this, "There is no books", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveBook(MyBook book) {

        listOfBooks.add(book);
        Log.v("test","book saved.  Number of books " + listOfBooks.size());
        Collections.sort(listOfBooks);
        adapter.notifyDataSetChanged();
    }
    public void updateBook (MyBook book){
        book.setDate(new Date());
        listOfBooks.set(book.getId()-1 , book);
        Collections.sort(listOfBooks);
        adapter.notifyDataSetChanged();
    }
    private boolean isNew(MyBook book) {

        if (listOfBooks.size()  < book.getId()) {
        return true;
        }else
            return false;
    }
    private class TextChangeListener implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    private void checkSearchActive(){
        if(listOfBooks.isEmpty()){
            txtSearch.setEnabled(false);
        }else {
            txtSearch.setEnabled(true);
            adapter.getFilter().filter(txtSearch.getText());
        }
    }
}
