package com.elliemae.alrg.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.elliemae.consts.ConsumerConnectConsts;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.Utils.CommonUtility;
import com.elliemae.core.Utils.EnvironmentData;
import com.elliemae.core.Utils.WSCommonUtilityApplication;
import com.elliemae.alrg.utils.ALRGLogger;

import Fillo.Recordset;


/**
 * <b>Name:</b> EnvironmentDataApplication
 * <b>Description: </b>This class is extending EnvironmentData class in EllieMaeATF and is used to create methods related Environment data.
 * 
 * 
 */

public class ALRGEnvironmentDataApplication extends EnvironmentData {

    public static Logger _log = Logger.getLogger(ALRGEnvironmentDataApplication.class);
    private static HashMap<String, HashMap<String, String>> userListDataMap = new HashMap<String, HashMap<String, String>>();
    private static HashMap<String, HashMap<String, String>> siteListDataMap = new HashMap<String, HashMap<String, String>>();

    public static HashMap<String, HashMap<String, String>> getUserListData() {
        if (userListDataMap.size() == 0) {
            try {
                String environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                String strUserListQuery = "Select * from UserList";

                ALRGLogger.log(_log, "Loading UserList data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

                Recordset recordSetUserListData = CommonUtility.getRecordSetUsingFillo(environmentConfigFileRelativePath, strUserListQuery);
                HashMap<String, String> userData = new HashMap<>();

                Object[] list = new Object[recordSetUserListData.getFieldNames().size()];

                list = recordSetUserListData.getFieldNames().toArray();

                while (recordSetUserListData.next()) {

                    for (Object obj : list) {
                        String key = obj.toString();
                        userData.put(key, recordSetUserListData.getField(key));
                    }
                    userListDataMap.put(userData.get("UserKey"), new HashMap<>(userData));
                    userData.clear();
                }

                ALRGLogger.log(_log, "Finished loading UserList data from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

            } catch (Exception e) {
                ALRGLogger.log(_log, "Exception in Loading UserList data from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
            }
        }
        return userListDataMap;
    }

    // To get the CCSiteID
    public static HashMap<String, HashMap<String, String>> getCCSiteID(String environmentName) {
        if (siteListDataMap.size() == 0) {
            try {
                String environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                String strUserListQuery = "Select * from SiteIDList where EnvironmentName='" + environmentName + "' and SiteKey = '"
                        + ConsumerConnectConsts.SITEKEYCC.trim() + "'";

                ALRGLogger.log(_log, "Loading SiteList data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

                Recordset recordSetSiteListData = CommonUtility.getRecordSetUsingFillo(environmentConfigFileRelativePath, strUserListQuery);
                HashMap<String, String> siteData = new HashMap<>();

                Object[] list = new Object[recordSetSiteListData.getFieldNames().size()];

                list = recordSetSiteListData.getFieldNames().toArray();

                while (recordSetSiteListData.next()) {

                    for (Object obj : list) {
                        String key = obj.toString();
                        siteData.put(key, recordSetSiteListData.getField(key));
                    }
                    siteListDataMap.put(siteData.get("CCSiteId"), new HashMap<>(siteData));
                    siteData.clear();
                }
                ALRGLogger.log(_log, "Finished loading CCSiteID data from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

            } catch (Exception e) {
                ALRGLogger.log(_log, "Exception in Loading CCSiteID data from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
            }
        }
        return siteListDataMap;
    }


    public static HashMap<String, HashMap<String, String>> getAllDocumentEBSRESTServiceData() { 
    	
    	HashMap<String, HashMap<String, String>> serviceInfoDataMap = new HashMap<String, HashMap<String, String>>();
    	
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from DocumentRESTServiceData";
            ALRGLogger.log(_log, "Loading Document Service Data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();            
            while (recordSet.next()) {
            	HashMap<String, String> rowDataMap = new HashMap<String, String>();
                for (Object obj : list) {
                    String key = obj.toString();
                    rowDataMap.put(key, recordSet.getField(key));   
                }
                serviceInfoDataMap.put(recordSet.getField("MethodKey"), rowDataMap);
            }
            ALRGLogger.log(_log, "Finished loading Service Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading Service Information from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;    	
    }
    
    public static HashMap<String, String> getDocumentEBSRESTServiceData(String methodKey) {    	

        HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from DocumentRESTServiceData where MethodKey='" + methodKey + "'";
            ALRGLogger.log(_log, "Loading Document Service Data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();
            while (recordSet.next()) {
                for (Object obj : list) {
                    String key = obj.toString();
                    serviceInfoDataMap.put(key, recordSet.getField(key));
                }
            }
            ALRGLogger.log(_log, "Finished loading Service Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading Service Information from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;
    }    

    /**
     * <b>Name:</b>getEBSRESTServiceInfoData<br>
     * <b>Description:</b> Returns the data in EBSRESTServiceInfo sheet in EnvironmentConfig excel depending upon the apiMethodName provided as input
     * <br>
     * 
     * @param apiMethodName
     * @return hashMap
     */
    public static HashMap<String, String> getEBSRESTServiceInfoData(String apiMethodName) {
    	
        apiMethodName = apiMethodName.trim();
        FrameworkConsts.APIMETHODNAME.set(apiMethodName);

        HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from EBSRESTServiceInfo where APIMethodName='" + apiMethodName + "'";
            ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();
            while (recordSet.next()) {
                for (Object obj : list) {
                    String key = obj.toString();
                    serviceInfoDataMap.put(key, recordSet.getField(key));
                    if (key.contains("SLA") && key.contains(FrameworkConsts.ENVIRONMENTNAME)) {
                        String slaTiming = recordSet.getField(key);
                        slaTiming = slaTiming.replaceAll("s", "");
                        slaTiming = (slaTiming.isEmpty() ? "-1" : slaTiming);
                        FrameworkConsts.APIEXPECTEDSLATIMING.set(Float.parseFloat(slaTiming));
                    }
                }
            }
            ALRGLogger.log(_log, "Finished loading Service Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading Service Information from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;
    }

    public static HashMap<String, HashMap<String, String>> getFileGuidData(String environmentName) {

        HashMap<String, HashMap<String, String>> fileGuidDataMap = new HashMap<String, HashMap<String, String>>();
        try {
            String environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strfileGuidListQuery = "Select * from MediaServerInfo where EnvironmentName='" + environmentName + "'";

            ALRGLogger.log(_log, "Loading File Guid data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSetFileGuidListData = CommonUtility.getRecordSetUsingFillo(environmentConfigFileRelativePath, strfileGuidListQuery);
            HashMap<String, String> fileGuidData = new HashMap<>();

            Object[] list = new Object[recordSetFileGuidListData.getFieldNames().size()];

            list = recordSetFileGuidListData.getFieldNames().toArray();

            while (recordSetFileGuidListData.next()) {

                for (Object obj : list) {
                    String key = obj.toString();
                    fileGuidData.put(key, recordSetFileGuidListData.getField(key));
                }
                fileGuidDataMap.put(fileGuidData.get("FileGuidKey"), new HashMap<>(fileGuidData));
                fileGuidData.clear();
            }
            ALRGLogger.log(_log, "Finished loading SiteList data from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading SiteList data from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
        }

        return fileGuidDataMap;
    }

    /**
     * <b>Name:</b>getWCMRESTServiceInfoData<br>
     * <b>Description:</b> Returns the data in WCMRESTServiceInfoData sheet in EnvironmentConfig excel depending upon the apiMethodName provided as
     * input<br>
     * 
     * @param apiMethodName
     * @return hashMap
     */
    public static HashMap<String, String> getWCMRESTServiceInfoData(String apiMethodName) {
        apiMethodName = apiMethodName.trim();
        FrameworkConsts.APIMETHODNAME.set(apiMethodName);

        HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from WCMRESTServiceInfo where APIMethodName='" + apiMethodName + "'";
            ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();
            while (recordSet.next()) {
                for (Object obj : list) {
                    String key = obj.toString();
                    serviceInfoDataMap.put(key, recordSet.getField(key));
                    if (key.contains("SLA") && key.contains(FrameworkConsts.ENVIRONMENTNAME)) {
                        String slaTiming = recordSet.getField(key);
                        slaTiming = slaTiming.replaceAll("s", "");
                        slaTiming = (slaTiming.isEmpty() ? "-1" : slaTiming);
                        FrameworkConsts.APIEXPECTEDSLATIMING.set(Float.parseFloat(slaTiming));
                    }
                }
            }
            ALRGLogger.log(_log, "Finished loading Service Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading Service Information from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;
    }


    public static HashMap<String, HashMap<String, String>> getPartnerInfoData() {
        HashMap<String, HashMap<String, String>> userListDataMap = new HashMap<String, HashMap<String, String>>();
        try {
            String environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strUserListQuery = "Select * from EPCPartnerInfo";

            ALRGLogger.log(_log, "Loading Partner Info data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSetUserListData = CommonUtility.getRecordSetUsingFillo(environmentConfigFileRelativePath, strUserListQuery);
            HashMap<String, String> userData = new HashMap<>();

            Object[] list = new Object[recordSetUserListData.getFieldNames().size()];

            list = recordSetUserListData.getFieldNames().toArray();

            while (recordSetUserListData.next()) {

                for (Object obj : list) {
                    String key = obj.toString();
                    userData.put(key, recordSetUserListData.getField(key));
                }
                userListDataMap.put(userData.get("PartnerName"), new HashMap<>(userData));
                userData.clear();
            }

            ALRGLogger.log(_log, "Finished loading  Partner Info data from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading  Partner Info data from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
        }

        return userListDataMap;
    }

    /**
     * <b>Name:</b>getEVPRESTServiceInfoData<br>
     * <b>Description:</b> Returns the data in EVPRESTServiceInfoData sheet in EnvironmentConfig excel depending upon the apiMethodName provided as
     * input<br>
     * 
     * @param apiMethodName
     * @return hashMap
     */
    public static HashMap<String, String> getEVPRESTServiceInfoData(String apiMethodName) {
        apiMethodName = apiMethodName.trim();
        FrameworkConsts.APIMETHODNAME.set(apiMethodName);

        HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from EVPRESTServiceInfo where APIMethodName='" + apiMethodName + "'";
            ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();
            while (recordSet.next()) {
                for (Object obj : list) {
                    String key = obj.toString();
                    serviceInfoDataMap.put(key, recordSet.getField(key));
                    if (key.contains("SLA") && key.contains(FrameworkConsts.ENVIRONMENTNAME)) {
                        String slaTiming = recordSet.getField(key);
                        slaTiming = slaTiming.replaceAll("s", "");
                        slaTiming = (slaTiming.isEmpty() ? "-1" : slaTiming);
                        FrameworkConsts.APIEXPECTEDSLATIMING.set(Float.parseFloat(slaTiming));
                    }
                }
            }
            ALRGLogger.log(_log, "Finished loading Service Information from EVPRESTServiceInfo worksheet in EnvironmentConfig.xlsx to Hash Map.",
                    EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading Service Information from getEVPESTServiceInfoData" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;
    }


    /**
     * <b>Name:</b>getEDMRESTServiceInfoData<br>
     * <b>Description:</b>
     * For EDM:
     * Returns the data in the EDMRESTServiceInfo worksheet in EnvironmentConfig excel depending upon the apiMethodName provided as input<br>
     * 
     * @param apiMethodName
     * @return hashMap
     */
    public static HashMap<String, String> getEDMRESTServiceInfoData(String apiMethodName) {
        apiMethodName = apiMethodName.trim();
        FrameworkConsts.APIMETHODNAME.set(apiMethodName);

        HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from EDMRESTServiceInfo where APIMethodName='" + apiMethodName + "'";
            ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();
            while (recordSet.next()) {
                for (Object obj : list) {
                    String key = obj.toString();
                    serviceInfoDataMap.put(key, recordSet.getField(key));
                    if (key.contains("SLA") && key.contains(FrameworkConsts.ENVIRONMENTNAME)) {
                        String slaTiming = recordSet.getField(key);
                        slaTiming = slaTiming.replaceAll("s", "");
                        slaTiming = (slaTiming.isEmpty() ? "-1" : slaTiming);
                        FrameworkConsts.APIEXPECTEDSLATIMING.set(Float.parseFloat(slaTiming));
                    }
                }
            }
            ALRGLogger.log(_log, "Finished loading EDMRESTServiceInfo Information from EnvironmentConfig.xlsx to Hash Map.",
                    EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading EDMRESTServiceInfo Information from EnvironmentConfig.xlsx" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;
    }

    /**
     * <b>Name:</b>getEPCRESTServiceInfoData<br>
     * <b>Description:</b> Returns the data in EPCRESTServiceInfoData sheet in EnvironmentConfig excel depending upon the apiMethodName provided as
     * input<br>
     * 
     * @param apiMethodName
     * @return hashMap
     */
    public static HashMap<String, String> getEPCRESTServiceInfoData(String apiMethodName) {
        apiMethodName = apiMethodName.trim();
        FrameworkConsts.APIMETHODNAME.set(apiMethodName);

        HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from EPCRESTServiceInfo where APIMethodName='" + apiMethodName + "'";
            ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();
            while (recordSet.next()) {
                for (Object obj : list) {
                    String key = obj.toString();
                    serviceInfoDataMap.put(key, recordSet.getField(key));
                    if (key.contains("SLA") && key.contains(FrameworkConsts.ENVIRONMENTNAME)) {
                        String slaTiming = recordSet.getField(key);
                        slaTiming = slaTiming.replaceAll("s", "");
                        slaTiming = (slaTiming.isEmpty() ? "-1" : slaTiming);
                        FrameworkConsts.APIEXPECTEDSLATIMING.set(Float.parseFloat(slaTiming));
                    }
                }
            }
            ALRGLogger.log(_log, "Finished loading Service Information from EPCRESTServiceInfo worksheet in EnvironmentConfig.xlsx to Hash Map.",
                    EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading Service Information from getEPCESTServiceInfoData" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;
    }

    /**
     * <b>Name:</b>getGatewayAPIRESTServiceInfoData<br>
     * <b>Description:</b>
     * For Open API:
     * Returns the data in the OpenaAPIRESTServiceInfo worksheet in EnvironmentConfig excel depending upon the apiMethodName provided as input<br>
     * 
     * @param apiMethodName
     * @return hashMapget
     */
    public static HashMap<String, String> getGatewayAPIRESTServiceInfoData(String apiMethodName) {
        apiMethodName = apiMethodName.trim();
        FrameworkConsts.APIMETHODNAME.set(apiMethodName);

        HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from GATEWAYAPIRESTServiceInfo where APIMethodName='" + apiMethodName + "'";
            ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();
            while (recordSet.next()) {
                for (Object obj : list) {
                    String key = obj.toString();
                    serviceInfoDataMap.put(key, recordSet.getField(key));
                    if (key.contains("SLA") && key.contains(FrameworkConsts.ENVIRONMENTNAME)) {
                        String slaTiming = recordSet.getField(key);
                        slaTiming = slaTiming.replaceAll("s", "");
                        slaTiming = (slaTiming.isEmpty() ? "-1" : slaTiming);
                        FrameworkConsts.APIEXPECTEDSLATIMING.set(Float.parseFloat(slaTiming));
                    }
                }
            }
            ALRGLogger.log(_log, "Finished loading getGatewayAPIRESTServiceInfoData Information from EnvironmentConfig.xlsx to Hash Map.",
                    EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading getGatewayAPIRESTServiceInfoData Information from EnvironmentConfig.xlsx" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;
    }

    /**
     * <b>Name:</b>getEDMServerList<br>
     * <b>Description:</b>
     * For EDM:
     * Returns the data in the EDMServerList worksheet in EnvironmentConfig excel depending upon environmentName and batchNumber provided as input<br>
     * 
     * @param environmentName
     * @param batchNumber
     * @return hashMap
     */
    public static HashMap<String, String> getEDMServerList(String environmentName, String batchNumber) {
        HashMap<String, String> serverUrlList = new HashMap<String, String>();
        try {
            String serverListRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEDMServerListQuery = "Select * from EDMServerList where EnvironmentName ='" + environmentName.trim() + "' and BatchNumber = '"
                    + batchNumber.trim() + "'";
            ALRGLogger.log(_log, "Loading EDM Server List from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serverListRelativePath, strEDMServerListQuery);
            while (recordSet.next()) {
                serverUrlList.put(recordSet.getField("ServerId"), recordSet.getField("ServerURL"));
            }
            ALRGLogger.log(_log, "Finished loading EDM ServerURL Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading EDM ServerURL Information from EnvironmentConfig.xlsx" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return serverUrlList;
    }

    /**
     * <b>Name:</b>getEBSServerList<br>
     * <b>Description:</b>
     * For EBS:
     * Returns the data in the getEBSServerList worksheet in EnvironmentConfig excel depending upon environmentName provided as input<br>
     * 
     * @param environmentName
     * @return hashMap
     */
    public static HashMap<String, String> getEBSServerList(String environmentName, String batchNumber) {
        HashMap<String, String> serverUrlList = new HashMap<String, String>();
        try {
            String serverListRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEBSServerListQuery = "Select * from EBSServerList where EnvironmentName ='" + environmentName.trim() + "' and BatchNumber = '"
                    + batchNumber.trim() + "'";
            ALRGLogger.log(_log, "Loading EBS Server List from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serverListRelativePath, strEBSServerListQuery);
            while (recordSet.next()) {
                serverUrlList.put(recordSet.getField("ServerURL"), recordSet.getField("BatchNumber"));
            }
            ALRGLogger.log(_log, "Finished loading EBS ServerURL Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading EBS ServerURL Information from EnvironmentConfig.xlsx" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return serverUrlList;
    }

    /**
     * <b>Name:</b>getEVPServerList<br>
     * <b>Description:</b>
     * For EVP:
     * Returns the data in the getEVPServerList worksheet in EnvironmentConfig excel depending upon environmentName provided as input<br>
     * 
     * @param environmentName
     * @return hashMap
     */
    public static HashMap<String, String> getEVPServerList(String environmentName) {
        HashMap<String, String> serverUrlList = new HashMap<String, String>();
        try {
            String serverListRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEVPServerListQuery = "Select * from EVPServerList where EnvironmentName ='" + environmentName.trim() + "'";
            ALRGLogger.log(_log, "Loading EDM Server List from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serverListRelativePath, strEVPServerListQuery);
            while (recordSet.next()) {
                serverUrlList.put(recordSet.getField("ServerURL"), recordSet.getField("Type"));
            }
            ALRGLogger.log(_log, "Finished loading EVP ServerURL Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading EVP ServerURL Information from EnvironmentConfig.xlsx" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return serverUrlList;
    }

    /**
     * <b>Name:</b>getServiceInfoData<br>
     * <b>Description:</b>
     * For Open API:
     * Returns the data in the for a given worksheet in EnvironmentConfig excel depending upon the apiMethodName provided as input<br>
     * 
     * @param apiMethodName
     * @return hashMapget
     */
    public static HashMap<String, String> getServiceInfoData(String tabName, String apiMethodName) {
        apiMethodName = apiMethodName.trim();
        FrameworkConsts.APIMETHODNAME.set(apiMethodName);

        HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from " + tabName + " where APIMethodName='" + apiMethodName + "'";
            ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = null;
            recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = new Object[recordSet.getFieldNames().size()];

            list = recordSet.getFieldNames().toArray();
            while (recordSet.next()) {
                for (Object obj : list) {
                    String key = obj.toString();
                    serviceInfoDataMap.put(key, recordSet.getField(key));
                    if (key.contains("SLA") && key.contains(FrameworkConsts.ENVIRONMENTNAME)) {
                        String slaTiming = recordSet.getField(key);
                        slaTiming = slaTiming.replaceAll("s", "");
                        slaTiming = (slaTiming.isEmpty() ? "-1" : slaTiming);
                        FrameworkConsts.APIEXPECTEDSLATIMING.set(Float.parseFloat(slaTiming));
                    }
                }
            }
            ALRGLogger.log(_log, "Finished loading Service Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading Service Information from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
        }

        return serviceInfoDataMap;
    }

    /*
     * <b>Name:</b>getWorkSheetData<br>
     * <b>Description:</b>
     * 
     * Returns the data in any given worksheet in EnvironmentConfig excel with key as first columnName<br>
     * 
     * @param workSheetName
     * 
     * @return hashMapget
     */
    public static HashMap<String, HashMap<String, String>> getWorkSheetData(String workSheetName) {
        HashMap<String, HashMap<String, String>> infoData = new HashMap<String, HashMap<String, String>>();
        try {
            String worksheetQuery = "Select * from " + workSheetName;
            String environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");

            Recordset recordSetWorkSheetData = CommonUtility.getRecordSetUsingFillo(environmentConfigFileRelativePath, worksheetQuery);
            if (recordSetWorkSheetData == null) {
                ALRGLogger.log(_log, "No data found in worksheet [" + workSheetName + "] in EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);
                return null;
            }

            HashMap<String, String> rowData = new HashMap<>();

            Object[] list = new Object[recordSetWorkSheetData.getFieldNames().size()];

            list = recordSetWorkSheetData.getFieldNames().toArray();

            while (recordSetWorkSheetData.next()) {

                for (Object obj : list) {
                    String key = obj.toString();
                    rowData.put(key, recordSetWorkSheetData.getField(key));
                }
                infoData.put(rowData.get(rowData.keySet().toArray()[0]), new HashMap<>(rowData)); // Index 0 should be the primary key of that
                                                                                                  // worksheet
                rowData.clear();
            }
            ALRGLogger.log(_log, "Finished loading data from worksheet [" + workSheetName + "] EnvironmentConfig.xlsx to Hash Map.",
                    EllieMaeLogLevel.reporter);

        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading data from [" + workSheetName + "] EnvironmentConfig.xlsx" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return infoData;
    }


    /**
     * <b>Name:</b>getServiceInfoData<br>
     * <b>Description:</b>
     * For Open API:
     * Returns the data in the for a given worksheet in EnvironmentConfig excel depending upon the apiMethodName provided as input<br>
     * 
     * @param apiMethodName
     * @return hashMapget
     */
    public static HashMap<String, HashMap<String, String>> getServiceInfoSheet(String tabName) {
        HashMap<String, HashMap<String, String>> serviceInfoSheet = new HashMap<>();

        try {
            String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
            String strEnvInfoQuery = "Select * from " + tabName;
            ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);

            Recordset recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);
            Object[] list = recordSet.getFieldNames().toArray();

            while (recordSet.next()) {
                String sKey = null;
                HashMap<String, String> serviceInfoDataMap = new HashMap<String, String>();
                for (Object obj : list) {
                    String key = obj.toString();
                    if (key.equals("APIMethodName")) {
                        sKey = recordSet.getField(key);
                    } else {
                        serviceInfoDataMap.put(key, recordSet.getField(key));
                    }
                }
                serviceInfoSheet.put(sKey, serviceInfoDataMap);
            }
            ALRGLogger.log(_log, "Finished loading Service Information from EnvironmentConfig.xlsx to Hash Map.", EllieMaeLogLevel.reporter);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in Loading Service Information from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
        }
        return serviceInfoSheet;
    }

    /**
     * Get Cell data from environment config
     * 
     * @param tabName - Sheet name
     * @param cellRowName
     * @param cellRowValue
     * @param cellColumnName
     * @return value of cell
     */

    public static String getCellData(String tabName, String cellRowName, String cellRowValue, String cellColumnName) {

        String serviceInfoFileRelativePath = WSCommonUtilityApplication.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
        String strEnvInfoQuery = "Select * from " + tabName + " where " + cellRowName + " = '" + cellRowValue + "'";
        ALRGLogger.log(_log, "Loading Service Information from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);
        Recordset recordSet = WSCommonUtilityApplication.getRecordSetUsingFillo(serviceInfoFileRelativePath, strEnvInfoQuery);

        try {
            recordSet.next();
            return recordSet.getField(cellColumnName);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception while fetching information from EnvironmentConfig.xlsx" + e.getMessage(), EllieMaeLogLevel.reporter);
            return null;
        }
    }



    /**
     * <b>Name:</b>getEnvironmentData<br>
     * <b>Description:</b> Returns the data in Environment Info sheet in
     * EnvironmentConfig excel depending upon the environmentName key provided
     * as input<br>
     * 
     * @param environmentName
     * @return hashMap
     */
    public static HashMap<String, String> getEnvironmentData(String environmentName) {
    	
        environmentName = environmentName.trim();
        HashMap<String, String> configDataMap = new HashMap<String, String>();
        try {
            configDataMap = getConfigurationInfo(FrameworkConsts.ENVIRONMENTCLIENTID, "", environmentName, "environmentdata");
            EllieMaeLog.log(_log, "Finished loading Environment Config data from EnvironmentInfo.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

            if (!FrameworkConsts.isEnvDetailsLogged.get()) {
                for (Map.Entry<String, String> entry : configDataMap.entrySet()) {
                    EllieMaeLog.log(_log, "Key: " + entry.getKey() + ";    Value:" + entry.getValue(), EllieMaeLogLevel.reporter);
                }
                FrameworkConsts.isEnvDetailsLogged.set(true);
            }
        } catch (Exception e) {
            EllieMaeLog.log(_log, "Exception in Loading Enviornment Config data from EnvironmentInfo.xlsx" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return configDataMap;
    }

    /**
     * <b>Name:</b>getEnvironmentData<br>
     * <b>Description:</b> Returns the data in Environment Info sheet in
     * EnvironmentConfig excel depending upon the environmentName key provided
     * as input<br>
     * 
     * @param environmentName
     * @return hashMap
     */
    public static HashMap<String, String> getEnvironmentData(String environmentName, String dataType) {
        environmentName = environmentName.trim();
        HashMap<String, String> configDataMap = new HashMap<String, String>();
        try {

            configDataMap = getConfigurationInfo(FrameworkConsts.ENVIRONMENTCLIENTID, "", environmentName, dataType);
            EllieMaeLog.log(_log, "Finished loading Environment Config data from EnvironmentInfo.xlsx to Hash Map.", EllieMaeLogLevel.reporter);

            if (!FrameworkConsts.isEnvDetailsLogged.get()) {
                for (Map.Entry<String, String> entry : configDataMap.entrySet()) {
                    EllieMaeLog.log(_log, "Key: " + entry.getKey() + ";    Value:" + entry.getValue(), EllieMaeLogLevel.reporter);
                }
                FrameworkConsts.isEnvDetailsLogged.set(true);
            }
        } catch (Exception e) {
            EllieMaeLog.log(_log, "Exception in Loading Enviornment Config data from EnvironmentInfo.xlsx" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return configDataMap;
    }

    /**
     * <b>Name:</b>getConfigurationInfo<br>
     * <b>Description:</b> Returns the data in
     * MongoConfigurationInfo/SQLConfigurationInfo/SOAPServiceName/UserList/EnvironmentInfo
     * sheet in EnvironmentConfig excel depending upon the
     * environmentClientID/environmentName/UserKey provided as input<br>
     * 
     * @param environmentClientID,userKey,environmentName,requestType
     * @return hashMap
     */
    private static HashMap<String, String> getConfigurationInfo(String clientID, String userKey, String environmentName, String requstType) {
        HashMap<String, String> userListDataMap = new HashMap<String, String>();
        try {
            String environmentConfigFileRelativePath = null;
            String query = "";
            switch (requstType.toLowerCase()) {
                case "mongoconfiguration":
                    if (file.exists()) {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                        EllieMaeLog.log(_log, "Loading MongoConfigurationInfo data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);
                    } else {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("env", "MongoConfigurationInfo.xlsx");
                        EllieMaeLog.log(_log, "Loading MongoConfigurationInfo data from MongoConfigurationInfo.xlsx", EllieMaeLogLevel.reporter);
                    }
                    query = "Select * from MongoConfigurationInfo where ClientID='" + clientID + "' and EnvironmentName='" + environmentName + "'";
                    break;

                case "sqlconfiguration":
                    if (file.exists()) {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                        EllieMaeLog.log(_log, "Loading SQLConfigurationInfo data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);
                    } else {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("env", "SQLConfigurationInfo.xlsx");
                        EllieMaeLog.log(_log, "Loading SQLConfigurationInfo data from SQLConfigurationInfo.xlsx", EllieMaeLogLevel.reporter);
                    }
                    query = "Select * from SQLConfigurationInfo where ClientID='" + clientID + "' and EnvironmentName='" + environmentName + "'";
                    break;

                case "soapservicename":
                    if (file.exists()) {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                        EllieMaeLog.log(_log, "Loading SOAPServiceName data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);
                    } else {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("env", "SOAPServiceName.xlsx");
                        EllieMaeLog.log(_log, "Loading SOAPServiceName data from SOAPServiceName.xlsx", EllieMaeLogLevel.reporter);
                    }
                    query = "Select * from SOAPServiceName";
                    break;

                case "userlistdata":
                    if (file.exists()) {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                        EllieMaeLog.log(_log, "Loading UserList data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);
                    } else {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("env", "UserList.xlsx");
                        EllieMaeLog.log(_log, "Loading UserList data from UserList.xlsx", EllieMaeLogLevel.reporter);
                    }
                    query = "Select * from UserList where UserKey = '" + userKey + "'";
                    break;

                case "environmentdata":
                    if (file.exists()) {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                        EllieMaeLog.log(_log, "Loading EnvironmentInfo data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);
                    } else {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("env", "EnvironmentInfo.xlsx");
                        EllieMaeLog.log(_log, "Loading Config data from EnvironmentInfo.xlsx", EllieMaeLogLevel.reporter);
                    }
                    query = "Select * from EnvironmentInfo where EnvironmentName='" + environmentName + "'";
                    break;


                case "ebsurl":
                    if (file.exists()) {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("config", "EnvironmentConfig.xlsx");
                        EllieMaeLog.log(_log, "Loading EnvironmentInfo data from EnvironmentConfig.xlsx", EllieMaeLogLevel.reporter);
                    } else {
                        environmentConfigFileRelativePath = CommonUtility.getRelativeFilePath("env", "EBSURL.xlsx");
                        EllieMaeLog.log(_log, "Loading Config data from EnvironmentInfo.xlsx", EllieMaeLogLevel.reporter);
                    }
                    query = "Select * from EBSURL where EnvironmentName='" + environmentName + "' AND ClientID='" + clientID + "'";
                    break;
            }

            Recordset recordSetUserListData = CommonUtility.getRecordSetUsingFillo(environmentConfigFileRelativePath, query);

            if (recordSetUserListData == null) {
                EllieMaeLog.log(_log, "No Information found. Please check the user input : [ClientID:  " + clientID + ", UserKey: " + userKey
                        + ", EnvironmentName: " + environmentName + " ].", EllieMaeLogLevel.reporter);
                return null;
            }

            Object[] list = new Object[recordSetUserListData.getFieldNames().size()];

            list = recordSetUserListData.getFieldNames().toArray();

            while (recordSetUserListData.next()) {

                for (Object obj : list) {
                    String key = obj.toString();
                    userListDataMap.put(key, recordSetUserListData.getField(key));
                }
            }
        } catch (Exception e) {
            EllieMaeLog.log(_log, "Exception in Loading getConfigurationInfo data from Environment Config files" + e.getMessage(),
                    EllieMaeLogLevel.reporter);
        }

        return userListDataMap;
    }
}
