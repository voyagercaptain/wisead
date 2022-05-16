package kr.wise.commons.cmm.security;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;

import kr.wise.commons.user.service.impl.KISA_SHA256;
import sun.util.logging.resources.logging;

public class License {
	public static String getMacAddr(){
		InetAddress addr = null;
		String macAddr = "";
		
		try {
			addr = InetAddress.getLocalHost();
			/* IP 주소 가져오기 */
			String ipAddr = addr.getHostAddress();

			/* 호스트명 가져오기 */
			String hostname = addr.getHostName();

			/* NetworkInterface를 이용하여 현재 로컬 서버에 대한 하드웨어 어드레스를 가져오기 */
			NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
			byte[] mac = ni.getHardwareAddress();
			
			for (int i = 0; i < mac.length; i++) {
				macAddr += String.format("%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return macAddr;
	}
	
	public static String getLicense() {
		String license = "";
		String macAddr = "";
		macAddr = getMacAddr();
		String saltKey = Calendar.getInstance().get(Calendar.YEAR) + "1231";
		
		license = KISA_SHA256.SHA256_Encrpyt(macAddr + saltKey);
		
				
		return license;
	}
	
	public static String getLicense(String macAddr) {
		String license = "";
		String saltKey = Calendar.getInstance().get(Calendar.YEAR) + "1231";
		
		license = KISA_SHA256.SHA256_Encrpyt(macAddr + saltKey);
				
		return license;
	}
}
