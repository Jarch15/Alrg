package com.elliemae.alrg.config;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.Utils.CommonUtility;
import com.elliemae.core.Utils.SQLDBHelper;
import com.elliemae.alrg.exceptions.NotSupportedException;
import com.elliemae.alrg.utils.ALRGLogger;

import Fillo.Recordset;

public class ALRGSQLDBHelper extends SQLDBHelper {

	public static Logger _log = Logger.getLogger(ALRGSQLDBHelper.class);
	//singleton
	public static ALRGSQLDBHelper dbHelper = null;
	
    public static ALRGSQLDBHelper getInstance()
    {
        if (dbHelper == null)
        	dbHelper = new ALRGSQLDBHelper();
 
        return dbHelper;
    }
    
    public static ALRGSQLDBHelper getInstance(String clientID)
    {
        if (dbHelper == null)
        	dbHelper = new ALRGSQLDBHelper(clientID);
 
        return dbHelper;
    }
    
    private ALRGSQLDBHelper(){
        _log= Logger.getLogger(ALRGSQLDBHelper.class);
        sqlConfigurationData = getSQLConfigurationInfoData(FrameworkConsts.ENVIRONMENTCLIENTID);
    }
    
    private ALRGSQLDBHelper(String clientID){
        _log= Logger.getLogger(ALRGSQLDBHelper.class);
        sqlConfigurationData = getSQLConfigurationInfoData(clientID);
    }
    
