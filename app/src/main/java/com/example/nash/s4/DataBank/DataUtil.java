package com.example.nash.s4.DataBank;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class DataUtil {

    private static MasterData masterData;
    private Context context;
    private static boolean ada;
    private int itemIndex = 999;

    public DataUtil() {
    }

    public DataUtil(Context context) {
        this.context = context;
    }

    public DataUtil(MasterData masterData) {
        this.masterData = masterData;
    }


    public MasterData getMasterData() {
        return masterData;
    }

    public void setMasterData(MasterData masterData) {
        this.masterData = masterData;
    }

    public void addValue(int catIndex, int itemIndex, List<String> value) {
        getMasterData().getCategories().get(catIndex).getItems().get(itemIndex).setValue(value);
    }

    public void updateOnePage(NewData newData) {
//        int catIndex = newData.getCategoryIndex();
        String catName = newData.getCategoryName();

        List<Category> categoryList = masterData.getCategories();
        List<Item> itemListLuar = newData.getItems();

        // 2nd choice : name as id
        for (int c=0; c<categoryList.size(); c++) {
            int catIndexDalam;
            String namaDalam = categoryList.get(c).getName();

            Log.d("tagLine", "1st loop");


            if (namaDalam.equals(catName)) {
                catIndexDalam = c;
                List<Item> itemListDalam = categoryList.get(catIndexDalam).getItems();

                Log.d("tagLine", "sama nama category");

                if (itemListDalam!=null) {
                    if (itemListDalam.size()>=itemListLuar.size()) {
                        for (int i = 0; i < itemListDalam.size(); i++) {
                            for (int j = 0; j < itemListLuar.size(); j++) {
                                Log.d("tagLine", "double loop");
                                Item itemBaru = itemListLuar.get(j);

                                if (i == j) {
                                    itemListDalam.set(i, itemBaru);
                                }
                            }
                            if (i > itemListLuar.size()-1) {
                                itemListDalam.remove(i);
                            }
                        }
                    }else {
                        for (int k = 0; k < itemListLuar.size(); k++) {
                            Item itemBaru = itemListLuar.get(k);
                            for (int l = 0; l < itemListDalam.size(); l++) {
                                Log.d("tagLine", "double loop");

                                if (k == l) {
                                    itemListDalam.set(k, itemBaru);
                                }

                            }
                            if (k > itemListDalam.size()-1) {
                                    itemListDalam.add(itemBaru);
                                }
                            Log.d("tagLine", "1st double loop");
                        }

                    }

                }else {
                    Category category = categoryList.get(catIndexDalam);
                    category.setItems(itemListLuar);
                }
            }
        }


    }

    public boolean adeKe(){
        return ada;
    }

    public int getItemIndex() {
        return itemIndex;
    }
}
