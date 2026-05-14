package com.example.wecommerce_api.payload.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponse {
    private Long id;
    private String name;
    private LocalDateTime createdDate;
//    private List<BookMarkResponse> bookMark;
    private List<String> photo;
}
