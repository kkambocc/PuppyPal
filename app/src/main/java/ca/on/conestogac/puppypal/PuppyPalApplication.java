package ca.on.conestogac.puppypal;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Extends to application class.
public class PuppyPalApplication extends Application {
    private static final String DB_NAME = "DB_PuppyPal";
    private static final int DB_VERSION = 1;

    private SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    public void onCreate() {
        sqLiteOpenHelper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION) {

            @Override
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
        };

        super.onCreate();
    }
}
