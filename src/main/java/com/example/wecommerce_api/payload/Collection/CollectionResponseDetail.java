package com.example.wecommerce_api.payload.Collection;

import com.example.wecommerce_api.payload.BookMark.BookMarkResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponseDetail {
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private List<BookMarkResponse> bookMark;
}
