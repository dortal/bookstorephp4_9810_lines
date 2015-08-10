package com.accor.asa.commun.versioninfo.metier;

import java.io.Serializable;

/**
  * données récupérées par la procédure proc_meta_get_dbinfo
  */
public class DatabaseVersionInformation implements Serializable {
    
    public DatabaseVersionInformation( String execDate, String serverName,
        String userName, String loginName,
        String dbName, String asaVersion ) {
        this.execDate=execDate;
        this.serverName=serverName;
        this.userName=userName;
        this.loginName=loginName;
        this.dbName=dbName;
        this.asaVersion=asaVersion;
    }

    public String execDate;
    public String serverName;
    public String userName;
    public String loginName;
    public String dbName;
    public String asaVersion;       
}