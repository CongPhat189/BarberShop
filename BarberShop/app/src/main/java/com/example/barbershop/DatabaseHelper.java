package com.example.barbershop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "barber.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng User
    private static final String TABLE_USER = "User";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_ROLE = "role";

    // Bảng Location
    private static final String TABLE_LOCATION = "Location";
    private static final String COL_LOCATION_ID = "location_id";
    private static final String COL_NAME = "name";
    private static final String COL_ADDRESS = "address";
    private static final String COL_SERVICE = "service";
    private static final String COL_PRICE = "price";
    private static final String COL_CONTACT = "contact";
    private static final String COL_IMAGE_URL = "image_url"; // Lưu URL hình ảnh

    // Bảng Booking
    private static final String TABLE_BOOKING = "Booking";
    private static final String COL_BOOKING_ID = "booking_id";
    private static final String COL_USER_ID_FK = "user_id";
    private static final String COL_DATETIME = "datetime";
    private static final String COL_STATUS = "status";

    // Bảng Rating
    private static final String TABLE_RATING = "Rating";
    private static final String COL_RATING_ID = "rating_id";
    private static final String COL_RATING = "rating";
    private static final String COL_REVIEW = "review";

    // Bảng Thợ
    private static final String TABLE_BARBER = "Barber";
    private static final String COL_BARBER_ID = "barber_id";
    private static final String COL_BARBER_NAME = "barber_name";
    private static final String COL_BARBER_EXPERIENCE = "experience";
    private static final String COL_BARBER_RATING = "rating";





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng User
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT, " +
                COL_ROLE + " TEXT)";
        db.execSQL(createUserTable);

        //Tạo bảng Booking

        String createBookingTable = "CREATE TABLE " + TABLE_BOOKING + " (" +
                COL_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_ID_FK + " INTEGER, " +
                COL_LOCATION_ID + " INTEGER, " +
                COL_DATETIME + " TEXT, " +
                COL_STATUS + " TEXT, " +
                "FOREIGN KEY(" + COL_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COL_USER_ID + "),"+
                "FOREIGN KEY(" + COL_LOCATION_ID + ") REFERENCES " + TABLE_LOCATION + "(" + COL_LOCATION_ID + "))";
        db.execSQL(createBookingTable);

        // Tạo bảng Location
        String createLocationTable = "CREATE TABLE " + TABLE_LOCATION + " (" +
                COL_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_ADDRESS + " TEXT, " +
                COL_SERVICE + " TEXT, " +
                COL_PRICE + " REAL,"+
                COL_CONTACT + " TEXT, " +
                COL_IMAGE_URL + " TEXT)";

        db.execSQL(createLocationTable);
        // Tạo bảng Rating
        String createRatingTable = "CREATE TABLE " + TABLE_RATING + " (" +
                COL_RATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_ID + " INTEGER, " +
                COL_LOCATION_ID + " INTEGER, " +
                COL_RATING + " INTEGER, " +
                COL_REVIEW + " TEXT, " +
                "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COL_USER_ID + "), " +
                "FOREIGN KEY(" + COL_LOCATION_ID + ") REFERENCES " + TABLE_LOCATION + "(" + COL_LOCATION_ID + "))";
        db.execSQL(createRatingTable);

        // Tạo bảng Thợ
        String createBarberTable = "CREATE TABLE " + TABLE_BARBER + " (" +
                COL_BARBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BARBER_NAME + " TEXT, " +
                COL_BARBER_EXPERIENCE + " INTEGER, " +
                COL_BARBER_RATING + " REAL)";
        db.execSQL(createBarberTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATING);
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_USER + " ADD COLUMN " + COL_ROLE + " TEXT DEFAULT 'user'");
        }
        onCreate(db);
    }

    // Thêm người dùng mới
    public boolean insertUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_ROLE, role);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }

    // Đăng nhập
    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " +new String[]{COL_ROLE} +
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }
    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE username = ?",
                new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Đặt lịch
    // Thêm phương thức đặt lịch
    public boolean bookAppointment(int userId, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID_FK, userId);
        values.put(COL_DATETIME, datetime);
        values.put(COL_STATUS, "Đã đặt");

        long result = db.insert(TABLE_BOOKING, null, values);
        return result != -1;
    }
    // Thêm phương thức gửi đánh giá
    public boolean submitRating(int userId, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID_FK, userId);
        values.put("rating", rating); // Giả sử có cột 'rating' trong bảng Booking

        long result = db.insert("Rating", null, values); // Giả sử có bảng 'Rating'
        return result != -1;
    }



    public String getUserRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COL_ROLE},
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String role = cursor.getString(0);
            cursor.close();
            return role;
        }
        return null;


    }
    // Thêm một lượt đặt lịch mới
    public boolean addBooking(int userId, int locationId, String datetime, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, userId);
        values.put(COL_LOCATION_ID, locationId);
        values.put(COL_DATETIME, datetime);
        values.put(COL_STATUS, status);

        long result = db.insert(TABLE_BOOKING, null, values);
        return result != -1;
    }

    // Lấy danh sách lượt đặt lịch của người dùng
    public Cursor getUserBookings(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOKING + " WHERE " + COL_USER_ID + "=?", new String[]{String.valueOf(userId)});
    }

    // Cập nhật trạng thái của một booking
    public boolean updateBookingStatus(int bookingId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, status);

        int result = db.update(TABLE_BOOKING, values, COL_BOOKING_ID + "=?", new String[]{String.valueOf(bookingId)});
        return result > 0;
    }

    // Xóa một booking
    public boolean deleteBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOKING, COL_BOOKING_ID + "=?", new String[]{String.valueOf(bookingId)});
        return result > 0;
    }

    // Thêm đánh giá cho một địa điểm
    public boolean addRating(int userId, int locationId, int rating, String review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, userId);
        values.put(COL_LOCATION_ID, locationId);
        values.put(COL_RATING, rating);
        values.put(COL_REVIEW, review);

        long result = db.insert(TABLE_RATING, null, values);
        return result != -1;
    }

    // Lấy danh sách đánh giá của một địa điểm
    public Cursor getLocationRatings(int locationId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RATING + " WHERE " + COL_LOCATION_ID + "=?", new String[]{String.valueOf(locationId)});
    }

    // Lấy đánh giá trung bình của một địa điểm
    public double getAverageRating(int locationId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(" + COL_RATING + ") FROM " + TABLE_RATING + " WHERE " + COL_LOCATION_ID + "=?", new String[]{String.valueOf(locationId)});
        if (cursor.moveToFirst()) {
            double avgRating = cursor.getDouble(0);
            cursor.close();
            return avgRating;
        }
        return 0;
    }

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM User", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                userList.add(new User(id, username, role));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    // Xóa người dùng
    public <string> boolean deleteUser(string username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("User", "username=?", new String[]{String.valueOf(username)});
        return false;
    }
    // Cập nhật mật khẩu người dùng
    public boolean updateUserPassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PASSWORD, newPassword);

        int result = db.update(TABLE_USER, values, COL_USERNAME + "=?", new String[]{username});
        return result > 0;
    }

    // Lấy danh sách thợ
    public ArrayList<Barber> getAllBarbers() {
        ArrayList<Barber> barbers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Barber", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int experience = cursor.getInt(2);
                float rating = cursor.getFloat(3);
                barbers.add(new Barber(id, name, experience, rating));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return barbers;
    }

    // Thêm thợ mới
    public boolean addBarber(String name, int experience, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("barber_name", name);
        values.put("experience", experience);
        values.put("rating", rating);

        long result = db.insert("Barber", null, values);
        return result != -1;
    }

    // Xóa thợ
    public void deleteBarber(int barberId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Barber", "barber_id=?", new String[]{String.valueOf(barberId)});
    }

    //Thêm địa điểm mới
    public boolean addLocation(String name, String address, String service, double price, String contact, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("service", service);
        values.put("price", price);
        values.put("contact", contact);
        values.put("image", imageUri);

        long result = db.insert("Location", null, values);
        return result != -1;
    }

    //Lấy danh sách địa điểm mới
    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Location", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("location_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String service = cursor.getString(cursor.getColumnIndexOrThrow("service"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String contact = cursor.getString(cursor.getColumnIndexOrThrow("contact"));
                String imageUri = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                locationList.add(new Location(id, name, address, service, price, contact, imageUri));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return locationList;
    }

    //Lấy thông tin địa điểm theo ID
    public Location getLocationById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Location WHERE location_id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Location location = new Location(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            cursor.close();
            return location;
        }
        cursor.close();
        return null;
    }


    //Cập nhật địa điểm
    public boolean updateLocation(int id, String name, String address, String service, double price, String contact, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("service", service);
        values.put("price", price);
        values.put("contact", contact);
        values.put("image", imageUri);

        int result = db.update("Location", values, "location_id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    //Xóa địa điểm
    public boolean deleteLocation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Location", "location_id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }








}







