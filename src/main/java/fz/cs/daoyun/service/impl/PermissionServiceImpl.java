package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.domain.Permission;
import fz.cs.daoyun.mapper.PassportMapper;
import fz.cs.daoyun.mapper.PermissionMapper;
import fz.cs.daoyun.service.IPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



@Transactional
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public Permission findById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Permission findByName(String name) {
        return permissionMapper.selectByName(name);
    }

    @Override
    public void AddPermission(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void deletePermission(Integer id) {
            permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deletePermissionByName(String name) {
        permissionMapper.deleteByName(name);
    }

    @Override
    public void update(Permission permission) throws Exception {
        permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public Permission findByType(String p)  throws  Exception{
        Permission permission = permissionMapper.selectByType(p);
        return permission;
    }

    @Override
    public List<String> getTypes() throws  Exception {

        return permissionMapper.getTypes();
    }


}
