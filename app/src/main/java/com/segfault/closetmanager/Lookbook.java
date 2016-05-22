package com.segfault.closetmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class Lookbook {

    private List<Outfit> mOutfitList;
    private Closet mBelongingCloset;

    public Lookbook(){
        mOutfitList = new ArrayList<>();
    }

    public void assignBelongingCloset(Closet closet){
        mBelongingCloset = closet;
    }

    public boolean writeToDatabase(){
        return false; //returns true if written successfully.
    }

    public boolean readFromDatabase(){
        return false;//return true if read successfully
    }

    /*
     * Takes in the PreferenceList and chooses an outfit accordingly.
     * @param preferenceList
     * @return Outfit
     */
    public Outfit generateOutfit(PreferenceList preferenceList){

        Clothing shirt = null;
        Clothing pants = null;
        Clothing shoes = null;

        Outfit result = null;

        //todo:need constructor to set the fields with the input preference + Category:top
        PreferenceList shirtPref = null;

        /* Get a shirt */
        shirt = pickOne(shirtPref);

        //todo:this will be the pants to match the shirt, get a prefList with shirts' attributes
        PreferenceList pantsPref = null;

        /* Get pants */
        pants = pickOne(pantsPref);

        //todo: a prefList for shoes with given pants'( or shirts' ?) attributes
        PreferenceList shoesPref = null;

        /* Get shoes */
        shoes = pickOne(shoesPref);

        //todo: use outfit constructor to get result outfit
        /* Construct the outfit */


        return result;
    }

    /*
     * Pick one clothing article given a preference list by calling filter multiple times.
     * @param PreferenceList
     * @return Clothing
     */
    private Clothing pickOne(PreferenceList prefList){

        //todo: assuming filter only returns List of clothing with exact match, and returns null if finds none

        List<Clothing> match = null;

        /* Filter for the perfect list */
        match = filter(prefList);

        /* Find second best */
        //todo: preferenceList needs a method to null the field with lowest priority, and then
        //todo: a) reduce preference list fields one by one until a not-null list is found(1234,123,12,1)
        //todo: b) 1234, 123, 124, 12, 134, 13, 1 (1 is highest priority)
        if(false){ // supposed to be !match
            //todo: the result List will be in match

        }

        /* Randomly choose one from the list */
        Random random = new Random();
        int index = random.nextInt(match.size());

        return match.get(index);
    }

    /*
     * Randomly pick a clothing from each category.
     * @preturn Outfit
     */
    public Outfit generateRandomOutfit(){

        Random random = new Random();

        Clothing accessory = null;
        Clothing shirt = null;
        Clothing pants = null;
        Clothing shoes = null;

        Outfit result = new Outfit();

        //todo: prefList with only category as accessory
        PreferenceList accessoryPref = new PreferenceList(false, Clothing.ACCESSORY, null, null, null, null, null);
        List<Clothing> accessoryList = mBelongingCloset.filter(accessoryPref);
        accessory = accessoryList.get(random.nextInt(accessoryList.size()));

        //todo: prefList with only category as top
        PreferenceList topPref = new PreferenceList(false, Clothing.TOP, null, null, null, null, null);
        List<Clothing> top = mBelongingCloset.filter(topPref);
        shirt = top.get(random.nextInt(top.size()));

        //todo: prefList with only category as pants
        PreferenceList pantsPref = new PreferenceList(false, Clothing.BOTTOM, null, null, null, null, null);;
        List<Clothing> bottom = mBelongingCloset.filter(pantsPref);
        pants = bottom.get(random.nextInt(bottom.size()));

        //todo: prefList with only category as shoes
        PreferenceList shoesPref = new PreferenceList(false, Clothing.SHOE, null, null, null, null, null);;
        List<Clothing> shoesL = mBelongingCloset.filter(shoesPref);
        shoes = shoesL.get(random.nextInt(shoesL.size()));

        //todo: construct the outfit result with selected clothing
        result.addAccessory(accessory);
        result.addTop(shirt);
        result.addBottom(pants);
        result.setShoes(shoes);

        return result;
    }

    private List<Clothing> filter(PreferenceList topPref) {
        return new ArrayList<>();
    }

    public List<Outfit> getOutfitList() {
        return mOutfitList;
    }

    public void addOutfit(Outfit out){
        mOutfitList.add(out);
    }

    public void removeOutfit(Outfit out){
        mOutfitList.remove(out);
    }
}
