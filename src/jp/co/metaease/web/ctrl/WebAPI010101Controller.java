package jp.co.metaease.web.ctrl;

import java.util.Iterator;
import java.util.Map;
import java.net.*;
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.metaease.com.WebAPIUtil;

/** Defines a controller to handle HTTP requests */
@Controller
public final class WebAPI010101Controller {

  private static String project;
  private static final Logger logger = LoggerFactory.getLogger(WebAPI010101Controller.class);

  /**
   * Create an endpoint for the landing page
   *
   * @return the index view template
   */
  @GetMapping("/")
  public String webapi010101(Model model) throws Exception {

    logger.info( "■■■webapi010101 START" );

    // Get Cloud Run environment variables.
    String revision = System.getenv("K_REVISION") == null ? "???" : System.getenv("K_REVISION");
    String service = System.getenv("K_SERVICE") == null ? "???" : System.getenv("K_SERVICE");

    // API呼び出し
    URL verisign = new URL("https://my-apigw-001-1upcx6wd.uc.gateway.dev");
    BufferedReader in = new BufferedReader(new InputStreamReader(verisign.openStream()));
    String inputLine;
    String strRespApi = "";
    while ((inputLine = in.readLine()) != null) {
        logger.info( "inputLine:" + inputLine );
        strRespApi = strRespApi + String.valueOf(inputLine);
    }
    in.close();

    logger.info( "strRespApi:" + strRespApi );

    // Set variables in html template.
    model.addAttribute("revision", revision);
    model.addAttribute("service", service);
    model.addAttribute("message", "メッセージです。" + strRespApi);

    printMap(model.asMap());
    logger.info( "■■■webapi010101 END" );

    return "index";
  }

  private void printMap(Map<String, Object> map) {

    if (map == null || map.isEmpty()) {
        logger.info( "戻り値なし" );
        return ;
    }

    for ( Object key : map.keySet() ) {
        logger.info( key.toString() + ":" + map.get(key.toString()).toString() );
    }

    return ;
  }

}
