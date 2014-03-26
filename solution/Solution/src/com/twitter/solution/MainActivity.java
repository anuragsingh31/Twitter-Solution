package com.twitter.solution;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
public class MainActivity extends Activity  {
	  
	  ImageButton iB;
	  TextView text,text2;
	  ImageView image;
	  Button button1;
      int check=0;
      Boolean isInternetPresent= null;
      ConnectionDetector cd;
      @Override
	    public void onCreate(Bundle savedInstanceState) 
         {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        Log.e(ALARM_SERVICE, "Starting");
	         image=(ImageView)findViewById(R.id.imageView1);
	     
	        iB = (ImageButton)findViewById(R.id.imageButton1);
	        cd = new ConnectionDetector(getApplicationContext());
	        
	        
	        
	        		
	        		
	      iB.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 check=1;
	            	 isInternetPresent=cd.isConnectingToInternet();
	            	 if (isInternetPresent) {
	            		  new Process().execute();//Everything happens in Asynctask
	                     
	                  } else {
	                      // Internet connection is not present
	                     
	                      showAlertDialog(MainActivity.this, "No Internet Connection",
	                              "You don't have internet connection.", false);
	                  }
	            	 
	             }
	         });
	        
	        button1 = (Button)findViewById(R.id.button1);
	        button1.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 check=2;
	            	 isInternetPresent=cd.isConnectingToInternet();
	            	 if (isInternetPresent) {
	            		  new Process().execute();//Everything happens in Asynctask
	                     
	                  } else {
	                      // Internet connection is not present
	                     
	                      showAlertDialog(MainActivity.this, "No Internet Connection",
	                              "You don't have internet connection.", false);
	                  }
	            	 
	             }
	         });
	  	
	     }
	   
	    
// where all the magic happens !!...
	    
	     
	     class Process extends AsyncTask<Void,Integer,String>
	{

	 		String s,tt;
	 		String[] arrangedID;
	 		String a="progress";
	 		Integer follower;
	 		User u,uk;
	 		String url;
	    	ProgressDialog dialog;
	    	
	    	protected void onPreExecute()
	    	{ 
	    	    dialog = new ProgressDialog(MainActivity.this);
	    		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		    	dialog.setMax(100);
	    		dialog.show();
	    	}
	    	
		    @Override
			protected void onProgressUpdate(Integer... values) 
		    {
		       dialog.incrementProgressBy(values[0]);
			}

		    //Everything happens here
	        @Override
			protected String doInBackground(Void... params) 
	        {
	        	
		
			       publishProgress(5);
		
		           //Authenticate to twitter using twitter4j library
		           ConfigurationBuilder cb = new ConfigurationBuilder();
	               cb.setDebugEnabled(true)
	               .setOAuthConsumerKey(Const.CONSUMER_KEY)
	               .setOAuthConsumerSecret(
	                     Const.CONSUMER_SECRET)
	               .setOAuthAccessToken(
	                     Const.ACCESS_TOKEN)
	               .setOAuthAccessTokenSecret(
	                     Const.ACCESS_SECRET);
	                Log.i(a, "Starting");
	                publishProgress(5);
	                TwitterFactory tf = new TwitterFactory(cb.build());
	                publishProgress(5);
	                Twitter twitter = tf.getInstance();
	        
	                //get all the tweets from the timeline
	        try {
	             List<twitter4j.Status> statuses;
	             String user;
	             IDs ki = null;
	             user = "BillGates";
	             Log.i(a, "getting timeline");
	              
	             //get latest tweet
	             publishProgress(5);
	             statuses = twitter.getUserTimeline(user);
	             Log.i(a, "got timeine");
	             twitter4j.Status status =null;
	             Log.i("Status Count", statuses.size() + " Feeds");
	
	             //get  the first status
	             status = statuses.get(0);
	             tt=status.getText();
	             publishProgress(5);
	 
	             //get id of the status
	             long l= status.getId(); 
	             String strLong = Long.toString(l);
	                  
	             //get retweeters id
	             Log.i(a, "getting retweeters");
	             
	             //Check if image button pressed
	             if (check==1) 
	             {
	    	         ki =twitter.getRetweeterIds(444519106067595264L,11, -1);
	    	     }
	           
	             //Check if latest tweet button is pressed
	    	     else if (check==2) 
	    	     {
	    	    	 ki =twitter.getRetweeterIds(l,11, -1);
	    		 }
	      
	      
	     Log.i(a, "creating array of ids");
		 long[] id=ki.getIDs();
		 
		 //for every retweeter id, get followers count and put in treemap
		 Log.i(a, "creating treemap");
		 publishProgress(5);
		 
		 //Using treemap to sort retweeters according to their followers
		 TreeMap<Integer,String> tm = new TreeMap<Integer, String>();
		 try{
		     for(int k=0;k<10;k++)
		         {
		    	     publishProgress(5);
			         Log.i(a, "treemap followers"+k);
			         u = twitter.showUser(id[k]);
			         follower=u.getFollowersCount();
			         url=u.getProfileImageURL();
			         tm.put(follower,url );
		         }
		    }
		catch(Exception e)
		    {
			    Log.e(a, e.toString());
		    }
		Log.i(a, "Done treemap");
		 
		 //Reverse the order of the treemap 
		 Log.i(a, "Reversing Treemap");
         NavigableMap<Integer,String> reverseTreeMap = tm.descendingMap();
		 publishProgress(5);
		 
		 //Put treemap values in string array
		 s=reverseTreeMap.values().toString();
		 s=s.replace("[", "");
		 s=s.replace("]", "");
		  Log.i(a, "Done");
		  publishProgress(100);
		
	    			 
	     }
	     catch(Exception e)
	     {
	    	 Log.e(CONNECTIVITY_SERVICE,e.toString());
	     }
				
	        
	        return s;
				
			}






			@Override
			protected void onPostExecute(String result)
			{
				 Log.i(a, "On post");
				 dialog.dismiss(); 
				 //Start new activity and transfer urls
				 Intent i = new Intent(MainActivity.this,ProcessView.class);
					
	                // startActivity(i);
					i.putExtra("KEY",result);
					MainActivity.this.startActivity(i);
			        super.onPostExecute(result);
			}






	}
	


	     @SuppressWarnings("deprecation")
		public void showAlertDialog(Context context, String title, String message, Boolean status) {
	         AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	  
	         // Setting Dialog Title
	         alertDialog.setTitle(title);
	  
	         // Setting Dialog Message
	         alertDialog.setMessage(message);
	          
	        
	  
	         // Setting OK Button
	         alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int which) {
	             }
	         });
	  
	         // Showing Alert Message
	         alertDialog.show();
	     }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
