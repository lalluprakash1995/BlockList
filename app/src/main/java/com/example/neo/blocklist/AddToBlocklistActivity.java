package com.example.neo.blocklist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class AddToBlocklistActivity extends Activity implements View.OnClickListener {
    EditText phnedt,countryedt;
    Button submit,clearbtn;
    private BlackListDAO blackListdao;
    private static final int REQUEST_CODE_PICK_CONTACTS=1;
    private Uri uriContact;
    private String contactID,numBlock,contactName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_blocklist);
        phnedt=(EditText)findViewById(R.id.phonenumeditxt);
        countryedt=(EditText)findViewById(R.id.countryeditxt);
        submit=(Button) findViewById(R.id.submitbtn);
        clearbtn=(Button) findViewById(R.id.cancelbtn);
        blackListdao=new BlackListDAO(this);
        submit.setOnClickListener(this);
        clearbtn.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        if (v==submit){
            if (countryedt.getText().toString().trim().length()>0 && phnedt.getText().toString().trim().length()>0){
                final BlackList phone=new BlackList();
                phone.phoneNumber="+"+countryedt.getText().toString()+phnedt.getText().toString();
                blackListdao.create(phone);
                showDialog();
            }
            else{
                showMessageDialog("All fields are mandatory !!");
            }

        }
        else if (v==clearbtn){
            reset();
        }

    }
    private void reset(){
        countryedt.setText("");
        phnedt.setText("");
    }
    private void showDialog(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("Phone number added to blocklist sucessfully");
        alertDialog.setPositiveButton("Add More", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        final AlertDialog alert=alertDialog.create();
        alert.show();
    }
    private void showMessageDialog(final String message){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void selectFromContact(View view) {
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
         //   blockListView.setAdapter(dap);
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
                countryedt.setText("91");
                phnedt.setText(numBlock);}
            else{
                Toast.makeText(getApplicationContext(),"No Number Found", Toast.LENGTH_LONG).show();
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
//            if(enterNum.getText().length()>2){
//                blockList_contactName.add(contactName);}
//            else{
//                Toast.makeText(getApplicationContext(),"No Number Found",Toast.LENGTH_LONG).show();
//            }
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

    }
}
