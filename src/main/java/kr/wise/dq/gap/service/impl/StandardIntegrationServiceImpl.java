package kr.wise.dq.gap.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import kr.wise.dq.gap.service.StandardIntegrationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("StandardIntegrationService")
public class StandardIntegrationServiceImpl implements StandardIntegrationService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int runStandardIntegration(String path, String file) throws Exception {
		Process process = null;
		//"cmd.exe" 커맨드라인 실행
		//"/c" 관리자 권한으로 명령 실행할 수 있게 하는 명령어
		//"cd 경로" 해당 경로로 이동
		//&& 경로 이동 후 뒤의 명령어 같이 실행
		//file 실행할 파일 실행
		String[] cmd = new String[] {"cmd.exe", "/c", "cd \"" + path + "\" && " + file};
		
		logger.debug("cmd >>> " + cmd);
		String str = null;

		try {

		    // 프로세스 빌더를 통하여 외부 프로그램 실행
		    process = new ProcessBuilder(cmd).start();

		    // 외부 프로그램의 표준출력 상태 버퍼에 저장
		    BufferedReader stdOut = new BufferedReader( new InputStreamReader(process.getInputStream()) );
		   
		    // 표준출력 상태를 출력
		    while( (str = stdOut.readLine()) != null ) {
		        System.out.println(str);
		    }
		    
		    process.waitFor();
		   
		} catch (IOException e) {
		    e.printStackTrace();

		} finally {
			process.destroy();
		}
		
		return 0;
	}

}
