package fz.cs.daoyun.domain;

import java.util.Date;



public class Sign {
    private Long id;

    private Long userId;

    private Integer classId;

    private Date signTime;

    private Integer score;

    private Integer startSignId;

    private Double latitude;

    private Double longitude;

    @Override
    public String toString() {
        return "Sign{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", classId=" + classId +
                ", signTime=" + signTime +
                ", score=" + score +
                ", startSignId=" + startSignId +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getStartSignId() {
        return startSignId;
    }

    public void setStartSignId(Integer startSignId) {
        this.startSignId = startSignId;
    }
}
