package com.maya.cbs.ledger.application.response;

import com.maya.cbs.core.domain.pagination.PageData;
import com.maya.cbs.core.domain.pagination.PaginatedResponse;
import com.maya.cbs.ledger.domain.studio.entity.Studio;
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
