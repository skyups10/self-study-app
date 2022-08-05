package com.example.guru

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var datePicker //  datePicker - 날짜를 선택하는 달력
            : DatePicker? = null
    var viewDatePick //  viewDatePick - 선택한 날짜를 보여주는 textView
            : TextView? = null
    var edtDiary //  edtDiary - 선택한 날짜의 일기를 쓰거나 기존에 저장된 일기가 있다면 보여주고 수정하는 영역
            : EditText? = null
    var btnSave //  btnSave - 선택한 날짜의 일기 저장 및 수정(덮어쓰기) 버튼
            : Button? = null
    var fileName //  fileName - 돌고 도는 선택된 날짜의 파일 이름
            : String? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰에 있는 위젯들 리턴 받아두기
        datePicker = view.findViewById<View>(R.id.datePicker) as DatePicker
        viewDatePick = view.findViewById<View>(R.id.viewDatePick) as TextView
        edtDiary = view.findViewById<View>(R.id.edtDiary) as EditText
        btnSave = view.findViewById<View>(R.id.btnSave) as Button

        // 오늘 날짜를 받게해주는 Calender 친구들
        val c = Calendar.getInstance()
        val cYear = c[Calendar.YEAR]
        val cMonth = c[Calendar.MONTH]
        val cDay = c[Calendar.DAY_OF_MONTH]

        // 첫 시작 시에는 오늘 날짜 일기 읽어주기
        checkedDay(cYear, cMonth, cDay)

        // datePick 기능 만들기
        // datePicker.init(연도,달,일)
        datePicker!!.init(
            datePicker!!.year,
            datePicker!!.month,
            datePicker!!.dayOfMonth
        ) { view, year, monthOfYear, dayOfMonth -> // 이미 선택한 날짜에 일기가 있는지 없는지 체크해야할 시간이다
            checkedDay(year, monthOfYear, dayOfMonth)
        }

        // 저장/수정 버튼 누르면 실행되는 리스너
        btnSave!!.setOnClickListener { // fileName을 넣고 저장 시키는 메소드를 호출
            saveDiary(fileName)
        }
    }

    // 일기 파일 읽기
    private fun checkedDay(year: Int, monthOfYear: Int, dayOfMonth: Int) {

        // 받은 날짜로 날짜 보여주는
        var month = monthOfYear + 1
        viewDatePick!!.text = "$year - $month - $dayOfMonth"

        // 파일 이름을 만들어준다. 파일 이름은 "20170318.txt" 이런식으로 나옴
        fileName = "$year$monthOfYear$dayOfMonth.txt"

        // 읽어봐서 읽어지면 일기 가져오고
        // 없으면 catch 그냥 살아? 아주 위험한 생각같다..
        var fis: FileInputStream? = null
        try {
            fis = getActivity()?.openFileInput(fileName)
            val fileData = fis?.available()?.let { ByteArray(it) }
            if (fis != null) {
                fis.read(fileData)
            }
            if (fis != null) {
                fis.close()
            }
            val str = fileData?.let { String(it) }
//            val str = String(fileData, "EUC-KR")

            Toast.makeText(getActivity()?.applicationContext, "일기 써둔 날", Toast.LENGTH_SHORT).show()
            edtDiary!!.setText(str)
            btnSave!!.text = "수정하기"
        } catch (e: Exception) { // UnsupportedEncodingException , FileNotFoundException , IOException
            // 없어서 오류가 나면 일기가 없는 것 -> 일기를 쓰게 한다.
            Toast.makeText(getActivity()?.applicationContext, "일기 없는 날", Toast.LENGTH_SHORT).show()
            edtDiary!!.setText("")
            btnSave!!.text = "새 일기 저장"
            e.printStackTrace()
        }
    }

    // 일기 저장하는 메소드
    @SuppressLint("WrongConstant")
    private fun saveDiary(readDay: String?) {
        var fos: FileOutputStream? = null
        try {
            fos = getActivity()?.openFileOutput(
                readDay,
                Context.MODE_NO_LOCALIZED_COLLATORS
            ) //MODE_WORLD_WRITEABLE
            val content = edtDiary!!.text.toString()

            // String.getBytes() = 스트링을 배열형으로 변환?
            if (fos != null) {
                fos.write(content.toByteArray())
            }
            //fos.flush();
            if (fos != null) {
                fos.close()
            }

            // getApplicationContext() = 현재 클래스.this ?
            Toast.makeText(getActivity()?.applicationContext, "일기 저장됨", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) { // Exception - 에러 종류 제일 상위 // FileNotFoundException , IOException
            e.printStackTrace()
            Toast.makeText(getActivity()?.applicationContext, "오류오류", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}