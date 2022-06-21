package kr.wise.commons.bbs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.bbs.service.BBSAttributeManageService;
import kr.wise.commons.bbs.service.BBSManagerService;
import kr.wise.commons.bbs.service.Board;
import kr.wise.commons.bbs.service.BoardMaster;
import kr.wise.commons.bbs.service.BoardMasterVO;
import kr.wise.commons.bbs.service.BoardVO;
import kr.wise.commons.cmm.FileVO;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.pagenation.DefaultPaginationRenderer;
import kr.wise.commons.cmm.pagenation.PaginationInfo;
import kr.wise.commons.cmm.security.HTMLInputFilter;
import kr.wise.commons.cmm.security.XSS;
import kr.wise.commons.cmm.service.FileManagerService;
import kr.wise.commons.cmm.service.FileManagerUtil;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapService;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
@RequestMapping("/commons/bbs")
public class BBSManagerCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private BBSAttributeManageService bbsAttrbService ;

	@Inject
	private BBSManagerService bbsMngService;

	@Inject
    private FileManagerUtil fileManagerUtil;

	@Inject
	private FileManagerService  fileMngService;

	//@Inject
	//private UsergMenuMapService usergMenuMapService;


	@Inject
	private CodeListService codeListService;

	private final Map<String, Object> codeMap = new HashMap<String, Object>();

	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 맵 모델 생성 for View(JSP)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 16.
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

