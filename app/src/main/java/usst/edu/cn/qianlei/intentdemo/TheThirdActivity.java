package usst.edu.cn.qianlei.intentdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;

public class TheThirdActivity extends AppCompatActivity {
    ImageView imageView;
    String photoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_third);
        imageView = (ImageView) findViewById(R.id.imageView3);
        Intent intent = getIntent();
        if (intent != null) {
            photoPath = intent.getStringExtra("photoPath");
            if(photoPath!=null){
                File file = new File(photoPath);
                if(file.exists()){
                    Bitmap bm = BitmapFactory.decodeFile(photoPath);
                    Matrix matrix = new Matrix();
                    matrix.preRotate(90);//通过路径解码为bitmap显示时会自动旋转九十度，此处做一个角度矫正
                    if(bm==null){
                        Toast.makeText(TheThirdActivity.this,"Bitmap is null!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Bitmap bit = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                                bm.getHeight(), matrix, true);
                        imageView.setImageBitmap(bit);
                    }
                }
            }
            else{
                Toast.makeText(TheThirdActivity.this,"Path is null!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
