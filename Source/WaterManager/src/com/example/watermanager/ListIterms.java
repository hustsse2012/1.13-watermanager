package com.example.watermanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListIterms {
	private List<Map<String,Object>> iterms = new ArrayList<Map<String,Object>>();
	private boolean[] iswater;
	private String[] mestime;
	private String[] mescontent;
	
	public ListIterms(boolean isWater[],String[] mesTime, String[] mesContent)
	{
		iswater = new boolean[isWater.length];
		mestime = new String[mesTime.length];
		mescontent = new String[mesContent.length];
		
		for(int i=0;i<isWater.length;i++)
			iswater[i] = isWater[i];
		for(int i=0;i<mesTime.length;i++)
			mestime[i]=mesTime[i];
		for(int i=0;i<mesContent.length;i++)
			mescontent[i] = mesContent[i];
		for(int i=0;i<mesContent.length;i++)
		{
			Map<String,Object> iterm = new HashMap<String,Object>();
			iterm.put("iswater", iswater[i]);
			iterm.put("time", mestime[i]);
			iterm.put("content", mescontent[i]);
			iterms.add(iterm);
		}
	}
	
	public List<Map<String,Object>> getiterms()
	{
		return iterms;
	}
}
