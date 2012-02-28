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
	
	public void setFaces()
	{
		if(bMap != null)
		{
			int k;
			arrayFaces = new FaceDetector(bMap.getWidth(), bMap.getHeight(), 1);
			
			k = arrayFaces.findFaces(bMap, getAllFaces);
			
			myFace = getAllFaces[0];
			
			
			
			myFace.getMidPoint(eyesMidPoint);
			
			eyesDistance = myFace.eyesDistance();
		}
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
	    
	    
	    /*int averageSkinColor = 0;
	    
	    for(int j = 0; j < 5; j++)
	    {
	    	for(int i = 0; i < 5; i++)
	    	{
	    		averageSkinColor += getAverage(bMap.getPixel(xStart-2+j, yStart-10 - i));
	    	}
	    }
	    
	    averageSkinColor /=25;
		for(int x = 0; x < transformed.getWidth(); x++){
			for(int y = 0; y < transformed.getHeight(); y++)
			{
				int average = (color.red(transformed.getPixel(x, y)) + color.green(transformed.getPixel(x, y))+ color.blue(transformed.getPixel(x, y)))/3;
				if(average - averageSkinColor < -65)
					transformed.setPixel(x,y, Color.BLACK);
				else
					transformed.setPixel(x, y, Color.WHITE);
			}}
		


		transformed.setPixel(leftEyeX, yStart, 255);
		transformed.setPixel(rightEyeX, yStart, 255);
		transformed.setPixel(xStart, yStart-10, averageSkinColor);*/


		
		if(myFace!= null)
		{
		xRatio = getWidth()*1.0f / bMap.getWidth();
		yRatio = getHeight()*1.0f / bMap.getHeight();
		
		bMap.setPixel((int) leftEyeX, (int) yStart, 255);
		bMap.setPixel((int) rightEyeX, (int) yStart, 255);
		
		c.drawBitmap(bMap, null, new Rect(0,0,getWidth(),getHeight()),tmpPaint);
		Bitmap lefteye = BitmapFactory.decodeResource(getResources(), R.drawable.left);
		Bitmap righteye = BitmapFactory.decodeResource(getResources(), R.drawable.right);

		c.drawBitmap(lefteye, (float) (leftEyeX*(c.getHeight()/bMap.getHeight())), (float) (yStart*(c.getHeight()/bMap.getHeight())), tmpPaint);
		c.drawBitmap(righteye, (float) (c.getWidth()-(rightEyeX *3)), (float) (c.getHeight() - (yStart*6.2)), tmpPaint);
		
		c.drawText(Float.toString(leftEyeX), 10, 10, circlePaint);

		c.drawText(Integer.toString(c.getWidth()), 30, 30, circlePaint);
		c.drawText(Integer.toString(bMap.getWidth()), 60, 30, circlePaint);
		c.drawText(Integer.toString(c.getHeight()), 90, 30, circlePaint);
		c.drawText(Integer.toString(bMap.getHeight()), 120, 30, circlePaint);


		
		circlePaint.setStrokeWidth(eyesDistance / 6);
		//c.drawCircle(eyesMidPoint.x*xRatio, eyesMidPoint.y*yRatio, eyesDistance*2, circlePaint);
		

	
		}
		
		

	}
	
	public int getAverage(int color)
	{
		return (Color.red(color)+Color.blue(color)+Color.green(color))/3;
	}
	

}
