package com.example.himanshu.myapplication;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

//This file is used to show the image all the metadata for the image as well

public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        //This part is used to show the image
        String title = getIntent().getStringExtra("title");
        File image = new File(title);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        ImageView imageView = (ImageView) findViewById(R.id.imageDetails);
        imageView.setImageBitmap(bitmap);

        //This part is to show all the metadata details of the image
        String allAttributes=new String();
        Cursor cursorForImages = null;
        try {
            cursorForImages = ConstantsClass.mydatabaseLatest.rawQuery("SELECT * from MESSAGE_TBL where imagePath='"+title+"'", null);
            if (cursorForImages == null)
                Log.d("DbFuncGetTags", "cursorForImages isnull");
        } catch (Exception e) {
            Log.d("DbFUncs", "Exception occurred, hahahaha!::" + e);
        }
        try {


            try {

                if (cursorForImages != null)
                    while (cursorForImages.moveToNext()) {

                        String sourceDeviceName = cursorForImages.getString(9);allAttributes+="Source name:"+sourceDeviceName+"\n";
                        String sourceDeviceAddr = cursorForImages.getString(8);allAttributes+="Source MAC address:"+sourceDeviceAddr+"\n";
                        String tags = cursorForImages.getString(4);allAttributes+="tags:"+tags+"\n";
                        String latitude = cursorForImages.getString(1);allAttributes+="latitude:"+latitude+"\n";
                        String longitude = cursorForImages.getString(2);allAttributes+="longitude:"+longitude+"\n";
                        String timestamp = cursorForImages.getString(3);allAttributes+="timestamp:"+timestamp+"\n";
                        String destDeviceName = cursorForImages.getString(12);allAttributes+="Destination name:"+destDeviceName+"\n";
                        String destDeviceAddr = cursorForImages.getString(11);allAttributes+="Destination MAC address:"+destDeviceAddr+"\n";
                        String mime = cursorForImages.getString(6);allAttributes+="Mime:"+mime+"\n";
                        String format = cursorForImages.getString(7);allAttributes+="Format:"+format+"\n";
                        String fileName = cursorForImages.getString(5);allAttributes+="File name:"+fileName+"\n";
                        String UUID=cursorForImages.getString(10);allAttributes+="UUID:"+UUID+"\n";

                        Cursor cursorForMsgIn=ConstantsClass.mydatabaseLatest.rawQuery("SELECT * FROM  INCENT_FOR_MSG_TBL where UUID='"+UUID+"'",null);
                        while(cursorForMsgIn.moveToNext())
                        {
                            Double incentivePromise=cursorForMsgIn.getDouble(1);allAttributes+="Incentive promise:"+incentivePromise+"\n";
                            Double incentiveReceived=cursorForMsgIn.getDouble(2);allAttributes+="Incentive received:"+incentiveReceived+"\n";
                            Double incentivePaid=cursorForMsgIn.getDouble(3);allAttributes+="Incentive paid:"+incentivePaid+"\n";

                        }

                    }
                TextView titleTextView = (TextView) findViewById(R.id.titleDetails);
                titleTextView.setText(allAttributes);
                titleTextView.setMovementMethod(new ScrollingMovementMethod());

            } catch (Exception e) {
                Log.d("DbFunctions", "Exception occured in sqlite query!!! ::" + e);
            }


        }
        catch(Exception e)
        {
            Log.d("ReceivedDetails","Exception:"+e);
        }
    }
}
