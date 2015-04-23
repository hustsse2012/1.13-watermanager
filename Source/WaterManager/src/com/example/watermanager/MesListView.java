package com.example.watermanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MesListView extends ListView {

	private float orginX,orginY;	//��������
	private float currentX,currentY;	//��ǰ����
	private float apartX,apartY;	//����һ���ƶ�����ľ���
	
	private int itemHeight = 65 ;
	private boolean delete = false;
	
	private DeleteListener deletelistener;
	
	public MesListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MesListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			int temp = getChildCount();
			itemHeight = (temp==0)? itemHeight:getChildAt(0).getHeight();
			orginX = ev.getX();
			orginY = ev.getY();
			
			currentX = orginX;
			currentY = orginY;
			
			if(deletelistener != null)
			{
				deletelistener.getMove(currentX, currentY);
			}
			break;
			
		case MotionEvent.ACTION_MOVE:
			float deltaX = ev.getX(ev.getPointerCount() - 1) - orginX;
			apartX = ev.getX() - currentX;
			apartY = ev.getY() - currentY;
			
			currentX = ev.getX();
			currentY = ev.getY();
			
			//��ָ���������¾��벻��̫�󣬱�֤ˮƽ�ƶ�
			if(apartX < 0 && - 20< apartY && apartY < 20)
			{
				if(itemHeight > apartY && deletelistener != null)
				{
					deletelistener.onFlipping(orginX, orginY, apartX, apartX);
				}
				if(Math.abs(deltaX)>this.getWidth() / 3) //�ƶ�������ȵ�3��֮һ
					delete = true;
				else
					delete = false;
			}
			break;
			
		case MotionEvent.ACTION_UP:
			if (delete &&deletelistener != null)
			{
				deletelistener.restoreView(currentX, currentY, true);
			}
			//�ж��Ƿ�����ƶ�������
			if(!delete)
			{
				if(deletelistener != null)
				{
					deletelistener.restoreView(orginX, orginY, false);
				}
			}
			reset();
			break;
		}		
		return super.onTouchEvent(ev);
	}
	
	public void reset()
	{
		delete = false;
		orginX = -1;
		orginY = -1;
	}
	
	public void setDeletListener(DeleteListener f)
	{
		deletelistener = f;
	}

	public interface DeleteListener
	{
		//��ȡ��ǰ����
		public void getMove(float moveXx,float moveYy);
		//�ƶ�����
		public void onFlipping(float xPosition,float yPosition,float apartX,float apartY);
		//����item����λ���Ƿ�ı�
		public void restoreView(float x,float y,boolean tag);
		
		public void resetView();
	}
	
	public int getItemHight()
	{
		return itemHeight;
	}
}
