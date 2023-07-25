package com.dbs.mapper;

import com.dbs.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DistrictMapper {

    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个夫区域下的所有区域列表
     */
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
