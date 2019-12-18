package com.example.haircal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haircal.R;
import com.example.haircal.vo.HairCardVO;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    private final String dbName = "hair";
    private  final String tableName = "person";

    ArrayList<HashMap<String, String>> personList;
    ListView list;
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE ="phone";

    SQLiteDatabase sampleDB = null;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        final TextView txt_update_oday = findViewById(R.id.txt_update_day);
        final EditText edt_update_salon = findViewById(R.id.edt_update_salon);
        final EditText edt_update_designer = findViewById(R.id.edt_update_designer);
        final EditText edt_update_price = findViewById(R.id.edt_update_price);
        final EditText edt_update_comment = findViewById(R.id.edt_update_comment);
        final ImageView iv_update_result = findViewById(R.id.iv_update_result);

        HairCardVO vo = (HairCardVO) getIntent().getSerializableExtra("vo");
        txt_update_oday.setText(vo.getDate());
        edt_update_salon.setText(vo.getHairShop());
        edt_update_designer.setText(vo.getDesigner());
        edt_update_price.setText(Integer.toString(vo.getPirce()));
        edt_update_comment.setText(vo.getComment());
        Uri uri = Uri.parse("file:///"+vo.getImgTest());
        iv_update_result.setImageURI(uri);
    }
}
