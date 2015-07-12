package test.athena;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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
import android.widget.Spinner;
import android.widget.TextView;

public class tabActivity_Demographic extends Activity {
	private dbAccess DBHelper = null;
	private Cursor PatientCursor = null;
	
	public static TextView HC, FName,LName, DAge, DateOfBirth, Age,  HouseNo, Address1, Address2, County, PostCode,TelNo, MobileNo, NOK, NOKC;
	public static Spinner Gender, Marital;
	public static Date DOB;
    public static TextView mDateDisplay;
    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demographics);
        
        String SelectedPatient;
        
        HC = (TextView) findViewById(R.id.txtHC);
        
        FName = (TextView) findViewById(R.id.txtFName);
        LName = (TextView) findViewById(R.id.txtLName);
        DateOfBirth = (TextView) findViewById(R.id.txtDateOfBirth);
        Age = (TextView) findViewById(R.id.txtAge);
        Gender = (Spinner) findViewById(R.id.spinGender);
        Marital =  (Spinner) findViewById(R.id.spinMarital);
        HouseNo = (TextView) findViewById(R.id.txtHouseNo);
        Address1 = (TextView) findViewById(R.id.txtAddress1);
        Address2 = (TextView) findViewById(R.id.txtAddress2);
        PostCode = (TextView) findViewById(R.id.txtPostCode);
        TelNo = (TextView) findViewById(R.id.txtTelNo);
        MobileNo = (TextView) findViewById(R.id.txtMobileNo);
        NOK =  (TextView) findViewById(R.id.txtNOK);
        NOKC =  (TextView) findViewById(R.id.txtNOKC);
        mDateDisplay = (TextView) findViewById(R.id.txtDateOfBirth);
        
        Spinner Gender = (Spinner) findViewById(R.id.spinGender);
        ArrayAdapter<CharSequence> GenderAdapter = ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_item);
        GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(GenderAdapter);
        
        Spinner MaritalStatus = (Spinner) findViewById(R.id.spinMarital);
        ArrayAdapter<CharSequence> MarriedAdapter = ArrayAdapter.createFromResource(this, R.array.Marital, android.R.layout.simple_spinner_item);
        MarriedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MaritalStatus.setAdapter(MarriedAdapter);
        
        //Check if User is adding a new patient
        if (Activity_AddPatient.active == true){
        	NewPaitent();
        }
    	SelectedPatient = Activity_PatientInformation.SelectedPatient;
    	//Check if User has selected a existing patient
        if (Activity_AddPatient.active == false){
        	int SelectedPatientId = Integer.parseInt(SelectedPatient);
        	SelectedPatient(SelectedPatientId);
        }

        mDateDisplay.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				showDialog(DATE_DIALOG_ID);
				return false;
			}
        });
	}
	private void SelectedPatient(int SelectedPatientID){

		PatientCursor = Activity_PatientInformation.GetCursorData.getCursor(null);
    	HC.setText(PatientCursor.getString(PatientCursor.getColumnIndex("HC")));		//Add Health and Care to database
    	HC.setEnabled(false);
    	Activity_AddPatient.PatientID = HC.getText().toString();
        FName.setText(PatientCursor.getString(PatientCursor.getColumnIndex("FName")));
        LName.setText(PatientCursor.getString(PatientCursor.getColumnIndex("LName")));
        if (PatientCursor.getString(PatientCursor.getColumnIndex("gender")).equals("Female")){
        	Gender.setSelection(0);
        }else{
        	Gender.setSelection(1);
        }
        DateOfBirth.setText(PatientCursor.getString(PatientCursor.getColumnIndex("dateOfBirth")));
        Age.setText(PatientCursor.getString(PatientCursor.getColumnIndex("age")));
        if (PatientCursor.getString(PatientCursor.getColumnIndex("maritalStatus")).equals("Married")){
        	Marital.setSelection(1);
        }else if (PatientCursor.getString(PatientCursor.getColumnIndex("maritalStatus")).equals("Single"))
        {
        	Marital.setSelection(2);
        }
        else if (PatientCursor.getString(PatientCursor.getColumnIndex("maritalStatus")).equals("Divorced"))
        {
        	Marital.setSelection(3);
        }
        else if (PatientCursor.getString(PatientCursor.getColumnIndex("maritalStatus")).equals("Widowed"))
        {
        	Marital.setSelection(4);
        }else{
        	Marital.setSelection(0);
        }
        Marital.setContentDescription(PatientCursor.getString(PatientCursor.getColumnIndex("maritalStatus")));
        HouseNo.setText(PatientCursor.getString(PatientCursor.getColumnIndex("houseNo")));
        Address1.setText(PatientCursor.getString(PatientCursor.getColumnIndex("address1")));
        Address2.setText(PatientCursor.getString(PatientCursor.getColumnIndex("address2")));

        PostCode.setText(PatientCursor.getString(PatientCursor.getColumnIndex("postCode")));
        TelNo.setText(PatientCursor.getString(PatientCursor.getColumnIndex("telNo")));
        MobileNo.setText(PatientCursor.getString(PatientCursor.getColumnIndex("mobileNo")));
        NOK.setText(PatientCursor.getString(PatientCursor.getColumnIndex("nextOfKin")));
        NOKC.setText(PatientCursor.getString(PatientCursor.getColumnIndex("nextOfKinTel")));
	}

	private void NewPaitent(){
        Gender.setContentDescription("Please Select");
        Marital.setContentDescription("Please Select");
	}
	//Work Out Patient Age
	private void Age(int yearDOB, int monthDOB, int dayDOB){
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        java.util.Date date = new java.util.Date();
        int thisYear = Integer.parseInt(dateFormat.format(date));

        dateFormat = new SimpleDateFormat("MM");
        date = new java.util.Date();
        int thisMonth = Integer.parseInt(dateFormat.format(date));

        dateFormat = new SimpleDateFormat("dd");
        date = new java.util.Date();
        int thisDay = Integer.parseInt(dateFormat.format(date));

        int age = thisYear - yearDOB;

        if(thisMonth < monthDOB){
        age = age - 1;
        }
        if(thisMonth == monthDOB && thisDay < dayDOB){
        age = age - 1;
        }
        Age.setText(Integer.toString(age));
	}
    // updates the date in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(new StringBuilder()
                    // Month is 0 based so add 1
        			.append(mDay).append("/")
                    .append(mMonth + 1).append("/")
                    .append(mYear).append(" "));
        Age(mYear,mMonth,mDay);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
}     