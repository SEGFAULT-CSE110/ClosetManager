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
        if(shirt == null){
            return null;
        }

		/* Pants should match the shirt */
        PreferenceList pantsPref = new PreferenceList(shirt);
        pantsPref = new PreferenceList(pantsPref, cat, Clothing.BOTTOM);

        /* Get pants */
        pants = pickOne(pantsPref);

		/* If no pants then no outfit will be produced */
        if( pants == null){
            return null;
        }

        /* Get shoes */
        PreferenceList shoesPref = new PreferenceList(shirtPref,
                cat, Clothing.SHOE);
        shoes = pickOne(shoesPref);

		/* If no shoes then no outfit will be produced */
        if( shoes == null){
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

    private List<String> colorMatches (String color){
        switch (color){
            case "red":
                //blue, black
                break;
            case "green":
                // blue, black, white, grey
                break;
            case "blue":
                // yellow, black, white, grey, purple, brown, pink, red, green
                break;
            case "yellow":
                //white, grey
                break;
            case "black":
                //everything
                break;
            case "white":
                //green, blue, yellow, black, grey, brown, pink
                break;
            case "grey":
                //green, blue, black, white
                break;
            case "purple":
                //blue, black
                break;
            case "orange":
                //black,
                break;
            case "brown":
                //blue, black, white
                break;
            case "pink":
                //blue, black, white
                break;
            default:
                break;
        }

        //TODO: Baowen plz replace this
        return new ArrayList<String>();

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
        if(match == null){
            match = mBelongingCloset.filter(second);
        }

		/* Next filter */
        if(match == null){
			/* If color is set, then consider color first */

            PreferenceList third = new PreferenceList(second,
                    attriWeather, null);
            /* Delete lowest priority weather field */
            if(weather != null && !weather.isEmpty()){
                    match = mBelongingCloset.filter(third);
            }

            /* Worn */
            if(match == null){
                    PreferenceList fourth = new PreferenceList(third, attriWorn, true);
                }
            /* Delete occasion field */


			/* If color is not set, occasion is primary and weather follows */
            if(color != null && !color.isEmpty()){

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

        //accesories
        if(random.nextInt(4)==0){
            PreferenceList accessoryPref = new PreferenceList
                    (false, Clothing.ACCESSORY, null, null, null, null, null, null);
            List<Clothing> accessoryList = mBelongingCloset.filter(accessoryPref);
            if(!accessoryList.isEmpty()) { //nullptr check
                accessory = accessoryList.get(random.nextInt(accessoryList.size()));
                result.addAccessory(accessory);
            }
        }

        //top
        PreferenceList topPref = new PreferenceList
                (false, Clothing.TOP, null, null, null, null, null, null);
        List<Clothing> topList = mBelongingCloset.filter(topPref);
        if (!topList.isEmpty()) { //nullptr check
            shirt = topList.get(random.nextInt(topList.size()));
            result.addTop(shirt);
        }

        //bottom
        PreferenceList pantsPref = new PreferenceList
                (false, Clothing.BOTTOM, null, null, null, null, null, null);;
        List<Clothing> bottomList = mBelongingCloset.filter(pantsPref);
        if (!bottomList.isEmpty()) {
            pants = bottomList.get(random.nextInt(bottomList.size()));
            result.addBottom(pants);
        }

        //shoes
        PreferenceList shoesPref = new PreferenceList
                (false, Clothing.SHOE, null, null, null, null, null, null);;
        List<Clothing> shoesList = mBelongingCloset.filter(shoesPref);
        if (!shoesList.isEmpty()) {
            shoes = shoesList.get(random.nextInt(shoesList.size()));
            result.setShoes(shoes);
        }

        //hat
        if(random.nextInt(4)==0){
            PreferenceList hatPref = new PreferenceList
                    (false, Clothing.HAT, null, null, null, null, null, null);;
            List<Clothing> hatList = mBelongingCloset.filter(hatPref);
            if (!hatList.isEmpty()){
                hat = hatList.get(random.nextInt(hatList.size()));
                result.setHat(hat);
            }
        }

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