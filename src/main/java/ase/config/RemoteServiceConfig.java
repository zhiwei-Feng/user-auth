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
    private String findMeetingByMeetingName;
    private String findPcmemberRelationByPcmemberIdAndMeetingId;
    private String updatePcmemberRelation;
    private String findPcmemberRelationByPcmemberIdAndStatusNot;
    private String findMeetingByChairName;
    private String findArticleByContributorName;
    private String findMeetingByStatusAndChairNameNot;

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

    public String getFindMeetingByMeetingName() {
        return findMeetingByMeetingName;
    }

    public void setFindMeetingByMeetingName(String findMeetingByMeetingName) {
        this.findMeetingByMeetingName = findMeetingByMeetingName;
    }

    public String getFindPcmemberRelationByPcmemberIdAndMeetingId() {
        return findPcmemberRelationByPcmemberIdAndMeetingId;
    }

    public void setFindPcmemberRelationByPcmemberIdAndMeetingId(String findPcmemberRelationByPcmemberIdAndMeetingId) {
        this.findPcmemberRelationByPcmemberIdAndMeetingId = findPcmemberRelationByPcmemberIdAndMeetingId;
    }

    public String getUpdatePcmemberRelation() {
        return updatePcmemberRelation;
    }

    public void setUpdatePcmemberRelation(String updatePcmemberRelation) {
        this.updatePcmemberRelation = updatePcmemberRelation;
    }

    public String getFindPcmemberRelationByPcmemberIdAndStatusNot() {
        return findPcmemberRelationByPcmemberIdAndStatusNot;
    }

    public void setFindPcmemberRelationByPcmemberIdAndStatusNot(String findPcmemberRelationByPcmemberIdAndStatusNot) {
        this.findPcmemberRelationByPcmemberIdAndStatusNot = findPcmemberRelationByPcmemberIdAndStatusNot;
    }

    public String getFindMeetingByChairName() {
        return findMeetingByChairName;
    }

    public void setFindMeetingByChairName(String findMeetingByChairName) {
        this.findMeetingByChairName = findMeetingByChairName;
    }

    public String getFindArticleByContributorName() {
        return findArticleByContributorName;
    }

    public void setFindArticleByContributorName(String findArticleByContributorName) {
        this.findArticleByContributorName = findArticleByContributorName;
    }

    public String getFindMeetingByStatusAndChairNameNot() {
        return findMeetingByStatusAndChairNameNot;
    }

    public void setFindMeetingByStatusAndChairNameNot(String findMeetingByStatusAndChairNameNot) {
        this.findMeetingByStatusAndChairNameNot = findMeetingByStatusAndChairNameNot;
    }
}
