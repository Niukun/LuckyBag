package com.lgc;

import com.alibaba.fastjson.TypeReference;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public  class StartUpClientModel
{
		private String contextID = "";
          
        public StartUpClientModel()
        {
            this.contextID =UUID.randomUUID().toString().replace("-", "");
        }
        
        public StartUpClientModel(String contextID)
        {
            this.contextID = contextID;
        }
        
        public <T> T GetService(String assemblyClass,Class<?> clazz) throws Exception
        {
            T reValue;
            //this.CreateContext();
            reValue = ModelManager.getInstance().GetService(this.contextID,assemblyClass, clazz);
            return reValue;
        }
       
        public void InitModelManager(List<String> serverURLs)
        {
            ModelManager.getInstance().InitModelManager(serverURLs);
        }
      
        public void Logout()
        {
            ModelManager.getInstance().Logout(this.contextID);
        }
        
        public boolean  ModifyPassword(String oldPassword, String newPassword)
        {
            return ModelManager.getInstance().ModifyPassword(this.contextID,oldPassword, newPassword);
        }
       
        public boolean UserChange(String currpassword, String userName, String password)
        {
            return ModelManager.getInstance().UserChange(this.contextID, currpassword, userName, password);
        }
         public LoginResponseDto GetLoginResponseDto(String LoginResponseDto)
        {
            LoginResponseDto reValue = null;
            reValue = ModelManager.getInstance().GetLoginResponseDto(LoginResponseDto, this.contextID);
            return reValue;
        }
        public String getContextID()
        {
            return this.contextID;// ModelManager.Instance.ContextID;
        }
        public List<DictionaryDto> GetCodeList(String name)
        {
            List<DictionaryDto> reValue = null;
            reValue = ModelManager.getInstance().GetCodeList(this.contextID, name);
            return reValue;
        }
       
        public List<DictionaryDto> GetCodeList(String name, String ID)
        {
            List<DictionaryDto> reValue = null;
            //this.CreateContext();
            reValue = ModelManager.getInstance().GetCodeList(this.contextID, name, ID);
            return reValue;
        }
        public static int DeleteCodeList(String name)
        {
            return ModelManager.getInstance().DeleteCodeList(UUID.randomUUID().toString().replace("-", ""), name);
        }
           public List<LoginResponseDto> GetLoginedDesks(String type)
        {
            List<LoginResponseDto> reValue = null;

            //this.CreateContext();
            reValue = ModelManager.getInstance().GetLoginedDesks(this.contextID, type);
            return reValue;
        }
       
        public String GetFile(String pathName,String fileName)
        {
            String reValue = null;
            reValue = ModelManager.getInstance().GetFile(this.contextID, fileName, pathName);
            return reValue;
        }
        
        public String UpFile(String fileName, int speedRate,InputStream inputStream)
        {
            String reValue = null;
            reValue = ModelManager.getInstance().UpFile(this.contextID, fileName, speedRate, inputStream);
            return reValue;
        }
       
        public List<FilePathDto> GetFilePath()
        {
            List<FilePathDto> reValue = null;
            reValue= (List<FilePathDto>)com.alibaba.fastjson.JSON.parseObject(ModelManager.getInstance().GetFilePath(this.contextID), new TypeReference<List<FilePathDto>>(){});
            return reValue;
        }
        
        public void WriteLog(String logGrade, String outPath, String type, String content)
        {
            ModelManager.getInstance().WriteLog(logGrade, outPath, type, content);
        }
        
        public <T> T GetMsgObject(String value,Class<?> clazz)
        {
            T reValue ;
            reValue =(T)com.alibaba.fastjson.JSON.parseObject(value,clazz);
            //reValue = Newtonsoft.Json.JsonConvert.DeserializeObject<T>(value);

            Class<?> t = reValue.getClass();
            Field[] dtoPropertys = t.getDeclaredFields();
            for(Field f:dtoPropertys)
            {
                try
                {
                    f.setAccessible(true);
                    Object _objValue = f.get(reValue);
                    
                    //object _objValue = t.InvokeMember(dtoPropertyInfo.Name, System.Reflection.BindingFlags.GetProperty, null, reValue, null);
                    if (null != _objValue)
                    {
                        if (_objValue.toString().toUpperCase() == "NULL")
                        {
                            f.set(reValue, null);
                            //dtoPropertyInfo.SetValue(reValue, lm.com.NullValues.STRING_NULL, null);
                        }
                    }
                }
                catch(IllegalAccessException ex)
                {
                }
            }
         
            return reValue;
        }
       
        public String ObjectToJson(Object obj)
        {
            return com.alibaba.fastjson.JSON.toJSONString(obj);

        }
        // public String XMLToJson(XmlNode obj)
        // {
        //     return Newtonsoft.Json.JsonConvert.SerializeXmlNode(obj);

        // }
        // public XmlDocument JsonToXML(String obj)
        // {
        //     return Newtonsoft.Json.JsonConvert.DeserializeXmlNode(obj);

        // }
        public static List<String> GetClientSessions()
        {
           return ModelManager.getInstance().GetClientSessions(UUID.randomUUID().toString().replace("-", ""));
        }
    }
