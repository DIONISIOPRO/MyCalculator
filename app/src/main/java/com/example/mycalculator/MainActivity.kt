package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvResult.text = ""
        binding.tvImput.text = ""
    }

    fun onDigit(view: View) {
        binding.tvImput.append((view as Button).text)
        lastNumeric = true
        lastDot = false
        binding.tvResult.text = ""
    }

    fun onPop(view: View) {
        var value = binding.tvImput.text
        if (value.length > 0) {
            value = value.dropLast(1)
        }
        binding.tvImput.text = value
        lastDot = false
        binding.tvResult.text = ""
    }

    fun onNegative(view: View) {
        var value = binding.tvImput.text
        if (value.startsWith("-")) {
            value = value.subSequence(1, value.lastIndex + 1)
        } else if (value.toString() != "0" && !value.startsWith("-")) {
            value = "-$value"
        }

        binding.tvImput.text = value
        lastNumeric = true
        lastDot = false
        binding.tvResult.text = ""
    }

    fun onClear(veiw: View) {
        binding.tvImput.text = ""
        lastNumeric = false
        lastDot = false
        binding.tvResult.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            binding.tvImput.append(".")
            lastDot = true
            lastNumeric = false
        }

    }

    private fun removeZerosAfterDot(result: String): String {
        var value = result
        if (value.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    fun onEqual(view: View) {
        if (lastNumeric) {

            var tvValue = binding.tvImput.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    tvValue = tvValue.substring(1)
                    prefix = "-"
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    val result = one.toDouble() - two.toDouble()

                    binding.tvResult.text = removeZerosAfterDot(result.toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    val result = one.toDouble() + two.toDouble()

                    binding.tvResult.text = removeZerosAfterDot(result.toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    val result = one.toDouble() / two.toDouble()

                    binding.tvResult.text = removeZerosAfterDot(result.toString())
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    val result = one.toDouble() * two.toDouble()

                    binding.tvResult.text = removeZerosAfterDot(result.toString())
                }


            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
        var value = binding.tvImput.text
        if (value.toString() == "-0") {
            value = "0"
            binding.tvImput.text = value
        }
    }

    fun onOperator(view: View) {

        if (lastNumeric && !lastDot && !isOpertorAdded(binding.tvImput.text.toString())) {
            binding.tvImput.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            binding.tvResult.text = ""
        }

        var value = binding.tvImput.text
        if (value.toString() == "-0") {
            value = "0"
            binding.tvImput.text = value
        }
    }

    private fun isOpertorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }

    }
}