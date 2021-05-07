package fz.cs.daoyun.utils.dto;


import fz.cs.daoyun.domain.Role;

import java.util.List;

public class RoleDto {

    private Integer id; // 编号
    private String role; // 角色标识 程序中判断使用,如"admin"
    private String description; // 角色描述,UI界面显示使用
    private String resourceIds; // 拥有的资源
    private List<Long> resourceIdList;
    private Boolean available; // 是否可用,如果不可用将不会添加给用户
    private String resourceNames;

    public RoleDto() {

    }

    public RoleDto(Role role) {
        this.id = role.getRoleId();
        this.role = role.getRoleName();
//        this.resourceIds = role.getResourceIds();
//        this.resourceIdList = Arrays.asList(role.getResourceIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
//        this.available = role.getAvailable();
    }


    public String getResourceNames() {
        return resourceNames;
    }

    public void setResourceNames(String resourceNames) {
        this.resourceNames = resourceNames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = Math.toIntExact(id);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public List<Long> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<Long> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
