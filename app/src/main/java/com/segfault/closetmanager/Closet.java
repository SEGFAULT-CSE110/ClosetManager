package com.segfault.closetmanager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher Cabreros on 03-May-16.
 * Tempoary class to read from
 */
public class Closet {

    private List<Clothing> list_clothes = new ArrayList<Clothing>();



    /**
     * This method will return all clothing matching the preferences given
     * Used in search, filter, etc.
     * @param pref
     * @return
     */
    public List<Clothing> filter(PreferenceList pref){

        return null;

    }


    public boolean writeToDatabase(){
        return false; //returns true if written successfully.
    }

    public boolean readFromDatabase(){
        return false;//return true if read successfully
    }

    public void addClothing(Clothing val){
        list_clothes.add(val);
    }

    public void removeClothing(Clothing val){
        list_clothes.remove(val);
    }

    /**
     * Someone think about this
     */
    public void getClothing(){

    }

    public List<Clothing> getList(){
        return list_clothes;
    }

}
