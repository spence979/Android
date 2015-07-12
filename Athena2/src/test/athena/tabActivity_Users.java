package test.athena;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class tabActivity_Users extends Activity implements OnClickListener{

	Button btnAdd, btnRemove, btnChange, btnDeletePatient;
	private dbAccess DBHelper = null;
	public Cursor DatabaseUsers, PatientCursor;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout_users);

		
		btnChange = (Button) findViewById(R.id.btnChangePassword);
		btnAdd = (Button) findViewById(R.id.btnAddUser);
		btnRemove = (Button) findViewById(R.id.btnDeleteUser);
		btnDeletePatient = (Button) findViewById(R.id.btnDeletePatient);
		
		
		
		btnChange.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		btnRemove.setOnClickListener(this);
		btnDeletePatient.setOnClickListener(this);
		
		UserDatabase();
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.actionbar_administration, menu);
    	return true;
    }
	
	public void UserDatabase(){
    	DBHelper = new dbAccess(tabActivity_Users.this);
    	DBHelper.openDatabase();
    	DatabaseUsers = DBHelper.getCursor("tblStaff", null, null, null, null);
	}
	protected Dialog onCreateDialog(int id) {
    	final Dialog Question = new Dialog(tabActivity_Users.this);
    	final Button Cancel = (Button) Question.findViewById(R.id.btnDialogCancel);
    	
	    switch (id) {
	    case 1:
	    	Question.setContentView(R.layout.dialog_adduser);
	    	Question.setTitle("Add New User");
	    	Question.setCancelable(true);
            //there are a lot of settings, for dialog, check them all out!
            
            final EditText DialogFName = (EditText) Question.findViewById(R.id.txtAFName);
            final EditText DialogLName = (EditText) Question.findViewById(R.id.txtALName);
            final EditText DialogUserName = (EditText) Question.findViewById(R.id.txtAUsername);
            final EditText DialogNewPassword = (EditText) Question.findViewById(R.id.txtAPassword);
            
            DialogLName.setOnFocusChangeListener(new OnFocusChangeListener(){

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if(!hasFocus){
						final String FirstName = DialogFName.getText().toString();
						final String LastName = DialogLName.getText().toString();
						int Index = 0;
						String UsernameString = FirstName + "." + LastName;
						while(CheckUserName(UsernameString)){
							Index ++;
							UsernameString = FirstName + "." + LastName + Index;
						}
						DialogUserName.setText(UsernameString);
					}
				}
            });
            
            
            //set up button
            Button AddUser = (Button) Question.findViewById(R.id.btnDialogAdd);
            AddUser.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) 
            	{
                final String UserName = DialogUserName.getText().toString();
                final String Password = DialogNewPassword.getText().toString();
                final String FirstName = DialogFName.getText().toString();
                final String LastName = DialogLName.getText().toString();
            	if(!(FirstName.toString().equals("")) || !(LastName.toString().equals("")) || !(Password.toString().equals("")))
            	{
	            	DBHelper = new dbAccess(tabActivity_Users.this);
	            	DBHelper.openDatabase();
	            	DBHelper.AddUser(UserName, Password,FirstName, LastName);
	            	DBHelper.close();
	            	UserDatabase();
	            	Question.dismiss();
            	}else{
            		Toast.makeText(tabActivity_Users.this, "You Must Complete All Fields", Toast.LENGTH_LONG).show();
            	}
            	}
            });
            Button AddCancel = (Button) Question.findViewById(R.id.btnDialogCancel);
            AddCancel.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) {
            	Question.dismiss();
                }
            });
      
            Question.show();
            break;
	    case 2:
	    	//Remove User
	    	Question.setContentView(R.layout.dialog_removeuser);
	    	Question.setTitle("Remove User");
	    	Question.setCancelable(true);

            startManagingCursor(DatabaseUsers);

            String[] column = new String[] { "username" };
            int[] to = new int[] { android.R.id.text1 };
            Spinner spnClients = (Spinner) Question.findViewById(R.id.spinUsers);
            final SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, DatabaseUsers, column, to);
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //UserDatabase();
            spnClients.setAdapter(mAdapter);
            
            spnClients.setOnItemSelectedListener( new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					DatabaseUsers.moveToPosition(arg2);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					
					
				}
            });
            //set up button
            Button Remove = (Button) Question.findViewById(R.id.btnDialogRemoveUser);
            Remove.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) 
            	{
            	AlertDialog.Builder builder = new AlertDialog.Builder(tabActivity_Users.this);
				
				builder.setMessage("Are You Sure You Want To Remove The User?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   	boolean Removed;
		        			int FirstNameColumn = DatabaseUsers.getColumnIndex("_id");
		        			String ContactID = DatabaseUsers.getString(FirstNameColumn);
		                	DBHelper = new dbAccess(tabActivity_Users.this);
		                	DBHelper.openDatabase();
		        			Removed = DBHelper.Remove(ContactID, "tblStaff");
		        			if (Removed = true){
		        				Toast.makeText(tabActivity_Users.this, "User Removed", Toast.LENGTH_LONG).show();
		        				UserDatabase();
		        				mAdapter.notifyDataSetChanged();
		        				dialog.dismiss();
		        				Question.dismiss();
		        				
		        			}else{
		        				Toast.makeText(tabActivity_Users.this, "User NOT Removed", Toast.LENGTH_LONG).show();
		        			}
		        			

			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.dismiss();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();	
                
            	}
            });
            Button Cancel1 = (Button) Question.findViewById(R.id.btnDialogRemoveCancel);
            Cancel1.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) {
            	Question.dismiss();
                }
            });
             
            Question.show();
            
	    	break;
	    case 3:
	    	Question.setContentView(R.layout.dialog_changepassword);
	    	Question.setTitle("Change Password");
	    	Question.setCancelable(true);
            //there are a lot of settings, for dialog, check them all out!
            
            final EditText OldPassword = (EditText) Question.findViewById(R.id.txtOldPassword);
            final EditText NewPassword = (EditText) Question.findViewById(R.id.txtNewPassword);
            final EditText ConfirmPassword = (EditText) Question.findViewById(R.id.txtConfirmPassword);
            
            //set up button
            Button ChangePassword = (Button) Question.findViewById(R.id.btndialogChangePassword);
            ChangePassword.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) 
            	{
	            	if (NewPassword.getText().toString().equals(ConfirmPassword.getText().toString()) == true);
	            	{
		            	boolean PasswordChanged = false;
		            	DBHelper = new dbAccess(tabActivity_Users.this);
		            	DBHelper.openDatabase();
		            	PasswordChanged = DBHelper.ChangePassword(Login.GUserName, OldPassword.getText().toString(), NewPassword.getText().toString(), DatabaseUsers);
		            	DBHelper.close();
		            	if (PasswordChanged == true){
		            		Toast.makeText(tabActivity_Users.this, "Password Changed", Toast.LENGTH_LONG).show();
		            		Question.dismiss();
		            	}else{
		            		Toast.makeText(tabActivity_Users.this, "Password NOT Changed", Toast.LENGTH_LONG).show();
		            		OldPassword.setText("");
		            		NewPassword.setText("");
		            		ConfirmPassword.setText("");
		            	}
	            	}
            	}
            });
            Button CancelChange = (Button) Question.findViewById(R.id.btnDialogCancel);
            CancelChange.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) {
            	Question.dismiss();
                }
            });
             
            Question.show();
	    	break;
	    case 4:		//Delete Patient
	    	Question.setContentView(R.layout.dialog_removepatient);
	    	Question.setTitle("Detele Patient");
	    	Question.setCancelable(true);
            //there are a lot of settings, for dialog, check them all out!
            
            final EditText PatientCareNumber = (EditText) Question.findViewById(R.id.txtDeletePatientID);

            
            //set up button
            Button DeletePatient = (Button) Question.findViewById(R.id.btnDialogRemovePatient);
            DeletePatient.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) 
            	{
            	if(!(PatientCareNumber.getText().toString().equals(""))){
	            	DBHelper = new dbAccess(tabActivity_Users.this);
	            	DBHelper.openDatabase();
	            	PatientCursor = DBHelper.getCursor("tblPatients", "HC='" + PatientCareNumber.getText().toString()  + "'", null, null, null);
	            	if (!(PatientCursor.getCount() == 0)){
		            	PatientCursor.moveToPosition(0);
		            	AlertDialog.Builder builder = new AlertDialog.Builder(tabActivity_Users.this);
		                
						builder.setMessage("Are you sure you want to delete " +  PatientCursor.getString(PatientCursor.getColumnIndex("FName")) + " " + PatientCursor.getString(PatientCursor.getColumnIndex("LName")) + " " + PatientCursor.getString(PatientCursor.getColumnIndex("HC")))
					       .setCancelable(false)
					       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   	boolean Removed;
				        			int FirstNameColumn = PatientCursor.getColumnIndex("_id");
				        			String ID = PatientCursor.getString(FirstNameColumn);
		
				        			Removed = DBHelper.Remove(ID, "tblPatients");
				        			if (Removed = true){
				        				Toast.makeText(tabActivity_Users.this, "Patient Removed", Toast.LENGTH_LONG).show();
				        				
				        				dialog.dismiss();
				        				Question.dismiss();
				        			}
					           }
					       })
					       .setNegativeButton("No", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                dialog.dismiss();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
	            	}else{
	            		Toast.makeText(tabActivity_Users.this, "No Patient Found With That H&C Number", Toast.LENGTH_LONG).show();
	            	}
            	}
            	else{
            		Toast.makeText(tabActivity_Users.this, "You Must Enter a H&C Number", Toast.LENGTH_LONG).show();
            	}
            	}
            });
            Button ChangeDelete = (Button) Question.findViewById(R.id.btnDialogRemovePatientCancel);
            ChangeDelete.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) {
            	Question.dismiss();
                }
            });
             
            Question.show();
	    	break;
	    default:
	        //dialog = null;
	    }
	    //return dialog;
		return null;
	}
	public Dialog removeUser() {
		Dialog dialog = new Dialog(this);

	    dialog.setContentView(R.layout.dialog_adduser);
	    dialog.setTitle("Remove User");

	    return dialog;
	}
	public Dialog changePassword() {
		Dialog dialog = new Dialog(this);

	    dialog.setContentView(R.layout.dialog_adduser);
	    dialog.setTitle("Change Password");

	    return dialog;
	}
	public Dialog DeletePatient() {
		Dialog dialog = new Dialog(this);
	    dialog.setContentView(R.layout.dialog_adduser);
	    dialog.setTitle("Delete Patient");
	    return dialog;
	}
	
    public void onClick(View arg0) {
    	switch (arg0.getId()){
    		case R.id.btnAddUser:
    			showDialog(1);
    			break;
    		case R.id.btnDeleteUser:
    			showDialog(2);
    			break;
    		case R.id.btnChangePassword:
    			showDialog(3);
    			break;
    		case R.id.btnDeletePatient:
    			showDialog(4);
    			break;
    	}

    }
    private boolean CheckUserName(String UserName){
        DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();
		Cursor PatientCursor = DBHelper.getCursor("tblStaff", "username='" + UserName + "'" ,  null, null, null);
		startManagingCursor(PatientCursor);
		
    	Boolean IDExists = false;
    	if(PatientCursor.getCount() > 0)
    	{
    		IDExists = true;
    	}
		 return (IDExists);
    }
    
	public boolean onOptionsItemSelected(MenuItem item)
	{
		DBHelper = new dbAccess(this);
		switch (item.getOrder())
		{
			case 1:
				DBHelper.TransferDatabase();
				return true;
			case 2:
				DBHelper.CopyNewDatabase();
				return true;
			case 3:
				tabActivity_Users.this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}
}