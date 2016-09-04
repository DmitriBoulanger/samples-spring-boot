package de.dbo.samples.springboot.mvc.greeting.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OpenController {
    private static final Logger LOG = LoggerFactory.getLogger(OpenController.class);

	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model) {
	    LOG.info("open ...");
	    return "welcome";
	}

}
