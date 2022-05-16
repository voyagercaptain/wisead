package kr.wise.commons.test;


public class SqlJdbcTest {
/*	
	public static void main(String[] args) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		CallableStatement cstmt = null;
		try {
			// Establish the connection. 
	         SQLServerDataSource ds = new SQLServerDataSource();
	         ds.setUser("potal");
	         ds.setPassword("potal!23");
	         ds.setServerName("10.1.60.58");
//	         ds.setPortNumber(1433); 
//	         ds.setDatabaseName("AdventureWorks");
	         con = ds.getConnection();

//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			con = DriverManager.getConnection("jdbc:sqlserver://10.1.60.58:1433", "potal", "potal!23");
			
			String sql = "SELECT US_ID, US_NICK FROM EDCM2_NEXT_COMMON.dbo.VIEWUSERINFO "; //WHERE US_NICK = '사번'	";
			
			pstm = con.prepareStatement(sql);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("US_ID") +":"+ rs.getString("US_NICK"));
			}
			
			
			//업무별 프로그램 본 수 집계
	 		cstmt = con.prepareCall("{call EDCM2_NEXT_COMMON.dbo.procAssistProgramCount}");
	 		rs.close(); 
	 		rs = cstmt.executeQuery();
	 		
			while(rs.next()){
				System.out.println( rs.getString("PR_NAME"));
			}
			
			//업무별 프로그램 변경 집계
	 		cstmt = con.prepareCall("{call EDCM2_NEXT_COMMON.dbo.procAssistSourceUpdateCountByDate(?, ?)}");
	 		cstmt.setString(1, "2013-01-01");
	 		cstmt.setString(2, "2013-10-01");
	 		rs = cstmt.executeQuery();
	 		
			while(rs.next()){
				System.out.println( rs.getString("PR_NAME"));
			}
			
			//업무별 요청서 집계
//	 		cstmt = con.prepareCall("{call EDCM2_NEXT_COMMON.dbo.procAssistDocumentCountByDate(?, ?)}");
//	 		cstmt.setString(1, "2013-01-01");
//	 		cstmt.setString(2, "2013-10-01");
			

			//사용자가 작성한 최근 작업 내역 리스트 추출
			cstmt = con.prepareCall("{call EDCM2_NEXT_COMMON.dbo.procRecentWork(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setNull(1, java.sql.Types.VARCHAR);
			cstmt.setNull(2, java.sql.Types.VARCHAR);
			cstmt.setNull(3, java.sql.Types.VARCHAR);
			cstmt.setNull(4, java.sql.Types.VARCHAR);
//			cstmt.setNull(5, java.sql.Types.VARCHAR);
			cstmt.setString(5, "2007121411305166");
			cstmt.setNull(6, java.sql.Types.VARCHAR);
			cstmt.setNull(7, java.sql.Types.VARCHAR);
			cstmt.setNull(8, java.sql.Types.VARCHAR);
			cstmt.setInt(9, 1);
			cstmt.setInt(10, 10);
//			cstmt.setNull(9, java.sql.Types.INTEGER);
//			cstmt.setNull(10, java.sql.Types.INTEGER);
//	 		cstmt.setNull(11, java.sql.Types.INTEGER);
			
			rs = null;
			rs = cstmt.executeQuery();
			
			System.out.println("=== start resultset ===");
			while(rs.next()){
				System.out.println( rs.getString("REQ_ID") + ":" + rs.getString("US_ID") + ":" + rs.getString("REQ_SUBJ"));
			}
			
			
		} catch(Exception e) {
			//logger.error("접속테스트 실패 : " + e);
			e.printStackTrace();
			
		} finally {
			if(rs != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstm != null) try { pstm.close(); } catch(Exception igonred) {}
			if(cstmt != null) try { cstmt.close(); } catch(Exception igonred) {}
			if(con != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	
*/
}
