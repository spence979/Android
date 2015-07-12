package test.athena;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;


public class tabActivity_Discharge extends Activity {
	
	public static String Deso = "Unknown", HomeVisit = "false", DateOfFirstVisit = "Unknown", AdmissionDate = "Unknown", DischargeDate = "Unknown", Consultant = "Unknown", Ward = "Unknown", DischargeComments = "Unknown", ClinicAppt = "false";
	EditText txtDischargeComments, txtAdmissionDate, txtDischargeDate, txtWard, txtConsultant, txtDateOfFirstVisit, txtDesOther, txtMedicationName, txtOtherComments, txtMedicationDose, txtMedicationFreq, txtOtherDes, txtFirstVisit;
	CheckBox chkDHome, chkDOther, chkHVisitY, chkHVisitN, chkCApptN, chkCApptY, chkBNA, chkBK, chkBUrea, chkBCreat, chkBGlucose, chkBHb, chkBWCC, chkBPlatelets, chkBTFT, chkBLFT;
	public static String BNa = "-0", BK = "-0", BUrea = "-0", BCreat = "-0", BGlucose = "-0", BHb = "-0", BWCC = "-0", BPlatelets = "-0", BTFT = "-0", BLFT = "-0";
	private dbAccess DBHelper = null;
	private Cursor ContactCursor = null;
	private Cursor BloodCursor = null;
	
