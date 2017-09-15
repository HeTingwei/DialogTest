package com.xingyi.test;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //AlertDialog，在android老版本与新版本之间，下面的按钮位置有很大不同
    public void alertDialogClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.
                Builder(MainActivity.this);
        dialog.setTitle("对话框最上面的字");//对话框最上面的字
        dialog.setMessage("对话框中部的字");//对话框中部的字
        dialog.setCancelable(false);//如果是false，点击其他位置或者返回键无效，这个地方默认为true
//以下为对话框最下面的选择项
        dialog.setPositiveButton("右边", new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "右边", Toast.LENGTH_SHORT).show();
            }

        });
        //需要第二个按钮才添加如下的setNegativeButton()
        dialog.setNegativeButton("左边", new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "左边", Toast.LENGTH_SHORT).show();
            }

        });
        //需要第三个按钮时，才添加如下的setNeutralButton()，android老版本是在中间，在新版本是在前面，没有研究从哪里开始变的
        dialog.setNeutralButton("前面", new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "前面", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //ProgressDialog
    public void progressDialogClick(View v) {
        //官网上是说Android 8.0已经弃用，但实际在8.0模拟器运行，没有发现问题。官网没有给出效果相同的直接代替的类
        //举例用ProgressBar替换
        ProgressDialog progressDialog = new ProgressDialog
                (MainActivity.this);
        progressDialog.setTitle("对话框上部的字");
        progressDialog.setMessage("正在加载.....");
        progressDialog.setCancelable(true);//如果是false，点击其他位置或者返回键无效，默认为true
        progressDialog.show();
        //progressDialog.dismiss();此句让progressDialog消失
    }

    //自定义对话框(可定义对话框大小)(比较麻烦)
    public void myClick(View v){
        View layout = LayoutInflater.from(this).inflate(R.layout.my_dialog, null);
        //在对话框里加载布局：setView()方法
        final AlertDialog.Builder dialog = new AlertDialog.
                Builder(this,R.style.style_dialog);
        dialog.setCancelable(false)//如果是false，点击其他位置或者返回键无效，这个地方默认为true
                .setView(layout);//这里加载布局
        final AlertDialog alert = dialog.create();

        //设置对话框的宽和高(注意，设置长宽还需要设置R.style.style_dialog，否则会出现很多问题)
        Window window = alert.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);//void setPadding (int left, int top,int right,int bottom)
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);//对话框出现的位置

        //按钮监听事件
        Button btLeft, btRight;
        btLeft = layout.findViewById(R.id.leftBt);
        btRight = layout.findViewById(R.id.rightBt);
        btLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里写左按钮点击响应事件
                Toast.makeText(MainActivity.this, "左", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });

        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里写右按钮点击响应事件
                Toast.makeText(MainActivity.this, "右", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });
        alert.show();
    }

    //自定义对话框（不可定义大小：对话框始终在宽度上铺满屏幕,高度始终是Wrap_content）(比较简单)
    public void myClick2(View v){
        MyDialog2 dialog = new MyDialog2();
        dialog.show(getFragmentManager(),"TAG");
    }

    //单选列表对话框
    public void radioListClick(View v){
        String[] arr={"第一项","第二项","第三项"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("单选列表对话框")
                .setItems(arr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // which是点击列表位置
                        Toast.makeText(MainActivity.this, which+"", Toast.LENGTH_SHORT).show();
                    }
                });
         builder.create().show();
    }

    List <String>mSelectedItems;//用于多选列表对话框选择项的暂时存储

    //多选列表对话框
    public void multipleListClick(View v){
        String[] arr={"第一项","第二项","第三项"};
        mSelectedItems = new ArrayList();  //用来存放被选中的项
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle("多选列表对话框")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(arr, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // 如果选择一项，那么在选定的项中添加此项
                                    mSelectedItems.add(which+"");
                                } else if (mSelectedItems.contains(which)) {
                                    // 如果取消选择，那么在选定的项中移除此项
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //确定
                        String result="";
                        for(String str:mSelectedItems){
                            result+=str;
                        }
                        //这里输出被选中项
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //取消
                    }
                });

         builder.create().show();
    }





}
