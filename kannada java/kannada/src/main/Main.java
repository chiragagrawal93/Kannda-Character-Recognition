package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import logical.regression;
import regression.miniHashing;
import Utils.Utils;

public class Main {

	static boolean trainigDataMode = false;
	public static void main(String[] args) {
		Utils.clearFolders();
		if(trainigDataMode)
		{
			File folder = new File("E:/test images/");
			listFilesForFolder(folder);
		}
		else
		{
			processFile(new File("./res/sample19.jpg"));
		}
	}
	
	public static void listFilesForFolder(final File folder) {
		int i = 1;
	    for (final File fileEntry : folder.listFiles()) {
	    	System.out.println("Reading file... " + i++ + " PicNum : " + VerticalProjectionProfile.picNum);
	    	Utils.clearFolders();
	    	processFile(fileEntry);
	    }
	}
	
	public static void processFile(File file)
	{
		Thinning th = new Thinning();
		miniHashing h = new miniHashing();
		regression r = new regression();
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[][] imageData = null;
		imageData = Utils.threshHoldBinarize(image);
		imageData = th.doZhangSuenThinning(imageData);
		new HorizontalProjectionProfile(imageData).extractLines("E:/buffer/",1);
		DeSkew.deSkewLines(1);
		
		h.methodforDocument();
		r.method();
		System.out.println("Recognition Done.");
		
//		System.out.println(Document.getInstance().toString());
		Document.getInstance().destroy();
	}
}
