package com.shubnikofff.eshop.commons.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface BaseEvent extends Serializable {

	@JsonIgnore
	String getEventName();
}
