package com.sparta.week6project.DAO.impl;

import com.sparta.week6project.DAO.interfaces.TitleService;
import com.sparta.week6project.DTO.TitleDTO;
import com.sparta.week6project.entities.Employee;
import com.sparta.week6project.entities.Title;
import com.sparta.week6project.entities.TitleId;
import com.sparta.week6project.mappers.TitleMapper;
import com.sparta.week6project.repositories.EmployeeRepository;
import com.sparta.week6project.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@EnableAutoConfiguration
public class TitleDAO implements TitleService {

    @Autowired
    private TitleMapper titleMapper;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public TitleDAO(TitleMapper titleMapper, TitleRepository titleRepository) {
        this.titleMapper = titleMapper;
        this.titleRepository = titleRepository;
    }

    public TitleDAO() {
    }

    @Override
    public Optional<TitleDTO> findById(TitleId id) {
        if(titleRepository.findById(id).isPresent()) {
            return Optional.of(titleMapper.titleToDTO(titleRepository.findById(id).get()));
        }
        return Optional.empty();
    }
    public Page<Title> findAllTitles(int pageNum){
        return titlePage(PageRequest.of(pageNum-1,50));
    }

    public Page<Title> titlePage(Pageable pageable){return titleRepository.findAll(pageable);}

    @Override
    public TitleDTO save(TitleDTO e) {
        return titleMapper.titleToDTO(titleRepository.save(titleMapper.dtoToTitle(e)));
    }

    @Override
    public void update(TitleDTO e, TitleId id) {
        Optional<Title> titleDb = titleRepository.findById(id);
        if(titleDb.isPresent()) {
            Title updatedTitle = titleDb.get();
            updatedTitle.setEmpNo(employeeRepository.findById(e.getEmpNo()).get());
            updatedTitle.setToDate(e.getToDate());
            titleRepository.save(updatedTitle);
        }
    }

    @Override
    public void deleteById(TitleId id) {
        if(titleRepository.findById(id).isPresent()) {
            titleRepository.deleteById(id);
        }
    }
}
