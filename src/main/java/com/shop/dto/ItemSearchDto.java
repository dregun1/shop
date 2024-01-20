package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ItemSearchDto {

    private String searchDateType; //현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회합니다.

    private ItemSellStatus searchSellStatus; //상품의 판매상태를 기준으로 상품 데이터를 조회합니다.

    private String searchBy; //상품을 조회할 때 어던 유형으로 죄할지 선택합니다. itemNm:상품명 / createBy:상품 등록자 아이디

    private String searchQuery = ""; //조회할 검색어 저장할 변수
}
