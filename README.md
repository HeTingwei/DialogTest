# Android 对话框相关总结
对话框相关总结，包括AlertDialog,ProgressDialog和自定义对话框
常用的对话框相关的有：AlertDialog,ProgressDialog，自定义的对话框（自定义的对话框的长宽设置需要注意），还有任意位置在其他应用上都可以弹出的对话框。其中任意位置在其他应用上都可以弹出的对话框见另一篇博文：[点击跳转](http://blog.csdn.net/htwhtw123/article/details/71758817)。该demo的详细讲解同样可以看博文：[点击跳转](http://blog.csdn.net/htwhtw123/article/details/77488040)只有“将事件传递回对话框的宿主”的例子在module:"passEventToHost"里，其他都在dialog里。

## 1.纯粹的 AlertDialog
AlertDialog对话框是非常常用的对话框，一般又一个头部，中间的说明文字和下面的按钮组成。按钮的数量可以是：1、2或3。注意的是，下面按钮中，最前面到的那个按钮，在Android的不同版本中，位置是不同的。
下面给出代码：

```
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
        //需要第三个按钮时，才添加如下的setNeutralButton()，android老版本是在中间，比如4.0，在新版本是在前面，没有研究从哪个版本开始变的
        dialog.setNeutralButton("前面", new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "前面", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
```
<br>
AlertDialog在android 8.0 模拟器上运行效果：<br>

![AlertDialog](https://github.com/HeTingwei/DialogTest/blob/master/doc/dialog1.gif)

## 2. ProgressDialog
虽然，ProgressDialog官方在Androidd 8.0中已经弃用它了：“This class was deprecated in API level 26.“，但是实际用Android 8.0模拟器运行并没有出现问题。而且官网没有给出效果相同的直接代替的类。只是建议用别的东西代替，如ProgressBar。
下面给出代码：

```
ProgressDialog progressDialog = new ProgressDialog
                (MainActivity.this);
        progressDialog.setTitle("对话框上部的字");
        progressDialog.setMessage("正在加载.....");
        progressDialog.setCancelable(true);//如果是false，点击其他位置或者返回键无效，默认为true
        progressDialog.show();
        //progressDialog.dismiss();//此句让progressDialog消失
```
ProgressDialog在android 8.0 模拟器上运行效果：<br>

![ProgressDialog](https://github.com/HeTingwei/DialogTest/blob/master/doc/dialog2.gif)

## 3. 自定义对话框
   自定义对话框，就是自己写一个布局，让对话框加载布局，并且能监听这个布局里面的点击事件，并且对话框可以响应点击消失。
首先写一个对话框的布局：my_dialog.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="wrap_content"
              android:layout_height="90dp"
              android:background="@color/colorAccent"
              android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="自定义对话框"
        android:textSize="20sp"/>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginTop="20dp">

        <Button
            android:id="@+id/leftBt"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="左按钮"/>

        <Button
            android:id="@+id/rightBt"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="右按钮"/>
    </LinearLayout>

</LinearLayout>
```
<br>

设计对话框的style,目的是去控制对话框长宽，并且避免出现一些上部阴影，下面是添加在styles.xml中的代码，其中windowIsFloating让java代码部分控制大小生效，windowNoTitle避免出现一些上部阴影：

```
<style name="style_dialog" parent="android:style/Theme.Dialog">
        <item name="android:windowNoTitle">true</item>
        <item name="android:windowIsFloating">true</item>
    </style>
```

在java代码部分会用到上面的文件，

```
 //获取自定义布局view实例
        View layout = LayoutInflater.from(this).inflate(R.layout.my_dialog, null);
        //在对话框里加载布局：setView()方法
        final AlertDialog.Builder dialog = new AlertDialog.
                Builder(MainActivity.this,R.style.style_dialog);
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
                Toast.makeText(MainActivity.this, "左按钮", Toast.LENGTH_SHORT).show();
                alert.dismiss();//让对话框消失
            }
        });

        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里写右按钮点击响应事件
                Toast.makeText(MainActivity.this, "右按钮", Toast.LENGTH_SHORT).show();
                alert.dismiss();//让对话框消失
            }
        });
        alert.show();
    }
```
自定义对话框在android 8.0 模拟器上运行效果：<br>

![自定义对话框](https://github.com/HeTingwei/DialogTest/blob/master/doc/dialog3.gif)



## 4.单选列表对话框

由于列表出现在对话框的内容区域，因此对话框无法同时显示消息和列表，通过 setTitle() 为对话框设置标题。要想指定列表项，调用setItems() 来传递一个数组。或者，也可以使用 setAdapter() 指定一个列表。 这样一来，您就可以使用 ListAdapter 以动态数据（如来自数据库的数据）支持列表。<br>
```
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
         
```
效果如下：<br>
![单选列表](https://github.com/HeTingwei/DialogTest/blob/master/doc/dialog4.gif)
## 5.多选列表对话框

需要定义一个List对象存储选定内容，在点击确定时读取。依靠setMultiChoiceItems()方法实现多选列表对话框。
```
List <String>mSelectedItems;//用于多选列表对话框选择项的暂时存储


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
```
效果如下：<br>
![多选列表](https://github.com/HeTingwei/DialogTest/blob/master/doc/dialog5.gif)
