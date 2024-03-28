package com.icia.jpaproject.service;

import com.icia.jpaproject.dto.JpaDto;
import com.icia.jpaproject.entity.JpaEntity;
import com.icia.jpaproject.repository.JpaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JpaService {
    @Autowired private JpaDataRepository jRepo;

    private ModelMapper mapper = new ModelMapper();

    public String getList(Model model) {
        log.info("getList()");
        //DB에서 데이터 목록 가져오기
        List<JpaEntity> jList = jRepo.findAll();
        //select * from jpatbl

        //Entity -> Dto로 매핑
        List<JpaDto> jdList = mapper.map(jList,
                new TypeToken<List<JpaDto>>(){}.getType());

        model.addAttribute("jdList",jdList);

        return "index";
    }

    public String insertData(JpaDto jdto, RedirectAttributes rttr) {
        log.info("insertData()");
        String view = null;
        String msg = null;

        //DTO -> Entity로 변환
        JpaEntity jEntity = mapper.map(jdto, JpaEntity.class);

        try {
            //id 컬럼 값이 없는 상태로 sava -> insert
            //id 컬럼 값이 있는 상태로 sava -> update
            jRepo.save(jEntity);
            view = "redirect:/";
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:writeForm";
            msg = "저장 실패";
        }
        rttr.addFlashAttribute("msg",msg);
        return view;
    }

    public String getData(String type, String keyword, Model model) {
        log.info("getData()");
        //다목적 메소드. 번호검색,문자열검색,숫자검색,날짜검색
        List<JpaEntity> jpaEntityList = null;
        List<JpaDto> jpaDtoList = null;
        JpaEntity jpaEntity = null;
        JpaDto jpaDto = null;

        //type에 따라 구분
        switch (type){
            case "no":
                long keyValue = Long.parseLong(keyword);
                jpaEntity = jRepo.findById(keyValue).orElse(null);
                if(jpaEntity != null){
                    jpaDto = mapper.map(jpaEntity, JpaDto.class);
                    //html에서 목록(list)로 받기때문에
                    // 목록으로 만들어서 보내준다.
                    jpaDtoList = new ArrayList<>();
                    jpaDtoList.add(jpaDto);
                }
                break;
        }
        model.addAttribute("jdList",jpaDtoList);
        return "index";
    }
}
