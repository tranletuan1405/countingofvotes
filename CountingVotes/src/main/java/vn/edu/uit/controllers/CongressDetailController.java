package vn.edu.uit.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.ListHolder;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.json.DelegateJson;
import vn.edu.uit.models.json.UnitJson;
import vn.edu.uit.models.service.congress.CongressService;
import vn.edu.uit.models.service.delegate.DelegateService;
import vn.edu.uit.models.service.delegate.support.EnumDelegateType;
import vn.edu.uit.models.service.delegate_type.DelegateTypeService;
import vn.edu.uit.models.service.unit.UnitService;

@Controller
@RequestMapping(value = "detail/**")
public class CongressDetailController {

	private static final Logger logger = LoggerFactory.getLogger(CongressDetailController.class);

	@Autowired
	private CongressService congressService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private DelegateTypeService typeService;

	@Autowired
	private DelegateService delegateService;

	@Autowired
	private ObjectMapper mapper;

	// Load Detail Page With New Session
	@RequestMapping(value = "/{id}")
	public ModelAndView congressDetail(@PathVariable("id") long id, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("congress_detail");
		HttpSession session = request.getSession();
		session.setAttribute(DataConfig.SESSION_NAME, id);
		Congress congress = congressService.fetch(id);

		long attendees = delegateService.getNumOfAttendees(id);

		model.addObject("attendees", attendees);
		model.addObject("congress", congress);
		
		model.addObject(DataConfig.DETAIL_ACTIVE, DataConfig.DETAIL_ACTIVE);
		
		return model;
	}
	
	//Load Detail Page With Old Session
	@RequestMapping(value = "/this")
	public ModelAndView congressDetailOld(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("congress_detail");
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		Congress congress = congressService.fetch(id);

		long attendees = delegateService.getNumOfAttendees(id);

		model.addObject("attendees", attendees);
		model.addObject("congress", congress);
		
		model.addObject(DataConfig.DETAIL_ACTIVE, DataConfig.DETAIL_ACTIVE);
		
		return model;
	}

	// Load delegates table
	@RequestMapping(value = "/delegates")
	@ResponseBody
	public String delegates(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		ListHolder<DelegateJson> json = new ListHolder<DelegateJson>();
		List<Delegate> delegates = new ArrayList<Delegate>(congressService.fetch(id).getDelegates());

		for (int i = 0; i < delegates.size(); i++) {
			json.getData().add(new DelegateJson(delegates.get(i)));
		}

		return mapper.writeValueAsString(json);
	}

	// Load units table
	@RequestMapping(value = "/units")
	@ResponseBody
	public String units(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		ListHolder<UnitJson> json = new ListHolder<UnitJson>();
		List<Unit> units = unitService.fetch(id);
		List<DelegateType> types = typeService.fetch();

		for (int i = 0; i < units.size(); i++) {
			Unit unit = units.get(i);
			UnitJson unitJson = new UnitJson(unit);

			for (int j = 0; j < types.size(); j++) {
				DelegateType delegateType = types.get(j);
				long result = 0;

				logger.info(unit.getName());
				logger.info(delegateType.getShortName());
				EnumDelegateType type = EnumDelegateType.getEnumByDescription(delegateType.getShortName());
			
				if (type != null) {
					switch (type) {
					case DBBC:
						result = delegateService.getNumOfDelegate(id, unit.getId(), delegateType.getId());
						unitJson.setNumOfDBBC(result);
						break;
					case DBCD:
						result = delegateService.getNumOfDelegate(id, unit.getId(), delegateType.getId());
						unitJson.setNumOfDBCD(result);
						break;
					case DBDK:
						result = delegateService.getNumOfDelegate(id, unit.getId(), delegateType.getId());
						unitJson.setNumOfDBDK(result);
						break;
					case DBDN:
						result = delegateService.getNumOfDelegate(id, unit.getId(), delegateType.getId());
						unitJson.setNumOfDBDN(result);
						break;
					}
				}
			}

			long total = unitJson.getNumOfDBBC() + unitJson.getNumOfDBCD() + unitJson.getNumOfDBDK()
					+ unitJson.getNumOfDBDN();
			unitJson.setTotal(total);
			json.getData().add(unitJson);
		}

		return mapper.writeValueAsString(json);
	}

	// Load delegate full information
	@RequestMapping(value = "/delegate/{id}")
	@ResponseBody
	public ModelAndView getDelegate(@PathVariable("id") long id, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("fragment/delegate");
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);

		Delegate delegate = delegateService.fetch(id);
		List<Unit> units = unitService.fetch(congressId);
		List<DelegateType> types = typeService.fetch();

		model.addObject("delegate", delegate);
		model.addObject("units", units);
		model.addObject("types", types);

		return model;
	}

	// Load unit full information
	@RequestMapping(value = "/unit/{id}")
	@ResponseBody
	public ModelAndView getUnit(@PathVariable("id") long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		ModelAndView model = new ModelAndView("fragment/unit");
		Unit unit = unitService.fetch(id, congressId);
		model.addObject("unit", unit);
		return model;
	}

	// Update attended delegate's field
	@RequestMapping(value = "/update_attended/{id}", method = RequestMethod.POST, produces = {
			"application/json; charset=UTF-8" })
	@ResponseBody
	public String updateAttended(@RequestParam("attended") boolean attended, @PathVariable("id") long id)
			throws JsonProcessingException {
		Delegate delegate = delegateService.fetch(id);
		delegate.setAttended(attended);

		Date arivalTime = delegate.getArivalTime();
		if (arivalTime == null && attended) {
			arivalTime = SupportMethods.toDate(new Date(), DataConfig.DATE_TIME_FORMAT);
			delegate.setArivalTime(arivalTime);
		} else if (arivalTime != null && !attended) {
			delegate.setArivalTime(null);
		}

		delegateService.update(delegate);
		String json = mapper.writeValueAsString(new DelegateJson(delegate));

		return json;
	}

	// Update congress information
	@RequestMapping(value = "update_congress/{field}", method = RequestMethod.POST, produces = {
			"application/text; charset=UTF-8" })
	@ResponseBody
	public String updateCongressInfo(@PathVariable("field") String field, @RequestParam(value = "value") String value,
			HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute(DataConfig.SESSION_NAME);
		if (value == null || value.isEmpty())
			return "";

		Congress congress = congressService.fetch(id);

		if (field.equals("name")) {
			if (value.equals(congress.getName()))
				return "";
			
			congress.setName(value);
			congressService.update(congress);
			return value;
		} else if (field.equals("startTime")) {
			Date date = SupportMethods.toDate(value, DataConfig.DATE_TIME_FORMAT);
			if (date != null) {
				if (congress.getEndTime() != null && date.after(congress.getEndTime()))
					return "";
				congress.setStartTime(date);
				congressService.update(congress);
				return value;
			}
		} else if (field.equals("endTime")) {
			Date date = SupportMethods.toDate(value, DataConfig.DATE_TIME_FORMAT);
			if (date != null) {
				if (congress.getStartTime() != null && date.before(congress.getStartTime()))
					return "";
				congress.setEndTime(date);
				congressService.update(congress);
				return value;
			}
		}

		return "";
	}
}
