package com.example.localservicefinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ServiceFinder.db";
    private static final String TABLE_NAME = "Services";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ServiceName TEXT, " +
                "Category TEXT, " +
                "ProviderName TEXT, " +
                "ContactDetails TEXT, " +
                "Location TEXT)");
        insertDemoData(db); // Populate demo data on database creation
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertService(String serviceName, String category, String providerName, String contactDetails, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ServiceName", serviceName);
        contentValues.put("Category", category);
        contentValues.put("ProviderName", providerName);
        contentValues.put("ContactDetails", contactDetails);
        contentValues.put("Location", location);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllServices() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY ID DESC", null);
    }

    public Cursor getServicesByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE Category = ? ORDER BY ID DESC";
        return db.rawQuery(query, new String[]{category});
    }

    public Cursor getServiceDetails(String serviceName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ServiceName = ?";
        return db.rawQuery(query, new String[]{serviceName});
    }

    public List<String> getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT Category FROM " + TABLE_NAME, null);

        List<String> categories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    private void insertDemoData(SQLiteDatabase db) {
        insertServiceDemo(db, "Electrician", "Home Services", "Ali Hasan", "9876543210", "Beirut");
        insertServiceDemo(db, "Plumber", "Home Services", "Kamal Saad", "9876543220", "Tripoli");
        insertServiceDemo(db, "Landscaping", "Outdoor Services", "Ahmad Khalil", "9876543230", "Saida");
        insertServiceDemo(db, "Taxi Service", "Transport", "Sara Ibrahim", "9876543240", "Beirut");
        insertServiceDemo(db, "Car Mechanic", "Automobile", "Jad Samir", "9876543250", "Byblos");
        insertServiceDemo(db, "Fitness Trainer", "Health & Fitness", "Layal Aoun", "9876543260", "Zahle");
        insertServiceDemo(db, "IT Support", "Technology", "Nour El Hage", "9876543270", "Jounieh");
        insertServiceDemo(db, "Painter", "Home Services", "Samir Azar", "9876543280", "Tripoli");
        insertServiceDemo(db, "Catering Service", "Food Services", "Rami Hajj", "9876543290", "Beirut");
        insertServiceDemo(db, "Florist", "Outdoor Services", "Maya Fares", "9876543300", "Saida");
        insertServiceDemo(db, "AC Technician", "Home Services", "Youssef Dabbas", "9876543310", "Jounieh");
        insertServiceDemo(db, "Mobile Repair", "Technology", "Hadi Anwar", "9876543320", "Beirut");
        insertServiceDemo(db, "Car Wash", "Automobile", "Nour Saab", "9876543330", "Byblos");
        insertServiceDemo(db, "Personal Chef", "Food Services", "Mona Darwish", "9876543340", "Tripoli");
        insertServiceDemo(db, "Yoga Instructor", "Health & Fitness", "Rania Nassar", "9876543350", "Zahle");
        insertServiceDemo(db, "Security Guard", "Outdoor Services", "Fadi Suleiman", "9876543360", "Saida");
        insertServiceDemo(db, "Guitar Teacher", "Education", "Wael Haddad", "9876543370", "Beirut");
        insertServiceDemo(db, "Wedding Planner", "Event Planning", "Dana Chahine", "9876543380", "Byblos");
        insertServiceDemo(db, "Courier Service", "Transport", "Mohamad Taha", "9876543390", "Tripoli");
        insertServiceDemo(db, "Tailor", "Home Services", "Leila Nader", "9876543400", "Zahle");
    }

    private void insertServiceDemo(SQLiteDatabase db, String serviceName, String category, String providerName, String contactDetails, String location) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ServiceName", serviceName);
        contentValues.put("Category", category);
        contentValues.put("ProviderName", providerName);
        contentValues.put("ContactDetails", contactDetails);
        contentValues.put("Location", location);
        db.insert(TABLE_NAME, null, contentValues);
    }
}
