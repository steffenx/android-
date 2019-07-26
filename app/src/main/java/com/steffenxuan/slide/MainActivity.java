package com.steffenxuan.slide;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView left,right;//顶部栏左右按钮
    private android.support.design.widget.NavigationView navigationview;//导航视图
    private android.support.v4.widget.DrawerLayout drawerlayout;//抽屉
    private ImageView person_pic;//侧滑头像
    private TextView companyText,addressText;//侧滑文字信息
    private TabLayout tabLayout;//标签
    private ViewPager viewPager;//滑动


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initFindId();
        initView();
        initViewPage();
        
    }


    /**
     * 初始化控件
     */
    private void initFindId() {
        left=findViewById(R.id.leftimgview);
        right=findViewById(R.id.rightimgview);
        navigationview=findViewById(R.id.nav);
        drawerlayout=findViewById(R.id.mdw);
        tabLayout =  findViewById(R.id.mtab);
        viewPager =  findViewById(R.id.mvp);
    }

    /**
     * 初始化侧滑菜单
     */
    private void initView() {
        //添加左右点击监听
        right.setOnClickListener(this);
        left.setOnClickListener(this);
        //图片圆角
        Glide.with(this).load(R.mipmap.tx).apply(RequestOptions.bitmapTransform(new RoundedCorners(45))).into(left);

        //监听侧滑菜单按钮点击
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.group_1:
                        Toast.makeText(MainActivity.this,"我的消息",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.group_2:
                        Toast.makeText(MainActivity.this,"商城",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.group_3:
                        Toast.makeText(MainActivity.this,"退出登录",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_1:
                        Toast.makeText(MainActivity.this,"会员中心",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_2:
                        Toast.makeText(MainActivity.this,"设置",Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerlayout.closeDrawer(GravityCompat.START);//关闭左边侧滑菜单
                return true;
            }
        });


        //侧滑菜单中的控件  要先获取到头部局才可以
        if(navigationview.getHeaderCount() > 0){
            View headerLayout = navigationview.getHeaderView(0);
            person_pic = headerLayout.findViewById(R.id.person_pic);
            companyText=headerLayout.findViewById(R.id.companyText);
            addressText=headerLayout.findViewById(R.id.addressText);
        }
        else {
            View headerLayout = navigationview.inflateHeaderView(R.layout.layout_head);
            person_pic = headerLayout.findViewById(R.id.person_pic);
            companyText=headerLayout.findViewById(R.id.companyText);
            addressText=headerLayout.findViewById(R.id.addressText);
        }
        //圆角
        Glide.with(this).load(R.mipmap.tx).apply(RequestOptions.bitmapTransform(new RoundedCorners(150))).into(person_pic);
    }


    private void initViewPage() {
            //添加适配器
            viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment=new Fragment();
                if(fragment!=null){
                    switch (position){
                        case 0:
                            fragment=new FragmentMain();
                            break;
                        case 1:
                            fragment=new FragmentSecond();
                            break;
                        case 2:
                            fragment=new FragmentEnd();
                            break;
                    }
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        //ViewPager关联到Tablayout中
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        tabLayout.getTabAt(0).setCustomView(getTabView("首页"));
        tabLayout.getTabAt(1).setCustomView(getTabView("另一页"));
        tabLayout.getTabAt(2).setCustomView(getTabView("最后"));


        //监听选中
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view=tab.getCustomView();
                TextView textView = view.findViewById(R.id.tabtxt);
                textView.setTextColor(Color.parseColor("#ed8200"));
                textView.setTextSize(16);
                textView.getPaint().setFakeBoldText(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView textView = view.findViewById(R.id.tabtxt);
                textView.setTextColor(Color.parseColor("#999999"));
                textView.setTextSize(14);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 获得Tablayout中Tab所在的View
     * @param titleName
     * @return
     */
    private View getTabView(String titleName) {
        Log.d("asd", "getTabView: "+titleName);
        //载入自定义标签
        View view = LayoutInflater.from(this).inflate(R.layout.layout_tab_item, null);
        TextView textView = view.findViewById(R.id.tabtxt);
        textView.setText(titleName);

        //默认选中的页面
        if (titleName.equals("首页")) {
            textView.setTextColor(Color.parseColor("#ed8200"));
            textView.setTextSize(16);
        }
        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rightimgview:
                showmenu(view);
                break;
            case R.id.leftimgview:
                if(drawerlayout.isDrawerOpen(navigationview)){
                    drawerlayout.closeDrawer(navigationview);
                }else {
                    drawerlayout.openDrawer(navigationview);
                }
        }
    }

    /**
     * 弹出菜单选项
     * @param view
     */
    public void showmenu(View view){
        PopupMenu popupMenu=new PopupMenu(MainActivity.this,view);//实例化PopupMenu
        getMenuInflater().inflate(R.menu.menu_main,popupMenu.getMenu());//加载Menu资源
        //监听点击
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.one:
                        Toast.makeText(MainActivity.this,"one",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.two:
                        Toast.makeText(MainActivity.this,"two",Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutUtil.setIndicator(tabLayout,30,30,0);//通过反射，改变标签下划线宽度
            }
        });
    }
}
