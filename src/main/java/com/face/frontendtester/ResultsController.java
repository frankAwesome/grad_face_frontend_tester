package com.face.frontendtester;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResultsController {

	ModelAndView model = new ModelAndView();	

	@RequestMapping("results")
	public ModelAndView Show()
	{	


		//model.addAttribute("imageone", Globals.baseImageOne);
		//model.addAttribute("imagetwo", Globals.baseImageTwo);

		model.addObject("imageone", Globals.baseImageOne);
		model.addObject("imagetwo", Globals.baseImageTwo);
		model.addObject("confidence", Globals.confidence);

		model.addObject("sexone", Globals.globalfaceone.faceAttributes.gender);
		model.addObject("sextwo", Globals.globalfacetwo.faceAttributes.gender);

		model.addObject("glassesone", Globals.globalfaceone.faceAttributes.glasses);
		model.addObject("glassestwo", Globals.globalfacetwo.faceAttributes.glasses);

		model.addObject("ageone", Globals.globalfaceone.faceAttributes.age);
		model.addObject("agetwo", Globals.globalfacetwo.faceAttributes.age);

		return model;
	}

}