package test.athena;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class tabActivity_PatientHistory extends Activity {
	EditText Tri, Chol, HDL,LDL, Weight, txtHeightF, txtHeightI, txtHeightC, txtClinic, txtAddPro;
	TextView Ratio;
	Button  btnCholsterol;
	String  HeightF = "", HeightI = "";
	double Chol_DHL = 0;
	public static String SCHOL_LDL = "0", HeightC="Unknown", SFluDate="Unknown", ClinicAndServices="Unknown", AddationalProblems="Unknown", STri="Unknown", SChol="Unknown", SHDL="Unknown", SLDL="Unknown";
	public static Spinner spnCHF, spnIHD, spnVA, spnCA, spnPM, spnAF, spnSmoker, spnOverweight, spnRF, spnVD, spnCRF, spnDiabetes, spnAsthma, spnLD, spnCVA, spnPVD, spnPneu;
	public static double HeightFoot, HeightInches, HeightCM;
	ListView List; 
	
	private dbAccess DBHelper = null;
	private Cursor ContactCursor = null;
	
    static TextView FluDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout_pasthistory);
		
		spnCHF = (Spinner) findViewById(R.id.spnCauseOfHeartFailure);
		ArrayAdapter<CharSequence> CHF = ArrayAdapter.createFromResource(this, R.array.CHF, android.R.layout.simple_spinner_item);
        CHF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCHF.setAdapter(CHF);
        
		spnIHD = (Spinner) findViewById(R.id.spnIHD);
        ArrayAdapter<CharSequence> IHD = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        IHD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnIHD.setAdapter(IHD);
        
		spnVA = (Spinner) findViewById(R.id.spnVA);
        ArrayAdapter<CharSequence> VA = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        VA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVA.setAdapter(VA);
        
		spnAF = (Spinner) findViewById(R.id.spnAF);
        ArrayAdapter<CharSequence> AF = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        AF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAF.setAdapter(AF);
        
		spnCA = (Spinner) findViewById(R.id.spnCA);
        ArrayAdapter<CharSequence> CA = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        CA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCA.setAdapter(CA);
        
		spnPM = (Spinner) findViewById(R.id.spnPM);
        ArrayAdapter<CharSequence> PM = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        PM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPM.setAdapter(PM);
        
		spnSmoker = (Spinner) findViewById(R.id.spnSmoker);
        ArrayAdapter<CharSequence> Smoker = ArrayAdapter.createFromResource(this, R.array.Smoker, android.R.layout.simple_spinner_item);
        Smoker.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSmoker.setAdapter(Smoker);
		
		spnOverweight = (Spinner) findViewById(R.id.spnOverweight);
        ArrayAdapter<CharSequence> OverWeight = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        OverWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOverweight.setAdapter(OverWeight);
        
		spnRF = (Spinner) findViewById(R.id.spnRF);
        ArrayAdapter<CharSequence> RF = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        RF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRF.setAdapter(RF);
        
		spnVD = (Spinner) findViewById(R.id.spnVD);
        ArrayAdapter<CharSequence> VD = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        VD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVD.setAdapter(VD);
		
		spnCRF = (Spinner) findViewById(R.id.spnRenalFailure);
        ArrayAdapter<CharSequence> RenalFailure = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        RenalFailure.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCRF.setAdapter(RenalFailure);
        
		spnDiabetes = (Spinner) findViewById(R.id.spnDiabetes);
        ArrayAdapter<CharSequence> Diabetes = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        Diabetes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDiabetes.setAdapter(Diabetes);
        
		spnAsthma = (Spinner) findViewById(R.id.spnAsthma);
        ArrayAdapter<CharSequence> Asthma = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        Asthma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAsthma.setAdapter(Asthma);
        
		spnLD = (Spinner) findViewById(R.id.spnLungDisease);
        ArrayAdapter<CharSequence> LD = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        LD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLD.setAdapter(LD);
        
		spnCVA = (Spinner) findViewById(R.id.spnCVA);
        ArrayAdapter<CharSequence> CVA = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        CVA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCVA.setAdapter(CVA);
        
		spnPVD = (Spinner) findViewById(R.id.spnPVD);
        ArrayAdapter<CharSequence> PVD = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        PVD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPVD.setAdapter(PVD);
        
		spnPneu = (Spinner) findViewById(R.id.spnPJ);
        ArrayAdapter<CharSequence> PJ = ArrayAdapter.createFromResource(this, R.array.YesNO, android.R.layout.simple_spinner_item);
        PJ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPneu.setAdapter(PJ);
		
        btnCholsterol = (Button) findViewById(R.id.btnCholesterol);
        btnCholsterol.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final Dialog Question = new Dialog(tabActivity_PatientHistory.this);
				Question.setContentView(R.layout.dialog_cholesterol);
		    	Question.setTitle("Cholesterol Results");
		    	Question.setCancelable(true);
		    	
		    	Tri = (EditText) Question.findViewById(R.id.txtTri);
		    	Chol = (EditText) Question.findViewById(R.id.txtChol);
		    	HDL = (EditText) Question.findViewById(R.id.txtHDL);
		    	LDL = (EditText) Question.findViewById(R.id.txtLDL);
		    	
		    	
	            //set up button
	            Button btnSave = (Button) Question.findViewById(R.id.btnStore);
	            btnSave.setOnClickListener(new OnClickListener() {
	            @Override
	                public void onClick(View v) 
	            	{
	            		if(!(Chol.getText().toString().equals("")) && !(HDL.getText().toString().equals("")) || !(Tri.getText().toString().equals("")) || !(LDL.getText().toString().equals(""))){
	            			String TempValue = Chol.getText().toString();
		            		double CholV = Double.parseDouble(TempValue);
		            		TempValue = HDL.getText().toString();
		            		double HDLV = Double.parseDouble(TempValue);
		            
		            		STri = Tri.getText().toString();
		            		SChol = Chol.getText().toString();
		            		SHDL = HDL.getText().toString();
		            		SLDL = LDL.getText().toString();
		            		
		            		Chol_DHL = CholV/HDLV;
		            		SCHOL_LDL = String.valueOf(Chol_DHL);
		            		Ratio = (TextView) findViewById(R.id.lblRatio);
		            		Ratio.setText(Double.toString(Chol_DHL));
		            		if (Chol_DHL >= 5){
		            			Ratio.setTextColor(Color.rgb(255,0,0));
		            		}else if (Chol_DHL <= 4 ){
		            			Ratio.setTextColor(Color.rgb(50,205,50));
		            		}else{
		            			Ratio.setTextColor(Color.rgb(0,0,255));
		            		}
		            		Question.dismiss();
	            		}else{
	            			Toast.makeText(tabActivity_PatientHistory.this, "Chol & HDL Are Required", Toast.LENGTH_LONG).show();
	            		}
	            	}
	            });
	            Button btnCancel = (Button) Question.findViewById(R.id.btnCCancel);
	            btnCancel.setOnClickListener(new OnClickListener() {
	            @Override
	                public void onClick(View v) {
	            		Question.dismiss();
	                }
	            });
	      
	            Question.show();
				
				
			}
        	
        });
	
        txtHeightF = (EditText) findViewById(R.id.txtHeightF);
        txtHeightF.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if (!(txtHeightF.getText().toString().equals(""))){
	            		String TempInput = txtHeightF.getText().toString();
	            		int TempInt = Integer.parseInt(TempInput);
	            		HeightFoot = TempInt*30.48;
	            		CM();
					}
				}
			}
        });
        txtHeightI = (EditText) findViewById(R.id.txtHeightI);
        txtHeightI.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if (!(txtHeightI.getText().toString().equals(""))){
	            		String TempInput = txtHeightI.getText().toString();
	            		int TempInt = Integer.parseInt(TempInput);
	            		HeightInches = TempInt*2.54;
	            		CM();
					}
				}
			}
        });
        txtHeightC = (EditText) findViewById(R.id.txtHeightC);
        FluDate = (EditText) findViewById(R.id.txtLFJ);
        FluDate.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				showDialog(DATE_DIALOG_ID);
				return false;
			}
        });
        txtClinic = (EditText) findViewById(R.id.txtCAS);
        txtClinic.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtClinic.getText().toString().equals(""))){
						ClinicAndServices = txtClinic.getText().toString();
					}else{
						ClinicAndServices = "";
					}
				}
				
			}
        	
        });

        txtAddPro = (EditText) findViewById(R.id.txtAP);
        txtAddPro.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					if(!(txtAddPro.getText().toString().equals(""))){
						AddationalProblems = txtAddPro.getText().toString();
					}else{
						AddationalProblems = "";
					}
				}
				
			}
        });
        
        if(Activity_AddPatient.active == false){
        	ShowContact();
        }
	}
	
	public void CM(){
		HeightCM = HeightFoot + HeightInches;
		txtHeightC.setText(String.valueOf(HeightCM));
	}
	
	private void updateDisplay() {
        FluDate.setText(new StringBuilder()
                    // Month is 0 based so add 1
        			.append(mDay).append("/")
                    .append(mMonth + 1).append("/")
                    .append(mYear).append(" "));
        SFluDate = FluDate.getText().toString();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
        	 Calendar c = Calendar.getInstance();
             int DYear = c.get(Calendar.YEAR);
             int DMonth = c.get(Calendar.MONTH);
             int DDay = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(this,
                        mDateSetListener,
                        DYear, DMonth, DDay);
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
    private void ShowContact(){
    	DBHelper = new dbAccess(this);
    	DBHelper.createDatabase();
    	DBHelper.openDatabase();
    	
    	if(Activity_AddContact.SelectedPatient == true){
    		ContactCursor = DBHelper.getCursor("tblPatientContact", "patientID='" + Activity_AddContact.PatientID + "'",  null, null, "contactID ASC");
    		startManagingCursor(ContactCursor);
    		ContactCursor.moveToLast();
    	}
    	if(Activity_AddContact.ViewContact == true){
    		ContactCursor = DBHelper.getCursor("tblPatientContact", "contactID='" + Activity_AddContact.ContactID + "'",  null, null, null);
    		startManagingCursor(ContactCursor);
    		ContactCursor.moveToPosition(0);
    	}
    	DBHelper.close();
    	
    	
    	String Value = ContactCursor.getString(ContactCursor.getColumnIndex("causeOfHeartFailure"));
    	spnCHF.setSelection(Integer.valueOf(Value));

    	if(ContactCursor.getString(ContactCursor.getColumnIndex("iHD")).equals("Yes")){
    		spnIHD.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("iHD")).equals("No")){
    		spnIHD.setSelection(2);
    	}else{
    		spnIHD.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("ventricularArrhyhmia")).equals("Yes")){
    		spnVA.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("ventricularArrhyhmia")).equals("No")){
    		spnVA.setSelection(2);
    	}else{
    		spnVA.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("cardiacArrest")).equals("Yes")){
    		spnCA.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("cardiacArrest")).equals("No")){
    		spnCA.setSelection(2);
    	}else{
    		spnCA.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("AF")).equals("Yes")){
    		spnAF.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("AF")).equals("No")){
    		spnAF.setSelection(2);
    	}else{
    		spnAF.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("smoker")).equals("Yes")){
    		spnSmoker.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("smoker")).equals("No")){
    		spnSmoker.setSelection(2);
    	}else if (ContactCursor.getString(ContactCursor.getColumnIndex("smoker")).equals("Ex-")){
    		spnSmoker.setSelection(3);
    	}else{
    		spnSmoker.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("paceMaker")).equals("Yes")){
    		spnPM.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("paceMaker")).equals("No")){
    		spnPM.setSelection(2);
    	}else{
    		spnPM.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("overweight")).equals("Yes")){
    		spnOverweight.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("overweight")).equals("No")){
    		spnOverweight.setSelection(2);
    	}else{
    		spnOverweight.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("rheumaticFever")).equals("Yes")){
    		spnRF.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("rheumaticFever")).equals("No")){
    		spnRF.setSelection(2);
    	}else{
    		spnRF.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("valveDisease")).equals("Yes")){
    		spnVD.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("valveDisease")).equals("No")){
    		spnVD.setSelection(2);
    	}else{
    		spnVD.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("chrRenalFailure")).equals("Yes")){
    		spnCRF.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("chrRenalFailure")).equals("No")){
    		spnCRF.setSelection(2);
    	}else{
    		spnCRF.setSelection(0);
    	}
    	if(ContactCursor.getString(ContactCursor.getColumnIndex("diabetes")).equals("Yes")){
    		spnDiabetes.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("diabetes")).equals("No")){
    		spnDiabetes.setSelection(2);
    	}else{
    		spnDiabetes.setSelection(0);
    	}
       	if(ContactCursor.getString(ContactCursor.getColumnIndex("asthma")).equals("Yes")){
    		spnAsthma.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("asthma")).equals("No")){
    		spnAsthma.setSelection(2);
    	}else{
    		spnAsthma.setSelection(0);
    	}
       	if(ContactCursor.getString(ContactCursor.getColumnIndex("cLungDisease")).equals("Yes")){
    		spnLD.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("cLungDisease")).equals("No")){
    		spnLD.setSelection(2);
    	}else{
    		spnLD.setSelection(0);
    	}
       	if(ContactCursor.getString(ContactCursor.getColumnIndex("CVA")).equals("Yes")){
    		spnCVA.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("CVA")).equals("No")){
    		spnCVA.setSelection(2);
    	}else{
    		spnCVA.setSelection(0);
    	}
       	if(ContactCursor.getString(ContactCursor.getColumnIndex("PVD")).equals("Yes")){
    		spnPVD.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("PVD")).equals("No")){
    		spnPVD.setSelection(2);
    	}else{
    		spnPVD.setSelection(0);
    	}
       	FluDate.setText(ContactCursor.getString(ContactCursor.getColumnIndex("lastFluJab")).toString());
       	if(ContactCursor.getString(ContactCursor.getColumnIndex("pheumococcalJab")).equals("Yes")){
       		spnPneu.setSelection(1);
    	}else if(ContactCursor.getString(ContactCursor.getColumnIndex("pheumococcalJab")).equals("No")){
    		spnPneu.setSelection(2);
    	}else{
    		spnPneu.setSelection(0);
    	}
       	
		TableLayout Table = (TableLayout) findViewById(R.id.tblHistory);
		TableRow Header = (TableRow) findViewById(R.id.rowHeight);
		Table.removeView(Header);
       	
       	Ratio = (TextView) findViewById(R.id.lblRatio);     	
       	Ratio.setText(ContactCursor.getString(ContactCursor.getColumnIndex("CHOL_LDL")));
    	
		
//    	HC.setText(PatientCursor.getString(PatientCursor.getColumnIndex("HC")));		//Add Health and Care to database
  //  	Activity_AddPatient.PatientID = HC.getText().toString();
    //    FName.setText(PatientCursor.getString(PatientCursor.getColumnIndex("FName")));

    }
}


