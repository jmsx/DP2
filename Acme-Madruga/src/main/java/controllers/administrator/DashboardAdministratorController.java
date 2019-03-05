
package controllers.administrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.BrotherhoodService;
import services.FinderService;
import services.MemberService;
import services.PositionService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Member;
import domain.Position;
import domain.Procession;

@Controller
@RequestMapping(value = "/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private PositionService		positionService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private AreaService			areaService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private MemberService		memberService;


	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public ModelAndView chart() {
		final ModelAndView result;

		final Map<Position, Long> positionsFrequency = this.positionService.getPositionsFrequency();
		Assert.notNull(positionsFrequency);
		final List<String> positions = new ArrayList<String>();
		final List<Long> frequencies = new ArrayList<Long>();
		for (final Map.Entry<Position, Long> entry : positionsFrequency.entrySet()) {
			positions.add(entry.getKey().getNameEnglish() + "/" + entry.getKey().getNameSpanish());
			frequencies.add(entry.getValue());
		}

		result = new ModelAndView("dashboard/chart"); //lleva al list.jsp
		result.addObject("positions2", positions);
		result.addObject("frequencies2", frequencies);
		result.addObject("requestURI", "dashboard/admnistrator/chart.do");

		return result;

	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public ModelAndView statistics() {
		final ModelAndView result;

		final Double averageResults = this.finderService.getAverageFinderResults();
		final Integer maxResults = this.finderService.getMaxFinderResults();
		final Integer minResults = this.finderService.getMinFinderResults();
		final Double desviationResults = this.finderService.getDesviationFinderResults();
		final Double ratioFinders = this.finderService.getRatioEmptyFinders();
		final Double[] statisticsMembersPerBrotherhood = this.brotherhoodService.getStatisticsOfMembersPerBrotherhood();
		final List<Brotherhood> smallestBrotherhood = this.brotherhoodService.getSmallestBrotherhood();
		final List<Brotherhood> largestBrotherhood = this.brotherhoodService.getLargestBrotherhood();
		final List<Procession> soon = this.processionService.getProcessionsThirtyDays();
		final Double requestApproved = this.requestService.findApprovedRequestRadio();
		final Double requestPending = this.requestService.findPendingRequestRadio();
		final Double requestRejected = this.requestService.findRejectedRequestRadio();
		final Double[] statisticsBrotherhoodsPerArea = this.areaService.getStatiticsBrotherhoodPerArea();
		final List<Member> membersTenPercent = this.memberService.getMembersTenPercent();

		result = new ModelAndView("dashboard/statistics"); //lleva al list.jsp
		result.addObject("requestURI", "dashboard/admnistrator/statistics.do");

		result.addObject("averageMembers", statisticsMembersPerBrotherhood[0]);
		result.addObject("minMembers", statisticsMembersPerBrotherhood[2]);
		result.addObject("maxMembers", statisticsMembersPerBrotherhood[1]);
		result.addObject("desviationMembers", statisticsMembersPerBrotherhood[3]);
		result.addObject("largest", largestBrotherhood);
		result.addObject("smallest", smallestBrotherhood);

		result.addObject("soon", soon);
		result.addObject("requestsApproved", requestApproved);
		result.addObject("requestsPending", requestPending);
		result.addObject("requestsRejected", requestRejected);
		result.addObject("membersPercent", membersTenPercent);
		result.addObject("minBrotherhoods", statisticsBrotherhoodsPerArea[2]);
		result.addObject("averageBrotherhoods", statisticsBrotherhoodsPerArea[0]);
		result.addObject("maxBrotherhoods", statisticsBrotherhoodsPerArea[1]);
		//result.addObject("ratioBrotherhoods",);
		result.addObject("averageResults", averageResults);
		result.addObject("minResults", minResults);
		result.addObject("maxResults", maxResults);
		result.addObject("desviationResults", desviationResults);
		result.addObject("ratioFinders", ratioFinders);

		return result;

	}
}
