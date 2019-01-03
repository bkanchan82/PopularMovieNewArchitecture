package movie.architech.android.com.popularmovienewarchitecture;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import movie.architech.android.com.popularmovienewarchitecture.ui.list.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityListTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);

    private CountingIdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivity.getActivity().getEspressoIdlingResourceForMainActivity();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void clickTest_LoadsMovieDetail() {
        onView(ViewMatchers.withId(R.id.rv_display_movies)).perform(RecyclerViewActions.actionOnItemAtPosition(0,
                click()));
    }

    @After
    public void unregister() {
        if(mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}
