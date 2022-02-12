package net.yoedtos.lequotes.persistence;


public class DbConfig {
	
	private String driver = "org.h2.Driver";
	private String url = "jdbc:h2:db/dbase";
	private String user = "admuser";
	private String password = "xO85kGwRP";
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
