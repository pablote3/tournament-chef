package com.rossotti.tournament.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateTimeConverter {

	private static final DateTimeFormatter dateNakedFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	
	static public String getStringDate(LocalDate date) {
		return date.format(dateFormatter);
	}

	static public String getStringDate(LocalDateTime dateTime) {
		return dateTime.format(dateFormatter);
	}

	static public String getStringDateNaked(LocalDateTime dateTime) {
		return dateTime.format(dateNakedFormatter);
	}

	static public String getStringDateNaked(LocalDate date) {
		return date.format(dateNakedFormatter);
	}

	static public String getStringDateTime(LocalDateTime dateTime) {
		return dateTime.format(dateTimeFormatter);
	}

	static public LocalDate getLocalDate(String strDate) {
		return LocalDate.parse(strDate);
	}

	static public LocalDate getLocalDate(LocalDateTime dateTime) {
		return dateTime.toLocalDate();
	}

	static public LocalDateTime getLocalDateTime(String strDateTime) {
		return LocalDateTime.parse(strDateTime);
	}

	static public LocalDateTime getLocalDateTime(ZonedDateTime zonedDateTime) { return LocalDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.of("US/Eastern")); }

	static public LocalDate getLocalDate(ZonedDateTime zonedDateTime) { return LocalDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.of("US/Eastern")).toLocalDate(); }

	static public LocalDateTime getLocalDateTimeMin(LocalDate localDate) {
		String stringDate = DateTimeConverter.getStringDate(localDate);
		return LocalDateTime.parse(stringDate + "T00:00");
	}

	static public LocalDateTime getLocalDateTimeMax(LocalDate localDate) {
		String stringDate = DateTimeConverter.getStringDate(localDate);
		return LocalDateTime.parse(stringDate + "T23:59");
	}

	static public boolean isDate(String strDate)  {
		try {
			LocalDate.parse(strDate);
			return true;
		}
		catch (DateTimeParseException e) {
			return false;
		}
	}

	static public LocalDate getDateMinusOneDay(LocalDate date) {
		return date.minusDays(1);
	}

	static public int getDaysBetweenTwoDateTimes(LocalDateTime minDate, LocalDateTime maxDate) {
		if(minDate != null) {
			int days = (int) ChronoUnit.DAYS.between(minDate, maxDate);
			if (days < 30) {
				return days;
			} else {
				return 0;
			}
		}
		else {
			return 0;
		}
	}
}