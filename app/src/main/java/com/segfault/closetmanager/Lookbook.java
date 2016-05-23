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
        Clothing acce = null;
        Clothing hat = null;

        String cat = "category";

        Outfit result = null;

        // PrefList with top
        PreferenceList shirtPref = new PreferenceList(
                preferenceList, cat, Clothing.TOP);

        /* Get a shirt */
        shirt = pickOne(shirtPref);

		/* If no shirt then no outfit will be produced */
        if(!shirt){
            return null;
        }

		/* Pants should match the shirt */
        PreferenceList pantsPref = new PreferenceList(shirt);
        pantsPref = new PreferenceList(pantsPref, cat, Clothing.BOTTOM);

        /* Get pants */
        pants = pickOne(pantsPref);

		/* If no pants then no outfit will be produced */
        if(!pants){
            return null;
        }

        /* Get shoes */
        PreferenceList shoesPref = new PreferenceList(shirtPref,
                cat, Clothing.SHOE);
        shoes = pickOne(shoesPref);

		/* If no shoes then no outfit will be produced */
        if(!shoes){
            return null;
        }

		/* 20% chance there will be an hat */
        Random randHat = new Random();
        int iHat = randHat.nextInt(20);
        if(iHat == 0){
            PreferenceList hatPref = new PreferenceList(shirt);
            hatPref = new PreferenceList(hatPref, cat, Clothing.HAT);
            hat = pickOne(hatPref);
        }

        /* Construct the outfit */
        result.addTop(shirt);
        result.addBottom(pants);
        result.setShoes(shoes);
        result.setHat(hat);

        return result;
    }

    /*
     * Pick one clothing article given a preference list by calling filter 
	 * multiple times.
	 * Current fields used in filter: Color, SecondaryColor, Occasion, Weather,
	 * Worn.
     * @param PreferenceList
     * @return Clothing
     */
    private Clothing pickOne(PreferenceList prefList){

        List<Clothing> match = null;

		/* Local for all fields in prefList*/
        boolean worn = prefList.isWorn();
        String category = prefList.getCategory();
        String color = prefList.getColor();
        String secColor = prefList.getSecondaryColor();
        String size = prefList.getSize();
        List<String> occasion = prefList.getOccasion();
        String style = prefList.getStyle();
        String weather = prefList.getWeather();

        String attriWorn = "worn";
        String attriWeather = "weather";

        /* Filter for the perfect list */
        match = mBelongingCloset.filter(prefList);
        PreferenceList second = new PreferenceList( prefList, attriWorn, true);

        /* Find second best */
        if(match == NULL){
            match = mBelongingCloset.filter(second);
        }

		/* Next filter */
        if(match == NULL){
			/* If color is set, then consider color first */
            if(color != NULL && !color.isEmpty()){

				/* Delete lowest priority weather field */
                if(weather != NULL && !weather.isEmpty()){
                    PreferenceList third = new PreferenceList(second,
                            attriWeather, null);
                    match = mBelongingCloset.filter(third);
                }

				/* Worn */
                if(match == NULL){

                }

				/* Delete occasion field */
            }

			/* If color is not set, occasion is primary and weather follows */
            else{

            }
        }
		
		/* If still nothing is found, then pick fails */
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
        Clothing hat = null;

        Outfit result = new Outfit();

        //todo: prefList with only category as accessory
        if(random.nextInt(4)==0){
            PreferenceList accessoryPref = new PreferenceList
                    (false, Clothing.ACCESSORY, null, null, null, null, null, null);
            List<Clothing> accessoryList = mBelongingCloset.filter
                    (accessoryPref);
            accessory = accessoryList.get(random.nextInt(accessoryList.size()));
        }

        //todo: prefList with only category as top
        PreferenceList topPref = new PreferenceList
                (false, Clothing.TOP, null, null, null, null, null, null);
        List<Clothing> top = mBelongingCloset.filter(topPref);
        shirt = top.get(random.nextInt(top.size()));

        //todo: prefList with only category as pants
        PreferenceList pantsPref = new PreferenceList
                (false, Clothing.BOTTOM, null, null, null, null, null, null);;
        List<Clothing> bottom = mBelongingCloset.filter(pantsPref);
        pants = bottom.get(random.nextInt(bottom.size()));

        //todo: prefList with only category as shoes
        PreferenceList shoesPref = new PreferenceList
                (false, Clothing.SHOE, null, null, null, null, null, null);;
        List<Clothing> shoesL = mBelongingCloset.filter(shoesPref);
        shoes = shoesL.get(random.nextInt(shoesL.size()));

        //todo: prefList with only category as hat
        if(random.nextInt(4)==0){
            PreferenceList hatPref = new PreferenceList
                    (false, Clothing.HAT, null, null, null, null, null, null);;
            List<Clothing> hats = mBelongingCloset.filter(hatPref);
            hat = hats.get(random.nextInt(hats.size()));
        }

        //todo: construct the outfit result with selected clothing
        result.addAccessory(accessory);
        result.addTop(shirt);
        result.addBottom(pants);
        result.setShoes(shoes);
        result.setHat(hat);

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