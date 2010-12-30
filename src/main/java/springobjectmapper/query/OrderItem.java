package springobjectmapper.query;

import java.io.Serializable;

public class OrderItem implements Serializable {
	private final String key;
	private boolean descending = false;

	public OrderItem(String key) {
		this.key = key;
	}

	public void setDescending(boolean value) {
		descending = value;
	}

	public String key() {
		return key;
	}

	public boolean descending() {
		return descending;
	}
}