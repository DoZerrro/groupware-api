package com.dq.aquaranth.userrole.dto.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO <E> {

    private int start, end, prev, next, first, last, total;

    private boolean prevFlag, nextFlag;

    private List<E> dtoList;

    private PageRequestDTO pageRequestDTO;
    //한 페이지에 보여질 페이지번호 개수
    private double pageLimit;

    public PageResponseDTO(PageRequestDTO pageRequestDTO, int total, List<E> dtoList){
        this.pageLimit = 5;
        this.pageRequestDTO = pageRequestDTO;
        this.total = total;
        this.dtoList = dtoList;
        //현재 페이지 번호
        int currentPage = pageRequestDTO.getPage();

        int size = pageRequestDTO.getSize();

        //페이지의 시작 페이지번호 계산
        this.start = (int)(Math.floor( (currentPage - 1) / (pageLimit) ) * pageLimit ) + 1;

        //페이지의 마지막 페이지번호 계산 123/pageLimit = 12.3
        if( (this.start + (pageLimit - 1) ) * size < total ){
            this.end = (int) ( this.start + (pageLimit - 1) );
        }else {
            this.end = (int) ( Math.ceil(total / (double)size ) );
        }

        //처음 페이지
        this.first = 1;

        //마지막 페이지
        this.last = (int) Math.ceil( total / (double)size );

        //이전 페이지
        this.prev = (int) (this.start - this.pageLimit < this.first ? this.first : this.start - this.pageLimit);

        //다음 페이지
        this.next = (int) (this.start + this.pageLimit > this.last ? this.last : this.start + this.pageLimit);

        //이전 페이지 블럭 활성화 여부
        this.prevFlag = this.start != this.first;

        //다음 페이지 블럭 활성화 여부
        this.nextFlag =  ( this.end * size ) < total;
    }
}















