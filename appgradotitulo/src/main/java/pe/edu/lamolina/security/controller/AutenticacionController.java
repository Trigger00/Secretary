package pe.edu.lamolina.security.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.misc.json.JSONObject;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;
import pe.edu.lamolina.security.service.AutenticacionService;

@Controller
public class AutenticacionController{
	
	private final Logger log =Logger.getLogger(AutenticacionController.class);
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@RequestMapping("/inicio")
	public String loadHome(HttpServletRequest request,HttpServletResponse response){
		final String method = "loadHome";
		final String params = "HttpServletRequest request, HttpServletResponse response";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		String view="home/home";
		
		UNALMLogger.exit(log, method, view);
		return view;
	}
	
	@RequestMapping("/workspace")
	public String loadWorkspace(HttpServletRequest request,HttpServletResponse response){
		final String method = "loadWorkspace";
		final String params = "HttpServletRequest request, HttpServletResponse response";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		String view="home/login";
		SeguridadUsuarioEntity usuario=(SeguridadUsuarioEntity)request.getSession().getAttribute(ApplicationConstant.USER_SESSION_ID);
		UNALMLogger.trace(log, method, "usuario: "+usuario);
		if(usuario!=null){
			view="home/workspace";
		}
		UNALMLogger.exit(log, method, view);
		return view;
	}
	@RequestMapping("/auth/getUserData")
	public ModelAndView getUserData(HttpServletRequest request,HttpServletResponse response){
		final String method = "getUserData";
		final String params = "HttpServletRequest request, HttpServletResponse response";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		String view="auth/getUserData";
		SeguridadUsuarioEntity usuario=(SeguridadUsuarioEntity)request.getSession().getAttribute(ApplicationConstant.USER_SESSION_ID);
		ModelAndView model=new ModelAndView(view);
		model.addObject("usuario",usuario);
		UNALMLogger.exit(log, method, view);
		return model;
	}
	
	@RequestMapping("/auth/login")
	public void login(HttpServletRequest request,HttpServletResponse response) throws Exception{
		final String method = "login";
		final String params = "HttpServletRequest request, HttpServletResponse response";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = null;
		rootJSON = new JSONObject();
        try {
        	String password=request.getParameter("u");
        	String loginid=request.getParameter("p");
        	UNALMLogger.trace(log, method,"password: "+password);
        	UNALMLogger.trace(log, method,"loginid: "+loginid);
        	
        	SeguridadUsuarioEntity usuarioLogin=new SeguridadUsuarioEntity();
        	usuarioLogin.setTextoLoginMD5(loginid);
        	usuarioLogin.setTextoClaveMD5(password);
        	
        	SeguridadUsuarioEntity usuarioLogueado=autenticacionService.validarUsuario(usuarioLogin);
        	if(usuarioLogueado!=null){
            	request.getSession().setAttribute(ApplicationConstant.USER_SESSION_ID, usuarioLogueado);
            	rootJSON.put("message", "Login OK");
                rootJSON.put("success", true);
        	}else{
        		rootJSON.put("message", "Usuario y/o contraseña incorrectos.");
                rootJSON.put("success", false);
        	}
        } catch (Exception e) {
        	UNALMLogger.error(log, method, "Error al loguear", e);
            rootJSON.put("success", false);
            rootJSON.put("message", e.getMessage());
        } finally {
            String jsonString = rootJSON.toString();
            UNALMLogger.trace(log, method,"jsonString: "+jsonString);
            out.print(jsonString);
            out.flush();
            out.close();
        }
        UNALMLogger.exit(log, method);
	}
	@RequestMapping("/auth/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		final String method = "logout";
		final String params = "HttpServletRequest request, HttpServletResponse response";
		UNALMLogger.entry(log, method, params, new Object[] { request, response });
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject rootJSON = null;
		rootJSON = new JSONObject();
        try {
        	request.getSession().removeAttribute(ApplicationConstant.USER_SESSION_ID);
            rootJSON.put("message", "Logout OK");
            rootJSON.put("success", true);
        } catch (Exception e) {
        	UNALMLogger.error(log, method, "Error al cerrar session", e);
            rootJSON.put("success", false);
            rootJSON.put("message", e.getMessage());
        } finally {
            String jsonString = rootJSON.toString();
            UNALMLogger.trace(log, method,"jsonString: "+jsonString);
            out.print(jsonString);
            out.flush();
            out.close();
        }
        UNALMLogger.exit(log, method);
	}
}
