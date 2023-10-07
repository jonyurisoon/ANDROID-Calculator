package com.soon.calculator.prelim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.soon.calculator.prelim.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    private var currentInput = ""
    private var previousInput = ""
    private var currentOperator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonClickListeners()

    }

    private fun setButtonClickListeners() {
        val numberButtons = arrayOf(
            binding.button0, binding.button1, binding.button2,
            binding.button3, binding.button4, binding.button5,
            binding.button6, binding.button7, binding.button8, binding.button9
        )

        for (button in numberButtons) {
            button.setOnClickListener {
                handleNumberButtonClick(button.text.toString())
            }
        }

        binding.buttonClear.setOnClickListener {
            clearInput()
        }
        binding.buttonAddition.setOnClickListener {
            handleOperatorButtonClick("+")
        }

        binding.buttonSubtraction.setOnClickListener {
            handleOperatorButtonClick("-")
        }

        binding.buttonMultiply.setOnClickListener {
            handleOperatorButtonClick("*")
        }

        binding.buttonDivision.setOnClickListener {
            handleOperatorButtonClick("/")
        }

        binding.buttonDot.setOnClickListener {
            handleDotButtonClick()
        }

        binding.buttonEqual.setOnClickListener {
            calculateResult()
        }

        binding.buttonPercentage.setOnClickListener {
            handlePercentageButtonClick()
        }

        binding.buttonPositiveNegative.setOnClickListener {
            handlePositiveNegativeButtonClick()
        }
    }
    private fun handleNumberButtonClick(number: String) {
        if (currentInput == "0" || currentInput == "-0") {
            currentInput = number
        } else {
            currentInput += number
        }
        updateResultText(currentInput)
    }

    private fun handleOperatorButtonClick(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (previousInput.isNotEmpty() && currentOperator.isNotEmpty()) {
                calculateResult()
            }
            currentOperator = operator
            previousInput = currentInput
            currentInput = ""
        }
    }


    private fun handleDotButtonClick() {
        if (!currentInput.contains(".")) {
            currentInput += "."
        }
        updateResultText(currentInput)
    }

    private fun clearInput() {
        currentInput = ""
        previousInput = ""
        currentOperator = ""
        updateResultText("0")
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && previousInput.isNotEmpty() && currentOperator.isNotEmpty()) {
            val num1 = previousInput.toDouble()
            val num2 = currentInput.toDouble()
            val result = when (currentOperator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> num1 / num2
                else -> 0.0
            }
            currentInput = if (result % 1 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }

            previousInput = ""
            currentOperator = ""
            updateResultText(currentInput)
        }
    }

    private fun handlePercentageButtonClick() {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDouble() / 100
            currentInput = value.toString()
            updateResultText(currentInput)
        }
    }

    private fun handlePositiveNegativeButtonClick() {
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            updateResultText(currentInput)
        }
    }

    private fun updateResultText(text: String) {
        binding.Result.text = Editable.Factory.getInstance().newEditable(text)
    }
}
