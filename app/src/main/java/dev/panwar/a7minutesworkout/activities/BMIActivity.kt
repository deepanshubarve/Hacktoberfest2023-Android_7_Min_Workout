package dev.panwar.a7minutesworkout.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.panwar.a7minutesworkout.R
import dev.panwar.a7minutesworkout.databinding.ActivityBmiBinding
import dev.panwar.a7minutesworkout.utils.gone
import dev.panwar.a7minutesworkout.utils.invisible
import dev.panwar.a7minutesworkout.utils.visible
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    // TODO(Step 2 : Added variables for METRIC and US UNITS views and a variable for displaying the current selected view..)
    // START defined the constant values
    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }

    // at starting we have metric view as current visible view
    private var currentVisibleView: String =
        METRIC_UNITS_VIEW // A variable to hold a value to make a selected view visible

    // END
    // create binding for the activity
    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate the layout
        binding = ActivityBmiBinding.inflate(layoutInflater)
        //connect the layout to this activity
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.goBack.setOnClickListener { finish() }

        // TODO(Step 5 : When the activity is launched make METRIC UNITS VIEW visible.)
        makeVisibleMetricUnitsView()
        // TODO(Step 6 : Adding a check change listener to the radio group and according to the radio button.)
        // Radio Group change listener is set to the radio group which is added in XML.
        //we use _ for the first value because we don't need it
        binding.rgUnits.setOnCheckedChangeListener { _, checkedId: Int ->

            // Here if the checkId is METRIC UNITS view then make the view visible else US UNITS view.
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }
        // Button will calculate the input values in Metric Units
        binding.btnCalculateUnits.setOnClickListener {
            calculateUnits()
        }
    }

    private fun calculateUnits() {
        //TODO(Step 2 : Handling the current visible view and calculating US UNITS view input values if they are valid.)
        // START
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            // The values are validated.
            if (validateMetricUnits()) {

                // The height value is converted to float value and divided by 100 to convert it to meter.
                val heightValue: Float =
                    binding.etMetricUnitHeight.text.toString().toFloat() / 100

                // The weight value is converted to float value
                val weightValue: Float = binding.etMetricUnitWeight.text.toString().toFloat()

                // BMI value is calculated in METRIC UNITS using the height and weight value.
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {

            // The values are validated.
            if (validateUsUnits()) {

                val usUnitHeightValueFeet: String =
                    binding.etUsMetricUnitHeightFeet.text.toString() // Height Feet value entered in EditText component.
                val usUnitHeightValueInch: String =
                    binding.etUsMetricUnitHeightInch.text.toString() // Height Inch value entered in EditText component.
                val usUnitWeightValue: Float = binding.etUsMetricUnitWeight.text.toString()
                    .toFloat() // Weight value entered in EditText component.

                // Here the Height Feet and Inch values are merged and multiplied by 12 for converting it to inches.
                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                // This is the Formula for US UNITS result.
                // Reference Link : https://www.cdc.gov/healthyweight/assessing/bmi/childrens_bmi/childrens_bmi_formula.html
                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi) // Displaying the result into UI
            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }


    // TODO(Step 3 : Making a function to make the METRIC UNITS view visible.)
    // START
    /**
     * Function is used to make the METRIC UNITS VIEW visible and hide the US UNITS VIEW.
     */
    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW // Current View is updated here.
        binding.tilMetricUnitWeight.visible() // METRIC  Height UNITS VIEW is Visible
        binding.tilMetricUnitHeight.visible() // METRIC  Weight UNITS VIEW is Visible
        binding.tilUsMetricUnitWeight.gone() // make weight view Gone.
        binding.tilMetricUsUnitHeightFeet.gone() // make height feet view Gone.
        binding.tilMetricUsUnitHeightInch.gone() // make height inch view Gone.

        binding.etMetricUnitHeight.text!!.clear() // height value is cleared if it is added.
        binding.etMetricUnitWeight.text!!.clear() // weight value is cleared if it is added.

        binding.llDiplayBMIResult.invisible()
    }
    // END

    // TODO(Step 4 : Making a function to make the US UNITS view visible.)
    // START
    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        binding.tilMetricUnitHeight.invisible() // METRIC  Height UNITS VIEW is InVisible
        binding.tilMetricUnitWeight.invisible() // METRIC Weight UNITS VIEW is InVisible
        binding.tilUsMetricUnitWeight.visible() // make weight view visible.
        binding.tilMetricUsUnitHeightFeet.visible() // make height feet view visible.
        binding.tilMetricUsUnitHeightInch.visible() // make height inch view visible.

        binding.etUsMetricUnitWeight.text!!.clear() // weight value is cleared.
        binding.etUsMetricUnitHeightFeet.text!!.clear() // height feet value is cleared.
        binding.etUsMetricUnitHeightInch.text!!.clear() // height inch is cleared.

        binding.llDiplayBMIResult.invisible()
    }

    /**
     * Function is used to validate the input values for METRIC UNITS.
     */
    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
    // END


    /**
     * Function is used to validate the input values for US UNITS.
     */
    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding.etUsMetricUnitWeight.text.toString().isEmpty() -> {
                isValid = false
            }

            binding.etUsMetricUnitHeightFeet.text.toString().isEmpty() -> {
                isValid = false
            }

            binding.etUsMetricUnitHeightInch.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }


    /**
     * Function is used to display the result of METRIC UNITS.
     */
    private fun displayBMIResult(bmi: Float) {
        binding.ivScale.visible()
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        binding.llDiplayBMIResult.visible()

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBMIValue.text = bmiValue // Value is set to TextView
        binding.tvBMIType.text = bmiLabel // Label is set to TextView
        binding.tvBMIDescription.text = bmiDescription // Description is set to TextView
    }
    // END
}
