package com.example.doctruyenfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.doctruyenfirebase.Adapter.ImagePagerAdapter;
import com.example.doctruyenfirebase.Adapter.StoryAdapter;
import com.example.doctruyenfirebase.model.Story;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ImagePagerAdapter imagePagerAdapter;
    private int totalItems;
    private Handler handler = new Handler();
    private static final int DELAY_MS = 3000; // Độ trễ giữa các chuyển đổi slide
    private int currentPage = 0;
    private ImageButton buttonAcc;
    DrawerLayout drawerLayout;
    ImageButton buttonBar;
    private List<Story> storyList = new ArrayList<>();
    private GridView gridView;
    private StoryAdapter storyAdapter;
    private ClipData.Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Quảng cáo Slide
        viewPager = findViewById(R.id.viewPager);
        imagePagerAdapter = new ImagePagerAdapter(this);
        viewPager.setAdapter(imagePagerAdapter);
        totalItems = imagePagerAdapter.getCount();

        // Hiển thị truyện ra màn hình
        gridView = findViewById(R.id.recyclerViewStories);
        storyAdapter = new StoryAdapter(this, storyList);
        gridView.setAdapter(storyAdapter);
        storyList = new ArrayList<>();
        storyAdapter = new StoryAdapter(this, storyList);
        gridView.setAdapter(storyAdapter);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference truyenRef = database.getReference("truyen");

        truyenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storyList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Story truyen = snapshot.getValue(Story.class);
                    storyList.add(truyen);
                }

                storyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

//        Tìm kiếm Truyện
        EditText edtTimKiem = findViewById(R.id.edtTimKiem);
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần thực hiện gì trước khi text thay đổi
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Khi text thay đổi, thực hiện tìm kiếm và cập nhật dữ liệu hiển thị
                filterStories(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Sau khi text đã thay đổi
            }
        });

        showNaviView();
        autoSwipe();
        initUi();
        login();
        onStop();
    }
    private void filterStories(String keyword) {
        List<Story> filteredList = new ArrayList<>();

        for (Story truyen : storyList) {
            if (truyen.getTentruyen().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(truyen);
            }
        }

        // Sắp xếp filteredList theo thứ tự giảm dần của sự giống nhau
        Collections.sort(filteredList, new Comparator<Story>() {
            @Override
            public int compare(Story story1, Story story2) {
                // So sánh độ giống nhau của tên truyện
                String name1 = story1.getTentruyen().toLowerCase();
                String name2 = story2.getTentruyen().toLowerCase();
                return Integer.compare(name2.length() - keyword.length(), name1.length() - keyword.length());
            }
        });

        // Cập nhật GridView với danh sách đã lọc và sắp xếp
        storyAdapter.setStoryList(filteredList);
        storyAdapter.notifyDataSetChanged();
    }
    // Đóng ứng dụng tự động đăng xuất
    protected void onStop() {
        super.onStop();
        // Đăng xuất người dùng ở đây
        FirebaseAuth.getInstance().signOut();
    }

    // Tự động chuyển ảnh Slide
    private void autoSwipe() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = (currentPage + 1) % totalItems;
                viewPager.setCurrentItem(currentPage, true);

                autoSwipe();
            }
        }, DELAY_MS);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Unused
            }

            @Override
            public void onPageSelected(int position) {
                // Unused
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    // When the scrolling state becomes idle, update the current page
                    currentPage = viewPager.getCurrentItem();
                }
            }
        });
    }

    private void initUi() {
        buttonAcc = findViewById(R.id.buttonAcc);
    }

    // Bấm để chuyển sang đăng nhập
    private void login() {
        buttonAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    // Chưa login
                    Intent intent = new Intent(MainActivity.this, MainDangNhap.class);
                    startActivity(intent);
                } else {
                    // Người dùng đã đăng nhập, bạn có thể xử lý logic tại đây nếu cần
                }
            }
        });
    }

    // Hiển thị View 1 bên ( NavigationView )
    private void showNaviView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        buttonBar = findViewById(R.id.buttonBar);
        buttonBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the drawer when the button is clicked
                drawerLayout.openDrawer(findViewById(R.id.nav_view));
            }
        });
    }

//    Chuyển đến trang thêm truyện
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Kiểm tra ID của mục được chọn
        if (id == R.id.dangtruyen) {
            // Mở activity_them_truyen.xml khi người dùng chọn "Đăng truyện"
            Log.d("MainActivity", "Đang chuyển đến ThemTruyen.class");
            Intent intent = new Intent(this, ThemTruyen.class);
            startActivity(intent);
            return true;
        }

        // Xử lý các mục menu khác nếu cần
        return super.onOptionsItemSelected(item);
    }

}
