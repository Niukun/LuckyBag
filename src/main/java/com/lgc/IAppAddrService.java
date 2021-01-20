package com.lgc;

import java.util.List;

public interface IAppAddrService {

    boolean ModifyPassword(String oldPassword, String newPassword);


    boolean UserChange(String currpassword, String userName, String password);


    List<DictionaryDto> GetCodeList(String name);


    List<DictionaryDto> GetCodeListByID(String name, String ID);


    LoginResponseDto GetLoginResponseDto(String LoginResponseDto);


    BytesTransmissionInfoDto DowndLoadFile(PositionDto position);


    long GetFileSize(String infoPath);


    String UpLoadFile(BytesTransmissionInfoDto bytesTransmissionInfoDto);


    List<LoginResponseDto> GetLoginedDesks(String type);


    String GetServiceMyURI(String _assemblyClass);


    int DeleteCodeList(String name);


    List<String> GetClientSessions(String str);


    void Logout();


    String GetFilePath();
}

