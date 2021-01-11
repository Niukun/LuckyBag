package com.lgc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppAddrServiceImpl implements IAppAddrService
{
    private String strUrl="";
    private String strCliendID="";
    public AppAddrServiceImpl()
    {

    }
    public AppAddrServiceImpl(String _Url,String _CliendID)
    {
        this.strUrl=_Url;
        this.strCliendID=_CliendID;
    }
    public boolean ModifyPassword(String arg0/*oldPassword*/, String arg1/*newPassword*/)
    {
        boolean reValue=true;
        try {

            URL restServiceURL = new URL(this.strUrl+"/ModifyPassword/"+arg0+"/"+arg1);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            if (httpConnection.getResponseCode() != 200) {
                return false;
                   //throw new RuntimeException("HTTP GET Request Failed with Error code : "
                   //              + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                   (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;

            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();

            } catch (MalformedURLException e) {
                reValue= false;
                //e.printStackTrace();

            } catch (IOException e) {
                reValue= false;
                //e.printStackTrace();

            }

        return reValue;
    }


    public boolean UserChange(String currpassword, String userName, String password)
    {
        boolean reValue=true;
        try {

            URL restServiceURL = new URL(this.strUrl+"/UserChange/"+currpassword+"/"+userName+"/"+password);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            if (httpConnection.getResponseCode() != 200) {
                return false;
                   //throw new RuntimeException("HTTP GET Request Failed with Error code : "
                   //              + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                   (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;

            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();

            } catch (MalformedURLException e) {
                reValue= false;
                //e.printStackTrace();

            } catch (IOException e) {
                reValue= false;
                //e.printStackTrace();

            }

        return reValue;
    }


    public List<DictionaryDto> GetCodeList(String name)
    {
        List<DictionaryDto> reValue=new ArrayList<DictionaryDto>();
        try {

            URL targetUrl = new URL(this.strUrl+"/GetCodeList");

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            String input;// = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
            input=com.alibaba.fastjson.JSON.toJSONString(name);

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                //    throw new RuntimeException("Failed : HTTP error code : "
                //           + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;
           // System.out.println("Output from Server:\n");
            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            int i=output.indexOf(":");
            output=output.substring(i+1, output.length()-1);
            reValue= (List<DictionaryDto>)com.alibaba.fastjson.JSON.parseArray(output, DictionaryDto.class);

       } catch (MalformedURLException e) {

           // e.printStackTrace();

       } catch (IOException e) {

           // e.printStackTrace();

        }
        return reValue;
    }


    public List<DictionaryDto> GetCodeListByID(String name, String ID)
    {
        List<DictionaryDto> reValue=new ArrayList<DictionaryDto>();
        try {

            URL restServiceURL = new URL(this.strUrl+"/GetCodeListByID/"+name+"/"+ID);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                   //throw new RuntimeException("HTTP GET Request Failed with Error code : "
                   //              + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                   (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;

            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            int i=output.indexOf(":");
            output=output.substring(i+1, output.length()-1);
            reValue= (List<DictionaryDto>)com.alibaba.fastjson.JSON.parseArray(output, DictionaryDto.class);
        } catch (MalformedURLException e) {
                //e.printStackTrace();

        } catch (IOException e) {
                //e.printStackTrace();

        }

        return reValue;
    }


    public LoginResponseDto GetLoginResponseDto(String loginResponseDto)
    {
        LoginResponseDto reValue=null;
        try {

            URL targetUrl = new URL(this.strUrl+"/GetLoginResponseDto");

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            String input;// = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
            input=com.alibaba.fastjson.JSON.toJSONString(loginResponseDto);

            OutputStream outputStream =(OutputStream) httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                //    throw new RuntimeException("Failed : HTTP error code : "
                //           + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;
           // System.out.println("Output from Server:\n");
            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            //ModelManager.getInstance().WriteLog("INFO", "AppAdrr", "GetLoginResponseDto", output);
            reValue=(LoginResponseDto)com.alibaba.fastjson.JSON.parseObject(output,LoginResponseDto.class);
            //ModelManager.getInstance().WriteLog("INFO", "AppAdrr", "GetLoginResponseDtoc", reValue.COUSTOMNAME);
       } catch (MalformedURLException e) {

           // e.printStackTrace();

       } catch (IOException e) {

           // e.printStackTrace();

        }
        return reValue;
    }


    public BytesTransmissionInfoDto DowndLoadFile(PositionDto position)
    {
        BytesTransmissionInfoDto reValue=null;
        try {

            URL targetUrl = new URL(this.strUrl+"/DowndLoadFile");

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            String input;// = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
            input=com.alibaba.fastjson.JSON.toJSONString(position);

            OutputStream outputStream =(OutputStream) httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                //    throw new RuntimeException("Failed : HTTP error code : "
                //           + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;
           // System.out.println("Output from Server:\n");
            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            reValue=(BytesTransmissionInfoDto)com.alibaba.fastjson.JSON.parseObject(output,BytesTransmissionInfoDto.class);

       } catch (MalformedURLException e) {

           // e.printStackTrace();

       } catch (IOException e) {

           // e.printStackTrace();

        }
        return reValue;
    }


    public long GetFileSize(String infoPath)
    {
        long reValue=0;
        try {

            URL targetUrl = new URL(this.strUrl+"/GetFileSize");

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            String input;// = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
            input=com.alibaba.fastjson.JSON.toJSONString(infoPath);

            OutputStream outputStream =(OutputStream) httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                //    throw new RuntimeException("Failed : HTTP error code : "
                //           + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;
           // System.out.println("Output from Server:\n");
            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            reValue=(long)com.alibaba.fastjson.JSON.parseObject(output,long.class);

       } catch (MalformedURLException e) {

           // e.printStackTrace();

       } catch (IOException e) {

           // e.printStackTrace();

        }
        return reValue;
    }


    public String UpLoadFile(BytesTransmissionInfoDto bytesTransmissionInfoDto)
    {
        String reValue="";
        try {

            URL targetUrl = new URL(this.strUrl+"/UpLoadFile");

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            String input;// = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
            input=com.alibaba.fastjson.JSON.toJSONString(bytesTransmissionInfoDto);

            OutputStream outputStream =(OutputStream) httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                //    throw new RuntimeException("Failed : HTTP error code : "
                //           + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;
           // System.out.println("Output from Server:\n");
            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            reValue=(String)com.alibaba.fastjson.JSON.parseObject(output,String.class);

       } catch (MalformedURLException e) {

           // e.printStackTrace();

       } catch (IOException e) {

           // e.printStackTrace();

        }
        return reValue;
    }


    public List<LoginResponseDto> GetLoginedDesks(String type)
    {
        List<LoginResponseDto> reValue=new ArrayList<LoginResponseDto>();
        try {

            URL restServiceURL = new URL(this.strUrl+"/GetLoginedDesks/"+type);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                   //throw new RuntimeException("HTTP GET Request Failed with Error code : "
                   //              + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                   (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;

            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            int i=output.indexOf(":");
            output=output.substring(i+1, output.length()-1);
            reValue= (List<LoginResponseDto>)com.alibaba.fastjson.JSON.parseArray(output, LoginResponseDto.class);
        } catch (MalformedURLException e) {
                //e.printStackTrace();

        } catch (IOException e) {
                //e.printStackTrace();

        }
        return reValue;
    }


    public String GetServiceMyURI(String _assemblyClass)
    {
        String reValue="http:";
        try {

            URL targetUrl = new URL(this.strUrl+"/GetServiceMyURI");

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            String input;// = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
            input=com.alibaba.fastjson.JSON.toJSONString(_assemblyClass);

            OutputStream outputStream =(OutputStream) httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                //    throw new RuntimeException("Failed : HTTP error code : "
                //           + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;
           // System.out.println("Output from Server:\n");
            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            reValue=(String)com.alibaba.fastjson.JSON.parseObject(output,String.class);

       } catch (MalformedURLException e) {

           // e.printStackTrace();

       } catch (IOException e) {

           // e.printStackTrace();

        }
        return reValue;
    }


    public int DeleteCodeList(String name)
    {
        int reValue=0;
        try {

            URL restServiceURL = new URL(this.strUrl+"/DeleteCodeList/"+name);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                   //throw new RuntimeException("HTTP GET Request Failed with Error code : "
                   //              + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                   (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;

            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            reValue=(int)com.alibaba.fastjson.JSON.parseObject(output,int.class);

        } catch (MalformedURLException e) {
                //e.printStackTrace();

        } catch (IOException e) {
                //e.printStackTrace();

        }
        return reValue;
    }


    public List<String> GetClientSessions(String str)
    {
        List<String> reValue=new ArrayList<String>();
        try {

            URL targetUrl = new URL(this.strUrl+"/GetCodeList");

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            String input;// = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
            input=com.alibaba.fastjson.JSON.toJSONString(str);

            OutputStream outputStream =(OutputStream) httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                //    throw new RuntimeException("Failed : HTTP error code : "
                //           + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;
           // System.out.println("Output from Server:\n");
            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            int i=output.indexOf(":");
            output=output.substring(i+1, output.length()-1);
            reValue= (List<String>)com.alibaba.fastjson.JSON.parseArray(output, String.class);

       } catch (MalformedURLException e) {

           // e.printStackTrace();

       } catch (IOException e) {

           // e.printStackTrace();

        }
        return reValue;
    }
    public void Logout()
    {
        try {

            URL restServiceURL = new URL(this.strUrl+"/Logout");

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            if (httpConnection.getResponseCode() != 200) {
                return ;
                   //throw new RuntimeException("HTTP GET Request Failed with Error code : "
                   //              + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                   (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;

            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {
                //e.printStackTrace();

        } catch (IOException e) {
                //e.printStackTrace();

        }
    }


    public String GetFilePath()
    {
        String reValue="";
        try {

            URL restServiceURL = new URL(this.strUrl+"/GetFilePath");

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestProperty("SOAPAction", this.strCliendID);

            if (httpConnection.getResponseCode() != 200) {
                return reValue;
                   //throw new RuntimeException("HTTP GET Request Failed with Error code : "
                   //              + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                   (httpConnection.getInputStream())));

            String output="";
            String tmpoutput;

            while ((tmpoutput = responseBuffer.readLine()) != null) {
                output=output+tmpoutput;
            }

            httpConnection.disconnect();
            reValue=(String)com.alibaba.fastjson.JSON.parseObject(output,String.class);

        } catch (MalformedURLException e) {
                //e.printStackTrace();

        } catch (IOException e) {
                //e.printStackTrace();

        }
        return reValue;
    }
}