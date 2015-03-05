package com.hts.web.userinfo.service.impl;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.userinfo.service.UserRetrievePWDService;
import com.hts.web.userinfo.dao.UserRetrievePWDDao;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.pojo.RetrievePasswordDto;
import com.hts.web.common.util.email.SendMailRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * 重置密码业务逻辑实现
 * @author zxx
 *
 */
@Service("HTSUserRetrievePWDService")
public class UserRetrievePWDServiceImpl extends BaseServiceImpl implements UserRetrievePWDService{
	
	private static final long validTime=2*24*60*60*1000;//链接有效期 2天
	
	@Autowired
	private UserRetrievePWDDao retrievePWDDao;
	
	@Value("${resetPwdURLHead}")
	private String resetPwdURLHead;
	
	private String htmlHead= "<html>"
			+ "<body>"
			+ "<div style='height:420px;width:580px;border:10px solid #e6e6e6;'>"
			+ "<div style='height:100%;width:540px;margin:auto;'>"
			+ "<div style='background-color:#fefefe;width:100%;height:52px;padding: 20px 0px 10px 20px;'>"
			+ "<img src='http://imzhitu.qiniudn.com/images/mail/logo.png' />"
			+ "</div>"
			+ "<div style='padding: 0px 0px 10px 20px;border-top:2px solid #e6e6e6;border-bottom:2px solid #e6e6e6;'>"
			+ "<p style='font-size:16;color:#414241;font-weight:700;'>"
			+ "亲爱的";
	private String htmlBody = "：</p>"
			+ "<p style='font-size:16;color:#757574;font-weight:500;'>&nbsp;&nbsp;&nbsp;&nbsp;我们收到您的织图账户发来的修改密码申请。如果确认是您本人提出的申请，请点击以下按钮。</p>"
			+ "<p>"
			+ "<a href='";
	private String htmlNail = "' style='color:#ffffff;font-size:16;font-weight:700;background-color:#75c5f3;width:160px;height:44px;text-align:center;padding:13px 45px 13px 45px;text-decoration:none;'>"
			+ "重设密码"
			+ "</a>"
			+ "</p>"
			+ "<p style='font:16;color:#ee9194;font-weight:500;'>此链接有效期为两天，或直到设置密码成功为止。</p>"
			+ "<p style='font:16;color:#757574;font-weight:500;'>如果您没有提出过重置密码的申请，请忽略此邮件。"
			+ "有可能是其他用户提交申请时出错，我们不会对您的账户进行任何变更.</p>"
			+ "</div>"
			+ "<div>"
			+ "<p style='font:16;color:#757574;font-weight:500;text-align:right'>织图团队<br/>";
	private String htmlEnd = "</p>"
			+ "</div>"
			+ "</div>"
			+ "</div>"
			+ "</body>"
			+ "</html>";
	
	public void setResetPwdURLHead(String resetPwdURLHead){
		this.resetPwdURLHead = resetPwdURLHead;
	}
	
	public String getResetPwdURLHead(){
		return this.resetPwdURLHead;
	}
	
	@Override
	public boolean resetPWD(String loginCode,String sid,String pwd)throws Exception{
		RetrievePasswordDto dto = retrievePWDDao.queryRetrievePasswordByLoginCode(loginCode);
		boolean r = MD5Encrypt.validatePassword(sid, dto.getSid());
		if(r){
			try{
				retrievePWDDao.resetPWD(loginCode, MD5Encrypt.encryptByMD5(pwd));
				retrievePWDDao.deleteRPWDByLoginCode(loginCode);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("重置密码失败");
			}			
		}
		return false;
	}
	
	@Override
	public void requestRPWD(String loginCode)throws Exception{
		if(loginCode==null)return;//检查logincode是否为空
//		boolean ckur = retrievePWDDao.checkUserInfoIsExist(loginCode);//检查该账号是否存在于user_info
//		if(ckur == false)return;
		String userName = retrievePWDDao.getUserNameByLoginCode(loginCode);
		if(null == userName)return;
		boolean ckpr = retrievePWDDao.checkRPWDIsExist(loginCode);//检查该账号是否存在于retrieve_password中
		if(ckpr == true){//若存在则删除
			retrievePWDDao.deleteRPWDByLoginCode(loginCode);
		}
		//获取sid
		//用logincode+当前时间/一个随机数
		Date now = new Date();//当前时间
		int cr = GetRandomNum();//随机数
		Long tr = now.getTime()/cr;//当前时间除以随机数
		String sidCode = tr.toString();//用于编码的字符串,不知道是否太简单了，以后再改!!!!
		byte[] sid = MD5Encrypt.encryptByMD5(sidCode);//用md5加密，获取sid
		String url = resetPwdURLHead+"user/rpwd_resetPWD?loginCode="+loginCode+"&sid="+sidCode;
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		
		Date end_time = new Date(now.getTime()+validTime);
		retrievePWDDao.saveRPWD(new RetrievePasswordDto(loginCode,sid,end_time));
		//发送邮件
		try{
			Runnable run = new SendMailRunnable(loginCode, "重置织图密码", htmlHead+userName+htmlBody+url+htmlNail+dft.format(now)+htmlEnd);
			Thread thread = new Thread(run);
			thread.start();
		}catch(Exception e){
			e.printStackTrace();
			retrievePWDDao.deleteRPWDByLoginCode(loginCode);
			throw new Exception("邮件发送失败！");
		}
	}
	
	private int GetRandomNum(){//返回的数据范围1~99
		int r;
		do{
			r = (int)(Math.random()*100);
		}while(r<1);
		return r;
	}
}
