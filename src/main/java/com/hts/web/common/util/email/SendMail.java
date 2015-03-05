package com.hts.web.common.util.email;

import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import java.util.Date;
public class SendMail {
	static Logger log = Logger.getLogger(SendMail.class);

	   //定义收件人、cc、bcc、发送人、主题等
	   String from = "";
	   String to = "";
	   String cc = "";
	   String bcc = "";
	   String host = "";
	   String filename = "";
	   String messagetext = "";
	   String subject = "";
	   boolean htmlformat = false; //是否HTML邮件标识

	   String username = "";
	   String password = "";

	   public static SendVO_Email vo = null;

	   public SendMail()
	   {
	   }

	   /**
	    * 发送邮件的参数定义
	    * 
	    * @param to 邮件接收方邮件地址，eg: abc@gdcn.com
	    * 允许发送多人：abc@gdcn.com,bcd@gdcn.com,efg@gdcn.com
	    * @param subject 邮件主题
	    * @param msg 邮件内容
	    */
	   public SendMail(String to, String subject, String msg)
	   {
	      //定义收件人、主题、内容等
	      this.to = to;
	      this.subject = subject;
	      this.messagetext = msg;
	   }
	   
	   /**
	    * 发送邮件的参数定义
	    * 
	    * @param to 邮件接收方邮件地址，eg: abc@gdcn.com
	    * 允许发送多人：abc@gdcn.com,bcd@gdcn.com,efg@gdcn.com
	    * @param subject 邮件主题
	    * @param msg 邮件内容
	    */
	   public SendMail(String to, String subject, String msg, String fileName)
	   {
	      //定义收件人、主题、内容等
	      this.to = to;
	      this.subject = subject;
	      this.messagetext = msg;
	      this.filename = fileName;
	   }

	   /**
	    * 发送邮件的参数定义
	    * 
	    * @param to 邮件接收方邮件地址
	    * @param cc 抄送接收方邮件地址
	    * @param bcc 暗送接收方邮件
	    * @param subject 邮件主题
	    * @param msg 邮件内容
	    */
	   public SendMail(String to, String cc, String bcc, String subject, String msg)
	   {
	      this.to = to;
	      this.cc = cc;
	      this.bcc = bcc;
	      this.subject = subject;
	      this.messagetext = msg;
	   }

	   /**
	    * 发送邮件的参数定义
	    * 
	    * @param to 邮件接收方邮件地址，eg: abc@gdcn.com
	    * 允许发送多人：abc@gdcn.com,bcd@gdcn.com,efg@gdcn.com
	    * @param cc 抄送接收方邮件地址
	    * @param bcc 暗送接收方邮件
	    * @param subject 邮件主题
	    * @param msg 邮件内容
	    * @param from 邮件发送方的邮件地址
	    */
	   public SendMail(String to, String cc, String bcc, String subject,
	         String msg, String from)
	   {
	      this.to = to;
	      this.cc = cc;
	      this.bcc = bcc;
	      this.subject = subject;
	      this.messagetext = msg;
	      this.from = from;
	   }

	   /**
	    * 发送邮件的参数定义
	    * 
	    * @param to 邮件接收方邮件地址，eg: abc@gdcn.com
	    * 允许发送多人：abc@gdcn.com,bcd@gdcn.com,efg@gdcn.com
	    * @param cc 抄送接收方邮件地址
	    * @param bcc 暗送接收方邮件
	    * @param subject 邮件主题
	    * @param msg 邮件内容
	    * @param from 邮件发送方的邮件地址
	    * @param smtpServer 发送邮件服务器的地址
	    * @param username 发送邮件方的邮箱用户名
	    * @parama password 发送邮件方的邮箱密码
	    */
	   public SendMail(String to, String cc, String bcc, String subject,
	         String msg, String from, String smtpServer, String username,
	         String password)
	   {
	      this.to = to;
	      this.cc = cc;
	      this.bcc = bcc;
	      this.subject = subject;
	      this.messagetext = msg;
	      this.from = from;
	      this.host = smtpServer;
	      this.username = username;
	      this.password = password;
	   }

	   public boolean sendmail()
	   {

	      try
	      {
	         if (vo == null)
	         {
	            //读取配置文件
	            InputStream propsInput = this.getClass().getResourceAsStream("/mail.properties");
	            Properties serviceProps = new Properties();
	            serviceProps.load(propsInput);
	            String smtp = (String) serviceProps.getProperty("Smtp");
	            String port = (String) serviceProps.getProperty("Port");
	            String email = (String) serviceProps.getProperty("Email");
	            String username = (String) serviceProps.getProperty("UserName");
	            String password = (String) serviceProps.getProperty("Password");

	            vo = new SendVO_Email();
	            vo.setSmtp(smtp);
	            vo.setPort(port);
	            vo.setEmail(email);
	            vo.setUsername(username);
	            vo.setPassword(password);
	         }

	         if (host == null || host.length() == 0)
	            host = vo.getSmtp();
	         if (username == null || username.length() == 0)
	            username = vo.getUsername();
	         if (password == null || password.length() == 0)
	            password = vo.getPassword();
	         if (from == null || from.length() == 0)
	            from = vo.getEmail();

	         SendImplement send = new SendImplement(to, cc, bcc, from, host,
	               subject, messagetext, username, password, htmlformat);
	         if(!this.filename.equals("")){
	        	 send = new SendImplement(to, cc, bcc, from, host,
	                     subject, messagetext, filename, username, password, htmlformat);
	         }
	         
	         boolean sendFlag = send.sendmail();
	         if (sendFlag)
	            log.info(new Date() + " 提示:向" + to
	                  + "发送邮件,身份认证并发送成功!");
	         else
	         {
	            log.info(new Date() + " 提示:向" + to
	                  + "发送邮件,身份认证并发送失败!开始不认证发送程序...");
	            send.setAuthentication(false);
	            sendFlag = send.sendmail();
	            if (sendFlag)
	               log.info(new Date() + "提示:向" + to
	                     + "发送邮件,身份不认证并发送成功!");
	            else
	               log.info(new Date() + " 提示:向" + to
	                     + "发送邮件,身份不认证并发送失败!");
	         }
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	         return false;
	      }
	      return true;
	   }

	   public void setHtmlformat(boolean flg)
	   {
	      this.htmlformat = flg;
	   }
}
