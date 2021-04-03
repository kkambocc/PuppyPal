package ca.on.conestogac.puppypal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExcrementRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.MealRecord;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

public class DBHandler
{

    PuppyPalApplication databaseHandler;

    public DBHandler(Context context)
    {
        databaseHandler = new PuppyPalApplication(context);
    }

    public void AddToTable(String tableName, ArrayList<String> values)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> columnNames = GetColumnNames(tableName);
        ContentValues contentValues = new ContentValues();

        for (int i = 1; i < columnNames.size(); i++)
        {
            contentValues.put(columnNames.get(i), values.get(i));
        }
        database.insert(tableName, null, contentValues);
    }

    /*Read
     *
     */

    //return a single column
    public ArrayList<String> ReadSingleColumn(String returnColumn, String tableName)
    {
        return ReadSingleColumn(returnColumn, tableName, null);
    }

    public ArrayList<String> ReadSingleColumn(String returnColumn, String tableName, String orderBy)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> values = new ArrayList<String>()
        {
        };
        Cursor cursor = database.query(tableName, new String[]{returnColumn}, null, null, null, null, orderBy + " ASC");
        while (cursor.moveToNext())
        {
            values.add(cursor.getString(cursor.getColumnIndex(returnColumn)));
        }
        cursor.close();
        return (values);
    }

    public ArrayList<String> MostRecentWeight(long petId)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> queryResults = new ArrayList<>();
        Cursor cursor = database.query(WeightRecord.TABLE_NAME, new String[]{WeightRecord.COLUMN_NAMES[1]}, Pet.PRIMARY_KEY + " = ?", new String[]{"" + petId}, null, null, WeightRecord.COLUMN_NAMES[0], "1");
        while (cursor.moveToNext())
        {
            for (int i = 0; i < cursor.getColumnCount(); i++)
            {
                queryResults.add(cursor.getString(i));
            }
        }
        cursor.close();
        return queryResults;
    }

    //return single entry
    public ArrayList<String> ReadSingleEntry(String id, String tableName)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        Cursor cursor = database.query(tableName, new String[]{"*"}, null, null, null, null, null, "1");
        String columnName = cursor.getColumnName(0);
        cursor.close();
        return ReadSingleEntry(id,tableName,columnName,null);
    }

    public ArrayList<String> ReadSingleEntry( String selection, String selectionValue, String tableName,String orderBy)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> queryResults = new ArrayList<>();
        Cursor cursor = database.query(tableName, new String[]{"*"}, selection + " = ?", new String[]{selectionValue}, null, null, orderBy,"1");
        while (cursor.moveToNext())
        {
            for (int i = 0; i < cursor.getColumnCount(); i++)
            {
                queryResults.add(cursor.getString(i));
            }

        }
        cursor.close();
        return queryResults;
    }

    //reading column names of a provided table
    public ArrayList<String> GetColumnNames(String tableName)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> queryResults = new ArrayList<>();
        Cursor cursor = database.query(tableName, new String[]{"*"}, null, null, null, null, null, "1");
        Collections.addAll(queryResults, cursor.getColumnNames());
        cursor.close();
        return queryResults;
    }


    /*Update
     *
     */

    public void DeleteFromTable(String tableName, Long id)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        String whereClause;
        switch (tableName)
        {
            case Pet.TABLE_NAME:
                whereClause = Pet.PRIMARY_KEY;
                break;
            case WeightRecord.TABLE_NAME:
                whereClause = WeightRecord.PRIMARY_KEY;
                break;
            case EnergyRecord.TABLE_NAME:
                whereClause = EnergyRecord.PRIMARY_KEY;
                break;
            case ExcrementRecord.TABLE_NAME:
                whereClause = ExcrementRecord.PRIMARY_KEY;
                break;
            case ExerciseRecord.TABLE_NAME:
                whereClause = ExerciseRecord.PRIMARY_KEY;
                break;
            //case MealRecord.TABLE_NAME:
            default:
                whereClause = MealRecord.PRIMARY_KEY;
                break;
        }
        whereClause = whereClause + " = ?";
        database.delete(tableName, whereClause, new String[]{id.toString()});
    }

    public void addAssistantToDB(String name, String phoneNumber, String address, String title, String generalDescription, boolean isUpdate, int updateID)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ContentValues assistantContentValues = new ContentValues();
        assistantContentValues.put("name", name);
        assistantContentValues.put("phone_number", phoneNumber);
        assistantContentValues.put("address", address);
        assistantContentValues.put("title", title);
        assistantContentValues.put("general_description", generalDescription);
        if (!isUpdate)
        {
            database.insertOrThrow("tbl_assistant", null, assistantContentValues);
        } else
        {
            database.update("tbl_assistant", assistantContentValues, "assistant_id = " + updateID, null);
        }
    }

    public void deleteAssistant(int deleteID)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        database.delete("tbl_assistant", "assistant_id = " + deleteID, null);
    }

    public Cursor getAssistantData()
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_assistant", null);
        cursor.close();
        return cursor;
    }

    //Add Weight Fitness Goal
    public void addWeightFitnessGoal(double weight)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_weight", weight);
        Log.d("addFitnessGoal", "addData: " + weight);

        database.insert("tbl_fitness_goal", null, contentValues);
    }

    //Add Energy Fitness Goal
    public void addEnergyFitnessGoal(int energy)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_energy_level", energy);
        database.insert("tbl_fitness_goal", null, contentValues);
        Log.d("addFitnessGoal", "addData: " + energy);
    }

    //Add Exercise Fitness Goal
    public void addExerciseFitnessGoal(String exerciseType, String exerciseDuration)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_exercise_type", exerciseType);
        Log.d("addFitnessGoal", "addData: " + exerciseType);
        contentValues.put("target_exercise_duration", exerciseDuration);
        Log.d("addFitnessGoal", "addData: " + exerciseDuration);

        database.insert("tbl_fitness_goal", null, contentValues);
    }

    //Code to implement database
    static class PuppyPalApplication extends SQLiteOpenHelper
    {
        private static final String DB_NAME = "DB_PuppyPal";
        private static final int DB_VERSION = 1;


        public PuppyPalApplication(Context context)
        {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + Pet.TABLE_NAME + "(" +
                    Pet.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.COLUMN_NAMES[0] + " TEXT, " +
                    Pet.COLUMN_NAMES[1] + " INTEGER, " +
                    Pet.COLUMN_NAMES[2] + " TEXT, " +
                    Pet.COLUMN_NAMES[3] + " INTEGER, " +
                    Pet.COLUMN_NAMES[4] + " INTEGER)");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + EnergyRecord.TABLE_NAME + "(" +
                    EnergyRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.PRIMARY_KEY + " INTEGER, " +
                    EnergyRecord.COLUMN_NAMES[0] + " TEXT, " +
                    EnergyRecord.COLUMN_NAMES[1] + "energy_level INTEGER, " +
                    "FOREIGN KEY (" + Pet.PRIMARY_KEY + ") REFERENCES " + Pet.TABLE_NAME + " (" + Pet.PRIMARY_KEY + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + ExcrementRecord.TABLE_NAME + "(" +
                    ExcrementRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.TABLE_NAME + " INTEGER, " +
                    ExcrementRecord.COLUMN_NAMES[0] + "date TEXT, " +
                    ExcrementRecord.COLUMN_NAMES[1] + " TEXT, " +
                    "FOREIGN KEY (" + Pet.TABLE_NAME + ") REFERENCES " + Pet.PRIMARY_KEY + " (" + Pet.TABLE_NAME + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + ExerciseRecord.TABLE_NAME + "(" +
                    ExerciseRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.TABLE_NAME + " INTEGER, " +
                    ExerciseRecord.COLUMN_NAMES[0] + " TEXT, " +
                    ExerciseRecord.COLUMN_NAMES[1] + " TEXT, " +
                    ExerciseRecord.COLUMN_NAMES[2] + " REAL, " +
                    "FOREIGN KEY (" + Pet.TABLE_NAME + ") REFERENCES " + Pet.PRIMARY_KEY + " (" + Pet.TABLE_NAME + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + MealRecord.TABLE_NAME + "(" +
                    MealRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.TABLE_NAME + " INTEGER, " +
                    MealRecord.COLUMN_NAMES[0] + " TEXT, " +
                    MealRecord.COLUMN_NAMES[1] + " REAL, " +
                    "FOREIGN KEY (" + Pet.TABLE_NAME + ") REFERENCES " + Pet.PRIMARY_KEY + " (" + Pet.TABLE_NAME + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + WeightRecord.TABLE_NAME + "(" +
                    WeightRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.TABLE_NAME + " INTEGER, " +
                    WeightRecord.COLUMN_NAMES[0] + " TEXT, " +
                    WeightRecord.COLUMN_NAMES[1] + " REAL, " +
                    "FOREIGN KEY (" + Pet.TABLE_NAME + ") REFERENCES " + Pet.PRIMARY_KEY + " (" + Pet.TABLE_NAME + "))");

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
                    "target_exercise_duration TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // no-op
        }
    }
}
