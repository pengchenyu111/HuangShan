package com.example.huangshan.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.example.huangshan.bean.DailyNum;
import com.example.huangshan.bean.HourlyNum;
import com.example.huangshan.bean.OneDayHourlyNum;
import com.example.huangshan.builder.BuilderManager;
import com.example.huangshan.utils.HttpUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import com.github.mikephil.charting.model.GradientColor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListenerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity中首页的fragment
 */
public class ShowDataFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {

    private static final String TAG = "ShowDataFragment";

    private View view;

    private String startTime;
    private String endTime;
    public Calendar calendar;
    private List<DailyNum> dailyNums = new ArrayList<>();//每天数据
    private List<HourlyNum> hourlyNums = new ArrayList<>();//小时数据
    private List<OneDayHourlyNum> oneDayHourlyNums = new ArrayList<>();//某一天的详细小时数据

    @BindView(R.id.showdata_line_chart)
    LineChart lineChart;
    @BindView(R.id.showdata_bar_chart)
    BarChart barChart;
    @BindView(R.id.showdata_boommenubtn)
    BoomMenuButton showdataBoomMenuBtn;

    public ShowDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载HomeFragment布局
        view = inflater.inflate(R.layout.fragment_main_showdata,container,false);

        //绑定控件
        ButterKnife.bind(this,view);
        calendar = Calendar.getInstance();

        //设置响应
        barChart.setOnChartValueSelectedListener(this);

        //从数据库获取数据
        //先初始化一次数据，避免没有选择日期而导致空指针
        initData("2019-09-10","2019-09-20");

        //绘制图表
        drawCharts();

        //底部浮动按钮
        drawFloatButton();

