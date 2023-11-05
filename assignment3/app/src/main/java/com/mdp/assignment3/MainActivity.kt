package com.mdp.assignment3

import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.mdp.assignment3.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAdd.setOnClickListener{
            val tableRow = TableRow(applicationContext)
            val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            tableRow.layoutParams = layoutParams
            tableRow.addView(createTableCell(binding.etVersion.text.toString(), 34 , 0), 0)
            tableRow.addView(createTableCell(binding.etCodeName.text.toString(), 41, 34), 1)

            binding.tableLayoutMain.addView(tableRow)

            //clear once added
            binding.etVersion.text.clear()
            binding.etCodeName.text.clear()
        }

    }

    private fun createTableCell(text: String, leftMargin: Int, rightMargin: Int): TextView {
        val cell = TextView(applicationContext)
        val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(leftMargin,9,rightMargin,0)
        cell.layoutParams = layoutParams
        cell.setBackgroundColor(resources.getColor(R.color.myBgColor))
        cell.setTextColor(resources.getColor(R.color.black))
        cell.text = text
        return cell
    }
}