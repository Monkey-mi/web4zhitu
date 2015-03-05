package com.hts.web.common.util.email;


public class SendMailRunnable implements Runnable{
	private String email;
	private String subject;
	private String msg;
	
	public SendMailRunnable(String email,String subject,String msg){
		this.email = email;
		this.subject = subject;
		this.msg = msg;
	}
	public void run() {
		SendMail mail = new SendMail(email,subject,msg);
		mail.setHtmlformat(true);
//		mail.setHtmlformat(false);
		mail.sendmail();
	}
}

