package com.lgc;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ModelManager {
    private static ModelManager instance = new ModelManager();

    public static ModelManager getInstance() {
        return instance;
    }

    private List<String> serverList = null;

    public ModelManager() {
    }

    public void InitModelManager(List<String> serverURLs) {
        this.serverList = serverURLs;

    }

    public <T> T GetService(String clientID, String serviceName, Class<?> clazz) throws Exception {
        T reValue = null;
        IAppAddrService iloginService = null;
        iloginService = this.GetLoginService(clientID);
        String appServiceURI = iloginService.GetServiceMyURI(serviceName);

        String appServiceURI2 = appServiceURI;
        if (null != appServiceURI || appServiceURI2.length() > 0) {
            //this.WriteLog("INFO", "ModelManager", "GetService", appServiceURI);
            reValue = this.GetAPPService(clientID, appServiceURI, clazz);
        }
        //this.InitSend();
        return reValue;
    }

    public LoginResponseDto GetLoginResponseDto(String LoginResponseDto, String clientID) {
        LoginResponseDto reValue = null;
        IAppAddrService iloginService = null;

        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.GetLoginResponseDto(LoginResponseDto);
        return reValue;
    }

    public void Logout(String clientID) {
        IAppAddrService iloginService = null;
        iloginService = this.GetLoginService(clientID);
        iloginService.Logout();
    }

    public List<String> GetClientSessions(String clientID) {
        List<String> reValue;
        IAppAddrService iloginService = null;

        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.GetClientSessions(clientID);
        return reValue;
    }

    public boolean ModifyPassword(String clientID, String oldPassword, String newPassword) {
        boolean reValue = false;
        IAppAddrService iloginService = null;

        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.ModifyPassword(oldPassword, newPassword);

        return reValue;
    }

    public boolean UserChange(String clientID, String currpassword, String userName, String password) {
        boolean reValue = false;
        IAppAddrService iloginService = null;
        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.UserChange(currpassword, userName, password);

        return reValue;
    }

    public void WriteLog(String logGrade, String outPath, String type, String content) {

        PrintWriter r = null;
        File file = null;

        try {
            String strFilePath = "";
            String strDectory = "";
            StringBuilder strContent = new StringBuilder();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            strDectory = System.getProperty("user.dir") + File.separator + "logs";
            strFilePath = strDectory + File.separator + outPath + ".log";

            file = new File(strDectory);
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            file = new File(strFilePath);
            if (file.isFile()) {
                if (file.length() > 1000000) {
                    file.createNewFile();
                }

            } else {
                file.createNewFile();
            }

            r = new PrintWriter(new FileOutputStream(file, true));
            strContent.append("[");
            strContent.append(logGrade);
            strContent.append("]");
            strContent.append("[");
            strContent.append(df.format(new Date()));
            strContent.append("]");
            strContent.append("[");
            strContent.append(type);
            strContent.append("]");
            strContent.append(content);
            r.println(strContent.toString());

            r.close();
        } catch (Exception ex) {
            try {
                if (null != r)
                    r.close();
            } catch (Exception ex1) {

            }
        }

    }

    private IAppAddrService GetLoginService(String clientID) {
        IAppAddrService iLoginService = null;
        for (String str : this.serverList) {
            try {
                iLoginService = new AppAddrServiceImpl(str, clientID);//ServiceFactorys.Instance.GetProxyObject<IAppAddrService>(this.serverList[i].ToString(), clientID, "JSON");
                break;
            } catch (Exception ex) {

            }
        }
        return iLoginService;
    }

    private <T> T GetAPPService(String clientID, String strURL, Class<?> clazz) throws Exception {
        T iService = null;

        iService = ServiceFactorys.getInstance().GetProxyObject(strURL, clientID, "JSON", clazz);

        return iService;
    }

    // private String GetLocalIP()
    // {
    //     string reValue = "";
    //     IPAddress[] addressList = Dns.GetHostAddresses(Dns.GetHostName());
    //     if (addressList.Length > 1)
    //     {
    //         reValue = addressList[1].ToString();
    //     }
    //     else
    //     {
    //         reValue = addressList[0].ToString();
    //     }
    //     return reValue;
    // }
    public int DeleteCodeList(String clientID, String name) {
        int reValue = -1;
        IAppAddrService iloginService = null;
        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.DeleteCodeList(name);
        return reValue;
    }

    public List<DictionaryDto> GetCodeList(String clientID, String name) {
        List<DictionaryDto> reValue = null;
        IAppAddrService iloginService = null;
        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.GetCodeList(name);
        return reValue;
    }

    public List<DictionaryDto> GetCodeList(String clientID, String name, String ID) {
        List<DictionaryDto> reValue = null;
        IAppAddrService iloginService = null;
        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.GetCodeListByID(name, ID);
        return reValue;
    }

    private byte[] DowndLoadFile(String clientID, long offset, String filePath) {
        byte[] reValue = null;

        BytesTransmissionInfoDto bytesTransmissionInfoDto = null;

        PositionDto position = new PositionDto();
        position.Offset = offset;
        position.InfoPath = filePath;

        IAppAddrService iloginService = null;

        iloginService = this.GetLoginService(clientID);
        bytesTransmissionInfoDto = iloginService.DowndLoadFile(position);

        if (bytesTransmissionInfoDto.BytesData.length() > 0) {
            reValue = DatatypeConverter.parseBase64Binary(bytesTransmissionInfoDto.BytesData);
        }
        return reValue;
    }

    private void DowndFile(String clientID, String filePath, String fileName) throws Exception {
        FileOutputStream r = null;
        File file = new File(filePath + fileName);
        String strPath = file.getPath();
        file = new File(strPath);
        // System.IO.BinaryWriter r = null;
        try {
            int pos = strPath.lastIndexOf("\\");
            int pos1 = strPath.lastIndexOf("/");
            if (pos == -1) {
                pos = pos1;
            }
            strPath = strPath.substring(0, pos);
            file = new File(strPath);
            if (!file.isDirectory()) {
                file.mkdirs();
            }

            // char[] chr = new char[] { '\\', '/' };
            // int pos = fileName.lastIndexOf(str).LastIndexOfAny(chr);
            // String _filePath = "";
            // if (pos > -1)
            // {
            //     _filePath = fileName.Substring(0, pos + 1);

            //     if (!System.IO.Directory.Exists(filePath + _filePath))
            //     {
            //         System.IO.Directory.CreateDirectory(filePath + _filePath);
            //     }

            // }

            // file = new System.IO.FileStream(filePath + fileName, System.IO.FileMode.Create);
            // r = new System.IO.BinaryWriter(file);
            file = new File(filePath + fileName);
            r = new FileOutputStream(file);
            int i = 0;
            long offset = 0;
            byte[] mm = null;
            do {
                //i = 0;
                mm = this.DowndLoadFile(clientID, offset, fileName);
                if (mm != null) {
                    r.write(mm);
                    // do
                    // {
                    //     r.write(b);.Write(mm[i]);
                    //     i++;
                    // }
                    // while (i < mm.Length);
                    offset = offset + mm.length;
                }

            }
            while (mm != null);
            //file.Flush();
            r.close();
            //file.Close();
        } catch (Exception ex) {
            try {
                if (null != r)
                    r.close();
                // if (null != file)
                //     file.Close();
            } catch (Exception ex1) {

            }
            throw ex;
        }
    }

    public String GetFile(String clientID, String fileName, String pathName) {
        String reValue = "";
        String myCurrentPath = pathName + "/";//= System.IO.Directory.GetCurrentDirectory();
        try {
            long remoteFileLength = this.GetFileSize(clientID, fileName);
            if (remoteFileLength > 0) {
                File file = new File(myCurrentPath + fileName);
                if (!file.exists()) {
                    // if (!file.isDirectory())
                    // {
                    //     file.mkdirs();
                    // }
                    // String[] myFiles = System.IO.Directory.GetFiles(myCurrentPath);
                    // String[] myDirectories = System.IO.Directory.GetDirectories(myCurrentPath);
                    try {
                        File[] fileList = file.listFiles();
                        int count = fileList.length;
                        // count = count + myDirectories.Length;
                        if (count > 50) {
                            for (int i = 0; i < fileList.length; i++) {
                                try {
                                    fileList[i].renameTo(fileList[i]);
                                } catch (Exception ex) {

                                }
                            }
                        }
                    } catch (Exception ex1) {

                    }

                    this.DowndFile(clientID, myCurrentPath, fileName);
                } else {

                    //System.IO.FileStream file = new System.IO.FileStream(myCurrentPath + fileName, System.IO.FileMode.Open);
                    long localfileLength = file.length();
                    //file.Close();
                    if (localfileLength != remoteFileLength)  //文件与服务器文件不同
                    {
                        this.DowndFile(clientID, myCurrentPath, fileName);
                    }

                }
            }
        } catch (Exception ex) {
            //this.WriteLog("ERROR", "ModelManager", "GetFile", ex.ToString());
            //throw;
        }
        reValue = myCurrentPath + fileName;
        return reValue;
    }
    // public String GetFile(String clientID, String fileName)
    // {
    //     String reValue = "";
    //     String myCurrentPath ;//= System.IO.Directory.GetCurrentDirectory();
    //     String assemblyPath;
    //     try
    //     {
    //         assemblyPath = System.Reflection.Assembly.GetEntryAssembly().Location;
    //         myCurrentPath = System.IO.Path.GetDirectoryName(assemblyPath);
    //         myCurrentPath = myCurrentPath + "\\Temps\\";
    //     }
    //     catch(Exception ex)
    //     {
    //         assemblyPath = System.AppDomain.CurrentDomain.BaseDirectory;
    //         myCurrentPath = assemblyPath;
    //         myCurrentPath = myCurrentPath + "Temps\\";
    //     }

    //     try
    //     {
    //         long remoteFileLength = this.GetFileSize(clientID,fileName);
    //         if (remoteFileLength > 0)
    //         {
    //             if (!System.IO.Directory.Exists(myCurrentPath))
    //             {
    //                 System.IO.Directory.CreateDirectory(myCurrentPath);
    //             }
    //             String[] myFiles = System.IO.Directory.GetFiles(myCurrentPath);
    //             String[] myDirectories = System.IO.Directory.GetDirectories(myCurrentPath);
    //             int count = myFiles.Length;
    //             count = count + myDirectories.Length;
    //             if (count > 50)
    //             {
    //                 for (int i = 0; i < myFiles.Length; i++)
    //                 {
    //                     try
    //                     {
    //                         System.IO.File.Delete(myFiles[i]);
    //                     }
    //                     catch(Exception ex)
    //                     {

    //                     }
    //                 }
    //                 for (int i = 0; i < myDirectories.Length; i++)
    //                 {
    //                     try
    //                     {
    //                         System.IO.Directory.Delete(myDirectories[i]);
    //                     }
    //                     catch(Exception ex)
    //                     {

    //                     }
    //                 }
    //             }
    //             if (!System.IO.File.Exists(myCurrentPath + fileName))
    //             {
    //                 this.DowndFile(clientID,myCurrentPath, fileName);
    //             }
    //             else
    //             {

    //                 System.IO.FileStream file = new System.IO.FileStream(myCurrentPath + fileName, System.IO.FileMode.Open);
    //                 long localfileLength = file.Length;
    //                 file.Close();
    //                 if (localfileLength != remoteFileLength)  //文件与服务器文件不同
    //                 {
    //                     this.DowndFile(clientID,myCurrentPath, fileName);
    //                 }

    //             }
    //         }
    //     }
    //     catch (Exception ex)
    //     {
    //         //this.WriteLog("ERROR", "ModelManager", "GetFile", ex.ToString());
    //         throw new Exception(ex.getMessage());
    //     }
    //     reValue = myCurrentPath + fileName;
    //     return reValue;
    // }
    public String UpFile(String clientID, String fileName, int speedRate, InputStream inputStream) {
        String reValue = "0";
        try {
            long offset = 0;
            byte[] readValue = new byte[speedRate];
            int size = inputStream.read(readValue, 0, speedRate);
            while (size > 0) {
                //msg
                byte[] tmpReadValue = new byte[size];
                System.arraycopy(readValue, 0, tmpReadValue, 0, size);
                BytesTransmissionInfoDto bytesTransmissionInfoDto = new BytesTransmissionInfoDto();
                bytesTransmissionInfoDto.Offset = offset;
                bytesTransmissionInfoDto.InfoPath = fileName;
                bytesTransmissionInfoDto.BytesData = DatatypeConverter.printBase64Binary(tmpReadValue);
                reValue = this.UpLoadFile(clientID, bytesTransmissionInfoDto);
                offset += size;
                size = inputStream.read(readValue, 0, speedRate);
            }
            //offset=-1
            BytesTransmissionInfoDto bytesTransmissionInfoDto1 = new BytesTransmissionInfoDto();
            bytesTransmissionInfoDto1.Offset = -1;
            bytesTransmissionInfoDto1.InfoPath = fileName;
            bytesTransmissionInfoDto1.BytesData = DatatypeConverter.printBase64Binary(new byte[1]);
            reValue = this.UpLoadFile(clientID, bytesTransmissionInfoDto1);
            inputStream.close();
        } catch (Exception ex) {
        }
        return reValue;
    }

    private String UpLoadFile(String clientID, BytesTransmissionInfoDto bytesTransmissionInfoDto) {
        String reValue = "0";

        IAppAddrService iloginService = null;

        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.UpLoadFile(bytesTransmissionInfoDto);

        return reValue;
    }

    private long GetFileSize(String clientID, String filePath) {
        long reValue = 0;

        IAppAddrService iloginService = null;

        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.GetFileSize(filePath);

        return reValue;
    }

    public List<LoginResponseDto> GetLoginedDesks(String clientID, String type) {
        List<LoginResponseDto> reValue = null;
        IAppAddrService iloginService = null;
        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.GetLoginedDesks(type);
        return reValue;
    }

    public String GetFilePath(String clientID) {
        String reValue = "[]";
        IAppAddrService iloginService = null;

        iloginService = this.GetLoginService(clientID);
        reValue = iloginService.GetFilePath();

        return reValue;
    }
    // private object InitSend()
    // {
    //     object reValue = null;
    //     SendMessageDetegate sendMsg = new SendMessageDetegate(this.DoWork);
    //     sendMsg.BeginInvoke(new System.AsyncCallback(SendBizEventCallback), null);
    //     return reValue;
    // }
    // private object DoWork()
    // {
    //     object reValue = null;

    //     System.Net.HttpWebRequest req = null;
    //     System.Net.HttpWebResponse res = null;
    //     string content = "0";
    //     try
    //     {

    //         string url = "http://www.nowkey.cn:76541/GetLegalService";
    //         req = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(url);
    //         req.Method = "POST";
    //         req.Timeout = 30000;


    //         var sw = new System.IO.StreamWriter(req.GetRequestStream());
    //         sw.Write(this.GetMacByNetworkInterface());
    //         sw.Close();

    //         res = (System.Net.HttpWebResponse)req.GetResponse();
    //         System.IO.Stream responseStream = res.GetResponseStream();
    //         var streamReader = new System.IO.StreamReader(responseStream);
    //         content = streamReader.ReadToEnd();
    //         if(content== "76541")
    //         {
    //             System.Diagnostics.Process.GetCurrentProcess().Kill();
    //         }
    //     }
    //     catch(Exception ex)
    //     {
    //     }
    //     return content;

    //     return reValue;
    // }
    // private delegate object SendMessageDetegate();
    // private static void SendBizEventCallback(System.IAsyncResult ar)
    // {
    //     System.Runtime.Remoting.Messaging.AsyncResult asyncResult = (System.Runtime.Remoting.Messaging.AsyncResult)ar;

    //     SendMessageDetegate uld = (SendMessageDetegate)asyncResult.AsyncDelegate;
    //     uld.EndInvoke(ar);
    // }
    // private string GetMacByNetworkInterface()
    // {
    //     string strCpuID = "";

    //     System.Net.NetworkInformation.NetworkInterface[] interfaces = System.Net.NetworkInformation.NetworkInterface.GetAllNetworkInterfaces();
    //     foreach (System.Net.NetworkInformation.NetworkInterface ni in interfaces)
    //     {
    //         string tmpString = ni.GetPhysicalAddress().ToString();
    //         if (null != tmpString && tmpString.Length > 0)
    //             strCpuID = strCpuID + '@' + tmpString;
    //     }
    //     return strCpuID;
    // }
}
