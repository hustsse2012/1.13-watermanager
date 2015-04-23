package com.example.watermanager;

import android.support.v7.app.ActionBarActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private MesFragment 	mesfragment;
	private WaterFragment 	waterfragment;
	private NoticeFragment  noticefragment;
	
	private ImageButton 	bt_mes;
	private ImageButton 	bt_water;
	private ImageButton 	bt_set;
	private ImageButton		bt_bell;
	
	private FragmentManager 	fragmentmanager;
	private FragmentTransaction transaction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		fragmentmanager = getFragmentManager();
		setTable(2);
	}
	
	private void setTable(int index)
	{
		//初始化
		transaction = fragmentmanager.beginTransaction();
		clearselection(); 			//清除选中状态
		hideFragment();				//隐藏界面
		
		switch (index)
		{
		case 1:
			bt_mes.setEnabled(false);
			if(mesfragment != null)
				transaction.show(mesfragment);
			else
			{
				mesfragment = new MesFragment();
				transaction.add(R.id.content, mesfragment);
			}
			break;
		case 2:
			bt_water.setEnabled(false);
			if(waterfragment != null)
				transaction.show(waterfragment);
			else
			{
				waterfragment = new WaterFragment();
				transaction.add(R.id.content, waterfragment);
			}
			break;
		case 3:
			bt_bell.setEnabled(false);
			if(noticefragment != null)
			{
				transaction.show(noticefragment);
			}
			else
			{
				noticefragment = new NoticeFragment();
				transaction.add(R.id.content,noticefragment);
			}
		default:break;
		}
		transaction.commit();
	}
	
	private void clearselection()
	{
		bt_mes.setEnabled(true);
		bt_water.setEnabled(true);
		bt_set.setEnabled(true);
		bt_bell.setEnabled(true);
	}
	
	private void hideFragment()
	{
		if(mesfragment != null)
			transaction.hide(mesfragment);
		if(waterfragment != null)
			transaction.hide(waterfragment);
		if(noticefragment != null)
			transaction.hide(noticefragment);
	}
	
	public void initView()
	{
		bt_mes = (ImageButton)findViewById(R.id.Bt_Mes);
		bt_water = (ImageButton)findViewById(R.id.Bt_Water);
		bt_set = (ImageButton)findViewById(R.id.Bt_Set);
		
		bt_bell = (ImageButton)findViewById(R.id.ibt_bell);
		
		bt_mes.setOnClickListener(this);
		bt_water.setOnClickListener(this);
		bt_set.setOnClickListener(this);
		bt_bell.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{  
        case R.id.Bt_Mes:  
            // 当点击了活动tab时，选中第1个tab  
        	setTable(1);
            break;
        case R.id.Bt_Water:
        	setTable(2);
        	break;
        case R.id.ibt_bell:
        	setTable(3);
        	break;
        case R.id.Bt_Set:
        	Toast.makeText(getApplicationContext(), "此功能暂不支持，请等待后续版本", Toast.LENGTH_SHORT).show();
        default:  
            break;  
        }  
	}
}
