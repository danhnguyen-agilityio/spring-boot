package proxy.pattern.virtual.cd;

import java.net.URL;

public class ImageProxyTestDriver {
  public static void main(String[] args) throws Exception {
    new ImageProxy(new URL("https://htmlcolorcodes.com/assets/images/html-color-codes-color-tutorials-hero-00e10b1f.jpg"))
        .paintIcon(null, null, 0, 0);

    new ImageProxy(new URL("https://st2.depositphotos.com/8291294/11285/i/950/depositphotos_112854140-stock-photo-unfocused-blurred-texture-pattern-abstract.jpg"))
        .paintIcon(null, null, 0, 0);
  }
}
