package com.adenon.sp.kernel.profile;

public class SecurityContext implements ISecurityContext {

    private static final long serialVersionUID = 1L;
    private String            userId;
    private String            companyId;
    private String            password;
    private String            sessionId;
    private String            ipAddress;
    private String[]          userRoles;
    private String            originatingIp;
    private String            requestChannel;

    public SecurityContext() {

    }

    @Override
    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String[] getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(String[] userRoles) {
        this.userRoles = userRoles;
    }

    public String getOriginatingIp() {
        return this.originatingIp;
    }

    public void setOriginatingIp(String originatingIp) {
        this.originatingIp = originatingIp;
    }

    public String getRequestChannel() {
        return this.requestChannel;
    }

    public void setRequestChannel(String requestChannel) {
        this.requestChannel = requestChannel;
    }

    @Override
    public String toString() {
        return "userId:" + this.userId + " sessionId:" + this.sessionId;

    }
}
