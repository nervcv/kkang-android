package com.example.heo.step11application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Lab12_2Activity extends AppCompatActivity {

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab12_2);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        // ContextMenu 와 특정 뷰를 연결할 때에 registerForContextMenu 사용
        registerForContextMenu(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lab2, menu);
        // Spannable 을 이용하는 방법은 메뉴 문자열 데이터에 ImageSpan을 추가해서 아이콘 이미지가 함께 출력되게 하는 방법

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        // ActionView
        MenuItem menuItem = menu.findItem(R.id.menu_main_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

        // 키보드에서 검색버튼이 눌린 순간의 이벤트
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
            showToast(query);
            return false;
        }

        // 검색 글이 한자 한자 입력순간마다
        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    // 화면에 출력되는 특정 뷰와 연결되어서 뷰를 오래 누르면 보이는 메뉴
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "서버전송");
        menu.add(0, 1, 0, "보관함에 보관");
        menu.add(0, 2, 0, "삭제");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showToast("서버 전송이 선택되었습니다.");
                break;
            case 1:
                showToast("보관함에 보관이 선택되었습니다.");
                break;
            case 2:
                showToast("삭제가 선택되었습니다.");
                break;
        }
        return true;
    }

    private void showToast(String message) {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
    }
}
