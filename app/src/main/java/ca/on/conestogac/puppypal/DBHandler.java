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

    /*
    Create
     */
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

    /*
    Read
     */

    //return a single column
    public ArrayList<String> ReadSingleColumn(String returnColumn, String tableName)
    {
        return ReadSingleColumn(returnColumn, tableName, null,null);
    }

    public ArrayList<String> ReadSingleColumn(String returnColumn, String tableName, String orderBy)
    {
        return ReadSingleColumn(returnColumn, tableName, orderBy,null);
    }

    public ArrayList<String> ReadSingleColumn(String returnColumn, String tableName, String orderBy, String limit)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> values = new ArrayList<String>()
        {
        };
        Cursor cursor = database.query(tableName, new String[]{returnColumn}, null, null, null, null, orderBy,limit);
        while (cursor.moveToNext())
        {
            values.add(cursor.getString(cursor.getColumnIndex(returnColumn)));
        }
        cursor.close();
        return (values);
    }


    //return single entry
    public ArrayList<String> ReadSingleEntry(String id, String tableName)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        Cursor cursor = database.query(tableName, new String[]{"*"}, null, null, null, null, null, "1");
        String columnName = cursor.getColumnName(0);
        cursor.close();
        return ReadSingleEntry(columnName, id, tableName, null);
    }

    public ArrayList<String> ReadSingleEntry(String selection, String selectionValue, String tableName, String orderBy)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> queryResults = new ArrayList<>();
        Cursor cursor = database.query(tableName, new String[]{"*"}, selection + " = ?", new String[]{selectionValue}, null, null, orderBy, "1");
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


    /*
    Update
     */
    public void UpdateTable(String tableName, ArrayList<String> values, String primaryKey, String id)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> columnNames = GetColumnNames(tableName);
        ContentValues contentValues = new ContentValues();
        for (int i = 1; i < columnNames.size(); i++)
        {
            contentValues.put(columnNames.get(i), values.get(i));
        }
        database.update(tableName,contentValues,primaryKey + " = " + id,null);
    }

    /*
    Delete
     */

    public void DeleteFromTable(String tableName, String whereClause, Long id)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        whereClause = whereClause + " = ?";
        database.delete(tableName, whereClause, new String[]{id.toString()});
    }

    /**
     * Will be removed when assistant class is created.
     * To be replaced by {@link #AddToTable(String, ArrayList)}
     * @param name
     * @param phoneNumber
     * @param address
     * @param title
     * @param generalDescription
     * @param isUpdate
     * @param updateID
     */
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
        }
        else
        {
            database.update("tbl_assistant", assistantContentValues, "assistant_id = " + updateID, null);
        }
    }

    /**
     * Will be removed when assistant class is created.
     * To be replaced by {@link #DeleteFromTable(String, String, Long)}
     * @param deleteID
     */
    public void deleteAssistant(int deleteID)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        database.delete("tbl_assistant", "assistant_id = " + deleteID, null);
    }

    //Add Weight Fitness Goal

    /**
     * Will be removed when fitness goal class is created.
     * To be replaced by {@link #AddToTable(String, ArrayList)}
     * @param weight
     */
    public void addWeightFitnessGoal(double weight)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_weight", weight);
        Log.d("addFitnessGoal", "addData: " + weight);

        database.insert("tbl_fitness_goal", null, contentValues);
    }

    //Add Energy Fitness Goal

    /**
     * Will be removed when fitness goal class is created.
     * To be replaced by {@link #AddToTable(String, ArrayList)}
     * @param energy
     */
    public void addEnergyFitnessGoal(int energy)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_energy_level", energy);
        Log.d("addFitnessGoal", "addData: " + energy);

        database.insert("tbl_fitness_goal",null,contentValues);
    }

    //Add Exercise Fitness Goal

    /**
     * Will be removed when fitness goal class is created.
     * To be replaced by {@link #AddToTable(String, ArrayList)}
     * @param exerciseType
     * @param exerciseDuration
     */
    public void addExerciseFitnessGoal(String exerciseType, long exerciseDuration)
    {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("target_exercise_type", exerciseType);
        Log.d("addFitnessGoal", "addData: " + exerciseType);
        contentValues.put("target_exercise_duration", exerciseDuration);
        Log.d("addFitnessGoal", "addData: " + exerciseDuration);

        db.insert("tbl_fitness_goal", null,contentValues);

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
                    EnergyRecord.COLUMN_NAMES[1] + " INTEGER, " +
                    "FOREIGN KEY (" + Pet.PRIMARY_KEY + ") REFERENCES " + Pet.TABLE_NAME + " (" + Pet.PRIMARY_KEY + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + ExcrementRecord.TABLE_NAME + "(" +
                    ExcrementRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.PRIMARY_KEY + " INTEGER, " +
                    ExcrementRecord.COLUMN_NAMES[0] + "date TEXT, " +
                    ExcrementRecord.COLUMN_NAMES[1] + " TEXT, " +
                    "FOREIGN KEY (" + Pet.PRIMARY_KEY + ") REFERENCES " + Pet.TABLE_NAME + " (" + Pet.PRIMARY_KEY + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + ExerciseRecord.TABLE_NAME + "(" +
                    ExerciseRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.PRIMARY_KEY + " INTEGER, " +
                    ExerciseRecord.COLUMN_NAMES[0] + " TEXT, " +
                    ExerciseRecord.COLUMN_NAMES[1] + " TEXT, " +
                    ExerciseRecord.COLUMN_NAMES[2] + " REAL, " +
                    "FOREIGN KEY (" + Pet.PRIMARY_KEY + ") REFERENCES " + Pet.TABLE_NAME + " (" + Pet.PRIMARY_KEY + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + MealRecord.TABLE_NAME + "(" +
                    MealRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.PRIMARY_KEY + " INTEGER, " +
                    MealRecord.COLUMN_NAMES[0] + " TEXT, " +
                    MealRecord.COLUMN_NAMES[1] + " REAL, " +
                    "FOREIGN KEY (" + Pet.PRIMARY_KEY + ") REFERENCES " + Pet.TABLE_NAME + " (" + Pet.PRIMARY_KEY + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + WeightRecord.TABLE_NAME + "(" +
                    WeightRecord.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.PRIMARY_KEY + " INTEGER, " +
                    WeightRecord.COLUMN_NAMES[0] + " TEXT, " +
                    WeightRecord.COLUMN_NAMES[1] + " REAL, " +
                    "FOREIGN KEY (" + Pet.PRIMARY_KEY + ") REFERENCES " + Pet.TABLE_NAME + " (" + Pet.PRIMARY_KEY + "))");

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
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // no-op
        }
    }
}
