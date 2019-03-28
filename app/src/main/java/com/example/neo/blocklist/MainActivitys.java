package com.example.neo.blocklist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivitys extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;
    String contactName = null;
    static int x=0;
    String numBlock;
    final String noName="no name";
    TextView listItem;
    EditText enterNum,countryCode;
    Button addNum;
 public   static ArrayList<String> blockList;
  public  static ArrayList<String> blockList_contactName;
    ListView blockListView;
    Intent tent=new Intent();
    Adapter dap =new Adapter();
    int i;

    //.......
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
//.................
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blockListView=(ListView)findViewById(R.id.list_view);
        enterNum=(EditText)findViewById(R.id.enter_number);
        blockList=new ArrayList<>();
        blockList_contactName=new ArrayList<>();
        blockListView.setAdapter(dap);
        countryCode=(EditText)findViewById(R.id.country_code);
        addNum=(Button)findViewById(R.id.add_button);
    //   blockList.add("123456789");
        blockListView.setAdapter(dap);

//        clearList=(Button)findViewById(R.id.contacts);
//        clearList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent tent=new Intent(MainActivity.this,Contact_input.class);
//                startActivity(tent);
//
//            }
//        });
        addNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try{
                if(countryCode.getText().toString().trim().length() > 0 &&
                        enterNum.getText().toString().trim().length() > 0) {
                    numBlock="+"+countryCode.getText().toString() + enterNum.getText().toString();
                    blockList.add(numBlock);

                   blockList_contactName.add(contactName);
                   //....................
                    // Steps to insert data into db (instead of using raw SQL query)
                    // first, Create an object of ContentValues
                    final ContentValues values = new ContentValues();

                    // second, put the key-value pair into it
                  //  values.put("phone_number", blockList.numBlock);

                    // thirst. insert the object into the database
                    final long id = database.insert(DatabaseHelper.TABLE_BLACKLIST , null, values);

                    // set the primary key to object and return back
                   // blockList.id = id;
                    //...........................

                    enterNum.setText("");
                    countryCode.setText("");
                    Log.e("List",numBlock);
                    for(i=0;i<blockList.size();i++) {
                        Log.e("blowList", blockList.get(i));
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivitys.this);
                        prefs.edit().putString("BLOCK",blockList.get(i)).apply();
                    }
                    for(int j=0;j<blockList_contactName.size();j++) {
                        Log.e("blowList_Name", blockList_contactName.get(j));
                       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivitys.this);
                        prefs.edit().putString("BLOCK",blockList_contactName.get(j)).apply();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Empty Feilds",Toast.LENGTH_LONG).show();
                }}
                catch (Exception e){
                    blockList_contactName.add("no name");


                }

                blockListView.setAdapter(dap);
//                tent.putStringArrayListExtra("BLOCK_LIST",blockList);
//                sendBroadcast(tent);
            }
        });
        try {


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivitys.this);
            prefs.edit().putString("BLOCK", blockList.get(i)).apply();
            Log.e("OUTSIDE", blockList.get(i));
        }
        catch (Exception e){
            Log.e("OUTSIDE", "hhhhh");

        }

//        clearList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                blockList.clear();
//                blockListView.setAdapter(dap);
//            }
//        });


        blockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                PopupMenu popup = new PopupMenu(MainActivitys.this, blockListView, Gravity.NO_GRAVITY);
                popup.getMenuInflater().inflate(R.menu.pop, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.remove) {
                            String numto_Remove = blockList.get(position);

                            blockList.remove(position);
                            blockList_contactName.remove(position);
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivitys.this);
                            prefs.edit().clear().apply();

                            Toast.makeText(getApplicationContext(),"Removed:" + numto_Remove, Toast.LENGTH_LONG).show();
                            blockListView.setAdapter(dap);
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu (Menu menu){
//        getMenuInflater().inflate(R.menu.list, menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        blockList.add(numBlock);

        blockList_contactName.add(contactName);

        Log.e("List",numBlock);
        int k;
        for(k=0;i<blockList.size();k++) {
            Log.e("blowList", blockList.get(i));
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putString("BLOCK",blockList.get(k)+"");
            editor.apply();
            Log.e("OUTSIDE", blockList.get(k));
        }

    }

//    @Override
//    protected void onDestroy() {
//        blockList.add(numBlock);
//
//        blockList_contactName.add(contactName);
//
//        Log.e("List",numBlock);
//        for(i=0;i<blockList.size();i++) {
//            Log.e("blowList", blockList.get(i));
//            SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
//            SharedPreferences.Editor editor=prefs.edit();
//            editor.putString("BLOCK",blockList.get(i)+"");
//            editor.apply();
//            Log.e("OUTSIDE", blockList.get(i));
//        }
//
//        super.onDestroy();
//
//    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.one) {
            Intent tent = new Intent(MainActivitys.this, BlockLog.class);
            startActivity(tent);
        } else if (id == R.id.two) {
            Toast.makeText(getApplicationContext(), "Help is On its Way ;-)", Toast.LENGTH_LONG).show();
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    class Adapter extends BaseAdapter {
        LayoutInflater inflater;
        @Override
        public int getCount() {
            return blockList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.text_view,null);
            TextView serialNum,conName;
            listItem =(TextView) convertView.findViewById(R.id.con_num);
            listItem.setText(blockList.get(position));
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivitys.this);
            prefs.edit().putString("BLOCK",blockList.get(position)).apply();
            serialNum=(TextView) convertView.findViewById(R.id.serial_number);
            serialNum.setText((position+1)+".");
            conName=(TextView) convertView.findViewById(R.id.con_name);
            conName.setText(blockList_contactName.get(position));
            return convertView;
        }

    }
    boolean search(String incomNumber){
//        blockList.add("nothing is added");
//        Log.e("blackList",blockList.get(blockList.size()-1));
        if(blockList.contains(incomNumber)){
            return true;
        }
        return false;
    }

    public void onClickSelectContact(View btnSelectContact) {

        // using native contacts selection
        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactName();
            retrieveContactNumber();
            blockListView.setAdapter(dap);
        }
    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            numBlock=contactNumber.replaceAll("\\s+","");
            if(numBlock.length()>8){
                countryCode.setText("91");
                enterNum.setText(numBlock);}
            else{
                Toast.makeText(getApplicationContext(),"No Number Found",Toast.LENGTH_LONG).show();
            }
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }

    private void retrieveContactName() {



        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))+":";
            if(enterNum.getText().length()>2){
                blockList_contactName.add(contactName);}
            else{
                Toast.makeText(getApplicationContext(),"No Number Found",Toast.LENGTH_LONG).show();
            }
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

    }
}