//		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");
//		String ofBizType 		= UtilJson.convertJsonString(codeListService.getComCodeList(WiseConfig.META, "BIZ_TYPE"));
//		String requestStatus 	= UtilJson.convertJsonString(codeListService.getComCodeList(WiseConfig.META, "STATUS_CODE"));
//		String sysareaibs 	= UtilJson.convertJsonString(codelistService.getCodeListIBS("sysarea"));
//		codeMap.put("ofBizType", ofBizType);
//		codeMap.put("ofBizType", codeListService.getComCodeList(WiseConfig.META, "RQST_BIZ_TYPE"));
//		codeMap.put("requestStatus", codeListService.getComCodeList(WiseConfig.META, "STATUS_CODE"));
//		codeMap.put("sysareaibs", sysareaibs);
		codeMap.put("prjCode", codeListService.getCodeList(WiseConfig.PRJLIST));
		codeMap.put("reportType", codeListService.getComCodeList(WiseConfig.PORTAL, "BBS011"));

		return codeMap;
	}

	private void setusermenumap(String bbsid, ModelMap model) {
		String servletPath = "/commons/bbs/selectBoardList.do";
		if (StringUtils.hasText(bbsid)) {
			servletPath += "?bbsId=" + bbsid;
		}
//		Map<String, Object> menumap = usergMenuMapService.getMenuMap(servletPath);
		//Map<String, Object> menumap = usergMenuMapService.getMenuMap2(servletPath);
		Map<String, Object> menumap = null;
		if (menumap.containsKey("REQ_MENU")) {
			model.addAttribute("REQ_MENU", menumap.get("REQ_MENU"));
		}
		if (menumap.containsKey("TOP_MENU")) {
			model.addAttribute("TOP_MENU", menumap.get("TOP_MENU"));
		}
		if (menumap.containsKey("SUB_MENU")) {
//			UtilJson.convertJsonString(menumap.get("SUB_MENU"));
			model.addAttribute("SUB_MENU", UtilJson.convertJsonString(menumap.get("SUB_MENU")));
		}
	}

    /**
     * 게시물에 대한 목록을 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectBoardList.do")
    public String selectBoardArticles(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();

	log.debug("USER : {}", user);

	//log.debug(this.getClass().getName() + " user.getId() "+ user.getId());
	//log.debug(this.getClass().getName() + " user.getName() "+ user.getName());
	//log.debug(this.getClass().getName() + " user.getUniqId() "+ user.getUniqId());
	//log.debug(this.getClass().getName() + " user.getOrgnztId() "+ user.getOrgnztId());
	//log.debug(this.getClass().getName() + " user.getUserSe() "+ user.getUserSe());
	//log.debug(this.getClass().getName() + " user.getEmail() "+ user.getEmail());

	//String attrbFlag = "";

	boardVO.setBbsId(boardVO.getBbsId());
	boardVO.setBbsNm(boardVO.getBbsNm());

	boardVO.setSearchWrd(new HTMLInputFilter().filter(boardVO.getSearchWrd()));

	BoardMasterVO vo = new BoardMasterVO();

	vo.setBbsId(boardVO.getBbsId());
	vo.setUniqId(user.getUniqId());
//	vo.setUniqId("SYSTEM");

	BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(vo);

	codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, master.getCateCode()));
//	codeMap.put("prjCode", codeListService.getCodeList(WiseConfig.PRJLIST));

	//-------------------------------
	// 방명록이면 방명록 URL로 forward
	//-------------------------------
//	if (master.getBbsTyCode().equals("BBST04")) {
//	    return "forward:/cop/bbs/selectGuestList.do";
//	}
	////-----------------------------


//	boardVO.setPageUnit(propertyService.getInt("pageUnit"));
//	boardVO.setPageSize(propertyService.getInt("pageSize"));

	boardVO.setPageUnit(WiseConfig.PAGEUNIT);
	boardVO.setPageSize(WiseConfig.PAGESIZE);

	PaginationInfo paginationInfo = new PaginationInfo();

	paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
	paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
	paginationInfo.setPageSize(boardVO.getPageSize());

	boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
	boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
	Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, master.getBbsAttrbCode());//2011.09.07
	int totCnt = Integer.parseInt((String)map.get("resultCnt"));

	paginationInfo.setTotalRecordCount(totCnt);
	DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
	String  pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_PAGE);

	//-------------------------------
	// 기본 BBS template 지정
	//-------------------------------
//	if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
//	    master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
//	}
	////-----------------------------

	model.addAttribute("resultList", map.get("resultList"));
	model.addAttribute("resultCnt", map.get("resultCnt"));
	model.addAttribute("boardVO", boardVO);
	model.addAttribute("brdMstrVO", master);
//	model.addAttribute("paginationInfo", paginationInfo);
	model.addAttribute("pageui", pageUi);

	setusermenumap(boardVO.getBbsId(), model);

	return "/commons/bbs/bbsNoticeList";
    }


    /**
     * 게시물에 대한 상세 정보를 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectBoardArticle.do")
    public String selectBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();

    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		// 조회수 증가 여부 지정
		boardVO.setPlusCount(true);

		//---------------------------------
		// 2009.06.29 : 2단계 기능 추가
		//---------------------------------
		if (!boardVO.getSubPageIndex().equals("")) {
		    boardVO.setPlusCount(false);
		}
		////-------------------------------

		boardVO.setLastUpdusrId(user.getUniqId());
		BoardVO vo = bbsMngService.selectBoardArticle(boardVO);

		model.addAttribute("result", vo);
		//CommandMap의 형태로 개선????

		model.addAttribute("sessionUniqId", user.getUniqId());

		//----------------------------
		// template 처리 (기본 BBS template 지정  포함)
		//----------------------------
		BoardMasterVO master = new BoardMasterVO();

		master.setBbsId(boardVO.getBbsId());
		master.setUniqId(user.getUniqId());

		BoardMasterVO masterVo = bbsAttrbService.selectBBSMasterInf(master);

//		if (masterVo.getTmplatCours() == null || masterVo.getTmplatCours().equals("")) {
//		    masterVo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
//		}

		model.addAttribute("brdMstrVO", masterVo);




		////-----------------------------
		//파일 업로드 있는 경우 처리...
//		String fileId = vo.getAtchFileId();
//		List<FileVO> listfile ;
//		if(fileId != null && !"".equals(fileId)) {
//			FileVO fvo = new FileVO();
//			fvo.setAtchFileId(fileId);
//			listfile = fileMngService.selectFileInfs(fvo);
//			model.addAttribute("filelist", listfile);
//		}

		//----------------------------
		// 2009.06.29 : 2단계 기능 추가
		// 2011.07.01 : 댓글, 스크랩, 만족도 조사 기능의 종속성 제거
		//----------------------------
/*		if (bbsCommentService != null){
			if (bbsCommentService.canUseComment(boardVO.getBbsId())) {
			    model.addAttribute("useComment", "true");
			}
		}
		if (bbsSatisfactionService != null) {
			if (bbsSatisfactionService.canUseSatisfaction(boardVO.getBbsId())) {
			    model.addAttribute("useSatisfaction", "true");
			}
		}
		if (bbsScrapService != null ) {
			if (bbsScrapService.canUseScrap()) {
			    model.addAttribute("useScrap", "true");
			}
		}*/
		////--------------------------

		setusermenumap(boardVO.getBbsId(), model);

		return "/commons/bbs/bbsNoticeDetail";
    }



    /**
     * 게시물 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/addBoardArticle.do")
    public String addBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();

    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		BoardMasterVO bdMstr = new BoardMasterVO();

		if (isAuthenticated) {

		    BoardMasterVO vo = new BoardMasterVO();
		    vo.setBbsId(boardVO.getBbsId());
		    vo.setUniqId(user.getUniqId());

		    bdMstr = bbsAttrbService.selectBBSMasterInf(vo);
		    model.addAttribute("bdMstr", bdMstr);
		    codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, bdMstr.getCateCode()));
		}

		//----------------------------
		// 기본 BBS template 지정
		//----------------------------
//		if (bdMstr.getTmplatCours() == null || bdMstr.getTmplatCours().equals("")) {
//		    bdMstr.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
//		}

		model.addAttribute("brdMstrVO", bdMstr);
		////-----------------------------

		setusermenumap(boardVO.getBbsId(), model);

		return "/commons/bbs/bbsNoticeRegist";
    }



    /**
     * 게시물을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertBoardArticle.do")
    public String insertBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board , //BindingResult bindingResult, SessionStatus status,
	    ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

/*	beanValidator.validate(board, bindingResult);
	if (bindingResult.hasErrors()) {

	    BoardMasterVO master = new BoardMasterVO();
	    BoardMasterVO vo = new BoardMasterVO();

	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId(user.getUniqId());

	    master = bbsAttrbService.selectBBSMasterInf(vo);

	    model.addAttribute("bdMstr", master);

	    //----------------------------
	    // 기본 BBS template 지정
	    //----------------------------
	    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		master.setTmplatCours("css/egovframework/com/cop/tpl/egovBaseTemplate.css");
	    }

	    model.addAttribute("brdMstrVO", master);
	    ////-----------------------------

	    return "egovframework/com/cop/bbs/EgovNoticeRegist";
	}*/

	if (isAuthenticated) {
	    List<FileVO> result = null;
	    String atchFileId = "";

	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if (!files.isEmpty()) {
			result = fileManagerUtil.parseFileInf(files, "BBS_", 0, "", "");
			atchFileId = fileMngService.insertFileInfs(result);
			log.debug("fileid[{}]", atchFileId);
	    }
	    board.setAtchFileId(UtilString.null2Blank(atchFileId));
	    board.setFrstRegisterId(user.getUniqId());
	    board.setBbsId(board.getBbsId());


	    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
	    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)


	    board.setNtcrId(user.getId()); //게시물 통계 집계를 위해 등록자 ID 저장
	    board.setNtcrNm(user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장

	    board.setNttSj(new HTMLInputFilter().filter(board.getNttSj()));	// XSS 방지
//	    board.setNttCn(new HTMLInputFilter().filter(board.getNttCn()));	// XSS 방지
	    board.setNttCn(XSS.unscript(board.getNttCn()));	// XSS 방지

	    bbsMngService.insertBoardArticle(board);
	}

	//status.setComplete();
	return "forward:/commons/bbs/selectBoardList.do";
    }



    /**
     * 게시물에 대한 내용을 삭제한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteBoardArticle.do")
    public String deleteBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		//인증 취약점 보완...
		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
		    board.setLastUpdusrId(user.getUniqId());
		    board.setFrstRegisterId(user.getUniqId());

		    bbsMngService.deleteBoardArticle(board);
		}

		return "forward:/commons/bbs/selectBoardList.do";
    }



    /**
     * 게시물 수정을 위한 수정페이지로 이동한다.
     *
     * @param boardVO
     * @param vo
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/forUpdateBoardArticle.do")
    public String selectBoardArticleForUpdt(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo, ModelMap model)
	    throws Exception {

    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		String errCode = "";

		boardVO.setFrstRegisterId(user.getUniqId());

		BoardMaster master = new BoardMaster();
		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();

		vo.setBbsId(boardVO.getBbsId());

		master.setBbsId(boardVO.getBbsId());
		master.setUniqId(user.getUniqId());

		//인증 취약점 보완...
		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
		    bmvo = bbsAttrbService.selectBBSMasterInf(master);
		    bdvo = bbsMngService.selectBoardArticle(boardVO);
		    codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, bmvo.getCateCode()));
		   /*if( !user.getUniqId().equals(bdvo.getFrstRegisterId())){
			   bdvo = new BoardVO();
			   errCode = "BBS.ERRAUTH";
		   }*/
		} else {
			errCode = "BBS.ERRAUTH";

		}

		model.addAttribute("result", bdvo);
		model.addAttribute("bdMstr", bmvo);
		model.addAttribute("errCode", errCode);

		//----------------------------
		// 기본 BBS template 지정
		//----------------------------
