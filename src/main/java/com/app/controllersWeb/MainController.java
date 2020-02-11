package com.app.controllersWeb;

import com.app.Utilities.CustomPaths;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    public String welcome() {


        // TODO: 2020-02-11
        System.out.println("***********************");
        System.out.println("root =" + CustomPaths.ROOT);
        System.out.println("cmp detail sfile=" + CustomPaths.COMPANIES_FILE_PATH);
        System.out.println("saved path=" + CustomPaths.SAVED_ORDERS_PATH);

        return "/index";
    }
}