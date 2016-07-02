package br.com.davi.musiclib.Domain.Infrastructure;

/**
 * Created by davi_000 on 01/07/2016.
 */
public interface DbDriver<T> {
    void open();

    void close();

    T getDatabase();
}
