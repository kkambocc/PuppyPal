package ca.on.conestogac.puppypal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

import ca.on.conestogac.puppypal.activities.App;
import ca.on.conestogac.puppypal.tables.Assistant;
import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExcrementRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.FitnessGoal;
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

    //return a single column from a single record
    public String ReadSingleValue(String returnColumn, String tableName, String primaryKeyName, String primaryKey)
    {
        SQLiteDatabase database = databaseHandler.getReadableDatabase();
        Cursor cursor = database.query(tableName,new String[]{returnColumn},primaryKeyName + " = ?", new String[]{primaryKey},null,null,null,"1");
        cursor.moveToFirst();
        String result = cursor.getString(cursor.getColumnIndex(returnColumn));
        cursor.close();
        return result;
    }

    //return a single column
    public ArrayList<String> ReadSingleColumn(String returnColumn, String tableName)
    {
        return ReadSingleColumn(returnColumn, tableName, null, null);
    }

    public ArrayList<String> ReadSingleColumn(String returnColumn, String tableName, String orderBy)
    {
        return ReadSingleColumn(returnColumn, tableName, orderBy, null);
    }

    public ArrayList<String> ReadSingleColumn(String returnColumn, String tableName, String orderBy, String limit)
    {
        SQLiteDatabase database = databaseHandler.getWritableDatabase();
        ArrayList<String> values = new ArrayList<String>()
        {
        };
        Cursor cursor = database.query(tableName, new String[]{returnColumn}, null, null, null, null, orderBy, limit);
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
        database.update(tableName, contentValues, primaryKey + " = " + id, null);
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

    //Code to implement database
    static class PuppyPalApplication extends SQLiteOpenHelper
    {
        private static final String DB_NAME = App.getContext().getString(R.string.db_name);
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
                    ExcrementRecord.COLUMN_NAMES[0] + " TEXT, " +
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

            db.execSQL("CREATE TABLE IF NOT EXISTS " + Assistant.TABLE_NAME + "(" +
                    Assistant.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Assistant.COLUMN_NAMES[0] + " TEXT, " +
                    Assistant.COLUMN_NAMES[1] + " TEXT, " +
                    Assistant.COLUMN_NAMES[2] + " TEXT, " +
                    Assistant.COLUMN_NAMES[3] + " TEXT, " +
                    Assistant.COLUMN_NAMES[4] + " TEXT)");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + FitnessGoal.TABLE_NAME + "(" +
                    FitnessGoal.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Pet.PRIMARY_KEY + " INTEGER, " +
                    FitnessGoal.COLUMN_NAMES[0] + " REAL, " +
                    FitnessGoal.COLUMN_NAMES[1] + " INTEGER, " +
                    FitnessGoal.COLUMN_NAMES[2] + " TEXT, " +
                    FitnessGoal.COLUMN_NAMES[3] + " REAL, " +
                    "FOREIGN KEY (" + Pet.PRIMARY_KEY + ") REFERENCES " + Pet.TABLE_NAME + " (" + Pet.PRIMARY_KEY + "))");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // no-op
        }
    }
}