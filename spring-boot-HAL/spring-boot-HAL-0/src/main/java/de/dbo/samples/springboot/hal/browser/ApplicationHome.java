package de.dbo.samples.springboot.hal.browser;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApplicationHome {
  
    /**
     * star-up
     * 
     * @param model
     * @return
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String homepage() {
        return "browser";
    }
    
    
}
