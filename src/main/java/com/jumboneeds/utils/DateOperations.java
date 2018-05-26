package com.jumboneeds.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateOperations {

    //For Server's Own Use
    public static Date getTodayStartDate(){
        Calendar calendar = Calendar.getInstance();

        //Change Date on the basis of Server Time >= 18:30
        long seconds = calendar.get(Calendar.HOUR_OF_DAY)*60*60 + calendar.get(Calendar.MINUTE)*60;

        if(seconds >= Constants.SERVER_DATE_CHANGE_SECOND){
            calendar.add(Calendar.DATE, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayEndDate(){
        Calendar calendar = Calendar.getInstance();

        //Change Date on the basis of Server Time >= 18:30
        long seconds = calendar.get(Calendar.HOUR_OF_DAY)*60*60 + calendar.get(Calendar.MINUTE)*60;

        if(seconds >= Constants.SERVER_DATE_CHANGE_SECOND){
            calendar.add(Calendar.DATE, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTomorrowStartDate(){
        Calendar calendar = Calendar.getInstance();

        //Change Date on the basis of Server Time >= 18:30
        long seconds = calendar.get(Calendar.HOUR_OF_DAY)*60*60 + calendar.get(Calendar.MINUTE)*60;

        if(seconds >= Constants.SERVER_DATE_CHANGE_SECOND){
            calendar.add(Calendar.DATE, 2);
        } else {
            calendar.add(Calendar.DATE, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTomorrowEndDate(){
        Calendar calendar = Calendar.getInstance();

        //Change Date on the basis of Server Time >= 18:30
        long seconds = calendar.get(Calendar.HOUR_OF_DAY)*60*60 + calendar.get(Calendar.MINUTE)*60;

        if(seconds >= Constants.SERVER_DATE_CHANGE_SECOND){
            calendar.add(Calendar.DATE, 2);
        } else {
            calendar.add(Calendar.DATE, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getDateDifference(Date date1, Date date2){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        long difference = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();

        return (int) (difference/86400000);
    }

    public static List<Date> getWeekDatesList(){
        List<Date> dates = new ArrayList<>();
        Integer counter = 0;
        for(int i = -6; i <= 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            dates.add(counter, calendar.getTime());
            counter ++;
        }
        return dates;
    }

    public static String getDateString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getDateStringForAdmin(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getDateStringForStore(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getDateStringForDump(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
        return simpleDateFormat.format(date);
    }

    public static Date getCustomStartDateFromString(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String getMonthStringFromDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
        return simpleDateFormat.format(date);
    }

    //For Client to Server Date Conversion
    public static Date getClientToServerCustomStartDateFromLong(Long date, int difference){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.add(Calendar.DATE, difference);
        calendar.add(Calendar.HOUR_OF_DAY, 5);
        calendar.add(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getClientToServerCustomEndDateFromLong(Long date, int difference){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.add(Calendar.DATE, difference);
        calendar.add(Calendar.HOUR_OF_DAY, 5);
        calendar.add(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    //For Server to Client Date Conversion
    public static Date getServerToClientCustomStartDate(Date date, int difference){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, difference);
        calendar.add(Calendar.HOUR_OF_DAY, -5);
        calendar.add(Calendar.MINUTE, -30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getServerToClientCustomEndDate(Date date, int difference){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, difference);
        calendar.add(Calendar.HOUR_OF_DAY, -5);
        calendar.add(Calendar.MINUTE, -30);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getCustomStartDateFromDate(Date date, int difference){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, difference);
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}