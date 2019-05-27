package patterns.proxy.virtual.cd;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageProxy implements Icon {
  ImageIcon imageIcon;
  URL imageURL;
  Thread retrievalThread;
  boolean retrieving = false;

  public ImageProxy(URL url) {
    imageURL = url;
  }

  @Override
  public void paintIcon(Component c, Graphics g, int x, int y) {
    if (imageIcon != null) {
      System.out.println("Loaded image");
    } else {
      System.out.println("Loading image, please wait");
      if (!retrieving) {
        retrieving = true;
        retrievalThread = new Thread(new Runnable() {
          @Override
          public void run() {
            System.out.println("Start load image");
            // The image loading with IconImage is synchronous
            // The imageIcon constructor does not return until the image is loaded
            imageIcon = new ImageIcon(imageURL,"CD cover");
            System.out.println("Image fully loaded");
            System.out.println("Width: " + getIconWidth());
            System.out.println("Height: " + getIconHeight());
          }
        });
        retrievalThread.start();

      }
    }
  }

  @Override
  public int getIconWidth() {
    if (imageIcon != null) {
      return imageIcon.getIconWidth();
    } else {
      return 800;
    }
  }

  @Override
  public int getIconHeight() {
    if (imageIcon != null) {
      return imageIcon.getIconHeight();
    } else {
      return 600;
    }
  }
}
