package com.example.nash.s4;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.nash.s4.DataBank.Category;

import java.lang.ref.WeakReference;
import java.util.List;

// activity & fragment communication : https://www.truiton.com/2015/12/android-activity-fragment-communication/

public class TabAdapter extends FragmentStatePagerAdapter {

    private List<Category> categories;
    private SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();


    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabAdapter(FragmentManager fm, List<Category> categories) {
        super(fm);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = DynamicFragment.newInstance(position + 1);
        return fragment;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position,new WeakReference<Fragment>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Nullable
    public Fragment getFragment(int position) {
        WeakReference<Fragment> fr = instantiatedFragments.get(position);
        return fr!=null ? fr.get() : null;
    }
}
