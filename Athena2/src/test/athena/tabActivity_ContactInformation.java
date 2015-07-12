package test.athena;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class tabActivity_ContactInformation extends Activity {
	
	EditText txtContactDate, txtFollowUpDate, txtBy;
	Spinner spnContactReason, spnAt;
	public static String ContactReason = "", ContactBy = "",ContactDate = "", FollowUpDate = "", ContactAt = "";
	
    private int mYear;
    private int mMonth;
    private int mDay;
    
    private dbAccess DBHelper = null;
    private Cursor ContactCursor = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout_contactinfo);
		
		txtContactDate = (EditText) findViewById(R.id.txtContactDate);
		spnContactReason = (Spinner) findViewById(R.id.spnContactReason);
		txtBy = (EditText) findViewById(R.id.txtContactBY);
		txtFollowUpDate = (EditText) findViewById(R.id.txtFollowUp);
		spnAt = (Spinner) findViewById(R.id.spnContactAt);
		
		txtContactDate.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				showDialog(1);
				return false;
			}
		});
		txtFollowUpDate.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				showDialog(2);
				return false;
			}
		});
	
        ArrayAdapter<CharSequence> ContactReason = ArrayAdapter.createFromResource(this, R.array.ContactType, android.R.layout.simple_spinner_item);
        ContactReason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnContactReason.setAdapter(ContactReason);
        
        txtBy.setText(Login.GUserName.toString());
        
        ArrayAdapter<CharSequence> ContactAt = ArrayAdapter.createFromResource(this, R.array.ContactAt, android.R.layout.simple_spinner_item);
        ContactAt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAt.setAdapter(ContactAt);
	}

	@Override
    protected Dialog onCreateDialog(int id) {
    	Calendar c = Calendar.getInstance();
    	int DYear = c.get(Calendar.YEAR);
    	int DMonth = c.get(Calendar.MONTH);
    	int DDay = c.get(Calendar.DAY_OF_MONTH);
    	switch (id) {
        case 1:
        	return new DatePickerDialog(this, CDateSetListener, DYear, DMonth, DDay);
        case 2:
    		return new DatePickerDialog(this, FDateSetListener, DYear, DMonth, DDay);

        }
        return null;
    }
	
    private DatePickerDialog.OnDateSetListener CDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplayContact();
        }
    };
    private DatePickerDialog.OnDateSetListener FDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplayFollowUp();
        }
    };
	private void updateDisplayContact() {
		txtContactDate.setText(new StringBuilder().append(mDay).append("/") .append(mMonth + 1).append("/").append(mYear).append(" "));
		ContactDate = txtContactDate.getText().toString();
    }
	private void updateDisplayFollowUp() {
		txtFollowUpDate.setText(new StringBuilder().append(mDay).append("/") .append(mMonth + 1).append("/").append(mYear).append(" "));
		FollowUpDate = txtFollowUpDate.getText().toString();
    }
}
