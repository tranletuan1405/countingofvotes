package vn.edu.uit.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.BarcodeGenerator;
import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.Barcode;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.json.CongressJson;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.unit.UnitService;

@Controller
public class CongressController {

	private static final Logger logger = LoggerFactory.getLogger(CongressController.class);
	private static final String BARCODE_DIRECTORY = "barcode\\";

	public CongressController() {
		logger.info("CongressController");
	}

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private BarcodeGenerator barcodeGenerator;

	@Autowired
	private CongressService congressService;

	@Autowired
	private DelegateService delegateService;

	@Autowired
	private UnitService unitService;

	@RequestMapping(value = "/")
	public ModelAndView congress() {
		ModelAndView model = new ModelAndView("congress");

		return model;
	}

	@RequestMapping(value = "/congress_table", method = RequestMethod.GET)
	@ResponseBody
	public String congressList() throws JsonProcessingException {

		ListHolder<CongressJson> json = new ListHolder<CongressJson>();
		json.setData(congressService.fetchJson());

		return mapper.writeValueAsString(json);
	}

	@RequestMapping(value = "/checking_delegates_file", method = RequestMethod.POST)
	@ResponseBody
	public String checkingDelegatesFile(@RequestParam(value = "delegatesFile") MultipartFile delegatesFile,
			HttpServletRequest request) throws IOException {

		HttpSession session = request.getSession();
		session.setAttribute("delegatesIS", delegatesFile.getInputStream());

		return "success";
	}

	@RequestMapping(value = "/delegates_table")
	@ResponseBody
	public String delegatesTable(HttpServletRequest request) throws IOException {

		HttpSession session = request.getSession();
		InputStream is = (InputStream) session.getAttribute("delegatesIS");
		if (is == null) return "";

		List<Delegate> delegates = delegateService.getByDocument(is);
		session.setAttribute("delegates", delegates);

		ListHolder<DelegateJson> json = new ListHolder<DelegateJson>();
		for (int i = 0; i < delegates.size(); i++) {
			json.getData().add(new DelegateJson(delegates.get(i)));
		}

		return mapper.writeValueAsString(json);

	}

	@RequestMapping(value = "/create_congress", method = RequestMethod.POST)
	public ModelAndView createCongress(@RequestParam(value = "name") String name,
			@RequestParam(value = "startTime", required = false) String startTimeString,
			@RequestParam(value = "delegatesFile") MultipartFile delegatesFile,
			@RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
			@RequestParam(value = "bannerFile", required = false) MultipartFile bannerFile, HttpServletRequest request,
			RedirectAttributes redirectAttr) throws ParseException {

		ModelAndView model = new ModelAndView("redirect:/");
		HttpSession session = request.getSession();
		List<Delegate> delegates = (List<Delegate>) session.getAttribute("delegates");
		if (delegates == null || delegates.size() == 0) {
			redirectAttr.addFlashAttribute("error", "Không tìm thấy danh sách Đại biểu");
			return model;
		}

		Congress congress = new Congress();
		String congressPath = SupportMethods.dateToString(new Date(), "dd-MM-yyyy") + "_" + SupportMethods.getUID();

		// Create key & Iv
		String congressKey = SupportMethods.getRandomString(24);
		String congressIv = SupportMethods.getRandomString(8);

		// Check start time format
		Date startTime = SupportMethods.toDate(startTimeString, DataConfig.DATE_FORMAT);
		if (startTime == null)
			startTime = SupportMethods.toDate(new Date(), DataConfig.DATE_FORMAT);

		// Save object congress
		congress.setName(name);
		congress.setStartTime(startTime);
		congress.setCongressKey(congressKey);
		congress.setCongressIv(congressIv);
		congress.setCongressPath(congressPath);
		congressService.persist(congress);

		// Create barcode for each delegate
		for (int i = 0; i < delegates.size(); i++) {
			try {

				Delegate delegate = delegates.get(i);
				Barcode barcode = new Barcode();
				String content = SupportMethods.getUID();

				barcode.setContent(content);
				String imagePath = barcodeGenerator.generateQR("barcode\\" + congressPath, SupportMethods.getUID(),
						content, 320);
				barcode.setImagePath(imagePath);
				delegate.setHashCode(barcode);
				delegate.setCongress(congress);
				delegateService.persist(delegate);

			} catch (Exception e) {

			}
		}

		// Set Units
		List<Unit> units = unitService.fetch();
		for (int i = 0; i < units.size(); i++) {

			Unit unit = units.get(i);
			unit.setCongress(congress);
			unitService.persist(unit);
		}

		return model;
	}

}
