package com.version4.mvc.chapter56.controller;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.version4.mvc.chapter56.domain.Spitter;
import com.version4.mvc.chapter56.domain.Spittle;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;
import com.version4.mvc.chapter56.exception.SpittleNotFoundException;
import com.version4.mvc.chapter56.service.SpittleRepository;

@Controller("homeControllerVersion4")
@RequestMapping(value = "/mvc")
public class HomeController {

	@Autowired
	private SpittleRepository spittleRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(){
		return "mvc/home";
	}

	@RequestMapping(value = "/spitter/register", method = RequestMethod.GET)
	public String register(Model model){
		//我们将commandName属性设置为spitter，因此，在模型中必须要有一个key为spitter的对象
		model.addAttribute(new Spitter());
		return "mvc/registerForm";
	}

	@RequestMapping(value = "/spittles", method = RequestMethod.GET)
	public String spittles(@RequestParam(value = "max", defaultValue = "1") int max, @RequestParam(value = "count", defaultValue = "20") int count, Model model){
		//由于返回值是List<Spittle>，所以推断属性名是spittleList
		model.addAttribute(spittleRepository.findSpittles(max, count));
		return "mvc/spittles";
	}

	@RequestMapping(value = "/spittles/{spittleId}", method = RequestMethod.GET)
	//因为名字一致，所以可以省略@PathVariable里的value属性
	public String spittle(@PathVariable int spittleId, Model model){
		Spittle spittle = spittleRepository.findOne(spittleId);
		if(spittle == null){
			throw new SpittleNotFoundException();
		}
		model.addAttribute(spittleRepository.findOne(spittleId));
		return "mvc/spittle";
	}

	@RequestMapping(value = "/spitter/register", method = RequestMethod.POST)
	//MultipartFile的一个替代方案是javax.servlet.http.Part，并且使用Part必须是servlet3.0，而且可以不用配置MultipartResolver
	public String registerUser(@RequestPart("profilePicture") MultipartFile profilePicture, @Valid Spitter spitter, Errors errors, Model model, RedirectAttributes model2){
		if(errors.hasErrors()){
			return "mvc/registerForm";
		}
		if (profilePicture != null) {
			try {
				validateImage(profilePicture);

				saveImage("e:/zsl/1.jpg", profilePicture);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		spittleRepository.save(spitter);
		//redirect:重定向；forward:前往
		//return "redirect:/mvc/spitter/" + spitter.getUserName();
		//还可以通过模板重定向传递参数
		//model.addAttribute("userName", spitter.getUserName());
		//model.addAttribute("firstName", spitter.getFirstName());
		//url为：http://localhost/mvc/spitter/zhangsulei?firstName=123456，因为firstName没设置占位符，所有按查询参数形式传递
		//return "redirect:/mvc/spitter/{userName}";
		//通过flash直接传递对象，其实就是把对象保存在会话中，然后下一个请求获取后再从会话中删除，这时showSpitterProfile方法也要修改一下
		model2.addAttribute("userName", spitter.getUserName());
		model2.addFlashAttribute("spitter", spitter);
		return "redirect:/mvc/spitter/{userName}";
	}

	@RequestMapping(value = "/spitter/{userName}", method = RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String userName, Model model){
		//先检查是否能从flash中获取，对应上面的flash直接传递对象的方式
		if(!model.containsAttribute("spitter")){
			//由于返回值是List<Spittle>，所以推断属性名是spittleList
			model.addAttribute(spittleRepository.findByUserName(userName));
		}

		return "mvc/profile";
	}

	private void validateImage(MultipartFile image) throws Exception {
		if (!image.getContentType().equals("image/jpeg")) {
			throw new Exception("Only JPG images accepted");
		}
	}

	private void saveImage(String filename, MultipartFile image)
			throws Exception {
		File f = new File(filename);
		FileUtils.writeByteArrayToFile(f, image.getBytes());
	}

	private void validateImage(Part image) throws Exception {
		if (!image.getContentType().equals("image/jpeg")) {
			throw new Exception("Only JPG images accepted");
		}
	}

	private void saveImage(String filename, Part image)
			throws Exception {
		image.write(filename);
	}

	/**
     * 异常页面控制
     * 当这个Controller中任何一个方法发生异常，一定会被这个方法拦截到。然后，输出日志。封装Map并返回，页面上得到status为false。
     * @param runtimeException
     * @return

    @ExceptionHandler(DuplicateSpittleException.class)
	@ResponseBody
	public ModelAndView runtimeExceptionHandler(DuplicateSpittleException ex) {
        System.out.println(ex.getMessage());

        ModelAndView model = new ModelAndView("mvc/error");
		model.addObject("errCode", ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());
        return model;
    }
     */

    /**
     * 或者直接返回页面
     * 异常页面控制
     *
     * @param runtimeException
     * @return

    @ExceptionHandler(DuplicateSpittleException.class)
    public String runtimeExceptionHandler(RuntimeException runtimeException, ModelMap modelMap) {
    	System.out.println(runtimeException.getLocalizedMessage());

        modelMap.put("status", false);
        return "exception";
    }
	*/

}
