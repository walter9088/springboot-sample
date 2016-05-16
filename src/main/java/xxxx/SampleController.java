package xxxx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class SampleController {

    private static final Logger log = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping("/test.html")
    @ResponseBody
    String home() {
        MDC.put("testMDC", "this is test mdc!");
        log.info("this is info=================");
        log.error("this is error=================");
        log.warn("this is info=================");
        log.debug("this is info==============");

        return "Hello World!";

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
