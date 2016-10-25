---
title: Material Design的基本控件使用（一）
date: 2016-10-25 16:21:23
tags: Material Design， RecycleView
---
自从知道了Material Design之后，做的APP界面布局或者控件，都爱用MaterialDesign风格做。本文设计简单的RecyclerView、Toolbar、FloatingAction Button以及
Switch Button的使用。

<!-- more -->
## 上图
<img src="https://github.com/pearl2015/components/blob/master/RecyclerViewExample/pic/example.gif?raw=true" width="40%" height="40%">

## RecyclerView
### 添加依赖
RecyclerView 位于android.support.v7.widget，如果targeted sdk版本高于22（我的是24）还是多少，就可以不用添加依赖。此步骤可以忽略。
```xml
 //for material design
    compile 'com.android.support:design:24.2.1'
    compile 'com.android.support:recyclerview-v7:24.2.1' 
```
### 搞定布局
1. activity_main.xml
  关于添加Recyclerview的部分代码：
```xml 
<LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="15">
        <android.support.v7.widget.RecyclerView
            android:id="@+id/list_rv"
            android:layout_height="match_parent"
            android:layout_width="match_parent"
            />
    </LinearLayout>
```
2. itemlayout.xml
在layout文件夹下，新建itemlayout.xml，为list中每个record的视图，代码如下：
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="30dp"
    android:layout_marginLeft="5dp"
    android:layout_marginRight="5dp">
    <TextView
        android:id="@+id/topic_tv"
        android:layout_width="120dp"
        android:layout_height="wrap_content" />
    <TextView
        android:id="@+id/time_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toRightOf="@id/topic_tv"
        android:layout_marginRight="30dp"
        android:layout_centerHorizontal="true"
        android:text="2016/10/21"
        android:textSize="13sp"/>
    <Button
        android:id="@+id/del_btn"
        android:layout_width="wrap_content"
        android:layout_height="18dp"
        android:layout_toRightOf="@id/time_tv"
        android:text="cancel"
        android:background="@drawable/btn_shape"
        android:textAllCaps="false"/>
</RelativeLayout>
```

### bean的创建
我觉得对于ListView来说，每个record都是一个数据项，Viewholder+itemlayout是数据项的视图表示和显示，而bean类是数据项的数据表示，因此，新建一个bean类：ListUnit.java
```java
public class ListUnit implements Parcelable {
    private String topic;
    private String time;

    protected ListUnit(Parcel in) {
        topic = in.readString();
        time = in.readString();
    }

    public ListUnit(){}

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Creator<ListUnit> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<ListUnit> CREATOR = new Creator<ListUnit>() {
        @Override
        public ListUnit createFromParcel(Parcel in) {
            return new ListUnit(in);
        }

        @Override
        public ListUnit[] newArray(int size) {
            return new ListUnit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topic);
        dest.writeString(time);
    }
}

