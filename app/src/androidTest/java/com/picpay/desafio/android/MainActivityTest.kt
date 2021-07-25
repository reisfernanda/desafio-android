package com.picpay.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.data.di.DataDI
import com.picpay.desafio.android.data.utils.CacheStrategy
import com.picpay.desafio.android.presentation.MainActivity
import com.picpay.desafio.android.presentation.UserFragment
import io.mockk.every
import io.mockk.mockkObject
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class MainActivityTest {

    private val server = MockWebServer()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val idlingResource = UserFragment.getIdlingResource()

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(idlingResource)

        unloadKoinModules(DataDI.serviceModule)
        loadKoinModules(TestDI.mockServiceModule)

        mockkObject(CacheStrategy)
        every { CacheStrategy.isCacheExpired(any()) } returns true

        server.dispatcher = MockWebServerDispatcher()
        server.start(MockWebServerDispatcher.serverPort)
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItems() {
        launchActivity<MainActivity>().apply {
            RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, 0, withText("@eduardo.santos"))
            RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, 0, withText("Eduardo Santos"))
            RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, 1, withText("@joao.silva"))
            RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, 1, withText("João Silva"))
            RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, 2, withText("@mario"))
            RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, 2, withText("Mário"))
        }
    }

    @After
    fun finish() {
        server.close()
        IdlingRegistry.getInstance().unregister(idlingResource)
        unloadKoinModules(TestDI.mockServiceModule)
    }
}