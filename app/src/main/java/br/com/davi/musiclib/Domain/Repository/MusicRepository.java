package br.com.davi.musiclib.Domain.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import br.com.davi.musiclib.Domain.Infrastructure.SqliteWrapper;
import br.com.davi.musiclib.Domain.Model.Music;

public class MusicRepository extends AbstractRepository {
    public MusicRepository(Context context) {
        super(context);
    }

    public Music save(Music music) {
        ContentValues values = new ContentValues();
        values.put("name", music.getName());

        Long id = db.insert(SqliteWrapper.MUSIC, null, values);

        Cursor cursor = db.query(
                SqliteWrapper.MUSIC,
                SqliteWrapper.MUSIC_COLS,
                "id = " + id,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        music = parseMusic(cursor);
        cursor.close();

        return music;
    }

    public void delete(Music music) {
        db.delete(SqliteWrapper.MUSIC, "id = ?",  new String[] {music.getId().toString()});
    }

    public List<Music> findAll() {
        List<Music> library = new ArrayList<>();
        Cursor cursor = db.query(
                SqliteWrapper.MUSIC,
                SqliteWrapper.MUSIC_COLS,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            library.add(this.parseMusic(cursor));
            cursor.moveToNext();
        }

        return library;
    }

    private Music parseMusic(Cursor cursor) {
        Music music = new Music();
        music.setId(cursor.getInt(0));
        music.setName(cursor.getString(1));

        return music;
    }
}
