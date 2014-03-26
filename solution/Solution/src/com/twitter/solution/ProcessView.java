package com.twitter.solution;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class ProcessView extends Activity {

	String url ;
	String z="Progress";
	Bitmap bitmap = null;
	String[]  reId;
	ImageView img1;
	ImageView img2;
	ImageView img3;
	ImageView img4;
	ImageView img5;
	ImageView img6;
	ImageView img7;
	ImageView img8;
	ImageView img9;
	ImageView img10;

		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_process_view);
			Log.i(z, "imageloader initialize");
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
	        ImageLoader.getInstance().init(config);
	        
			 img1 = (ImageView) findViewById(R.id.imageView1);
			 img2 = (ImageView) findViewById(R.id.imageView2);
			 img3 = (ImageView) findViewById(R.id.imageView3);
			 img4 = (ImageView) findViewById(R.id.imageView4);
			 img5 = (ImageView) findViewById(R.id.imageView5);
			 img6 = (ImageView) findViewById(R.id.imageView6);
			 img7 = (ImageView) findViewById(R.id.imageView7);
			 img8 = (ImageView) findViewById(R.id.imageView8);
			 img9 = (ImageView) findViewById(R.id.imageView9);
			 img10 = (ImageView) findViewById(R.id.imageView10);
			
			 try
			 {
			     Log.i(z, "Processing Images");
		
			     //Get url from other activity and make array of urls
			     Intent intent = getIntent();
			     url=getIntent().getStringExtra("KEY");
			     reId=url.split(",");
			
			     //Used Picasso library to convert URL to bitmaps
			     Picasso.with(getBaseContext()).load(reId[0]).transform(new CircleTransform()).into(img1);
			     Picasso.with(getBaseContext()).load(reId[1]).transform(new CircleTransform()).into(img2);
			     Picasso.with(getBaseContext()).load(reId[2]).transform(new CircleTransform()).into(img3);
			     Picasso.with(getBaseContext()).load(reId[3]).transform(new CircleTransform()).into(img4);
			     Picasso.with(getBaseContext()).load(reId[4]).transform(new CircleTransform()).into(img5);
			     Picasso.with(getBaseContext()).load(reId[5]).transform(new CircleTransform()).into(img6);
			     Picasso.with(getBaseContext()).load(reId[6]).transform(new CircleTransform()).into(img7);
			     Picasso.with(getBaseContext()).load(reId[7]).transform(new CircleTransform()).into(img8);
			     Picasso.with(getBaseContext()).load(reId[8]).transform(new CircleTransform()).into(img9);
			     Picasso.with(getBaseContext()).load(reId[9]).transform(new CircleTransform()).into(img10);
		    }
			catch(Exception e)
			{
			
	    	    
	    	    Log.e(ACCESSIBILITY_SERVICE, e.toString());
	    	    
				
			}
		}

		//Class to crop the image received to form a perfect circle
		public class CircleTransform implements Transformation {
		    @Override
		    public Bitmap transform(Bitmap source)
		    {
		        int size = Math.min(source.getWidth(), source.getHeight());

		        int x = (source.getWidth() - size) / 2;
		        int y = (source.getHeight() - size) / 2;

		        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
		        if (squaredBitmap != source) {
		            source.recycle();
		        }

		        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

		        Canvas canvas = new Canvas(bitmap);
		        Paint paint = new Paint();
		        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
		        paint.setShader(shader);
		        paint.setAntiAlias(true);

		        float r = size/2f;
		        canvas.drawCircle(r, r, r, paint);

		        squaredBitmap.recycle();
		        return bitmap;
		    }

		    @Override
		    public String key() {
		        return "circle";
		    }
		}
		
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.process_view, menu);
		return true;
	}

}
