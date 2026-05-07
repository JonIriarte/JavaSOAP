package com.company.legacy.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.company.legacy.dao.RoleDao;
import com.company.legacy.model.Role;

@WebService(name = "RoleService", targetNamespace = "http://legacy.company.com/soap", portName = "RoleServicePort")
public class RoleService {

    private RoleDao roleDao = new RoleDao();

    @WebMethod
    public List<Role> getAllRoles() throws Exception {
        return roleDao.findAll();
    }

}
