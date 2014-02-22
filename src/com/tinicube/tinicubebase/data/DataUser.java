package com.tinicube.tinicubebase.data;

import org.json.JSONObject;

public class DataUser extends BaseData {

	public DataUser(JSONObject jsonObject) {
		super(jsonObject);
	}
	
	public DataUser(){
		super();
	}
	
	public int getId(){
		return getInt("id");
	}
	
	public String getUsername(){
		return getString("username");
	}
	
	public String getName(){
		return getString("name");
	}
	
	public String getType(){
		return getString("type");
	}
	
	public String getNickname(){
		return getString("nickname");
	}
	
	public String getGender(){
		return getString("gender");
	}
	
	public String getTel(){
		return getString("tel");
	}
	
	public String getCreatedDate(){
		return getString("created");
	}

}