        return view;
    }

    private void drawFloatButton() {
        assert showdataBoomMenuBtn != null;
        showdataBoomMenuBtn.addBuilder(BuilderManager.getHamButtonBuilder("点击日期区间", "").normalImageRes(R.mipmap.calendar));
        showdataBoomMenuBtn.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                super.onClicked(index, boomButton);
                showDatePicker();
                changeBoomButton(index);
            }
        });
    }

    private void drawCharts() {
        //折线图
        //先往折线图中装载初始数据，以免最开始没有数据
        DailyNum dailyNum = dailyNums.get(dailyNums.size()-1);
        String firstDateName = dailyNum.getDataName();
        String showDateName = firstDateName.substring(5,10);
        handleHourlyData(firstDateName);
        initLineChart(showDateName);

        //柱状图
        initBarChart();
        setBarChartData();
    }

    private void showDatePicker(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.datepicker, null);
        final DatePicker startTimePicker = (DatePicker) view.findViewById(R.id.starttime);
        final DatePicker endTimePicker = (DatePicker) view.findViewById(R.id.endtime);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //开始时间
                int yearS = startTimePicker.getYear();
                int realMonthS = startTimePicker.getMonth()+1;
                int realDayS = startTimePicker.getDayOfMonth();
                String formatMonthS = formatDate(realMonthS);
                String formatDayS = formatDate(realDayS);
                startTime = String.valueOf(yearS)+"-"+formatMonthS+"-"+formatDayS;
                Log.d(TAG,"startTime：-------------"+startTime+"----------------结束");

                //结束时间
                int yearE = endTimePicker.getYear();
                int realMonthE = endTimePicker.getMonth()+1;
                int realDayE = endTimePicker.getDayOfMonth();
                String formatMonthE = formatDate(realMonthE);
                String formatDayE = formatDate(realDayE);
                endTime = String.valueOf(yearE)+"-"+formatMonthE+"-"+formatDayE;
                Log.d(TAG,"endTime：-------------"+endTime+"----------------结束");

                //更新图表
                initData(startTime,endTime);
                drawCharts();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private String formatDate(int date){
        String formatedDate = String.valueOf(date);
        if (date == 1 || date == 2 || date == 3 || date == 4 || date == 5 || date == 6 || date == 7 || date == 8 || date == 9) {
            formatedDate = "0"+formatedDate;
        }
        return formatedDate;
    }

    private void changeBoomButton(int index) {
        HamButton.Builder builder = (HamButton.Builder) showdataBoomMenuBtn.getBuilder(index);
        if (index == 0) {
            String tip = dailyNums.get(0).getDataName()+"----"+dailyNums.get(dailyNums.size()-1).getDataName();
            builder.normalText(tip);
//            builder.highlightedText("Highlighted, changed!");
            builder.subNormalText("点击选取日期区间");
            builder.normalTextColor(Color.YELLOW);
            builder.highlightedTextColorRes(R.color.colorPrimary);
            builder.subNormalTextColor(Color.BLACK);
        }
    }

    private void initLineChart(String showDateName) {
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragDecelerationFrictionCoef(0.9f);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setHighlightPerDragEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.animateX(1000);
        //底部标签设置
        final String[] labels = new String[oneDayHourlyNums.size()];
        for (int i = 0; i <oneDayHourlyNums.size() ; i++) {
            labels[i] = oneDayHourlyNums.get(i).getHour();
        }
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if ((int)value>=labels.length){
                    return "";
                }else{
                    return labels[(int) value];
                }
            }
        };
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(oneDayHourlyNums.size());//横坐标显示几个;
        xAxis.setValueFormatter(formatter);

        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < oneDayHourlyNums.size(); i++) {
            entries.add(new Entry(i,oneDayHourlyNums.get(i).getHourNum()));
        }

        LineDataSet lineDataSet;
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(entries);
            lineDataSet.setLabel(showDateName+"日小时客流");
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();

        } else {
            lineDataSet = new LineDataSet(entries, showDateName+"日小时客流");
            lineDataSet.setDrawIcons(false);
            lineDataSet.setLineWidth(2.5f);
            lineDataSet.setCircleRadius(4.5f);
            lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
            lineDataSet.setDrawValues(true);//设置显示数据

            List<ILineDataSet> lineDataSets = new ArrayList<>();
            lineDataSets.add(lineDataSet);

            LineData data = new LineData(lineDataSets);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(11f);
            data.setDrawValues(true);

            lineChart.setData(data);
            lineChart.invalidate();
        }
    }

    private void handleHourlyData(String dateName) {
        oneDayHourlyNums.clear();
//        int count = 0;
//        float start = System.currentTimeMillis();
        for (int i = 0; i < hourlyNums.size(); i++) {
            if (hourlyNums.get(i).getDateName().equals(dateName)){
                oneDayHourlyNums.add(new OneDayHourlyNum(hourlyNums.get(i).getHour(),hourlyNums.get(i).getHourNum()));
            }
//            count++;
            if (oneDayHourlyNums.size()>0 && !hourlyNums.get(i).getDateName().equals(dateName)){
               break;
           }
        }
//        float end = System.currentTimeMillis();
//        Log.d(TAG,"hourlyNums.size()为：-----------"+hourlyNums.size()+"----------------");
//        Log.d(TAG,"循环次数为：-----------"+count+"次----------------");
//        Log.d(TAG,"花费的时间为：-----------"+(end-start)+"ms----------------");
    }

    //往柱状图中设置数据
    private void setBarChartData() {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < dailyNums.size(); i++) {
            int val = dailyNums.get(i).getTotalNum();

            if (val<100) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet barDataSet;
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            barDataSet.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();

        } else {
            barDataSet = new BarDataSet(values, "每日客流总数");
            barDataSet.setDrawIcons(false);

            //设置花里胡哨的渐变色
            int[] startColors = {
                    ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_blue_light),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_green_light),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_red_light)
            };
            int[] endColors = {
                    ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_purple),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_red_dark),
                    ContextCompat.getColor(getActivity(), android.R.color.holo_orange_dark)
            };

            List<GradientColor> gradientColors = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                gradientColors.add(new GradientColor(startColors[i],endColors[i]));
            }

            //BarDataSet设置渐变色
            barDataSet.setGradientColors(gradientColors);

            //ArrayList<IBarDataSet>添加BarDataSet
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet);

            //BarData设置ArrayList<IBarDataSet>
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(tfLight);
            data.setBarWidth(0.8f);//设置每个柱子的宽度

            barChart.setData(data);
        }
    }

    //初始化柱状图
    private void initBarChart() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.animateY(1000);//动画

        //X轴设置
        //底部标签设置
        final String[] labels = new String[dailyNums.size()];
        for (int i = 0; i <dailyNums.size() ; i++) {
            labels[i] = dailyNums.get(i).getDataName().substring(5,10);
        }
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if ((int)value>=labels.length){
                    return "";
                }else{
                    return labels[(int) value];
                }
            }
        };
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(dailyNums.size());//横坐标显示几个;
        xAxis.setValueFormatter(formatter);

        //Y轴设置
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    /**
     * 从数据库获得开始和结束时间区间内的客流数据，包括日客流和小时客流
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    private void initData(String startTime,String endTime) {
        String url = Constant.URL+"SelectDailyNumServlet";
        String url2 = Constant.URL+"SelectHourlyNumServlet";

        Map<String,String> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);

        //发起网络请求
        try {
            String dailyResult = HttpUtil.postRequest(url,map);
            String hourlyResult = HttpUtil.postRequest(url2,map);

            //转为对象
            Gson gson = new Gson();
            dailyNums = gson.fromJson(dailyResult,new TypeToken<List<DailyNum>>(){}.getType());
            Log.d(TAG,dailyNums.toString());
            Gson gson1 = new Gson();
            hourlyNums = gson1.fromJson(hourlyResult,new TypeToken<List<HourlyNum>>(){}.getType());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            default:
                break;
        }
    }

    //接下来是  OnChartValueSelectedListener  需要实现的两个方法
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null){
            return;
        }
//        Toast.makeText(getActivity(),"you click a bar",Toast.LENGTH_SHORT).show();
        int getX = (int) e.getX();
        String oneDayDataName = dailyNums.get(getX).getDataName();

        handleHourlyData(oneDayDataName);
        initLineChart(oneDayDataName.substring(5,10));
    }

    @Override
    public void onNothingSelected() {

    }
}