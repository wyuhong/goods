package com.dhc.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.commons.BeanKit;
import com.commons.StrKit;
import com.dhc.biz.IUserBiz;
import com.dhc.biz.impl.UserBizImpl;
import com.dhc.entity.User;
import com.servlet.BaseServlet;

public class UserAction extends BaseServlet {

	private IUserBiz ubiz = new UserBizImpl();

	public String existLoginname(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		String loginname = request.getParameter("loginname");
		boolean bl = ubiz.existLoginname(loginname);
		if (bl) {
			// 表中存在该登录名
			jo.put("status", "exist");
		} else {
			// 表中不存在该登录名
			jo.put("status", "noexist");
		}
		out.print(jo);
		out.flush();
		out.close();
		// 返回值为字符串，如果字符串中包含"f:"，这就是转发；如果字符串中包含"r:"，这就是重定向；如果return
		// null，既不转发也不重定向
		return null;
	}

	public String existEmail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		boolean bl = ubiz.existEmail(email);
		if (bl) {
			// 表中存在该登录名
			jo.put("status", "exist");
		} else {
			// 表中不存在该登录名
			jo.put("status", "noexist");
		}
		out.print(jo);
		out.flush();
		out.close();
		// 返回值为字符串，如果字符串中包含"f:"，这就是转发；如果字符串中包含"r:"，这就是重定向；如果return
		// null，既不转发也不重定向
		return null;
	}

	public String validateVerifyCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		String verifyCode = request.getParameter("verifyCode");
		boolean bl = verifyCode.equalsIgnoreCase(request.getSession().getAttribute("vCode").toString());
		if (bl) {
			// 校验通过
			jo.put("status", "pass");
		} else {
			// 校验未通过
			jo.put("status", "nopass");
		}
		out.print(jo);
		out.flush();
		out.close();
		// 返回值为字符串，如果字符串中包含"f:"，这就是转发；如果字符串中包含"r:"，这就是重定向；如果return
		// null，既不转发也不重定向
		return null;
	}

	public String validateOldPass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		String loginpass = request.getParameter("loginpass");
		User user = (User) request.getSession().getAttribute("sessionUser");
		boolean bl = loginpass.equals(user.getLoginpass());
		if (bl) {
			// 校验通过
			jo.put("status", "pass");
		} else {
			// 校验未通过
			jo.put("status", "nopass");
		}
		out.print(jo);
		out.flush();
		out.close();
		// 返回值为字符串，如果字符串中包含"f:"，这就是转发；如果字符串中包含"r:"，这就是重定向；如果return
		// null，既不转发也不重定向
		return null;
	}

	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求流中的数据封装到User对象中
		User user = BeanKit.toBean(request.getParameterMap(), User.class);
		// 后台校验
		Map<String, String> errors = validateRegist(user, request.getSession());
		if (errors != null && errors.size() > 0) {
			request.setAttribute("user", user);
			request.setAttribute("errors", errors);
			return "f:/jsps/user/regist.jsp";
		}
		// 注册用户
		ubiz.regist(user);
		// 保存成功信息，转发到下一个页面
		request.setAttribute("code", "success");
		request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱完成激活！");
		return "f:/jsps/msg.jsp";
	}

	private Map<String, String> validateRegist(User formUser, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		String loginname = formUser.getLoginname();
		if (StrKit.isBlank(loginname)) {
			errors.put("loginname", "用户名不能为空！");
		} else if (loginname.length() < 6 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在6~20之间！");
		} else if (ubiz.existLoginname(loginname)) {
			errors.put("loginname", "用户名已被占用！");
		}

		String loginpass = formUser.getLoginpass();
		if (StrKit.isBlank(loginpass)) {
			errors.put("loginpass", "密码不能为空！");
		} else if (loginpass.length() < 6 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在6~20之间！");
		}

		String reloginpass = formUser.getReloginpass();
		if (StrKit.isBlank(reloginpass)) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if (!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}

		String email = formUser.getEmail();
		if (StrKit.isBlank(email)) {
			errors.put("email", "Email不能为空！");
		} else if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if (ubiz.existEmail(email)) {
			errors.put("email", "Email已被注册！");
		}

		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if (StrKit.isBlank(verifyCode)) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}

		return errors;
	}

	public String activation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String code = request.getParameter("activationCode");
		try {
			ubiz.activatioin(code);
			request.setAttribute("code", "success");
			request.setAttribute("msg", "恭喜，激活成功，请马上登录！");
		} catch (Exception e) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", e.getMessage());
		}

		return "f:/jsps/msg.jsp";
	}

	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 将请求流中的数据封装到User对象中
		User formUser = BeanKit.toBean(request.getParameterMap(), User.class);
		// 后台校验
		Map<String, String> errors = validateLogin(formUser, request.getSession());
		if (errors != null && errors.size() > 0) {
			request.setAttribute("user", formUser);
			request.setAttribute("errors", errors);
			return "f:/jsps/user/login.jsp";
		}

		//
		User user = ubiz.login(formUser);

		if (user == null) {
			request.setAttribute("msg", "用户名或密码错误！");
			request.setAttribute("user", formUser);
			return "f:/jsps/user/login.jsp";
		} else {
			if (!user.isStatus()) {
				request.setAttribute("msg", "您还没有激活！");
				request.setAttribute("user", formUser);
				return "f:/jsps/user/login.jsp";
			} else {
				//将正确的用户信息存放到session中
				request.getSession().setAttribute("sessionUser", user);
				//获取登录名
				String loginname = user.getLoginname();
				//通过UTF-8的字符集将loginname进行编码，便于网络传输，不会乱码
				loginname = URLEncoder.encode(loginname, "UTF-8");
				//创建一个cookie对象
				Cookie cookie = new Cookie("loginname", loginname);
				//设置cookie的最大存在时间，单位是秒，10天
				cookie.setMaxAge(60 * 60 * 24 * 10);
				//通过响应流把cookie写回浏览器端
				response.addCookie(cookie);
				//重定向回首页
				return "r:/index.jsp";
			}
		}
	}

	private Map<String, String> validateLogin(User formUser, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		String loginname = formUser.getLoginname();
		if (StrKit.isBlank(loginname)) {
			errors.put("loginname", "用户名不能为空！");
		} else if (loginname.length() < 6 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在6~20之间！");
		}

		String loginpass = formUser.getLoginpass();
		if (StrKit.isBlank(loginpass)) {
			errors.put("loginpass", "密码不能为空！");
		} else if (loginpass.length() < 6 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在6~20之间！");
		}

		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if (StrKit.isBlank(verifyCode)) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}

		return errors;
	}

	public String quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/jsps/user/login.jsp";
	}

	public String updatePass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("sessionUser");
		if (user == null) {
			request.setAttribute("msg", "您还没有登录！");
			return "f:/jsps/user/login.jsp";
		}

		// 将请求流中的数据封装到User对象中
		User formUser = BeanKit.toBean(request.getParameterMap(), User.class);
		// 后台校验表单内容
		Map<String, String> errors = validateUpdatePass(formUser, request.getSession());
		if (errors != null && errors.size() > 0) {
			request.setAttribute("user", user);
			request.setAttribute("errors", errors);
			return "f:/index.jsp";
		}
		formUser.setUid(user.getUid());
		ubiz.updatePass(formUser);
		// 保存成功信息，转发到下一个页面
		request.setAttribute("code", "success");
		request.setAttribute("msg", "密码修改成功！");
		return "f:/jsps/msg.jsp";

	}

	private Map<String, String> validateUpdatePass(User formUser, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		String loginpass = formUser.getLoginpass();
		if (StrKit.isBlank(loginpass)) {
			errors.put("loginpass", "原密码不能为空！");
		} else if (loginpass.length() < 6 || loginpass.length() > 20) {
			errors.put("loginpass", "原密码长度必须在6~20之间！");
		}

		String newpass = formUser.getNewpass();
		if (StrKit.isBlank(newpass)) {
			errors.put("newpass", "新密码不能为空！");
		} else if (newpass.length() < 6 || newpass.length() > 20) {
			errors.put("newpass", "新密码长度必须在6~20之间！");
		}

		String reloginpass = formUser.getReloginpass();
		if (StrKit.isBlank(reloginpass)) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if (!reloginpass.equals(newpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}

		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if (StrKit.isBlank(verifyCode)) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		return errors;
	}

}
