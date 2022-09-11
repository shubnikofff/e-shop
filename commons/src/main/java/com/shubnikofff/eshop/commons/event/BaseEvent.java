package com.shubnikofff.eshop.commons.event;

import java.io.Serializable;

abstract public class BaseEvent implements Serializable {

	protected String eventName;

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	abstract public String getEventName();
}
