package net.yoedtos.lequotes.utils;

import java.io.Serializable;

public class LastState implements Serializable {

	private static final long serialVersionUID = -613124973346465800L;
	
	private int lastid;

	public void setLastid(int lastid) {
		this.lastid = lastid;
	}

	public int getLastid() {
		return lastid;
	}
}
