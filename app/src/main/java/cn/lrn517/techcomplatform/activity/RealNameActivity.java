package cn.lrn517.techcomplatform.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import cn.lrn517.techcomplatform.R;
import cn.lrn517.techcomplatform.bean.common;
import cn.lrn517.techcomplatform.model.UserModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealNameActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button send;
    private ImageView idcard_pic;
    private SharedPreferences sharedPreferences;

    private String uid;

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    private String uimage = null;
    private int state = 2;// 0->使用照相机 1->使用图库 2->未选择图片

    private Call call;
    private UserModel userModel = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);
        sharedPreferences = getSharedPreferences("userInfo" , MODE_PRIVATE);
        uid = sharedPreferences.getString("uid" , null);
        initView();
        initEvent();
    }

    private void initView(){
        toolbar = findViewById(R.id.real_name_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send = findViewById(R.id.real_name_send);
        idcard_pic = findViewById(R.id.real_name_pic);
    }

    private void initEvent(){
        idcard_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageChooseDialog();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state){
                    case 0:
                        if(Build.VERSION.SDK_INT >= 24)
                        {
                            File file = new File(getExternalCacheDir() , "useridcard.jpg");
                            Log.i("photo" , file.toString());
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data") , file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("pic" , file.getName() , requestBody);
                            call = userModel.applyForRealName(body,uid);
                        }
                        else
                        {
                            File file = new File(imageUri.getPath().toString());
                            Log.i("photo" , file.toString());
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data") , file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("pic" , file.getName() , requestBody);
                            call = userModel.applyForRealName(body,uid);
                        }
                        break;
                    case 1:
                        File file = new File(uimage.toString());
                        Log.i("file" , file.toString());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data") , file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("pic" , file.getName() , requestBody);
                        call = userModel.applyForRealName(body,uid);
                        break;
                    case 2:
                        Toast.makeText(RealNameActivity.this, "请选择图片！", Toast.LENGTH_SHORT).show();
                        break;
                }
                Callback<common> commonCallback = new Callback<common>() {
                    @Override
                    public void onResponse(Call<common> call, Response<common> response) {
                        common data = response.body();
                        if( 1 == data.getSuccess()){
                            Toast.makeText(RealNameActivity.this, "申请成功！", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("ispassed" , "1");
                            editor.apply();
                            finish();
                        }else{
                            Toast.makeText(RealNameActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<common> call, Throwable t) {

                    }
                };
                call.enqueue(commonCallback);
            }
        });
    }

    private void ImageChooseDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(RealNameActivity.this);
        normalDialog.setMessage("请选择上传图片方式");
        normalDialog.setPositiveButton("拍照上传", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText( AddActivity.this , "这是拍照上传" , Toast.LENGTH_SHORT).show();
                getImageByTakePhoto();
            }
        });

        normalDialog.setNegativeButton("选择图片上传", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText( AddActivity.this , "这是选择图片上传" , Toast.LENGTH_SHORT).show();
                getImageByChoosePhoto();
            }
        });

        normalDialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO
            }
        });

        normalDialog.show();
    }

    private void getImageByTakePhoto(){
        File outputImage = new File( getExternalCacheDir() , "useridcard.jpg");
        try{
            if( outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if( Build.VERSION.SDK_INT >= 24 )
        {
            imageUri = FileProvider.getUriForFile( RealNameActivity.this ,"cn.lrn517.techcomplatform.fileprovider" , outputImage );
        }
        else
        {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT , imageUri);
        startActivityForResult( intent , TAKE_PHOTO);
    }

    private void getImageByChoosePhoto(){
        if(ContextCompat.checkSelfPermission(RealNameActivity.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(RealNameActivity.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , 1);
        }
        else
        {
            Matisse.from(RealNameActivity.this)
                    .choose(MimeType.allOf())
                    .countable(true)
                    .maxSelectable(1)
                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.album_item_height))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(CHOOSE_PHOTO);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case TAKE_PHOTO:
                if( resultCode == RESULT_OK )
                {
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        idcard_pic.setImageBitmap(bitmap);
                        state = 0;

                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if( resultCode == RESULT_OK)
                {
                    List<Uri> result = Matisse.obtainResult(data);
                    idcard_pic.setImageURI(result.get(0));
                    imageUri = result.get(0);
                    handleImage();
                }
                break;
            default:
                break;
        }
    }

    private void handleImage(){
        if(DocumentsContract.isDocumentUri(this , imageUri))
        {
            String docId = DocumentsContract.getDocumentId(imageUri);
            if( "com.android.providers.media.documents".equals(imageUri.getAuthority()))
            {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                uimage = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , selection);
            }
            else if( "com.android.providers.downloads.documents".equals(imageUri.getAuthority()))
            {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads") , Long.valueOf(docId));
                uimage = getImagePath(contentUri , null);
            }
        }
        else if("content".equalsIgnoreCase(imageUri.getScheme()))
        {
            uimage = getImagePath(imageUri , null);
        }
        else if("file".equalsIgnoreCase(imageUri.getScheme()))
        {
            uimage = imageUri.getPath();
        }
        displayImage(uimage);
    }

    private String getImagePath(Uri imageUri , String selection){
        String path = null;
        //String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(imageUri , null , selection , null , null);
        if( null != cursor )
        {
            if( cursor.moveToFirst())
            {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if( null != uimage )
        {
            Bitmap bitmap = BitmapFactory.decodeFile(uimage);
            uimage = imagePath;
            idcard_pic.setImageBitmap(bitmap);
            state = 1;
        }
    }
}
