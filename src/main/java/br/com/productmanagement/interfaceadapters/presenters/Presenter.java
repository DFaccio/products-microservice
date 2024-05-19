package br.com.productmanagement.interfaceadapters.presenters;

import br.com.productmanagement.interfaceadapters.presenters.dto.Dto;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Presenter<T extends Serializable, D extends Dto> {

    D convert(T document);

    T convert(D dto) throws ValidationsException;

    default PagedResponse<D> convertDocuments(Page<T> page) {
        PagedResponse<D> paged = new PagedResponse<>();

        paged.setPage(new Pagination(page.getNumber(), page.getSize(), page.getTotalPages()));

        List<D> dada = convertDocuments(page.get().toList());

        paged.setData(dada);

        return paged;
    }

    default List<D> convertDocuments(List<T> documents) {
        if (documents == null) {
            return Collections.emptyList();
        }

        return documents.stream().map(this::convert).toList();
    }

    default List<T> convert(List<D> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }

        List<T> documents = new ArrayList<>();

        dtos.forEach(item -> {
            try {
                documents.add(convert(item));
            } catch (ValidationsException e) {
                throw new RuntimeException(e);
            }
        });

        return documents;
    }
}