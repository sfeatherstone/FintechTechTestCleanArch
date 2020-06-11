package com.sfeatherstone.propertyPriceAverage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.sfeatherstone.propertyPriceAverage.R
import com.sfeatherstone.propertyPriceAverage.model.PropertyPriceAverageState
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: PriceAverageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.propertyPriceAverageState.observe(this, Observer{ onPropertyPriceAverage(it) })
        viewModel.getPropertyPriceAverage()
    }

    private fun onPropertyPriceAverage(propertyPriceAverageState : PropertyPriceAverageState) {
        when(propertyPriceAverageState) {
            is PropertyPriceAverageState.Loading -> averagePrice.text = getString(R.string.main_activity_loading)
            is PropertyPriceAverageState.Error -> averagePrice.text = getString(R.string.main_activity_error)
            is PropertyPriceAverageState.Success -> {
                averagePrice.text = if (propertyPriceAverageState.data.propertiesFound == 0) {
                    getString(R.string.main_activity_no_properties_error)
                } else {
                    "${propertyPriceAverageState.data.averagePrice}"
                }
            }
        }
    }

}