    private int mYear;
    private int mMonth;
    private int mDay;
	

    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_discharge);
        
        chkDHome = (CheckBox) findViewById(R.id.chkHome);
        chkDOther = (CheckBox) findViewById(R.id.chkOther);
        chkHVisitY = (CheckBox) findViewById(R.id.chkHomeVisitY);
        chkHVisitN = (CheckBox) findViewById(R.id.chkHomeVisitN);
        chkCApptN = (CheckBox) findViewById(R.id.chkClinicY);
        chkCApptY = (CheckBox) findViewById(R.id.chkClinicN);
        chkBNA = (CheckBox) findViewById(R.id.chkBNa);
        chkBK = (CheckBox) findViewById(R.id.chkBK);
        chkBUrea = (CheckBox) findViewById(R.id.chkBUrea);
        chkBCreat = (CheckBox) findViewById(R.id.chkBCreat);
        chkBGlucose = (CheckBox) findViewById(R.id.chkBGlucose);
        chkBHb = (CheckBox) findViewById(R.id.chkBHb);
        chkBWCC = (CheckBox) findViewById(R.id.chkBWCC);
        chkBPlatelets = (CheckBox) findViewById(R.id.chkBPlatelets);
        chkBTFT = (CheckBox) findViewById(R.id.chkBTFT);
        chkBLFT = (CheckBox) findViewById(R.id.chkBLFT);

        txtDischargeComments = (EditText) findViewById(R.id.txtDischargeComments);
        txtAdmissionDate = (EditText) findViewById(R.id.txtAdmissionDate);
        txtDischargeDate = (EditText) findViewById(R.id.txtDischargeDate);
        txtWard = (EditText) findViewById(R.id.txtWard);
        txtDesOther = (EditText) findViewById(R.id.txtOtherDestination);
        
        if(Activity_AddPatient.active != true){
        	ShowContact();
        }
        
        txtWard.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtWard.getText().toString().equals("")))
					{
						Ward = txtWard.getText().toString();
					}else{
						Ward = "Unknown";
					}
				}
			}
        });
        txtConsultant = (EditText) findViewById(R.id.txtConsultant);
        txtConsultant.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtConsultant.getText().toString().equals("")))
					{
						Consultant = txtConsultant.getText().toString();
					}else{
						Consultant = "Unknown";
					}
				}
			}
        });
        txtDateOfFirstVisit = (EditText) findViewById(R.id.txt1stVisit);
        txtDateOfFirstVisit.setEnabled(false);
        txtDesOther = (EditText) findViewById(R.id.txtOtherDestination);
        txtDesOther.setEnabled(false);
        
        chkDHome.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkDHome.isChecked())
				{
					chkDOther.setChecked(false);
					Deso = "Home";
					txtDesOther.setText("");
					txtDesOther.setEnabled(false);
				}
			}
        });
        chkDOther.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkDOther.isChecked()){
					chkDHome.setChecked(false);
					txtDesOther.setEnabled(true);		
				}
			}
        });
        txtDesOther.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtDesOther.getText().toString().equals("")))
					{
						Deso = txtDesOther.getText().toString();
					}else{
						Deso = "Unknown";
					}
				}
			}
        });
        
        chkHVisitY.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkHVisitY.isChecked()){
					chkHVisitN.setChecked(false);
					HomeVisit = "Yes";
					txtDateOfFirstVisit.setEnabled(true);
				}
				
			}
        });
        chkHVisitN.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkHVisitN.isChecked()){
					chkHVisitY.setChecked(false);
					HomeVisit = "No";
				}
				
			}
        });
        chkCApptN.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkCApptN.isChecked()){
					chkCApptY.setChecked(false);
					ClinicAppt = "No";
				}
				
			}
        });
        chkCApptY.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkCApptY.isChecked()){
					chkCApptN.setChecked(false);
					ClinicAppt = "No";
				}
				
			}
        });

        
        chkBNA.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBNA.isChecked()){
					DisplayBloodTests(1);
				}else{
					BNa = "-0";
					chkBNA.setText("Na");
				}
				
			}
        	
        });
        chkBK.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBK.isChecked()){
					DisplayBloodTests(2);
				}else{
					BK = "-0";
					chkBK.setText("K");
				}
				
			}
        	
        });
        chkBUrea.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBUrea.isChecked()){
					DisplayBloodTests(3);
				}else{
					BUrea = "-0";
					chkBUrea.setText("Urea");
				}
				
			}
        	
        });
        chkBCreat.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBCreat.isChecked()){
					DisplayBloodTests(4);
				}else{
					BCreat = "-0";
					chkBCreat.setText("Creat");
				}
				
			}
        	
        });
        chkBGlucose.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBGlucose.isChecked()){
					DisplayBloodTests(5);
				}else{
					BGlucose = "-0";
					chkBGlucose.setText("Glucose");
				}
				
			}
        	
        });
        chkBHb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBHb.isChecked()){
					DisplayBloodTests(6);
				}else{
					BHb = "-0";
					chkBHb.setText("Hb");
				}
				
			}
        	
        });
        chkBWCC.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBWCC.isChecked()){
					DisplayBloodTests(7);
				}else{
					BWCC = "-0";
					chkBWCC.setText("WCC");
				}
				
			}
        	
        });
        chkBPlatelets.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBPlatelets.isChecked()){
					DisplayBloodTests(8);
				}else{
					BPlatelets = "-0";
					chkBPlatelets.setText("Platelets");
				}
				
			}
        	
        });
        chkBTFT.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBTFT.isChecked()){
					DisplayBloodTests(9);
				}else{
					BTFT = "-0";
					chkBTFT.setText("TFT");
				}
				
			}
        	
        });
        chkBLFT.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(chkBLFT.isChecked()){
					DisplayBloodTests(10);
				}else{
					BLFT = "-0";
					chkBLFT.setText("LFT");
				}
				
			}
        	
        });

        txtAdmissionDate.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				showDialog(1);
				return false;
			}
        });
        txtDischargeDate.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				showDialog(2);
				return false;
			}
        });
        txtDateOfFirstVisit.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				showDialog(3);
				return false;
			}
        });
        txtDischargeComments.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtDischargeComments.getText().toString().equals(""))){
						DischargeComments = txtDischargeComments.getText().toString();
					}
				}
			}
        });
	}
	
	private void updateDisplayAdmission() {
		txtAdmissionDate.setText(new StringBuilder().append(mDay).append("/") .append(mMonth + 1).append("/").append(mYear).append(" "));
		AdmissionDate = txtAdmissionDate.getText().toString();
    }
	private void updateDisplayDischarge() {
		txtDischargeDate.setText(new StringBuilder().append(mDay).append("/") .append(mMonth + 1).append("/").append(mYear).append(" "));
		DischargeDate = txtDischargeDate.getText().toString();
    }
	private void updateDisplayFirstVisit() {
		txtDateOfFirstVisit.setText(new StringBuilder().append(mDay).append("/") .append(mMonth + 1).append("/").append(mYear).append(" "));
		DateOfFirstVisit = txtDateOfFirstVisit.getText().toString();
    }
	
	private void DisplayBloodTests(final int id){
		final Dialog Question = new Dialog(tabActivity_Discharge.this);
		Question.setContentView(R.layout.dialog_generalbloodtest);
    	Question.setTitle("Enter Result");
    	Question.setCancelable(true);
    	
    	final EditText value = (EditText) Question.findViewById(R.id.txtValue);
    	 Button btnSave = (Button) Question.findViewById(R.id.btnDialogAdd);
            btnSave.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) 
            	{
            	if(!(value.getText().toString().equals("")))
            		switch(id){
            		case 1:
            			BNa = value.getText().toString();
            			chkBNA.setText("Na (" +BNa+ ")");
            			break;
            		case 2:
            			BK = value.getText().toString();
            			chkBK.setText("K (" +BK+ ")");
            			break;
            		case 3:
            			BUrea = value.getText().toString();
            			chkBUrea.setText("Urea (" +BUrea+ ")");
            			break;
            		case 4:
            			BCreat = value.getText().toString();
            			chkBCreat.setText("Creat (" +BCreat+ ")");
            			break;
            		case 5:
            			BGlucose = value.getText().toString();
            			chkBGlucose.setText("Glucose (" +BGlucose+ ")");
            			break;
            		case 6:
            			BHb = value.getText().toString();
            			chkBHb.setText("Hb (" +BHb+ ")");
            			break;
            		case 7:
            			BWCC = value.getText().toString();
            			chkBWCC.setText("WCC (" +BWCC+ ")");
            			break;
            		case 8:
            			BPlatelets = value.getText().toString();
            			chkBPlatelets.setText("Platelets (" +BPlatelets+ ")");
            			break;
            		case 9:
            			BTFT = value.getText().toString();
            			chkBTFT.setText("TFT (" +BTFT+ ")");
            			break;
            		case 10:
            			BLFT = value.getText().toString();
            			chkBLFT.setText("LFT (" +BLFT+ ")");
            			break;
            		}else{
            			switch(id){
	            		case 1:
	            			chkBNA.setText("Na");
	            			chkBNA.setChecked(false);
	            			BNa = "-0";
	            			break;
	            		case 2:;
	            			chkBK.setText("K");
	            			chkBK.setChecked(false);
	            			BK = "-0";
	            			break;
	            		case 3:
	            			chkBUrea.setText("Urea");
	            			chkBUrea.setChecked(false);
	            			BUrea = "-0";
	            			break;
	            		case 4:
	            			chkBCreat.setText("Creat");
	            			chkBCreat.setChecked(false);
	            			BCreat = "-0";
	            			break;
	            		case 5:
	            			chkBGlucose.setText("Glucose");
	            			chkBGlucose.setChecked(false);
	            			BGlucose = "-0";
	            			break;
	            		case 6:
	            			chkBHb.setText("Hb");
	            			chkBHb.setChecked(false);
	            			BHb = "-0";
	            			break;
	            		case 7:
	            			chkBWCC.setText("WCC");
	            			chkBWCC.setChecked(false);
	            			BWCC = "-0";
	            			break;
	            		case 8:
	            			chkBPlatelets.setText("Platelets");
	            			chkBPlatelets.setChecked(false);
	            			BPlatelets = "-0";
	            			break;
	            		case 9:
	            			chkBTFT.setText("TFT");
	            			chkBTFT.setChecked(false);
	            			BTFT = "-0";
	            			break;
	            		case 10:
	            			chkBLFT.setText("LFT");
	            			chkBLFT.setChecked(false);
	            			BLFT = "-0";
	            			break;
            			}
            		Question.dismiss();
            	}
            	Question.dismiss();
            	}
            });
            
            Button btnCancel = (Button) Question.findViewById(R.id.btnDialogCancel);
            btnCancel.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) {
            		Question.dismiss();
                }
            });
            Question.show();
	}
	
    @Override
    protected Dialog onCreateDialog(int id) {
    	Calendar c = Calendar.getInstance();
    	int DYear = c.get(Calendar.YEAR);
    	int DMonth = c.get(Calendar.MONTH);
    	int DDay = c.get(Calendar.DAY_OF_MONTH);
    	switch (id) {
        case 1:
        	return new DatePickerDialog(this, mDateSetListener, DYear, DMonth, DDay);
        case 2:
    		return new DatePickerDialog(this, DDateSetListener, DYear, DMonth, DDay);
        case 3:
        	return new DatePickerDialog(this, FDateSetListener, DYear, DMonth, DDay);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplayAdmission();
                }
            };
    private DatePickerDialog.OnDateSetListener DDateSetListener = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplayDischarge();
                }
            };
    private DatePickerDialog.OnDateSetListener FDateSetListener = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplayFirstVisit();
                }
            };
    private void ShowContact(){
    	
    	DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();
    	
    	if(Activity_AddContact.SelectedPatient == true){
    		ContactCursor = DBHelper.getCursor("tblPatientContact", "patientID='" + Activity_AddContact.PatientID.toString() + "'" ,null,  null, "contactID ASC");
    		ContactCursor.moveToLast();
        	DisplayData();
    		
    	}
    	if(Activity_AddContact.ViewContact == true){
    		ContactCursor = DBHelper.getCursor("tblPatientContact", "contactID='" + Activity_AddContact.ContactID.toString() + "'" ,null,  null, null);

        	ContactCursor.moveToPosition(0);
        	BloodCursor = DBHelper.getCursor("tblBloodTests", "contactID='" + Activity_AddContact.ContactID.toString() + "'", null, null, null);

        	BloodCursor.moveToPosition(0);
        	DisplayData();
        }
    }
    private void DisplayData(){
	
		EditText txtAdmissionDate = (EditText) findViewById(R.id.txtAdmissionDate);
		txtAdmissionDate.setText(ContactCursor.getString(ContactCursor.getColumnIndex("admissionDate")).toString());
		AdmissionDate = txtAdmissionDate.getText().toString();
		
		EditText txtDischargeDate = (EditText) findViewById(R.id.txtDischargeDate);
		txtDischargeDate.setText(ContactCursor.getString(ContactCursor.getColumnIndex("dischargeDate")).toString());
		DischargeDate = txtDischargeDate.getText().toString();
		
		EditText txtWard = (EditText) findViewById(R.id.txtWard);
		txtWard.setText(ContactCursor.getString(ContactCursor.getColumnIndex("ward")).toString());
		Ward = txtWard.getText().toString();
		
		EditText txtConsultant = (EditText) findViewById(R.id.txtConsultant);
		txtConsultant.setText(ContactCursor.getString(ContactCursor.getColumnIndex("consultant")).toString());
		Consultant = txtConsultant.getText().toString();
		
		if(ContactCursor.getString(ContactCursor.getColumnIndex("destination")).toString().equals("Home")){
			chkDHome.setChecked(true);
			Deso = "Home";
		}else{
			chkDOther.setChecked(true);
			txtDesOther.setText(ContactCursor.getString(ContactCursor.getColumnIndex("destination")).toString());
			Deso = txtDesOther.getText().toString();
		}
		if(ContactCursor.getString(ContactCursor.getColumnIndex("homeVisit")).toString().equals("Yes")){
			chkHVisitY.setChecked(true);
			HomeVisit = "Yes";
		}else{
			chkHVisitN.setChecked(true);
			HomeVisit = "No";
		}
		if(ContactCursor.getString(ContactCursor.getColumnIndex("clinicAppt")).toString().equals("Yes")){
			chkCApptY.setChecked(true);
			ClinicAppt = "Yes";
		}else{
			chkCApptN.setChecked(true);
			ClinicAppt = "No";
		}
		
		txtDischargeComments.setText(ContactCursor.getString(ContactCursor.getColumnIndex("otherComments")).toString());
		
		if(!(BloodCursor == null)){
			{
				if (BloodCursor.getCount() != 0)
				{
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("Na")).toString().equals("-0"))){
						chkBNA.setText("Na (" + BloodCursor.getString(BloodCursor.getColumnIndex("Na")).toString()+")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("K")).toString().equals("-0"))){
					
						chkBK.setText("K (" + BloodCursor.getString(BloodCursor.getColumnIndex("K")).toString() + ")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("Urea")).toString().equals("-0"))){
				
						chkBUrea.setText("Urea (" + BloodCursor.getString(BloodCursor.getColumnIndex("Urea")).toString() + ")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("Creat")).toString().equals("-0"))){
					
						chkBCreat.setText("Creat (" + BloodCursor.getString(BloodCursor.getColumnIndex("Creat")).toString() + ")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("Glucose")).toString().equals("-0"))){
					
						chkBGlucose.setText("Glucose (" +BloodCursor.getString(BloodCursor.getColumnIndex("Glucose")).toString() + ")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("Hb")).toString().equals("-0"))){
					
						chkBHb.setText("Hb (" + BloodCursor.getString(BloodCursor.getColumnIndex("Hb")).toString() + ")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("WCC")).toString().equals("-0"))){
					
						chkBWCC.setText("WCC (" + BloodCursor.getString(BloodCursor.getColumnIndex("WCC")).toString() + ")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("Platelets")).toString().equals("-0"))){
					
						chkBPlatelets.setText("Platelets (" + BloodCursor.getString(BloodCursor.getColumnIndex("Platelets")).toString() + ")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("TFT")).toString().equals("-0"))){
					
						chkBTFT.setText("TFT ("+BloodCursor.getString(BloodCursor.getColumnIndex("TFT")).toString() + ")");
					}
					if(!(BloodCursor.getString(BloodCursor.getColumnIndex("LFT")).toString().equals("-0"))){
					
						chkBLFT.setText("LFT(" + BloodCursor.getString(BloodCursor.getColumnIndex("LFT")).toString() + ")");
					}
				}
			}
		}
	}
}
