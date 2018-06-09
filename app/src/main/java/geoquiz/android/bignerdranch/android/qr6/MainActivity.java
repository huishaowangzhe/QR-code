package geoquiz.android.bignerdranch.android.qr6;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private static int REQUEST_CODE = 1;
    private Button but2;
    private EditText editText;
    private ImageView imageView;
    private EditText mQuery;
    private Bitmap mBitmap;
    Dialog dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "c22f7d5f3cf16e33db01670599e9487c");
        BmobSMS.initialize(this,"c22f7d5f3cf16e33db01670599e9487c");
        but2 = (Button) findViewById(R.id.button_2);
        editText = (EditText) findViewById(R.id.et_1);
        imageView = (ImageView) findViewById(R.id.iv_1);
        mQuery = (EditText) findViewById(R.id.query_et);

//bmob的id


    }

    //取出文本框内容
    public void login(View view){
        String texts = editText.getText().toString().trim();
        if (TextUtils.isEmpty(texts)){
            return;
        }else {
            qr_code codeObj = new qr_code();
            codeObj.setText(texts);
            codeObj.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null){
                        return;
                    }
                }
            });
        }

        Bitmap bitmap = CodeUtils.createImage(texts, 800, 800,
                null);
        // 这里是获取图片Bitmap，也可以传入其他参数到Dialog中
        MyDialog.Builder dialogBuild = new  MyDialog.Builder(this);
        dialogBuild.setImage(bitmap);
        MyDialog dialog = dialogBuild.create();
        dialog.setCanceledOnTouchOutside(true);// 点击外部区域关闭
        dialog.show();

        //输出二维码图片
        /*if (TextUtils.isEmpty(texts)) {
            Toast.makeText(MainActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        editText.setText("");
        mBitmap = CodeUtils.createImage(texts, 400, 400,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        imageView.setImageBitmap(mBitmap);*/


        qr_code gameScore = new qr_code();
        gameScore.setText(texts);
        gameScore.update("7e8f9a2e71", new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","更新成功");
                }else{
                    Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });



        /*Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        Bitmap data = mBitmap;
        intent.putExtra("extra_data",data);
        startActivity(intent);
*/

    }

//查询所有学生信息
    public void queryData(View view) {
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> persons, BmobException e) {

                if(e==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("姓名 ，学号");
                    String str = "";
                    for (Person feed : persons) {
                        str += feed.getsName() + "," + feed.getsPassword() + "\n";
                        builder.setMessage(str);
                        builder.create().show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }




//查询某个学生信息
    public void queryOneData(View view) {
        String str = mQuery.getText().toString();
        if (str.equals(""))
            return;
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.addWhereEqualTo("name", str);   //条件查询，传进去要查询的name
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> persons, BmobException e) {
                if(e==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);  //定义dialog，显示查询信息
                    builder.setTitle("姓名 ，学号");
                    String str = "";   //存储查询结果
                    for (Person feed : persons)
                        str += feed.getsName() + "," + feed.getsPassword() + "\n";
                    builder.setMessage(str);
                    builder.create().show();
                }
            }

        });
    }
}
