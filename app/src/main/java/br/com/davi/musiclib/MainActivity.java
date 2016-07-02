package br.com.davi.musiclib;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.davi.musiclib.Adapters.MusicList;
import br.com.davi.musiclib.Domain.Model.Music;
import br.com.davi.musiclib.Domain.Repository.MusicRepository;

public class MainActivity extends AppCompatActivity {
    private final List<Music> musicLibrary = new ArrayList<>();
    protected MusicList adapter = null;
    protected MusicRepository musicRepo = null;
    protected ListView musicLibraryView = null;
    private Runnable redrawMusicLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicRepo = new MusicRepository(this);
        adapter = new MusicList(this, new ArrayList<Music>());
        adapter.setMusicRepo(musicRepo);

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Buscando músicas...");
        dialog.show();
        populateTable(dialog);

        redrawMusicLib = new Runnable() {
            public void run() {
                //reload content
                musicLibrary.clear();
                fetchMusic();
                adapter.clear();
                adapter.addAll(musicLibrary);
                adapter.notifyDataSetChanged();
                musicLibraryView.invalidateViews();
                musicLibraryView.refreshDrawableState();
            }
        };
    }

    private void populateTable(ProgressDialog dialog) {
        musicLibraryView = (ListView) findViewById(R.id.music_library);

        runOnUiThread(new Runnable() {
            private ProgressDialog dialog;
            private MainActivity activity;
            private List<Music> musicLibrary;
            private ListView musicListView;

            public void run() {
                musicLibrary = fetchMusic();

                for (Music music : musicLibrary) {
                    adapter.add(music);
                }

                if (null != musicListView) {
                    musicListView.setAdapter(adapter);
                    // musicListView.setOnItemClickListener(new ProductDetailListener(activity, musicLibrary));
                }

                dialog.dismiss();
            }

            public Runnable init(ProgressDialog dialog, MainActivity activity, ListView musicLibraryView) {
                this.dialog = dialog;
                this.activity = activity;
                this.musicListView = musicLibraryView;
                return this;
            }
        }.init(dialog, this, musicLibraryView));
    }

    private List<Music> fetchMusic() {
        if (this.musicLibrary.isEmpty()) {
            this.musicLibrary.addAll(musicRepo.findAll());
        }

        return this.musicLibrary;
    }

    public void addMusic(View view) {
        EditText newMusicName = (EditText) findViewById(R.id.new_music_name);
        assert newMusicName != null;

        String musicName = newMusicName.getText().toString();
        newMusicName.setText("");
        newMusicName.clearFocus();

        Music music = new Music();
        music.setName(musicName);
        musicRepo.save(music);

        runOnUiThread(redrawMusicLib);
    }
}
