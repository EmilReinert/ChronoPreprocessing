package resize;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;



public class ImageSizer {

	String in_path;
	String out_path;
	
	public static void main(String[] args) {

		// Handling Images
		ImageSizer ims = new ImageSizer("Abbildungen", "thumbnails");
		ims.scaleAllImages();
	}
	
	public ImageSizer(String input, String output) {
		in_path = input;
		out_path = output;
	}
	
	public void scaleAllImages() {
		// creating out path
		 File out = new File(out_path);
	      //Creating the directory
		 if(!out.exists())
			 out.mkdir();
	// calling copyimage and scaleimage on all images in in_path	
	  File dir = new File(in_path);
	  File[] directoryListing = dir.listFiles();
	  if (directoryListing != null) {
	    for (File child : directoryListing) {
	      // Do something with child

			try {
				copyImage(child.getName());
				scaleImage(child.getName());
			} catch (IOException e) {
				System.err.println("file not found");
			}
	    }
	  } else {
	    // Handle the case where dir is not really a directory.
	    // Checking dir.isDirectory() above would not be sufficient
	    // to avoid race conditions with another process that deletes
	    // directories.
		  }
	}
	
	public void copyImage(String name) throws IOException {
		System.out.println("copying "+name);
		// copy image from inpath to outpath
		InputStream is = null;
        OutputStream os = null;
        try {
            System.out.println(in_path+"/"+name);
            is = new FileInputStream(new File(in_path+"/"+name));
            os = new FileOutputStream(new File(out_path+"/"+name));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            }catch (Exception e) {
				System.err.println("file not found");
			}
        finally {
            is.close();
            os.close();
        }        
	}
	
	public void scaleImage(String name) throws IOException{
		File out = new File(out_path+"/"+name);
		BufferedImage in = ImageIO.read(out);
		
		ImageIO.write(resizeImage(in, 100, 100), "jpg", out);

	}
	
	
	public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
	    Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
	    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
	    return outputImage;
	}
}
