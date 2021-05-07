package fz.cs.daoyun.data.domain;

import java.util.Date;

public class Sign {
    private Long id;

    private String userName;

    private Integer classId;

    private Date signTime;

    private Integer score;

    private Integer singnTimes;

    private Integer startSignId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSingnTimes() {
        return singnTimes;
    }

    public void setSingnTimes(Integer singnTimes) {
        this.singnTimes = singnTimes;
    }

    public Integer getStartSignId() {
        return startSignId;
    }

    public void setStartSignId(Integer startSignId) {
        this.startSignId = startSignId;
    }
}