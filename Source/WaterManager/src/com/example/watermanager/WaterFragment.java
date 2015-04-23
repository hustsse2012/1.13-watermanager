package com.example.watermanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WaterFragment extends Fragment 
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		View activitywater = inflater.inflate(R.layout.activity_water, container, false);
		return activitywater;
	}

}
