package com.example.haircal.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.haircal.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.util.HashMap;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CameraActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_camera);
        final TextView txt_today = findViewById(R.id.txt_today);
        final EditText edt_salon = findViewById(R.id.edt_salon);
        final EditText edt_designer = findViewById(R.id.edt_designer);
        final EditText edt_price = findViewById(R.id.edt_price);
        final EditText edt_comment = findViewById(R.id.edt_comment);
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.getDefault()).format(currentTime);
        txt_today.setText(date_text);

        // 권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();


        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_date, str_salon, str_designer, str_price, str_comment;
                str_salon = edt_salon.getText().toString();
                str_designer = edt_designer.getText().toString();
                str_price = edt_price.getText().toString();
                str_date = txt_today.getText().toString();
                str_comment = edt_comment.getText().toString();
                if(str_salon.length()==0||str_designer.length()==0||str_comment.length()==0||str_price.length()==0){
                    Toast.makeText(getApplicationContext(),"입력 덜했다!!!!", Toast.LENGTH_SHORT).show();
                }else{
                    try {


                        sampleDB = openOrCreateDatabase(dbName, MODE_PRIVATE, null);

                        //테이블이 존재하지 않으면 새로 생성합니다.
                        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                                + " (img VARCHAR(100), salon VARCHAR(20), name VARCHAR(20), price VARCHAR(20), date VARCHAR(30), comment VARCHAR(100) );");

                        //새로운 데이터를 테이블에 집어넣습니다..
                        sampleDB.execSQL("INSERT INTO " + tableName
                                + " (img, salon, name, price, date, comment)  Values ('" + imageFilePath + "', '" + str_salon+"', '" +str_designer+"', '" + str_price+"', '" + str_date+"', '" + str_comment+"');");

                        sampleDB.close();


                    }catch (SQLiteException se) {
                        Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("", se.getMessage());


                    }

                    Intent i = new Intent(CameraActivity.this,MainActivity.class);

                    String[] itemInfo = {imageFilePath,str_salon,str_designer,str_price,str_date,str_comment};
                    i.putExtra("itemInfo", itemInfo);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }


            }
        });

        findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null){
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }catch (IOException e){

                    }
                    if (photoFile != null) {
                        photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }



                }
            }
        });
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        Log.w(this.getClass().getName(),imageFilePath);


        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }


            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);
            } else {
                exifDegree = 0;
            }

            ((ImageView) findViewById(R.id.iv_result)).setImageBitmap(rotate(bitmap, exifDegree));

        }
    }

    private int exifOrientationToDegress(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }






    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨",Toast.LENGTH_SHORT).show();
        }
    };


}
