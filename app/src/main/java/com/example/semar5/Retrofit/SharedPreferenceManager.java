package com.example.semar5.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.semar5.ModelResponse.NotificationModel;
import com.example.semar5.ModelResponse.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SharedPreferenceManager {

    private static String SHARED_PREF_NAME = "semar_undip";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPreferenceManager(Context context) {
        this.context = context;
    }
    private static SharedPreferenceManager instance;

    public void saveUser(User user){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id_mahasiswa",user.getId_mahasiswa());
        editor.putInt("id_role", user.getId_role());
        editor.putString("email",user.getEmail_mahasiswa());
        editor.putString("password",user.getPassword_mahasiswa());
        editor.putString("nama_mahasiswa",user.getNama_mahasiswa());
        editor.putString("nim",user.getNim_mahasiswa());
        editor.putInt("id_fakultas",user.getid_fakultas());
        editor.putInt("id_prodi",user.getid_prodi());
        editor.putString("tgl_lahir",user.getTgl_lahir_mahasiswa());
        editor.putString("agama",user.getAgama_mahasiswa());
        editor.putString("jenis_kelamin",user.getJenis_kelamin_mahasiswa());
        editor.putString("alamat",user.getAlamat_mahasiswa());
        editor.putString("no_telp",user.getNo_telp_mahasiswa());
        editor.putString("nama_orangtua",user.getNama_orangtua());
        editor.putString("pekerjaan_orangtua",user.getPekerjaan_orangtua());
        editor.putString("alamat_orangtua",user.getAlamat_orangtua());
        editor.putString("notelp_orangtua",user.getNotelp_orangtua());

        editor.putBoolean("logged",true);
        editor.apply();
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context.getApplicationContext());
        }
        return instance;
    }

    public boolean isLoggedIn(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged", false);
    }

    public User getUser(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt("id_mahasiswa", -1),
                sharedPreferences.getInt("id_role", 2),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("nama_mahasiswa", null),
                sharedPreferences.getString("nim", null),
                sharedPreferences.getInt("id_fakultas", -1),
                sharedPreferences.getInt("id_prodi", -1),
                sharedPreferences.getString("tgl_lahir", null),
                sharedPreferences.getString("agama", null),
                sharedPreferences.getString("jenis_kelamin", null),
                sharedPreferences.getString("alamat", null),
                sharedPreferences.getString("no_telp", null),
                sharedPreferences.getString("nama_orangtua", null),
                sharedPreferences.getString("pekerjaan_orangtua", null),
                sharedPreferences.getString("alamat_orangtua", null),
                sharedPreferences.getString("notelp_orangtua", null));
    }

    public void logout(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public String getImageUri(int userId) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("imageUri" + userId, "");
    }


    public class SharedPreferenceHelper {
        private static final String PREF_NAME = "NotificationPrefs";
        private final SharedPreferences sharedPreferences;

        public SharedPreferenceHelper(Context context) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }

        public void saveNotifications(Integer roleId, int mahasiswaId, List<NotificationModel> notifications) {
            Gson gson = new Gson();
            String json = gson.toJson(notifications);
            String key = getNotificationKey(roleId, mahasiswaId);
            sharedPreferences.edit().putString(key, json).apply();
        }

        public List<NotificationModel> getNotifications(Integer roleId, int mahasiswaId) {
            String key = getNotificationKey(roleId, mahasiswaId);
            String json = sharedPreferences.getString(key, null);
            if (json != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<NotificationModel>>() {}.getType();
                return gson.fromJson(json, type);
            }
            return new ArrayList<>();
        }

        private String getNotificationKey(Integer roleId, int mahasiswaId) {
            return "notifications_" + roleId + "_" + mahasiswaId;
        }
    }



    public void saveNotification(String title, String message, long timeMillis) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Ambil daftar notifikasi yang sudah ada
        Set<String> titles = sharedPreferences.getStringSet("notification_titles", new HashSet<>());
        Set<String> messages = sharedPreferences.getStringSet("notification_messages", new HashSet<>());
        Set<Long> timesMillis = getNotificationTimesMillis(); // Ambil daftar waktu dalam bentuk long

        // Tambahkan notifikasi baru ke dalam daftar
        titles.add(title);
        messages.add(message);
        timesMillis.add(timeMillis);

        // Simpan daftar notifikasi yang sudah diperbarui
        editor.putStringSet("notification_titles", titles);
        editor.putStringSet("notification_messages", messages);
        saveNotificationTimesMillis(timesMillis); // Simpan daftar waktu dalam bentuk long

        editor.apply();
    }

    private static final String NOTIFICATION_TIMES_KEY = "notification_times_millis";

    public Set<Long> getNotificationTimesMillis() {
        Set<String> timesString = sharedPreferences.getStringSet(NOTIFICATION_TIMES_KEY, new HashSet<>());
        Set<Long> timesMillis = new HashSet<>();

        for (String timeString : timesString) {
            timesMillis.add(Long.parseLong(timeString));
        }

        return timesMillis;
    }

    public void saveNotificationTimesMillis(Set<Long> timesMillis) {
        Set<String> timesString = new HashSet<>();
        for (Long timeMillis : timesMillis) {
            timesString.add(String.valueOf(timeMillis));
        }

        editor.putStringSet(NOTIFICATION_TIMES_KEY, timesString);
    }

}
