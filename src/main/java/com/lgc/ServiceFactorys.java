package com.lgc;
//import java.awt.dnd.DnDConstants;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public  class ServiceFactorys
    {
        
        private static  ServiceFactorys instance = new ServiceFactorys();
        public static ServiceFactorys getInstance()
        {
            
                return instance;
          
        }
        
        public <InterfaceType/*, BaseType*/> InterfaceType GetProxyObject(String URL, String cliendID, String contractType,Class<?> clazz) throws Exception
        {
            InterfaceType reVaule=null;
            
            {
                Class<?> interfaceType = clazz;
                //this.strCliendID = cliendID;
                this.ContractType = contractType;
                Class<?> targetType = null;
                switch (ContractType.toUpperCase())
                {
                    case "JSON":
                        targetType = GetProxyTypeForJson(interfaceType);
                         //String str1 = "abc";
                        Constructor<?> con = targetType.getConstructor(String.class,String.class);
                        reVaule=(InterfaceType) con.newInstance(URL,cliendID);
                        // System.out.println(con.newInstance(str1));
                        //URL  header
                        // reVaule = (InterfaceType)Activator.CreateInstance(targetType, new object[] { URL, cliendID/*this.strCliendID*/ });
                        //reVaule=(InterfaceType)targetType.newInstance();
                        break;
                    default:
                         break;
                }
            }
            return reVaule;
        }

        private Class<?> GetProxyTypeForJson(Class<?> interfaceType) throws Exception
        {
            CheckParams(interfaceType);

            String proxyClassName = "_dynamicproxy" + interfaceType.getSimpleName() + "Impl";
            // boolean blFlag=false;
            // for (String key : typeList.keySet())
            // {
            //     if(key==proxyClassName)
            //     {
            //         blFlag=true;
            //         break;
            //     }
            // }
            StringBuilder src = new StringBuilder();
            if(null==typeList.get(proxyClassName))
            {

                //Add it to proper package
//                src.append("package com.hrssc.service.impl;\n");


//                src.append("package com.hrssc.service.impl;\n");
//                src.append("import com.hrssc.entity.*;\n");
//                src.append("import com.lgc.SqlConditionDto;\n");
                src.append("import java.io.*;\n");





                src.append("import java.net.*;\n");
               // src.append("import java.util.*;\n");
                src.append("public class "+proxyClassName+" implements  ");
                src.append(interfaceType.getName()+"  {\n");
                src.append("    private String strUrl=\"\" ; \n");
                src.append("    private String strCliendID=\"\" ; \n");
                src.append(this.CreateCtor(proxyClassName)+" \n");

               Method[] MethodsBuilder= interfaceType.getDeclaredMethods();
               for(Method method : MethodsBuilder)
                {
                    src.append(this.CreateMethod(method)+"\n");
                }
                src.append("  }\n");
               //Second develop to save the src file for the project
//                StrUtils.strToJavaFile(proxyClassName, src.toString());



                //ModelManager.getInstance().WriteLog("INFO", "ServiceFactorys", "source.code", src.toString());
                //System.out.println(src.toString());
                DynamicEngine de = DynamicEngine.getInstance();
//                Class<?> instance = de.myJavaCodeToObject(proxyClassName,src.toString());

                Class<?> instance = de.myJavaCodeToObject(proxyClassName, src.toString());
//                Field strCliendID = aClass.getField("strCliendID");
//                Field strUrl = aClass.getField("strUrl");


                // String str1 = "abc";
                // Constructor con = String.class.getConstructor(String.class);
                // System.out.println(con.newInstance(str1));
                typeList.put(proxyClassName, instance);
                
            }
            
            return typeList.get(proxyClassName);
        }
        private void CheckParams(Class<?> interfaceType) throws Exception
        {
            if (!interfaceType.isInterface())  //
                throw new Exception();

        }

        private String CreateCtor(String _proxyClassName)
        {
            String reValue="";
            reValue=reValue+"public "+_proxyClassName+"()\n";
            reValue=reValue+"{\n";
            reValue=reValue+"}\n";
            reValue=reValue+"public "+_proxyClassName+"(String _Url,String _CliendID)\n";
            reValue=reValue+"{\n";
            reValue=reValue+"   this.strUrl=_Url;\n";
            reValue=reValue+"   this.strCliendID=_CliendID;\n";
            reValue=reValue+"}\n";
            return reValue;
        }

        private String CreateMethod(Method _method)
        {
            StringBuilder src = new StringBuilder();
            String  methodName=_method.getName();
            Type reType=_method.getGenericReturnType();//.getReturnType();
            //_method.
            src.append("public "+reType.getTypeName()+" " +methodName+"("); 
            String strParameter="";
            String parameterName="";
            for(Type parameter : _method.getGenericParameterTypes())//.getParameters())
            {
                strParameter=strParameter+parameter.getTypeName()/*.getType().getName()*/+" arg0";//+parameter.getName()+",";
                //parameterName=parameter.getName();
            }
           //strParameter=strParameter.substring(0, strParameter.length()-1);
            src.append(strParameter+")\n");
            src.append("{\n");
            if(reType.getTypeName().indexOf("<")>=0)
            {
                int i=reType.getTypeName().indexOf("<");
                parameterName=reType.getTypeName().substring(i);
                src.append(reType.getTypeName()+" reValue=new java.util.ArrayList"+parameterName+"();\n");
            }
            else
            {
                src.append(reType.getTypeName()+" reValue=null;\n");
            }
           
            src.append("try {\n");

                src.append("URL targetUrl = new URL(this.strUrl+\"/"+methodName+"\");\n");

                src.append("HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();\n");
                src.append("httpConnection.setDoOutput(true);\n");
                src.append("httpConnection.setRequestMethod(\"POST\");\n");
                src.append("httpConnection.setRequestProperty(\"Content-Type\", \"application/json; charset=utf-8\");\n");
                src.append("httpConnection.setReadTimeout(30000);\n");
                src.append("httpConnection.setRequestProperty(\"SOAPAction\", this.strCliendID);\n");
                src.append("String input;\n");// = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
                src.append("input=(String)com.alibaba.fastjson.JSON.toJSONString(arg0);\n");

                src.append("OutputStream outputStream =(OutputStream) httpConnection.getOutputStream();\n");
                src.append("outputStream.write(input.getBytes());\n");
                src.append("outputStream.flush();\n");

                src.append("if (httpConnection.getResponseCode() != 200) {\n");
                    src.append("return reValue;\n");
                    //    throw new RuntimeException("Failed : HTTP error code : "
                    //           + httpConnection.getResponseCode());
                    src.append("}\n");

                    src.append("BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(\n");
                        src.append("          (httpConnection.getInputStream())));\n");

                        src.append("String output=\"\";\n");
                        src.append("String tmpoutput;\n");
               src.append("while ((tmpoutput = responseBuffer.readLine()) != null) {\n");
                src.append("    output=output+tmpoutput;\n");
                src.append("}\n");

                src.append("httpConnection.disconnect();\n");
                if(reType.getTypeName().indexOf("<")>=0)
                {
                    src.append("int i=output.indexOf(\":\");\n");
                    src.append("output=output.substring(i+1, output.length()-1);\n");
                    src.append("reValue= ("+reType.getTypeName()+")com.alibaba.fastjson.JSON.parseArray(output, "+parameterName.substring(1,parameterName.length()-1)+".class);\n");
                }
                else
                {
                    src.append("reValue=("+reType.getTypeName()+")com.alibaba.fastjson.JSON.parseObject(output,"+reType.getTypeName()+".class);\n");
                }
               

                src.append("} catch (MalformedURLException e) {\n");

               // e.printStackTrace();

               src.append("} catch (IOException e) {\n");

               // e.printStackTrace();

               src.append("}\n");
               src.append("return reValue;\n");
            src.append("}\n");
  
            return src.toString();
      }
       
        private Map<String, Class<?>> typeList = new HashMap<String, Class<?>>();
        private String ContractType = "JSON";

    }

