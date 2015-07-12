package test.athena;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class tabActivity_Medication extends Activity {
	
	String MedicationString = "";
    public final ArrayList<String> AMedicationType = new ArrayList<String>();
    public final ArrayList<String> AMedicationName = new ArrayList<String>();
    public final ArrayList<String> AMedicationDose = new ArrayList<String>();
    public final ArrayList<String> AMedicationFreq = new ArrayList<String>();
	
	private dbAccess DBHelper = null;
	private Cursor Medication = null;	
	public static boolean NewPatient = false;
	public static boolean SelectedPatient = false;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_medications);

        if (NewPatient == true || Activity_AddContact.SelectedPatient == true){
        	NewMedication();
        }
        if (Activity_PatientInformation.PatientInfo == true){
        	DBHelper = new dbAccess(this);
        	DBHelper.createDatabase();
        	DBHelper.openDatabase();

        	Medication = DBHelper.getCursor("tblMedications", "patientID='"+ Activity_AddPatient.PatientID +"'" ,null,  null, null);
        	RemoveButtons();
        	ShowMedication();
        }
        if (Activity_AddContact.ViewContact == true){
        	DBHelper = new dbAccess(this);
        	DBHelper.createDatabase();
        	DBHelper.openDatabase();

        	Medication = DBHelper.getCursor("tblMedications", "contactID='"+ Activity_AddContact.ContactID +"'" ,null,  null, null);
        	RemoveButtons();
        	ShowMedication();
        }
        
	}
	static class GetArrays {
		   private static volatile ArrayList<String> AMedicationType;
		   private static volatile ArrayList<String> AMedicationName;
		   private static volatile ArrayList<String> AMedicationDose; 
		   private static volatile ArrayList<String> AMedicationFreq;
		   
		   public static ArrayList<String> getType(Context context){
		      return AMedicationType;
		   }
		   public static ArrayList<String> getName(Context context){
			      return AMedicationName;
			   }
		   public static ArrayList<String> getDose(Context context){
			      return AMedicationDose;
			   }
		   public static ArrayList<String> getFreq(Context context){
			      return AMedicationFreq;
			   }

		   public static void setType (ArrayList<String> list){
		      AMedicationType = list;
		   }
		   public static void setName (ArrayList<String> list){
			   AMedicationName = list;
			   }
		   public static void setDose (ArrayList<String> list){
			   AMedicationDose = list;
			   }
		   public static void setFreq (ArrayList<String> list){
			   AMedicationFreq = list;
			   }
		}
	
	private void NewMedication(){
		
        final EditText MedicalName  = (EditText) findViewById(R.id.txtDrugName);
        final EditText MedicationDose  = (EditText) findViewById(R.id.txtDrugDose);
        final EditText MedicationFreq  = (EditText) findViewById(R.id.txtDrugFreq);
        
        final Spinner DrugType = (Spinner) findViewById(R.id.spinDrugType);
        ArrayAdapter<CharSequence> DrugAdapter = ArrayAdapter.createFromResource(this, R.array.MedicationType, android.R.layout.simple_spinner_item);
        DrugAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DrugType.setAdapter(DrugAdapter);
        
        ListView lstMedication = (ListView) findViewById(R.id.lstMedications);
        
    	final ArrayList<String> noteList = new ArrayList<String>();
    	final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteList);
        lstMedication.setAdapter(aa);
         
        Button AddMedication = (Button) findViewById(R.id.btnAddMedication);
        AddMedication.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		if (DrugType.getSelectedItemPosition() == 0 || MedicalName.getText().equals("") || MedicationDose.getText().equals("") || MedicationFreq.getText().equals("")){
        			Toast.makeText(tabActivity_Medication.this, "Please Complete All Fields", Toast.LENGTH_LONG).show();
        		}else{
            		noteList.add(0, (DrugType.getSelectedItem().toString()) + "\t\t|\t\t" + (MedicalName.getText().toString()) + " \t\t|\t\t" + (MedicationDose.getText().toString()) + " \t\t|\t\t" + (MedicationFreq.getText().toString()) );
            		MedicationString = (MedicationString + DrugType.getSelectedItem().toString()) + "\t\t|\t\t" + (MedicalName.getText().toString()) + " \t\t|\t\t" + (MedicationDose.getText().toString()) + " \t\t|\t\t" + (MedicationFreq.getText().toString() + "\f"); 
            		aa.notifyDataSetChanged();
            		
            		AMedicationType.add(DrugType.getSelectedItem().toString());
            		AMedicationName.add(MedicalName.getText().toString());
            		AMedicationDose.add(MedicationDose.getText().toString());
            		AMedicationFreq.add(MedicationFreq.getText().toString());
            		
            		DrugType.setSelection(0);
            		MedicalName.setText("");
            		MedicationDose.setText("");
            		MedicationFreq.setText("");
            		GetArrays.setType(AMedicationType);
            		GetArrays.setName(AMedicationName);
            		GetArrays.setDose(AMedicationDose);
            		GetArrays.setFreq(AMedicationFreq);
            		
        		}
        	}
        });
	}

	private void RemoveButtons(){
		TableLayout Table = (TableLayout) findViewById(R.id.tblMedication);
		Table.removeAllViewsInLayout();
	}
	
	private void ShowMedication(){
        ListView lstMedicaiton = (ListView) findViewById(R.id.lstMedications);
        ArrayList<String> MedicationList = new ArrayList<String>();
    	if(Medication != null){
    		for (Medication.moveToFirst(); !Medication.isAfterLast(); Medication.moveToNext()){
    			String MedicationType = Medication.getString(4);
    			String MedicationName = Medication.getString(5);
    			String MedicationDose = Medication.getString(6);
    			String MedicationFreq = Medication.getString(7);
    			
    			String StringValue = ("Type :" + MedicationType + " - Name:" + MedicationName + " - Dose:" + MedicationDose + " - Freq:" + MedicationFreq);
    			MedicationList.add(StringValue.toString());
    			}
    	}
    	ArrayAdapter<String> MedicationAdpater = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, MedicationList);
    	lstMedicaiton.setAdapter(MedicationAdpater); 

	}

}
