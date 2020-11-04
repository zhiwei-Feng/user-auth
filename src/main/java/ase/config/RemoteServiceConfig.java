package ase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务调用配置文件，具体配置在application.properties中
 */
@Configuration
@ConfigurationProperties(prefix = "remote")
public class RemoteServiceConfig {

    private String check;
    private String findArticleById;
    private String findPcmemberByPcmemberIdAndStatus;
    private String findMeetingById;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getFindArticleById() {
        return findArticleById;
    }

    public void setFindArticleById(String findArticleById) {
        this.findArticleById = findArticleById;
    }

    public String getFindPcmemberByPcmemberIdAndStatus() {
        return findPcmemberByPcmemberIdAndStatus;
    }

    public void setFindPcmemberByPcmemberIdAndStatus(String findPcmemberByPcmemberIdAndStatus) {
        this.findPcmemberByPcmemberIdAndStatus = findPcmemberByPcmemberIdAndStatus;
    }

    public String getFindMeetingById() {
        return findMeetingById;
    }

    public void setFindMeetingById(String findMeetingById) {
        this.findMeetingById = findMeetingById;
    }
}
