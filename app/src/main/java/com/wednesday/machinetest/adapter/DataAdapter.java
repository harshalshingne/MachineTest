package com.wednesday.machinetest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wednesday.machinetest.R;
import com.wednesday.machinetest.model.ArtiestDetails;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>implements Filterable {
    private Context context;
    private ArrayList<ArtiestDetails> mArrayList;
    private ArrayList<ArtiestDetails> mFilteredList;

    public DataAdapter(Context context, ArrayList<ArtiestDetails> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
        mFilteredList = mArrayList;
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;

                } else {

                    ArrayList<ArtiestDetails> filteredList = new ArrayList<>();

                    for (ArtiestDetails artiestDetails : mArrayList) {

                        if ( artiestDetails.getCollectionArtistName().toLowerCase().contains(charString)) {

                            filteredList.add(artiestDetails);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ArtiestDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        if ( mFilteredList.get(position).getCollectionCensoredName()!=null ){

            viewHolder.tv_collectionCensoredName.setVisibility(View.VISIBLE);
            viewHolder.tv_collectionCensoredName.setText(mFilteredList.get(position).getCollectionCensoredName());
        }else {
            viewHolder.tv_collectionArtistName.setVisibility(View.GONE);
        }

        if ( mFilteredList.get(position).getCollectionArtistName()!=null ){

            viewHolder.tv_collectionArtistName.setVisibility(View.VISIBLE);
            viewHolder.tv_collectionArtistName.setText(mFilteredList.get(position).getCollectionArtistName());
        }else {
            viewHolder.tv_collectionArtistName.setVisibility(View.GONE);
        }

        Glide.with(context).load(mFilteredList.get(position).getArtworkUrl100()).placeholder(R.drawable.ic_baseline_photo_24)
                .error(R.drawable.ic_baseline_photo_24).into(viewHolder.img_artworkUrl);

    }

    @Override
    public int getItemCount() {
        return (mFilteredList == null) ? 0 : mFilteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_collectionCensoredName;
        private TextView tv_collectionArtistName;
        private ImageView img_artworkUrl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_collectionCensoredName = itemView.findViewById(R.id.tv_collectionCensoredName);
            tv_collectionArtistName = itemView.findViewById(R.id.tv_collectionArtistName);
            Button btn_preview = itemView.findViewById(R.id.btn_preview);
            img_artworkUrl=itemView.findViewById(R.id.img_artworkartwork);
            btn_preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArtiestDetails artiestDetails=mArrayList.get(getAdapterPosition());
                    onPreviewClick.onPreview(artiestDetails);
                }
            });
        }
    }

    public interface onPreviewClick{
        void onPreview(ArtiestDetails artiestDetails);
    }
    private onPreviewClick onPreviewClick;

    public void setOnPreviewClick(DataAdapter.onPreviewClick onPreviewClick) {
        this.onPreviewClick = onPreviewClick;
    }
}
