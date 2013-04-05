package com.eyebrow;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CameraTakeActivity extends Activity
{

    private static final int PICTURE_RESULT = 1;
    //private FileOutputStream imageFile;
    private File imageFile;
    private ImageView pictureHolder;
    
    //Paint object to draw stuff
    private Paint tmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint Circle = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        

        
        //The intent here is to capture an image
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        //The file will be in the application's directory/savedImages
        //imageFile = new File(this.getDir("savedImages", MODE_WORLD_WRITEABLE), getFileName());
        
        //When we are running the camera app, we want uri to be of the file
        Uri uri = Uri.fromFile(getTempFile(this));
        
        //Pass the intent some parameters...
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        
        //And we're running the camera app!
        this.startActivityForResult(intent, PICTURE_RESULT);
        
        
    }
    
    //This code runs after the camera app has been completed! Face detection and image manip
    //Will be done here.
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PICTURE_RESULT)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Bundle b = data.getExtras();
                Bitmap pic = (Bitmap) b.get("data");
                
                Bitmap nBitmap = pic.copy(Bitmap.Config.RGB_565, true);
                if(nBitmap != null)
                {
                    TargetView tView = new TargetView(this);
                    setContentView(tView);  
                    LayoutParams l = tView.getLayoutParams();
                    l.height = 320;
                   // l.width = 1000;
                    tView.setLayoutParams(l);
                    
                    tView.setBitmap(nBitmap);
                    if(!tView.setFaces())
                    {
                    	Toast t = Toast.makeText(this, "No Faces Found Try again!", Toast.LENGTH_SHORT);
                    	t.show();
                    	this.finish();
                    }

                    
                    /*ImageView iv = new ImageView(this);
                    
                    iv.setImageBitmap(nBitmap);
                    setContentView(iv);
                    TextView tv = new TextView(this);
                    
                    String str = Integer.toString(k);
                    /*if(getAllFaces[0] != null)
                        str =Float.toString(myFace.confidence());
                    else
                        str = Integer.toString(nBitmap.getWidth());
                    
                    tv.setText(str);
                    setContentView(tv);*/
                
                
                    //Toast.makeText(this, Double.toString(getAllFaces[0].confidence()), Toast.LENGTH_LONG).show();
                }
            
            }
        }
    }

    
    private File getTempFile(Context ctx)
    {
        final File path = new File(Environment.getExternalStorageDirectory(), ctx.getPackageName());
        if(!path.exists())
        {
            path.mkdir();
        }
        
        return new File(path, "image.tmp");
        /*String seedSuffix = Integer.toString((int) System.currentTimeMillis());
        return "NEIMAGE" + seedSuffix.substring(2, 7) + ".png";*/
    }
}

