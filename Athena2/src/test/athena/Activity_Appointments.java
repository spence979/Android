package test.athena;


import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_Appointments extends Activity {
	
	Cursor Appointments = null, Patients = null;
	ListView AppointmentList;
	dbAccess DBHelper = null;
	int NumberOfAppointment = 0;
	String TodaysDate = null;
	Date DDate, TDate;
	public final ArrayList<String> ListAppointmentList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointments);
		
		Calendar c = Calendar.getInstance(); 
		TodaysDate =  Integer.toString(c.get(Calendar.DATE)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/" + Integer.toString(c.get(Calendar.YEAR));
    	
		
		AppointmentList =(ListView) findViewById(R.id.lstAppointments);
		AppointmentList.setOnItemClickListener(AppointmentListClick);
		LoadAppointments();
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.actionbar_appointment, menu);
    	return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getOrder())
		{
			case 1:
				Activity_Appointments.this.finish();
			return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void LoadAppointments(){
    	DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();
    	Appointments = DBHelper.getCursor("tblAppointments", null , null, null, null);
    	startManagingCursor(Appointments);
    	LoadList();
	}
	private void LoadPatients(String HC){
    	DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();
    	Patients = DBHelper.getCursor("tblPatients", "HC='" + HC + "'", null, null, null);
    	startManagingCursor(Patients);
    	Patients.moveToPosition(0);
		
	}
	private void LoadList(){
		NumberOfAppointment = Appointments.getCount();
		ListAppointmentList.clear();
		if(!(NumberOfAppointment == 0)){
			//Appointments.moveToFirst();
			for (Appointments.moveToFirst(); !Appointments.isAfterLast(); Appointments.moveToNext()){
				String ID = Appointments.getString(Appointments.getColumnIndex("patientID"));
				LoadPatients(ID);
				ListAppointmentList.add(Appointments.getString(Appointments.getColumnIndex("date")) + " | " + Patients.getString(Patients.getColumnIndex("FName")) + " " + Patients.getString(Patients.getColumnIndex("LName")));
			}
		}	

    	final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListAppointmentList);
        AppointmentList.setAdapter(aa);
	}
    private AdapterView.OnItemClickListener AppointmentListClick = new AdapterView.OnItemClickListener() {
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	{
    		Appointments.moveToPosition(position);
    		String ID = Appointments.getString(Appointments.getColumnIndex("patientID"));
			LoadPatients(ID);
			String Name = Patients.getString(Patients.getColumnIndex("FName")) + " " + Patients.getString(Patients.getColumnIndex("LName"));
			String Address = Patients.getString(Patients.getColumnIndex("houseNo")) + "\n" + Patients.getString(Patients.getColumnIndex("address1")) + "\n" + Patients.getString(Patients.getColumnIndex("address2")) + "\n" + Patients.getString(Patients.getColumnIndex("postCode"));
			
        	AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Appointments.this);
			builder.setTitle("Appointment Details");
			builder.setMessage(Name.toString() + "\n" + Address.toString())
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
	        			dialog.dismiss();
		           }
		       })
		       .setNegativeButton("Complete Appointment", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   	String RowID = Appointments.getString(Appointments.getColumnIndex("_id"));
	                	boolean Removed = false;
		        	   	DBHelper = new dbAccess(Activity_Appointments.this);
	                	DBHelper.openDatabase();
	                	Removed = DBHelper.Remove(RowID, "tblAppointments");
	                	
	                	LoadAppointments();
		                dialog.dismiss();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();	
    		
    	}
    };
	
}
