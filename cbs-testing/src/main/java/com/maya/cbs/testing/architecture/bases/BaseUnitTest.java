package com.maya.cbs.testing.architecture.bases;

import com.maya.cbs.core.domain.pagination.PageData;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith (MockitoExtension.class)
public interface BaseUnitTest {

    PageData PAGE_DATA = PageData.of(0, 5, 5, 25);
}
