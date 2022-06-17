package com.exchange.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.api.bean.CashinoFavour;
import com.exchange.api.bean.EXUser;
import com.exchange.api.service.EXCashinoFavourService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@ControllerAdvice
@RestController
@CrossOrigin
@RequestMapping("/api")
public class EXCashinoFavourController {

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	private EXCashinoFavourService eXCashinoFavourService;

	@RequestMapping(value = "/cashinoFavour", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> cashinoFavour(@RequestBody CashinoFavour request) {
		HttpSession session = httpServletRequest.getSession(true);
		
		EXUser usersession = (EXUser) session.getAttribute("user");
		Map<String, Object> response = new HashMap<String, Object>();

		Integer cashinoFavourStatus = 0;
		try {
			if (usersession != null) {
				if (StringUtils.isNotBlank(request.getType()) && request.getMatchid() != null && request.getMatchid() != 0 
						&& request.getFavour() != null) {
					cashinoFavourStatus = eXCashinoFavourService.cashinoFavour(request.getMatchid(), request.getType(), 
							request.getFavour());
					response.put("status", cashinoFavourStatus);
				} else {
					response.put("status", "validation failed");
				}
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			response.put("status", "exception occured - "+e.getMessage());
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
