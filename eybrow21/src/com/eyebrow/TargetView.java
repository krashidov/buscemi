package com.eyebrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.view.View;
import android.widget.Toast;

public class TargetView extends View {
	private Bitmap bMap;
	
	private PointF eyesMidPoint = new PointF();
	
	private float eyesDistance;
	
	private FaceDetector arrayFaces;
	
	private final int NUMBER_OF_FACES = 1;
	private final Face getAllFaces[] = new Face[NUMBER_OF_FACES];
	
	private FaceDetector.Face myFace=null;
	
	private float xRatio, yRatio;
	
	private Paint tmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	TargetView(Context c)
	{
		super(c);
		
		tmpPaint.setStyle(Paint.Style.STROKE);
		tmpPaint.setTextAlign(Paint.Align.CENTER); 
		
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setColor(Color.RED);

	}
	
	public void setBitmap(Bitmap b)
	{
		bMap = b.copy(Bitmap.Config.RGB_565, true);
	}
	
	public boolean setFaces()
	{
		if(bMap != null)
		{
			int k;
			arrayFaces = new FaceDetector(bMap.getWidth(), bMap.getHeight(), 1);
			
			k = arrayFaces.findFaces(bMap, getAllFaces);
			
			myFace = getAllFaces[0];
			
			
			if(myFace != null)
			{
				myFace.getMidPoint(eyesMidPoint);
				eyesDistance = myFace.eyesDistance();
			}
			else
			{
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	
	public void onDraw(Canvas c)
	{
		Color color = new Color();
		
		Bitmap transformed = bMap.copy(Bitmap.Config.RGB_565, true);
		
		int xStart = (int)eyesMidPoint.x;
		float yStart = eyesMidPoint.y;
		
		int leftEyebrowX = xStart-(xStart/4);
		int leftEyebrowY = (int) ((int) yStart/1.099);
		
	    float leftEyeX = xStart- ( eyesDistance/2);
	    float rightEyeX = xStart + ( eyesDistance/2);
	    
	    
		if(myFace!= null)
		{
		xRatio = getWidth()*1.0f / bMap.getWidth();
		yRatio = getHeight()*1.0f / bMap.getHeight();
		Bitmap lefteye = BitmapFactory.decodeResource(getResources(), R.drawable.left);
		Bitmap righteye = BitmapFactory.decodeResource(getResources(), R.drawable.right);
		
		float scale =18;
		
		///----------Left Eye-----------------------//
		float calcStepLeftEyeWidth  =  ((float) lefteye.getWidth())/scale;
		float calcStepLeftEyeHeight = ((float) lefteye.getHeight())/scale;
		int leftEyeY = (int) (yStart -  8);
		
		for(float j = 0; j < lefteye.getHeight(); j+= calcStepLeftEyeHeight)
		{
			int lX = (int) leftEyeX - 8;
			for(float i = 0 ; i < lefteye.getWidth();  i+= calcStepLeftEyeWidth)
			{
				int p = lefteye.getPixel((int)i, (int)j);
				if (p != Color.TRANSPARENT)
					bMap.setPixel((int) lX, leftEyeY, p);
				lX++;
			}
			leftEyeY++;
		}
		
		//------------------------------------------//
		float calcStepRightEyeWidth  =  ((float) righteye.getWidth())/scale;
		float calcStepRightEyeHeight = ((float) righteye.getHeight())/scale;
		int rightEyeY = (int) (yStart -  6);
		
		for(float j = 0; j < righteye.getHeight(); j+= calcStepRightEyeHeight)
		{
			int rX = (int) rightEyeX - 8;
			for(float i = 0 ; i < righteye.getWidth();  i+= calcStepRightEyeWidth)
			{
				int p = righteye.getPixel((int)i, (int)j);
				if (p != Color.TRANSPARENT)
					bMap.setPixel((int) rX, rightEyeY, p);
				rX++;
			}
			rightEyeY++;
		}
		//bMap.setPixel((int) leftEyeX, (int) yStart, 255);
		//bMap.setPixel((int) rightEyeX, (int) yStart, 255);
		
		c.drawBitmap(bMap, null, new Rect(0,0,getWidth(),getHeight()),tmpPaint);


		//c.drawBitmap(lefteye, (float) (leftEyeX*3), (float) (yStart*2.5), tmpPaint);
		//c.drawBitmap(righteye, (float) (rightEyeX*3), (float) (yStart*2.5), tmpPaint);
		
		//c.drawText(Float.toString(leftEyeX), 10, 10, circlePaint);

		/*c.drawText(Integer.toString(c.getWidth()), 30, 30, circlePaint);
		c.drawText(Integer.toString(bMap.getWidth()), 60, 30, circlePaint);
		c.drawText(Integer.toString(c.getHeight()), 90, 30, circlePaint);
		c.drawText(Integer.toString(bMap.getHeight()), 120, 30, circlePaint);*/


		
		circlePaint.setStrokeWidth(eyesDistance / 6);
		//c.drawCircle(eyesMidPoint.x*xRatio, eyesMidPoint.y*yRatio, eyesDistance*2, circlePaint);
		

	
		}
		
		

	}
	
	public int getAverage(int color)
	{
		return (Color.red(color)+Color.blue(color)+Color.green(color))/3;
	}
	

}
