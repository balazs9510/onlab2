package hu.bme.aut.physicexperiment.UIElement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.PreferenceFragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import hu.bme.aut.physicexperiment.R;

public class TimePreference extends DialogPreference {
    private final String DEFAULT_VALUE = "0:0:1";
    private static final String KEY_PERIOD_TIME = "pref_period_time";

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;

    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;

    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;

    }

    private int hour;
    private int min;
    private int sec;

    private NumberPicker hourPicker;
    private NumberPicker minPicker;
    private NumberPicker secPicker;

    public String getTimeString() {
        return getHour() + ":" + getMin() + ":" + getSec();
    }

    public TimePreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);
        setDialogLayoutResource(R.layout.timepicker_dialog);
        setPositiveButtonText(R.string.set);
        setNegativeButtonText(R.string.cancel);
        String storedTimeValue = this.getPersistedString(DEFAULT_VALUE);
        String[] timeValues = storedTimeValue.split(":");
        setKey(KEY_PERIOD_TIME);
        hour = Integer.parseInt(timeValues[0]);
        min = Integer.parseInt(timeValues[1]);
        sec = Integer.parseInt(timeValues[2]);
        setDialogIcon(null);
    }


    @Override
    public CharSequence getTitle() {
        return getContext().getString(R.string.setPeriod);
    }

    @Override
    public CharSequence getDialogTitle() {
        return getContext().getString(R.string.setPeriod)
                ;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        hourPicker = view.findViewById(R.id.hourpicker);
        minPicker = view.findViewById(R.id.minpicker);
        secPicker = view.findViewById(R.id.secpicker);
        setRangeOfPickers();
        setValues();
    }

    private void setRangeOfPickers() {
        secPicker.setMinValue(0);
        minPicker.setMinValue(0);
        hourPicker.setMinValue(0);
        secPicker.setMaxValue(59);
        minPicker.setMaxValue(59);
        hourPicker.setMaxValue(23);
    }

    private void setValues() {
        secPicker.setValue(getSec());
        minPicker.setValue(getMin());
        hourPicker.setValue(getHour());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            setHour(hourPicker.getValue());
            setMin(minPicker.getValue());
            setSec(secPicker.getValue());
            if (shouldPersist()) {
                persistString(getTimeString());
            }
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            String[] timeValues = this.getPersistedString(DEFAULT_VALUE).split(":");
            setTimeValues(timeValues);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        // Check whether this Preference is persistent (continually saved)
        if (isPersistent()) {
            // No need to save instance state since it's persistent,
            // use superclass state
            return superState;
        }

        // Create instance of custom BaseSavedState
        final SavedState myState = new SavedState(superState);
        // Set the state's value with the class member that holds current
        // setting value
        myState.value = getTimeString();
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // Check whether we saved the state in onSaveInstanceState
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save the state, so call superclass
            super.onRestoreInstanceState(state);
            return;
        }

        // Cast state to custom BaseSavedState and pass to superclass
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());

        // Set this Preference's widget to reflect the restored state
        setTimeValues(myState.value.split(":"));

    }

    private void setTimeValues(String[] timeValues) {
        hour = Integer.parseInt(timeValues[0]);
        min = Integer.parseInt(timeValues[1]);
        sec = Integer.parseInt(timeValues[2]);
    }
}
