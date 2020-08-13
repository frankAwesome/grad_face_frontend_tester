package com.face.frontendtester;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import com.face.frontendtester.models.Face;
import com.face.frontendtester.models.FaceResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FaceCompareController {

    byte[] image1;byte[] image2;

    BufferedImage imgA;
    BufferedImage imgB;


	@RequestMapping("/facecompare")
	public ModelAndView Show()
	{	


        return new ModelAndView();
    }

    @RequestMapping("/pass")
	public String Results()
	{	


        return "/results";
    }
    


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("input-file") MultipartFile file, @RequestParam("input-file-two") MultipartFile filetwo) throws IOException {

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

        FaceResponse response = restTemp.postForObject(uri, entity, FaceResponse.class);


        try
        {
            imgA = ImageIO.read(new ByteArrayInputStream(image1));
            System.out.println(imgA.toString()); 
            imgB = ImageIO.read(new ByteArrayInputStream(image2));
            System.out.println(imgB.toString()); 
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }



        Face faceone =  response.faces.get(0);
        Face facetwo =  response.faces.get(1);

        var noseTip = faceone.faceLandmarks.noseTip;





        Graphics2D g = (Graphics2D) imgA.getGraphics();
        g.setStroke(new BasicStroke(6));
        g.setColor(Color.BLUE);
        //draw face rectangle
        g.drawRect(faceone.faceRectangle.left, faceone.faceRectangle.top, faceone.faceRectangle.width, faceone.faceRectangle.height);

        //nosetip
        g.drawArc((int)faceone.faceLandmarks.noseTip.x, (int)faceone.faceLandmarks.noseTip.y, 3, 3, 0, 360);

        //eyeLeftBottom
        g.drawArc((int)faceone.faceLandmarks.eyeLeftBottom.x, (int)faceone.faceLandmarks.eyeLeftBottom.y, 3, 3, 0, 360);
        //eyeLeftInner
        g.drawArc((int)faceone.faceLandmarks.eyeLeftInner.x, (int)faceone.faceLandmarks.eyeLeftInner.y, 3, 3, 0, 360);
        //eyeLeftOuter
        g.drawArc((int)faceone.faceLandmarks.eyeLeftOuter.x, (int)faceone.faceLandmarks.eyeLeftOuter.y, 3, 3, 0, 360);
        //eyeLeftTop
        g.drawArc((int)faceone.faceLandmarks.eyeLeftTop.x, (int)faceone.faceLandmarks.eyeLeftTop.y, 3, 3, 0, 360);


        //eyeRightBottom
        g.drawArc((int)faceone.faceLandmarks.eyeRightBottom.x, (int)faceone.faceLandmarks.eyeRightBottom.y, 3, 3, 0, 360);
        //eyeRightInner
        g.drawArc((int)faceone.faceLandmarks.eyeRightInner.x, (int)faceone.faceLandmarks.eyeRightInner.y, 3, 3, 0, 360);
        //eyeRightOuter
        g.drawArc((int)faceone.faceLandmarks.eyeRightOuter.x, (int)faceone.faceLandmarks.eyeRightOuter.y, 3, 3, 0, 360);
        //eyeRightTop
        g.drawArc((int)faceone.faceLandmarks.eyeRightTop.x, (int)faceone.faceLandmarks.eyeRightTop.y, 3, 3, 0, 360);

        //eyebrowLeftInner
        g.drawArc((int)faceone.faceLandmarks.eyebrowLeftInner.x, (int)faceone.faceLandmarks.eyebrowLeftInner.y, 3, 3, 0, 360);
        //eyebrowLeftOuter
        g.drawArc((int)faceone.faceLandmarks.eyebrowLeftOuter.x, (int)faceone.faceLandmarks.eyebrowLeftOuter.y, 3, 3, 0, 360);
        //eyebrowRightInner
        g.drawArc((int)faceone.faceLandmarks.eyebrowRightInner.x, (int)faceone.faceLandmarks.eyebrowRightInner.y, 3, 3, 0, 360);
        //eyebrowRightOuter
        g.drawArc((int)faceone.faceLandmarks.eyebrowRightOuter.x, (int)faceone.faceLandmarks.eyebrowRightOuter.y, 3, 3, 0, 360);



        //mouthLeft
        g.drawArc((int)faceone.faceLandmarks.mouthLeft.x, (int)faceone.faceLandmarks.mouthLeft.y, 3, 3, 0, 360);
        //mouthRight
        g.drawArc((int)faceone.faceLandmarks.mouthRight.x, (int)faceone.faceLandmarks.mouthRight.y, 3, 3, 0, 360);


        //noseLeftAlarOutTip
        g.drawArc((int)faceone.faceLandmarks.noseLeftAlarOutTip.x, (int)faceone.faceLandmarks.noseLeftAlarOutTip.y, 3, 3, 0, 360);
        //noseLeftAlarTop
        g.drawArc((int)faceone.faceLandmarks.noseLeftAlarTop.x, (int)faceone.faceLandmarks.noseLeftAlarTop.y, 3, 3, 0, 360);
        //noseRightAlarOutTip
        g.drawArc((int)faceone.faceLandmarks.noseRightAlarOutTip.x, (int)faceone.faceLandmarks.noseRightAlarOutTip.y, 3, 3, 0, 360);
        //noseRightAlarTop
        g.drawArc((int)faceone.faceLandmarks.noseRightAlarTop.x, (int)faceone.faceLandmarks.noseRightAlarTop.y, 3, 3, 0, 360);
        //noseRootLeft
        g.drawArc((int)faceone.faceLandmarks.noseRootLeft.x, (int)faceone.faceLandmarks.noseRootLeft.y, 3, 3, 0, 360);
        //noseRootRight
        g.drawArc((int)faceone.faceLandmarks.noseRootRight.x, (int)faceone.faceLandmarks.noseRootRight.y, 3, 3, 0, 360);

        //pupilLeft
        g.drawArc((int)faceone.faceLandmarks.pupilLeft.x, (int)faceone.faceLandmarks.pupilLeft.y, 3, 3, 0, 360);
        //pupilRight
        g.drawArc((int)faceone.faceLandmarks.pupilRight.x, (int)faceone.faceLandmarks.pupilRight.y, 3, 3, 0, 360);


        //underLipBottom
        g.drawArc((int)faceone.faceLandmarks.underLipBottom.x, (int)faceone.faceLandmarks.underLipBottom.y, 3, 3, 0, 360);
        //underLipTop
        g.drawArc((int)faceone.faceLandmarks.underLipTop.x, (int)faceone.faceLandmarks.underLipTop.y, 3, 3, 0, 360);







        //image2

        Graphics2D g2 = (Graphics2D) imgB.getGraphics();
        g2.setStroke(new BasicStroke(6));
        g2.setColor(Color.BLUE);
        //draw face rectangle
        g2.drawRect(facetwo.faceRectangle.left, facetwo.faceRectangle.top, facetwo.faceRectangle.width, facetwo.faceRectangle.height);

        //nosetip
        g2.drawArc((int)facetwo.faceLandmarks.noseTip.x, (int)facetwo.faceLandmarks.noseTip.y, 3, 3, 0, 360);

        //eyeLeftBottom
        g2.drawArc((int)facetwo.faceLandmarks.eyeLeftBottom.x, (int)facetwo.faceLandmarks.eyeLeftBottom.y, 3, 3, 0, 360);
        //eyeLeftInner
        g2.drawArc((int)facetwo.faceLandmarks.eyeLeftInner.x, (int)facetwo.faceLandmarks.eyeLeftInner.y, 3, 3, 0, 360);
        //eyeLeftOuter
        g2.drawArc((int)facetwo.faceLandmarks.eyeLeftOuter.x, (int)facetwo.faceLandmarks.eyeLeftOuter.y, 3, 3, 0, 360);
        //eyeLeftTop
        g2.drawArc((int)facetwo.faceLandmarks.eyeLeftTop.x, (int)facetwo.faceLandmarks.eyeLeftTop.y, 3, 3, 0, 360);


        //eyeRightBottom
        g2.drawArc((int)facetwo.faceLandmarks.eyeRightBottom.x, (int)facetwo.faceLandmarks.eyeRightBottom.y, 3, 3, 0, 360);
        //eyeRightInner
        g2.drawArc((int)facetwo.faceLandmarks.eyeRightInner.x, (int)facetwo.faceLandmarks.eyeRightInner.y, 3, 3, 0, 360);
        //eyeRightOuter
        g2.drawArc((int)facetwo.faceLandmarks.eyeRightOuter.x, (int)facetwo.faceLandmarks.eyeRightOuter.y, 3, 3, 0, 360);
        //eyeRightTop
        g2.drawArc((int)facetwo.faceLandmarks.eyeRightTop.x, (int)facetwo.faceLandmarks.eyeRightTop.y, 3, 3, 0, 360);

        //eyebrowLeftInner
        g2.drawArc((int)facetwo.faceLandmarks.eyebrowLeftInner.x, (int)facetwo.faceLandmarks.eyebrowLeftInner.y, 3, 3, 0, 360);
        //eyebrowLeftOuter
        g2.drawArc((int)facetwo.faceLandmarks.eyebrowLeftOuter.x, (int)facetwo.faceLandmarks.eyebrowLeftOuter.y, 3, 3, 0, 360);
        //eyebrowRightInner
        g2.drawArc((int)facetwo.faceLandmarks.eyebrowRightInner.x, (int)facetwo.faceLandmarks.eyebrowRightInner.y, 3, 3, 0, 360);
        //eyebrowRightOuter
        g2.drawArc((int)facetwo.faceLandmarks.eyebrowRightOuter.x, (int)facetwo.faceLandmarks.eyebrowRightOuter.y, 3, 3, 0, 360);



        //mouthLeft
        g2.drawArc((int)facetwo.faceLandmarks.mouthLeft.x, (int)facetwo.faceLandmarks.mouthLeft.y, 3, 3, 0, 360);
        //mouthRight
        g2.drawArc((int)facetwo.faceLandmarks.mouthRight.x, (int)facetwo.faceLandmarks.mouthRight.y, 3, 3, 0, 360);


        //noseLeftAlarOutTip
        g2.drawArc((int)facetwo.faceLandmarks.noseLeftAlarOutTip.x, (int)facetwo.faceLandmarks.noseLeftAlarOutTip.y, 3, 3, 0, 360);
        //noseLeftAlarTop
        g2.drawArc((int)facetwo.faceLandmarks.noseLeftAlarTop.x, (int)facetwo.faceLandmarks.noseLeftAlarTop.y, 3, 3, 0, 360);
        //noseRightAlarOutTip
        g2.drawArc((int)facetwo.faceLandmarks.noseRightAlarOutTip.x, (int)facetwo.faceLandmarks.noseRightAlarOutTip.y, 3, 3, 0, 360);
        //noseRightAlarTop
        g2.drawArc((int)facetwo.faceLandmarks.noseRightAlarTop.x, (int)facetwo.faceLandmarks.noseRightAlarTop.y, 3, 3, 0, 360);
        //noseRootLeft
        g2.drawArc((int)facetwo.faceLandmarks.noseRootLeft.x, (int)facetwo.faceLandmarks.noseRootLeft.y, 3, 3, 0, 360);
        //noseRootRight
        g2.drawArc((int)facetwo.faceLandmarks.noseRootRight.x, (int)facetwo.faceLandmarks.noseRootRight.y, 3, 3, 0, 360);

        //pupilLeft
        g2.drawArc((int)facetwo.faceLandmarks.pupilLeft.x, (int)facetwo.faceLandmarks.pupilLeft.y, 3, 3, 0, 360);
        //pupilRight
        g2.drawArc((int)facetwo.faceLandmarks.pupilRight.x, (int)facetwo.faceLandmarks.pupilRight.y, 3, 3, 0, 360);


        //underLipBottom
        g2.drawArc((int)facetwo.faceLandmarks.underLipBottom.x, (int)facetwo.faceLandmarks.underLipBottom.y, 3, 3, 0, 360);
        //underLipTop
        g2.drawArc((int)facetwo.faceLandmarks.underLipTop.x, (int)facetwo.faceLandmarks.underLipTop.y, 3, 3, 0, 360);





        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(imgA, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();
            var imageString = Base64.getEncoder().encodeToString(imageBytes);

            Globals.baseImageOne = "data:image/jpg;base64, "+imageString;
            //System.out.println(imageString);
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(imgB, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();
            var imageString = Base64.getEncoder().encodeToString(imageBytes);

            Globals.baseImageTwo = "data:image/jpg;base64, "+imageString;
            //System.out.println(imageString);
        }


        Globals.confidence = String.valueOf(response.confidence);

        Globals.globalfaceone = response.faces.get(0);
        Globals.globalfacetwo = response.faces.get(1);


        
        return "/pass";
    }


    public static BufferedImage convertToARGB(BufferedImage image, int x, int y)
    {
        Color red = new Color(255,69,0); 
        int rgb = red.getRGB();
        BufferedImage newImage = new BufferedImage(
        image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
        image.setRGB(x, y, rgb);
        return newImage;
    }


    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
    
            Base64.Encoder encoder = Base64.getEncoder();
            imageString = encoder.encodeToString(imageBytes);
    
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }


}