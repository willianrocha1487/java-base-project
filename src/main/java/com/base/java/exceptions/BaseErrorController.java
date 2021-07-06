package com.base.java.exceptions;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@Controller
public class BaseErrorController implements ErrorController {

	 @RequestMapping("/error")
	 public void handleError(HttpServletRequest request) throws Exception {
		  Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		  if (status != null) {
				int statusCode = Integer.parseInt(status.toString());
				if(statusCode == INTERNAL_SERVER_ERROR.value()) {
					 throw new Exception("Conexão recusada!");
				}
				if(statusCode == NOT_FOUND.value()) {
					 throw new ResourceNotFoundException("Endereço não encontrado!");
				}
				if(statusCode == FORBIDDEN.value()) {
				  throw new UnauthorizedErrorException("Usuário não autorizado!");
				}
		  }
		  throw new Exception("INTERNAL_SERVER_ERROR");
	 }

//	 @Override
	 public String getErrorPath() {
		  return null;
	 }

}
