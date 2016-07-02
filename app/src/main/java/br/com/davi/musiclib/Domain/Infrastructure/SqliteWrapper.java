package br.com.davi.musiclib.Domain.Infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteWrapper extends SQLiteOpenHelper {
    public static final String MUSIC = "musics";
    public static final String[] MUSIC_COLS = {"id", "name"};

    private static final String DB_NAME = "musiclib.db";
    private static final Integer DB_VERSION = 1;

    public SqliteWrapper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable(MUSIC));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    protected String createTable(String table) {
        switch (table) {
            case MUSIC:
                return "CREATE TABLE musics (id integer primary key autoincrement, name text not null);";
            default:
                return "";
        }
    }
}
