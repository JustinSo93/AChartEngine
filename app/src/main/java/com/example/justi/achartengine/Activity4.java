package com.example.justi.achartengine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

public class Activity4 extends Activity {
    private static int columnCount ;
    private GraphicalView mChart;
    protected SQLiteDatabase database;
   protected  CategorySeries distributionSeries = new CategorySeries("Pie Chart");
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

    private XYSeries mCurrentSeries;

    private XYSeriesRenderer mCurrentRenderer;

    private void initChart() {


    }

    private void addSampleData() {

        mCurrentSeries.add(1, 2);
        mCurrentSeries.add(2, 3);
        mCurrentSeries.add(3, 2);
        mCurrentSeries.add(4, 5);
        mCurrentSeries.add(5, 4);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        columnCount = 0;
        final Context context = this;
        ArrayList<Double> expenseList = new ArrayList<Double>();
        ArrayList<Double> percentageList = new ArrayList<Double>();

        mCurrentSeries = new XYSeries("Sample Data");
        mDataset.addSeries(mCurrentSeries);
        mCurrentRenderer = new XYSeriesRenderer();
        mRenderer.addSeriesRenderer(mCurrentRenderer);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        mRenderer.setShowGrid(true); // we show the grid
        mCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
        mCurrentRenderer.setPointStrokeWidth(3);
        mRenderer.setXTitle("Categories");
        mRenderer.setYTitle("Expenses($)");
        mRenderer.setChartTitle("Expense Report");
        mRenderer.setAxisTitleTextSize(36);
        mRenderer.setChartTitleTextSize(36);
        mRenderer.setLabelsTextSize(24);






        Button datainsertion = (Button) findViewById(R.id.datainsertion);
        final DatabaseHelper dh = new DatabaseHelper(this);
        database = dh.getWritableDatabase();
       // database.execSQL("DELETE FROM summary where Type = 'gas'");
        String[] allColumns = {dh.ID, dh.type, dh.expenses};
        Cursor cursor = database.rawQuery("SELECT * FROM summary", null);
         if (cursor!=null) {
             cursor.moveToFirst();
             String[] categories = new String[cursor.getColumnCount()];
             columnCount = cursor.getColumnCount();
             double expenseTotal = 0;
             int count = 1;
             int count1 = 0;
             while (!cursor.isAfterLast()) {
                 System.out.println("Cursor integer is: " +cursor.getDouble(cursor.getColumnIndex(dh.expenses)));
                double temp = cursor.getDouble(cursor.getColumnIndex(dh.expenses));
                 expenseList.add(temp);
                 expenseTotal += temp;
                 mCurrentSeries.add(count, temp);
                 mRenderer.addXTextLabel(count,cursor.getString(cursor.getColumnIndex(dh.type) ));
                 categories[count1] = cursor.getString(cursor.getColumnIndex(dh.type));
                 System.out.println("Cursor string is: " + cursor.getString(cursor.getColumnIndex(dh.type)));
                 count++;
                 count1++;
                 cursor.moveToNext();
             }

             for (int i = 0; i < columnCount;i++){
                percentageList.add(i, expenseList.get(i)/expenseTotal);
                 distributionSeries.add(categories[i],percentageList.get(i));
             }
             String[] chartTypes = {"BarChart", "PieChart"};

             System.out.println("categories[0]: " + categories[0]);
             ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chartTypes);
             ListView listView = (ListView) findViewById(R.id.listview);
             listView.setAdapter(itemsAdapter);
             listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0){
                        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
                        if (mChart == null) {

                            //addSampleData();
                            //mChart = ChartFactory.getCubeLineChartView(this, mDataset, mRenderer, 0.3f);
                            mChart =  ChartFactory.getBarChartView(context, mDataset, mRenderer, BarChart.Type.DEFAULT);
                            Intent intent = ChartFactory.getBarChartIntent(getBaseContext(), mDataset, mRenderer, BarChart.Type.DEFAULT);
                            startActivity(intent);
                           // layout.addView(mChart);
                        } /**else {
                            mChart.repaint();
                        }*/


                    }
                    if (i==1){
                        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN};
                        DefaultRenderer defaultRenderer  = new DefaultRenderer();

                        for(int j = 0 ;j<columnCount;j++) {
                            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
                            seriesRenderer.setColor(colors[j]);
                            defaultRenderer.addSeriesRenderer(seriesRenderer);
                        }
                            //seriesRenderer.setDisplayChartValues(true);
                            // Adding a renderer for a slice

                            defaultRenderer.setChartTitleTextSize(10);
                        defaultRenderer.setLabelsTextSize(30);
                            //defaultRenderer.setZoomButtonsVisible(true);
                            Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");

                            // Start Activity
                            startActivity(intent);


                    }
                 }
             });
         }

        datainsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Activity4.this.startActivity(new Intent(Activity4.this,Data.class));
            }
        });

    }

    protected void onResume() {
        super.onResume();

    }

}
