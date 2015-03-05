package com.hts.web.common.util.email;

public class SendVO_Email {
	String smtp; //发送人的smtp

	   String port; //发送人的邮件发送端口号

	   String email; //发送人的email地址

	   String username; //发送人的email账号

	   String password; //发送人的email密码

	   public SendVO_Email()
	   {
	   }

	   public String getSmtp()
	   {
	      return this.smtp;
	   }

	   public void setSmtp(String smtp)
	   {
	      this.smtp = smtp;
	   }

	   public void setPort(String port)
	   {
	      this.port =port;
	   }

	   public String getPort()
	   {
	      return this.port;
	   }

	   public void setEmail(String email)
	   {
	      this.email = email;
	   }

	   public String getEmail()
	   {
	      return this.email;
	   }

	   public void setUsername(String username)
	   {
	      this.username = username;
	   }

	   public String getUsername()
	   {
	      return this.username;
	   }

	   public void setPassword(String password)
	   {
	      this.password = password;
	   }

	   public String getPassword()
	   {
	      return this.password;
	   }
}
