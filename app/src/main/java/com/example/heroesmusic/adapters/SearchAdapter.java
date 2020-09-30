package com.example.heroesmusic.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Music;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> implements Filterable {

    private List<Music> mMusicList;
    private List<Music> mAllMusic;
    private Context mContext;

    public SearchAdapter(Context context, List<Music> musicList) {
        mMusicList = new ArrayList<>();
        mAllMusic = new ArrayList<>(musicList);
        mContext = context;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        Music music = mMusicList.get(position);
        holder.bind(music, mContext, mMusicList);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Music> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty())
                filterList.addAll(mAllMusic);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Music music : mAllMusic){
                    if (music.getMusicName().toLowerCase().contains(filterPattern) ||
                            music.getSinger().toLowerCase().contains(filterPattern) ||
                            music.getAlbum().toLowerCase().contains(filterPattern))
                        filterList.add(music);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMusicList.clear();
            mMusicList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public static class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mMusicImage;
        private TextView mMusicName , mMusicArtist;
        private Context mContext;
        private List<Music> mMusicList;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        public void bind(Music music, Context context, List<Music> musicList){
            mContext = context;
            mMusicList = musicList;
            mMusicName.setText(music.getMusicName());
            mMusicArtist.setText(music.getSinger());
            setMusicImage(music, context);
        }

        private void setMusicImage(Music music, Context context) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, music.getAlbumId());
            Glide
                    .with(context)
                    .load(albumArtUri)
                    .centerCrop()
                    .placeholder(R.drawable.default_music_cover)
                    .into(mMusicImage);
        }

        private void findViews(@NonNull View itemView) {
            itemView.setOnClickListener(this);
            mMusicImage = itemView.findViewById(R.id.music_image_view);
            mMusicName = itemView.findViewById(R.id.music_name);
            mMusicArtist = itemView.findViewById(R.id.music_artist);
        }

        @Override
        public void onClick(View v) {
    //        Intent intent = PlayMusicActivity.newIntent(mContext, getAdapterPosition(), mMusicList);
    //        v.getContext().startActivity(intent);
        }
    }
}
