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
        Cursor c = ReadDB.rawQuery("SELECT * FROM " + tableName, null);
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
        if(list !=null){
            for(HairCardVO vo : list){
                listHairCard.add(vo);
            }
        }
//        if(getIntent().getStringArrayExtra("itemInfo")!=null){
//            String[] item = getIntent().getStringArrayExtra("itemInfo");
//            listHairCard.add(new HairCardVO(item[0], item[1], item[2], Integer.parseInt(item[3]),item[4],item[5]));
//        }
        return listHairCard;
    }
}
