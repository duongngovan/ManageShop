package com.example.shopmanage.thongke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.shopmanage.R;
import com.example.shopmanage.dao.HoaDonDAO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private PieChart mChart;
    private HoaDonDAO hoaDonDAO;
    private static double ngay,nam,thang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        Toolbar toolbar = findViewById(R.id.toolbar_tk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hoaDonDAO = new HoaDonDAO(getApplicationContext());
         ngay = hoaDonDAO.getDoanhThuTheoNgay();
         thang = hoaDonDAO.getDoanhThuTheoThang();
         nam = hoaDonDAO.getDoanhThuTheoNam();
        mChart = (PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        Description description = new Description();
        description.setText("Thống kê theo biểu đồ tròn !");
        description.setTextSize(20f);

        mChart.setDescription(description);
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Thống Kê");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);

        addDataSet(mChart);

        mChart.setOnChartValueSelectedListener(this);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Giá trị: "
                + e.getY()

                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private static void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        yEntrys.add(new PieEntry((float)ngay,"Ngày"));
        yEntrys.add(new PieEntry((float)thang,"Tháng"));
        yEntrys.add(new PieEntry((float)nam,"Năm"));



        PieDataSet pieDataSet=new PieDataSet(yEntrys,"");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
