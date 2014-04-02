package org.esgi.proxy.request;

import org.esgi.proxy.interfaces.ICookie;

public class Cookie implements ICookie{

	String name;
	String value;;
	
	
	public Cookie(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

}
