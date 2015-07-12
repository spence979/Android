package test.athena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Environment;
import android.util.Log;

public class dbAccess extends SQLiteOpenHelper{
	
	//Declare Variables
	 private static final String DATABASE_PATH = "/data/data/test.athena/databases/";
	 private static final String DATABASE_NAME = "Athenadb.db";
	 private static final int SCHEMA_VERSION=1;
	 
	 public Cursor CursorValues = null;
	 //public static Cursor ContactCursor = null;
	 public int SelectedPatient;
	 
	 public SQLiteDatabase dbSqlite;
	 
	 public final Context myContext;
	 
	 public dbAccess (Context context)
	 {
		 super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		 this.myContext = context;
	 }
	 
	 public void populateDB(){
		 
	 }
	 
	 @Override
	 public void onCreate(SQLiteDatabase db)
	 {
		 
	 }
	 
	 @Override
	 public void onUpgrade(SQLiteDatabase db, int oldVerion, int newVersion) {
		 
	 }
	 
	 public void createDatabase() {
		 createDB();
	 }
	 private void createDB() {
		 boolean dbExist = DBExists();
		 
		 if (!dbExist){
			 this.getReadableDatabase();
			 copyDBFromResource();
		 }
	 }
	 
	 public void CreateEntryPatient(String HC, String FName, String LName, String Gender, String DOB, String Age, String MaritalStatus, String HouseNo, 
			 String Address1, String Address2, String PostCode,String TelephoneNo, String MobileNo, String NOK, String NOKC){
			ContentValues CV = new ContentValues();
			CV.put("HC", HC);
			CV.put("FName", FName);
			CV.put("LName", LName);
			CV.put("dateOfBirth", DOB);
			CV.put("age", Age);
			CV.put("address1", Address1);
			CV.put("address2", Address2);
			CV.put("postCode", PostCode);
			CV.put("telNo", TelephoneNo);
			CV.put("mobileNo", MobileNo);
			CV.put("maritalStatus", MaritalStatus);
			CV.put("nextOfKin", NOK);
			CV.put("nextOfKinTel", NOKC);
			CV.put("houseNo", HouseNo);
			CV.put("gender", Gender);
			getWritableDatabase().insert("tblPatients", "_id", CV); 
		}
	 public void AddPatientContact(String[] Values){
			ContentValues CV = new ContentValues();
			try{
				CV.put("patientID", Values[0]);
				CV.put("contactID", Values[1]);
				CV.put("contactDate", Values[2]);
				CV.put("CCSA", Values[3]);
				CV.put("NYHA", Values[4]);
				CV.put("atRest", Values[5]);
				CV.put("onExertion", Values[6]);
				CV.put("noctural", Values[7]);
				CV.put("orthopnoea", Values[8]);
				CV.put("fatigue", Values[9]);
				CV.put("dizziness", Values[10]);
				CV.put("chestPain", Values[11]);
				CV.put("palpitations", Values[12]);
				CV.put("cough", Values[13]);
				CV.put("angina", Values[14]);
				CV.put("heartRate", Values[15]);
				CV.put("bloodPressure1", Values[16]);
				CV.put("bloodPressure2", Values[17]);
				CV.put("oedema", Values[18]);
				CV.put("ascities", Values[19]);
				CV.put("weight", Values[20]);
				CV.put("JVP", Values[21]);
				CV.put("creps", Values[22]);
				CV.put("standingBloodPressure1", Values[23]);
				CV.put("standingBloodPressure2", Values[24]);
				CV.put("heartSounds", Values[25]);
				CV.put("causeOfHeartFailure", Values[26]);
				CV.put("iHD", Values[27]);
				CV.put("ventricularArrhyhmia", Values[28]);
				CV.put("cardiacArrest", Values[29]);
				CV.put("AF", Values[30]);
				CV.put("smoker", Values[31]);
				CV.put("TRI", Values[32]);
				CV.put("CHOL", Values[33]);
				CV.put("HDL", Values[34]);
				CV.put("LDL", Values[35]);
				
				CV.put("CHOL_LDL", Values[36]);
				CV.put("paceMaker", Values[37]);
				CV.put("overweight", Values[38]);
				CV.put("rheumaticFever", Values[39]);
				CV.put("valveDisease", Values[40]);
				CV.put("chrRenalFailure", Values[41]);
				CV.put("diabetes", Values[42]);
				CV.put("asthma", Values[43]);
				CV.put("cLungDisease", Values[44]);
				CV.put("CVA", Values[45]);
				CV.put("PVD", Values[46]);
				CV.put("additionalProblems", Values[47]);
				CV.put("clinicAndServices", Values[48]);
				CV.put("lastFluJab", Values[49]);
				CV.put("pheumococcalJab", Values[50]);
				CV.put("admissionDate", Values[51]);
				CV.put("dischargeDate", Values[52]);
				CV.put("ward", Values[53]);
				CV.put("consultant", Values[54]);
				CV.put("destination", Values[55]);
				
				CV.put("homeVisit", Values[56]);
				CV.put("clinicAppt", Values[57]);
				CV.put("otherComments", Values[58]);
				CV.put("generally", Values[59]);
				CV.put("rhythm", Values[60]);
				
				getWritableDatabase().insert("tblPatientContact", "_id", CV); 
			}catch (Exception e) {
				
			}
	 }
	 public void AddBloodTests(String ContactID, String ContactDate, String PatientID, String Urea, String Na, String K, String Glucose, String Hb, String WCC, String Platelets, String TFT, String LFT, String Creat){
		 
			ContentValues CV = new ContentValues();
			CV.put("patientID", PatientID);
			CV.put("contactID", ContactID);
			CV.put("contactDate", ContactDate);
			CV.put("Na", Na);
			CV.put("K", K);
			CV.put("Glucose", Glucose);
			CV.put("Hb", Hb);
			CV.put("WCC", WCC);
			CV.put("Platelets", Platelets);
			CV.put("TFT", TFT);
			CV.put("LFT", LFT);
			CV.put("Creat", Creat);
			CV.put("Urea", Urea);
			getWritableDatabase().insert("tblBloodTests", "_id", CV); 
	 }
	 public void AddMedication(String ContactID, String ContactDate, String PatientID, ArrayList<String> MedType, ArrayList<String> MedName, ArrayList<String> MedDose, ArrayList<String> MedFreq){
			ContentValues CV = new ContentValues();
			int size = MedType.size();
			for (int i = 0; i < size; i ++){
				CV.put("patientID", PatientID);
				CV.put("contactID", ContactID);
				CV.put("contactDate", ContactDate);
				CV.put("medType", MedType.get(i).toString());
				CV.put("medName", MedName.get(i).toString());
				CV.put("medDose", MedDose.get(i).toString());
				CV.put("medFreq", MedFreq.get(i).toString());
				getWritableDatabase().insert("tblMedications", null, CV); 
			}
	 }
	 public boolean AddUser(String UserName, String Password, String FirstName, String LastName){
		 boolean Added = false;
		 String databasePath = DATABASE_PATH + DATABASE_NAME;
		 SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE); 
		 try{
			db.execSQL("INSERT INTO tblStaff (username, password, fName, lName) values " + "('"+ UserName +"','" + Password + "','" + FirstName + "','" + LastName + "')");
			Added = true;
			}catch (Exception e){
				Added = false;
			} finally{
				db.close();
			}
		 return Added;
	 }
	 public void UpdatePatient(String HC, String FName, String LName, String Gender, String DOB, String Age, String MaritalStatus, String HouseNo, String Address1, String Address2, String PostCode,String TelephoneNo, String MobileNo, String NOK, String NOKC){
			ContentValues CV = new ContentValues();
			String[] args={HC};
			CV.put("FName", FName);
			CV.put("LName", LName);
			CV.put("dateOfBirth", DOB);
			CV.put("age", Age);
			CV.put("address1", Address1);
			CV.put("address2", Address2);
			CV.put("postCode", PostCode);
			CV.put("telNo", TelephoneNo);
			CV.put("mobileNo", MobileNo);
			CV.put("maritalStatus", MaritalStatus);
			CV.put("nextOfKin", NOK);
			CV.put("nextOfKinTel", NOKC);
			CV.put("houseNo", HouseNo);
			CV.put("gender", Gender);
			getWritableDatabase().update("tblPatients", CV, "HC=?", args);
		}
	 public boolean ChangePassword(String UserName, String OldPassword, String NewPassword, Cursor c) {
		 int passwordColumn = c.getColumnIndex("password");
		 int UsernameColumn = c.getColumnIndex("username");
		 boolean Changed = false;
		 if(c != null)
		 {
			 for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				 String CheckPassword = c.getString(passwordColumn);
				 String CheckUserName = c.getString(UsernameColumn);
				 if (CheckPassword.toString().equalsIgnoreCase(OldPassword) && CheckUserName.toString().equalsIgnoreCase(UserName))
				 {
					 String databasePath = DATABASE_PATH + DATABASE_NAME;
					 SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE); 
					 try{
						 db.execSQL("UPDATE tblStaff SET password='"+NewPassword.toString()+"' WHERE username='"+ UserName +"' AND password='"+ OldPassword +"'");
						 Changed = true;
					 } catch (Exception e){
						 Changed = false;
					 } finally{
						 db.close();
					 }
				 }
			 }
		 }
		 return Changed;
	 }

	 public boolean Remove(String ID, String Table){
		boolean Deleted = false;
		String databasePath = DATABASE_PATH + DATABASE_NAME;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE); 
		try{
			db.execSQL("DELETE FROM " + Table + " WHERE _id='"+ ID +"'");
			Deleted = true;
		} catch (Exception e){
			Deleted = false;
		} finally{
			db.close();
		}
		return Deleted;
	}
	 public void AddAppointment(String PatientID, String AppointDate){
		 ContentValues CV = new ContentValues();
		 CV.put("patientID", PatientID);
		 CV.put("date", AppointDate);
		 getWritableDatabase().insert("tblAppointments", "_id", CV); 
	 }
	 
	 private boolean DBExists(){
		 SQLiteDatabase db = null;
		 try {
			 String databasePath = DATABASE_PATH + DATABASE_NAME;
			 db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
			 db.setLocale(Locale.getDefault());
			 db.setLockingEnabled(true);
			 db.setVersion(1);
		 } catch (SQLiteException e) {
			 Log.e("SqlHelper", "Database Not Found");
		 }
		 
		 if (db != null) {
			 db.close();
		 }
		 return db != null ? true : false;
	 }
	 
	 private void copyDBFromResource() {
		 InputStream inputStream = null;
		 OutputStream outStream = null;
		 String dbFilePath = DATABASE_PATH + DATABASE_NAME;
		 
		 try {
			 inputStream = myContext.getAssets().open(DATABASE_NAME);
			 outStream = new FileOutputStream(dbFilePath);
			 
			 byte[] buffer = new byte[1024];
			 int length;
			 while ((length = inputStream.read(buffer)) > 0) {
				 outStream.write(buffer, 0 , length);
			 }
			 outStream.flush();
			 outStream.close();
			 inputStream.close();
		 } catch (IOException e) {
			 throw new Error("Error Copying DB");
		 }
	 }
	 
	 public void CopyNewDatabase() {
			try 
			{
			    File sd = Environment.getExternalStorageDirectory();
			    File data = Environment.getDataDirectory();
			    String GetDBS = "/Athena/Marged/Athenadb.db";
			    String PutDBS = "/data/test.athena/databases/Athenadb.db";
			    File GetDB = new File(sd, GetDBS);
			    File PutDB = new File(data, PutDBS);
		
			    if (GetDB.exists()) 
			    {
			    	FileChannel src = new FileInputStream(GetDB).getChannel();
			        FileChannel dst = new FileOutputStream(PutDB).getChannel();
			        dst.transferFrom(src, 0, src.size());
			        src.close();
			        dst.close();
			    }
			} 
			catch (Exception e) 
			{
				throw new Error("Error Copying DB");
			}
	 }
	 
	 @Override
	 public synchronized void close() {
		 if (dbSqlite != null)
		 {
			 dbSqlite.close();
		 }
		 super.close();
	 }
	 
	 public Cursor getCursor(String Table, String Where , String Row1, String Row2, String OrderBy){
		 SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		 queryBuilder.setTables(Table);
		 CursorValues = queryBuilder.query(dbSqlite, null, Where , null, null, null, null);
		 return CursorValues;
	 }

	 public String getName(Cursor ourCursor) {
		 int FirstNameColumn = ourCursor.getColumnIndex("FName");
		 int LastNameColumn = ourCursor.getColumnIndex("LName");
		 String FullName = (ourCursor.getString(FirstNameColumn) + " " + ourCursor.getString(LastNameColumn));
		 return(FullName);
	 }
	 public String getID(Cursor ourCursor) {
		 int IDColumn = ourCursor.getColumnIndex("HC");
		 String ID =  (ourCursor.getString(IDColumn));
		 return(ID);
	 }
	 public static Cursor getByID(Cursor SelectedpatientCursor, int SelectedID) {
		 SelectedpatientCursor.moveToPosition(SelectedID);
		 return SelectedpatientCursor;
	 }
	 public String  GetContactDate(Cursor PatientContact){
		 int FirstNameColumn = PatientContact.getColumnIndex("contactDate");
		 String ContactDate = (PatientContact.getString(FirstNameColumn));
		 return(ContactDate);
	 }
	 
	 public String GetContactID(Cursor PatientContact){
		 int FirstNameColumn = PatientContact.getColumnIndex("contactID");
		 String ContactID = (PatientContact.getString(FirstNameColumn));
		 return ContactID;
	 }
	 
	 public String checkUserDetails(Cursor ourCursor,String UName, String PWord){
		 String StaffName = "";
		 int usernameColumn = ourCursor.getColumnIndex("username");
		 int passwordColumn = ourCursor.getColumnIndex("password");
		 int FNameColum = ourCursor.getColumnIndex("fName");
		 int LNameColum = ourCursor.getColumnIndex("lName");
		 	
		 if(ourCursor != null)
		 {
			 for (ourCursor.moveToFirst(); !ourCursor.isAfterLast(); ourCursor.moveToNext()){
				 String Username = ourCursor.getString(usernameColumn);
				 String Password = ourCursor.getString(passwordColumn);
				 if (Username.toString().equalsIgnoreCase(UName) && Password.toString().equalsIgnoreCase(PWord))
				 {
					 StaffName = (ourCursor.getString(FNameColum) + " " + ourCursor.getString(LNameColum));
				 }	
			 }
		 }
		 return (StaffName);
	 }
	 
	 public void setSelectedPatient(int Value)
	 {
		 SelectedPatient = Value;
	 }
	 
	 public void openDatabase() throws SQLException {
		 String myPath = DATABASE_PATH + DATABASE_NAME;
		 dbSqlite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	 }

	public void TransferDatabase(){
		try 
		{
		    File sd = Environment.getExternalStorageDirectory();
		    File data = Environment.getDataDirectory();
		    sd.setWritable(true);
		    String currentDBPath = "/data/test.athena/databases/Athenadb.db";
		    String backupDBPath = "Athena/Athenadb.db";
		    File currentDB = new File(data, currentDBPath);
		    File backupDB = new File(sd, backupDBPath);
	
		    if (currentDB.exists()) 
		    {
		    	FileChannel src = new FileInputStream(currentDB).getChannel();
		        FileChannel dst = new FileOutputStream(backupDB).getChannel();
		        dst.transferFrom(src, 0, src.size());
		        src.close();
		        dst.close();
		    }
		} 
		catch (Exception e) 
		{
			throw new Error("Error Copying DB");
		}
	}
}