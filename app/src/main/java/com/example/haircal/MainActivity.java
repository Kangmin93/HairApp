package com.example.haircal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HairCardAdapter.OnItemClickListener{
    private Context mContext;
    private RecyclerView rcc_album;
    private HairCardAdapter mHairCardAdapter;
    private Button btnEnroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnroll = findViewById(R.id.btnEnroll);
        mContext = this;
        init();
        String str_path = null;

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(i);
            }
        });

    }

    private void init() {
        rcc_album = (RecyclerView) findViewById(R.id.rcc_album);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        rcc_album.setLayoutManager(mLayoutManager);
//        rcc_album.addItemDecoration(new ItemDecoration(this));
        mHairCardAdapter = new HairCardAdapter(mContext, getHairCardList());
        mHairCardAdapter.setOnItemClickListener(this);

        rcc_album.setAdapter(mHairCardAdapter);
    }
    @Override
    public void onItemClick(View view, HairCardVO hairCardVO) {
        Log.e("RecyclerVIew :: ", hairCardVO.toString());
    }
    private ArrayList<HairCardVO> getHairCardList(){
        ArrayList<HairCardVO> listHairCard = new ArrayList<>();
//        String hairShop, String designer, int pirce, String date, String comment
        listHairCard.add(new HairCardVO("인동미용실", "신혜진", 15000,"2019.12.12","뿅뿅뿅~"));
        listHairCard.add(new HairCardVO("인의동미용실", "이채은", 20000,"2019.12.10","할라할라~"));
        if(getIntent().getStringArrayExtra("itemInfo")!=null){
            String[] item = getIntent().getStringArrayExtra("itemInfo");
            listHairCard.add(new HairCardVO(item[0], item[1], item[2], Integer.parseInt(item[3]),item[4],item[5]));
        }
        return listHairCard;
    }
}
