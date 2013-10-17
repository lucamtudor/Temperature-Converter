
package com.tudorluca.temperatureconverter.test;

import static org.fest.assertions.api.ANDROID.assertThat;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.tudorluca.temperatureconverter.R;
import com.tudorluca.temperatureconverter.TemperatureActivity;
import com.tudorluca.temperatureconverter.TemperatureFragment;

public class TemperatureConverterTests extends ActivityInstrumentationTestCase2<TemperatureActivity> {

    private TemperatureActivity mActivity;
    private TemperatureFragment mFragment;

    private EditText mCelsius;
    private EditText mFahrenheit;

    public TemperatureConverterTests() {
        super(TemperatureActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        assertThat(mActivity).isNotNull();

        mFragment = (TemperatureFragment)waitForFragment(TemperatureFragment.TAG, 3000);
        assertThat(mFragment).isNotNull();

        mCelsius = (EditText)mFragment.getView().findViewById(R.id.celsius);
        assertThat(mCelsius).isNotNull();

        mFahrenheit = (EditText)mFragment.getView().findViewById(R.id.fahrenheit);
        assertThat(mFahrenheit).isNotNull();
    }

    @SmallTest
    public void testFieldsOnScreen() {
        final View origin = mActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(origin, mCelsius);
        ViewAsserts.assertOnScreen(origin, mFahrenheit);
    }

    @SmallTest
    public void testFieldsAlignment() {
        ViewAsserts.assertLeftAligned(mCelsius, mFahrenheit);
        ViewAsserts.assertRightAligned(mCelsius, mFahrenheit);
    }

    @SmallTest
    public void testFiledsShouldStartEmpty() {
        assertThat(mCelsius).hasTextString("");
        assertThat(mFahrenheit).hasTextString("");
    }

    @SmallTest
    public void testFieldsTextJustification() {
        assertThat(mCelsius).hasGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        assertThat(mFahrenheit).hasGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    }

    @SmallTest
    public void testFieldsHintText() {
        assertThat(mCelsius).hasHint("Celsius");
        assertThat(mFahrenheit).hasHint("Fahrenheit");
    }

    public Fragment waitForFragment(String tag, int timeout) {
        long endTime = SystemClock.uptimeMillis() + timeout;
        while (SystemClock.uptimeMillis() <= endTime) {
            Fragment fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null) {
                return fragment;
            }
        }
        return null;
    }

}
