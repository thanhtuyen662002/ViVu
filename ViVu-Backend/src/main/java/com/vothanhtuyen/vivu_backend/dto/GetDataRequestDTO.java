package com.vothanhtuyen.vivu_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDataRequestDTO {
    String location;
    String budget;
    String needs;
    String details;
}
