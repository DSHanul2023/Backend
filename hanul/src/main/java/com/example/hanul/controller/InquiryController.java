package com.example.hanul.controller;

import com.example.hanul.dto.InquiryDTO;
import com.example.hanul.dto.ResponseDTO;
import com.example.hanul.model.InquiryEntity;
import com.example.hanul.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/inquiry")
public class InquiryController {
    @Autowired
    private InquiryService service;
    @PostMapping
    public ResponseEntity<?> createInquiry(@RequestBody InquiryDTO dto){ //@AuthenticationPrincipal String memberId,
        String memberId = "temporary-user";
        try{
            InquiryEntity entity = InquiryDTO.toEntity(dto);
            entity.setId(null);
            entity.setMemberId(memberId);
            List<InquiryEntity> entities = service.create(entity);
            List<InquiryDTO> dtos = entities.stream().map(InquiryDTO::new).collect(Collectors.toList());
            ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch(Exception e){
            String error = e.getMessage();
            ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveInquiryList(){
        String memberId = "temporary-user";

        List<InquiryEntity> entities = service.retrieve(memberId);
        List<InquiryDTO> dtos = entities.stream().map(InquiryDTO::new).collect(Collectors.toList());
        ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody InquiryDTO dto){
        String memberId = "temporary-user";

        InquiryEntity entity = InquiryDTO.toEntity(dto);
        entity.setMemberId(memberId);
        List<InquiryEntity> entities = service.update(entity);
        List<InquiryDTO> dtos=entities.stream().map(InquiryDTO::new).collect(Collectors.toList());
        ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody InquiryDTO dto){
        String memberId = "temporary-user";

        try{
            InquiryEntity entity = InquiryDTO.toEntity(dto);
            entity.setMemberId(memberId);
            List<InquiryEntity> entities = service.delete(entity);
            List<InquiryDTO> dtos = entities.stream().map(InquiryDTO::new).collect(Collectors.toList());
            ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            String error = e.getMessage();
            ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}