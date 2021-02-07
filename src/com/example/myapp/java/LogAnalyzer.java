package com.example.myapp.java;

/**
 * Write a description of class LogAnalyzer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;

public class LogAnalyzer {

    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        this.records = new ArrayList<>();
    }

    public void readFile(String fileName) {

        String myDirectory = "com/example/myapp/data";
        FileResource fr = new FileResource(myDirectory + "/" + fileName); // getting the file
        for (String logLines : fr.lines()) { // read file by line
            LogEntry log = WebLogParser.parseEntry(logLines); // parser lines using weblog parser
            records.add(log); // add LogEntries to records
        }
    }
    
    //  print all log entries
    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }
    
    //  print logs that have more than n STATUS CODE
    public void printAllHigherThanNum(int num) {
        for(LogEntry entry : records) { 
            if(entry.getStatusCode() > num) { 
                System.out.println(entry);
            }
        }
    }
    
    //  get number of UNIQUE IP ADDRESSES
    public int countUniqueIPs() {

        ArrayList<String> uniqueIPs = new ArrayList<>();

        for (LogEntry log : records) {
            String currIP = log.getIpAddress();
            if (!uniqueIPs.contains(currIP)) {
                uniqueIPs.add(currIP);
            }
        }

        return uniqueIPs.size();
    }
    
    //  get an arraylist of IP ADDRESSES from web logs that has been logged on a specified date
    public ArrayList<String> uniqueIPVisitsOnADay(String dateFormat) {
        
        ArrayList<String> ipAddressOnADay = new ArrayList<>();
            for(LogEntry le : records) { 
             
                if(le.getAccessTime().toString().contains(dateFormat)) {
                    
                    if(! ipAddressOnADay.contains(le.getIpAddress())){
                        ipAddressOnADay.add(le.getIpAddress());
                    }
                    
                }else {
                }
            }
            
        return ipAddressOnADay;
    }
    
    //  get count of unique IPs that has a STATUS CODE thats within 2 STATUS CODE
    public int countUniqueIPsInRange(int low, int high) {
        
        ArrayList<String> occuredIPs = new ArrayList<>();
        int countIPs = 0;
        for(LogEntry le : records) {
            String ip = le.getIpAddress();
            int statIP = le.getStatusCode();
            if(statIP >= low && statIP <= high) {
                
                if(!occuredIPs.contains(ip)) {
                    countIPs += 1;
                    occuredIPs.add(le.getIpAddress());
                }
            }
        }
        
        return countIPs;
    }
    
    //  get count of visits of every IP
    public HashMap<String, Integer> countVisitsPerIP() { 
        
        HashMap<String, Integer> counts = new HashMap<>();
        
        for(LogEntry le : records) {
            String ip = le.getIpAddress();
            
            if(!counts.containsKey(ip)) {
                counts.put(ip, 1);
            }else {
                counts.put(ip, counts.get(ip) + 1);
            }
            
        }
        
        return counts;
    }
    
    //  get max number of visit of IPs
    public int mostNumberVisitsByIP(HashMap<String, Integer> map) { 
        
        Map.Entry<String, Integer> maxEntry = Collections.max(map.entrySet(), (Map.Entry<String, Integer> en1, Map.Entry<String, Integer> en2) ->
                en1.getValue().compareTo(en2.getValue()));
        
        return maxEntry.getValue();
    }
    
    public ArrayList<String> ipsMostVisits(HashMap<String, Integer> myMap) { 
        
        ArrayList<String> ipWithMax = new ArrayList<>();
        int max = mostNumberVisitsByIP(myMap);
        for(String currIP : myMap.keySet()) {
            
            if(myMap.get(currIP) == max) {
                ipWithMax.add(currIP);
            }
        }
        
        return ipWithMax;
    }
    
    // helper method to get only the month and date on the access time ( Wed Sep 30 19:47:11 SGT 2015 ) 
    private String getMonthAndDay(String date) { 
        
    // ASSUMING THAT THIS IS THE ACCESS TIME THAT WE GOT FROM THE LOG -- Wed Sep 30 19:47:11 SGT 2015 -- 
        ArrayList<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");
        
        String monthDay = null;
        for(String month : months) { 
            if(date.contains(month)) { 
                int index = date.indexOf(month);
                monthDay = date.substring(index, (index + 6));
                break;
            }
        }
        
        return monthDay;
    }
    
    
    //  get IPs who visited on a specific day
    public HashMap<String, ArrayList<String>> ipsForDays() { 
        
        HashMap<String, ArrayList<String>> ipsForDay = new HashMap<>();
        
        for(LogEntry le : records) { 
            String date = getMonthAndDay(le.getAccessTime().toString());
            
            if(!ipsForDay.containsKey(date)) { // list is empty at first iteration
                ArrayList<String> ipAdd = new ArrayList<>(); // initialize arraylist
                ipAdd.add(le.getIpAddress()); // add ip
                ipsForDay.put(date, ipAdd);
            }else { // if date exists in the map, just update the arraylist that it maps to
                ArrayList<String> listOfIPFromMap = ipsForDay.get(date); // get arraylist from map
                listOfIPFromMap.add(le.getIpAddress()); // add item to that list
                
                ipsForDay.put(date, listOfIPFromMap); // update map with the date
             }
        }
        
        return ipsForDay;
    }
    
    // get day when there has the most number of IP visits
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
        
        String day = null;
        int maxSize = 0;
        for(String date : map.keySet()) { 
            int currSize = map.get(date).size();
            if(currSize > maxSize) { 
                maxSize = currSize;
                day = date;
            }
        }
        return day;
    }
    
    //  get IP addresses that had the most number of access on the given day
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String day) { 
        
        
        ArrayList<String> ipsOnADay = map.get(day); // get all IPs that had access on the day -- NOT THE UNIQUES
        HashMap<String, Integer> counts = new HashMap<>(); // new HasMap for storing counts per ip
        
        for(int i = 0 ; i < ipsOnADay.size() ; i++) {
            String ip = ipsOnADay.get(i);
            
            if(!counts.containsKey(ip)) {
                counts.put(ip, 1);
            }else {
                counts.put(ip, counts.get(ip)+1);
            }
        }
        
        return ipsMostVisits(counts); // get IPs with most visits
    }
    
}
