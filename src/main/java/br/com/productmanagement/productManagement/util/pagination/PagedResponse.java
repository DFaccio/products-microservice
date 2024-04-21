package br.com.productmanagement.productManagement.util.pagination;


import br.com.productmanagement.productManagement.interfaceAdapters.presenters.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PagedResponse<T extends Dto> {

    private Pagination page;

    private List<T> data;

}