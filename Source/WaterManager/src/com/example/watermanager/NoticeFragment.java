package com.example.watermanager;

import java.util.ArrayList;
import java.util.List;

import com.example.watermanager.MesListView.DeleteListener;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

@SuppressLint("ViewHolder")
public class NoticeFragment extends Fragment {
	
	private MesListView noticelist;
	private Context context;
	private ArrayList<String> iterm = new ArrayList<String>();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		View fragmentnotice = inflater.inflate(R.layout.fragment_notice, container,false);
		noticelist = (MesListView)fragmentnotice.findViewById(R.id.notice_lis);
		for(int i =0;i<4;i++)
			iterm.add("true");
		BaseAdapter adapter = new BaseAdapter()
		{
			public int getCount() {
				return iterm.size();
			}
			public Object getItem(int position) {
				return null;
			}
			@Override
			public long getItemId(int position) {
				return 0;
			}
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(getActivity().getApplicationContext()).inflate  
			            (R.layout.notice_list,parent,false);
				
				ImageButton delet = (ImageButton)convertView.findViewById(R.id.nts_Ibt_del);
				delet.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						iterm.remove(position);
						notifyDataSetChanged();
						noticelist.postInvalidate();
					}
					
				});
				return convertView;
			}
		};
		

		if(noticelist !=null)
		{
			noticelist.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			noticelist.setDeletListener(new DeleteListener()
			{
				int moveX = 0;
				int moveY = 0;
				
				@Override
				public void getMove(float moveXx, float moveYy) 
				{
					moveY = (int)moveYy;
					moveX = (int)moveXx;
				}

				@Override
				public void onFlipping(float xPosition, float yPosition,
						float apartX, float apartY) {
					int  index = noticelist.pointToPosition((int)xPosition, moveY);
					if(index >= 0)
					{
						int firstVisible = noticelist.getFirstVisiblePosition();
						View v = noticelist.getChildAt(index-firstVisible);
						LinearLayout ntlis_item = (LinearLayout)v.findViewById(R.id.ntlist_iterm);
						//临时移动多少像素 apartX 起始位置到终点位置
						int temp = (int)apartX;
						
						if(ntlis_item != null && Math.abs(xPosition - moveX)<ntlis_item.getWidth()/2)
						{
							moveX = moveX + temp;//计算移动距离
							//像素转换 
							if(Math.abs(moveX)<dp2px(context,ntlis_item.getWidth()/3))
							{
								ntlis_item.scrollBy(-temp, 0);
							}
						}
					}
				} 

				@Override
				public void restoreView(float x, float y, boolean tag) {
					//获取需要移动的listview项
					int index = noticelist.pointToPosition((int)x, moveY);
					if(index >= 0)
					{
						int firstVisible = noticelist.getFirstVisiblePosition();
						View v = noticelist.getChildAt(index-firstVisible);
						LinearLayout ntlis_item = (LinearLayout)v.findViewById(R.id.ntlist_iterm);
						if(tag)
						{
							int list_num = noticelist.getChildCount();
							for(int i = 0; i <list_num ; i++)
							{
								View v2 = noticelist.getChildAt(i);
								LinearLayout ntlis_item1 = (LinearLayout)v2.findViewById(R.id.ntlist_iterm);
								if(ntlis_item1 != null)
								{
									ntlis_item1.scrollTo(0, 0);
								}
							}
							ntlis_item.scrollTo(dp2px(context,65), 0);
						}
						else
						{
							if(ntlis_item != null)
							{
								ntlis_item.scrollTo(0, 0);
							}
						}
					}
					moveY = 0;
				}

				@Override
				public void resetView()
				{
				}
			});
		}
		return fragmentnotice;
	}

		
		
	protected int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;  
	    return (int)(dp * scale + 0.5f);
	}
}
