package com.example.wecommerce_api.payload.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse<T> {
    private  String message;
    private  int status;
    private  T payload;
}
