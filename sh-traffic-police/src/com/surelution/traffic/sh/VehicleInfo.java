/**
 * 
 */
package com.surelution.traffic.sh;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class VehicleInfo implements Serializable {

	private static final long serialVersionUID = -563649934321997647L;
	
	//http://mggs.tencenthouse.net/sht/api/dzjk.php?vehnum=%E6%B5%99F62339&type1=02&fdjh=VQ23073361&callback=checksub2&_=1433053039209
	private static final String baseUrl = "http://mggs.tencenthouse.net/sht/api/dzjk.php?vehnum=";

	private String vehicleNo;
	private String engineNo;
	private String vehicleType;

	public VehicleInfo(String vehicleNo, String engineNo, String vehicleType) {
		this.vehicleNo = vehicleNo;
		this.engineNo = engineNo;
		this.vehicleType = vehicleType;
	}
	
	public List<Peccancy> query() {
		List<Peccancy> ps = new ArrayList<Peccancy>();
		StringBuilder sb = new StringBuilder(baseUrl);
		sb.append(vehicleNo);
		sb.append("&type1=");
		sb.append(vehicleType);
		sb.append("&fdjh=");
		sb.append(engineNo);
		sb.append("&callback=checksub2");
		
		HttpClient hc = HttpClients.createDefault();
		HttpGet get = new HttpGet(sb.toString());
		try {
			HttpResponse response = hc.execute(get);
			InputStream is = response.getEntity().getContent();
			List<String> lines = IOUtils.readLines(is, "utf-8");
			sb = new StringBuilder();
			for(String line : lines) {
				sb.append(line);
			}
			String content = sb.substring("checksub2(".length(), sb.length() - 2);
			System.out.println(content);
			
			try {
				JSONArray array = new JSONArray(content);
				for(int i = 0; i < array.length(); i++) {
					JSONObject o = array.getJSONObject(i);

					//为0时是没有返回值
					String rawType = o.getString("fenlei");
					if(!"0".equals(rawType)) {
						Peccancy p = new Peccancy();
						p.setDescription(o.getString("wznr"));
						p.setRoad(o.getString("roadnum"));
						p.setStatus(o.getString("clbj"));
						p.setTerm(o.getString("wftk"));
						p.setRawTime(o.getString("violatetime"));
						p.setRawType(rawType);
						p.setPoliceOffice(o.getString("cjjg"));
						ps.add(p);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ps;
	}

	public static void main(String[] args) {
		//"沪mt8357", "8207633", "02"
		VehicleInfo vi = new VehicleInfo("沪cps419", "110531082", "02");
		List<Peccancy> ps = vi.query();
		for(Peccancy p : ps) {
			System.out.println(p.getDescription());
			System.out.println(p.getTime());
		}
	}
}
