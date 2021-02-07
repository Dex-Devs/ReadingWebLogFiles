package com.example.myapp.java;

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("short-test_log");
        analyzer.printAll();
    }
    
    public void testUniqueIP() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        
        System.out.println("Number of logs with unique IP ADDRESSES:" + "\t" + analyzer.countUniqueIPs());
    }
    
    public void testAtLeastStatusCode() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog1_log");
        
        System.out.println("Logs with greater than STATUS CODE 400");
        analyzer.printAllHigherThanNum(400);
    }
    
    public void testIPsOnADay() { 
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        ArrayList<String> a = analyzer.uniqueIPVisitsOnADay("Sep 27");
        
        System.out.println("Size of list of IPs on a date: " + a.size());
        System.out.println("IPS ON A DAY");
        for(String gg : a) { 
            System.out.println(gg);
        }
    }
    
    public void testCountIPsInRange() { 
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        
        System.out.println("No of IPs in range 400-499: " + analyzer.countUniqueIPsInRange(400, 499));
    }
    
    public void testMostNumberVisitsByIP() { 
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        
        HashMap<String, Integer> myMap = analyzer.countVisitsPerIP();
        System.out.println(analyzer.mostNumberVisitsByIP(myMap));
    }
    
    public void testIPsMostVisits () { 
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        
         HashMap<String, Integer> myMap = analyzer.countVisitsPerIP();
         ArrayList<String> IPsWithMostVisits = analyzer.ipsMostVisits(myMap);
         
         System.out.println("Size of list containing IPS with most visits: " + IPsWithMostVisits.size());
         for(String ip : IPsWithMostVisits) { 
             System.out.println(ip);
         }
     
    }
    
    public void testIPForDays() { 
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog3-short_log");
        
        HashMap<String, ArrayList<String>> map = analyzer.ipsForDays();
        for(String date : map.keySet()) { 
            System.out.println("Date: " + date + "\t" + map.get(date).size());
        }
    }
    
    public void testdDayWithMostIPVisits() { 
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        
        HashMap<String, ArrayList<String>> map = analyzer.ipsForDays();
        String day = analyzer.dayWithMostIPVisits(map);
        
        System.out.println("Day with the most IP visits: " + day );
    }     
    
    public void testIPsWithMostVisitOnADay() { 
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        
        HashMap<String, ArrayList<String>> myMap = analyzer.ipsForDays();
        
        ArrayList<String> ipsWithMostVisits = analyzer.iPsWithMostVisitsOnDay(myMap, "Sep 29");
        
        for(String ip : ipsWithMostVisits) { 
            System.out.println(ip);
        }
        
        
    }
    public static void main(String [] args) {
        Tester tester = new Tester();
//        tester.testLogAnalyzer();
//        tester.testUniqueIP();
//        System.out.println();
//        tester.testAtLeastStatusCode();
//        tester.testIPsOnADay();
//        tester.testCountIPsInRange();
//        tester.testMostNumberVisitsByIP();
//        tester.testIPsMostVisits();
//        tester.testIPForDays();
//        tester.testdDayWithMostIPVisits();
        tester.testIPsWithMostVisitOnADay();
//        
        
        
    }
}
