package com.opencredo.demo.hateoas;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationHome {
   
     /*  http://localhost:8080/service?ctx=cutomer-service */
  
    /**
     * description of a service
     * 
     * @param ctx
     * @param model
     * @return
     */
    @RequestMapping(value="/service", method = RequestMethod.GET)    
    public String homepage(@RequestParam(value="ctx", required=true) String ctx, Model model) {
        model.addAttribute("hateoasURL", ctx + "/hateoas");
        return "service";
    }
    
    /**
     * self-description
     * 
     * @param model
     * @return
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String homepage() {
        return "self";
    }
    
    
}
