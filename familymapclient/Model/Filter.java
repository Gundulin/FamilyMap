package austen.arts.familymapclient.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Filter {

    public Filter() {}

    private boolean mFemaleMarkersOn = true;
    private boolean mMaleMarkersOn = true;

    private Map<String, Boolean> mTypesOn;

    private boolean mFatherSideOn = true;
    private boolean mMotherSideOn = true;

    public boolean isFemaleMarkersOn() {
        return mFemaleMarkersOn;
    }

    public boolean isMaleMarkersOn() {
        return mMaleMarkersOn;
    }

    public Map<String, Boolean> getTypesOn() {
        return mTypesOn;
    }

    public boolean isFatherSideOn() {
        return mFatherSideOn;
    }

    public boolean isMotherSideOn() {
        return mMotherSideOn;
    }

    public void setFemaleMarkersOn(boolean femaleMarkersOn) {
        mFemaleMarkersOn = femaleMarkersOn;
    }

    public void setMaleMarkersOn(boolean maleMarkersOn) {
        mMaleMarkersOn = maleMarkersOn;
    }

    public void setTypesOn() {

        Set<String> Types = Model.getInstance().getEventTypes();
        mTypesOn = new HashMap<>();
        for (String s : Types) {
            mTypesOn.put(s, true);
        }

    }

    public void setFatherSideOn(boolean fatherSideOn) {
        mFatherSideOn = fatherSideOn;
    }

    public void setMotherSideOn(boolean motherSideOn) {
        mMotherSideOn = motherSideOn;
    }
}
