package com.onandhome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String index() {
		return "index"; // templates/index.html로 이동
	}

    @GetMapping("/product")
    public String product() { return "product"; }

    @GetMapping("/about")
    public String about() { return "about"; }
}
