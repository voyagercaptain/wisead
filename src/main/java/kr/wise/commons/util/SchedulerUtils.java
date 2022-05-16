package kr.wise.commons.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.helper.CStreamGobbler;

import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("SchedulerUtils")
public class SchedulerUtils {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);
	
	private SchedulerUtils(){}

    @Inject
    private static MessageSource message;
	
//  @Value("#{configure['CSchedulerUtils']}")
//  private static String schdulerpath;
	 
    private boolean connectSchedulerServer(String schdulerpath) {
//    	String schdulerpath = message.getMessage(WiseConfig.UPLOAD_PATH, null, Locale.getDefault()); 
//		schdulerpath = "C:/IDE/workspace-nhic/wiseda_scheduler";
		
		try {
			Properties properties = new Properties();
			FileInputStream fi = new FileInputStream(schdulerpath+"/scheduler/classes/schedulerRMI.properties");
			properties.load(new BufferedInputStream(fi));
			new StdSchedulerFactory(properties).getScheduler();
			
			return true;
		} catch(SchedulerException e) {
			logger.error(e.toString());
			return false;
		} catch(Exception e) {
			logger.error(e.toString());
			return false;
		}
	}
    
	public static boolean testConnectSchedulerServer(String schdulerpath) {
		return new SchedulerUtils().connectSchedulerServer(schdulerpath);
	}

	//Quartz 등록
    public static  void registrySchedule(WamShd saveVO, String schdulerCmd) throws Exception {
    	
		if(null == saveVO.getShdId()) {
			throw new Exception("스케줄 등록 type 오류입니다.");
		}
		Process process = null;
		try {
			logger.debug(" {} \n" , saveVO);
			// {I,U,D 택일} {스케줄ID}
			String cmd = schdulerCmd + " " + saveVO.getRegTypCd() + " " + saveVO.getShdId();
			logger.debug(" registrySchedule cmd ::  "+cmd);
//			String cmd = schdulerpath+"/scheduler/bin/ScheduleRegistry.cmd" + " " + saveVO.getRegTypCd() + " " + saveVO.getShdId();
			process = Runtime.getRuntime().exec(cmd);

			// 서버가 윈도우인 경우 버퍼 해소
			CStreamGobbler out   = new CStreamGobbler(process.getInputStream(), "OUT");
			CStreamGobbler error = new CStreamGobbler(process.getErrorStream(), "ERROR");

			out.start();
			error.start();

			/*while (true) {
				if (!out.isAlive() && !error.isAlive()) {  //두개의 스레드가 정지하면 프로세스 종료때까지 기다린다.
					process.waitFor();
					break;
				}
			}*/
			//int exitValue = process.exitValue();
			int exitValue = process.waitFor();
			logger.debug("ExitValue : " + exitValue);
			
			if(exitValue != 0) {
				throw new Exception("Runtime error.("+exitValue+")");
			}
		} catch (InterruptedException e) {
			logger.error(e.toString());
			throw new Exception(e);
		} catch(IOException e) {
			logger.error(e.toString());
			throw new Exception(e);
		} catch(Exception e) {
			logger.error(e.toString());
			throw e;
		} finally {
			if(process != null) try { process.destroy(); } catch(Exception ignored) {}
		}
	}
}
