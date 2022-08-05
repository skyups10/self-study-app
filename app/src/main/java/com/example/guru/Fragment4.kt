package com.example.guru

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.String.format
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Fragment4 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var dayText: TextView
    lateinit var timeText: TextView
    lateinit var minuteText: TextView
    lateinit var secondText: TextView
    lateinit var ddayText: TextView
    lateinit var todayText: TextView

    private var todayYear = 0
    private var todayMonth = 0
    private var todayDay = 0

    private var ddayDate: Long = 0
    private var todayDate: Long = 0
    private var result: Long = 0

    var dday: Long = 0
    var today: Long = 0

    private val ONE_DAY = 24 * 60 * 60 * 1000
    private lateinit var mCalendar: Calendar
    private lateinit var mTvResult: TextView

    lateinit var mDateSetListener: OnDateSetListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_4, container, false)

        Locale.setDefault(Locale.KOREAN)

        mDateSetListener =
            OnDateSetListener() { datePicker: DatePicker, year: Int, month: Int, day: Int ->

                mTvResult.setText(getDday(year, month, day))

                result = (dday - today) + 1

                ddayText.text = format(" %d년 %d월 %d일까지 ", year, month + 1, day).toString()
                dayText.text = String.format(" %d/%d일까지 ", month + 1, day)
                timeText.text = String.format("> %d시간", (result - 1) * 24)
                minuteText.text = String.format("> %d분", (result - 1) * 24 * 60)
                secondText.text = String.format("> %d초 남았습니다", (result - 1) * 24 * 60 * 60)
            }

        mCalendar = GregorianCalendar.getInstance()
        mTvResult = view.findViewById<TextView>(R.id.result)

        val btn = view.findViewById<Button>(R.id.dateButton)
        btn.setOnClickListener {
            val year = mCalendar.get(Calendar.YEAR)
            val month = mCalendar.get(Calendar.MONTH)
            val day = mCalendar.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(this.requireContext(), mDateSetListener, year, month, day)
            dialog.show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todayText = view.findViewById(R.id.today) as TextView
        ddayText = view.findViewById(R.id.dday) as TextView

        dayText = view.findViewById(R.id.day) as TextView
        timeText = view.findViewById(R.id.time) as TextView
        minuteText = view.findViewById(R.id.minute) as TextView
        secondText = view.findViewById(R.id.second) as TextView

        val calendar = Calendar.getInstance()
        todayYear = calendar[Calendar.YEAR]
        todayMonth = calendar[Calendar.MONTH]
        todayDay = calendar[Calendar.DAY_OF_MONTH]

        updateDisplay()
    }

    private fun updateDisplay() {
        todayText.text = String.format("%d / %d / %d", todayYear, todayMonth + 1, todayDay)
    }

    fun getDday(a_year: Int, a_monthOfYear: Int, a_dayOfMonth: Int): String {
        val ddayCalendar = Calendar.getInstance()
        ddayCalendar.set(a_year, a_monthOfYear, a_dayOfMonth)

        dday = ddayCalendar.timeInMillis / ONE_DAY
        today = Calendar.getInstance().timeInMillis / ONE_DAY
        result = dday - today

        var strFormat = ""
        if (result > 0) {
            strFormat = "D-%d"
        } else if (result == 0L) {
            strFormat = "D-day"
        } else {
            result *= -1
            strFormat = "D+%d"
        }

        val strCount: String = String.format(strFormat, result)
        return strCount
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}