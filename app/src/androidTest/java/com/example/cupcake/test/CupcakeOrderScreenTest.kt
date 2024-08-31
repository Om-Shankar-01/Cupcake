package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent () {
        // Given list of options
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        // Subtotal
        val subtotal = "$100"

        // When SelectOptionScreen is Loaded
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        // then all the options are displayed on the screen
        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        // and then the subtotal is displayed correctly
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                com.example.cupcake.R.string.subtotal_price,
                subtotal
            )
        ).assertIsDisplayed()

        // And then the next button is displayed
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next).assertIsNotEnabled()
    }

    @Test
    fun startOrderScreen_verifyContent () {
        // Given list of choices
        val options = listOf(
            Pair(com.example.cupcake.R.string.one_cupcake, 1),
            Pair(com.example.cupcake.R.string.six_cupcakes, 6),
            Pair(com.example.cupcake.R.string.twelve_cupcakes, 12)
        )

        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = options,
                onNextButtonClicked = {  }
            )
        }

        options.forEach { option ->
            composeTestRule.onNodeWithStringId(option.first).assertIsDisplayed()
        }
    }

    @Test
    fun summaryScreen_verifyContent () {
        val orderUiState = OrderUiState(
            quantity = 12,
            flavor = "Chocolate",
            date = "23 Jun 2024",
            price = "$100",
            pickupOptions = listOf()
        )

        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = orderUiState,
                onCancelButtonClicked = {},
                onSendButtonClicked = { subject: String, summary: String ->}
            )
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                com.example.cupcake.R.string.subtotal_price,
                orderUiState.price
            )
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.date).assertIsDisplayed()
    }
}