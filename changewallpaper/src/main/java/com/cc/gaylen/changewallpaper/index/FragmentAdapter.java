package com.cc.gaylen.changewallpaper.index;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cc.gaylen.changewallpaper.ui.FifthFragment;
import com.cc.gaylen.changewallpaper.ui.FirstFragment;
import com.cc.gaylen.changewallpaper.ui.FourthFragment;
import com.cc.gaylen.changewallpaper.ui.SecondFragment;
import com.cc.gaylen.changewallpaper.ui.ThirdFragment;


public class FragmentAdapter extends FragmentPagerAdapter {

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public final static int TAB_COUNT = 5;

	@Override
	public Fragment getItem(int id) {
		switch (id) {
		case MainActivity.TAB_First:
			FirstFragment firstfragment = new FirstFragment();
			return firstfragment;
		case MainActivity.TAB_Second:
			SecondFragment secondfragment = new SecondFragment();
			return secondfragment;
		case MainActivity.TAB_Third:
			ThirdFragment thirdfragment = new ThirdFragment();
			return thirdfragment;
		case MainActivity.TAB_Fourth:
			FourthFragment fourthfragment = new FourthFragment();
			return fourthfragment;
		case MainActivity.TAB_Fifth:
			FifthFragment fifthfragment = new FifthFragment();
			return fifthfragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}
}
