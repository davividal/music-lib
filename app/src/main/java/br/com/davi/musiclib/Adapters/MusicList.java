package br.com.davi.musiclib.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.davi.musiclib.Domain.Model.Music;
import br.com.davi.musiclib.R;

/**
 * Created by davi_000 on 01/07/2016.
 */
public class MusicList extends ArrayAdapter<Music> {
    private final Activity context;
    private final ArrayList<Music> library;

    public MusicList(Activity context, ArrayList<Music> library) {
        super(context, R.layout.music_list, library);
        this.context = context;
        this.library = library;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MusicViewHolder musicViewHolder;

        if (null == convertView) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.music_list, parent, false);

            musicViewHolder = new MusicViewHolder();
            musicViewHolder.name = (TextView) convertView.findViewById(R.id.music_name);

            convertView.setTag(musicViewHolder);
        } else {
            musicViewHolder = (MusicViewHolder) convertView.getTag();
        }

        Music music = library.get(position);
        if (null != music) {
            musicViewHolder.name.setText(music.getName());
        }

        return convertView;
    }

    static class MusicViewHolder {
        TextView name;
    }
}
