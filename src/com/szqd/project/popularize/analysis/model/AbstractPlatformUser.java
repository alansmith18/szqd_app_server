package com.szqd.project.popularize.analysis.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by like on 4/1/15.
 */
public abstract class AbstractPlatformUser implements Serializable{

    private Long id;
    private Long platformID;
    private String platformName;
    private String loginName;
    private String loginPwd;
    private Float incremental = null;
    private Float scale = null;
    private Integer role = null;

    /**
     * 电话
     */
    private String contactInfo = null;

    /**
     * qq
     */
    private String qq = null;

    /**
     * 产品名称(应用程序)
     */
    private String productName = null;

    /**
     * 公司名称
     */
    private String companyName = null;

    /**
     * 联系人
     */
    private String contractPerson = null;

    public String getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private List<String> menuList = null;

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }

    public Long getPlatformID() {
        return platformID;
    }

    public void setPlatformID(Long platformID) {
        this.platformID = platformID;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public Float getIncremental() {
        return incremental;
    }

    public void setIncremental(Float incremental) {
        this.incremental = incremental;
    }

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
