package austen.arts.familymapclient.Model;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;

import austen.arts.familymapclient.R;

public class Setting {

    private int mLifeStoryLinesColor = 0xFF008000;
    private boolean mLifeStoryLinesOn = true;
    private int mFamilyTreeLinesColor = 0xFF0000FF;
    private boolean mFamilyTreeLinesOn = true;
    private int mSpouseLinesColor = 0xFFFF0000;
    private boolean mSpouseLinesOn = true;
    private int mMapType = GoogleMap.MAP_TYPE_NORMAL;

    public Setting() {
    }

    public void setLifeStoryLinesOn(boolean lifeStoryLinesOn) {
        mLifeStoryLinesOn = lifeStoryLinesOn;
    }


    public void setFamilyTreeLinesOn(boolean familyTreeLinesOn) {
        mFamilyTreeLinesOn = familyTreeLinesOn;
    }


    public void setSpouseLinesOn(boolean spouseLinesOn) {
        mSpouseLinesOn = spouseLinesOn;
    }

    public void setMapType(int mapType) {
        mMapType = mapType;
    }

    public boolean isLifeStoryLinesOn() {
        return mLifeStoryLinesOn;
    }

    public boolean isFamilyTreeLinesOn() {
        return mFamilyTreeLinesOn;
    }

    public boolean isSpouseLinesOn() {
        return mSpouseLinesOn;
    }

    public int getMapType() {
        return mMapType;
    }

    public void setLifeStoryLinesColor(int lifeStoryLinesColor) {
        mLifeStoryLinesColor = lifeStoryLinesColor;
    }

    public void setFamilyTreeLinesColor(int familyTreeLinesColor) {
        mFamilyTreeLinesColor = familyTreeLinesColor;
    }

    public void setSpouseLinesColor(int spouseLinesColor) {
        mSpouseLinesColor = spouseLinesColor;
    }

    public int getLifeStoryLinesColor() {
        return mLifeStoryLinesColor;
    }

    public int getFamilyTreeLinesColor() {
        return mFamilyTreeLinesColor;
    }

    public int getSpouseLinesColor() {
        return mSpouseLinesColor;
    }
}
