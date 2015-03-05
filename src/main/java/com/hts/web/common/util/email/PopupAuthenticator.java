package com.hts.web.common.util.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
public class PopupAuthenticator extends Authenticator{
       String username = null;
	   String password = null;
	
	   public PopupAuthenticator()
	   {
	   }
	   
	   public PopupAuthenticator(String user, String pass)
	   {
		   this.username = user;
		   this.password = pass;
	   }
	
	   public PasswordAuthentication performCheck(String user, String pass)
	   {
	      username = user;
	      password = pass;
	      return getPasswordAuthentication();
	   }
	
	   protected PasswordAuthentication getPasswordAuthentication()
	   {
	      return new PasswordAuthentication(username, password);
	   }
}
