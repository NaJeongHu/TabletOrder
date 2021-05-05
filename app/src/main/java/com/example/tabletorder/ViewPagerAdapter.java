package com.example.tabletorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();
    public static int tab_position;


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    // 프래그먼트 교체를 보여주는 처리를 구현한 곳
    @NonNull
    @Override
    public Fragment getItem(int position) {
        tab_position = position;                // 현재위치가 어느 탭인지를 알려주는 전역변수
        return lstFragment.get(position);
//        if(position >= 0)
//            return FragCommon.newinstance();
//        else
//            return null;
    }

    @Override
    public int getCount() {
        return lstTitles.size();               // 총 카테고리 개수
    }

    //CharSequence category[] = {"Ear","Apple","Fire","Nine"};            //카테고리 이름 배열
    // 상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstTitles.get(position);
//        if(position >= 0)
//            return category[position];
//        else
//            return null;
    }

    public void AddFragment (Fragment fragment, String title) {
        lstFragment.add(fragment);
        lstTitles.add(title);
    }
}