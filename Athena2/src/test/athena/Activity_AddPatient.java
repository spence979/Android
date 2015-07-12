package test.athena;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class Activity_AddPatient extends TabActivity {
	private dbAccess DBHelper = null;
	private TabHost mTabHost;
	public static String PatientID = null;
	static boolean active = false;
	    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatientlayout);
        {
        	active = true;
        	mTabHost = getTabHost();
            TabHost.TabSpec spec;
            Intent intent;
            
            //Demo Tab
            intent = new Intent(this, tabActivity_Demographic.class);
            spec = mTabHost.newTabSpec("Demographics").setIndicator("Patient Information").setContent(intent);
            mTabHost.addTab(spec);
            
            intent = new Intent(this, tabActivity_Sympthoms.class);
            spec = mTabHost.newTabSpec("Patient_Assessment").setIndicator("Symptoms & Signs").setContent(intent);
            mTabHost.addTab(spec);
            
            intent = new Intent(this, tabActivity_PatientHistory.class);
            spec = mTabHost.newTabSpec("MedicalHistory").setIndicator("Patient History").setContent(intent);
            mTabHost.addTab(spec);
            
            intent = new Intent(this, tabActivity_Discharge.class);
            spec = mTabHost.newTabSpec("Discharge").setIndicator("Discharge Information").setContent(intent);
            mTabHost.addTab(spec);
            
            intent = new Intent(this, tabActivity_Medication.class);
            spec = mTabHost.newTabSpec("Medication").setIndicator("Medication").setContent(intent);
            mTabHost.addTab(spec);
            
            mTabHost.setCurrentTab(0);
        }
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.actionbar_addmenu, menu);
    	return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
	{
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (item.getOrder())
		{
			case 1:
				StorePatient();
				return true;
			case 2:		//New				
				builder.setMessage("Are You Sure You Want To Clear All Data?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
								Intent intent = getIntent();
								finish();
								startActivity(intent);
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog ResetAlert = builder.create();
				ResetAlert.show();
			
				return true;
			case 3:		//Cancel Option
				builder.setMessage("Are you sure you want to exit?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   	active = false;
				        	   	Activity_AddPatient.this.finish();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
				return true;
			case 4:		//Cancel Option

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
    private void StorePatient(){
    	
        mTabHost.setCurrentTab(1);	
        mTabHost.setCurrentTab(2);	
        mTabHost.setCurrentTab(3);	
        mTabHost.setCurrentTab(0);	
    	
    	//Get Todays Date for contact date
    	Calendar c = Calendar.getInstance(); 
    	String ContactDate =  Integer.toString(c.get(Calendar.DATE)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/" + Integer.toString(c.get(Calendar.YEAR));
    	
    	//Patient Information
    	String HC = tabActivity_Demographic.HC.getText().toString();
    	String ContactID = HC + "-1";
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

    	String Height = tabActivity_PatientHistory.HeightC;
    	
    	String[] ContactValues = new String[66];
    	ContactValues[0] = HC.toString();
    	ContactValues[1] = ContactID.toString();
    	ContactValues[2] = ContactDate.toString();
    	ContactValues[3] = String.valueOf(tabActivity_Sympthoms.spnCCSA.getSelectedItemPosition());
    	ContactValues[4] = String.valueOf(tabActivity_Sympthoms.spnNYHA.getSelectedItemPosition());
    	ContactValues[5] = tabActivity_Sympthoms.AtRest.toString();
    	ContactValues[6] = tabActivity_Sympthoms.OnExertion.toString();
    	ContactValues[7] = tabActivity_Sympthoms.Noctural.toString();
    	ContactValues[8] = tabActivity_Sympthoms.Orthopnoea.toString();
    	ContactValues[9] = tabActivity_Sympthoms.Fatigue.toString();
    	ContactValues[10] = tabActivity_Sympthoms.Dizziness.toString();
    	ContactValues[11] = tabActivity_Sympthoms.ChestPain.toString();
    	ContactValues[12] = tabActivity_Sympthoms.Palpitation.toString();
    	ContactValues[13] = tabActivity_Sympthoms.Cough.toString();
    	ContactValues[14] = tabActivity_Sympthoms.Angina.toString();
    	ContactValues[15] = tabActivity_Sympthoms.HeartRate.toString();
    	ContactValues[16] = tabActivity_Sympthoms.BP1.toString();
    	ContactValues[17] = tabActivity_Sympthoms.BP2.toString();
    	ContactValues[18] = String.valueOf(tabActivity_Sympthoms.spnOedema.getSelectedItemPosition());
    	ContactValues[19] = tabActivity_Sympthoms.spnAscites.getSelectedItem().toString();
    	ContactValues[20] = tabActivity_Sympthoms.WeightString.toString();
    	ContactValues[21] = tabActivity_Sympthoms.spnJVP.getSelectedItem().toString();
    	ContactValues[22] = tabActivity_Sympthoms.spnCreps.getSelectedItem().toString();
    	ContactValues[23] = tabActivity_Sympthoms.SBP1.toString();
    	ContactValues[24] = tabActivity_Sympthoms.SBP2.toString();
    	ContactValues[25] = tabActivity_Sympthoms.spnHeartSounds.getSelectedItem().toString();
    	ContactValues[26] = String.valueOf(tabActivity_PatientHistory.spnCHF.getSelectedItemPosition());
    	ContactValues[27] = tabActivity_PatientHistory.spnIHD.getSelectedItem().toString();
    	ContactValues[28] = tabActivity_PatientHistory.spnVA.getSelectedItem().toString();
    	ContactValues[29] = tabActivity_PatientHistory.spnCA.getSelectedItem().toString();
    	ContactValues[30] = tabActivity_PatientHistory.spnAF.getSelectedItem().toString();
    	ContactValues[31] = tabActivity_PatientHistory.spnSmoker.getSelectedItem().toString();
    	ContactValues[32] = tabActivity_PatientHistory.STri.toString();
    	ContactValues[33] = tabActivity_PatientHistory.SChol.toString();
    	ContactValues[34] = tabActivity_PatientHistory.SHDL.toString();
    	ContactValues[35] = tabActivity_PatientHistory.SLDL.toString();
    	ContactValues[36] = tabActivity_PatientHistory.SCHOL_LDL.toString();
    	ContactValues[37] = tabActivity_PatientHistory.spnPM.getSelectedItem().toString();
    	ContactValues[38] = tabActivity_PatientHistory.spnOverweight.getSelectedItem().toString();
    	ContactValues[39] = tabActivity_PatientHistory.spnRF.getSelectedItem().toString();
    	ContactValues[40] = tabActivity_PatientHistory.spnVD.getSelectedItem().toString();
    	ContactValues[41] = tabActivity_PatientHistory.spnCRF.getSelectedItem().toString();
    	ContactValues[42] = tabActivity_PatientHistory.spnDiabetes.getSelectedItem().toString();
    	ContactValues[43] = tabActivity_PatientHistory.spnAsthma.getSelectedItem().toString();
    	ContactValues[44] = tabActivity_PatientHistory.spnLD.getSelectedItem().toString();
    	ContactValues[45] = tabActivity_PatientHistory.spnCVA.getSelectedItem().toString();
    	ContactValues[46] = tabActivity_PatientHistory.spnPVD.getSelectedItem().toString();
    	ContactValues[47] = tabActivity_PatientHistory.AddationalProblems.toString();
    	ContactValues[48] = tabActivity_PatientHistory.ClinicAndServices.toString();
    	ContactValues[49] = tabActivity_PatientHistory.SFluDate.toString();
    	ContactValues[50] = tabActivity_PatientHistory.spnPneu.getSelectedItem().toString();
    	ContactValues[51] = tabActivity_Discharge.AdmissionDate.toString();
    	ContactValues[52] = tabActivity_Discharge.DischargeDate.toString();
    	ContactValues[53] = tabActivity_Discharge.Ward.toString();
    	ContactValues[54] = tabActivity_Discharge.Consultant.toString();
    	ContactValues[55] = tabActivity_Discharge.Deso.toString();
    	ContactValues[56] = tabActivity_Discharge.HomeVisit.toString();
    	ContactValues[57] = tabActivity_Discharge.ClinicAppt.toString();
    	ContactValues[58] = tabActivity_Discharge.DischargeComments.toString();
    	ContactValues[59] = tabActivity_Sympthoms.Generally.toString();
    	ContactValues[60] = tabActivity_Sympthoms.spnRhythm.getSelectedItem().toString();
    	
    	//Blood Tests
    	String Na = tabActivity_Discharge.BNa.toString();
    	String K = tabActivity_Discharge.BK.toString();
    	String Creat = tabActivity_Discharge.BCreat.toString();
    	String Glucose = tabActivity_Discharge.BGlucose.toString();
    	String Hb = tabActivity_Discharge.BHb.toString();
    	String WCC = tabActivity_Discharge.BWCC.toString();
    	String Platelets = tabActivity_Discharge.BPlatelets;
    	String TFT = tabActivity_Discharge.BTFT.toString();
    	String Urea =  tabActivity_Discharge.BUrea.toString();
    	String LFT = tabActivity_Discharge.BLFT.toString();
    	
    	//Medications
        ArrayList<String> AMedicationType = tabActivity_Medication.GetArrays.getType(null);
        ArrayList<String> AMedicationName = tabActivity_Medication.GetArrays.getName(null);
        ArrayList<String> AMedicationDose = tabActivity_Medication.GetArrays.getDose(null);
        ArrayList<String> AMedicationFreq = tabActivity_Medication.GetArrays.getFreq(null);
    	
        //Appointments
        String AppointmentDate = tabActivity_Discharge.DateOfFirstVisit.toString();
        
    	if (!(HC.equals("") || FName.equals("") || LName.equals(""))){
    		if (CheckPateintID(HC) == false)
    		{
	        	DBHelper = new dbAccess(this);
	        	DBHelper.openDatabase();
	    		DBHelper.CreateEntryPatient(HC, FName, LName, Gender, DOB, Age, Marital, HouseNo , Address1, Address2, PostCode, TelephoneNo , MobileNo, NOK, NOKC); 
	    		DBHelper.AddPatientContact(ContactValues);
	    		if(!(Urea.toString().equals("-0") && Na.toString().equals("-0") && K.toString().equals("-0") && Glucose.toString().equals("-0") && Hb.toString().equals("-0") && WCC.toString().equals("-0") && Platelets.toString().equals("-0") && TFT.toString().equals("-0") && LFT.toString().equals("-0") && Creat.toString().equals("-0"))){
	    			DBHelper.AddBloodTests(ContactID, ContactDate, HC, Urea, Na, K, Glucose, Hb, WCC, Platelets, TFT, LFT, Creat);
	    		}
	    		if(!(AMedicationType==null)){
	    			DBHelper.AddMedication(ContactID, ContactDate, HC, AMedicationType, AMedicationName, AMedicationDose, AMedicationFreq);
	    		}
	    		if(!(AppointmentDate.toString().equals("Unknown"))){
	    			DBHelper.AddAppointment(HC.toString(), AppointmentDate);
	    		}
	    		DBHelper.close();
	    		active = false;
	    		Toast.makeText(this, "Patient Has Been Added to the database", Toast.LENGTH_LONG).show();
	    	   	Activity_AddPatient.this.finish();
    		}else{
    			Toast.makeText(this, "That H&C Number already Exists", Toast.LENGTH_LONG).show();
    		}
 
    	}else{
    		Toast.makeText(this, "Health And Care and First & Last Name Is Required", Toast.LENGTH_LONG).show();
    	}
    	
    }
    private boolean CheckPateintID(String ID){
        DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();
		Cursor PatientCursor = DBHelper.getCursor("tblPatients", "HC='" + ID + "'" ,  null, null, null);
		startManagingCursor(PatientCursor);
		
    	Boolean IDExists = false;
    	if(PatientCursor.getCount() > 0)
    	{
    		IDExists = true;
    	}
		 return (IDExists);
    }
}
