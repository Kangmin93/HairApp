package com.example.haircal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haircal.adapter.HairCardAdapter;
import com.example.haircal.vo.HairCardVO;
import com.example.haircal.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HairCardAdapter.OnItemClickListener {
    private Context mContext;
    private RecyclerView rcc_album;
    private HairCardAdapter mHairCardAdapter;
    private Button btnEnroll;
    private final String dbName = "hair";
    private  final String tableName = "person";
    private ArrayList<HairCardVO> list = null;
    private PopupWindow mPopWindow;

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
        mHairCardAdapter.setOnItemClickListener(new HairCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, HairCardVO hairCardVO) {
                Intent i = new Intent(getApplicationContext(),ImageActivity.class);
                i.putExtra("imgPath",hairCardVO.getImgTest());
                startActivity(i);
            }
        });
        mHairCardAdapter.setOnItemLongClickListener(new HairCardAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final HairCardVO hairCardVO) {
                final List<String> listItems = new ArrayList<>();
                listItems.add("수정");
                listItems.add("삭제");
                final CharSequence[] items = listItems.toArray(new String[listItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(hairCardVO.getHairShop());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = items[which].toString();
                        switch (selectedText){
                            case "수정":
                                Intent i = new Intent(MainActivity.this,UpdateActivity.class);
                                i.putExtra("vo",hairCardVO);
                                startActivity(i);
                                break;
                            case "삭제":

                                break;
                        }
                        Toast.makeText(getApplicationContext(),selectedText,Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

                //popupWindow
//                Toast.makeText(getApplicationContext(),"long",Toast.LENGTH_SHORT).show();
//                View popupView = getLayoutInflater().inflate(R.layout.dialog,null);
//                mPopWindow = new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                mPopWindow.setFocusable(true);
//                mPopWindow.showAtLocation(popupView,Gravity.CENTER,0,0);
//
//                Button btn_delete, btn_update;
//                TextView tv_popuptitle;
//                btn_delete = popupView.findViewById(R.id.btn_delete);
//                btn_update = popupView.findViewById(R.id.btn_update);
//                tv_popuptitle = popupView.findViewById(R.id.tv_popuptitle);
//                tv_popuptitle.setText(hairCardVO.getHairShop());
//                btn_delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(),hairCardVO.getHairShop()+"삭제",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                btn_update.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(),hairCardVO.getHairShop()+"수정",Toast.LENGTH_SHORT).show();
//                    }
//                });

                //popupmenu
//                PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
//                getMenuInflater().inflate(R.menu.popup_list,popupMenu.getMenu());
//
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.popup_delete:
//                                Toast.makeText(getApplicationContext(),"DELETE",Toast.LENGTH_SHORT).show();
//
//                                break;
//                            case R.id.popup_update:
//                                Toast.makeText(getApplicationContext(),"SAVE",Toast.LENGTH_SHORT).show();
//
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
            }
        });

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
