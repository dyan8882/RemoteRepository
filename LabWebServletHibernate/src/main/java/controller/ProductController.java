package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import config.PrimitiveNumberEditor;
import model.ProductBean;
import model.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@InitBinder
	public void registerPropertyEditor(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		
		webDataBinder.registerCustomEditor(double.class, "price",
				new PrimitiveNumberEditor(Double.class, true));
		
		webDataBinder.registerCustomEditor(int.class,
				new PrimitiveNumberEditor(Integer.class, true));
	}
	
	@RequestMapping(
			path={"/pages/product.controller"}
	)
	public String method(Model model, String prodaction, ProductBean bean,
			BindingResult bindingResult, @RequestParam("id") String temp1) {
		System.out.println("bindingResult="+bindingResult);
//接收資料	
//轉換資料	
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errors", errors);
		
		if(bindingResult!=null && bindingResult.hasFieldErrors()) {
			if(bindingResult.hasFieldErrors("id")) {
				errors.put("id", "Id must be an integer (FormBean)");
			}
			if(bindingResult.hasFieldErrors("price")) {
				errors.put("price", "Price must be a number (FormBean)");
			}
			if(bindingResult.hasFieldErrors("make")) {
				errors.put("make", "Make must be a date of YYYY-MM-DD (FormBean)");
			}
			if(bindingResult.hasFieldErrors("expire")) {
				errors.put("expire", "Expire must be an integer (FormBean)");
			}
		}
		
//驗證資料
		if("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if (temp1 == null || temp1.length() == 0) {
				errors.put("id", "please enter id for " + prodaction+" (FormBean)");
			}
		}
		
		if(errors!=null && !errors.isEmpty()) {
			return "product.error";
		}
		
//根據Model執行結果呼叫View
		if("Select".equals(prodaction)) {
			List<ProductBean> result = productService.select(bean);
			model.addAttribute("select", result);
			return "product.servlet";
		} else if("Insert".equals(prodaction)) {
			ProductBean result = productService.insert(bean);
			if(result==null) {
				errors.put("action", "Insert fail");
			} else {
				model.addAttribute("insert", result);
			}
			return "product.error";
		} else if("Update".equals(prodaction)) {
			ProductBean result = productService.update(bean);
			if(result==null) {
				errors.put("action", "Update fail");
			} else {
				model.addAttribute("update", result);
			}
			return "product.error";
		} else if("Delete".equals(prodaction)) {
			boolean result = productService.delete(bean);
			if(!result) {
				model.addAttribute("delete", 0);
			} else {
				model.addAttribute("delete", 1);
			}
			return "product.error";
		} else  {
			errors.put("action", "Unknown Action:"+prodaction);
			return "product.error";
		}
	}
}
