/*
     * Copyright (C) 2008  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.toycode;

import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Oct 11, 2008
 *
 */
public class BugParse
{
    protected Hashtable<Integer, Hashtable<Integer, Integer>> hash = new Hashtable<Integer, Hashtable<Integer,Integer>>();
    public BugParse()
    {
        process("20060901.html");
        process("20070901.html");
        process("20080301.html");
        process("20081001.html");
        
        StringBuilder yearStr = new StringBuilder();
        StringBuilder dataStr = new StringBuilder();
        
        Vector<Integer> keys = new Vector<Integer>(hash.keySet());
        Collections.sort(keys);
        for (Integer year : keys)
        {
            Hashtable<Integer, Integer> yearHash = hash.get(year);
            
            Vector<Integer> mKeys = new Vector<Integer>(yearHash.keySet());
            Collections.sort(mKeys);
            for (Integer mon : mKeys)
            {
                if (yearStr.length() > 0) yearStr.append(',');
                yearStr.append(mon+"/"+Integer.toString(year-2000));

                if (dataStr.length() > 0) dataStr.append(',');
                dataStr.append(yearHash.get(mon));

                System.out.println(year+"  "+mon+"  "+yearHash.get(mon));
            }
        }
        
        System.out.println(yearStr.toString());
        System.out.println(dataStr.toString());
        
        List<String> dataLines = new Vector<String>();
        dataLines.add(yearStr.toString());
        dataLines.add(dataStr.toString());
        createChart(dataLines);
    }
    
    protected static void fill(DefaultCategoryDataset ds, double[] vals, String[] col, String cat)
    {
        double total = 0.0;
        int i = 0;
        for (double d : vals)
        {
            total += d;
            ds.addValue(d, col[i++], cat);
        }
    }
    
    protected void createChart(final List<String> lines)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String[] headers = StringUtils.split(lines.get(0), ",");
        System.out.println(headers.length);
        List<double[]> valArray = new Vector<double[]>();
        
        for (int i=1;i<lines.size();i++)
        {
            String[] values =  StringUtils.splitPreserveAllTokens(lines.get(i), ",");
            double[] vals   = new double[headers.length];
            int inx = 0;
            double prev = -1;
            for (int j=0;j<headers.length;j++)
            {
                if (StringUtils.isNotEmpty(values[j]))
                {
                    prev = Double.parseDouble(values[j]);
                    vals[inx++] = prev;
                } else
                {
                    vals[inx++] = 0.0;
                }
            }
            for (int j=0;j<vals.length-1;j++)
            {
                if (vals[j] == 0.0 && vals[j+1] != 0.0 && vals[j-1] != 0.0)
                {
                    vals[j] = ((vals[j+1] - vals[j-1]) / 2.0) + vals[j-1];
                }
            }
            valArray.add(vals);
        }

        double[] vals = valArray.get(0);
        for (int i=0;i<vals.length;i++)
        {
            dataset.addValue(vals[i], "Bugs", headers[i]);
        }
        
        
        JFreeChart chart;
            chart = ChartFactory.createLineChart("Bugs", "Time", "Bugs", 
                    dataset, PlotOrientation.VERTICAL, 
                    true, true, false);
        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        //plot.setBackgroundPaint(Color.lightGray);
        //plot.setRangeGridlinePaint(Color.white);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAxisLineVisible(true);
        
        CategoryAxis catAxis = plot.getDomainAxis();
        catAxis.setAxisLineVisible(true);
        catAxis.setTickMarksVisible(true);
        
        ChartFrame frame = new ChartFrame("", chart, false);
        frame.setBackground(Color.WHITE);
        frame.setSize(500,500);
        frame.setVisible(true);
    }
    
    protected void process(final String fileName)
    {
        try
        {
            List<String> lines = FileUtils.readLines(new File("/Users/rod/Downloads/"+fileName));
            for (String line : lines)
            {
                int sInx = line.indexOf("<td><nobr>");
                if (sInx > -1)
                {
                    sInx += 10;
                    int eInx = line.indexOf("</nobr>", sInx);
                    if (eInx > -1)
                    {
                        String token = line.substring(sInx, eInx);
                        if (token.length() == 10 && token.charAt(4) == '-' && token.charAt(7) == '-')
                        {
                            int year = Integer.parseInt(token.substring(0, 4));
                            int mon  = Integer.parseInt(token.substring(5, 7));
                            
                            Hashtable<Integer, Integer> yearHash = hash.get(year);
                            if (yearHash == null)
                            {
                                yearHash = new Hashtable<Integer, Integer>();
                                hash.put(year, yearHash);
                            }
                            
                            Integer monTotal = yearHash.get(mon);
                            if (monTotal == null)
                            {
                                monTotal = 0;
                            }
                            monTotal++;
                            yearHash.put(mon, monTotal);
                        }
                    }
                }
            }
            
            
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new BugParse();
    }

}
