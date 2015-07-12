package test.athena;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class tabActivity_Contacts extends Activity {
	
	ListView lstContacts;
	private dbAccess DBHelper = null;
	private Cursor ContactCursor = null;
	private PatientAdapter adapter = null;
	int SelectedContact = 0; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_contacts);
        
    	DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();

    	ContactCursor = DBHelper.getCursor("tblPatientContact", "patientID='"+ Activity_AddPatient.PatientID +"'"  ,null,  null, "contactID ASC");
    	startManagingCursor(ContactCursor);

    	ListView contactsListView = (ListView)findViewById(R.id.lstContacts);
    	adapter = new PatientAdapter(ContactCursor);
    	contactsListView.setAdapter(adapter);
    	contactsListView.setOnItemClickListener(ContactClick);
	}
	
    private AdapterView.OnItemClickListener ContactClick = new AdapterView.OnItemClickListener() {
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    	{
    		ContactCursor.moveToPosition(position);
    		
    		Activity_AddContact.PatientID = (ContactCursor.getString(ContactCursor.getColumnIndex("patientID")));
    		Activity_AddContact.ContactID = (ContactCursor.getString(ContactCursor.getColumnIndex("contactID")));
    		
    		Activity_AddContact.ViewContact = true;
    		
    		//Activity_AddContact.NewPaitent = false;
    		Activity_AddContact.SelectedPatient = false;
    		
    		Intent i = new Intent(tabActivity_Contacts.this,Activity_AddContact.class);
    		startActivity(i);
    	}
    };
	
	class PatientAdapter extends CursorAdapter{
		PatientAdapter(Cursor c) {
			super(tabActivity_Contacts.this, c);
		}
		public void bindView (View row, Context ctxt, Cursor c){
			PatientListHolder holder = (PatientListHolder)row.getTag();
			holder.populateFrom(c, DBHelper);
			
		}
		@Override
		public View newView(Context ctxt, Cursor C, ViewGroup parent){
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.listlayout_contacts, parent, false);
			PatientListHolder holder = new PatientListHolder(row);
			row.setTag(holder);
			return(row);
		}
		}
	static class PatientListHolder{
		private TextView patient_ID = null;
		PatientListHolder(View row){
			patient_ID = (TextView)row.findViewById(R.id.txt_ID);
			
		}
		void populateFrom(Cursor c, dbAccess r) {
			patient_ID.setText(r.GetContactID(c));
		}
	}

}
