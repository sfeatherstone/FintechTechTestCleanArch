package com.sfeatherstone.propertyPriceAverage.ui


import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertTextColorIs
import com.sfeatherstone.propertyPriceAverage.R
import com.sfeatherstone.propertyPriceAverage.model.PropertyPriceAverage
import com.sfeatherstone.propertyPriceAverage.model.PropertyPriceAverageState
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.lang.RuntimeException


@RunWith(AndroidJUnit4::class)
class MainActivityTest: KoinTest {

    @Rule @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var viewModelMock: PriceAverageViewModel

    private val propertyPriceAverageState = MutableLiveData<PropertyPriceAverageState>()

    @Before
    fun setUp() {
        // Mock our View Model to stub out calls later
        viewModelMock = mock()
        whenever(viewModelMock.propertyPriceAverageState).thenReturn(propertyPriceAverageState)

        val mockModule = module {
            viewModel<PriceAverageViewModel> {
                viewModelMock
            }
        }

        // Stub out so we have control over LiveData's value
        startKoin {
            modules(mockModule)
        }

    }


    @After
    fun cleanUp() {
        stopKoin()
    }


    @Test
    fun `check_question_text_and_colours_are_as_specified`() {
        activityRule.launchActivity(Intent());
        assertDisplayed(R.id.question,  "What is the average property price?")
        assertTextColorIs(R.id.question,  R.color.colorBerry)
    }

    @Test
    fun check_answer_text_and_colours_are_as_specified() {
        activityRule.launchActivity(Intent());
        assertDisplayed(R.id.averagePrice,  "")
        assertTextColorIs(R.id.averagePrice,  R.color.colorBerry)
        propertyPriceAverageState.postValue(PropertyPriceAverageState.Loading)
        assertDisplayed(R.id.averagePrice,  "Loading")
        propertyPriceAverageState.postValue(PropertyPriceAverageState.Error(RuntimeException()))
        assertDisplayed(R.id.averagePrice,  "Error")
        propertyPriceAverageState.postValue(PropertyPriceAverageState.Success(PropertyPriceAverage(0,0)))
        assertDisplayed(R.id.averagePrice,  "No Properties found")
        propertyPriceAverageState.postValue(PropertyPriceAverageState.Success(PropertyPriceAverage(10000,2)))
        assertDisplayed(R.id.averagePrice,  "10000")
    }

}