package kr.wise.commons.user.service;

import java.util.ArrayList;
import java.util.List;

public interface LicService {

	public List<WaaLic> getList(WaaLic search) ;

	public int regLic(WaaLic record) ;

	public int regList(ArrayList<WaaLic> list) ;
	
	public int delList(ArrayList<WaaLic> list) ;
}
