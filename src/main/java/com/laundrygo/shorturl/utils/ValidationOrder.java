package com.laundrygo.shorturl.utils;

import javax.validation.GroupSequence;

public interface ValidationOrder {

	interface First {
	}

	interface Second {
	}

	@GroupSequence({First.class, Second.class})
	interface All {
	}
}
