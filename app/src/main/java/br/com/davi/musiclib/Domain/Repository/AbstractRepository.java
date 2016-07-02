package br.com.davi.musiclib.Domain.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.davi.musiclib.Domain.Infrastructure.DbDriver;
import br.com.davi.musiclib.Domain.Infrastructure.SqliteDriver;

public abstract class AbstractRepository {
    protected DbDriver driver;
    protected SQLiteDatabase db;

    public AbstractRepository(Context context) {
        /**
         * @TODO: implement a factory
         **/
        driver = new SqliteDriver(context);
        driver.open();
        db = (SQLiteDatabase) driver.getDatabase();
    }
}
