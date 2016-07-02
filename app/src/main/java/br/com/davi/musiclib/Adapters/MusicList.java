package br.com.davi.musiclib.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.davi.musiclib.Domain.Model.Music;
import br.com.davi.musiclib.Domain.Repository.MusicRepository;
import br.com.davi.musiclib.R;

public class MusicList extends ArrayAdapter<Music> {
    private final Activity context;
    private final List<Music> library;
    private MusicRepository musicRepo;

    public MusicList(Activity context, List<Music> library) {
        super(context, R.layout.music_list, library);
        this.context = context;
        this.library = library;
    }

    public void setMusicRepo(MusicRepository musicRepo) {
        this.musicRepo = musicRepo;
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

        final Music music = library.get(position);
        if (null != music) {
            musicViewHolder.id = music.getId();
            musicViewHolder.name.setText(music.getName());
        }

        Button deleteMusic = (Button) convertView.findViewById(R.id.delete_music);
        deleteMusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                musicRepo.delete(music);
                context.runOnUiThread(new Runnable() {
                    public void run() {
                        //reload content
                        remove(music);
                        notifyDataSetChanged();
                    }
                });
            }
        });


        return convertView;
    }

    static class MusicViewHolder {
        Integer id;
        TextView name;
    }
}
