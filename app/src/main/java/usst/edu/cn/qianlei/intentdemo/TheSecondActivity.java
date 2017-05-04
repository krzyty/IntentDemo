package usst.edu.cn.qianlei.intentdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class TheSecondActivity extends AppCompatActivity {
    ImageView imageView;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_second);
        imageView = (ImageView) findViewById(R.id.imageView2);
        Intent intent = getIntent();
        if (intent != null) {
            bitmap = intent.getParcelableExtra("bitmap");
            Matrix matrix = new Matrix();
            matrix.postScale(3f, 3f);//将接收到的Thumbnail放大三倍，自己视情更改
            Bitmap bit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            imageView.setImageBitmap(bit);
        }
    }
}
