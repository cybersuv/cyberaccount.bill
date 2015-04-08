package com.suv.flat.bill.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.suv.flat.bill.bo.BillingBO;
import com.suv.flat.bill.bo.UserBO;
import com.suv.flat.bill.bo.impl.BillingBoImpl;
import com.suv.flat.bill.common.Constants;
import com.suv.flat.bill.vo.AuthResponse;
import com.suv.flat.bill.vo.TxResponse;
import com.suv.flat.bill.vo.entity.Accounts;
import com.suv.flat.bill.vo.entity.FlatBill;
import com.suv.flat.bill.vo.entity.GlobalBill;
import com.suv.flat.bill.vo.entity.User;



@Controller
public class BillingController {

	@Value("${application.name}")
	String appName;

	UserBO userBo;
	BillingBO billinglBo;

	// DI by Spring
	public void setUserBo(UserBO userBo) {
		this.userBo = userBo;
	}
	// DI by Spring
	public void setBillingBo(BillingBO billingBo){
		this.billinglBo=billingBo;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showLoginPage(ModelMap model) {
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		return new ModelAndView(Constants.LOGIN_PAGE, "command", new User());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String authenticateUser(@ModelAttribute("SpringWeb") User user,
			HttpServletRequest request, ModelMap model) {
		// UserDAO userDao=new UserDAOImpl();
		AuthResponse authresponse = userBo.authenticate(user);
		if (authresponse.isStatus()) {
			request.getSession().setAttribute("authuser",
					authresponse.getUser());
			model.addAttribute(Constants.APPLICATION_NAME, appName);
			return Constants.WELCOME_PAGE;
		} else {
			user.setPassword("");
			model.addAttribute("command", user);
			model.addAttribute(Constants.APPLICATION_NAME, appName);
			// model.addAttribute("orgList", CoreService.getOrganisationsMap());
			model.addAttribute("loginmessage", authresponse.getExceptionMsg());
			return Constants.LOGIN_PAGE;
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String processLogout(SessionStatus status, HttpSession session,
			ModelMap model) {
		status.setComplete();
		session.invalidate();
		return "redirect:/";
	}
	
	
	/** Initbinder **/
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	/** Global Bill Requests **/
	
	@RequestMapping(value = "/globalentry", method = RequestMethod.GET)
	public ModelAndView showGlobalEntryPage(ModelMap model) {
		List<String> meterTypes=new ArrayList<String>();
		meterTypes.add(Constants.METER_TYPE_COMMON);
		meterTypes.add(Constants.METER_TYPE_FLAT);
		model.addAttribute(Constants.METER_LIST, meterTypes);
		model.addAttribute(Constants.OPERATION,Constants.OPERATION_NEW);
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		return new ModelAndView(Constants.GLOBAL_BILL_ENTRY_PAGE, "command", new GlobalBill());
	}
	
	@RequestMapping(value = "/getglobalentry", method = RequestMethod.GET)
	public ModelAndView showGlobalUpdatePage(ModelMap model,HttpServletRequest request) {
		GlobalBill paramBill=new GlobalBill();
		paramBill.setGlobalBillId(Long.parseLong(request.getParameter("billId")));
		List<String> meterTypes=new ArrayList<String>();
		meterTypes.add(Constants.METER_TYPE_COMMON);
		meterTypes.add(Constants.METER_TYPE_FLAT);
		model.addAttribute(Constants.METER_LIST, meterTypes);
		model.addAttribute(Constants.OPERATION,Constants.OPERATION_UPDATE);
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		return new ModelAndView(Constants.GLOBAL_BILL_ENTRY_PAGE, "command", billinglBo.getGlobalBill(paramBill));
	}

	@RequestMapping(value = "/globalentry", method = RequestMethod.POST)
	public @ResponseBody 
	String executeGlobalEntry(@ModelAttribute("SpringWeb") GlobalBill globalBill,
			HttpServletRequest request, ModelMap model) {
		TxResponse tx;
		String operation=request.getParameter("operation");
		if(operation.equals("new")){
			tx=billinglBo.addGlobalBill(globalBill);
		}else{
			tx=billinglBo.updateGlobalBill(globalBill);
		}
		return tx.isStatus() + "|" + tx.getResponseMsg() + "|" + tx.getResponseId();
	}
	
	@RequestMapping(value = "/deleteGlobalBill", method = RequestMethod.GET)
	public @ResponseBody
	TxResponse deleteGlobalBill(@RequestParam String billId) {
		GlobalBill bill= new GlobalBill();
		bill.setGlobalBillId(Long.parseLong(billId));
		bill=billinglBo.getGlobalBill(bill);
		return billinglBo.deleteGlobalBill(bill);
 	}

	
	/** Flat Bill Requests **/
	
	@RequestMapping(value = "/flatentry", method = RequestMethod.GET)
	public ModelAndView showFlatEntryPage(ModelMap model) {
		List<String> monthStamps=billinglBo.getMonthStamps();
		model.addAttribute(Constants.STR_MONTH_STAMPS, monthStamps);
		model.addAttribute(Constants.OPERATION,Constants.OPERATION_NEW);
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		return new ModelAndView(Constants.FLAT_BILL_ENTRY_PAGE, "command", new FlatBill());
	}
	
	@RequestMapping(value = "/getflatentry", method = RequestMethod.GET)
	public ModelAndView showFlatUpdatePage(ModelMap model,HttpServletRequest request) {
		FlatBill paramBill=new FlatBill();
		paramBill.setFlatBillId(Long.parseLong(request.getParameter("billId")));
		List<String> monthStamps=billinglBo.getMonthStamps();
		model.addAttribute(Constants.STR_MONTH_STAMPS, monthStamps);
		model.addAttribute(Constants.OPERATION,Constants.OPERATION_UPDATE);
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		return new ModelAndView(Constants.FLAT_BILL_ENTRY_PAGE, "command", billinglBo.getFlatBill(paramBill));
	}

	@RequestMapping(value = "/flatentry", method = RequestMethod.POST)
	public @ResponseBody 
	String executeFlatEntry(@ModelAttribute("SpringWeb") FlatBill flatBill,
			HttpServletRequest request, ModelMap model) {
		TxResponse tx;
		String operation=request.getParameter("operation");
		if(operation.equals("new")){
			tx=billinglBo.addFlatBill(flatBill);
		}else{
			tx=billinglBo.updateFlatBill(flatBill);
		}
		return tx.isStatus() + "|" + tx.getResponseMsg() + "|" + tx.getResponseId();
	}
	
	
	@RequestMapping(value = "/deleteFlatBill", method = RequestMethod.GET)
	public @ResponseBody
	TxResponse deleteFlatBill(@RequestParam String billId) {
		FlatBill bill=new FlatBill();
		bill.setFlatBillId(Long.parseLong(billId));
		bill=billinglBo.getFlatBill(bill);
		return billinglBo.deleteFlatBill(bill);
 	}
	
	
	
	@RequestMapping(value = "/getOwners", method = RequestMethod.GET)
	public @ResponseBody
	List<Accounts> getTags(@RequestParam String term) {
		return billinglBo.getAccountBySearchString(term);
 	}
	
	
	@RequestMapping(value = "/getGBReportUI", method = RequestMethod.GET)
	public String showGBReportPage(ModelMap model) {
		List<String> monthStamps=billinglBo.getMonthStamps();
		//System.out.println("MonthStamp List Size : " + monthStamps.size());
		model.addAttribute("monthStamps", monthStamps);
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		model.addAttribute(Constants.REPORT_TYPE,Constants.REPORT_TYPE_GLOBAL_BILL);
		return Constants.BILL_REPORT_UI_PAGE;
	}
	
	@RequestMapping(value = "/getFBReportUI", method = RequestMethod.GET)
	public String showFBReportPage(ModelMap model) {
		List<String> monthStamps=billinglBo.getMonthStamps();
		//System.out.println("MonthStamp List Size : " + monthStamps.size());
		model.addAttribute("monthStamps", monthStamps);
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		model.addAttribute(Constants.REPORT_TYPE,Constants.REPORT_TYPE_FLAT_BILL);
		return Constants.BILL_REPORT_UI_PAGE;
	}
	
	@RequestMapping(value = "/getSummaryReportUI", method = RequestMethod.GET)
	public String showSummaryReportPage(ModelMap model) {
		List<String> monthStamps=billinglBo.getMonthStamps();
		//System.out.println("MonthStamp List Size : " + monthStamps.size());
		model.addAttribute("monthStamps", monthStamps);
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		model.addAttribute(Constants.REPORT_TYPE,Constants.REPORT_TYPE_SUMMARY);
		return Constants.BILL_REPORT_UI_PAGE;
	}

	
	
	@RequestMapping(value = "/getBillReport", method = RequestMethod.POST)
	public String showBillReport(ModelMap model,HttpServletRequest request) {
		String reportType=request.getParameter(Constants.STR_REPORT_TYPE);
		String monthStr=request.getParameter(Constants.STR_MONTH_STAMPS);
		System.out.println("ReportType : " + reportType + " And MonthString : " + monthStr);
		model.addAttribute("responseTable", billinglBo.getBillReport(monthStr, reportType));
		return Constants.BILL_REPORT_PAGE;
	}
	
	
	@RequestMapping(value = "/getProcessBillUI", method = RequestMethod.GET)
	public String showProcessBillPage(ModelMap model) {
		List<String> monthStamps=billinglBo.getMonthStamps();
		//System.out.println("MonthStamp List Size : " + monthStamps.size());
		model.addAttribute("monthStamps", monthStamps);
		model.addAttribute(Constants.APPLICATION_NAME, appName);
		return Constants.PROCESS_BILL_UI_PAGE;
	}
	
	@RequestMapping(value = "/processbill", method = RequestMethod.POST)
	public @ResponseBody String processBill(ModelMap model,HttpServletRequest request) {
		String monthStr=request.getParameter(Constants.STR_MONTH_STAMPS);
		System.out.println("MonthString : " + monthStr);
		TxResponse tx=billinglBo.processBill(monthStr);
		String respStr="";
		if(tx.isStatus()){
			respStr="<center><font color='green'>" + tx.getResponseMsg() + "</font></center>";
		}else{
			respStr="<center><font color='red'>" + tx.getResponseMsg() + "</font></center>";
		}
		return respStr;
	}
	
	@RequestMapping(value = "/reportindex", method = RequestMethod.GET)
	public String showReportIndexPage(ModelMap model) {
		return Constants.REPORT_INDEX_PAGE;
	}
	
	
	

}
