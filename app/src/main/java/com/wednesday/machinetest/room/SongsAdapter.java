package com.wednesday.machinetest.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wednesday.machinetest.R;
import com.wednesday.machinetest.model.ArtiestDetails;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder>implements Filterable {

    private Context mCtx;
    private List<SongDetails> mList;
    private List<SongDetails> songList;

    public SongsAdapter(Context mCtx, List<SongDetails> songList) {
        this.mCtx = mCtx;
        this.songList = songList;
        this.mList=songList;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.artist_list, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        SongDetails t = songList.get(position);
        holder.tv_collectionCensoredName.setText(t.getCollectionCensoredName());
        holder.tv_collectionArtistName.setText(t.getCollectionArtistName());
       // holder.img_artworkUrl.setText(t.getFinishBy());
        Glide.with(mCtx).load(songList.get(position).getArtworkUrl100()).placeholder(R.drawable.ic_baseline_photo_24)
                .error(R.drawable.ic_baseline_photo_24).into(holder.img_artworkUrl);
//        if (t.isFinished())
//            holder.textViewStatus.setText("Completed");
//        else
//            holder.textViewStatus.setText("Not Completed");
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    songList = mList;

                } else {

                    ArrayList<SongDetails> filteredList = new ArrayList<>();

                    for (SongDetails songDetails : mList) {

                        if ( songDetails.getCollectionCensoredName().toLowerCase().contains(charString)) {

                            filteredList.add(songDetails);
                        }
                    }

                    songList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = songList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                songList = (ArrayList<SongDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
        return (songList == null) ? 0 : songList.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder  {

        private TextView tv_collectionCensoredName;
        private TextView tv_collectionArtistName;
        private ImageView img_artworkUrl;

        public SongViewHolder(View itemView) {
            super(itemView);

            tv_collectionCensoredName = itemView.findViewById(R.id.tv_collectionCensoredName);
            tv_collectionArtistName = itemView.findViewById(R.id.tv_collectionArtistName);
            Button btn_preview = itemView.findViewById(R.id.btn_preview);
            img_artworkUrl=itemView.findViewById(R.id.img_artworkartwork);
            btn_preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SongDetails songDetails=songList.get(getAdapterPosition());
                    onPreviewClick.onPreview(songDetails);
                }
            });

        }
    }
    public interface onPreviewClick{
        void onPreview(SongDetails songDetails);
    }
    private onPreviewClick onPreviewClick;

    public void setOnPreviewClick(onPreviewClick onPreviewClick) {
        this.onPreviewClick = onPreviewClick;
    }


}
