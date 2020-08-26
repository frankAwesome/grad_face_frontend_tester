package com.face.frontendtester;

import java.io.IOException;

import com.face.frontendtester.models.Token;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    ModelAndView model = new ModelAndView();	

    @RequestMapping("/login")
	public ModelAndView Show()
	{	

        model.addObject("user", Globals.baseImageOne);
		model.addObject("password", Globals.baseImageTwo);


        return new ModelAndView();
    }


    @RequestMapping(value = "/loginconfirm", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("user") String user, @RequestParam("password") String password) throws IOException {


        String body = "{"+ "\"" +"username"+ "\"" +":"+"\"" + user + "\"" + "," + 
        		"\"" +"password"+ "\"" +":"+"\"" + password + "\"" +"}";
        
        System.out.println(body);
        
        RestTemplate restTemplate = new RestTemplate();
        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        Token token = restTemplate.postForObject("http://40.112.168.248:5000/api/v1/login", entity, Token.class);
        
        System.out.println(token);
        
        Globals.token = "Bearer " + token.jwt;






        return "/home";


    }
    
}