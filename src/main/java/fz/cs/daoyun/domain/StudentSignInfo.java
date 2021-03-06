package fz.cs.daoyun.domain;

import java.util.Date;

/**
 * Created by siberia0015 on 2021/6/10.
 */
public class StudentSignInfo {
    private Long studentId;

    private String studentName;

    private String schoolNumber;

    private String studentImage;

    private Date signTime;

    private Integer startSignId;

    private Double latitude;

    private Double longitude;

    private String nickname;

    private Double distance;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String studentNumber) {
        this.schoolNumber = studentNumber;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getStartSignId() {
        return startSignId;
    }

    public void setStartSignId(Integer startSignId) {
        this.startSignId = startSignId;
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
}
