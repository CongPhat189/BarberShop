package com.example.barbershop;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "barber.db";
    private static final int DATABASE_VERSION = 2;

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

    //  bảng Booking
    private static final String TABLE_BOOKING = "Booking";
    private static final String COL_BOOKING_ID = "booking_id";
    private static final String COL_USER_ID_FK = "user_id";
    private static final String COL_BOOKING_NAME = "full_name";
    private static final String COL_PHONE = "phone";
    private static final String COL_DATE = "date";
    private static final String COL_TIME = "time";
    private static final String COL_SERVICES = "services";
    private static final String COL_PRICES = "price";
    private static final String COL_LOCATION_ADDRESS_FK = "location_address";
    private static final String COL_PAYMENT_METHOD = "payment_method";
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

        // Tạo bảng lưu lịch đặt
        String createBookingTable = "CREATE TABLE " + TABLE_BOOKING + " (" +
                    COL_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_USER_ID + " INTEGER, " +
                    COL_BOOKING_NAME + " TEXT, " +
                    COL_PHONE + " TEXT, " +
                    COL_DATE + " TEXT, " +
                    COL_TIME + " TEXT, " +
                    COL_SERVICES + " TEXT, " +
                    COL_PRICES + " REAL,"+
                    COL_LOCATION_ADDRESS_FK + " TEXT, " +
                    COL_PAYMENT_METHOD + " TEXT, " +
                    COL_STATUS + " TEXT, " +
                "FOREIGN KEY(" + COL_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COL_USER_ID + "), " +
                "FOREIGN KEY(" + COL_LOCATION_ADDRESS_FK + ") REFERENCES " + TABLE_LOCATION + "(" + COL_ADDRESS + "))";
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
                COL_USER_ID_FK + " INTEGER, " +
                COL_LOCATION_ID + " INTEGER, " +
                COL_RATING + " INTEGER, " +
                COL_REVIEW + " TEXT, " +
                "FOREIGN KEY(" + COL_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COL_USER_ID + "), " +
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
        if(oldVersion < 3)
        {
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COL_BOOKING_NAME  );
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COL_USER_ID_FK  );
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COL_PHONE );
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COL_DATE );
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COL_TIME );
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COL_SERVICES );
            db.execSQL("ALTER TABLE " + TABLE_BOOKING+ " ADD COLUMN " + COL_LOCATION_ADDRESS_FK );
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COL_PAYMENT_METHOD );
            db.execSQL("ALTER TABLE " + TABLE_BOOKING + " ADD COLUMN " + COL_PRICES );
        }

        onCreate(db);
    }
    public static class PasswordUtils {

        public static String hashPassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();

                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error hashing password", e);
            }
        }
    }

    // Thêm người dùng mới
    public boolean insertUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        String hashedPassword = PasswordUtils.hashPassword(password);
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD,  hashedPassword);
        values.put(COL_ROLE, role);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }
    public String getUserRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COL_ROLE,COL_PASSWORD},
                COL_USERNAME + "=? " ,
                new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE));
            String storedHashedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD));

            // Băm mật khẩu người dùng nhập vào
            String Password = PasswordUtils.hashPassword(password);

            // So sánh mật khẩu
            if (storedHashedPassword.equals(Password)) {
                cursor.close();
                db.close();
                return role;
            }

        }
        if (cursor != null) cursor.close();
        db.close();
        return null;


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
    public boolean addBooking(int user_id,String full_name, String phone, String date, String time, String services, double price, String locationAddress, String paymentMethod,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID_FK, user_id);
        values.put( COL_BOOKING_NAME, full_name);
        values.put(COL_PHONE, phone);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_SERVICES, services);
        values.put(COL_PRICES, price);
        values.put(COL_LOCATION_ADDRESS_FK, locationAddress);
        values.put(COL_PAYMENT_METHOD, paymentMethod);
        values.put(COL_STATUS, status);

        long result = db.insert(TABLE_BOOKING, null, values);
        return result != -1;
    }
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Booking";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int booking_id = cursor.getInt(cursor.getColumnIndexOrThrow("booking_id"));
                int user_id = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                String full_name = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String locationAddress = cursor.getString(cursor.getColumnIndexOrThrow("location_address"));
                String services = cursor.getString(cursor.getColumnIndexOrThrow("services"));
                Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));


                bookings.add(new Booking(booking_id,user_id,full_name, phone, date,time, locationAddress, services,price, paymentMethod, status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookings;
    }
    public List<Booking> getBookingsByUserId(int user_id) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

//
        Cursor cursor = db.query(
                TABLE_BOOKING,
                null, // Lấy tất cả các cột
                COL_USER_ID_FK + " = ?", // Điều kiện WHERE
                new String[]{String.valueOf(user_id)}, // Tham số
                null,
                null,
                null
        );


            if (cursor.moveToFirst()) {
                do {

                    int booking_id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_BOOKING_ID));
                    String full_name = cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_NAME));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
                    String locationName = cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION_ADDRESS_FK));
                    String services = cursor.getString(cursor.getColumnIndexOrThrow(COL_SERVICES));
                    Double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICES));
                    String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow(COL_PAYMENT_METHOD));
                    String status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS));

                    // Tạo đối tượng Booking
                    Booking booking = new Booking(booking_id, user_id, full_name, phone, date, time,locationName, services, price,  paymentMethod, status);

                    // Thêm vào danh sách
                    bookingList.add(booking);
                } while (cursor.moveToNext());
            }
            cursor.close();

        db.close();
        return bookingList;
    }










    // Xóa một booking
    public boolean deleteBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS, "Đã hủy");
        int result = db.delete(TABLE_BOOKING, COL_BOOKING_ID + "=?", new String[]{String.valueOf(bookingId)});
        return result > 0;
    }

    // Thêm đánh giá cho một địa điểm
    public boolean addRating(int user_id, int location_id, float rating, String review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID_FK, user_id);
        values.put(COL_LOCATION_ID, location_id);
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
    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("User", "username = ?", new String[]{username});
        db.close();
        return result > 0;
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
    public boolean addLocation(String name, String address, String service, String price, String contact, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("service", service);
        values.put("price", Double.parseDouble(price));
        values.put("contact", contact);
        values.put("image_url",
                imageUri.toString());

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
                String price = String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
                String contact = cursor.getString(cursor.getColumnIndexOrThrow("contact"));
                String imageUri = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));
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
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            cursor.close();
            return location;
        }
        cursor.close();
        return null;
    }
    public List<String> getAllLocationsForSpinner() {
        List<String> locations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT address FROM Location"; // Hoặc SELECT address nếu bạn muốn hiển thị địa chỉ
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                locations.add(address);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return locations;
    }




    //Cập nhật địa điểm
    public boolean updateLocation(int id, String name, String address, String service, String price, String contact, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("service", service);
        values.put("price", Double.parseDouble(price));
        values.put("contact", contact);
        values.put("image_url", imageUri.toString());

        int result = db.update("Location", values, "location_id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    //Xóa địa điểm
    public boolean deleteLocation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Location", "location_id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }


    @SuppressLint("Range")
    public int getUserId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT user_id FROM User WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        int user_id = -1;
        if (cursor.moveToFirst()) {
            user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
        }

        cursor.close();
        db.close();
        return user_id;
    }



    // Chức năng thống kê báo cáo của admin
    // Tổng số người dùng
    public int getTotalUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        int totalUsers = cursor.moveToFirst() ? cursor.getInt(0) : 0;
        cursor.close();
        db.close();
        return totalUsers;
    }

    // Số người dùng đã đặt lịch
    public int getUsersWithBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(DISTINCT " + COL_USER_ID_FK + ") FROM " + TABLE_BOOKING;
        Cursor cursor = db.rawQuery(query, null);
        int usersWithBookings = cursor.moveToFirst() ? cursor.getInt(0) : 0;
        cursor.close();
        db.close();
        return usersWithBookings;
    }

    // Người dùng hoạt động tích cực nhất
    public String getMostActiveUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_USER_ID_FK + ", COUNT(*) AS booking_count " +
                "FROM " + TABLE_BOOKING +
                " GROUP BY " + COL_USER_ID_FK +
                " ORDER BY booking_count DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        String mostActiveUser = "N/A";
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            int bookingCount = cursor.getInt(1);
            mostActiveUser = "User ID: " + userId + ", Số lượng lịch đã đặt: " + bookingCount;
        }
        cursor.close();
        db.close();
        return mostActiveUser;
    }
    // Tổng số lịch đã đặt
    public int getTotalBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_BOOKING;
        Cursor cursor = db.rawQuery(query, null);
        int totalBookings = cursor.moveToFirst() ? cursor.getInt(0) : 0;
        cursor.close();
        db.close();
        return totalBookings;
    }

    // Lịch đã đặt theo trạng thái
    public Map<String, Integer> getBookingsByStatus() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_STATUS + ", COUNT(*) FROM " + TABLE_BOOKING + " GROUP BY " + COL_STATUS;
        Cursor cursor = db.rawQuery(query, null);

        Map<String, Integer> statusStats = new HashMap<>();
        while (cursor.moveToNext()) {
            String status = cursor.getString(0);
            int count = cursor.getInt(1);
            statusStats.put(status, count);
        }
        cursor.close();
        db.close();
        return statusStats;
    }

    // Lịch đã đặt theo khoảng thời gian
    public int getBookingsByDateRange(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_BOOKING +
                " WHERE " + COL_DATE + " BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});
        int count = cursor.moveToFirst() ? cursor.getInt(0) : 0;
        cursor.close();
        db.close();
        return count;
    }
    // Tổng doanh thu
    public double getTotalRevenue() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COL_PRICES + ") FROM " + TABLE_BOOKING ;
        Cursor cursor = db.rawQuery(query, null);
        double totalRevenue = cursor.moveToFirst() ? cursor.getDouble(0) : 0.0;
        cursor.close();
        db.close();
        return totalRevenue;
    }

    // Doanh thu theo khoảng thời gian
    public double getRevenueByDateRange(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COL_PRICES + ") FROM " + TABLE_BOOKING +
                " WHERE " + COL_DATE + " BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});
        double revenue = cursor.moveToFirst() ? cursor.getDouble(0) : 0.0;
        cursor.close();
        db.close();
        return revenue;
    }





}







