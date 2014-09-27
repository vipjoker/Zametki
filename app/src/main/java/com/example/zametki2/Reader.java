package com.example.zametki2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class Reader extends Activity {
    private EditText editText;
    public static final int RESULT_BOOK_SAVE = 1;
    public static final int RESULT_BOOK_DELETE = -1;
    public static final int RESULT_CANCELED = 0;
    public static final int NAME_LENGTH= 20;
    private MyBook bookFromIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        bookFromIntent= (MyBook) getIntent().getSerializableExtra(MyActivity.BOOK_DOC);
        Toast.makeText(this,bookFromIntent.getContext().toString() , Toast.LENGTH_SHORT).show();
        editText = (EditText)findViewById(R.id.editText);
        editText.setText(bookFromIntent.getContext());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.save :
            case android.R.id.home:
                String text = editText.getText().toString().trim();
                if (text.length() != 0) {
                    saveBook(text);
                    setResult(RESULT_BOOK_SAVE, getIntent());
                }else{
                    setResult(RESULT_CANCELED, getIntent());
                }
                    finish();
                return true;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete this file?");
                builder.setPositiveButton("Delete" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        setResult(RESULT_BOOK_DELETE, Reader.this.getIntent());
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveBook(String text){


        if (text.length() < NAME_LENGTH){
            bookFromIntent.setName(text);
        }else {
            bookFromIntent.setName(text.substring(0,20) + "...");
        }
        bookFromIntent.setContext(text);
    }
}
