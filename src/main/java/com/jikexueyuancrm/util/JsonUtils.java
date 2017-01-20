package com.jikexueyuancrm.util;

import java.util.Iterator;

import javax.sound.midi.MidiDevice.Info;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	
	
	private static Logger log = Logger.getLogger(JsonUtils.class);
	//遍历解析JSONObject
		public static void decodeJSONObject(JSONObject jsonObject){
			Iterator<String> keys = jsonObject.keys();
			JSONObject jo = null;
			JSONArray ja = null;
			Object o;
			String key;
			while (keys.hasNext()) {
				key = keys.next();
				
				o = jsonObject.get(key);
				
	           if(key.equals("general_msg_list")){
					
					log.info(o.getClass());
				}
	           
	           
				if (o instanceof JSONObject) {
					jo = (JSONObject) o;
					log.info("JSONObject");
					log.info("key:"+key);
					if (jo.keySet().size() > 0) {
						decodeJSONObject(jo);
					} else {
						
					}
				} else if (o instanceof JSONArray) {
					ja = (JSONArray) o;
					log.info("JSONArray");
					log.info("key:"+key);
					for (int i = 0; i < ja.size(); i++) {
						decodeJSONObject(ja.getJSONObject(i));
					}
				} else {
					
				  log.info("key:"+key+";"+"value:"+o)  ;
				}
			
			}
			}
}
