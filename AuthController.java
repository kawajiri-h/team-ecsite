package jp.co.internous.team2505.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jp.co.internous.team2505.model.domain.MstUser;
import jp.co.internous.team2505.model.form.UserForm;
import jp.co.internous.team2505.model.mapper.MstUserMapper;
import jp.co.internous.team2505.model.mapper.TblCartMapper;
import jp.co.internous.team2505.model.session.LoginSession;

/**
 * 認証に関する処理を行うコントローラー
 * @author インターノウス
 *
 */
@RestController
@RequestMapping("/team2505/auth")
public class AuthController {
	/*
	 * フィールド定義
	 */
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private TblCartMapper cartMapper; 

	private Gson gson = new Gson();
		
	/**
	 * ログイン処理をおこなう
	 * @param f ユーザーフォーム
	 * @return ログインしたユーザー情報(JSON形式)
	 */
	@PostMapping("/login")
	public String login(@RequestBody UserForm f) {
		MstUser user = userMapper.findByUserNameAndPassword(f.getUserName(), f.getPassword());
		
		if(user != null) {
			cartMapper.updateUserId(user. getId(), loginSession.getTmpUserId());
			loginSession.setUserId(user.getId());
			loginSession.setTmpUserId(0);
		    loginSession.setUserName(user.getUserName());
		    loginSession.setPassword(user.getPassword());
		    loginSession.setLoggedIn(true);
		    
			int tmpUserId = loginSession.getTmpUserId();
		    if (tmpUserId != 0) {
		    	cartMapper.updateUserId(user. getId(), tmpUserId);
		    	loginSession.setTmpUserId(0);
		    }
		    
		} else {
			loginSession.setUserId(0);
			loginSession.setUserName(null);
			loginSession.setPassword(null);
			loginSession.setLoggedIn(false);
		}
		
		return gson.toJson(user);
	}
	
	/**
	 * ログアウト処理をおこなう
	 * @return 空文字
	 */
	@PostMapping("/logout")
	public String logout() {
		loginSession.setUserId(0);
		loginSession.setTmpUserId(0);
		loginSession.setUserName(null);
		loginSession.setPassword(null);
		loginSession.setLoggedIn(false);
		
		return "";
	}
	
	/**
	 * パスワード再設定をおこなう
	 * @param f ユーザーフォーム
	 * @return 処理後のメッセージ
	 */
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody UserForm f) {
		String newPassword = f.getNewPassword();
		
		loginSession.setPassword(newPassword);
		
		String userName = loginSession.getUserName();
		int result = userMapper.updatePassword(userName, newPassword);
		
		if (result > 0) {
			return "パスワードが再設定されました。";
		}
		return "";
	}
	
}
