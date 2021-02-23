package ca.on.conestogac.puppypal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler
{
    PuppyPalApplication databaseHandler;

    public DBHandler(Context context)
    {
        databaseHandler = new PuppyPalApplication(context);
    }

    //Create
    public long AddToPetTable(Pet pet) {
        SQLiteDatabase dbb = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", pet.getName());
        contentValues.put("age", pet.getAge());
        contentValues.put("weight", pet.getWeight());
        contentValues.put("breed", pet.getBreed());
        contentValues.put("gender", pet.getGender());
        contentValues.put("spayed_neutered", pet.getSpayedNeutered());
        long id = dbb.insert("tbl_pet", null, contentValues);
        return id;
    }

    //Read


    //Update


    //Delete


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

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_energy(" +
                    "energu_level_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "energy_level INTEGER, " +
                    "date TEXT, " +
                    "pet_id INTEGER, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_excrement(" +
                    "excrement_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "abnormalties TEXT, " +
                    "date TEXT, " +
                    "pet_id INTEGER, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_exercise(" +
                    "exercise_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type TEXT, " +
                    "duration REAL, " +
                    "date TEXT, " +
                    "pet_id INTEGER, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_meal(" +
                    "meal_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "amount REAL, " +
                    "result INTEGER, " +
                    "date TEXT, " +
                    "pet_id INTEGER, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");

            db.execSQL("CREATE TABLE IF NOT EXISTS tbl_weight(" +
                    "weight_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "weight REAL, " +
                    "date TEXT, " +
                    "pet_id INTEGER, " +
                    "FOREIGN KEY (pet_id) REFERENCES tbl_pet (pet_id))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // no-op
        }
    }
}