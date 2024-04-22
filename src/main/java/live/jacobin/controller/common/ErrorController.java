package live.jacobin.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error-404")
    public String handle404() {
        return "error/error_404_page";
    }

    @GetMapping("/error-500")
    public String handle500() {
        return "error/error_500_page";
    }

    @GetMapping("/error-java")
    public String handleJava() {
        return "error/error_java_page";
    }

}
