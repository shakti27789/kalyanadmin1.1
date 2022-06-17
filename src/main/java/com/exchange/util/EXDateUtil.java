package com.exchange.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

public class EXDateUtil {

	protected static final Logger LOGGER = Logger.getRootLogger();

	/**
	 * @return
	 */
	public String getYestDate(Date dt) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date yesterday = dateAdd(dt, -1);
		return df.format(yesterday);
	}

	public String convertBetfairToDate(Date date, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(date);
	}

	public String getSevenDaysBack() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Date sevendaybck = dateAdd(date, -7);
		return dateFormat.format(sevendaybck);
	}

	/**
	 * @param joinedOn
	 * @return
	 * @throws ParseException
	 */

	public String getYestDay() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Date yest = dateAdd(date, -1);
		return dateFormat.format(yest);
	}

	public String getToday() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}

	/**
	 * @param in
	 * @param daysToAdd
	 * @return
	 */
	public Date dateAdd(Date in, int daysToAdd) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(in);
		cal.add(Calendar.DAY_OF_MONTH, daysToAdd);
		return cal.getTime();
	}

	public Date hourAdd(Date in, int hoursToAdd) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(in);
		cal.add(Calendar.HOUR, hoursToAdd);
		return cal.getTime();
	}

	public Date minAdd(Date in, int minsToAdd) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(in);
		cal.add(Calendar.MINUTE, minsToAdd);
		return cal.getTime();
	}

	public Date secondAdd(Date in, int secsToAdd) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(in);
		cal.add(Calendar.SECOND, secsToAdd);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @param format
	 * @param daysToAdd
	 * @return
	 */
	public String dateAdd(String date, String format, int daysToAdd) {
		final String DATE_TIME_FORMAT_SOURCE = format;
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);

		Date specifiedTime = null;

		try {
			specifiedTime = sdfSource.parse(date);
			specifiedTime = dateAdd(specifiedTime, daysToAdd);
		} catch (Exception e) {
			LOGGER.warn(e);
		}

		return sdfSource.format(specifiedTime);
	}

	/**
	 * @param date
	 * @param format
	 * @param secToAdd
	 * @return
	 */
	public String dateAddSecond(String date, String format, int secToAdd) {
		final String DATE_TIME_FORMAT_SOURCE = format;
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);

		Date specifiedTime = null;

		try {
			specifiedTime = sdfSource.parse(date);
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(specifiedTime);
			cal.add(Calendar.SECOND, secToAdd);
			specifiedTime = cal.getTime();
		} catch (Exception e) {
			LOGGER.warn(e);
		}

		return sdfSource.format(specifiedTime);
	}

	/**
	 * @param in
	 * @param daysToAdd
	 * @return
	 */
	public Date dateAddMonth(Date in, int mnthsToAdd) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(in);
		cal.add(Calendar.MONTH, mnthsToAdd);
		return cal.getTime();
	}

	public Date dateAddMinutes(Date in, int minutesToAdd) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(in);
		cal.add(Calendar.MINUTE, minutesToAdd);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public Date convertToDate(String date, String format) throws ParseException {
		DateFormat df = new SimpleDateFormat(format, Locale.US);

		return df.parse(date);
	}

	public String convertToStringfromSeconds(String format, String date) throws ParseException {
		Format formatter = new SimpleDateFormat(format, Locale.US);
		Date dt = new Date(Long.parseLong(date) * 1000);
		String s = formatter.format(dt);
		return s;
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public Date convertToDate(Date date, String format) throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		return df.parse(df.format(date));
	}

	/**
	 * @param dt
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public String convertToString(Date dt, String format) throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(dt);
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public Date convertToDate2(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(date);
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public String formatDate(Date date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	public String formatDate(Date date, String format) throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public Date convertToDate3(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(date);
	}

	public String formatDate(String date, String sourceformat, String destformat) {
		DateFormat df = new SimpleDateFormat(sourceformat);

		DateFormat df2 = new SimpleDateFormat(destformat);
		try {
			Date dt = df.parse(date);

			return df2.format(dt);
		} catch (Exception e) {
			return date;
		}
	}

	/*
	 * Converts time from sourceTZ TimeZone to destTZ TimeZone.
	 * 
	 * @return converted time, or the original time, in case the datetime could not
	 * be parsed
	 */
	public String convTimeZone(String time, String sourceTZ, String destTZ) {
		final String DATE_TIME_FORMAT = "E, dd MMM yyyy HH:mm:ss Z";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

		Date specifiedTime = null;

		try {
			if (sourceTZ != null)
				sdf.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			else
				sdf.setTimeZone(TimeZone.getDefault());
			specifiedTime = sdf.parse(time);

		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(specifiedTime);
	}

	/*
	 * Converts time from sourceTZ TimeZone to destTZ TimeZone.
	 * 
	 * @return converted time, or the original time, in case the datetime could not
	 * be parsed
	 */

	public String convTimeZone2(String time, String sourceTZ, String destTZ) {

		final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

		Date specifiedTime = null;

		try {
			if (sourceTZ != null)
				sdf.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			else
				sdf.setTimeZone(TimeZone.getDefault());
			specifiedTime = sdf.parse(time);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(specifiedTime);
	}

	public String convTimeZone(Date time, String sourceTZ, String destTZ, String destFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(destFormat);

		try {
			if (sourceTZ != null)
				sdf.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			else
				sdf.setTimeZone(TimeZone.getDefault());
		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(time);
	}

	/**
	 * @param time
	 * @param destTZ
	 * @return
	 */
	public String conToUserTimeZone(Date time, String destTZ) {
		final String DATE_TIME_FORMAT_DEST = "hh:mm aaa EEE, MMM d";

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_DEST);

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(time);
	}

	/**
	 * @param time
	 * @param destTZ
	 * @return
	 */
	public String conToUserTimeZoneWorkbook(Date time, String destTZ) {
		final String DATE_TIME_FORMAT_DEST = "dd-MM-yyyy HH:mm:ss";

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_DEST);

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(time);
	}

	/**
	 * @param time
	 * @param destTZ
	 * @return
	 * @throws ParseException
	 */
	public Date convTimeZone(Date time, String destTZ) throws ParseException {

		final String DATE_TIME_FORMAT_DEST = "yyyy-MM-dd HH:mm:ss";

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_DEST);

		SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_TIME_FORMAT_DEST);

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
		
			sdf.setTimeZone(TimeZone.getDefault());

		return sdf2.parse(sdf.format(time));
	}

	/**
	 * @param time
	 * @param sourceTZ
	 * @param destTZ
	 * @return
	 */
	public String convTimeZoneSolr(String time, String sourceTZ, String destTZ) {
		final String DATE_TIME_FORMAT_SOURCE = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);

		final String DATE_TIME_FORMAT_SOLR = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdfDest = new SimpleDateFormat(DATE_TIME_FORMAT_SOLR);

		Date specifiedTime = null;

		try {
			if (sourceTZ != null)
				sdfSource.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			else
				sdfSource.setTimeZone(TimeZone.getDefault());
			specifiedTime = sdfSource.parse(time);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (destTZ != null)
			sdfDest.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdfDest.setTimeZone(TimeZone.getDefault());

		return sdfDest.format(specifiedTime);
	}

	public String convTimeZoneSolrApi(String time, String sourceTZ, String destTZ) {
		final String DATE_TIME_FORMAT_SOURCE = "yyyy-MM-dd HH:mm:ss.SSS";
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);

		final String DATE_TIME_FORMAT_SOLR = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		SimpleDateFormat sdfDest = new SimpleDateFormat(DATE_TIME_FORMAT_SOLR);

		Date specifiedTime = null;

		try {
			if (sourceTZ != null)
				sdfSource.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			else
				sdfSource.setTimeZone(TimeZone.getDefault());
			specifiedTime = sdfSource.parse(time);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (destTZ != null)
			sdfDest.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdfDest.setTimeZone(TimeZone.getDefault());

		return sdfDest.format(specifiedTime);
	}

	/**
	 * @param time
	 * @return
	 */
	public String convToSolrTime(Date time) {
		final String DATE_TIME_FORMAT_SOLR = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdfDest = new SimpleDateFormat(DATE_TIME_FORMAT_SOLR);

		return sdfDest.format(time);
	}

	/*
	 * Converts time from sourceTZ TimeZone to destTZ TimeZone.
	 * 
	 * @return converted time, or the original time, in case the datetime could not
	 * be parsed
	 */
	public String convToTimeZoneAndAdd(String time, String dtFmt, String sourceTZ, String destTZ, int month, int days,
			int year) throws ParseException {
		final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; //$NON-NLS-1$
		final String DATE_TIME_FORMAT_AM_PM = "yyyy-MM-dd h:mm a"; //$NON-NLS-1$

		SimpleDateFormat sdf = null;

		if (dtFmt.equalsIgnoreCase("AM_PM")) //$NON-NLS-1$
		{
			sdf = new SimpleDateFormat(DATE_TIME_FORMAT_AM_PM);
		} else {
			sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		}

		GregorianCalendar cal = new GregorianCalendar();
		Date specifiedTime = null;

		try {
			if (sourceTZ != null)
				sdf.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			else
				sdf.setTimeZone(TimeZone.getDefault());

			specifiedTime = sdf.parse(time);

			cal.setTime(specifiedTime);
			if (month > 0) {
				cal.add(Calendar.MONTH, month);
			}

			if (days > 0) {
				cal.add(Calendar.DAY_OF_MONTH, days);
			}

			if (year > 0) {
				cal.add(Calendar.YEAR, year);
			}

			specifiedTime = cal.getTime();
		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(specifiedTime);
	}

	public Date add(Date date, int month, int days, int year) {
		GregorianCalendar cal = new GregorianCalendar();

		try {

			cal.setTime(date);
			if (month > 0) {
				cal.add(Calendar.MONTH, month);
			}

			if (days > 0) {
				cal.add(Calendar.DAY_OF_MONTH, days);
			}

			if (year > 0) {
				cal.add(Calendar.YEAR, year);
			}

		} catch (Exception e) {
			LOGGER.error(e);
		}

		return cal.getTime();
	}

	/**
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public ArrayList<String> getAllBetweenDates(String date1, String date2, String format) throws ParseException {

		ArrayList<String> finaldates = new ArrayList<String>();
		Set<String> dates = new LinkedHashSet<String>();

		SimpleDateFormat df = new SimpleDateFormat(format);

		SimpleDateFormat finalDf = new SimpleDateFormat(EXConstants.DATE_YYYY_MM_DD);

		Date dt1 = df.parse(date1);
		Date dt2 = df.parse(date2);

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(dt1);
		c2.setTime(dt2);

		long milliseconds1 = c1.getTimeInMillis();
		long milliseconds2 = c2.getTimeInMillis();

		long days = ((milliseconds2 - milliseconds1) / (1000 * 60 * 60 * 24));

		dates.add(finalDf.format(dt1));

		for (int i = 1; i < days; i++) {
			c1.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(finalDf.format(c1.getTime()));
		}
		dates.add(finalDf.format(dt2));

		finaldates.addAll(dates);

		return finaldates;
	}

	public ArrayList<String> getAllBetweenDates(String date1, String date2, String current_format, String format)
			throws ParseException {

		ArrayList<String> finaldates = new ArrayList<String>();
		Set<String> dates = new LinkedHashSet<String>();

		SimpleDateFormat current_df = new SimpleDateFormat(current_format);
		SimpleDateFormat df = new SimpleDateFormat(format);

		Date dt1 = current_df.parse(date1);
		Date dt2 = current_df.parse(date2);

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(dt1);
		c2.setTime(dt2);

		long milliseconds1 = c1.getTimeInMillis();
		long milliseconds2 = c2.getTimeInMillis();

		long days = ((milliseconds2 - milliseconds1) / (1000 * 60 * 60 * 24));

		dates.add(df.format(dt1));

		for (int i = 1; i < days; i++) {
			c1.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(df.format(c1.getTime()));
		}
		dates.add(df.format(dt2));

		finaldates.addAll(dates);

		return finaldates;
	}

	public ArrayList<String> getAllBetweenMonths(Date date1, Date date2, String format) throws ParseException {
		ArrayList<String> months = new ArrayList<String>();

		while (date1.before(date2)) {

			months.add(convertToString(date1, format));
			date1 = dateAddMonth(date1, 1);
		}
		String s2 = convertToString(date2, format);

		if (!months.contains(s2))
			months.add(s2);

		return months;
	}

	/**
	 * @param expiryDate
	 * @param currentDate
	 * @return
	 */
	public int getRemainingDays(Date expiryDate, Date currentDate) {

		Float deltaDays = Float.valueOf(String.valueOf((expiryDate.getTime() - currentDate.getTime())))
				/ (24 * 60 * 60 * 1000);
		return Math.round(deltaDays);
	}

	/**
	 * @param dt
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public String convertDayStartingString(Date dt, String format) throws ParseException {
		DateFormat df = new SimpleDateFormat(format);

		String dat = df.format(dt);

		dat = dat + " 00:00:00";

		return dat;
	}

	/**
	 * @param timeZone
	 * @return
	 */
	public String getTimeZoneOffset(String timeZone) {

		int hour = EXDateUtil.getTZOffSet(timeZone) / 60;
		int minute = EXDateUtil.getTZOffSet(timeZone) % 60;

		String s = new String();
		try {
			if (Integer.signum(hour) == -1) {
				hour = -1 * hour;
				s = "-";
			} else {
				s = "+";
			}
			if (Integer.signum(minute) == -1) {
				minute = -1 * minute;
			}

			if (hour < 10) {
				s = s + "0" + hour;
			} else {
				s = s + hour;
			}

			if (minute > 9) {
				s = s + ":" + minute;
			} else {
				s = s + ":0" + minute;
			}

			return s;
		} catch (Exception e) {

			return "+00:00";
		}

	}

	public static int getTZOffSet(String timeZone) {

		if (TimeZone.getTimeZone(timeZone).inDaylightTime(new Date())) {
			return (TimeZone.getTimeZone(timeZone).getRawOffset() + TimeZone.getTimeZone(timeZone).getDSTSavings())
					/ (1000 * 60);
		} else {
			return TimeZone.getTimeZone(timeZone).getRawOffset() / (1000 * 60);
		}

	}

	/**
	 * @param date
	 * @param format
	 * @param zone
	 * @return
	 */
	public String convertToSolrFormat(String date, String format, String zone) {
		final String DATE_TIME_FORMAT_SOURCE = format;
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);

		final String DATE_TIME_FORMAT_SOLR = "yyyy-MM-dd'T'HH:mm:ss'Z'";

		SimpleDateFormat sdfDest = new SimpleDateFormat(DATE_TIME_FORMAT_SOLR);

		Date specifiedTime = null;

		try {
			specifiedTime = sdfSource.parse(date);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		StringBuffer dt = new StringBuffer();
		dt.append(sdfDest.format(specifiedTime));

		int offset = getTZOffSet(zone);
		if (offset >= 0) {
			dt.append("+");
			dt.append(offset);
		} else {
			dt.append(offset);
		}
		dt.append("MINUTES");

		return dt.toString();

	}

	/**
	 * @param date
	 * @param timeZone
	 * @return
	 * @throws ParseException
	 */
	public String convertToSolrFormat(Date date, String solrFormat, String timeZone) throws ParseException {

		String dt = formatDate(date, solrFormat);

		StringBuffer sb = new StringBuffer();
		sb.append(dt);
		int offset = getTZOffSet(timeZone);
		if (offset >= 0) {
			sb.append("+");
			sb.append(offset);
		} else {
			sb.append(offset);
		}
		sb.append("MINUTES");

		return sb.toString();
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int daysBetween(Calendar startDate, Calendar endDate) {

		Calendar date = (Calendar) startDate.clone();
		int daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}

		return daysBetween;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int daysBetween(Date startDate, Date endDate) {

		Calendar startDateCal = Calendar.getInstance();
		startDateCal.setTime(startDate);

		Calendar endDateCal = Calendar.getInstance();
		endDateCal.setTime(endDate);

		int daysBetween = 0;
		while (startDateCal.before(endDateCal)) {
			startDateCal.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}

		return daysBetween;
	}

	public int minutesBetween(Date startDate, Date endDate) {

		int mins = Long.valueOf((endDate.getTime() - startDate.getTime()) / (60 * 1000)).intValue();

		return mins;
	}

	/**
	 * @param dates
	 * @param sourceFormat
	 * @param destinationFormat
	 * @return
	 */
	public ArrayList<String> formatStringDate(ArrayList<String> dates, String sourceFormat, String destinationFormat) {

		ArrayList<String> finalDates = new ArrayList<String>();

		final String DATE_TIME_FORMAT_SOURCE = sourceFormat;
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);

		final String DATE_TIME_FORMAT_DESTINATION = destinationFormat;

		SimpleDateFormat sdfDest = new SimpleDateFormat(DATE_TIME_FORMAT_DESTINATION);

		Date specifiedTime = null;

		try {
			for (String date : dates) {
				specifiedTime = sdfSource.parse(date);
				finalDates.add(sdfDest.format(specifiedTime));
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}

		return finalDates;

	}

	public Date convToUserTimeZone(String time, String destTZ, String format) {

		Date finalDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault());

		try {
			finalDate = sdf.parse(time);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		return finalDate;
	}

	public Date convDateTimeZone(String time, String sourceTZ, String destTZ, String format) {
		SimpleDateFormat sdfSource = new SimpleDateFormat(format);

		SimpleDateFormat sdfDest = new SimpleDateFormat(format);

		Date specifiedTime = null;

		try {
			if (sourceTZ != null)
				sdfSource.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			else
				sdfSource.setTimeZone(TimeZone.getDefault());
			specifiedTime = sdfSource.parse(time);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (destTZ != null)
			sdfDest.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdfDest.setTimeZone(TimeZone.getDefault());

		Date finalDate = null;

		try {
			finalDate = sdfDest.parse(sdfDest.format(specifiedTime));
		} catch (Exception e) {
			LOGGER.error(e);
		}

		return finalDate;
	}

	/**
	 * @param date1
	 * @param date2
	 * @return
	 */
	public int dayDiffOfStringDates(String date1, String date2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

		Date d1 = null;
		Date d2 = null;
		int diffInDays = 0;
		try {
			d1 = format.parse(date1);
			d2 = format.parse(date2);
			diffInDays = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
		} catch (ParseException e) {
			LOGGER.error(e);
		}

		return diffInDays;
	}

	public String convToUserTimeZoneString(String time, String destTZ, String format) {

		String finalDate = time;
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		if (destTZ != null)
			sdf.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf.setTimeZone(TimeZone.getDefault());

		try {
			Date d = sdf.parse(time);
			finalDate = sdf.format(d);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		return finalDate;
	}

	/**
	 * @param datetime
	 * @return
	 */
	public int extractHourFromSolrTime(String datetime, String timeZone) {
		String sourceTZ = "GMT";
		final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

		SimpleDateFormat sdfDest = new SimpleDateFormat(DATE_TIME_FORMAT);
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT);

		Date specifiedTime = null;

		try {
			sdfSource.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			specifiedTime = sdfSource.parse(datetime);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (timeZone != null)
			sdfDest.setTimeZone(TimeZone.getTimeZone(timeZone));
		else
			sdfDest.setTimeZone(TimeZone.getDefault());

		String convertedTime = sdfDest.format(specifiedTime);

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(convertedTime));
		} catch (ParseException e) {
			LOGGER.error(e);
		}

		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		return hour;
	}

	/**
	 * @param datetime
	 * @return
	 */
	public int extractDayFromDate(String datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat(EXConstants.DATE_YYYY_MM_DD);

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(datetime));
		} catch (ParseException e) {
			LOGGER.error(e);
		}

		int day = calendar.get(Calendar.DAY_OF_WEEK);

		return day;
	}

	/**
	 * @param datetime
	 * @return
	 */
	public String extractDayOfWeek(int dayInteger) {
		String day = null;
		try {
			if (dayInteger == Calendar.SUNDAY) {
				day = "Sunday";
			} else if (dayInteger == Calendar.MONDAY) {
				day = "Monday";
			} else if (dayInteger == Calendar.TUESDAY) {
				day = "Tuesday";
			} else if (dayInteger == Calendar.WEDNESDAY) {
				day = "Wednesday";
			} else if (dayInteger == Calendar.THURSDAY) {
				day = "Thursday";
			} else if (dayInteger == Calendar.FRIDAY) {
				day = "Friday";
			} else if (dayInteger == Calendar.SATURDAY) {
				day = "Saturday";
			} else {
				day = "N/A";
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}

		return day;
	}

	public boolean isValidDate(String dateString) {
		final String DATE_TIME_FORMAT_SOURCE = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);
		try {
			sdfSource.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean isValidDateAPI(String dateString) {
		final String DATE_TIME_FORMAT_SOURCE = "yyyy-MM-dd HH:mm:ss.SSS";
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);
		try {
			sdfSource.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * @param datetime
	 * @return e.g. Wednesday, June 05, 2013
	 */
	public String getFullDateString(String datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat(EXConstants.DATE_YYYY_MM_DD);
		Date date = new Date();
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			LOGGER.error(e);
		}

		String formatStr = new SimpleDateFormat("EEE, d MMM yyyy").format(date);
		return formatStr;
	}

	public HashMap<Integer, String> datesBasedOnDaysSolr(String fromDate, String endDate, String timeZone) {
		HashMap<Integer, String> returnHM = new HashMap<Integer, String>();
		ArrayList<String> dates = new ArrayList<String>();
		int day = 0;
		String solrFromDate = null;
		String solrEndDate = null;
		String value = null;

		try {
			dates = getAllBetweenDates(fromDate.trim(), endDate.trim(), EXConstants.DATE_YYYY_MM_DD_HMS);
			for (String date : dates) {

				day = extractDayFromDate(date);
				solrFromDate = convTimeZoneSolr(date + " 00:00:00", timeZone, "GMT");
				solrEndDate = convTimeZoneSolr(date + " 23:59:59", timeZone, "GMT");
				value = null;
				if (returnHM.containsKey(day)) {
					value = returnHM.get(day) + " OR [" + solrFromDate + " TO " + solrEndDate + "]";
					returnHM.put(day, value);
				} else {
					value = "[" + solrFromDate + " TO " + solrEndDate + "]";
					returnHM.put(day, value);
				}
			}

		} catch (ParseException e) {
			LOGGER.error(e);
		}
		return returnHM;
	}

	public long toEpocTime(String date, String format) {
		long epoch = -1;
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			Date dt = df.parse(date);
			epoch = dt.getTime() / 1000;
		} catch (ParseException e) {
			LOGGER.error(e);
		}

		return epoch;
	}

	public Date fromEpocTime(String epochStr, String format) {
		long epoch = Long.valueOf(epochStr).longValue();
		DateFormat df = new SimpleDateFormat(format, Locale.US);
		Date dt = null;
		try {
			String date = df.format(new Date(epoch));
			dt = df.parse(date);
		} catch (ParseException e) {
			LOGGER.error(e);
		}

		return dt;
	}

	/**
	 * @param time
	 * @return
	 */
	public String convToSolrTime(String time) {
		final String DATE_TIME_FORMAT_SOURCE = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT_SOURCE);

		final String DATE_TIME_FORMAT_SOLR = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdfDest = new SimpleDateFormat(DATE_TIME_FORMAT_SOLR);

		Date specifiedTime = null;

		try {
			sdfSource.setTimeZone(TimeZone.getDefault());
			specifiedTime = sdfSource.parse(time);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		sdfDest.setTimeZone(TimeZone.getDefault());
		time = sdfDest.format(specifiedTime);

		return time;
	}

	/**
	 * @param date
	 * @return
	 */
	public String gmtToString(Date date) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sd.format(date);
	}

	public ArrayList<String> getAllHourBetweenDates(String date1, String date2, String format) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		ArrayList<String> finaldates = new ArrayList<String>();
		Set<String> dates = new LinkedHashSet<String>();

		SimpleDateFormat df2 = new SimpleDateFormat("hh a");

		Date dt1 = df.parse(date1);
		Date dt2 = df.parse(date2);

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(dt1);
		c2.setTime(dt2);

		long milliseconds1 = c1.getTimeInMillis();
		long milliseconds2 = c2.getTimeInMillis();

		long hours = ((milliseconds2 - milliseconds1) / (1000 * 60 * 60));

		for (int i = 1; i < hours; i++) {
			c1.add(Calendar.HOUR, 1);
			dates.add(df2.format(c1.getTime()));
		}
		dates.add(df2.format(c2.getTime()));

		finaldates.addAll(dates);

		return finaldates;

	}

	public String extractHourTime(String datetime, String timeZone) {
		String sourceTZ = "GMT";
		final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

		SimpleDateFormat sdfDest = new SimpleDateFormat("hh a");
		SimpleDateFormat sdfSource = new SimpleDateFormat(DATE_TIME_FORMAT);

		Date specifiedTime = null;

		try {
			sdfSource.setTimeZone(TimeZone.getTimeZone(sourceTZ));
			specifiedTime = sdfSource.parse(datetime);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		if (timeZone != null)
			sdfDest.setTimeZone(TimeZone.getTimeZone(timeZone));
		else
			sdfDest.setTimeZone(TimeZone.getDefault());

		String convertedTime = sdfDest.format(specifiedTime);

		return convertedTime;
	}

	public String getMonth(String dt) {
		String monthDate = null;
		String[] dateArray = dt.split("\\s+");
		if (dateArray[0].equalsIgnoreCase("January"))
			monthDate = dateArray[1] + "-01";
		else if (dateArray[0].equalsIgnoreCase("February"))
			monthDate = dateArray[1] + "-02";
		if (dateArray[0].equalsIgnoreCase("March"))
			monthDate = dateArray[1] + "-03";
		if (dateArray[0].equalsIgnoreCase("April"))
			monthDate = dateArray[1] + "-04";
		if (dateArray[0].equalsIgnoreCase("May"))
			monthDate = dateArray[1] + "-05";
		if (dateArray[0].equalsIgnoreCase("June"))
			monthDate = dateArray[1] + "-06";
		if (dateArray[0].equalsIgnoreCase("July"))
			monthDate = dateArray[1] + "-07";
		if (dateArray[0].equalsIgnoreCase("August"))
			monthDate = dateArray[1] + "-08";
		if (dateArray[0].equalsIgnoreCase("September"))
			monthDate = dateArray[1] + "-09";
		if (dateArray[0].equalsIgnoreCase("October"))
			monthDate = dateArray[1] + "-10";
		if (dateArray[0].equalsIgnoreCase("November"))
			monthDate = dateArray[1] + "-11";
		if (dateArray[0].equalsIgnoreCase("December"))
			monthDate = dateArray[1] + "-12";
		return monthDate;
	}

	public int getMonthsDifference(String startDate, String endDate) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date stDate = null;
		try {
			stDate = df.parse(startDate);
		} catch (ParseException e) {
			LOGGER.error(e);
		}
		Date edDate = null;
		try {
			edDate = df.parse(endDate);
		} catch (ParseException e) {
			LOGGER.error(e);
		}
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(stDate);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(edDate);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = (diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)) + 1;
		return diffMonth;
	}

	public ArrayList<String> getNextMonth(String dt, int monthDiff) {
		ArrayList<String> monthList = new ArrayList<String>();
		String[] dateArray = dt.split("\\s+");
		String month = dateArray[0];
		int year = Integer.parseInt(dateArray[1]);
		for (int i = 1; i <= monthDiff; i++) {

			if (month.equalsIgnoreCase("January")) {
				monthList.add("Jan " + year);
				month = "February";
			} else if (month.equalsIgnoreCase("February")) {
				monthList.add("Feb " + year);
				month = "March";
			} else if (month.equalsIgnoreCase("March")) {
				monthList.add("Mar " + year);
				month = "April";
			} else if (month.equalsIgnoreCase("April")) {
				monthList.add("Apr " + year);
				month = "May";
			} else if (month.equalsIgnoreCase("May")) {
				monthList.add("May " + year);
				month = "June";
			} else if (month.equalsIgnoreCase("June")) {
				monthList.add("Jun " + year);
				month = "July";
			} else if (month.equalsIgnoreCase("July")) {
				monthList.add("Jul " + year);
				month = "August";
			} else if (month.equalsIgnoreCase("August")) {
				monthList.add("Aug " + year);
				month = "September";
			} else if (month.equalsIgnoreCase("September")) {
				monthList.add("Sep " + year);
				month = "October";
			} else if (month.equalsIgnoreCase("October")) {
				monthList.add("Oct " + year);
				month = "November";
			} else if (month.equalsIgnoreCase("November")) {
				monthList.add("Nov " + year);
				month = "December";
			} else if (month.equalsIgnoreCase("December")) {
				monthList.add("Dec " + year);
				month = "January";
				year = year + 1;
			}
		}

		return monthList;
	}

	public String getLastday(String stDate) {
		String[] dateArray = stDate.split("-");
		Calendar calendar = Calendar.getInstance();

		calendar.set(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]) - 1, 1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		Date date = calendar.getTime();
		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		String lastDay = String.valueOf(DATE_FORMAT.format(date));
		return lastDay;
	}

	public ArrayList<String> getAllBetweenDates(Long date1, Long date2, String format) throws ParseException {
		ArrayList<String> finaldates = new ArrayList<String>();
		Set<String> dates = new LinkedHashSet<String>();

		SimpleDateFormat df = new SimpleDateFormat(format);

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTimeInMillis(date1);
		c2.setTimeInMillis(date2);

		long days = ((date2 - date1) / (1000 * 60 * 60 * 24));

		dates.add(df.format(date1));

		for (int i = 1; i < days; i++) {
			c1.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(df.format(c1.getTime()));
		}

		dates.add(df.format(date2));

		finaldates.addAll(dates);

		return finaldates;

	}

	public static String getTime(Long val) {
		if (val > 0) {
			long sec = val / 1000;

			int hours = (int) sec / 3600;
			int remainder = (int) sec - hours * 3600;
			int mins = remainder / 60;
			remainder = remainder - mins * 60;
			int secs = remainder;

			return hours + " hour(s) " + mins + " minute(s) " + secs + " sec(s)";
		} else
			return "NA";

	}

	public static String getElapsedTime(Long diff) {
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;

		StringBuffer sb = new StringBuffer();

		if (diffHours != 0) {
			sb.append(diffHours + " Hour");

		}

		if (diffMinutes != 0) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(diffMinutes + " Minutes");

		}

		if (diffSeconds != 0) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(diffSeconds + " Seconds");

		}
		return sb.toString();

	}

	public static String getHourTime(long sec) {

		if (sec > 0) {

			int hours = (int) sec / 3600;
			int remainder = (int) sec - hours * 3600;
			int mins = remainder / 60;
			remainder = remainder - mins * 60;
			int secs = remainder;

			StringBuffer sb = new StringBuffer();

			if (hours > 0 && hours < 10)
				sb.append("0" + hours + ":");
			else if (hours > 9)
				sb.append(hours + ":");
			else
				sb.append("00:");

			if (mins > 0 && mins < 10)
				sb.append("0" + mins + ":");
			else if (mins > 9)
				sb.append(mins + ":");
			else
				sb.append("00:");

			if (secs > 0 && secs < 10)
				sb.append("0" + secs);
			else if (secs > 9)
				sb.append(secs);
			else
				sb.append("00");

			return sb.toString();
		} else
			return "00:00:00";

	}

	public static String getAgeingTime(Long val) {

		if (val > 0) {
			StringBuffer sb = new StringBuffer();

			long sec = val / 1000;

			int days = (int) sec / (3600 * 24);
			int days_remainder = (int) sec - days * (3600 * 24);
			int hours = (int) days_remainder / 3600;
			int remainder = (int) days_remainder - hours * 3600;
			int mins = remainder / 60;
			int seconds = remainder - mins * 60;

			if (days > 0)
				sb.append(days + " d ");

			if (hours > 0 && hours < 10)
				sb.append("0" + hours + ":");
			else if (hours > 9)
				sb.append(hours + ":");
			else
				sb.append("00:");

			if (mins > 0 && mins < 10)
				sb.append("0" + mins + ":");
			else if (mins > 9)
				sb.append(mins + ":");
			else
				sb.append("00:");

			if (seconds > 0 && seconds < 10)
				sb.append("0" + seconds);
			else if (seconds > 9)
				sb.append(seconds);
			else
				sb.append("00");

			if (sb.length() == 0)
				sb.append("< minute");

			return sb.toString();

		} else if (val == 0) {
			return "< minute";
		} else
			return "NA";

	}

	public static String getAgeingTimeNew(Long val) {

		if (val > 0) {
			StringBuffer sb = new StringBuffer();

			long sec = val / 1000;

			int days = (int) sec / (3600 * 24);

			if (days > 0) {
				sb.append(days + " day");

				return sb.toString();
			}

			int days_remainder = (int) sec - days * (3600 * 24);
			int hours = (int) days_remainder / 3600;

			if (hours > 0) {
				sb.append(hours + " hr");
				return sb.toString();
			}
			int remainder = (int) days_remainder - hours * 3600;
			int mins = remainder / 60;
			if (mins > 0) {
				sb.append(mins + " min");
				return sb.toString();
			}

			if (sb.length() == 0)
				sb.append("< minute");

			return sb.toString();

		} else if (val == 0) {
			return "< minute";
		} else
			return "NA";

	}

	public static String getWorkingTime(Long val) {

		if (val > 0) {
			StringBuffer sb = new StringBuffer();

			long sec = val / 1000;

			int days = (int) sec / (3600 * 24);
			int days_remainder = (int) sec - days * (3600 * 24);
			int hours = (int) days_remainder / 3600;
			int remainder = (int) days_remainder - hours * 3600;
			int mins = remainder / 60;
			remainder = remainder - mins * 60;

			if (days > 0)
				sb.append(days + " day(s) ");

			if (hours > 0)
				sb.append(hours + " hr(s) ");

			if (mins > 0)
				sb.append(mins + " min(s) ");

			if (sb.length() == 0)
				sb.append("less than a minute ");
			return sb.toString();

		} else
			return "NA";

	}

	public String convAMPMDateToHMS(String inputDate) {

		SimpleDateFormat format = new SimpleDateFormat(EXConstants.DATE_YYYY_MM_DD_HM_A);
		Date date = null;
		String dateString = null;

		try {
			date = format.parse(inputDate);
			format = new SimpleDateFormat(EXConstants.DATE_YYYY_MM_DD_HMS);
			dateString = format.format(date);
		} catch (ParseException e) {
			LOGGER.error(e);
		}

		return dateString;
	}

	public String convGMTTimeZoneToDestTZ(String inputDate, String destTZ, String pattern) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);

		if (destTZ != null)
			sdf2.setTimeZone(TimeZone.getTimeZone(destTZ));
		else
			sdf2.setTimeZone(TimeZone.getDefault());

		Date timestamp = sdf.parse(inputDate);
		String output = sdf2.format(timestamp);

		return output;
	}

	public ArrayList<String> last24Hour(String timezone) {
		ArrayList<String> finaldates = new ArrayList<String>();
		try {

			Set<String> dates = new LinkedHashSet<String>();

			SimpleDateFormat df2 = new SimpleDateFormat("HH");

			Date dt2 = convTimeZone(new Date(), timezone);
			Date dt1 = dateAdd(dt2, -1);

			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();

			c1.setTime(dt1);
			c2.setTime(dt2);

			long milliseconds1 = c1.getTimeInMillis();
			long milliseconds2 = c2.getTimeInMillis();

			long hours = ((milliseconds2 - milliseconds1) / (1000 * 60 * 60));

			for (int i = 1; i < hours; i++) {
				c1.add(Calendar.HOUR, 1);
				dates.add(df2.format(c1.getTime()));
			}
			dates.add(df2.format(c2.getTime()));

			finaldates.addAll(dates);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return finaldates;

	}

	public static Time getTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		try {
			Date d1 = format.parse(time);
			Time sql_time = new java.sql.Time(d1.getTime());
			return sql_time;
		} catch (Exception e) {
			LOGGER.error(e);
		}

		return new Time(System.currentTimeMillis());
	}

	public String getFromDate(String dateRange) {
		Date fromDate = null;
		String fromDateStr = null;
		if (dateRange != null) {

			String type = dateRange.split("~")[0];
			String number = dateRange.split("~")[1];
			Date currentDate = new Date();

			if ("d".equalsIgnoreCase(type)) {
				fromDate = dateAdd(currentDate, -Integer.parseInt(number));
			} else if ("w".equalsIgnoreCase(type)) {
				fromDate = dateAdd(currentDate, -Integer.parseInt(number) * 7);
			} else if ("m".equalsIgnoreCase(type)) {
				fromDate = dateAdd(currentDate, -Integer.parseInt(number) * 30);
			} else if ("y".equalsIgnoreCase(type)) {
				fromDate = dateAdd(currentDate, -Integer.parseInt(number) * 365);
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			fromDateStr = df.format(fromDate) + " 00:00:00";
		}
		return fromDateStr;
	}

	public String getEndDate() {
		Date currentDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = df.format(currentDate) + " 23:59:59";
		return endDate;
	}

	public Date addSecondsMinutesAndHours(Date date, int second, int minute, int hour) {
		return DateUtils.addSeconds(DateUtils.addMinutes((DateUtils.addHours(date, hour)), minute), second);
	}

	public Date convertISTtoGMT(Date date) {
		return DateUtils.addSeconds(DateUtils.addMinutes(date, 05), 30);
	}
	
	public Date addHourToDate(Date date) {
		     Calendar calendar1 = Calendar.getInstance();
		    calendar1.setTime(date);
		    calendar1.add(Calendar.HOUR_OF_DAY,5);
		    calendar1.add(Calendar.MINUTE,30);
		   return calendar1.getTime();
	}
	
	public Date addMinusToDate(Date date) {
	     Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(date);
	    calendar1.add(Calendar.HOUR_OF_DAY,-5);
	    calendar1.add(Calendar.MINUTE,-30);
	   return calendar1.getTime();
}
	
	
}
