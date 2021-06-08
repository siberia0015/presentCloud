package fz.cs.daoyun.service;


import fz.cs.daoyun.domain.Param;

import java.util.List;


public interface IParamService {
    List<Param> getAll() throws Exception;

    void update(String key_eng, String key_name, Integer val) throws Exception;

    Param findById(Integer id) throws Exception;

    Param findByKeyEng(String key_eng) throws Exception;

    void updateByRecord(Param param);

    void insert(Param param);

    void delete(String keyEng);
}
