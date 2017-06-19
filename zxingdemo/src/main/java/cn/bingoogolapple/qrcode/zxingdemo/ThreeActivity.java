package cn.bingoogolapple.qrcode.zxingdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class ThreeActivity extends AppCompatActivity {
    public EditText editText = null;
    public Button button = null;
    public Button button1 = null;
    public ImageView imageView = null;

    public Bitmap mBitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        initView();
    }
    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
    private void initView() {
        editText = (EditText) findViewById(R.id.edit_content);
        button = (Button) findViewById(R.id.button_content);
        button1 = (Button) findViewById(R.id.button1_content);
        imageView = (ImageView) findViewById(R.id.image_content);
        /**
         * 生成带log二维码图片
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String textContent = editText.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(ThreeActivity.this, "您输入为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                editText.setText("");
                new Thread(){
                    @Override
                    public synchronized void start() {
                        super.start();
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bga_pp_ic_cb_normal,getBitmapOption(2));
                        mBitmap = QRCodeEncoder.syncEncodeQRCode(textContent, 400, Color.BLACK, bitmap);
                        imageView.setImageBitmap(mBitmap);
                    }
                }.start();
            }
        });

        /**
         * 生成二维码图片
         */

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String textContent = editText.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(ThreeActivity.this, "您的输入为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        editText.setText("");
                        mBitmap = QRCodeEncoder.syncEncodeQRCode(textContent, 400);
                        imageView.setImageBitmap(mBitmap);
                    }
                }).start();

            }
        });
    }
}
