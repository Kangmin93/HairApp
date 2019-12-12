package com.example.haircal;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class HairCardAdapter extends RecyclerView.Adapter<HairCardAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<HairCardVO> listHairCard;
    public OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, HairCardVO hairCardVO);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public HairCardAdapter(Context mContext, ArrayList<HairCardVO> listHairCard) {
        this.mContext = mContext;
        this.listHairCard = listHairCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_haircard_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HairCardVO hairCardVO = listHairCard.get(position);
        if(hairCardVO.getImgTest()!=null){
            Uri uri = Uri.parse("file:///"+hairCardVO.getImgTest());
            holder.imgHair.setImageURI(uri);
        }else{
            holder.imgHair.setImageResource(R.drawable.aoa);
        }
        holder.txtDate.setText(hairCardVO.getDate());
        holder.txtHairShop.setText(hairCardVO.getHairShop());
        holder.txtDesigner.setText(hairCardVO.getDesigner());
        holder.txtPrice.setText(Integer.toString(hairCardVO.getPirce()));
        holder.txtComment.setText(hairCardVO.getComment());
        holder.layout_haircard_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view,hairCardVO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHairCard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout layout_haircard_panel;
        private TextView txtDate;
        private Button btnModify;
        private ImageView imgHair;
        private TextView txtHairShop;
        private TextView txtDesigner;
        private TextView txtPrice;
        private TextView txtComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_haircard_panel = itemView.findViewById(R.id.layout_haircard_panel);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnModify = itemView.findViewById(R.id.btnModify);
            imgHair = itemView.findViewById(R.id.imgHair);
            txtHairShop = itemView.findViewById(R.id.txtHairShop);
            txtDesigner = itemView.findViewById(R.id.txtDesigner);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtComment = itemView.findViewById(R.id.txtComment);


        }
    }
}
