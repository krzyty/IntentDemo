package usst.edu.cn.qianlei.intentdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TheFirstActivity extends AppCompatActivity implements View.OnClickListener{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    //static final Uri mLocationForPhotos = Uri.fromFile(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES));
    String mCurrentPhotoPath;
    Bitmap bitmapForTrans;
    String pathForTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_first);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
        /*
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //dispatchTakePictureIntent();
                capturePhoto();
            }
        });*/
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.button1:
                capturePhoto();
                break;
            case R.id.button2:
                dispatchTakePictureIntent();
                break;
            case R.id.button3:
                transBitmap();
                break;
            case R.id.button4:
                transFullSizePic();
                break;
            default:
                break;
        }
    }

    //不对照片进行存储，在onActivityResult中返回缩略图
    public void capturePhoto(/*String targetFilename*/) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT,
                //Uri.withAppendedPath(mLocationForPhotos, targetFilename));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,    // prefix
                ".jpg",    // suffix
                storageDir    // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //拍照后存储原始尺寸照片，onActivityResult中返回intent为null
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //直接传递bitmap对象
    protected void transBitmap(){
        Intent intent=new Intent(TheFirstActivity.this,TheSecondActivity.class);
        intent.putExtra("bitmap", bitmapForTrans);
        if(bitmapForTrans!=null){
            startActivity(intent);
        }
        else{
            Toast.makeText(TheFirstActivity.this,"Please press the first button to take a photo and return the thumbnail!",Toast.LENGTH_SHORT).show();
        }
    }

    //intent传递内容的大小有限制（1M左右），无法对原始尺寸图片通过intent传递，因而此处传递图片的路径
    protected void transFullSizePic(){
        if(mCurrentPhotoPath!=null){
            pathForTrans=mCurrentPhotoPath;
            Intent intent=new Intent(TheFirstActivity.this,TheThirdActivity.class);
            intent.putExtra("photoPath", pathForTrans);
            startActivity(intent);
        }
        else{
            Toast.makeText(TheFirstActivity.this,"Please press the second button to take a photo and save the full size pic!",Toast.LENGTH_SHORT).show();
        }
        /*方法二：压缩传递
        Bitmap bm;
        if(mCurrentPhotoPath!=null){
            File file = new File(mCurrentPhotoPath);
            if(file.exists()){
                // Get the dimensions of the View
                int targetW = 50;//自己视情更改
                int targetH = 30;
                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;
                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;
                bm = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                Intent intent=new Intent(TheFirstActivity.this,TheThirdActivity.class);
                intent.putExtra("bitmap", bm);
                startActivity(intent);
            }
            else{
                Toast.makeText(TheFirstActivity.this,"Please press the second button to take a photo and save the full size pic!",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(TheFirstActivity.this,"Please press the second button to take a photo and save the full size pic!",Toast.LENGTH_SHORT).show();
        }
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView mImageView;
        mImageView=(ImageView)findViewById(R.id.imageView1);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data!=null) {
            //Bitmap thumbnail = data.getParcelableExtra("data");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");//获取返回的Thumbnail
            mImageView.setImageBitmap(imageBitmap);
            bitmapForTrans=imageBitmap;
            // Do other work with full size photo saved in mLocationForPhotos

        }
    }
}
