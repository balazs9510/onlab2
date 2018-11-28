package hu.bme.aut.client.Preference;

import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;

public class SavedState extends Preference.BaseSavedState {
    // Standard creator object using an instance of this class
    public static final Creator<SavedState> CREATOR =
            new Creator<SavedState>() {

                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
    // Member that holds the setting's value
    // Change this data type to match the type saved by your Preference
    String value;

    public SavedState(Parcelable superState) {
        super(superState);
    }

    public SavedState(Parcel source) {
        super(source);
        // Get the current preference's value
        value = source.readString();  // Change this to read the appropriate data type
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        // Write the preference's value
        dest.writeString(value);  // Change this to write the appropriate data type
    }
}