    /**
     * <b>Name:</b>getSQLConfigurationInfoData<br>
     * <b>Description:</b> Returns the data in SQLConfigurationInfo sheet in EnvironmentConfig excel depending upon the environmentClientID provided as input<br>
     * 
     * @param environmentClientID
     * @return hashMap
     */
    public HashMap<String, String> getSQLConfigurationInfoData(String environmentClientID)
    {   
        environmentClientID= environmentClientID.trim();
        HashMap<String, String> sqlConfigInfoDataMap  = new HashMap<String, String>();
        try
        {           
            sqlConfigInfoDataMap = getSQLConfigurationInfo(environmentClientID,"",FrameworkConsts.ENVIRONMENTNAME.trim());
            ALRGLogger.log(_log, "Finished loading SQL Configuration Information from SQLConfigurationInfo.xlsx to Hash Map.",EllieMaeLogLevel.reporter);
        }
        catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading SQL Configuration Information from SQLConfigurationInfo.xlsx: " +e.getMessage(),EllieMaeLogLevel.reporter, EllieMaeLogLevel.error);
        } 
        return sqlConfigInfoDataMap;
    }
    
    /**
     * <b>Name:</b>getConfigurationInfo<br>
     * <b>Description:</b> Returns the data in MongoConfigurationInfo/SQLConfigurationInfo/SOAPServiceName/UserList/EnvironmentInfo sheet in EnvironmentConfig excel depending upon the environmentClientID/environmentName/UserKey provided as input<br>
     * 
     * @param environmentClientID,userKey,environmentName,requestType
     * @return hashMap
     */
    private HashMap<String, String> getSQLConfigurationInfo(String clientID, String userKey, String environmentName) 
    {
        HashMap<String, String> userListDataMap  = new HashMap<String, String>();
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/com/elliemae/config/EnvironmentConfig.xlsx" );
        try {
            String environmentConfigFileRelativePath= null;
            String query="";
            if(file.exists())
            {
                environmentConfigFileRelativePath= CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                ALRGLogger.log(_log, "Loading SQLConfigurationInfo data from EnvironmentConfig.xlsx",EllieMaeLogLevel.reporter);
            }
            else{
                environmentConfigFileRelativePath= CommonUtility.getRelativeFilePath("env", "SQLConfigurationInfo.xlsx");
                ALRGLogger.log(_log, "Loading SQLConfigurationInfo data from SQLConfigurationInfo.xlsx",EllieMaeLogLevel.reporter);
            }
            query = "Select * from SQLConfigurationInfo where ClientID='"+clientID+"' and EnvironmentName='"+environmentName+"'";        

            Recordset recordSetUserListData=CommonUtility.getRecordSetUsingFillo(environmentConfigFileRelativePath,query);      
            if(recordSetUserListData == null) {                
                ALRGLogger.log(_log, 
                        "No Information found. Please check the user input : [ClientID:  "+clientID + ", UserKey: "+ userKey + ", EnvironmentName: " + environmentName+" ]. Try by ClientID only",
                        EllieMaeLogLevel.reporter);
                //as database info is based on clientID, regardless of environmentName, we're going to select by clientID and use first row found
                query = "Select * from SQLConfigurationInfo where ClientID='"+clientID+"'";  
                recordSetUserListData=CommonUtility.getRecordSetUsingFillo(environmentConfigFileRelativePath,query);      
            }
            
            Object[] list= new Object[ recordSetUserListData.getFieldNames().size()];
            list=recordSetUserListData.getFieldNames().toArray();
            while(recordSetUserListData.next()) {
                for(Object obj : list)
                {
                    String key=obj.toString();  
                    userListDataMap.put(key, recordSetUserListData.getField(key));              
                }  
                //the first row loads
                break;
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading getConfigurationInfo data from Environment Config files" +e.getMessage(),EllieMaeLogLevel.reporter);
        }         
        return userListDataMap;
    }   
    
	public Connection getDBConnection() throws SQLException {
		try {
			return super.getDBConnection("ADMIN_SQLDBServerName", "ADMIN_SQLUserName", "ADMIN_SQLPassword",	"ADMIN_SQLDBName");
		} catch (Exception e) {
			ALRGLogger.log(_log, "Exception in getDBConnection() method:"+ e.getMessage());
			throw new SQLException(e.getMessage());
		}
	}
	
	@Override
	public int executeSQLUpdate(Connection conn,String query) {
		throw new NotSupportedException("SQLDBHealper.executeSQLUpdate() not supported");
	}
	
	@Override
	public List<HashMap<String,String>> executeSQLSelect(Connection conn,String query) {
		throw new NotSupportedException("SQLDBHealper.executeSQLSelect() not supported");
	}	
	
	/**
	 * <b>Name:</b> executeSQLSelect
	 * <b>Description:</b>This method is used to execute a SQL query and return the data as a List of HashMap
	 * 
	 * @param query
	 * @return List of HashMap
	 * 
	 */
	public List<HashMap<String,String>> executeSelect(Connection conn,String query) throws SQLException
	{
		List<HashMap<String,String>> mapList= null;
		ResultSet resultSet=null;
		
		try
		{
			Statement  stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ALRGLogger.log(_log, "SQL Query to execute: \n" +query, EllieMaeLogLevel.reporter);
			
			resultSet = stmt.executeQuery(query);

			ALRGLogger.log(_log, "Formatting SQL ResultSet to a HashMap List...");
			mapList = getFormattedResult(resultSet);
			ALRGLogger.log(_log, "Formatted SQL ResultSet to a HashMap List...");
			return mapList;
		} 
		catch (Exception e) {
			ALRGLogger.log(_log, "Exception in executeSQLSelect() method: "+ e.getMessage());
			throw new SQLException(e.getMessage());
		} finally {
			super.closeConnection(conn);
		}
	}
	
	/**
	 * <b>Name:</b> executeSQLUpdate
	 * <b>Description:</b>This method is used to execute insert, update or delete SQL query and returns either 
	 * (1) the row count for SQL Data Manipulation Language (DML) statements or 
	 * (2) 0 for SQL statements that return nothing
	 * (3) -1 for exception
	 * 
	 * @param query
	 * @return int
	 * 
	 */
	public int executeUpdate(Connection conn,String query) throws SQLException {
		int rowCount=-1;
		try
		{
			ALRGLogger.log(_log, "SQL Query to execute: \n" +query, EllieMaeLogLevel.reporter);
			Statement stmt = conn.createStatement();
			rowCount =  stmt.executeUpdate(query);

		} catch (Exception e) {
			ALRGLogger.log(_log, "Exception in executeSQLUpdate() method:"+ e.getMessage());
			throw new SQLException(e.getMessage());
		}
		super.closeConnection(conn);
		return rowCount;
	}	
}
