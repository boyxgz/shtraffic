/**
 * 
 */
package com.surelution.traffic.sh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class Peccancy {
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 违章类型
	 */
	private String type;
	
	/**
	 * 违章时间
	 */
	private Date time;
	
	/**
	 * 道路
	 */
	private String road;
	
	/**
	 * 条款
	 */
	private String term;
	
	/**
	 * 违法内容
	 */
	private String description;
	
	/**
	 * 执行状态
	 */
	private String status;

	public String getType() {
		return type;
	}

	public void setRawType(String type) {
		if("1".equals(type)) {
			this.type = "本市电子监控";
		} else if("2".equals(type)) {
			this.type = "本市违法停车";
		} else if("3".equals(type)) {
			this.type = "外地电子监控";
		} else {
			this.type = "未知";
		}
	}

	public Date getTime() {
		return time;
	}

	public void setRawTime(String time) {
		try {
			this.time = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