//		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
//		    bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
//		}

		model.addAttribute("brdMstrVO", bmvo);
		////-----------------------------

		setusermenumap(boardVO.getBbsId(), model);
		return "/commons/bbs/bbsNoticeUpdt";
    }

    /**
     * 게시물에 대한 내용을 수정한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateBoardArticle.do")
    public String updateBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, //BindingResult bindingResult, SessionStatus status,
	    ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		String atchFileId = boardVO.getAtchFileId();

/*		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

		    boardVO.setFrstRegisterId(user.getUniqId());

		    BoardMaster master = new BoardMaster();
		    BoardMasterVO bmvo = new BoardMasterVO();
		    BoardVO bdvo = new BoardVO();

		    master.setBbsId(boardVO.getBbsId());
		    master.setUniqId(user.getUniqId());

		    bmvo = bbsAttrbService.selectBBSMasterInf(master);
		    bdvo = bbsMngService.selectBoardArticle(boardVO);

		    model.addAttribute("result", bdvo);
		    model.addAttribute("bdMstr", bmvo);

		    return "egovframework/com/cop/bbs/EgovNoticeUpdt";
		}*/

		/*
		boardVO.setFrstRegisterId(user.getUniqId());
		BoardMaster _bdMstr = new BoardMaster();
		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();
		vo.setBbsId(boardVO.getBbsId());
		_bdMstr.setBbsId(boardVO.getBbsId());
		_bdMstr.setUniqId(user.getUniqId());

		if (isAuthenticated) {
		    bmvo = bbsAttrbService.selectBBSMasterInf(_bdMstr);
		    bdvo = bbsMngService.selectBoardArticle(boardVO);
		}
		//*/

		//인증 취약점 보완...
		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
		    final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    if (!files.isEmpty()) {
				if ("".equals(atchFileId)) {
				    List<FileVO> result = fileManagerUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
				    atchFileId = fileMngService.insertFileInfs(result);
				    board.setAtchFileId(atchFileId);
				} else {
				    FileVO fvo = new FileVO();
				    fvo.setAtchFileId(atchFileId);
				    int cnt = fileMngService.getMaxFileSN(fvo);
				    List<FileVO> _result = fileManagerUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
				    fileMngService.updateFileInfs(_result);
				}
		    }

		    board.setLastUpdusrId(user.getUniqId());
		    board.setFrstRegisterId(user.getUniqId());

		    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

		    board.setNttCn(XSS.unscript(board.getNttCn()));	// XSS 방지

		    bbsMngService.updateBoardArticle(board);
		}

		return "forward:/commons/bbs/selectBoardList.do";
    }




    /**
     * 게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/addReplyBoardArticle.do")
    public String addReplyBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
//		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId(user.getUniqId());

		master = bbsAttrbService.selectBBSMasterInf(vo);

		codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, master.getCateCode()));


		model.addAttribute("bdMstr", master);
		model.addAttribute("result", boardVO);

		//----------------------------
		// 기본 BBS template 지정
		//----------------------------
	//	if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
	//	    master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
	//	}

		model.addAttribute("brdMstrVO", master);
		////-----------------------------

		setusermenumap(boardVO.getBbsId(), model);

	return "/commons/bbs/bbsNoticeReply";
    }

    /**
     * 게시물에 대한 답변을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/replyBoardArticle.do")
    public String replyBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board,  ModelMap model
	    ) throws Exception {

    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

//	beanValidator.validate(board, bindingResult);
/*	if (bindingResult.hasErrors()) {
	    BoardMasterVO master = new BoardMasterVO();
	    BoardMasterVO vo = new BoardMasterVO();

	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId(user.getUniqId());

	    master = bbsAttrbService.selectBBSMasterInf(vo);

	    model.addAttribute("bdMstr", master);
	    model.addAttribute("result", boardVO);

	    //----------------------------
	    // 기본 BBS template 지정
	    //----------------------------
	    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
	    }

	    model.addAttribute("brdMstrVO", master);
	    ////-----------------------------

	    return "egovframework/com/cop/bbs/EgovNoticeReply";
	}*/

	if (isAuthenticated) {
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    String atchFileId = "";

	    if (!files.isEmpty()) {
		List<FileVO> result = fileManagerUtil.parseFileInf(files, "BBS_", 0, "", "");
		atchFileId = fileMngService.insertFileInfs(result);
	    }

	    board.setAtchFileId(atchFileId);
	    board.setReplyAt("Y");
	    board.setFrstRegisterId(user.getUniqId());
	    board.setBbsId(board.getBbsId());
	    board.setParnts(Long.toString(boardVO.getNttId()));
	    board.setSortOrdr(boardVO.getSortOrdr());
	    board.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));

	    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
	    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

	    board.setNttCn(XSS.unscript(board.getNttCn()));	// XSS 방지

	    bbsMngService.insertBoardArticle(board);
	}

	return "forward:/commons/bbs/selectBoardList.do";
    }

    /**
     * XSS 방지 처리.
     *
     * @param data
     * @return
     */
/*    protected String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }

        String ret = data;

        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }*/

}
