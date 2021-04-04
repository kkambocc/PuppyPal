package ca.on.conestogac.puppypal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.Pet;

public class DBHandler
{

    PuppyPalApplication databaseHandler;

    public DBHandler(Context context)
    {
        databaseHandler = new PuppyPalApplication(context);
    }

    public long AddToTable(String tableName, ArrayList<String> values)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> columnNames = GetColumnNames(tableName);
        ContentValues contentValues = new ContentValues();

        for (int i = 1; i<columnNames.size();i++)
        {
            contentValues.put(columnNames.get(i),values.get(i));
        }
        long id = database.insert(tableName,null,contentValues);
        return id;
    }

    /*Read
     *
     */

    //return a single column
    public ArrayList ReadSingleColumn(String returnColumn, String tableName)
    {
        return ReadSingleColumn(returnColumn,tableName,null);
    }

    public ArrayList ReadSingleColumn(String returnColumn, String tableName,String orderBy)
    {
        ArrayList<String> values = new ArrayList<String>(){};
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        Cursor cursor = database.query(tableName,new String[]{returnColumn},null,null,null,null,orderBy + " ASC");
        while (cursor.moveToNext())
        {
            values.add(cursor.getString(cursor.getColumnIndex(returnColumn)));
        }
        return (values);
    }

    //return single entry
    public ArrayList ReadSingleEntry(String id, String tableName)
    {
        ArrayList<String> queryResults = new ArrayList();
        String[] searchColumns = new String[]{"*"};
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        Cursor cursor = database.query(tableName,searchColumns,null,null,null,null,null,"1");
        String columnName = cursor.getColumnName(0);
        cursor = database.query(tableName, searchColumns,columnName + " = ?",new String[]{id},null,null,null);
        while (cursor.moveToNext())
        {
            for (int i = 0;i<cursor.getColumnCount();i++)
            {
                queryResults.add(cursor.getString(i));
            }

        }
        return queryResults;
    }

    //reading column names of a provided table
    public ArrayList GetColumnNames(String tableName)
    {
        ArrayList queryResults = new ArrayList();
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        Cursor cursor = database.query(tableName,new String[]{"*"},null,null,null,null,null,"1");
        Collections.addAll(queryResults,cursor.getColumnNames());
        return queryResults;
    }


    /*Update
     *
     */

    //Delete Pet
    public void DeletePetFromTable(Pet pet)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        database.delete("tbl_pet","pet_id = ?",new String[]{"" + pet.getPetId()});
    }

    public void addAssistantToDB(String name,String phoneNumber,String address,String title,String generalDescription,boolean isUpdate,int updateID)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ContentValues assistantContentValues = new ContentValues();
        assistantContentValues.put("name",name);
        assistantContentValues.put("phone_number",phoneNumber);
        assistantContentValues.put("address",address);
        assistantContentValues.put("title",title);
        assistantContentValues.put("general_description",generalDescription);
        if (!isUpdate) {
            database.insertOrThrow("tbl_assistant", null, assistantContentValues);
        }
        else {
            database.update("tbl_assistant",assistantContentValues,"assistant_id = "+ updateID,null);
        }
    }
    public void deleteAssistant(int deleteID)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        database.delete("tbl_assistant","assistant_id = "+deleteID,null);
    }
    public Cursor getAssistantData()
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayMap<Integer,String> arrayMap =new ArrayMap<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_assistant",null);
        return cursor;
    }

    //Add Weight Fitness Goal
    public void addWeightFitnessGoal(double weight) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_weight", weight);
        Log.d("addFitnessGoal", "addData: " + weight);

        long id = db.insert("tbl_fitness_goal",null,contentValues);
    }
    //Add Energy Fitness Goal
    public void addEnergyFitnessGoal(int energy) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_energy_level", energy);
        Log.d("addFitnessGoal", "addData: " + energy);

        long id = db.insert("tbl_fitness_goal",null,contentValues);
    }
    //Add Exercise Fitness Goal
    public void addExerciseFitnessGoal(String exerciseType, long exerciseDuration) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_exercise_type", exerciseType);
        Log.d("addFitnessGoal", "addData: " + exerciseType);
        contentValues.put("target_exercise_duration", exerciseDuration);
        Log.d("addFitnessGoal", "addData: " + exerciseDuration);

        long id = db.insert("tbl_fitness_goal", null,contentValues);
    }

    //Code to implement database
    static class PuppyPalApplication extends SQLiteOpenHelper {
        private static final String DB_NAME = "DB_PuppyPal";
        private static final int DB_VERSION = 1;
        private final Context context;

        private SQLiteOpenHelper sqLiteOpenHelper;

        public PuppyPalApplication(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {
            // No boolean or date/time variable type?

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_pet(" +
                    "pet_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "age INTEGER, " +
                    "weight REAL, " +
                    "breed TEXT, " +
                    "gender INTEGER, " +
                    "spayed_neutered INTEGER)");

            db.execSQL("CREATE TABLE IF NOT EXISTS "+ EnergyRecord.TABLE_NAME + "(" +
                    "energy_level_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "pet_id INTEGER, " +
                    "date TEXT, " +
                    "energy_level INTEGER, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_excrement(" +
                    "excrement_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "pet_id INTEGER, " +
                    "date TEXT, " +
                    "abnormalties TEXT, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_exercise(" +
                    "exercise_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "pet_id INTEGER, " +
                    "date TEXT, " +
                    "type TEXT, " +
                    "duration REAL, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_meal(" +
                    "meal_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "pet_id INTEGER, " +
                    "date TEXT, " +
                    "amount REAL, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_weight(" +
                    "weight_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "pet_id INTEGER, " +
                    "date TEXT, " +
                    "weight REAL, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_assistant(" +
                    "assistant_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "phone_number TEXT, " +
                    "address TEXT, " +
                    "title TEXT, " +
                    "general_description TEXT)");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_fitness_goal(" +
                    "fitness_goal_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "target_weight REAL, " +
                    "target_energy_level INTEGER, " +
                    "target_exercise_type TEXT, " +
                    "target_exercise_duration REAL)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // no-op
        }

    }
}
