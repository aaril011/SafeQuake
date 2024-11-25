package com.dicoding.safequake.ui.evaluation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.dicoding.safequake.R
import com.dicoding.safequake.ui.form.FormEvaluationActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class EvaluationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_evaluation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pieChart: PieChart = view.findViewById(R.id.pieChart)

        val entries = listOf(
            PieEntry(75f, "Gempa"),
            PieEntry(85f, "Tsunami"),
            PieEntry(65f, "Longsor"),
            PieEntry(90f, "Banjir")
        )

        val colors = listOf(
            Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN
        )

        val dataSet = PieDataSet(entries, "Skor Resiko").apply {
            setColors(colors)
            sliceSpace = 3f
            valueTextSize = 12f
            valueTextColor = Color.BLACK
        }

        val pieData = PieData(dataSet)

        pieChart.apply {
            data = pieData
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setEntryLabelColor(Color.BLACK)
            animateY(1000)
        }

        val btnAnalysis: Button = view.findViewById(R.id.btn_analysis)

        btnAnalysis.setOnClickListener {
            val intent = Intent(activity, FormEvaluationActivity::class.java)
            startActivity(intent)
        }
    }
}
