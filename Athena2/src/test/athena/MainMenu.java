package test.athena;

import com.bugsense.trace.BugSenseHandler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity{
	
ListView list, PatientList;
Button test;
public final static String ID_EXTRA = "test.athena.mainmenu._ID";

private dbAccess DBHelper = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmenu);
        
        BugSenseHandler.setup(this, "c2bc7e6a"); 
        
        ListView listView = (ListView) findViewById(R.id.lstOptions);

    	String[] values = new String[] { "Patients", "Appointments", "Administration","Logout" };
    	
    	MySimpleArrayAdapter adapterWI = new MySimpleArrayAdapter(this, values);
    	listView.setAdapter(adapterWI);
    	
    	listView.setOnItemClickListener(onListClick);
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_main, menu);
    	return true;
    }
    private AdapterView.OnItemClickListener PatientListClick = new AdapterView.OnItemClickListener() {
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	{
    		tabActivity_Medication.NewPatient = false;
    		Intent i = new Intent(MainMenu.this,Activity_PatientInformation.class);
    		i.putExtra(ID_EXTRA, String.valueOf(position));
    		startActivity(i);
    	}
    };
    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	{
    		Intent i = new Intent();
    		switch(position){
    			case 0:
    				tabActivity_Medication.NewPatient = false;
    				i = new Intent("android.intent.action.PINFO");
    	    		startActivity(i);
    				break;
    			case 1:
    				tabActivity_Medication.NewPatient = false;
    				i = new Intent("android.intent.action.AAPPOINTMENT");
    	    		startActivity(i);
    				break;
    			case 2:
    				tabActivity_Medication.NewPatient = false;
    				i = new Intent("android.intent.action.USERS");
    				startActivity(i);
    				break;
    			case 3:
    				MainMenu.this.finish();
    		}
    	}
    };

	public boolean onOptionsItemSelected(MenuItem item)
	{
		DBHelper = new dbAccess(this);
		switch (item.getOrder())
		{
			case 1:
				tabActivity_Medication.NewPatient = true;
				Intent i = new Intent();
				i = new Intent("android.intent.action.APATIENT");
	    		startActivity(i);
				return true;
			case 2:
				DBHelper.TransferDatabase();
				return true;
			case 3:
				DBHelper.CopyNewDatabase();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}

}