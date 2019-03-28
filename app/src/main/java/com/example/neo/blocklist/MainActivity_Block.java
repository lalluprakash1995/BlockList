package com.example.neo.blocklist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

//import com.android.internal.telephony.ITelephony;
public class MainActivity_Block extends Activity implements View.OnClickListener,AdapterView.OnItemLongClickListener {
    ListView blocklistitems;
    Button addnewnmbr,contact;
    private BlackListDAO blackListDAO;
    public static List<BlackList> blocklist;
    private int selectedRecordPosition = -1;
    private static final int  REQUEST_CODE_PICK_CONTACTS=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainblock);
        addnewnmbr=(Button)findViewById(R.id.addnumberbtn);
     //   contact=(Button)findViewById(R.id.contactbtn);
        addnewnmbr.setOnClickListener(this);
        blocklistitems=(ListView)findViewById(R.id.blklist);
        final LayoutInflater inflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowview=inflater.inflate(R.layout.contents,blocklistitems,false);
        blocklistitems.addHeaderView(rowview);
        blocklistitems.setOnItemLongClickListener(this);

        // Contacts.............
//        contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });









    }
    private void populateNoRecordMsg(){
        if (blocklist.size()==0){
            final TextView tv=new TextView(this);
            tv.setPadding(5,5,5,5);
            tv.setTextSize(15);
            tv.setText("No Record Found.....");
        }
    }

    @Override
    public void onClick(View v) {
        if (v==addnewnmbr){
            startActivity(new Intent(this,AddToBlocklistActivity.class));
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (position>0){
            selectedRecordPosition=position-1;
            showDialog();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        blackListDAO = new BlackListDAO(this);
        blocklist = blackListDAO.getAllBlackList();
        if (blocklistitems.getChildCount() > 1)
            blocklistitems.removeFooterView(blocklistitems.getChildAt(blocklistitems.getCount() - 1));
        blocklistitems.setAdapter(new CustomArrayAdapter(this, R.layout.contents, blocklist));
        populateNoRecordMsg();
    }
    private void showDialog(){
        final AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are want to delete..?");
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    blackListDAO.delete(blocklist.get(selectedRecordPosition));
                    blocklist.remove(selectedRecordPosition);
                    blocklistitems.invalidateViews();
                    selectedRecordPosition=-1;
                    populateNoRecordMsg();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();
    }
/////contacts
//    public void selectFromContact(View view) {
//        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
//    }

    public class CustomArrayAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;
        //private List<BlackList> records;
private List<BlackList> records;

        public CustomArrayAdapter(Context context, int resource, List objects) {
            super(context, resource,objects);
            this.records=objects;
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public View getView(int position, View converView, ViewGroup parent){
            if (converView==null){
                converView=inflater.inflate(R.layout.contents,parent,false);
            }
            final BlackList phoneNumber=records.get(position);
//            ((TextView)convertView.findViewById(R.id.serial_tv)).setText("" + (position +1));
//            ((TextView)convertView.findViewById(R.id.phone_number_tv)).setText(phoneNumber.phoneNumber);
            Log.e("Serilalnumber",""+position +1);
            Log.e("Number",phoneNumber.toString());
            ((TextView)converView.findViewById(R.id.numbertxtview)).setText(phoneNumber.phoneNumber);
            return converView;
        }
    }

    }





//}
