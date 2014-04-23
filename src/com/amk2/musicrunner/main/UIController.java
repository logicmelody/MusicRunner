package com.amk2.musicrunner.main;

import com.amk2.musicrunner.R;
import com.amk2.musicrunner.discover.DiscoverFragment;
import com.amk2.musicrunner.music.MusicFragment;
import com.amk2.musicrunner.my.MyFragment;
import com.amk2.musicrunner.start.StartFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Manipulate most of UI controls in this app.
 * Note: Operate the UI as far as possible in this class, not in main activity.
 *
 * @author DannyLin
 */
public class UIController implements TabListener, ViewPager.OnPageChangeListener {

	private final MusicRunnerActivity mMainActivity;

	private FragmentManager mFragmentManager;
	private ActionBar mActionBar;
	private SwipeControllableViewPager mViewPager;
	private TabViewPagerAdapter mPagerAdapter;

	// Fragments for each tab
	private StartFragment mStartFragment;
	private MyFragment mMyFragment;
	private MusicFragment mMusicFragment;
	private DiscoverFragment mDiscoverFragment;

	public static class ViewState {
		public static final int START = 0;
		public static final int MY = 1;
		public static final int MUSIC = 2;
		public static final int DISCOVER = 3;
		public static final int VIEW_SIZE = 4;
	}

	public static class FragmentTag {
		public static final String START_FRAGMENT_TAG = "start_fragment";
		public static final String MY_FRAGMENT_TAG = "my_fragment";
		public static final String MUSIC_FRAGMENT_TAG = "music_fragment";
		public static final String DISCOVER_FRAGMENT_TAG = "discover_fragment";
	}

	public UIController(MusicRunnerActivity activity) {
		mMainActivity = activity;
		mFragmentManager = activity.getFragmentManager();
		mActionBar = activity.getActionBar();
	}

	public void onActivityCreate(Bundle savedInstanceState) {
		initialize();
	}

	private void initialize() {
		initActionBar();
		initViewPager();
		initFragments();
	}

	/**
	 * Create all fragments and add as children of the view pager.
	 * The pager adapter will only change the visibility(show/hide).
	 * It'll never create/destroy fragments.
	 *
	 * If it's after screen rotation, the fragment have been recreated by
	 * the FragmentManager. So first see if there're already the target
	 * fragment existing.
	 */
	private void initFragments() {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();

		// Init StartFragment
		mStartFragment = (StartFragment) mFragmentManager
				.findFragmentByTag(FragmentTag.START_FRAGMENT_TAG);
		if (mStartFragment == null) {
			mStartFragment = new StartFragment();
			transaction.add(R.id.tab_pager, mStartFragment,
					FragmentTag.START_FRAGMENT_TAG);
		}

		// Init MyFragment
		mMyFragment = (MyFragment) mFragmentManager
				.findFragmentByTag(FragmentTag.MY_FRAGMENT_TAG);
		if (mMyFragment == null) {
			mMyFragment = new MyFragment();
			transaction.add(R.id.tab_pager, mMyFragment,
					FragmentTag.MY_FRAGMENT_TAG);
		}

		// Init MusicFragment
		mMusicFragment = (MusicFragment) mFragmentManager
				.findFragmentByTag(FragmentTag.MUSIC_FRAGMENT_TAG);
		if (mMusicFragment == null) {
			mMusicFragment = new MusicFragment();
			transaction.add(R.id.tab_pager, mMusicFragment,
					FragmentTag.MUSIC_FRAGMENT_TAG);
		}

		// Init DiscoverFragment
		mDiscoverFragment = (DiscoverFragment) mFragmentManager
				.findFragmentByTag(FragmentTag.DISCOVER_FRAGMENT_TAG);
		if (mDiscoverFragment == null) {
			mDiscoverFragment = new DiscoverFragment();
			transaction.add(R.id.tab_pager, mDiscoverFragment,
					FragmentTag.DISCOVER_FRAGMENT_TAG);
		}

		transaction.hide(mStartFragment);
		transaction.hide(mMyFragment);
		transaction.hide(mMusicFragment);
		transaction.hide(mDiscoverFragment);
		transaction.commit();
	}

	private void initViewPager() {
		mViewPager = (SwipeControllableViewPager)mMainActivity.findViewById(R.id.tab_pager);
		mPagerAdapter = new TabViewPagerAdapter(mFragmentManager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setSwipeable(false);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initActionBar() {
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Add tabs
		mActionBar.addTab(mActionBar.newTab()
				.setText(mMainActivity.getString(R.string.start_tab))
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab()
				.setText(mMainActivity.getString(R.string.my_tab))
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab()
				.setText(mMainActivity.getString(R.string.music_tab))
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab()
				.setText(mMainActivity.getString(R.string.discover_tab))
				.setTabListener(this));
	}

	public void onActivityRestoreInstanceState(Bundle savedInstanceState) {

	}

	public void onActivityResume() {

	}

	public void onActivitySaveInstanceState(Bundle outState) {

	}

	public void onActivityPause() {

	}

	public void onActivityDestroy() {

	}

	/**
	 * PagerAdapter for ViewPager
	 *
	 * @author DannyLin
	 */
	public class TabViewPagerAdapter extends PagerAdapter {

		private FragmentManager mFm;
		private FragmentTransaction mCurTransaction = null;

		public TabViewPagerAdapter(FragmentManager fm) {
			mFm = fm;
		}

		/**
		 * Show fragment
		 * Fragments will be instantiated near the current fragment.
		 * Ex: Current = Fragment2 => Fragment1 and Fragment3 will be instantiated.
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (mCurTransaction == null) {
				mCurTransaction = mFm.beginTransaction();
			}

			Log.d("danny", "Show Fragment = " + (position+1));
			Fragment f = getFragment(position);
			mCurTransaction.show(f);

			return f;
		}

		/**
		 * We override this method to just hide the fragment instead of destroying it.
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			if (mCurTransaction == null) {
				mCurTransaction = mFm.beginTransaction();
			}
			mCurTransaction.hide((Fragment) object);
		}

		
		@Override
		public void finishUpdate(ViewGroup container) {
			if (mCurTransaction != null) {
				mCurTransaction.commit();
				mCurTransaction = null;
			}
		}

		@Override
		public int getCount() {
			return ViewState.VIEW_SIZE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return ((Fragment) object).getView() == view;
		}

	}

	private Fragment getFragment(int position) {
		switch(position) {
		case ViewState.START:
			return mStartFragment;
		case ViewState.MY:
			return mMyFragment;
		case ViewState.MUSIC:
			return mMusicFragment;
		case ViewState.DISCOVER:
			return mDiscoverFragment;
		}
		return null;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		mActionBar.setSelectedNavigationItem(position);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(mViewPager != null) {
			mViewPager.setCurrentItem(tab.getPosition(),false);
			Log.d("danny", "Set current position = " + tab.getPosition());
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
