package com.howardpchen.aries.dao;

import com.howardpchen.aries.Util;
import com.howardpchen.aries.dao.UserDAO;
import com.howardpchen.aries.model.User;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
 
@ManagedBean(name = "loginBean")
@SessionScoped
/**
 *
 * @author User
 */
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String password;
    private String message, uname;
    private String fullname;
	private String option;
    
    public LoginBean(){
    	this.setOption("Clinical");
    }
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getUname() {
        return uname;
    }
 
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String register() {
    	boolean result = UserDAO.login(uname, password);
    	if(result == true){
    		   FacesContext.getCurrentInstance().addMessage(
                       null,
                       new FacesMessage(FacesMessage.SEVERITY_ERROR,
                       "Username and Password combination is already there",
                       "Please Try Again!"));
    		   return null;
    	}
    	String success= UserDAO.addLoginDetails(this.getUname(),this.getPassword());
    	if(success.equalsIgnoreCase("true")){
    		FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registration Successful",
                    "Please Try Again!"));
    		this.setUname("");
    		this.setPassword("");
    		this.setFullname("");
    	}
    	else{
    		 FacesContext.getCurrentInstance().addMessage(
                     null,
                     new FacesMessage(FacesMessage.SEVERITY_ERROR,
                     "Registration failed.Please check in console",
                     "Please Try Again!"));
    	}
    	return "";
    }
    public String loginProject() {
        boolean result = UserDAO.login(uname, password);
      
        if (result) {
        	  
            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);
            session.setAttribute("password", password);
           // int userid = UserDAO.getUserID(uname,password);
           // session.setAttribute("User", obj);
           // this.setUname("");
           // this.setFullname("");
            System.out.println("this.getOption() .."+this.getOption());
            if("QC".equals(this.getOption())&& (!uname.equalsIgnoreCase("manuel")|| !uname.equalsIgnoreCase("manuel1") || !uname.equalsIgnoreCase("manuel2") || uname.equalsIgnoreCase("manuel3")
            		|| !uname.equalsIgnoreCase("manuel4") || !uname.equalsIgnoreCase("siva")|| !uname.equalsIgnoreCase("yamuna"))){
            	 FacesContext.getCurrentInstance().addMessage(
                         null,
                         new FacesMessage(FacesMessage.SEVERITY_ERROR,
                         "Access to QC is restricted to selected users only",
                         ""));
            	
            }
            if("Clinical".equals(this.getOption())){
                return "index?faces-redirect=true";
            }
            else if("Submit Case".equals(this.getOption())){
            	return "caseInput_form?faces-redirect=true";
            }
            else if("Research".equals(this.getOption())){
            	return "research?faces-redirect=true";
            }
            else if("Education".equals(this.getOption())){
            	return "education?faces-redirect=true";
            }
            else if("QC".equals(this.getOption())&& (uname.equalsIgnoreCase("manuel")|| uname.equalsIgnoreCase("manuel1") || uname.equalsIgnoreCase("manuel2") || uname.equalsIgnoreCase("manuel3")
            		|| uname.equalsIgnoreCase("manuel4") )){
            	return "qualityControl?faces-redirect=true";
            }
           
        } else {
        	
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Username or Password is incorrect",
                    "Please Try Again!"));
        }
        return ""; 
    }
 
    public String logout() {
      HttpSession session = Util.getSession();
      session.invalidate();
      return "login";
   }

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
}