package com.example.album

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.album.ui.albumdetail.AlbumDetailFragment
import com.example.album.ui.albums.AlbumListFragment
import com.example.album.ui.albums.AlbumViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@ExperimentalCoroutinesApi
class AlbumListFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun shouldDisplayAlbumListFragment() {
        launchFragmentInHiltContainer<AlbumListFragment>()

        onView(withId(R.id.recyclerViewAlbums)).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldDisplayAlbumListDetailFragment() {
        launchFragmentInHiltContainer<AlbumDetailFragment>()

        onView(withId(R.id.rootViewAlbumDetail)).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldAbleToScrollAndClickTheAlbumList() {
        launchFragmentInHiltContainer<AlbumListFragment>()

        scrollAndClickRecyclerViewItem(R.id.recyclerViewAlbums)
    }

    private fun scrollAndClickRecyclerViewItem(@IdRes recyclerViewId: Int, position: Int = 4) {
        onView(withId(recyclerViewId))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<AlbumViewHolder>(position),
                ViewActions.click()
            )

    }

    //TODO: As future improvements, incorporate fake view model with data to test the recyclerview text view items
}