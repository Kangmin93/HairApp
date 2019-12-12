package com.example.haircal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<AlbumVO> list_album;



//    public OnItemClickListener mOnItemClickListener = null;



    public interface OnItemClickListener {

        void onItemClick(View view, AlbumVO albumVO);

    }



    public void setOnItemClickListener(OnItemClickListener listener) {

//        mOnItemClickListener = listener;

    }



    public AlbumAdapter(Context mContext, ArrayList<AlbumVO> list_album) {

        this.mContext = mContext;

        this.list_album = list_album;

    }



    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_album_item, parent, false);

        return new ViewHolder(convertView);

    }



    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {

        final AlbumVO albumVO = list_album.get(position);



        Glide.with(mContext)

                .load(albumVO.getThumb())

                .thumbnail(0.5f)

                .into(holder.img_thumb);



        holder.txt_artist.setText(albumVO.getArtist());

        holder.txt_title.setText(albumVO.getTitle());

        holder.layout_album_panel.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

//                mOnItemClickListener.onItemClick(v, albumVO);

            }

        });

    }



    @Override

    public int getItemCount() {

        return list_album.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout layout_album_panel;
        private ImageView img_thumb;
        private TextView txt_artist;
        private TextView txt_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_album_panel = itemView.findViewById(R.id.layout_album_panel);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            txt_artist = itemView.findViewById(R.id.txt_artist);
            txt_title = itemView.findViewById(R.id.txt_title);
        }
    }
}