package test.athena;

import java.util.ArrayList;

import android.R.menu;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class Activity_AddContact extends TabActivity {
	
	private TabHost mTabHost;
	private dbAccess DBHelper = null;
	public static String PatientID = null;
	public static String ContactID = null;
	
	public static boolean SelectedPatient = false;	//Add Contact
	public static boolean ViewContact = false;		//View Contact

	
	@Override
	protected void onStop(){
		super.onStop();
		SelectedPatient = false;	
		ViewContact = false;	
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpatientlayout);
		
    	mTabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        if(ViewContact == false){
	        intent = new Intent(this, tabActivity_ContactInformation.class);
	        spec = mTabHost.newTabSpec("ContactInformation").setIndicator("Contact Information").setContent(intent);
	        mTabHost.addTab(spec);
        }
        
        intent = new Intent(this, tabActivity_Sympthoms.class);
        spec = mTabHost.newTabSpec("Sympthoms").setIndicator("Sympthoms").setContent(intent);
        mTabHost.addTab(spec);

        intent = new Intent(this, tabActivity_PatientHistory.class);
        spec = mTabHost.newTabSpec("History").setIndicator("Patient History").setContent(intent);
        mTabHost.addTab(spec);
        
        intent = new Intent(this, tabActivity_Discharge.class);
        spec = mTabHost.newTabSpec("Blood_Tests").setIndicator("Bloods").setContent(intent);
        mTabHost.addTab(spec);
        
        intent = new Intent(this, tabActivity_Medication.class);
        spec = mTabHost.newTabSpec("Medication").setIndicator("Medication").setContent(intent);
        mTabHost.addTab(spec);
        
        mTabHost.setCurrentTab(0);
		}
		
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.actionbar_contactmenu, menu);
    	MenuItem item = menu.findItem(R.id.Add);
    	if(ViewContact == true){
    		item.setEnabled(false);
    	}else{
    		item.setEnabled(true);
    	}
    	return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
	{
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (item.getOrder())
		{
			case 1:
				StoreContact();
				return true;
			case 2:		//New				
				builder.setMessage("Are you sure you want to exit?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   	Activity_AddContact.this.finish();
			   				//Intent i = new Intent(Activity_AddContact.this,Activity_PatientInformation.class);
			   				//startActivity(i);
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
			default:
				return super.onOptionsItemSelected(item);
		}
	}
    private void StoreContact(){
    	
        mTabHost.setCurrentTab(0);	
        mTabHost.setCurrentTab(1);	
        mTabHost.setCurrentTab(2);	
        mTabHost.setCurrentTab(3);
        mTabHost.setCurrentTab(4);
    	String[] ContactValues = new String[66];
    	
    	ContactValues[0] = PatientID.toString();
    	ContactValues[1] = PatientID.toString() + "-" + ContactID.toString();
    	ContactValues[2] = tabActivity_ContactInformation.ContactDate.toString();
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
    	ContactValues[61] = tabActivity_ContactInformation.ContactReason.toString();
    	ContactValues[62] = tabActivity_ContactInformation.ContactBy.toString();
    	//ContactValues[63] = tabActivity_ContactInformation.c.toString();
    	ContactValues[64] = tabActivity_ContactInformation.ContactAt.toString();
    	
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
        
    	if (!(ContactValues[2].toString().equals(""))){
        	DBHelper = new dbAccess(this);
        	DBHelper.openDatabase();
    		DBHelper.AddPatientContact(ContactValues);
    		if(!(Urea.toString().equals("-0") && Na.toString().equals("-0") && K.toString().equals("-0") && Glucose.toString().equals("-0") && Hb.toString().equals("-0") && WCC.toString().equals("-0") && Platelets.toString().equals("-0") && TFT.toString().equals("-0") && LFT.toString().equals("-0") && Creat.toString().equals("-0"))){
    			DBHelper.AddBloodTests(ContactValues[1].toString(), ContactValues[2].toString(), ContactValues[0].toString(), Urea, Na, K, Glucose, Hb, WCC, Platelets, TFT, LFT, Creat);
    		}
    		if(!(AMedicationType==null)){
    			DBHelper.AddMedication(ContactValues[1].toString(), ContactValues[2].toString(), ContactValues[0].toString(), AMedicationType, AMedicationName, AMedicationDose, AMedicationFreq);
    		}
    		if(!(AppointmentDate.toString().equals("Unknown"))){
    			DBHelper.AddAppointment(ContactValues[0].toString(), AppointmentDate);
    		}
    		DBHelper.close();
    	   	//Activity_AddContact.this.finish();
    		Toast.makeText(this, "Patient Contact Has Been Added to the database", Toast.LENGTH_LONG).show();
    		
			Intent i = new Intent(Activity_AddContact.this,Activity_PatientInformation.class);
			startActivity(i);
    	}else{
    		Toast.makeText(this, "Contact Date Must Be Entered", Toast.LENGTH_LONG).show();
    		mTabHost.setCurrentTab(0);	
    	}
    }
    
}
