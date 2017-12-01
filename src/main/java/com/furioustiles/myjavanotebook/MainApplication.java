package com.furioustiles.myjavanotebook;

import java.awt.Desktop;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

/**
 * @author furioustiles
 */
@SpringBootApplication
public class MainApplication {

  public void browse() {
    // port number is hard coded for now
    String portNo = new String("8000");
    String url = "http://localhost:" + portNo;

    if (Desktop.isDesktopSupported()) {
      Desktop desktop = Desktop.getDesktop();
      try {
        desktop.browse(new URI(url));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      Runtime runtime = Runtime.getRuntime();
      try {
        runtime.exec("xdg-open "+url);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);

    MainApplication mainApp = new MainApplication();
    mainApp.browse();
  }
}
