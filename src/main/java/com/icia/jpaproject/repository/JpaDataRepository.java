package com.icia.jpaproject.repository;

import com.icia.jpaproject.entity.JpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface JpaDataRepository extends JpaRepository<JpaEntity, Long> {
    //숫자(intData) 검색용 메소드
    JpaEntity findByIntData(int i);
    //문자열(strData) 검색용 메소드 (LIKE 조건 처리)
    List<JpaEntity> findByStrDataContains(String keyword);
    //입력한 날짜 이전 데이터를 모두 가져오는 메소드
    //@Query("select j from JpaEntity as j where j.regData <= :date")
    @Query(value = "select * from jpatbl where reg_date <= : date",
            nativeQuery = true)
    List<JpaEntity> selectDateBefore(@Param("date") Timestamp dt);

}
