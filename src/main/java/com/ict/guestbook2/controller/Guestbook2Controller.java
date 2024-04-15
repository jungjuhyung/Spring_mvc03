package com.ict.guestbook2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.guestbook2.dao.GB2_VO;
import com.ict.guestbook2.service.GB2_Service;

@Controller
public class Guestbook2Controller {
	@Autowired
	private GB2_Service service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("gb2_list2.do")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("guestbook2/list");
		List<GB2_VO> list = service.getList();
		mv.addObject("list", list);
		return mv;
	}
	
	@GetMapping("gb2_write.do")
	public ModelAndView write() {
		ModelAndView mv = new ModelAndView("guestbook2/write");
		return mv;
	}
	
	@PostMapping("gb2_write_ok.do")
	public ModelAndView writeOK(GB2_VO vo, HttpServletRequest request) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/resources/upload");
			MultipartFile file = vo.getFile();
			if (file.isEmpty()) {
				vo.setF_name("");
			}else {
				UUID uuid = UUID.randomUUID();
				String f_name = uuid.toString()+"_"+file.getOriginalFilename();
				vo.setF_name(f_name);
				
				byte[] in = file.getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
				
				String epwd =passwordEncoder.encode(vo.getPwd());
				vo.setPwd(epwd);
				
				int result = service.getInsert(vo);
				if (result > 0) {
					return new ModelAndView("redirect:gb2_list2.do");
				}else {
					return new ModelAndView("guestbook2/error");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("guestbook2/error");
	}
	
	@GetMapping("gb2_detail.do")
	public ModelAndView detail(String idx) {
		ModelAndView mv = new ModelAndView("guestbook2/onelist");
		GB2_VO vo = service.getDetail(idx);
		mv.addObject("vo", vo);
		return mv;
	}
	
	@GetMapping("gb2_down.do")
	public void down(HttpServletRequest request, HttpServletResponse response) {
		try {
			String f_name = request.getParameter("f_name");
			String path = request.getSession().getServletContext().getRealPath("/resources/upload/"+f_name);
			String r_path = URLEncoder.encode(path, "UTF-8");
			
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + r_path);
			
			File file = new File(new String(path.getBytes(), "UTF-8"));
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(in, out);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@PostMapping("gb2_delete.do")
	public ModelAndView delete(String idx) {
		ModelAndView mv = new ModelAndView("guestbook2/delete");
		mv.addObject("idx", idx);
		return mv;
	}
	@PostMapping("gb2_delete_ok.do")
	public ModelAndView deleteOK(String idx, GB2_VO vo) {
		ModelAndView mv = new ModelAndView();
		String cpwd = vo.getPwd();
		GB2_VO dbvo = service.getDetail(idx);
		String dbpwd = dbvo.getPwd();
		if (!passwordEncoder.matches(cpwd,dbpwd)) {
			mv.setViewName("guestbook2/delete");
			mv.addObject("pwdcheck", "fail");
			mv.addObject("idx", idx);
			return mv;
		}else {
			int result = service.getDelete(idx);
			if (result > 0) {
				mv.setViewName("redirect:gb2_list2.do");
				return mv;
			}
			return new ModelAndView("guestbook2/error");
		}
		
	}
}
