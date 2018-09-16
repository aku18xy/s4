package com.example.nash.s4;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nash.s4.DataBank.Category;
import com.example.nash.s4.DataBank.ConnectionData;
import com.example.nash.s4.DataBank.DataUtil;
import com.example.nash.s4.DataBank.Item;
import com.example.nash.s4.DataBank.MasterData;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends Fragment {

    public static String ARG_PAGE = "arg_page";
    public static String ARG_LOCK = "arg_lock";
    int mPage, mLock;
    ItemAdapter adapter;
    private DataUtil dataUtil;
    private String connectInfo = null;

    public static DynamicFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DynamicFragment fragment = new DynamicFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public DynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dynamic, container, false);

        RecyclerView recycle = v.findViewById(R.id.recycle);
        dataUtil = new DataUtil(getActivity());
        MasterData masterData = dataUtil.getMasterData();
        List<Category> categoryList = masterData.getCategories();
        List<Item> itemList = categoryList.get(mPage-1).getItems();

        adapter = new ItemAdapter(itemList, getActivity());
        recycle.setAdapter(adapter);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    public void onRefresh(int catIndex) {
        if (mPage==catIndex+1) {

            boolean ada = dataUtil.adeKe();
            int itemIndex = dataUtil.getItemIndex();
            Log.d("s3", "adeke=" + String.valueOf(ada));

            List<Item> itemList = dataUtil.getMasterData().getCategories()
                    .get(catIndex).getItems();
            adapter.updateItemAdapter(itemList);
            adapter.notifyDataSetChanged();

            if (ada)
                adapter.notifyItemChanged(itemIndex);



        }
    }

}
