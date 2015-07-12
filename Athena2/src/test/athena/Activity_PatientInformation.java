
package test.athena;

import java.util.Calendar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;


public class Activity_PatientInformation extends TabActivity implements OnClickListener {

	private TabHost mTabHost;
	private dbAccess DBHelper = null;
	public Cursor ourCursor = null;
	private PatientAdapter adapter = null;
	public static boolean PatientInfo = false;
	public static String SelectedPatient = null;
	TextView lblPatientName, lblHCN;
	Button btnAddContact, btnUpdateInfo;
	ListView ListOfPatients;
	private String PatientID;
	
    private int mYear;
    private int mMonth;
    private int mDay;
    /** Called when the activity is first created. */
	
    protected void onStop(){
    	super.onStop();
    	PatientInfo = false;
    }
    protected void onStart(){
    	super.onStart();
    	PatientInfo = true;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_main);

		GetCursor(null);

        ListOfPatients = (ListView)findViewById(R.id.lstPatientList);
        lblPatientName = (TextView) findViewById(R.id.lblPatientName);
        lblHCN = (TextView) findViewById(R.id.txtHCNo);
        btnAddContact = (Button) findViewById(R.id.btnAddContact);	
        btnAddContact.setOnClickListener(this);
        btnAddContact.setEnabled(false);
        btnUpdateInfo = (Button) findViewById(R.id.btnUpdate);	
        btnUpdateInfo.setOnClickListener(this);
        btnUpdateInfo.setEnabled(false);
        ListOfPatients.setOnItemClickListener(PatientListClick);
        
        
        SelectedPatient = getIntent().getStringExtra(MainMenu.ID_EXTRA);
        
