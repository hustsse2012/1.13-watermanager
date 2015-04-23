package com.example.watermanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.watermanager.MesListView.DeleteListener;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MesFragment extends Fragment {

	private MesListView mes_list;
	private Context context;
	private LinearLayout DeIterm = null;
	private List<Map<String,Object>> iterms = new ArrayList<Map<String,Object>>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context= getActivity().getApplicationContext();
		View activitymes = inflater.inflate(R.layout.activity_mes, container,false);
		mes_list = (MesListView)activitymes.findViewById(R.id.mes_lis);
		Myadapter adapter = new Myadapter();
		
		boolean iswater[] = {true,false,true,false};
		String[] time = {"2015/4/1","2015/4/1","2015/4/1","2015/4/1"};
		String content= "接到武汉市水务集团通知，因配合市政建设施工进行水管道改造，定于2015年4月1日傍晚22时至2日下午18时，洪山珞瑜路、瑜伽湖路、文苑路至光谷三路移动沿线水压下降或无水。届时，华中科技大学个大楼、学生宿舍、食堂、家属区均无水、请学校与各单位和家属做好停水准备。";
		String mescontent[]={content,content,content,content};
		ListIterms Iterms = new ListIterms(iswater,time,mescontent);
		iterms = Iterms.getiterms();
		
		if(mes_list != null)
		{
			mes_list.setAdapter(adapter);
			mes_list.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context,WatermesActivity.class);
					getActivity().startActivity(intent);
				}
				
			});
			
			
			adapter.notifyDataSetChanged();
			mes_list.setDeletListener(new DeleteListener()
			{
				int moveX = 0;
				int moveY = 0;
				
				@Override
				public void getMove(float moveXx, float moveYy) 
				{
					// TODO Auto-generated method stub
					moveY = (int)moveYy;
					moveX = (int)moveXx;
				}

				@Override
				public void onFlipping(float xPosition, float yPosition,
						float apartX, float apartY) {
					// TODO Auto-generated method stub
					int  index = mes_list.pointToPosition((int)xPosition, moveY);
					if(index >= 0)
					{
						int firstVisible = mes_list.getFirstVisiblePosition();
						View v = mes_list.getChildAt(index-firstVisible);
						LinearLayout mlis_item = (LinearLayout)v.findViewById(R.id.mlis_item);
						//临时移动多少像素 apartX 起始位置到终点位置
						int temp = (int)apartX;
						
						if(mlis_item != null && Math.abs(xPosition - moveX)<mlis_item.getWidth()/2)
						{
							moveX = moveX + temp;//计算移动距离
							//像素转换 
							if(Math.abs(moveX)<dp2px(context,mlis_item.getWidth()/3))
							{
								mlis_item.scrollBy(-temp, 0);
							}
						}
					}
				} 

				@Override
				public void restoreView(float x, float y, boolean tag) {
					// TODO Auto-generated method stub
					//获取需要移动的listview项
					int index = mes_list.pointToPosition((int)x, moveY);
					if(index >= 0)
					{
						int firstVisible = mes_list.getFirstVisiblePosition();
						View v = mes_list.getChildAt(index-firstVisible);
						LinearLayout mlis_item = (LinearLayout)v.findViewById(R.id.mlis_item);
						if(tag)
						{
							int list_num = mes_list.getChildCount();
							for(int i = 0; i <list_num ; i++)
							{
								View v2 = mes_list.getChildAt(i);
								LinearLayout mlis_item1 = (LinearLayout)v2.findViewById(R.id.mlis_item);
								if(mlis_item1 != null)
								{
									mlis_item1.scrollTo(0, 0);
								}
							}
							mlis_item.scrollTo(dp2px(context,65), 0);
						}
						else
						{
							if(mlis_item != null)
							{
								mlis_item.scrollTo(0, 0);
							}
						}
					}
					moveY = 0;
				}

				@Override
				public void resetView()
				{
					// TODO Auto-generated method stub
					if(DeIterm!=null)
					{
						DeIterm.scrollTo(0, 0);
						DeIterm = null;
					}
				}
			});
		}
		return activitymes;
	}
	
	public class Myadapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return iterms.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView( final int position, View convertView, ViewGroup parent) 
		{
			// TODO Auto-generated method stub
			convertView = LayoutInflater.from(context).inflate
					(R.layout.mes_list, null);
			
			ImageView	isWater = (ImageView)convertView.findViewById(R.id.iswater);
			TextView	mes		= (TextView)convertView.findViewById(R.id.mesIterm_mes);
			TextView	data	= (TextView)convertView.findViewById(R.id.mesIterm_data);
			TextView	content	= (TextView)convertView.findViewById(R.id.mesIterm_content);
			
			Map<String,Object> iterm = iterms.get(position);
			if((Boolean) iterm.get("iswater"))
			{
				isWater.setImageResource(R.drawable.water);
				mes.setText(R.string.water_mes);
			}
			else
			{
				isWater.setImageResource(R.drawable.elec);
				mes.setText(R.string.elec_mes);
			}
			
			data.setText((CharSequence) iterm.get("time"));
			content.setText((CharSequence) iterm.get("content"));
			
			//MyOnClick myonclick = new MyOnClick(position);
			ImageButton ibt_del = (ImageButton)convertView.findViewById(R.id.ibt_del);
			ibt_del.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					iterms.remove(position);
					notifyDataSetChanged();
					mes_list.postInvalidate();
				}
				
			});
			return convertView;
		}
	}
	
	protected int dp2px(Context context, float dp) {
		// TODO Auto-generated method stub
		final float scale = context.getResources().getDisplayMetrics().density;  
	    return (int)(dp * scale + 0.5f);
	}

}
