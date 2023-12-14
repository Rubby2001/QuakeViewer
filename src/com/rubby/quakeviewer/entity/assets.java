package com.rubby.quakeviewer.entity;

public class assets {

    private int count;
    private String url;
    private String ip;
    private String port;
    private String domain;
    private String service;
    private String title;
    private String cms;
    private String icp;
    private String company;

    public String getUrl() {
        return url;
    }
    public int getCount() {
        return count;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getDomain() {
        return domain;
    }

    public String getService() {
        return service;
    }

    public String getTitle() {
        return title;
    }

    public String getCms() {
        return cms;
    }

    public String getIcp() {
        return icp;
    }

    public String getCompany() {
        return company;
    }

    public assets(int count,String url, String ip, String port, String domain, String service, String title, String cms, String icp, String company) {
        this.count=count;
        this.url = url;
        this.ip = ip;
        this.port = port;
        this.domain = domain;
        this.service = service;
        this.title = title;
        this.cms = cms;
        this.icp = icp;
        this.company = company;
    }
}
