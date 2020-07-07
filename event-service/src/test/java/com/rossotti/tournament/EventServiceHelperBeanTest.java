package com.rossotti.tournament;

import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.service.EventServiceHelperBean;
import com.rossotti.tournament.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceHelperBeanTest {

	@InjectMocks
	private EventServiceHelperBean eventServiceHelperBean;

	@Test
	public void validateDatabaseEvent_valid() {
		Event event = new Event();
		event.setEventStatus(EventStatus.Sandbox);
		boolean i = eventServiceHelperBean.validateDatabaseEvent(event);
		Assert.assertTrue(i);
	}

	@Test
	public void validateDatabaseEvent_invalid() {
		Event event = new Event();
		event.setEventStatus(EventStatus.InProgress);
		Assert.assertFalse(eventServiceHelperBean.validateDatabaseEvent(event));
	}

	@Test
	public void validateRequestEvent_valid() {
		Event event = new Event();
		event.setEventStatus(EventStatus.Scheduled);
		Assert.assertTrue(eventServiceHelperBean.validateDatabaseEvent(event));
	}

	@Test
	public void validateRequestEvent_invalid() {
		Event event = new Event();
		event.setEventStatus(EventStatus.InProgress);
		Assert.assertFalse(eventServiceHelperBean.validateDatabaseEvent(event));
	}
}