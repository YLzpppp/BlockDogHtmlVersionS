package lzp.entity;

public class Account {

	private Integer id;
	private String name;
	private String account;
	private String password;
	private String ip;
	private String lasttime;
	
	public Account(Integer id,String name,String account,String password,String ip,String lasttime) {
		this.id = id;
		this.name = name;
		this.account = account;
		this.password = password;
		this.ip = ip;
		this.lasttime = lasttime;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	
	
}