```

当然，简单一点的话，也不用创建bean类，有时候，如果record只有一个数据项变更，而且是字符串的话，这步就可以省略了。
### 新建Adapter- new class： NewAdapter
就跟listview的使用一样，Adapter将数据集适配到RecyclerView上,且新的Adapter中使用一个新建的ViewHolder类：

```java

    public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {

        private List<ListUnit> mDatas = new ArrayList<>();
        private Context context;

        public NewAdapter(List<ListUnit> mDatas, Context context) {
            this.mDatas = mDatas;
            this.context = context;
        }

        @Override
        public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NewViewHolder holder = new NewViewHolder(LayoutInflater.from(context)
                                    .inflate(R.layout.itemlayout,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(NewViewHolder holder, int position) {
            //
                ListUnit temp = mDatas.get(position);
                holder.topic_tv.setText(temp.getTopic());
                holder.time_tv.setText(temp.getTime());


        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class NewViewHolder extends RecyclerView.ViewHolder{

            private TextView topic_tv;
            private TextView time_tv;
            private Button del_btn;

            public NewViewHolder(View itemView) {
                super(itemView);
                 topic_tv = (TextView)itemView.findViewById(R.id.topic_tv);
                time_tv = (TextView)itemView.findViewById(R.id.time_tv);
                del_btn = (Button)itemView.findViewById(R.id.del_btn);

            }
        }
    }

```
### 数据与视图的绑定/适配
前面的过程已经创建好了视图（layout）和适配器（Adapter），数据也准备好了（ListUnit），最后就是绑定（Adapter）数据到视图上了。
1. 添加成员变量
```java
    RecyclerView mRecycleView;
    private List<ListUnit> mDatas;
    private NewAdapter mAdapter;
```
2. 初始化视图，然后为数据添加适配器
```java
        //recyclerview
        mRecycleView = (RecyclerView)findViewById(R.id.list_rv);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewAdapter(mDatas,MainActivity.this);
        mRecycleView.setAdapter(mAdapter);
```

## Toolbar
Toolbar默认会产生那个三个竖点点的，但是，有时候，我们经常需要添加别的图标在Toolbar上。
### 基础的menu设置，就是那三个点点里面的内容：新建menu文件，右键app，新建Android Recource File中，选menu: base_toolbar_menu.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/setting_menu"
        android:title="setting"
        android:showAsAction="never"
        />
</menu>
```
还需要别的直接添加item就可以了
### 在main视图中添加toolbar： activity_main.xml中添加代码：
```xml
    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_height="45dp"
        android:layout_width="match_parent"
        android:background="@color/gray"
        android:layout_gravity="left"
        >
        <TextView
            android:id="@+id/app_name_tv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/app_name"
            android:textSize="15dp"/>
    </android.support.v7.widget.Toolbar>
```
这个的toolbar有点单调，继续添加，我添加一个switch button，我是使用的别人的switch button
###Switch Button
1. 添加依赖
```xml
  //swith button
    compile 'com.kyleduo.switchbutton:library:1.4.4'
```
2. 将switch button添加到toolbar：新activity_main.xml:
```xml
    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_height="45dp"
        android:layout_width="match_parent"
        android:background="@color/gray"
        android:layout_gravity="left"
        >
        <TextView
            android:id="@+id/app_name_tv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/app_name"
            android:textSize="15dp"/>
        <com.kyleduo.switchbutton.SwitchButton
            android:id="@+id/switch_btn"
            style="@style/SwitchButtonMD"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="180dp"/>
    </android.support.v7.widget.Toolbar>
```
### Toolbar的实现
1.添加成员变量
```java
    Toolbar toolbar;
    SwitchButton start_btn;
```
2. 添加适配器
```java
 //toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //switch button
        start_btn = (SwitchButton)findViewById(R.id.switch_btn);
        start_btn.setOnClickListener(this);
```
3. 为switch button添加响应：
```java
  @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_btn:
                boolean on = start_btn.isChecked();
                Toast.makeText(getApplicationContext(),on+"",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
```
Toolbar完成～
## Floating Action Button
### 添加依赖
。。。前面加的那些依赖就已经满足了。。。
### 布局
```xml
    <android.support.design.widget.FloatingActionButton
        android:id="@+id/subscribe_fab"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        android:src="@android:drawable/ic_input_add"
        android:elevation="8dp"
        android:clickable="true"
        />
```
### 实现
1. 添加成员变量
```java
    private FloatingActionButton add_fab;
```
2. 视图初始化
```java
        start_btn = (SwitchButton)findViewById(R.id.switch_btn);
        start_btn.setOnClickListener(this);
```
3. 添加响应:
```java
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_btn:
                boolean on = start_btn.isChecked();
                Toast.makeText(getApplicationContext(),on+"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.subscribe_fab:
                //click the fab, add a new record to the recyclerview list
                ListUnit newtopic = new ListUnit();
                newtopic.setTopic("new");
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss");
                String curtime = df.format(new Date());
                newtopic.setTime(curtime);
                mDatas.add(newtopic);
                mAdapter.notifyDataSetChanged();
            default:
                break;
        }
    }
```
##Github下载地址：
https://github.com/pearl2015/components.git/RecyclerViewExample
