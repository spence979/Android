package test.athena;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class tabActivity_Sympthoms extends Activity {
	
public static Spinner spnOedema, spnNYHA, spnCCSA, spnRhythm, spnAscites, spnJVP, spnCreps, spnHeartSounds;
CheckBox chkAtRest, chkOnExertion, chkNoctural, chkOrthopnoea, chkFatigue, chkDizziness, chkChestPain, chkPalpitation, chkCough, chkAngina;
public static String OtherComments = "", WeightString="0", HeartRate = "0", BP1 = "0", BP2 = "0", SBP1 = "0", SBP2 = "0" , AtRest = "false", OnExertion= "false", Noctural= "false", Orthopnoea= "false", Fatigue= "false", Dizziness= "false", ChestPain = "false", Palpitation= "false", Cough= "false", Angina= "false", Generally = "4";
static TextView txtWeightStone, txtWeightPounds, txtWeightKG, txtHeartRate, txtBloodPressure1, txtBloodPressure2, txtSBloodPressure1 , txtSBloodPressure2;
private dbAccess DBHelper = null;
private Cursor ContactCursor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout_symptons);
		

        if (Activity_AddPatient.active == true){
        	NewContact();
        }
		
		spnCCSA = (Spinner) findViewById(R.id.spnCCSA);
		spnNYHA = (Spinner) findViewById(R.id.spnNYHA);
		spnOedema = (Spinner) findViewById(R.id.spnOedema);
		spnRhythm = (Spinner) findViewById(R.id.spnRhythm);
		spnAscites = (Spinner) findViewById(R.id.spnAscites);
		spnJVP = (Spinner) findViewById(R.id.spnJVP);
		spnCreps = (Spinner) findViewById(R.id.spnCreps);
		spnHeartSounds = (Spinner) findViewById(R.id.spnHeartSounds);
		
		chkAtRest = (CheckBox) findViewById(R.id.chkRest);
		chkOnExertion = (CheckBox) findViewById(R.id.chkExertion);
		chkNoctural = (CheckBox) findViewById(R.id.chkNoctural);
		chkOrthopnoea = (CheckBox) findViewById(R.id.chkOrthopnoea);
		chkFatigue = (CheckBox) findViewById(R.id.chkFatigue);
		chkDizziness = (CheckBox) findViewById(R.id.chkDizziness);
		chkChestPain = (CheckBox) findViewById(R.id.chkChest);
		chkPalpitation = (CheckBox) findViewById(R.id.chkPalpitations);
		chkCough = (CheckBox) findViewById(R.id.chkCough);
		chkAngina = (CheckBox) findViewById(R.id.chkAngina);
		
		
        ArrayAdapter<CharSequence> CCSA = ArrayAdapter.createFromResource(this, R.array.NYHA, android.R.layout.simple_spinner_item);
        CCSA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCCSA.setAdapter(CCSA);
        
        ArrayAdapter<CharSequence> NYHA = ArrayAdapter.createFromResource(this, R.array.NYHA, android.R.layout.simple_spinner_item);
        NYHA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNYHA.setAdapter(NYHA);
        
        ArrayAdapter<CharSequence> Oedema = ArrayAdapter.createFromResource(this, R.array.Oedema, android.R.layout.simple_spinner_item);
        Oedema.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOedema.setAdapter(Oedema);
		
        ArrayAdapter<CharSequence> Rhythm = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        Rhythm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRhythm.setAdapter(Rhythm);
        
        ArrayAdapter<CharSequence> Ascites = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        Ascites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAscites.setAdapter(Ascites);
        
        ArrayAdapter<CharSequence> JVP = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        JVP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJVP.setAdapter(JVP); 
        
        ArrayAdapter<CharSequence> Creps = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        Creps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCreps.setAdapter(Creps);
        
        ArrayAdapter<CharSequence> HeartSounds = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        HeartSounds.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHeartSounds.setAdapter(HeartSounds);
        
        txtWeightStone = (EditText) findViewById(R.id.txtWS);
        txtWeightPounds = (EditText) findViewById(R.id.txtWP);
        txtWeightKG = (EditText) findViewById(R.id.txtWK);
        txtHeartRate = (EditText) findViewById(R.id.txtHR);
        txtBloodPressure1 = (EditText) findViewById(R.id.txtBP1);
        txtBloodPressure2 = (EditText) findViewById(R.id.txtBP2);
        txtSBloodPressure1 = (EditText) findViewById(R.id.txtSBP1);
        txtSBloodPressure2 = (EditText) findViewById(R.id.txtSBP2);
        txtHeartRate = (EditText) findViewById(R.id.txtHR);
        
        txtWeightStone.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtWeightStone.getText().toString().equals(""))){
	            		String TempInput = txtWeightStone.getText().toString();
	            		double TempDouble = Double.parseDouble(TempInput);
	            		double TempValue = TempDouble*14;
	            		txtWeightPounds.setText(Double.toString(TempValue));
	            		TempDouble = Double.parseDouble(TempInput);
	            		TempValue = TempDouble*6.35;
	            		txtWeightKG.setText(Double.toString(TempValue));
					}
				}
			}	
        });
        txtWeightPounds.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtWeightPounds.getText().toString().equals(""))){
	            		String TempInput = txtWeightPounds.getText().toString();
	            		double TempDouble = Double.parseDouble(TempInput);
	            		double TempValue = TempDouble*0.0714285714;
	            		txtWeightStone.setText(Double.toString(TempValue));
	            		TempDouble = Double.parseDouble(TempInput);
	            		TempValue = TempDouble*0.45359237;
	            		txtWeightKG.setText(Double.toString(TempValue));
	            		WeightString=Double.toString(TempValue);
					}
				}
			}	
        });
        txtHeartRate.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtHeartRate.getText().toString().equals(""))){
						HeartRate = txtHeartRate.getText().toString();
					}
				}
			}	
        });
        txtWeightKG.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtWeightKG.getText().toString().equals(""))){
	            		String TempInput = txtWeightKG.getText().toString();
	            		double TempDouble = Double.parseDouble(TempInput);
	            		double TempValue = TempDouble*0.157473044;
	            		txtWeightStone.setText(Double.toString(TempValue));
	            		TempDouble = Double.parseDouble(TempInput);
	            		TempValue = TempDouble*2.20462262;
	            		txtWeightPounds.setText(Double.toString(TempValue));
	            		
					}
				}
			}	
        });        

        chkAtRest.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkAtRest.isChecked()){
					AtRest = "true";
				}else{
					AtRest = "false";
				}
			}
        	
        });
        chkOnExertion.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkOnExertion.isChecked()){
					OnExertion = "true";
				}else{
					OnExertion = "false";
				}
			}
        	
        });
        chkNoctural.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkNoctural.isChecked()){
					Noctural = "true";
				}else{
					Noctural = "false";
				}
			}
        	
        });
        chkOrthopnoea.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkOrthopnoea.isChecked()){
					Orthopnoea = "true";
				}else{
					Orthopnoea = "false";
				}
			}
        });
        if(Activity_AddPatient.active == true){
        chkFatigue.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkFatigue.isChecked()){
					Fatigue = "true";
				}else{
					Fatigue = "false";
				}
			}
        });
        chkDizziness.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkDizziness.isChecked()){
					Dizziness = "true";
				}else{
					Dizziness = "false";
				}
			}
        });
        chkChestPain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkChestPain.isChecked()){
					ChestPain = "true";
				}else{
					ChestPain = "false";
				}
			}
        });
        chkPalpitation.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkPalpitation.isChecked()){
					Palpitation = "true";
				}else{
					Palpitation = "false";
				}
			}
        });
        chkCough.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkCough.isChecked()){
					Cough = "true";
				}else{
					Cough = "false";
				}
			}
        });
        chkAngina.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (chkAngina.isChecked()){
					Angina = "true";
				}else{
					Angina = "false";
				}
			}
        });
        
        }else{
        	SetValues();
        }
        	
	
	}
	private void NewContact(){
		TableLayout Table = (TableLayout) findViewById(R.id.tblTable1);
		TableRow Header = (TableRow) findViewById(R.id.rowOtherIssuesScoreHeader);
		TableRow Scores = (TableRow) findViewById(R.id.rowOtherIssuesScore);
		Table.removeView(Header);
		Table.removeView(Scores);
	}
	private void SetValues(){
    	DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();
    	
    	if(Activity_AddContact.SelectedPatient == true){
    		ContactCursor = DBHelper.getCursor("tblPatientContact", "patientID='" + Activity_AddContact.PatientID.toString() + "'" ,null,  null, "contactID ASC");
    		//startManagingCursor(ContactCursor);
    		ContactCursor.moveToLast();
    		Activity_AddContact.ContactID = ContactCursor.getString(ContactCursor.getColumnIndex("_id"));
    	}
    	if(Activity_AddContact.ViewContact == true){
    		ContactCursor = DBHelper.getCursor("tblPatientContact", "contactID='" + Activity_AddContact.ContactID.toString() + "'" ,null,  null, null);
    		//startManagingCursor(ContactCursor);
        	ContactCursor.moveToPosition(0);
    	}
    	
		
    	final RadioButton optFatigue1 = (RadioButton) findViewById(R.id.radFatigue1);
    	final RadioButton optFatigue2 = (RadioButton) findViewById(R.id.radFatigue2);
    	final RadioButton optFatigue3 = (RadioButton) findViewById(R.id.radFatigue3);
    	final RadioButton optFatigue4 = (RadioButton) findViewById(R.id.radFatigue4);
    	final RadioButton optFatigue5 = (RadioButton) findViewById(R.id.radFatigue5);
    	final RadioButton optFatigue6 = (RadioButton) findViewById(R.id.radFatigue6);
    	
    	final RadioButton optDizziness1 = (RadioButton) findViewById(R.id.radDizziness1);
    	final RadioButton optDizziness2 = (RadioButton) findViewById(R.id.radDizziness2);
    	final RadioButton optDizziness3 = (RadioButton) findViewById(R.id.radDizziness3);
    	final RadioButton optDizziness4 = (RadioButton) findViewById(R.id.radDizziness4);
    	final RadioButton optDizziness5 = (RadioButton) findViewById(R.id.radDizziness5);
    	final RadioButton optDizziness6 = (RadioButton) findViewById(R.id.radDizziness6);
    	
    	final RadioButton optChestPain1 = (RadioButton) findViewById(R.id.radChestPain1);
    	final RadioButton optChestPain2 = (RadioButton) findViewById(R.id.radChestPain2);
    	final RadioButton optChestPain3 = (RadioButton) findViewById(R.id.radChestPain3);
    	final RadioButton optChestPain4 = (RadioButton) findViewById(R.id.radChestPain4);
    	final RadioButton optChestPain5 = (RadioButton) findViewById(R.id.radChestPain5);
    	final RadioButton optChestPain6 = (RadioButton) findViewById(R.id.radChestPain6);
    	
    	final RadioButton optPalpitaiton1 = (RadioButton) findViewById(R.id.radPalpitations1);
    	final RadioButton optPalpitaiton2 = (RadioButton) findViewById(R.id.radPalpitations2);
    	final RadioButton optPalpitaiton3 = (RadioButton) findViewById(R.id.radPalpitations3);
    	final RadioButton optPalpitaiton4 = (RadioButton) findViewById(R.id.radPalpitations4);
    	final RadioButton optPalpitaiton5 = (RadioButton) findViewById(R.id.radPalpitations5);
    	final RadioButton optPalpitaiton6 = (RadioButton) findViewById(R.id.radPalpitations6);
    	
    	final RadioButton optCough1 = (RadioButton) findViewById(R.id.radCough1);
    	final RadioButton optCough2 = (RadioButton) findViewById(R.id.radCough2);
    	final RadioButton optCough3 = (RadioButton) findViewById(R.id.radCough3);
    	final RadioButton optCough4 = (RadioButton) findViewById(R.id.radCough4);
    	final RadioButton optCough5 = (RadioButton) findViewById(R.id.radCough5);
    	final RadioButton optCough6 = (RadioButton) findViewById(R.id.radCough6);
    	
    	final RadioButton optAngina1 = (RadioButton) findViewById(R.id.radAngina1);
    	final RadioButton optAngina2 = (RadioButton) findViewById(R.id.radAngina2);
    	final RadioButton optAngina3 = (RadioButton) findViewById(R.id.radAngina3);
    	final RadioButton optAngina4 = (RadioButton) findViewById(R.id.radAngina4);
    	final RadioButton optAngina5 = (RadioButton) findViewById(R.id.radAngina5);
    	final RadioButton optAngina6 = (RadioButton) findViewById(R.id.radAngina6);
    	
    	final RadioButton optGenerally1 = (RadioButton) findViewById(R.id.radGenerally1);
    	final RadioButton optGenerally2 = (RadioButton) findViewById(R.id.radGenerally2);
    	final RadioButton optGenerally3 = (RadioButton) findViewById(R.id.radGenerally3);
    	final RadioButton optGenerally4 = (RadioButton) findViewById(R.id.radGenerally4);
    	final RadioButton optGenerally5 = (RadioButton) findViewById(R.id.radGenerally5);
    	final RadioButton optGenerally6 = (RadioButton) findViewById(R.id.radGenerally6);
    	
    	EditText txtOtherComments = (EditText) findViewById(R.id.txtContactComments); 
    	String Value = (ContactCursor.getString(ContactCursor.getColumnIndex("CCSA")));
    	spnCCSA.setSelection(Integer.valueOf(Value));
    
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("NYHA")));
    	spnNYHA.setSelection(Integer.valueOf(Value));
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("atRest")));
    	if(Value.toString().equals("true")){
    		chkAtRest.setChecked(true);
    		AtRest = "true";
    	}
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("onExertion")));
    	if(Value.toString().equals("true")){
    		chkOnExertion.setChecked(true);
    		OnExertion = "true";
    	}
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("noctural")));
    	if(Value.toString().equals("true")){
    		chkNoctural.setChecked(true);
    		Noctural = "true";
    	}
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("orthopnoea")));
    	if(Value.toString().equals("true")){
    		chkOrthopnoea.setChecked(true);
    		Orthopnoea = "true";
    	}
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("fatigue")));
    	if(Value.toString().equals("false")){
    		optFatigue1.setChecked(true);
    	}else if ((Value.toString().equals("1"))){
    		optFatigue2.setChecked(true);
    	}else if ((Value.toString().equals("2"))){
    		optFatigue2.setChecked(true);
    	}else if ((Value.toString().equals("3"))){
    		optFatigue3.setChecked(true);
    	}else if ((Value.toString().equals("4"))){
    		optFatigue4.setChecked(true);
    	}else if ((Value.toString().equals("5"))){
    		optFatigue5.setChecked(true);}
    	else if ((Value.toString().equals("6"))){
        	optFatigue6.setChecked(true);
    	}else{
    		optFatigue4.setChecked(true);
    	}
    	
		optFatigue1.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(optFatigue1.isChecked()){
					Fatigue = "1";
				}
			}	
		});
		optFatigue2.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(optFatigue2.isChecked()){
					Fatigue = "2";
				}
			}	
		});
		optFatigue3.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(optFatigue3.isChecked()){
					Fatigue = "3";
				}
			}	
		});
		optFatigue4.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(optFatigue4.isChecked()){
					Fatigue = "4";
				}
			}	
		});
		optFatigue5.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(optFatigue1.isChecked()){
					Fatigue = "5";
				}
			}	
		});
		optFatigue6.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(optFatigue1.isChecked()){
					Fatigue = "6";
				}
			}	
		});
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("dizziness")));
    	if(Value.toString().equals("false")){
    		optDizziness1.setChecked(true);
    	}else if ((Value.toString().equals("1"))){
    		optDizziness1.setChecked(true);
    	}else if ((Value.toString().equals("2"))){
    		optDizziness2.setChecked(true);
    	}else if ((Value.toString().equals("3"))){
    		optDizziness3.setChecked(true);
    	}else if ((Value.toString().equals("4"))){
    		optDizziness4.setChecked(true);
    	}else if ((Value.toString().equals("5"))){
    		optDizziness5.setChecked(true);}
    	else if ((Value.toString().equals("6"))){
    		optDizziness6.setChecked(true);
    	}else{
    		optDizziness4.setChecked(true);
    	}
    	
    	optDizziness1.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optDizziness1.isChecked()){
				Dizziness = "1";
				}
			}	
		});
    	optDizziness2.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optDizziness2.isChecked()){
				Dizziness = "2";
				}
			}	
		});
    	optDizziness3.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optDizziness3.isChecked()){
			}
				Dizziness = "3";
			}	
		});
    	optDizziness4.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optDizziness4.isChecked()){
				Dizziness = "4";
				}
			}	
		});
    	optDizziness5.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optDizziness5.isChecked()){
				Dizziness = "5";
				
				}
			}	
		});
    	optDizziness6.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optDizziness6.isChecked()){
					Dizziness = "6";
				}
				
			}	
		});
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("chestPain")));
    	if(Value.toString().equals("false")){
    		optChestPain1.setChecked(true);
    	}else if ((Value.toString().equals("1"))){
    		optChestPain1.setChecked(true);
    	}else if ((Value.toString().equals("2"))){
    		optChestPain2.setChecked(true);
    	}else if ((Value.toString().equals("3"))){
    		optChestPain3.setChecked(true);
    	}else if ((Value.toString().equals("4"))){
    		optChestPain4.setChecked(true);
    	}else if ((Value.toString().equals("5"))){
    		optChestPain5.setChecked(true);}
    	else if ((Value.toString().equals("6"))){
    		optChestPain6.setChecked(true);
    	}else{
    		optChestPain4.setChecked(true);
    	}
    	
    	optChestPain1.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optChestPain1.isChecked()){
				ChestPain = "1";
				}
			}	
		});
    	optChestPain2.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optChestPain2.isChecked()){
				ChestPain = "2";
				}
			}	
		});
    	optChestPain3.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optChestPain3.isChecked()){
				ChestPain = "3";
				}
			}	
		});
    	optChestPain4.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optChestPain4.isChecked()){
				ChestPain = "4";
				}
			}	
		});
    	optChestPain5.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optChestPain5.isChecked()){
				ChestPain = "5";
				}
			}	
		});
    	optChestPain6.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optChestPain6.isChecked()){
				ChestPain = "6";
				}
			}	
		});
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("palpitations")));
    	if(Value.toString().equals("false")){
    		optPalpitaiton1.setChecked(true);
    	}else if ((Value.toString().equals("1"))){
    		optPalpitaiton1.setChecked(true);
    	}else if ((Value.toString().equals("2"))){
    		optPalpitaiton2.setChecked(true);
    	}else if ((Value.toString().equals("3"))){
    		optPalpitaiton3.setChecked(true);
    	}else if ((Value.toString().equals("4"))){
    		optPalpitaiton4.setChecked(true);
    	}else if ((Value.toString().equals("5"))){
    		optPalpitaiton5.setChecked(true);}
    	else if ((Value.toString().equals("6"))){
    		optPalpitaiton6.setChecked(true);
    	}else{
    		optPalpitaiton4.setChecked(true);
    	}
    	
    	optPalpitaiton1.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optPalpitaiton1.isChecked()){
				Palpitation = "1";
				}
			}	
		});
    	optPalpitaiton2.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optPalpitaiton2.isChecked()){
				Palpitation = "2";
				}
			}	
		});
    	optPalpitaiton3.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optPalpitaiton3.isChecked()){
				Palpitation = "3";
				}
			}	
		});
    	optPalpitaiton4.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optPalpitaiton4.isChecked()){
				Palpitation = "4";
				}
			}	
		});
    	optPalpitaiton5.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optPalpitaiton5.isChecked()){
				Palpitation = "5";
				}
			}	
		});
    	optPalpitaiton6.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optPalpitaiton6.isChecked()){
				Palpitation = "6";
				}
			}	
		});
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("cough")));
    	if(Value.toString().equals("false")){
    		optCough1.setChecked(true);
    	}else if ((Value.toString().equals("1"))){
    		optCough1.setChecked(true);
    	}else if ((Value.toString().equals("2"))){
    		optCough2.setChecked(true);
    	}else if ((Value.toString().equals("3"))){
    		optCough3.setChecked(true);
    	}else if ((Value.toString().equals("4"))){
    		optCough4.setChecked(true);
    	}else if ((Value.toString().equals("5"))){
    		optCough5.setChecked(true);}
    	else if ((Value.toString().equals("6"))){
    		optCough6.setChecked(true);
    	}else{
    		optCough4.setChecked(true);
    	}
    	
    	optCough1.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optCough1.isChecked()){
					Cough = "1";
				}
			}	
		});
    	optCough2.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optCough2.isChecked()){
					Cough = "2";
				}
			}	
		});
    	optCough3.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optCough3.isChecked()){
					Cough = "3";
				}
			}	
		});
    	optCough4.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optCough4.isChecked()){
					Cough = "4";
				}
			}	
		});
    	optCough5.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optCough5.isChecked()){
					Cough = "5";
				}
			}	
		});
    	optCough6.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optCough6.isChecked()){
					Cough = "6";
				}
			}	
		});
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("angina")));
    	if(Value.toString().equals("false")){
    		optAngina1.setChecked(true);
    	}else if ((Value.toString().equals("1"))){
    		optAngina1.setChecked(true);
    	}else if ((Value.toString().equals("2"))){
    		optAngina2.setChecked(true);
    	}else if ((Value.toString().equals("3"))){
    		optAngina3.setChecked(true);
    	}else if ((Value.toString().equals("4"))){
    		optAngina4.setChecked(true);
    	}else if ((Value.toString().equals("5"))){
    		optAngina5.setChecked(true);}
    	else if ((Value.toString().equals("6"))){
    		optAngina6.setChecked(true);
    	}else{
    		optAngina4.setChecked(true);
    	}
    	
    	optAngina1.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optAngina1.isChecked()){
				Angina = "1";
				}
			}	
		});
    	optAngina2.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optAngina2.isChecked()){
				Angina = "2";
				}
			}	
		});
    	optAngina3.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optAngina3.isChecked()){
				Angina = "3";
				}
			}	
		});
    	optAngina4.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optAngina4.isChecked()){
				Angina = "4";
				}
			}	
		});
    	optAngina5.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optAngina5.isChecked()){
				Angina = "5";
				}
			}	
		});
    	optAngina6.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optAngina6.isChecked()){
				Angina = "6";
				}
			}	
		});
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("generally")));
    	if(Value.toString().equals("false")){
    		optGenerally1.setChecked(true);
    	}else if ((Value.toString().equals("1"))){
    		optGenerally1.setChecked(true);
    	}else if ((Value.toString().equals("2"))){
    		optGenerally2.setChecked(true);
    	}else if ((Value.toString().equals("3"))){
    		optGenerally3.setChecked(true);
    	}else if ((Value.toString().equals("4"))){
    		optGenerally4.setChecked(true);
    	}else if ((Value.toString().equals("5"))){
    		optGenerally5.setChecked(true);}
    	else if ((Value.toString().equals("6"))){
    		optGenerally6.setChecked(true);
    	}else{
    		optGenerally4.setChecked(true);
    	}
    	
    	optGenerally1.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optGenerally1.isChecked()){
				Generally = "1";
				}
			}	
		});
    	optGenerally2.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optGenerally2.isChecked()){
				Generally = "2";
				}
			}	
		});
    	optGenerally3.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optGenerally3.isChecked()){
				Generally = "3";
				}
			}	
		});
    	optGenerally4.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optGenerally4.isChecked()){
				Generally = "4";
				}
			}	
		});
    	optGenerally5.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optGenerally5.isChecked()){
				Generally = "5";
				}
			}	
		});
    	optGenerally6.setOnCheckedChangeListener( new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (optGenerally6.isChecked()){
				Generally = "6";
				}
			}	
		});
    	
    	txtHeartRate.setText(ContactCursor.getString(ContactCursor.getColumnIndex("heartRate")));
    	HeartRate = txtHeartRate.getText().toString();
   
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("rhythm")));
        if (ContactCursor.getString(ContactCursor.getColumnIndex("rhythm")).equals("Yes")){
        	spnRhythm.setSelection(1);
        }else if (ContactCursor.getString(ContactCursor.getColumnIndex("rhythm")).equals("No")){
        	spnRhythm.setSelection(2);
        }else{
        	spnRhythm.setSelection(0);
        }
    	
    	txtBloodPressure1.setText(ContactCursor.getString(ContactCursor.getColumnIndex("bloodPressure1")));
    	BP1 = txtBloodPressure1.getText().toString();
    	txtBloodPressure2.setText(ContactCursor.getString(ContactCursor.getColumnIndex("bloodPressure2")));
    	BP2 = txtBloodPressure1.getText().toString();
    	
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("oedema")));
    	int Position = Integer.valueOf(Value);
    	spnOedema.setSelection(Position);
   
    	Value = (ContactCursor.getString(ContactCursor.getColumnIndex("ascities")));
        if (ContactCursor.getString(ContactCursor.getColumnIndex("ascities")).equals("Yes")){
        	spnAscites.setSelection(1);
        }else if (ContactCursor.getString(ContactCursor.getColumnIndex("ascities")).equals("No")){
        	spnAscites.setSelection(2);
        }else{
        	spnAscites.setSelection(0);
        }
        txtWeightKG.setText(ContactCursor.getString(ContactCursor.getColumnIndex("weight")));
        WeightString = txtWeightKG.getText().toString();
        
        if (ContactCursor.getString(ContactCursor.getColumnIndex("JVP")).equals("Yes")){
        	spnJVP.setSelection(1);
        }else if (ContactCursor.getString(ContactCursor.getColumnIndex("JVP")).equals("No")){
        	spnJVP.setSelection(2);
        }else{
        	spnJVP.setSelection(0);
        }
        if (ContactCursor.getString(ContactCursor.getColumnIndex("creps")).equals("Yes")){
        	spnCreps.setSelection(1);
        }else if (ContactCursor.getString(ContactCursor.getColumnIndex("creps")).equals("No")){
        	spnCreps.setSelection(2);
        }else{
        	spnCreps.setSelection(0);
        }

        txtSBloodPressure1.setText(ContactCursor.getString(ContactCursor.getColumnIndex("standingBloodPressure1")));
        SBP1 = txtSBloodPressure1.getText().toString();
    	txtSBloodPressure2.setText(ContactCursor.getString(ContactCursor.getColumnIndex("standingBloodPressure2")));
    	SBP2 = txtSBloodPressure2.getText().toString();
    	
        if (ContactCursor.getString(ContactCursor.getColumnIndex("heartSounds")).equals("Yes")){
        	spnHeartSounds.setSelection(1);
        }else if (ContactCursor.getString(ContactCursor.getColumnIndex("heartSounds")).equals("No")){
        	spnHeartSounds.setSelection(2);
        }else{
        	spnHeartSounds.setSelection(0);
        }
        txtOtherComments.setText(ContactCursor.getString(ContactCursor.getColumnIndex("otherComments")));
        OtherComments = txtOtherComments.getText().toString();
	}
	
}

