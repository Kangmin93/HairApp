package com.example.haircal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private final String dbName = "hair";
    private  final String tableName = "person";
    ArrayList<HairCardVO> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnroll = findViewById(R.id.btnEnroll);
        mContext = this;
        list = new ArrayList<HairCardVO>();

        SQLiteDatabase ReadDB = openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        ReadDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                + " (img VARCHAR(100), salon VARCHAR(20), name VARCHAR(20), price VARCHAR(20), date VARCHAR(30), comment VARCHAR(100) );");

        //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다..
        Cursor c = ReadDB.rawQuery("SELECT * FROM " + tableName+" ORDER BY date desc", null);
        String imgTest = null;
        String hairShop = null;
        String designer = null;
        int price = 0;
        String date = null;
        String comment = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    //테이블에서 두개의 컬럼값을 가져와서
                    imgTest = c.getString(c.getColumnIndex("img"));
                    hairShop = c.getString(c.getColumnIndex("salon"));
                    designer  = c.getString(c.getColumnIndex("name"));
                    price  = Integer.parseInt(c.getString(c.getColumnIndex("price")));
                    date  = c.getString(c.getColumnIndex("date"));
                    comment  = c.getString(c.getColumnIndex("comment"));
                    HairCardVO hairvo = new HairCardVO(imgTest,  hairShop,  designer,  price,  date,  comment);
                    list.add(hairvo);
                } while (c.moveToNext());
                Log.i("checklist", list.get(0).getHairShop());
            }
        }
        ReadDB.close();

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
        if(list !=null){
            for(HairCardVO vo : list){
                listHairCard.add(vo);
            }
        }
        return listHairCard;
    }
}
