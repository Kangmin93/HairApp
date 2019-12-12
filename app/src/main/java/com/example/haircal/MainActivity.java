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

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HairCardAdapter.OnItemClickListener{
    private Context mContext;
    private RecyclerView rcc_album;
    private HairCardAdapter mHairCardAdapter;
    private AlbumAdapter mAlbumAdapter;
    private Button btnEnroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnroll = findViewById(R.id.btnEnroll);
        mContext = this;
        init();
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

//        mAlbumAdapter = new AlbumAdapter(mContext, getAlbumList());
//        mAlbumAdapter.setOnItemClickListener(this);
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
        return listHairCard;
    }
    private ArrayList<AlbumVO> getAlbumList() {
        ArrayList<AlbumVO> list_album = new ArrayList<>();
        Gson gson = new Gson();
        try {
            InputStream is = getAssets().open("album.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("album");
            int index = 0;
            while (index < jsonArray.length()) {
                AlbumVO albumVO = gson.fromJson(jsonArray.get(index).toString(), AlbumVO.class);
                list_album.add(albumVO);
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_album;
    }
}
