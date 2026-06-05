package com.cinemayan.catalog.application.response;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.core.domain.pagination.PageData;
import com.cinemayan.core.domain.pagination.PaginatedResponse;
import lombok.Value;

import java.util.List;

@Value
public class GetStudiosResponse {

    PageData pageData;
    List<StudioResponse> studios;

    public static GetStudiosResponse of (PaginatedResponse<Studio> paginatedResponse) {
        return new GetStudiosResponse(paginatedResponse.pageData(), paginatedResponse.content()
            .stream()
            .map(StudioResponse::of)
            .toList());
    }
}
