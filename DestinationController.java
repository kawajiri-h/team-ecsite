package jp.co.internous.team2505.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.team2505.model.domain.MstDestination;
import jp.co.internous.team2505.model.domain.MstUser;
import jp.co.internous.team2505.model.form.DestinationForm;
import jp.co.internous.team2505.model.mapper.MstDestinationMapper;
import jp.co.internous.team2505.model.mapper.MstUserMapper;
import jp.co.internous.team2505.model.session.LoginSession;

/**
 * 宛先情報に関する処理のコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/team2505/destination")
public class DestinationController {

	/*
	 * フィールド定義
	 */
	@Autowired
	private MstDestinationMapper mstDestinationMapper;

	@Autowired
	private LoginSession loginSession;

	@Autowired
	private MstUserMapper userMapper;

	private Gson gson = new Gson();

	/**
	 * 宛先画面を初期表示する
	 * @param m 画面表示用オブジェクト
	 * @return 宛先画面
	 */
	@RequestMapping("/")
	public String index(Model m) {
		if (loginSession.isLoggedIn()) {
			MstUser user = userMapper.findByUserNameAndPassword(
					loginSession.getUserName(),
					loginSession.getPassword());
			List<MstDestination> destinations = mstDestinationMapper.findByUserId(loginSession.getUserId());
			m.addAttribute("destinations", destinations);
			m.addAttribute("user", user);
		}

		m.addAttribute("loginSession", loginSession);
		return "destination";
	}

	/**
	 * 宛先情報を削除する
	 * @param destinationId 宛先情報ID
	 * @return true:削除成功、false:削除失敗
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/delete")
	@ResponseBody
	public boolean delete(@RequestBody String destinationId) {
		try {
			Map<String, String> map = gson.fromJson(destinationId, Map.class);
			String id = map.get("destinationId");
			int result = mstDestinationMapper.logicalDeleteById(Integer.parseInt(id));
			return result > 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 宛先情報を登録する
	 * @param f 宛先情報のフォーム
	 * @return 宛先情報id
	 */
	@PostMapping("/register")
	@ResponseBody
	public String register(@RequestBody DestinationForm f) {
		if (!loginSession.isLoggedIn()) {
			return "";
		}

		int userId = loginSession.getUserId();

		MstDestination destination = new MstDestination(f);
		destination.setUserId(userId);

		mstDestinationMapper.insert(destination);

		return String.valueOf(destination.getId());
	}
}
