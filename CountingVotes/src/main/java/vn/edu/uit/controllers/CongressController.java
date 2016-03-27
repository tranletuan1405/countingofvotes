package vn.edu.uit.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import vn.edu.uit.extra.TripleDes;
import vn.edu.uit.models.Barcode;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.json.CongressJson;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.json.UnitJson;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.delegate.support.EnumDelegateType;
import vn.edu.uit.models.service.delegate_type.DelegateTypeService;
import vn.edu.uit.models.service.unit.UnitService;

@Controller
public class CongressController {

	private static final Logger logger = LoggerFactory.getLogger(CongressController.class);

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

	@Autowired
	private DelegateTypeService typeService;

	@RequestMapping(value = { "/", "/home/**", "/list" })
	public ModelAndView congress() {
		ModelAndView model = new ModelAndView("congress_list");

		return model;
	}

	// LOAD TABLE
	@RequestMapping(value = "/congress_table", method = RequestMethod.GET)
	@ResponseBody
	public String congressList() throws JsonProcessingException {

		ListHolder<CongressJson> json = new ListHolder<CongressJson>();
		json.setData(congressService.fetchJson());

		return mapper.writeValueAsString(json);
	}

	// GET IMPORT FILE INFOMATION
	@RequestMapping(value = "/checking_delegates_file", method = RequestMethod.POST)
	@ResponseBody
	public String checkingDelegatesFile(@RequestParam(value = "delegatesFile") MultipartFile delegatesFile,
			HttpServletRequest request) throws IOException {

		HttpSession session = request.getSession();
		session.setAttribute("delegatesIS", delegatesFile.getInputStream());

		return "success";
	}

	// GET DELEGATES TABLE
	@RequestMapping(value = "/delegates_table")
	@ResponseBody
	public String delegatesTable(HttpServletRequest request) throws IOException {

		HttpSession session = request.getSession();
		InputStream is = (InputStream) session.getAttribute("delegatesIS");
		if (is == null)
			return "";

		List<Delegate> delegates = delegateService.getByDocument(is);

		ListHolder<DelegateJson> json = new ListHolder<DelegateJson>();
		Map<String, UnitJson> units = new HashMap<String, UnitJson>();

		for (int i = 0; i < delegates.size(); i++) {
			Delegate delegate = delegates.get(i);
			Unit unit = delegate.getUnit();
			DelegateType type = delegate.getType();

			json.getData().add(new DelegateJson(delegates.get(i)));

			if (unit != null && unit.getName() != null && !unit.getName().isEmpty()) {

				UnitJson unitJson = units.get(unit.getName());
				if (unitJson == null) {
					unitJson = new UnitJson();
					unitJson.setName(unit.getName());
					units.put(unit.getName(), unitJson);
				}

				try {
					String typeName = type.getShortName();
					EnumDelegateType enumType = EnumDelegateType.getEnumByDescription(typeName);

					switch (enumType) {
					case DBBC:
						unitJson.numOfDBBC++;
						unitJson.total++;
						break;
					case DBCD:
						unitJson.numOfDBCD++;
						unitJson.total++;
						break;
					case DBDK:
						unitJson.numOfDBDK++;
						unitJson.total++;
						break;
					case DBDN:
						unitJson.numOfDBDN++;
						unitJson.total++;
						break;
					}
				}

				catch (Exception e) {
				}
			}
		}

		session.setAttribute("delegates", delegates);
		session.setAttribute("units", units);

		return mapper.writeValueAsString(json);

	}

	// GET UNITS TABLE
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "units_table")
	@ResponseBody
	public String unitsTable(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		Map<String, UnitJson> unitsJson = (Map<String, UnitJson>) session.getAttribute("units");

		if (unitsJson == null)
			return "";

		ListHolder<UnitJson> json = new ListHolder<UnitJson>();
		for (Entry<String, UnitJson> entry : unitsJson.entrySet()) {
			json.getData().add(entry.getValue());
		}

		return mapper.writeValueAsString(json);
	}

	// CREATE NEW CONGRESS
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/create_congress", method = RequestMethod.POST)
	public ModelAndView createCongress(@RequestParam(value = "name") String name,
			@RequestParam(value = "startTime", required = false) String startTimeString,
			@RequestParam(value = "delegatesFile") MultipartFile delegatesFile,
			@RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
			@RequestParam(value = "bannerFile", required = false) MultipartFile bannerFile, HttpServletRequest request,
			RedirectAttributes redirectAttr) throws Exception {

		ModelAndView model = new ModelAndView("redirect:/");
		HttpSession session = request.getSession();
		List<Delegate> delegates = (List<Delegate>) session.getAttribute("delegates");
		if (delegates == null || delegates.size() == 0) {
			redirectAttr.addFlashAttribute("error", "Không tìm thấy danh sách Đại biểu");
			return model;
		}

		Congress congress = new Congress();
		String congressPath = SupportMethods.dateToString(new Date(), DataConfig.DATE_FORMAT_PATH) + "_"
				+ SupportMethods.getUID();

		// Create key & Iv
		String congressKey = SupportMethods.getRandomString(24);
		String congressIv = SupportMethods.getRandomString(8);
		TripleDes tDes = new TripleDes(congressKey, congressIv);

		// Check start time format
		Date startTime = SupportMethods.toDate(startTimeString, DataConfig.DATE_TIME_FORMAT);
		if (startTime == null)
			startTime = SupportMethods.toDate(new Date(), DataConfig.DATE_TIME_FORMAT);

		// Save object congress
		congress.setName(name);
		congress.setStartTime(startTime);
		congress.setCongressKey(congressKey);
		congress.setCongressIv(congressIv);
		congress.setCongressPath(congressPath);
		congressService.persist(congress);

		// Create delegate for each detected delegate
		for (int i = 0; i < delegates.size(); i++) {

			Delegate delegate = delegates.get(i);
			Barcode barcode = new Barcode();
			Unit unitTemp = delegate.getUnit();
			DelegateType typeTemp = delegate.getType();
			String content = tDes.encryptText(SupportMethods.getUID());

			// Barcode
			barcode.setContent(content);
			String directory = barcodeGenerator.generateQR(DataConfig.BARCODE_DIRECTORY + congressPath,
					SupportMethods.getUID(), content, 320);
			barcode.setImagePath(directory);

			// Unit
			if (unitTemp != null && unitTemp.getName() != null && !unitTemp.getName().isEmpty()) {
				Unit unit = unitService.fetch(unitTemp.getName(), congress.getId());

				if (unit == null) {
					unitTemp.setCongress(congress);
					unitService.persist(unitTemp);
				} else {
					delegate.setUnit(unit);
				}
			}

			// Type
			if (typeTemp != null && typeTemp.getShortName() != null && !typeTemp.getShortName().isEmpty()) {
				DelegateType type = typeService.fetch(typeTemp.getShortName());

				if (type == null) {
					typeService.persist(typeTemp);
				} else {
					delegate.setType(type);
				}
			}

			delegate.setHashCode(barcode);
			delegate.setCongress(congress);
			delegateService.persist(delegate);

		}

		return model;
	}

}
