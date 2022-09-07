package com.shubnikofff.eshop.commons.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BaseEvent implements Serializable {

	protected String __name__;

	public BaseEvent(String name) {
		__name__ = name;
	}

	@JsonProperty("__name__")
	public String getEventName() {
		return __name__;
	}

	@JsonProperty("__name__")
	public void setEventName(String name) {
		this.__name__ = name;
	}
}
