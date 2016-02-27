package vn.edu.uit.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.uit.extra.ListHolder;
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

	@RequestMapping(value = "/{id}")
	public ModelAndView congressDetail(@PathVariable("id") long id, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("congress_detail");
		HttpSession session = request.getSession();
		session.setAttribute("congress_id", id);
		Congress congress = congressService.fetch(id);

		model.addObject("congress", congress);

		return model;
	}

	@RequestMapping(value = "/delegates")
	@ResponseBody
	public String delegates(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		long id = (Long) session.getAttribute("congress_id");
		ListHolder<DelegateJson> json = new ListHolder<DelegateJson>();
		List<Delegate> delegates = new ArrayList<Delegate>(congressService.fetch(id).getDelegates());

		for (int i = 0; i < delegates.size(); i++) {
			json.getData().add(new DelegateJson(delegates.get(i)));
		}

		return mapper.writeValueAsString(json);
	}

	@RequestMapping(value = "/units")
	@ResponseBody
	public String units(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("congress_id");
		ListHolder<UnitJson> json = new ListHolder<UnitJson>();
		List<Unit> units = unitService.fetch(id);
		List<DelegateType> types = typeService.fetch();

		for (int i = 0; i < units.size(); i++) {
			Unit unit = units.get(i);
			UnitJson unitJson = new UnitJson(unit);

			for (int j = 0; j < types.size(); j++) {
				DelegateType delegateType = types.get(j);
				long result = 0;

				EnumDelegateType type = EnumDelegateType.getEnumByDescription(delegateType.getShortName());
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

			long total = unitJson.getNumOfDBBC() + unitJson.getNumOfDBCD() + unitJson.getNumOfDBDK()
					+ unitJson.getNumOfDBDN();
			unitJson.setTotal(total);
			json.getData().add(unitJson);
		}

		return mapper.writeValueAsString(json);
	}

	@RequestMapping(value = "/delegate/{id}")
	@ResponseBody
	public ModelAndView getDelegate(
			@PathVariable("id") long id,
			HttpServletRequest request){
		ModelAndView model = new ModelAndView("fragment/delegate");
		HttpSession session = request.getSession();
		long congressId = (Long) session.getAttribute("congress_id");
		
		Delegate delegate = delegateService.fetch(id);
		List<Unit> units = unitService.fetch(congressId);
		List<DelegateType> types = typeService.fetch();
		
		model.addObject("delegate", delegate);
		model.addObject("units", units);
		model.addObject("types", types);
		
		return model;
	}
}
