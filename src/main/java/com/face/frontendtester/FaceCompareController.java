package com.face.frontendtester;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FaceCompareController {

    byte[] image1;byte[] image2;


	@GetMapping("/facecompare")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "facecompare";
    }
    


    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("filetwo") MultipartFile filetwo) {

        String uri = "http://localhost:5000/api/v1/compare";
        String body = "";

        try{image1 = file.getBytes();}catch(IOException ex){}
        try{image2 = filetwo.getBytes();}catch(IOException ex){}

        byte[] encoded1 = Base64.getEncoder().encode(image1);
        byte[] encoded2 = Base64.getEncoder().encode(image2);

        String s1 = new String(encoded1);
        String s2 = new String(encoded2);

        body = s1 + "split" + s2;

        

        RestTemplate restTemp = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("token", LoginTokenSetter.token); 

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);

        var response = restTemp.postForObject(uri, entity, String.class);

        
        
        return "results";
    }


}