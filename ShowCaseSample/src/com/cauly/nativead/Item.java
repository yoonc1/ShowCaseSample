package com.cauly.nativead;

import android.graphics.Bitmap;

public class Item
{
	 public int img, icon;
	 public String  title, subTitle, description, tag;
	 public Bitmap imageBitmap, iconBitmap; 
	 
	 Item(int img, int icon, String title, String subTitle,String description,String tag)
	 {
		 this.icon = icon;
		 this.img = img;
		 this.title =title;
		 this.subTitle = subTitle;
		 this.tag = tag;
		 this.description = description;
	 }
	 Item(int img, String title, String subTitle,String description,String tag)
	 {
		 this.img = img;
		 this.title =title;
		 this.subTitle = subTitle;
		 this.tag = tag;
		 this.description = description;
	 }
	 Item(Bitmap icon, Bitmap img, String title, String subTitle, String description)
	 {
		 this.imageBitmap = img;
		 this.title =title;
		 this.subTitle = subTitle;
		 this.iconBitmap = icon;
		 this.description = description;
	 }
}