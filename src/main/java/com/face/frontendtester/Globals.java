package com.face.frontendtester;

import com.face.frontendtester.models.Emotion;
import com.face.frontendtester.models.Face;
import com.face.frontendtester.models.FaceResponse;
import com.face.frontendtester.models.FacialHair;
import com.face.frontendtester.models.Makeup;

import org.springframework.stereotype.Component;

@Component
public class Globals {
    public static String baseImageOne;
    public static String baseImageTwo;
    public static String confidence = "";

    public static Face globalfaceone;
    public static Face globalfacetwo;

    public static String token;
}