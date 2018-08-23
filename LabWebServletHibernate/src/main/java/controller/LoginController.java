package controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import model.CustomerBean;
import model.CustomerService;

@Controller
@SessionAttributes(names={"user"})
public class LoginController {
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(
			path={"/secure/login.controller"}
	)
	public String method(String username, String password, Model model) {
		Locale locale = LocaleContextHolder.getLocale();

//接收資料
//轉換資料
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errorMsgs", errors);
		if(username==null || username.length()==0) {
			errors.put("xxx1", context.getMessage(
					"seucre.login.username.required", null, locale));	
		}
		if(password==null || password.length()==0) {
			errors.put("xxx2", context.getMessage(
					"seucre.login.password.required", null, locale));	
		}
		if(errors!=null && !errors.isEmpty()) {
			return "login.error";
		}
		
//呼叫Model
		CustomerBean bean = customerService.login(username, password);
		
//根據Model執行結果呼叫View
		if(bean==null) {
			errors.put("xxx2", "login failed (view)");	
			return "login.error";			
		} else {
			model.addAttribute("user", bean);
			return "login.success";
		}
	}
}
