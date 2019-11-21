package ir.ariact.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {
    private Context context;
    private FragmentManager fragmentManager;
    private List<Song> songs;

    SongsAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        songs = SongRepository.getInstance().getSongs();
    }

    SongsAdapter(Context context, FragmentManager fragmentManager, String artistName) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        songs = new LinkedList<>();
        for (Song song : SongRepository.getInstance().getSongs()) {
            if (song.getArtist().equals(artistName)){
                songs.add(song);
            }
        }
    }

    static class SongsViewHolder extends RecyclerView.ViewHolder{
        TextView title, artist, album;
        ImageView art;
        View view;

        SongsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.song_row_layout_title);
            artist = itemView.findViewById(R.id.song_row_layout_artist);
            album = itemView.findViewById(R.id.song_row_layout_album);
            art = itemView.findViewById(R.id.song_row_layout_art);
            view = itemView;
        }
    }
    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View songRowLayout = inflater.inflate(R.layout.song_row_layout, parent, false);
        return new SongsViewHolder(songRowLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, final int position) {
        Song currentSong = songs.get(position);
        holder.title.setText(currentSong.getTitle());
        holder.artist.setText(currentSong.getArtist());
        holder.album.setText(currentSong.getAlbum());
        Picasso.get().load(currentSong.getArt()).placeholder(R.drawable.ic_action_single_music).into(holder.art);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicPlayerService.class);
                intent.putExtra(Vars.getIntentMusicPosition(), position);
                context.startService(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
