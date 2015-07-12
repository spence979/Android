package test.athena;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	
	Button Login, Cancel;
	EditText txtUserName, txtPassword;
	public static String GUserName;
	private dbAccess DBHelper = null;
	public Cursor UsersCursor = null;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //Reference the buttons and user inputs
        Login = (Button) findViewById(R.id.btnLogin);
        Cancel = (Button) findViewById(R.id.btnCancel);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        
        //Set OnClick Listeners
        Login.setOnClickListener(this);
        Cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Login.this.finish();
			}
        });
    }
    public void LoadStaff(){
    	DBHelper = new dbAccess(this);
    	//Check to make sure database exists
    	DBHelper.createDatabase();
    	//Make the database read for queries
    	DBHelper.openDatabase();
    	//Submit the query and save results
		UsersCursor = DBHelper.getCursor("tblStaff", null, null, null, null);
    }
    
    public void onClick(View arg0) {
    	switch (arg0.getId()){
    		case R.id.btnLogin:
    			//Get user input from text boxes
    			String UserName = txtUserName.getText().toString();
    			String Password = txtPassword.getText().toString();
    			String User = "";
    			
    			try{
    				//Populate the cursor with information form the database
    				LoadStaff();
    				//Pass cursor and user inputs to check if their details match and return value
    				User = DBHelper.checkUserDetails(UsersCursor, UserName, Password);
    				//Check that Login details are correct by checking staff members name is not equal to nothing 
    				if (!(User.toString().equals("")))
    				{
    					//Store staff members name so it can be used in other activities
    					GUserName = User.toString();
    					//Setup intent to call/open new activity
    					txtUserName.setText("");
    					txtPassword.setText("");
    	    			Intent i = new Intent("android.intent.action.MMENU");
    	    			//Start new activity as defined in i
    	    			startActivity(i);
    	    			//Close database cursor and database
    	    			UsersCursor.close();
    	    			DBHelper.close();
    				}
    				else
    				{
    					//If login details are incorrect display error message
    					Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
    					txtUserName.setText("");
    					txtPassword.setText("");
    					txtUserName.isSelected();
    				}
    			}catch (Exception e){
    				//If error found in database connection display result
					Dialog d = new Dialog(this);
					d.setTitle("Error!");
					TextView tv = new TextView(this);
					tv.setText(e.toString());
					d.setContentView(tv);
					d.show();
    			}
    			break;
    	}

    }
}