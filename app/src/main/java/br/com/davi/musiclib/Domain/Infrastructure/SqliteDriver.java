package br.com.davi.musiclib.Domain.Infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SqliteDriver implements DbDriver<SQLiteDatabase> {
    private SqliteWrapper wrapper;
    private SQLiteDatabase database;

    public SqliteDriver(Context context) {
        this.wrapper = new SqliteWrapper(context);
    }

    @Override
    public void open() {
        this.database = wrapper.getWritableDatabase();
    }

    @Override
    public void close() {
        wrapper.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
