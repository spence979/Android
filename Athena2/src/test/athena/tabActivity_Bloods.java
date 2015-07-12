package test.athena;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class tabActivity_Bloods extends Activity {
	
	ListView lstContacts;
	private dbAccess DBHelper = null;
	private Cursor BloodCursor = null;	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_bloodtests);
        
        ListView lstBloods = (ListView) findViewById(R.id.lstBloods);
        ArrayList<String> BloodResults = new ArrayList<String>();
		DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();

    	BloodCursor = DBHelper.getCursor("tblBloodTests", "patientID='"+ Activity_AddPatient.PatientID +"'" ,null,  null, null);
    	//Load cursor based on selected patientID
    	startManagingCursor(BloodCursor);
    	
    	if(BloodCursor != null){		//Check the cursor has values
    		for (BloodCursor.moveToFirst(); !BloodCursor.isAfterLast(); BloodCursor.moveToNext()){
    			//For loop used to move through the cursor and check the cursor is not a the last row.
    			String ContactDate = BloodCursor.getString(0);
    			String Na = BloodCursor.getString(4);
    			String K = BloodCursor.getString(5);
    			String Urea = BloodCursor.getString(6);
    			String Creat = BloodCursor.getString(7);
    			String Glucose = BloodCursor.getString(8);
    			String Hb = BloodCursor.getString(9);
    			String WCC = BloodCursor.getString(10);
    			String Platelets = BloodCursor.getString(11);
    			String LFT = BloodCursor.getString(12);
    			String TFT = BloodCursor.getString(13);
    			
    			String StringValue = ("Contact Date: " + ContactDate + " - Na:" + Na + " - K:" + K + " - Urea:" + Urea +
    					" - Creat:" + Creat + " - Glucose:" + Glucose + " - Hb:" + Hb + " - WCC:" + WCC + " - Platelets:" + Platelets +
    					" - LFT:" + LFT + " - TFT:" + TFT);
    			//Create string based on values from database
    			BloodResults.add(StringValue.toString());
    			//Add String of values into array
    			}
    	}
    	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, BloodResults);
        lstBloods.setAdapter(arrayAdapter); 
        //Populate list based on values inside the Array

	}
}