        if (SelectedPatient != null)
        {
        	SelectedPatient();
        }
        ListPatients();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.actionbar_selectedpatient, menu);
    	SearchView searchView = (SearchView)menu.findItem(R.id.mnu_search).getActionView();
        searchView.setOnQueryTextListener(queryListener);
        searchView.setOnCloseListener(new OnCloseListener(){
			@Override
			public boolean onClose() {
				GetCursor(null);
				ListPatients();
				return false;
			}
        });
    	return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
	{
    	AlertDialog.Builder Builder = new AlertDialog.Builder(this);
		switch (item.getOrder())
		{
		case 2:
			showDialog(1);
			break;
		case 3:
			Intent i = new Intent(Activity_PatientInformation.this,Activity_AddPatient.class);
			startActivity(i);
			return true;
		case 4:
			Activity_PatientInformation.this.finish();
			return true;
		}
		return false;
	}
    private String grid_currentQuery = null; //Hold The Search Info
    final private OnQueryTextListener queryListener = new OnQueryTextListener() {       
		@Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {            
                grid_currentQuery = null;
            } else {
                grid_currentQuery = newText;

            }   
            return false;
        }
        @Override
        public boolean onQueryTextSubmit(String query) {            
        	DBHelper.createDatabase();
        	DBHelper.openDatabase();
        	ourCursor = DBHelper.getCursor("tblPatients", "LName LIKE'%" + query + "%'",  null, null, "FName ASC");
    		if(ourCursor.getCount() != 0){
    			ListOfPatients = (ListView)findViewById(R.id.lstPatientList);
            	adapter = new PatientAdapter(ourCursor);
            	ListOfPatients.setAdapter(adapter);
    		}else{
    			Toast.makeText(Activity_PatientInformation.this, "No Paitent Found With The Name - " + query, Toast.LENGTH_LONG).show();
    		}
            return false;
        }
    };
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	//Get todays date
    	Calendar c = Calendar.getInstance();
    	int DYear = c.get(Calendar.YEAR);
    	int DMonth = c.get(Calendar.MONTH);
    	int DDay = c.get(Calendar.DAY_OF_MONTH);
    	switch (id) 
    	{
        	case 1:
        		//Call new instance of date picker dialog with todays date as the default.
        		return new DatePickerDialog(this, mDateSetListener, DYear, DMonth, DDay);
        }
		return null;
    }
    
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Collect the date from the date picker
            	mYear = year;				//Year
                mMonth = monthOfYear;		//Month
                mDay = dayOfMonth;			//Day
                updateDisplayAdmission();	//Call Add to database
            }
        };
    private void updateDisplayAdmission() {
    		final StringBuilder AppointMentDate = new StringBuilder().append(mDay).append("/") .append(mMonth + 1).append("/").append(mYear).append(" ");
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage("Are You Sure You Want To Add A Appointment for "+ lblPatientName.getText().toString() + " on " + AppointMentDate.toString()+"?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   AddAppointment(PatientID,AppointMentDate.toString());
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog ResetAlert = builder.create();
		ResetAlert.show();
        }
    
    private AdapterView.OnItemClickListener PatientListClick = new AdapterView.OnItemClickListener() {
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	{
    		GetCursorData.setCursor(ourCursor);
    		SelectedPatient = String.valueOf(position);
    		SelectedPatient();
    	}
    };
    
    private void ListPatients(){
        adapter = new PatientAdapter(ourCursor);
        ListOfPatients.setAdapter(adapter);
    }
    private void GetCursor(String Where){
        DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();
		ourCursor = DBHelper.getCursor("tblPatients", null,  null, null, "LName ASC");
		startManagingCursor(ourCursor);
    }
    
    private void SelectedPatient(){
    	int SelectedPatientId = Integer.parseInt(SelectedPatient);
    	ourCursor = dbAccess.getByID(ourCursor, SelectedPatientId);
    	lblPatientName.setText(DBHelper.getName(ourCursor));
    	lblHCN.setText(DBHelper.getID(ourCursor));
    	PatientID = (DBHelper.getID(ourCursor));
    	CreateTabs();
    }
    
    private void CreateTabs(){
    	//Declare Tabs
        mTabHost = getTabHost();
        mTabHost.setCurrentTab(0);
        //Make sure first is selected
        mTabHost.clearAllTabs();
        //Clear all tabs
        
        mTabHost.addTab(mTabHost.newTabSpec("Demographics").setIndicator("Demographics").setContent(new Intent(this, tabActivity_Demographic.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("BloodTests").setIndicator("Blood-Tests").setContent(new Intent(this, tabActivity_Bloods.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("Medication").setIndicator("Medication").setContent(new Intent(this, tabActivity_Medication.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("Contacts").setIndicator("Contacts").setContent(new Intent(this, tabActivity_Contacts.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        //Add tab view, Declare tab Identifier
        mTabHost.setCurrentTab(0);
        //Set the first tab as active 
        btnUpdateInfo.setEnabled(true);
        btnAddContact.setEnabled(true);
        //Enable both buttons now that a patient has been selected
        
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {         

            	int Pos = mTabHost.getCurrentTab();
            	switch(Pos){
            	case 0:	//Demographics
            		btnUpdateInfo.setEnabled(true);
            		break;
            	case 1:	//Blood Tests
            		//Disable update button now demographic tab is no longer active
            		btnUpdateInfo.setEnabled(false);
            		break;
            	case 2: //Medication Tab
            		//Disable update button now demographic tab is no longer active
            		btnUpdateInfo.setEnabled(false);
            		tabActivity_Medication.SelectedPatient = true;
            		Activity_AddContact.ViewContact = false;
            		break;
            	case 3:	//Contacts Tab
            		//Disable update button now demographic tab is no longer active
            		btnUpdateInfo.setEnabled(false);
            		tabActivity_Medication.SelectedPatient = false;
            		Activity_AddContact.ViewContact = true;
            		break;
            	}
            }       
        }); 
    }
	static class GetCursorData {
		   private static volatile Cursor OurCursorData;
		   
		   public static Cursor getCursor(Context context){
		      return OurCursorData;
		   }

		   public static void setCursor (Cursor ourCursor){
			   OurCursorData = ourCursor;
		   }
	}
  
        class PatientAdapter extends CursorAdapter{
        	PatientAdapter(Cursor c) {
        		super(Activity_PatientInformation.this, c);
        	}
        	public void bindView (View row, Context ctxt, Cursor c){
        		PatientHolder holder = (PatientHolder)row.getTag();
        		holder.populateFrom(c, DBHelper);
        	}
        	@Override
        	public View newView(Context ctxt, Cursor C, ViewGroup parent){
        		LayoutInflater inflater = getLayoutInflater();
        		View row = inflater.inflate(R.layout.custom_list1, parent, false);
        		PatientHolder holder = new PatientHolder(row);
        		row.setTag(holder);
        		return(row);
        	}
        }
        static class PatientHolder{
        	private TextView patient_ID = null;
        	private TextView patient_NAME = null;
        	PatientHolder(View row){
        		patient_ID = (TextView)row.findViewById(R.id.first_row);
        		patient_NAME = (TextView)row.findViewById(R.id.second_row);
        	}
        	void populateFrom(Cursor c, dbAccess r) {
        		patient_ID.setText(r.getName(c));
        		patient_NAME.setText(r.getID(c));
        		
        	}
        }
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()){
				case R.id.btnAddContact:
	   				Intent i = new Intent(Activity_PatientInformation.this,Activity_AddContact.class);
	   				startActivity(i);
	   				
		    	    Activity_AddContact.ViewContact = false;
		    	    Activity_AddContact.SelectedPatient = true;
		    	    Activity_AddContact.PatientID = PatientID;
		    	    PatientInfo = false;
    				break;
				case R.id.btnUpdate:
					UpdateInformation();
					break;
			}
		}
		private void UpdateInformation(){
	    	//Patient Information
	    	String HC = tabActivity_Demographic.HC.getText().toString();
	    	String FName = tabActivity_Demographic.FName.getText().toString();
	    	String LName = tabActivity_Demographic.LName.getText().toString();
	    	String Gender = tabActivity_Demographic.Gender.getSelectedItem().toString();
	    	String DOB = tabActivity_Demographic.DateOfBirth.getText().toString();
	    	String Age = tabActivity_Demographic.Age.getText().toString();
	    	String Marital = tabActivity_Demographic.Marital.getSelectedItem().toString();
	    	String HouseNo = tabActivity_Demographic.HouseNo.getText().toString();
	    	String Address1 = tabActivity_Demographic.Address1.getText().toString();
	    	String Address2 = tabActivity_Demographic.Address2.getText().toString();
	    	String PostCode = tabActivity_Demographic.PostCode.getText().toString();
	    	String TelephoneNo = tabActivity_Demographic.TelNo.getText().toString();
	    	String MobileNo = tabActivity_Demographic.MobileNo.getText().toString();
	    	String NOK =  tabActivity_Demographic.NOK.getText().toString();
	    	String NOKC = tabActivity_Demographic.NOKC.getText().toString();
	 
	    	DBHelper = new dbAccess(this);
        	DBHelper.openDatabase();
    		DBHelper.UpdatePatient(HC, FName, LName, Gender, DOB, Age, Marital, HouseNo , Address1, Address2, PostCode, TelephoneNo , MobileNo, NOK, NOKC); 
    		Toast.makeText(this, "Patient Information Has Been Updated", Toast.LENGTH_LONG).show();
    		GetCursor(null);
    		ListPatients();
		}
	
		private void AddAppointment(String PatientID, String AppointMentDate){
        	DBHelper = new dbAccess(this);
        	DBHelper.openDatabase();
    		if(!(AppointMentDate.toString().equals("Unknown"))){
    			DBHelper.AddAppointment(PatientID.toString(), AppointMentDate.toString());
    			Toast.makeText(this, "Appointment Added", Toast.LENGTH_LONG).show();
    		}
    		DBHelper.close();
		}
		
    }
    
