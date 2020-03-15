package com.rossotti.tournament.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class DateTimeConverterTest {

	@Test
	public void getStringDate_FromDate() {
		String date = DateTimeConverter.getStringDate(LocalDate.of(2013, 3, 30));
		Assert.assertEquals("2013-03-30", date);
	}

	@Test
	public void getStringDate_FromDateTime() {
		String dateTime = DateTimeConverter.getStringDate(LocalDateTime.of(2013, 3, 30, 10, 30));
		Assert.assertEquals("2013-03-30", dateTime);
	}

	@Test
	public void getStringDateTime() {
		String dateTime = DateTimeConverter.getStringDateTime(LocalDateTime.of(2013, 3, 30, 10, 30));
		Assert.assertEquals("2013-03-30T10:30", dateTime);
	}

	@Test
	public void getStringDateNaked_FromDateTime() {
		String dateTime = DateTimeConverter.getStringDateNaked(LocalDateTime.of(2013, 3, 30, 10, 30));
		Assert.assertEquals("20130330", dateTime);
	}

	@Test
	public void getStringDateNaked_FromDate() {
		String date = DateTimeConverter.getStringDateNaked(LocalDate.of(2013, 3, 30));
		Assert.assertEquals("20130330", date);
	}

	@Test
	public void getLocalDate_FromString() {
		LocalDate date = DateTimeConverter.getLocalDate("2014-06-30");
		Assert.assertEquals(LocalDate.of(2014, 6, 30), date);
	}

	@Test
	public void getLocalDate_FromLocalDateTime() {
		LocalDateTime dateTime = DateTimeConverter.getLocalDateTime("2014-06-30T10:30");
		Assert.assertEquals(LocalDate.of(2014, 6, 30), DateTimeConverter.getLocalDate(dateTime));
	}

	@Test
	public void getLocalDateTime_FromLocalDateTime() {
		LocalDateTime dateTime = DateTimeConverter.getLocalDateTime("2014-06-30T10:30");
		Assert.assertEquals(LocalDateTime.of(2014, 6, 30, 10, 30), dateTime);
	}

	@Test
	public void getLocalDateTime_FromZonedDateTime() {
		LocalDateTime dateTime = DateTimeConverter.getLocalDateTime(ZonedDateTime.parse("2015-11-29T18:00:00-05:00"));
		Assert.assertEquals(LocalDateTime.of(2015, 11, 29, 18, 0), dateTime);
	}

	@Test
	public void getLocalDate_FromZonedDateTime() {
		LocalDate date = DateTimeConverter.getLocalDate(ZonedDateTime.parse("2015-11-29T18:00:00-05:00"));
		Assert.assertEquals(LocalDate.of(2015, 11, 29), date);
	}
	
	@Test
	public void getLocalDateTimeMin() {
		LocalDateTime dateTime = DateTimeConverter.getLocalDateTimeMin(LocalDate.of(2014, 6, 30));
		Assert.assertEquals(LocalDateTime.of(2014, 6, 30, 0, 0), dateTime);
	}

	@Test
	public void getLocalDateTimeMax() {
		LocalDateTime dateTime = DateTimeConverter.getLocalDateTimeMax(LocalDate.of(2014, 6, 30));
		Assert.assertEquals(LocalDateTime.of(2014, 6, 30, 23, 59), dateTime);
	}

	@Test
	public void calculateDateDiff_23Hours() {
		LocalDateTime minDate = LocalDateTime.of(2013, 3, 31, 19, 0);
		LocalDateTime maxDate = LocalDateTime.of(2013, 4, 1, 18, 0);
		int days = DateTimeConverter.getDaysBetweenTwoDateTimes(minDate, maxDate);
		Assert.assertEquals(0, days);
	}

	@Test
	public void calculateDateDiff_25Hours() {
		LocalDateTime minDate = LocalDateTime.of(2013, 3, 31, 19, 0);
		LocalDateTime maxDate = LocalDateTime.of(2013, 4, 1, 20, 0);
		int days = DateTimeConverter.getDaysBetweenTwoDateTimes(minDate, maxDate);
		Assert.assertEquals(1, days);
	}

	@Test
	public void calculateDateDiff_Over30Days() {
		LocalDateTime minDate = LocalDateTime.of(2013, 3, 31, 19, 0);
		LocalDateTime maxDate = LocalDateTime.of(2013, 6, 1, 20, 0);
		int days = DateTimeConverter.getDaysBetweenTwoDateTimes(minDate, maxDate);
		Assert.assertEquals(0, days);
	}

	@Test
	public void calculateDateDiff_NullMinDate() {
		LocalDateTime maxDate = LocalDateTime.of(2013, 6, 1, 20, 0);
		int days = DateTimeConverter.getDaysBetweenTwoDateTimes(null, maxDate);
		Assert.assertEquals(0, days);
	}

	@Test
	public void createDateMinusOneDay_EndOfMonth() {
		LocalDate date = DateTimeConverter.getDateMinusOneDay(LocalDate.of(2013, 6, 30));
		Assert.assertEquals(LocalDate.of(2013, 6, 29), date);
	}
	@Test
	public void createDateMinusOneDay_BeginningOfMonth() {
		LocalDate date = DateTimeConverter.getDateMinusOneDay(LocalDate.of(2013, 7, 1));
		Assert.assertEquals(LocalDate.of(2013, 6, 30), date);
	}
}
